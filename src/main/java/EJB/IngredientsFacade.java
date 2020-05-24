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
import modelo.Category;
import modelo.Ingredients;
import modelo.Recipe;

/**
 *
 * @author Javier
 */
@Stateless
public class IngredientsFacade extends AbstractFacade<Ingredients> implements IngredientsFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngredientsFacade() {
        super(Ingredients.class);
    }

    @Override
    public Ingredients findByName(String name) {

        List<Ingredients> results = null;
        try {
            String hql = "FROM Ingredients c WHERE c.name=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", name);

            results = query.getResultList();

        } catch (Exception e) {
            System.out.println("Fallo al obtener los ingredientes " + name + ": " + e.getMessage());
        }

        return results.get(0);
    }

}
