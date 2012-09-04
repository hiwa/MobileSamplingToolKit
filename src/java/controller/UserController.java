package controller;

import model.User;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import facade.OrganizationFacade;
import facade.UserFacade;
import facade.UserXOrganizationFacade;
import java.io.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Organization;
import model.UserXOrganization;

/**
 *
 * @author Pedram
 */
@ManagedBean(name = "userController")
@SessionScoped
public class UserController implements Serializable {

    /**
     *
     * DEFAULT SETTINGS (FINAL VARIABLES) YEARPERIOD Set the number of years in
     * the Year drop-down to go back from the current year
     */
    private final static int YEARPERIOD = 95;
    // SELECTEDCOUNTRY
    // for the country dropdown menu
    private final static String SELECTEDCOUNTRY = "Sweden";
    /*
     * COUNTRYLISTFILELOCATION File location of the list of countries. This
     * property has to be set after installation of the server
     */
    private final static String COUNTRYLISTFILELOCATION = "C:\\Users\\Pedram\\Documents\\NetBeansProjects\\MobileSamplingToolkit0.4\\web\\countrylist.txt";
    private User current;
    private UserXOrganization currentUserXorganization;
    private DataModel items = null;
    @EJB
    private facade.UserFacade ejbFacade;
    @EJB
    private facade.OrganizationFacade ejbOrganizationFacade;
    @EJB
    private facade.UserXOrganizationFacade ejbUserXOrganizationFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<String> countriesListItem = null;
    private String birthDay;
    private String birthMonth;
    private String birthYear;
    private String birthdate;
    private List<String> birthDayListItem = null;
    private List<String> birthMonthListItem = null;
    private List<String> birthYearListItem = null;
    // list of all the organizations from db
    private String[] organizations = null;
    private List<String> organizationListItem = null;
    private List<SelectableItem> selectableOrganizations = null;
    /**
     * ******************************************
     * TIMEZONE These are the variables used when in the list of users, if
     * Because the day of the month displayed in the lists usually differs by
     * one day that what's actually in the data base we have to use the time
     * zone property.
     */
    TimeZone timeZone = TimeZone.getDefault();
    /**
     * ******************************************
     * EDIT VARIABLES These are the variables used when in the list of users, if
     * admin clicks on edit is needed to fill the dropdrown menus in the edit
     * page for a specific users. ******************************************
     */
    private String editBirthDay;
    private String editBirthMonth;
    private String editBirthYear;
    private String monthNumber;
    private String editType;
    private String editCountry;
    private boolean editBanned;
    private boolean editSex;

    public enum Months {

        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER
    };

    public UserController() {
        loadDropDownCountry();
        loadDropDownBirthDay();
        loadDropDownBirthMonth();
        loadDropDownBirthYear();
    }
    
    // Called after the constructor
    @PostConstruct
    public void init() {
        List<Organization> organizationresult = ejbOrganizationFacade.findAll();
        //System.out.println(organizationresult.get(1).getName());
        selectableOrganizations = new ArrayList<SelectableItem>();
        for (Organization item: organizationresult)
            selectableOrganizations.add(new SelectableItem(Integer.valueOf(item.getId()),item.getName(),Boolean.FALSE));

    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }

        return current;
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    private void loadDropDownCountry() {
        countriesListItem = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(COUNTRYLISTFILELOCATION);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                countriesListItem.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void loadDropDownBirthDay() {
        birthDayListItem = new ArrayList<String>();
        for (int day = 1; day < 32; day++) {
            birthDayListItem.add(Integer.toString(day));
        }
    }

    private void loadDropDownBirthMonth() {
        birthMonthListItem = new ArrayList<String>();
        birthMonthListItem.add("January");
        birthMonthListItem.add("February");
        birthMonthListItem.add("March");
        birthMonthListItem.add("April");
        birthMonthListItem.add("May");
        birthMonthListItem.add("June");
        birthMonthListItem.add("July");
        birthMonthListItem.add("August");
        birthMonthListItem.add("September");
        birthMonthListItem.add("October");
        birthMonthListItem.add("November");
        birthMonthListItem.add("December");
    }

    private void loadDropDownBirthYear() {
        birthYearListItem = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i > year - YEARPERIOD; i--) {
            birthYearListItem.add(Integer.toString(i));
        }
    }

    private void loadOrganizations() {
//        organizationListItem = new ArrayList<String>();
//        organizationListItem.add("KTH");
//        organizationListItem.add("Uppsala");
//        organizationListItem.add("Lund");
//
//        String organizationresult = ejbOrganizationFacade.OrganizationList();
//        System.out.println("SHOUT SHOUT!!!: "+ organizationresult);
//        if (ejbOrganizationFacade.OrganizationList() == null) {
//            System.out.println("list returns null");
//        } else {
//             System.out.println("list is not null");
//            //organizationresult = ejbOrganizationFacade.OrganizationList();
//        }
        //Organization foundOrganization;
        //foundOrganization  = ejbOrganizationFacade.OrganizationList().get(0);
        //System.out.println(foundOrganization.getName());
//        try{
//        for (Organization item: ejbOrganizationFacade.OrganizationList())
//           System.out.println(item.getName());
//        }catch(Exception e){
//            System.out.println("HERE!!!!!!: "+e.getCause());
//        }
//        
//        for (String item : organizationListItem) {
//            OrganizationCheckMap.put(item, Boolean.FALSE);
//        }
    }

