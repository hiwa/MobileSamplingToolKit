package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Questionnaire;
import model.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(UserXQuestionnaire.class)
public class UserXQuestionnaire_ { 

    public static volatile SingularAttribute<UserXQuestionnaire, Integer> id;
    public static volatile SingularAttribute<UserXQuestionnaire, Questionnaire> questionnaireid;
    public static volatile SingularAttribute<UserXQuestionnaire, User> userid;
    public static volatile SingularAttribute<UserXQuestionnaire, Short> completedstate;

}