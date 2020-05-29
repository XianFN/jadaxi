/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Commentary;

/**
 *
 * @author Javier
 */
@Local
public interface CommentaryFacadeLocal {

    void create(Commentary commentary);

    void edit(Commentary commentary);

    void remove(Commentary commentary);

    Commentary find(Object id);

    List<Commentary> findAll();

    List<Commentary> findRange(int[] range);

    int count();
    
    List<Commentary> findByRecipe(int id);
    
    Commentary findRelation(int idRecipe, int idUser);
    
}
