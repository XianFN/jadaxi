package controlador;

import EJB.Category_recipeFacadeLocal;
import EJB.RecipeFacadeLocal;
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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jadaxi
 */
@Named
@ViewScoped
public class ViewRecipesListControler implements Serializable {

    private int categoryId;

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private Category_recipeFacadeLocal cat_recEJB;

    private List<Recipe> recipes;

    private Recipe selectedRecipe;

    @PostConstruct
    public void inicio() {
        recipes = new ArrayList<>();
        categoryId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("categoryToShow");

   
        List<Category_recipe> idRecipe = cat_recEJB.findByCategoryId(categoryId);

        for (int i = 0; i < idRecipe.size(); i++) {

            recipes.add(recipeEJB.find(idRecipe.get(i).getRecipe()));

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

    /**
     * 
     * @param id
     * @return 
     * 
     * Imprimimos las imagenes
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
        System.out.println("jsahbdfñoiuhasñoiudfhñpoiaushdfñiouahdfñ.i");
        return dbImage;
    }

    public String getRecipeCategoryName(int id) {

        return "";
    }

    public String viewRecipe(int id) {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recipe", id);

        
        return "viewRecipe.xhtml?faces-redirect=true";

    }

}
