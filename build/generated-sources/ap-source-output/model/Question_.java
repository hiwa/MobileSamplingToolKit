package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Choiceanswer;
import model.Questionnaire;
import model.Textanswer;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(Question.class)
public class Question_ { 

    public static volatile CollectionAttribute<Question, Choiceanswer> choiceanswerCollection;
    public static volatile SingularAttribute<Question, Questionnaire> questionnaireid;
    public static volatile SingularAttribute<Question, Integer> id;
    public static volatile SingularAttribute<Question, Integer> weight;
    public static volatile SingularAttribute<Question, String> tag;
    public static volatile SingularAttribute<Question, String> correctanswer;
    public static volatile CollectionAttribute<Question, Textanswer> textanswerCollection;
    public static volatile SingularAttribute<Question, Boolean> mandatory;
    public static volatile SingularAttribute<Question, Integer> ordernumber;
    public static volatile SingularAttribute<Question, String> type;
    public static volatile SingularAttribute<Question, String> answertext;
    public static volatile SingularAttribute<Question, String> questiontext;

}