    // Used when user is Signing up for a new account.
    public String create() {
        try {
            // set the id of the new entry to zero;
            // we don't need the user to set the id themselves.
            current.setId(0);
            current.setType("user");
            current.setBanned(Boolean.FALSE);
            Date date = new Date();
            // Set current timestamp as registerdate
            current.setRegisterdate(new Timestamp(date.getTime()));
            String receivedMonth = this.getBirthMonth();

            monthNumber = "";
            Months month = Months.valueOf(receivedMonth.toUpperCase());
            switch (month) {
                case JANUARY:
                    monthNumber = "01";
                    break;
                case FEBRUARY:
                    monthNumber = "02";
                    break;
                case MARCH:
                    monthNumber = "03";
                    break;
                case APRIL:
                    monthNumber = "04";
                    break;
                case MAY:
                    monthNumber = "05";
                    break;
                case JUNE:
                    monthNumber = "06";
                    break;
                case JULY:
                    monthNumber = "07";
                    break;
                case AUGUST:
                    monthNumber = "08";
                    break;
                case SEPTEMBER:
                    monthNumber = "09";
                    break;
                case OCTOBER:
                    monthNumber = "10";
                    break;
                case NOVEMBER:
                    monthNumber = "11";
                    break;
                case DECEMBER:
                    monthNumber = "12";
                    break;
            }
            birthdate = this.getBirthYear() + "-" + monthNumber + "-" + this.getBirthDay();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormat.parse(birthdate);
                current.setBirthdate(convertedDate);
            } catch (ParseException e) {
                e.getCause();
            }
            getFacade().create(current);
            currentUserXorganization = new UserXOrganization();
            for (SelectableItem item: selectableOrganizations)
            {
                if (item.isSelected()){
                currentUserXorganization.setId(0);
                currentUserXorganization.setUserid(current);
                Organization currentOrganization = ejbOrganizationFacade.find(item.getId());
                currentUserXorganization.setOrganizationid(currentOrganization);
                getEjbUserXOrganizationFacade().create(currentUserXorganization);
                }
            }
             
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    // Called when admin is adding a new user
    public String createUserByAdmin() {
        try {
            // set the id of the new entry to zero;
            // we don't need the user to set the id themselves.
            current.setId(0);
            Date date = new Date();
            // Set current timestamp as registerdate
            current.setRegisterdate(new Timestamp(date.getTime()));
            String receivedMonth = this.getBirthMonth();
            monthNumber = "";
            Months month = Months.valueOf(receivedMonth.toUpperCase());
            switch (month) {
                case JANUARY:
                    monthNumber = "01";
                    break;
                case FEBRUARY:
                    monthNumber = "02";
                    break;
                case MARCH:
                    monthNumber = "03";
                    break;
                case APRIL:
                    monthNumber = "04";
                    break;
                case MAY:
                    monthNumber = "05";
                    break;
                case JUNE:
                    monthNumber = "06";
                    break;
                case JULY:
                    monthNumber = "07";
                    break;
                case AUGUST:
                    monthNumber = "08";
                    break;
                case SEPTEMBER:
                    monthNumber = "09";
                    break;
                case OCTOBER:
                    monthNumber = "10";
                    break;
                case NOVEMBER:
                    monthNumber = "11";
                    break;
                case DECEMBER:
                    monthNumber = "12";
                    break;
            }
            birthdate = this.getBirthYear() + "-" + monthNumber + "-" + this.getBirthDay();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            try {
                Date convertedDate = dateFormat.parse(birthdate);
                current.setBirthdate(convertedDate);
            } catch (ParseException e) {
                e.getMessage();
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        if (current != null) {
            editType = current.getType();
            editSex = current.getSex();
            editBanned = current.getBanned();
            editCountry = current.getCountry();
            Date editBirthDate = current.getBirthdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(editBirthDate);
            editBirthDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            editBirthYear = String.valueOf(cal.get(Calendar.YEAR));
            monthNumber = String.valueOf(cal.get(Calendar.MONTH) + 1);
            switch (Integer.valueOf(monthNumber)) {
                case 1:
                    editBirthMonth = "January";
                    break;
                case 2:
                    editBirthMonth = "February";
                    break;
                case 3:
                    editBirthMonth = "March";
                    break;
                case 4:
                    editBirthMonth = "April";
                    break;
                case 5:
                    editBirthMonth = "May";
                    break;
                case 6:
                    editBirthMonth = "June";
                    break;
                case 7:
                    editBirthMonth = "July";
                    break;
                case 8:
                    editBirthMonth = "August";
                    break;
                case 9:
                    editBirthMonth = "September";
                    break;
                case 10:
                    editBirthMonth = "October";
                    break;
                case 11:
                    editBirthMonth = "November";
                    break;
                case 12:
                    editBirthMonth = "December";
                    break;
            }
            if (!editType.isEmpty()) {
                current.setType(editType);
            }
            if (!editBanned) {
                current.setBanned(editBanned);
            }
            if (!editSex) {
                current.setSex(editSex);
            }
            if (!editCountry.isEmpty()) {
                current.setCountry(editCountry);
            } else {
                current.setCountry(SELECTEDCOUNTRY);
            }
            birthDay = editBirthDay;
            birthMonth = editBirthMonth;
            birthYear = editBirthYear;

        }

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            String receivedMonth = this.getBirthMonth();
            monthNumber = "";
            Months month = Months.valueOf(receivedMonth.toUpperCase());
            switch (month) {
                case JANUARY:
                    monthNumber = "01";
                    break;
                case FEBRUARY:
                    monthNumber = "02";
                    break;
                case MARCH:
                    monthNumber = "03";
                    break;
                case APRIL:
                    monthNumber = "04";
                    break;
                case MAY:
                    monthNumber = "05";
                    break;
                case JUNE:
                    monthNumber = "06";
                    break;
                case JULY:
                    monthNumber = "07";
                    break;
                case AUGUST:
                    monthNumber = "08";
                    break;
                case SEPTEMBER:
                    monthNumber = "09";
                    break;
                case OCTOBER:
                    monthNumber = "10";
                    break;
                case NOVEMBER:
                    monthNumber = "11";
                    break;
                case DECEMBER:
                    monthNumber = "12";
                    break;
            }
            String birthDate = birthYear + "-" + monthNumber + "-" + birthDay;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormat.parse(birthDate);
                current.setBirthdate(convertedDate);
                System.out.println(birthDate);
            } catch (ParseException e) {
                e.getCause();
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public List<String> getCountriesListItem() {
        return countriesListItem;
    }

    public void setCountriesListItem(List<String> countriesListItem) {
        this.countriesListItem = countriesListItem;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserController.class.getName());
            }
        }
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public List<String> getBirthDayListItem() {
        return birthDayListItem;
    }

    public void setBirthDayListItem(List<String> birthDayListItem) {
        this.birthDayListItem = birthDayListItem;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public List<String> getBirthMonthListItem() {
        return birthMonthListItem;
    }

    public void setBirthMonthListItem(List<String> birthMonthListItem) {
        this.birthMonthListItem = birthMonthListItem;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public List<String> getBirthYearListItem() {
        return birthYearListItem;
    }

    public void setBirthYearListItem(List<String> birthYearListItem) {
        this.birthYearListItem = birthYearListItem;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEditBirthDay() {
        return editBirthDay;
    }

    public void setEditBirthDay(String editBirthDay) {
        this.editBirthDay = editBirthDay;
    }

    public String getEditBirthMonth() {
        return editBirthMonth;
    }

    public void setEditBirthMonth(String editBirthMonth) {
        this.editBirthMonth = editBirthMonth;
    }

    public String getEditBirthYear() {
        return editBirthYear;
    }

    public void setEditBirthYear(String editBirthYear) {
        this.editBirthYear = editBirthYear;
    }

    public String getEditCountry() {
        return editCountry;
    }

    public void setEditCountry(String editCountry) {
        this.editCountry = editCountry;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public boolean isEditBanned() {
        return editBanned;
    }

    public void setEditBanned(boolean editBanned) {
        this.editBanned = editBanned;
    }

    public boolean isEditSex() {
        return editSex;
    }

    public void setEditSex(boolean editSex) {
        this.editSex = editSex;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String[] getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String[] organizations) {
        this.organizations = organizations;
    }

    public List<String> getOrganizationListItem() {
        return organizationListItem;
    }

    public void setOrganizationListItem(List<String> organizationListItem) {
        this.organizationListItem = organizationListItem;
    }

    public OrganizationFacade getEjbOrganizationFacade() {
        return ejbOrganizationFacade;
    }

    public void setEjbOrganizationFacade(OrganizationFacade ejbOrganizationFacade) {
        this.ejbOrganizationFacade = ejbOrganizationFacade;
    }

    public List<SelectableItem> getSelectableOrganizations() {
        return selectableOrganizations;
    }

    public void setSelectableOrganizations(List<SelectableItem> selectableOrganizations) {
        this.selectableOrganizations = selectableOrganizations;
    }

    public UserXOrganizationFacade getEjbUserXOrganizationFacade() {
        return ejbUserXOrganizationFacade;
    }

    public void setEjbUserXOrganizationFacade(UserXOrganizationFacade ejbUserXOrganizationFacade) {
        this.ejbUserXOrganizationFacade = ejbUserXOrganizationFacade;
    }
    
    
}
