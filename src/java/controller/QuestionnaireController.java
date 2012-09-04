package controller;

import model.Questionnaire;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import facade.QuestionnaireFacade;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

@ManagedBean(name = "questionnaireController")
@SessionScoped
public class QuestionnaireController implements Serializable {

    private Questionnaire current;
    private DataModel items = null;
    @EJB
    private facade.QuestionnaireFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String publishDay;
    private String publishMonth;
    private String publishYear;
    private String publishdate;
    private List<String> publishDayListItem = null;
    private List<String> publishMonthListItem = null;
    private List<String> publishYearListItem = null;
    private String deadlineDay;
    private String deadlineMonth;
    private String deadlineYear;
    private String deadlinedate;
    private List<String> deadlineDayListItem = null;
    private List<String> deadlineMonthListItem = null;
    private List<String> deadlineYearListItem = null;
    private String monthNumber;
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
     * EDIT VARIABLES These are the variables used when in the list of
     * questionnaires, if admin clicks on edit is needed to fill the dropdrown
     * menus in the edit page for a specific questionnaire.
     * *******************************************
     */
    private String editPublishDay;
    private String editPublishMonth;
    private String editPublishYear;
    private String editDeadlineDay;
    private String editDeadlineMonth;
    private String editDeadlineYear;
    private String editType;
    /**
     * *******************************************
     * DEFAULT SETTINGS (FINAL VARIABLES) 
     * YEARPERIOD Set the number of years in
     * the Year drop-down to forth
     */
    private final static int YEARPERIOD = 5;

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

    /**
     * CONSTRUCTOR
     */
    public QuestionnaireController() {
        loadDropDownPublishDay();
        loadDropDownPublishMonth();
        loadDropDownPublishYear();
        loadDropDownDeadlineDay();
        loadDropDownDeadlineMonth();
        loadDropDownDeadlineYear();
    }

    private void loadDropDownPublishDay() {
        publishDayListItem = new ArrayList<String>();
        for (int day = 1; day < 32; day++) {
            publishDayListItem.add(Integer.toString(day));
        }
    }

    private void loadDropDownPublishMonth() {
        publishMonthListItem = new ArrayList<String>();
        publishMonthListItem.add("January");
        publishMonthListItem.add("February");
        publishMonthListItem.add("March");
        publishMonthListItem.add("April");
        publishMonthListItem.add("May");
        publishMonthListItem.add("June");
        publishMonthListItem.add("July");
        publishMonthListItem.add("August");
        publishMonthListItem.add("September");
        publishMonthListItem.add("October");
        publishMonthListItem.add("November");
        publishMonthListItem.add("December");
    }

