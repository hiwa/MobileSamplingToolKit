/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedram
 */
@Entity
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByType", query = "SELECT q FROM Question q WHERE q.type = :type"),
    @NamedQuery(name = "Question.findByMandatory", query = "SELECT q FROM Question q WHERE q.mandatory = :mandatory"),
    @NamedQuery(name = "Question.findByOrdernumber", query = "SELECT q FROM Question q WHERE q.ordernumber = :ordernumber"),
    @NamedQuery(name = "Question.findByWeight", query = "SELECT q FROM Question q WHERE q.weight = :weight"),
    @NamedQuery(name = "Question.findByTag", query = "SELECT q FROM Question q WHERE q.tag = :tag")})
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 40)
    @Column(name = "type")
    private String type;
    @Lob
    @Size(max = 65535)
    @Column(name = "questiontext")
    private String questiontext;
    @Lob
    @Size(max = 65535)
    @Column(name = "answertext")
    private String answertext;
    @Column(name = "mandatory")
    private Boolean mandatory;
    @Lob
    @Size(max = 65535)
    @Column(name = "correctanswer")
    private String correctanswer;
    @Column(name = "ordernumber")
    private Integer ordernumber;
    @Column(name = "weight")
    private Integer weight;
    @Size(max = 40)
    @Column(name = "tag")
    private String tag;
    @OneToMany(mappedBy = "questionid")
    private Collection<Textanswer> textanswerCollection;
    @JoinColumn(name = "questionnaireid", referencedColumnName = "id")
    @ManyToOne
    private Questionnaire questionnaireid;
    @OneToMany(mappedBy = "questionid")
    private Collection<Choiceanswer> choiceanswerCollection;

    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @XmlTransient
    public Collection<Textanswer> getTextanswerCollection() {
        return textanswerCollection;
    }

    public void setTextanswerCollection(Collection<Textanswer> textanswerCollection) {
        this.textanswerCollection = textanswerCollection;
    }

    public Questionnaire getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Questionnaire questionnaireid) {
        this.questionnaireid = questionnaireid;
    }

    @XmlTransient
    public Collection<Choiceanswer> getChoiceanswerCollection() {
        return choiceanswerCollection;
    }

    public void setChoiceanswerCollection(Collection<Choiceanswer> choiceanswerCollection) {
        this.choiceanswerCollection = choiceanswerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Question[ id=" + id + " ]";
    }
    
}
