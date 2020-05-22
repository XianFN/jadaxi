/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import EJB.RecipeFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Recipe;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class ViewRecipeControler implements Serializable {

    @EJB
    private RecipeFacadeLocal recipeEJB;

    private int idRecipe;
    private Recipe recipe;

    @PostConstruct
    public void inicio() {
        recipe = null;
        idRecipe = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("recipe");
        System.out.println("ID: " + idRecipe);
        try {
            recipe = recipeEJB.find(idRecipe);
        } catch (Exception e) {
            System.out.println("Error al traer la receta con el id: " + idRecipe + ".:" + e.getMessage());
        }
        
        
        System.out.println("RECIPE: " + recipe.toString());

    }

}
