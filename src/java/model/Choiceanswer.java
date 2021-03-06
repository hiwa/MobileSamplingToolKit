/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedram
 */
@Entity
@Table(name = "choiceanswer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choiceanswer.findAll", query = "SELECT c FROM Choiceanswer c"),
    @NamedQuery(name = "Choiceanswer.findById", query = "SELECT c FROM Choiceanswer c WHERE c.id = :id"),
    @NamedQuery(name = "Choiceanswer.findByProvidedanswer", query = "SELECT c FROM Choiceanswer c WHERE c.providedanswer = :providedanswer"),
    @NamedQuery(name = "Choiceanswer.findByAnswertimestamp", query = "SELECT c FROM Choiceanswer c WHERE c.answertimestamp = :answertimestamp")})
public class Choiceanswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "providedanswer")
    private String providedanswer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "answertimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date answertimestamp;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private User userid;
    @JoinColumn(name = "questionid", referencedColumnName = "id")
    @ManyToOne
    private Question questionid;
    @JoinColumn(name = "questionnaireid", referencedColumnName = "id")
    @ManyToOne
    private Questionnaire questionnaireid;

    public Choiceanswer() {
    }

    public Choiceanswer(Integer id) {
        this.id = id;
    }

    public Choiceanswer(Integer id, Date answertimestamp) {
        this.id = id;
        this.answertimestamp = answertimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvidedanswer() {
        return providedanswer;
    }

    public void setProvidedanswer(String providedanswer) {
        this.providedanswer = providedanswer;
    }

    public Date getAnswertimestamp() {
        return answertimestamp;
    }

    public void setAnswertimestamp(Date answertimestamp) {
        this.answertimestamp = answertimestamp;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public Question getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Question questionid) {
        this.questionid = questionid;
    }

    public Questionnaire getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Questionnaire questionnaireid) {
        this.questionnaireid = questionnaireid;
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
        if (!(object instanceof Choiceanswer)) {
            return false;
        }
        Choiceanswer other = (Choiceanswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Choiceanswer[ id=" + id + " ]";
    }
    
}
