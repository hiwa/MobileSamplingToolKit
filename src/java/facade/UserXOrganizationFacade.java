/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.UserXOrganization;

/**
 *
 * @author Pedram
 */
@Stateless
public class UserXOrganizationFacade extends AbstractFacade<UserXOrganization> {
    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserXOrganizationFacade() {
        super(UserXOrganization.class);
    }
    
}
