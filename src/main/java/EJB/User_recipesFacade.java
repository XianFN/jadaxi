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
import modelo.User_recipes;

/**
 *
 * @author Javier
 */
@Stateless
public class User_recipesFacade extends AbstractFacade<User_recipes> implements User_recipesFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User_recipesFacade() {
        super(User_recipes.class);
    }

    @Override
    public List<User_recipes> findByUserId(int id) {
        List<User_recipes> results = null;
        try {
            String hql = "FROM User_recipes c WHERE c.user_id=:param1";
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
    public boolean isJustStored(int idUSer, int idRecipe) {
        List<User_recipes> results = null;
        try {
            String hql = "FROM User_recipes c WHERE c.user_id=:param1 and c.recipe_id=:param2";
            Query query = em.createQuery(hql);
            query.setParameter("param1", idUSer);
            query.setParameter("param2", idRecipe);

            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las id de las recetas desde las categorias: " + e.getMessage());
        }

        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<User_recipes> findByRecipeId(int id) {
        List<User_recipes> results = null;
        try {
            String hql = "FROM User_recipes c WHERE c.recipe_id=:param1";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);

            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las relaciones usuario recetas: " + e.getMessage());
        }

        return results;

    }

    @Override
    public boolean isCreated(int idUSer, int idRecipe) {
        List<User_recipes> results = null;
        try {
            String hql = "FROM User_recipes c WHERE c.user_id=:param1 and c.recipe_id=:param2";
            Query query = em.createQuery(hql);
            query.setParameter("param1", idUSer);
            query.setParameter("param2", idRecipe);

            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener las id de las recetas desde las categorias: " + e.getMessage());
        }

        return results.get(0).isCreated();
    }

    public int getRecipeOwnerID(int recipeId) {

        List<User_recipes> results = null;
        try {
            String hql = "FROM User_recipes c WHERE c.recipe_id=:param1 and c.created=:param2";
            Query query = em.createQuery(hql);
            query.setParameter("param1", recipeId);
            query.setParameter("param2", true);

            results = query.getResultList();

            //System.out.println(results.get(0).toString());
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al obtener el credor de la receta: " + e.getMessage());
        }

        return results.get(0).getUser_id();

    }

}
