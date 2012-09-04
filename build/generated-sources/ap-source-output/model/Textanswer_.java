package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Question;
import model.Questionnaire;
import model.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(Textanswer.class)
public class Textanswer_ { 

    public static volatile SingularAttribute<Textanswer, Integer> id;
    public static volatile SingularAttribute<Textanswer, String> providedanswer;
    public static volatile SingularAttribute<Textanswer, Questionnaire> questionnaireid;
    public static volatile SingularAttribute<Textanswer, User> userid;
    public static volatile SingularAttribute<Textanswer, Question> questionid;
    public static volatile SingularAttribute<Textanswer, Date> answertimestamp;

}