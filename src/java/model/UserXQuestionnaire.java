/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedram
 */
@Entity
@Table(name = "user_x_questionnaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserXQuestionnaire.findAll", query = "SELECT u FROM UserXQuestionnaire u"),
    @NamedQuery(name = "UserXQuestionnaire.findById", query = "SELECT u FROM UserXQuestionnaire u WHERE u.id = :id"),
    @NamedQuery(name = "UserXQuestionnaire.findByCompletedstate", query = "SELECT u FROM UserXQuestionnaire u WHERE u.completedstate = :completedstate")})
public class UserXQuestionnaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "completedstate")
    private Short completedstate;
    @JoinColumn(name = "questionnaireid", referencedColumnName = "id")
    @ManyToOne
    private Questionnaire questionnaireid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private User userid;

    public UserXQuestionnaire() {
    }

    public UserXQuestionnaire(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getCompletedstate() {
        return completedstate;
    }

    public void setCompletedstate(Short completedstate) {
        this.completedstate = completedstate;
    }

    public Questionnaire getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Questionnaire questionnaireid) {
        this.questionnaireid = questionnaireid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
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
        if (!(object instanceof UserXQuestionnaire)) {
            return false;
        }
        UserXQuestionnaire other = (UserXQuestionnaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserXQuestionnaire[ id=" + id + " ]";
    }
    
}
