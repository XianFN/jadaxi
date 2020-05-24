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
import modelo.Category_recipe;
import modelo.Recipes_ingredients;

/**
 *
 * @author Javier
 */
@Stateless
public class Recipes_ingredientsFacade extends AbstractFacade<Recipes_ingredients> implements Recipes_ingredientsFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Recipes_ingredientsFacade() {
        super(Recipes_ingredients.class);
    }
    
    
    @Override
    public List<Recipes_ingredients> findByIngredientId(int id){
        List<Recipes_ingredients> results = null;
        try {
            String hql = "FROM Recipes_ingredients c WHERE c.ingredients=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);
            
            results = query.getResultList();

            System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las id de las recetas desde los ingredientes: " + e.getMessage());
        }
        
        return results;
        
    }
    
}
