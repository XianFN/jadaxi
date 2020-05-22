/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Category_recipe;

/**
 *
 * @author Javier
 */
@Local
public interface Category_recipeFacadeLocal {

    void create(Category_recipe category_recipe);

    void edit(Category_recipe category_recipe);

    void remove(Category_recipe category_recipe);

    Category_recipe find(Object id);

    List<Category_recipe> findAll();

    List<Category_recipe> findRange(int[] range);

    int count();
    
}
