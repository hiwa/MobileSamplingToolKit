package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Choiceanswer;
import model.Question;
import model.Textanswer;
import model.UserXQuestionnaire;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(Questionnaire.class)
public class Questionnaire_ { 

    public static volatile CollectionAttribute<Questionnaire, Question> questionCollection;
    public static volatile CollectionAttribute<Questionnaire, Textanswer> textanswerCollection;
    public static volatile SingularAttribute<Questionnaire, Date> createdate;
    public static volatile CollectionAttribute<Questionnaire, UserXQuestionnaire> userXQuestionnaireCollection;
    public static volatile SingularAttribute<Questionnaire, Short> frequency;
    public static volatile SingularAttribute<Questionnaire, Date> publishdate;
    public static volatile SingularAttribute<Questionnaire, String> type;
    public static volatile CollectionAttribute<Questionnaire, Choiceanswer> choiceanswerCollection;
    public static volatile SingularAttribute<Questionnaire, Integer> id;
    public static volatile SingularAttribute<Questionnaire, Integer> numberofquestions;
    public static volatile SingularAttribute<Questionnaire, String> name;
    public static volatile SingularAttribute<Questionnaire, String> context;
    public static volatile SingularAttribute<Questionnaire, Date> deadline;

}