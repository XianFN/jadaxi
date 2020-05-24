/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Recipes_ingredients;

/**
 *
 * @author Javier
 */
@Local
public interface Recipes_ingredientsFacadeLocal {

    void create(Recipes_ingredients recipes_ingredients);

    void edit(Recipes_ingredients recipes_ingredients);

    void remove(Recipes_ingredients recipes_ingredients);

    Recipes_ingredients find(Object id);

    List<Recipes_ingredients> findAll();

    List<Recipes_ingredients> findRange(int[] range);

    int count();
    
    List<Recipes_ingredients> findByIngredientId(int id);
    
}
