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
@Table(name = "user_x_organization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserXOrganization.findAll", query = "SELECT u FROM UserXOrganization u"),
    @NamedQuery(name = "UserXOrganization.findById", query = "SELECT u FROM UserXOrganization u WHERE u.id = :id")})
public class UserXOrganization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "organizationid", referencedColumnName = "id")
    @ManyToOne
    private Organization organizationid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private User userid;

    public UserXOrganization() {
    }

    public UserXOrganization(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Organization getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(Organization organizationid) {
        this.organizationid = organizationid;
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
        if (!(object instanceof UserXOrganization)) {
            return false;
        }
        UserXOrganization other = (UserXOrganization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserXOrganization[ id=" + id + " ]";
    }
    
}
