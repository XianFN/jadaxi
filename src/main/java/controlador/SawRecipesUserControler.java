/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.Category_recipeFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.User_recipesFacadeLocal;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;
import modelo.Category_recipe;
import modelo.Recipe;
import modelo.User;
import modelo.User_recipes;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class SawRecipesUserControler  implements Serializable{
    private User user;

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private User_recipesFacadeLocal usr_recEJB;

    private List<Recipe> recipes;

    private Recipe selectedRecipe;

    @PostConstruct
    public void inicio() {
        recipes = new ArrayList<>();
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        System.out.println("usuario: " + user.getName());
        
        
        
        

        List<User_recipes> idRecipesList = usr_recEJB.findByUserId(user.getId());

       
            for (User_recipes idrecipe : idRecipesList) {
                
             recipes.add(recipeEJB.find(idrecipe.getRecipe_id()));
                System.out.println("se ha añadido la receta " + idrecipe.getRecipe_id());
            }

        

        System.out.println(recipes);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public void setSelectedRecipe(Recipe selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    /*
     public void clearMultiViewState() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        PrimeFaces.current().multiViewState().clearAll(viewId, true, (clientId) -> {
            showMessage(clientId);
        });
    }
     */
    private void showMessage(String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }

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
        System.out.println("jsahbdfñoiuhasñoiudfhñpoiaushdfñiouahdfñ.i");
        return dbImage;
    }

    public String getRecipeCategoryName(int id) {

        return "";
    }

    public String viewRecipe(int id) {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recipe", id);

        //TODO ?faces-redirect=true
        return "viewRecipe.xhtml?faces-redirect=true";

    }
}
