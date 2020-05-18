/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Category;
import modelo.Recipe;
import modelo.User;

/**
 *
 * @author Javier
 */
@Stateless
public class RecipeFacade extends AbstractFacade<Recipe> implements RecipeFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @EJB
    private CategoryFacadeLocal categoryEJB;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecipeFacade() {
        super(Recipe.class);
    }

    @Override
    public List<Recipe> findByName(String name) {

        Category cat = categoryEJB.findByName(name);

        System.out.println("cat:" + cat.toString());

        List<Recipe> results = null;
        //TODO PREGUNTAR dalla por ls FK ????
        try {
            String hql = "FROM Recipes r WHERE r.category=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", cat.getId());

            results = query.getResultList();

        } catch (Exception e) {
            System.out.println("Fallo al obtener las recetas de la categoria " + name + ": " + e.getMessage());
        }
        System.out.println(results);
        return results;
    }

}
