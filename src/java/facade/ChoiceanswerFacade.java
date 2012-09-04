/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Choiceanswer;

/**
 *
 * @author Pedram
 */
@Stateless
public class ChoiceanswerFacade extends AbstractFacade<Choiceanswer> {
    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChoiceanswerFacade() {
        super(Choiceanswer.class);
    }
    
}
