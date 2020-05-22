/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Category_recipe;

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
    
}
