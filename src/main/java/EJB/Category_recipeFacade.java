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
import modelo.User;

/**
 *
 * @author Javier
 */
@Stateless
public class Category_recipeFacade extends AbstractFacade<Category_recipe> implements Category_recipeFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Category_recipeFacade() {
        super(Category_recipe.class);
    }
    
    @Override
    public List<Category_recipe> findByCategoryId(int id){
        List<Category_recipe> results = null;
        try {
            String hql = "FROM Category_recipe c WHERE c.category=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);
            
            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las id de las recetas desde las categorias: " + e.getMessage());
        }
        
        return results;
        
    }
    
    @Override
    public List<Category_recipe> findByRecipeId(int id){
        List<Category_recipe> results = null;
        try {
            String hql = "FROM Category_recipe c WHERE c.recipe=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);
            
            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las id de las recetas desde las categorias: " + e.getMessage());
        }
        
        return results;
        
    }
    
}
