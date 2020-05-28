/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.User_recipes;

/**
 *
 * @author Javier
 */
@Local
public interface User_recipesFacadeLocal {

    void create(User_recipes user_recipes);

    void edit(User_recipes user_recipes);

    void remove(User_recipes user_recipes);

    User_recipes find(Object id);

    List<User_recipes> findAll();

    List<User_recipes> findRange(int[] range);

    int count();

    List<User_recipes> findByUserId(int id);

    boolean isJustStored(int idUSer, int idRecipe);

    List<User_recipes> findByRecipeId(int id);
    
    boolean isCreated(int idUSer, int idRecipe);
    
    int getRecipeOwnerID(int recipeId);

}
