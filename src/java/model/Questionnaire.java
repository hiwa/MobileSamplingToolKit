/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "questionnaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionnaire.findAll", query = "SELECT q FROM Questionnaire q"),
    @NamedQuery(name = "Questionnaire.findById", query = "SELECT q FROM Questionnaire q WHERE q.id = :id"),
    @NamedQuery(name = "Questionnaire.findByNumberofquestions", query = "SELECT q FROM Questionnaire q WHERE q.numberofquestions = :numberofquestions"),
    @NamedQuery(name = "Questionnaire.findByCreatedate", query = "SELECT q FROM Questionnaire q WHERE q.createdate = :createdate"),
    @NamedQuery(name = "Questionnaire.findByPublishdate", query = "SELECT q FROM Questionnaire q WHERE q.publishdate = :publishdate"),
    @NamedQuery(name = "Questionnaire.findByDeadline", query = "SELECT q FROM Questionnaire q WHERE q.deadline = :deadline"),
    @NamedQuery(name = "Questionnaire.findByType", query = "SELECT q FROM Questionnaire q WHERE q.type = :type"),
    @NamedQuery(name = "Questionnaire.findByFrequency", query = "SELECT q FROM Questionnaire q WHERE q.frequency = :frequency"),
    @NamedQuery(name = "Questionnaire.findByContext", query = "SELECT q FROM Questionnaire q WHERE q.context = :context")})
public class Questionnaire implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "publishdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "numberofquestions")
    private Integer numberofquestions;
    @Size(max = 30)
    @Column(name = "type")
    private String type;
    @Column(name = "frequency")
    private Short frequency;
    @Size(max = 200)
    @Column(name = "context")
    private String context;
    @OneToMany(mappedBy = "questionnaireid")
    private Collection<Textanswer> textanswerCollection;
    @OneToMany(mappedBy = "questionnaireid")
    private Collection<Question> questionCollection;
    @OneToMany(mappedBy = "questionnaireid")
    private Collection<UserXQuestionnaire> userXQuestionnaireCollection;
    @OneToMany(mappedBy = "questionnaireid")
    private Collection<Choiceanswer> choiceanswerCollection;

    public Questionnaire() {
    }

    public Questionnaire(Integer id) {
        this.id = id;
    }

    public Questionnaire(Integer id, Date createdate, Date publishdate, Date deadline) {
        this.id = id;
        this.createdate = createdate;
        this.publishdate = publishdate;
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberofquestions() {
        return numberofquestions;
    }

    public void setNumberofquestions(Integer numberofquestions) {
        this.numberofquestions = numberofquestions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getFrequency() {
        return frequency;
    }

    public void setFrequency(Short frequency) {
        this.frequency = frequency;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @XmlTransient
    public Collection<Textanswer> getTextanswerCollection() {
        return textanswerCollection;
    }

    public void setTextanswerCollection(Collection<Textanswer> textanswerCollection) {
        this.textanswerCollection = textanswerCollection;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @XmlTransient
    public Collection<UserXQuestionnaire> getUserXQuestionnaireCollection() {
        return userXQuestionnaireCollection;
    }

    public void setUserXQuestionnaireCollection(Collection<UserXQuestionnaire> userXQuestionnaireCollection) {
        this.userXQuestionnaireCollection = userXQuestionnaireCollection;
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
        if (!(object instanceof Questionnaire)) {
            return false;
        }
        Questionnaire other = (Questionnaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Questionnaire[ id=" + id + " ]";
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
