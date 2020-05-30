/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;

import modelo.Recipe;
import modelo.Recipes_ingredients;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class ViewRecipeListIngredientsControler implements Serializable {

    private int ingredientId;

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private Recipes_ingredientsFacadeLocal red_ingEJB;

    private List<Recipe> recipes;

    private Recipe selectedRecipe;

    @PostConstruct
    public void inicio() {
        recipes = new ArrayList<>();
        ingredientId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ingredientToShow");

        System.out.println("ID:" + ingredientId);

        List<Recipes_ingredients> idRecipe = red_ingEJB.findByIngredientId(ingredientId);

        System.out.println("SIZE: " + idRecipe.size());
        for (int i = 0; i < idRecipe.size(); i++) {

            recipes.add(recipeEJB.find(idRecipe.get(i).getRecipe()));

        }

        System.out.println(recipes);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    
    /**
     * 
     * @param id
     * @return 
     * 
     * Imprimimos las imagenes de las recetas
     */
    public StreamedContent getImage(int id) {
        System.out.println("id: " + id);

        Blob bl = null;
        int num = 0;
        for (int i = 0; i < recipes.size(); i++) {

            if (recipes.get(i).getId() == id) {
                num = i;

            }
        }

        try {
            bl = new SerialBlob(recipes.get(num).getImage());
        } catch (SQLException ex) {
            Logger.getLogger(ViewRecipesListControler.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputStream dbStream = null;
        try {
            dbStream = bl.getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(ViewRecipesListControler.class.getName()).log(Level.SEVERE, null, ex);
        }

        StreamedContent dbImage = new DefaultStreamedContent(dbStream, "image/jpeg", "nombre.jpeg");
       
        return dbImage;
    }
    
    /**
     * 
     * @param id
     * @return 
     * 
     * Redireccionamos a ver la receta seleccionada
     */
    public String viewRecipe(int id) {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recipe", id);


        return "viewRecipe.xhtml?faces-redirect=true";

    }

}
