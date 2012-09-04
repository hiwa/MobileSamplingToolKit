/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import com.sun.xml.ws.api.tx.at.Transactional;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.*;
import javax.inject.Named;
import javax.persistence.*;
import model.Choiceanswer;
import model.Question;
import model.User;

/**
 *
 * @author Pedram
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class UserEJB {

    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    EntityManager em;
    EntityManagerFactory emf;
    private String username = "";
    private String password = "";
    private String passwordconfirm = "";
    private String firstname = "";
    private String lastname = "";
    private String email = "";
    private String country = "Sweden"; // the default value of country
    private String about = "";
    private String image = "";
    //birthdate variables
    private int birthDay;
    private String birthMonth;
    private int birthYear;
    private String birthdate;
    private boolean sex = false; // sex: false = woman, true = man
    //list of countries
    private List<String> countriesListItem = null;
    // birthdate lists
    private List<String> birthDayListItem = null;
    private List<String> birthMonthListItem = null;
    private List<String> birthYearListItem = null;
    private String message = ""; // Display message in the bottom of the form

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

    // Contructor
    public UserEJB() {
    }

    public List authenticateUser(String username, String password) {
        List results = null;
        try {
            em = Persistence.createEntityManagerFactory("MobileSamplingToolkit0.4PU").createEntityManager();
            Query queryFindUsername = em.createNamedQuery("User.findByUsername");
            queryFindUsername.setParameter("username", username);
            results = queryFindUsername.getResultList();           
        } catch (EJBException ejbex) {
            System.out.println(ejbex.getMessage());
        } finally {
            em.close();
            
        }
        return results;

    }
    
    @Transactional
    public String addUser(String username,String password,String firstname,String lastname){
        User user = new User();
        em = Persistence.createEntityManagerFactory("MobileSamplingToolkitPU").createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            return "error";
        } finally {
            em.clear();
            em.close(); 
            
            return "success";
        }
//        user.setEmail(this.getEmail());
//        user.setCountry(this.getCountry());
//        user.setAbout(this.getAbout());
//        user.setImage(this.getImage());
//        user.setSex(this.isSex());
//        String receivedMonth = this.getBirthMonth();
//        String monthNumber = "";
//        Months month = Months.valueOf(receivedMonth.toUpperCase());
//        switch (month) {
//            case JANUARY:
//                monthNumber = "01";
//                break;
//            case FEBRUARY:
//                monthNumber = "02";
//                break;
//            case MARCH:
//                monthNumber = "03";
//                break;
//            case APRIL:
//                monthNumber = "04";
//                break;
//            case MAY:
//                monthNumber = "05";
//                break;
//            case JUNE:
//                monthNumber = "06";
//                break;
//            case JULY:
//                monthNumber = "07";
//                break;
//            case AUGUST:
//                monthNumber = "08";
//                break;
//            case SEPTEMBER:
//                monthNumber = "09";
//                break;
//            case OCTOBER:
//                monthNumber = "10";
//                break;
//            case NOVEMBER:
//                monthNumber = "11";
//                break;
//            case DECEMBER:
//                monthNumber = "12";
//                break;
//        }
//        birthdate = this.getBirthYear() + "-" + month + this.getBirthDay();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//        try {
//            Date convertedDate = dateFormat.parse(birthdate);
//            user.setBirthdate(convertedDate);
//        } catch (ParseException e) {
//            e.getMessage();
//        }

    }

    /**
     * GETTERS AND SETTERS
     */
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordconfirm() {
        return passwordconfirm;
    }

    public void setPasswordconfirm(String passwordconfirm) {
        this.passwordconfirm = passwordconfirm;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCountriesListItems() {
        return countriesListItem;
    }

    public void setCountriesListItems(List<String> countriesListItem) {
        this.countriesListItem = countriesListItem;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<String> getBirthDayList() {
        return birthDayListItem;
    }

    public void setBirthDayList(List<String> birthDayListItem) {
        this.birthDayListItem = birthDayListItem;
    }

    public List<String> getBirthMonthList() {
        return birthMonthListItem;
    }

    public void setBirthMonthList(List<String> birthMonthListItem) {
        this.birthMonthListItem = birthMonthListItem;
    }

    public List<String> getBirthYearList() {
        return birthYearListItem;
    }

    public void setBirthYearList(List<String> birthYearListItem) {
        this.birthYearListItem = birthYearListItem;
    }

    public List<String> getCountriesListItem() {
        return countriesListItem;
    }

    public void setCountriesListItem(List<String> countriesListItem) {
        this.countriesListItem = countriesListItem;
    }

    public List<String> getBirthDayListItem() {
        return birthDayListItem;
    }

    public void setBirthDayListItem(List<String> birthDayListItem) {
        this.birthDayListItem = birthDayListItem;
    }

    public List<String> getBirthMonthListItem() {
        return birthMonthListItem;
    }

    public void setBirthMonthListItem(List<String> birthMonthListItem) {
        this.birthMonthListItem = birthMonthListItem;
    }

    public List<String> getBirthYearListItem() {
        return birthYearListItem;
    }

    public void setBirthYearListItem(List<String> birthYearListItem) {
        this.birthYearListItem = birthYearListItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