    private void loadDropDownPublishYear() {
        publishYearListItem = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i < year + YEARPERIOD; i++) {
            publishYearListItem.add(Integer.toString(i));
        }
    }
    
        private void loadDropDownDeadlineDay() {
        deadlineDayListItem = new ArrayList<String>();
        for (int day = 1; day < 32; day++) {
            deadlineDayListItem.add(Integer.toString(day));
        }
    }

    private void loadDropDownDeadlineMonth() {
        deadlineMonthListItem = new ArrayList<String>();
        deadlineMonthListItem.add("January");
        deadlineMonthListItem.add("February");
        deadlineMonthListItem.add("March");
        deadlineMonthListItem.add("April");
        deadlineMonthListItem.add("May");
        deadlineMonthListItem.add("June");
        deadlineMonthListItem.add("July");
        deadlineMonthListItem.add("August");
        deadlineMonthListItem.add("September");
        deadlineMonthListItem.add("October");
        deadlineMonthListItem.add("November");
        deadlineMonthListItem.add("December");
    }

    private void loadDropDownDeadlineYear() {
        deadlineYearListItem = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i < year + YEARPERIOD; i++) {
            deadlineYearListItem.add(Integer.toString(i));
        }
    }

    public Questionnaire getSelected() {
        if (current == null) {
            current = new Questionnaire();
            selectedItemIndex = -1;
        }
        return current;
    }

    private QuestionnaireFacade getFacade() {
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
        current = (Questionnaire) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Questionnaire();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setId(0);
            Date date = new Date();
            // Set current timestamp as createdate
            current.setCreatedate(new Timestamp(date.getTime()));
            if (current.getType().equals("onetime"))
                current.setFrequency((short)0);
            // Filling the publish date from the three publish drop-down menus
            String receivedPublishMonth = this.getPublishMonth();
            String publishMonthNumber = "";
            Months publishmonth = Months.valueOf(receivedPublishMonth.toUpperCase());
            switch (publishmonth) {
                case JANUARY:
                    publishMonthNumber = "01";
                    break;
                case FEBRUARY:
                    publishMonthNumber = "02";
                    break;
                case MARCH:
                    publishMonthNumber = "03";
                    break;
                case APRIL:
                    publishMonthNumber = "04";
                    break;
                case MAY:
                    publishMonthNumber = "05";
                    break;
                case JUNE:
                    publishMonthNumber = "06";
                    break;
                case JULY:
                    publishMonthNumber = "07";
                    break;
                case AUGUST:
                    publishMonthNumber = "08";
                    break;
                case SEPTEMBER:
                    publishMonthNumber = "09";
                    break;
                case OCTOBER:
                    publishMonthNumber = "10";
                    break;
                case NOVEMBER:
                    publishMonthNumber = "11";
                    break;
                case DECEMBER:
                    publishMonthNumber = "12";
                    break;
            }
            publishdate = this.getPublishYear() + "-" + publishMonthNumber + "-" + this.getPublishDay();
            SimpleDateFormat dateFormatPublish = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormatPublish.parse(publishdate);
                current.setPublishdate(convertedDate);
            } catch (ParseException e) {
                e.getCause();
            }
            // Filling the deadline date from the three deadline drop-down menus
            String receivedDeadlineMonth = this.getDeadlineMonth();
            String deadlineMonthNumber = "";
            Months deadlinemonth = Months.valueOf(receivedDeadlineMonth.toUpperCase());
            switch (deadlinemonth) {
                case JANUARY:
                    deadlineMonthNumber = "01";
                    break;
                case FEBRUARY:
                    deadlineMonthNumber = "02";
                    break;
                case MARCH:
                    deadlineMonthNumber = "03";
                    break;
                case APRIL:
                    deadlineMonthNumber = "04";
                    break;
                case MAY:
                    deadlineMonthNumber = "05";
                    break;
                case JUNE:
                    deadlineMonthNumber = "06";
                    break;
                case JULY:
                    deadlineMonthNumber = "07";
                    break;
                case AUGUST:
                    deadlineMonthNumber = "08";
                    break;
                case SEPTEMBER:
                    deadlineMonthNumber = "09";
                    break;
                case OCTOBER:
                    deadlineMonthNumber = "10";
                    break;
                case NOVEMBER:
                    deadlineMonthNumber = "11";
                    break;
                case DECEMBER:
                    deadlineMonthNumber = "12";
                    break;
            }
            deadlinedate = this.getDeadlineYear() + "-" + deadlineMonthNumber + "-" + this.getDeadlineDay();
            SimpleDateFormat dateFormatDeadline = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormatDeadline.parse(deadlinedate);
                current.setDeadline(convertedDate);
            } catch (ParseException e) {
                e.getCause();
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionnaireCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Questionnaire) getItems().getRowData();
        if (current != null){
            // Send the publish date information to the "Edit" page
            editType = current.getType();
            Date editPublishDate = current.getPublishdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(editPublishDate);
            editPublishDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            editPublishYear = String.valueOf(cal.get(Calendar.YEAR));
            String monthNumber = String.valueOf(cal.get(Calendar.MONTH)+1);
            switch (Integer.valueOf(monthNumber)) {
                case 1:
                    editPublishMonth = "January";
                    break;
                case 2:
                    editPublishMonth = "February";
                    break;
                case 3:
                    editPublishMonth = "March";
                    break;
                case 4:
                    editPublishMonth = "April";
                    break;
                case 5:
                    editPublishMonth = "May";
                    break;
                case 6:
                    editPublishMonth = "June";
                    break;
                case 7:
                    editPublishMonth = "July";
                    break;
                case 8:
                    editPublishMonth = "August";
                    break;
                case 9:
                    editPublishMonth = "September";
                    break;
                case 10:
                    editPublishMonth = "October";
                    break;
                case 11:
                    editPublishMonth = "November";
                    break;
                case 12:
                    editPublishMonth = "December";
                    break;
            }
            publishDay = editPublishDay;
            publishMonth = editPublishMonth;
            publishYear = editPublishYear;
            // Send the deadline date information to the "Edit" page
            Date editDeadlineDate = current.getDeadline();
            cal.setTime(editDeadlineDate);
            editDeadlineDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            editDeadlineYear = String.valueOf(cal.get(Calendar.YEAR));
            monthNumber = String.valueOf(cal.get(Calendar.MONTH)+1);
            switch (Integer.valueOf(monthNumber)) {
                case 1:
                    editDeadlineMonth = "January";
                    break;
                case 2:
                    editDeadlineMonth = "February";
                    break;
                case 3:
                    editDeadlineMonth = "March";
                    break;
                case 4:
                    editDeadlineMonth = "April";
                    break;
                case 5:
                    editDeadlineMonth = "May";
                    break;
                case 6:
                    editDeadlineMonth = "June";
                    break;
                case 7:
                    editDeadlineMonth = "July";
                    break;
                case 8:
                    editDeadlineMonth = "August";
                    break;
                case 9:
                    editDeadlineMonth = "September";
                    break;
                case 10:
                    editDeadlineMonth = "October";
                    break;
                case 11:
                    editDeadlineMonth = "November";
                    break;
                case 12:
                    editDeadlineMonth = "December";
                    break;
            }
            deadlineDay = editDeadlineDay;
            deadlineMonth = editDeadlineMonth;
            deadlineYear = editDeadlineYear;

        }
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            // Update the publish date to the new value
            String receivedMonth = this.getPublishMonth();
            monthNumber = "";
            UserController.Months month = UserController.Months.valueOf(receivedMonth.toUpperCase());
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
            String publishDate = publishYear + "-" + monthNumber + "-" + publishDay;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormat.parse(publishDate);
                current.setPublishdate(convertedDate);
            } catch (ParseException e) {
                e.getCause();
            }
            // Update the deadline date to the new value
            receivedMonth = this.getDeadlineMonth();
            monthNumber = "";
            month = UserController.Months.valueOf(receivedMonth.toUpperCase());
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
            String deadlineDate = deadlineYear + "-" + monthNumber + "-" + deadlineDay;
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date convertedDate = dateFormat.parse(deadlineDate);
                current.setDeadline(convertedDate);
            } catch (ParseException e) {
                e.getCause();
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionnaireUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Questionnaire) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionnaireDeleted"));
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

    @FacesConverter(forClass = Questionnaire.class)
    public static class QuestionnaireControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionnaireController controller = (QuestionnaireController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionnaireController");
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
            if (object instanceof Questionnaire) {
                Questionnaire o = (Questionnaire) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + QuestionnaireController.class.getName());
            }
        }
    }

    public String getDeadlineDay() {
        return deadlineDay;
    }

    public void setDeadlineDay(String deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    public List<String> getDeadlineDayListItem() {
        return deadlineDayListItem;
    }

    public void setDeadlineDayListItem(List<String> deadlineDayListItem) {
        this.deadlineDayListItem = deadlineDayListItem;
    }

    public String getDeadlineMonth() {
        return deadlineMonth;
    }

    public void setDeadlineMonth(String deadlineMonth) {
        this.deadlineMonth = deadlineMonth;
    }

    public List<String> getDeadlineMonthListItem() {
        return deadlineMonthListItem;
    }

    public void setDeadlineMonthListItem(List<String> deadlineMonthListItem) {
        this.deadlineMonthListItem = deadlineMonthListItem;
    }

    public String getDeadlineYear() {
        return deadlineYear;
    }

    public void setDeadlineYear(String deadlineYear) {
        this.deadlineYear = deadlineYear;
    }

    public List<String> getDeadlineYearListItem() {
        return deadlineYearListItem;
    }

    public void setDeadlineYearListItem(List<String> deadlineYearListItem) {
        this.deadlineYearListItem = deadlineYearListItem;
    }

    public String getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(String deadlinedate) {
        this.deadlinedate = deadlinedate;
    }

    public String getPublishDay() {
        return publishDay;
    }

    public void setPublishDay(String publishDay) {
        this.publishDay = publishDay;
    }

    public List<String> getPublishDayListItem() {
        return publishDayListItem;
    }

    public void setPublishDayListItem(List<String> publishDayListItem) {
        this.publishDayListItem = publishDayListItem;
    }

    public String getPublishMonth() {
        return publishMonth;
    }

    public void setPublishMonth(String publishMonth) {
        this.publishMonth = publishMonth;
    }

    public List<String> getPublishMonthListItem() {
        return publishMonthListItem;
    }

    public void setPublishMonthListItem(List<String> publishMonthListItem) {
        this.publishMonthListItem = publishMonthListItem;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public List<String> getPublishYearListItem() {
        return publishYearListItem;
    }

    public void setPublishYearListItem(List<String> publishYearListItem) {
        this.publishYearListItem = publishYearListItem;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getEditDeadlineDay() {
        return editDeadlineDay;
    }

    public void setEditDeadlineDay(String editDeadlineDay) {
        this.editDeadlineDay = editDeadlineDay;
    }

    public String getEditDeadlineMonth() {
        return editDeadlineMonth;
    }

    public void setEditDeadlineMonth(String editDeadlineMonth) {
        this.editDeadlineMonth = editDeadlineMonth;
    }

    public String getEditDeadlineYear() {
        return editDeadlineYear;
    }

    public void setEditDeadlineYear(String editDeadlineYear) {
        this.editDeadlineYear = editDeadlineYear;
    }

    public String getEditPublishDay() {
        return editPublishDay;
    }

    public void setEditPublishDay(String editPublishDay) {
        this.editPublishDay = editPublishDay;
    }

    public String getEditPublishMonth() {
        return editPublishMonth;
    }

    public void setEditPublishMonth(String editPublishMonth) {
        this.editPublishMonth = editPublishMonth;
    }

    public String getEditPublishYear() {
        return editPublishYear;
    }

    public void setEditPublishYear(String editPublishYear) {
        this.editPublishYear = editPublishYear;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    
    
}
