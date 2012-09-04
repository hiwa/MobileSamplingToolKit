package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Question;
import model.Questionnaire;
import model.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(Choiceanswer.class)
public class Choiceanswer_ { 

    public static volatile SingularAttribute<Choiceanswer, Integer> id;
    public static volatile SingularAttribute<Choiceanswer, String> providedanswer;
    public static volatile SingularAttribute<Choiceanswer, Questionnaire> questionnaireid;
    public static volatile SingularAttribute<Choiceanswer, User> userid;
    public static volatile SingularAttribute<Choiceanswer, Question> questionid;
    public static volatile SingularAttribute<Choiceanswer, Date> answertimestamp;

}