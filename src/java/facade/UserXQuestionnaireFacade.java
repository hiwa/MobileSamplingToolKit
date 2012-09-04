/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.UserXQuestionnaire;

/**
 *
 * @author Pedram
 */
@Stateless
public class UserXQuestionnaireFacade extends AbstractFacade<UserXQuestionnaire> {
    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserXQuestionnaireFacade() {
        super(UserXQuestionnaire.class);
    }
    
}
