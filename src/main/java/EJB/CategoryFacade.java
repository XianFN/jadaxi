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
import modelo.Recipe;

/**
 *
 * @author Javier
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> implements CategoryFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }

    @Override
    public Category findByName(String name) {

        List<Category> results = null;
        try {
            String hql = "FROM Category c WHERE c.name=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", name);

            results = query.getResultList();

        } catch (Exception e) {
            System.out.println("Fallo al obtener las categorias " + name + ": " + e.getMessage());
        }

        return results.get(0);
    }

}
