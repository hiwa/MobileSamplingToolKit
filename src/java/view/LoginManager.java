package view;

import bean.UserEJB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.User;

/**
 *
 * @author Pedram
 */
@Named(value = "loginManager")
@SessionScoped
public class LoginManager implements Serializable {

    @EJB
    UserEJB userEJB;
    User currentUser;
    private String username = "";
    private String password = "";
    private String lblUserAuthenticated = "";
    private boolean newUser = false;
    private boolean isUserAuthenticated = false;
    private boolean isAdmin = false;
    private boolean isBanned = false;
    private boolean signout = false;

    public boolean authenticateUser() {
        isUserAuthenticated = false;
        isAdmin = false;
        List result = userEJB.authenticateUser(username, password);
        if (!result.isEmpty()) {
            User foundUser = (User) result.get(0);
            if (foundUser.getUsername().equalsIgnoreCase(username) && foundUser.getPassword().equals(password)) {
                if (foundUser.getBanned()) {
                    isBanned = true;
                    this.setLblUserAuthenticated("Unfortunately you are banned from entering the system.");

                } else {
                    isUserAuthenticated = true;
                    this.currentUser = foundUser;
                    this.currentUser.setFirstname(foundUser.getFirstname());
                    if (foundUser.getType().equals("admin")) {
                        isAdmin = true;
                    }

                    return true;
                }

            } else if (foundUser.getUsername().equalsIgnoreCase(username) && !foundUser.getPassword().equals(password)) {
                this.setLblUserAuthenticated("Wrong password.");

                return false;
            }
        } else {
            this.setLblUserAuthenticated("No such user found");
            return false;
        }
        return false;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsUserAuthenticated() {
        return isUserAuthenticated;
    }

    public void setIsUserAuthenticated(boolean isUserAuthenticated) {
        this.isUserAuthenticated = isUserAuthenticated;
    }

    public String getLblUserAuthenticated() {
        return lblUserAuthenticated;
    }

    public void setLblUserAuthenticated(String lblUserAuthenticated) {
        this.lblUserAuthenticated = lblUserAuthenticated;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public boolean isSignout() {
        return signout;
    }

    public void setSignout(boolean signout) {
        this.signout = signout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Creates a new instance of loginManager
     */
    public LoginManager() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "homepage?faces-redirect=true";

    }
}