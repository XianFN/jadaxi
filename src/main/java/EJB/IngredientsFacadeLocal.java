/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Ingredients;

/**
 *
 * @author Javier
 */
@Local
public interface IngredientsFacadeLocal {

    void create(Ingredients ingredients);

    void edit(Ingredients ingredients);

    void remove(Ingredients ingredients);

    Ingredients find(Object id);

    List<Ingredients> findAll();

    List<Ingredients> findRange(int[] range);

    int count();

}
