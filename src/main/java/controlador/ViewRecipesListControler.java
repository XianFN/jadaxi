/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.RecipeFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Recipe;

/**
 *
 * @author jadaxi
 */

@Named
@ViewScoped
public class ViewRecipesListControler implements Serializable  {
    
    private String category;
    
     @EJB
    private RecipeFacadeLocal recipeEJB;

    private List<Recipe> recipes;

    private List<String> images;
    
    @PostConstruct
    public void inicio() {
        
        category = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryToShow");
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryToShow") == null){
            System.out.println("NULL");
        }
        System.out.println("category: " + category);
        recipes = recipeEJB.findByName(category);
        
        System.out.println(recipes);
    }
    
    
    
}
