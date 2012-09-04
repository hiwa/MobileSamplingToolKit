/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Organization;

/**
 *
 * @author Pedram
 */
@Stateless
public class OrganizationFacade extends AbstractFacade<Organization> {

    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    private EntityManager em;
    List<Organization> results = null;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizationFacade() {
        super(Organization.class);
    }

//    public List<Organization> OrganizationList() {
//        System.out.println("ENETRED SANDMAMAAMMAMA.");
//        try {
//            em = Persistence.createEntityManagerFactory("MobileSamplingToolkit0.4PU").createEntityManager();
//            Query query = em.createNamedQuery("Organization.findAll");
//            results = query.getResultList();
//            
//        } catch (Exception e) {
//            System.out.println("ERROR IN FACADE:"+e.getMessage());
//        }
//        return results;
//    }
}
