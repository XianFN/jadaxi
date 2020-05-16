/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
