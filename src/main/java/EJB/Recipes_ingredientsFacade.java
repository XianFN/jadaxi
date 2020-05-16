/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
