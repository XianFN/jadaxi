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
import modelo.Commentary;
import modelo.User;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 *
 * @author Javier
 */
@Stateless
public class CommentaryFacade extends AbstractFacade<Commentary> implements CommentaryFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_inso3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentaryFacade() {
        super(Commentary.class);
    }

    @Override
    public List<Commentary> findByRecipe(int id) {
       
       
        List<Commentary> results = null;
        
        try {
            String hql = "FROM Commentary c WHERE c.recipeId=:param1 ";
            Query query = em.createQuery(hql);
            query.setParameter("param1", id);
           

            results = query.getResultList();

            
            
        } catch (Exception e) {
            System.out.println("Algo ha salido mal al taer los comentarios de la receta: " + id + "   ->  " + e.getMessage());
        }
        return results;
        
        
        
    }
    
    
}
