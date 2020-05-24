/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Steps;

/**
 *
 * @author Javier
 */
@Stateless
public class StepsFacade extends AbstractFacade<Steps> implements StepsFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StepsFacade() {
        super(Steps.class);
    }
    
    @Override
    public List<Steps> findByRecipeId(int id){
        List<Steps> results = null;
        try {
            String hql = "FROM Steps r WHERE r.recipeId=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);
            
            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener los pasos de una categoria: " + e.getMessage());
        }
        
        return results;
        
    }
    
}
