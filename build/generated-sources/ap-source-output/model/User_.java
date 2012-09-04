package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Choiceanswer;
import model.Textanswer;
import model.UserXOrganization;
import model.UserXQuestionnaire;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Boolean> sex;
    public static volatile CollectionAttribute<User, Textanswer> textanswerCollection;
    public static volatile SingularAttribute<User, String> about;
    public static volatile SingularAttribute<User, String> lastname;
    public static volatile SingularAttribute<User, String> image;
    public static volatile CollectionAttribute<User, UserXQuestionnaire> userXQuestionnaireCollection;
    public static volatile SingularAttribute<User, String> firstname;
    public static volatile CollectionAttribute<User, UserXOrganization> userXOrganizationCollection;
    public static volatile SingularAttribute<User, String> type;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Boolean> banned;
    public static volatile SingularAttribute<User, String> country;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile CollectionAttribute<User, Choiceanswer> choiceanswerCollection;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Date> registerdate;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Date> birthdate;

}