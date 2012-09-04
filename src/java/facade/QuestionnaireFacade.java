/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Questionnaire;

/**
 *
 * @author Pedram
 */
@Stateless
public class QuestionnaireFacade extends AbstractFacade<Questionnaire> {
    @PersistenceContext(unitName = "MobileSamplingToolkit0.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionnaireFacade() {
        super(Questionnaire.class);
    }
    
}
