
package controlador;

import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
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
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class ViewRecipeControler implements Serializable {

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private Recipes_ingredientsFacadeLocal rec_ingEJB;

    @EJB
    private IngredientsFacadeLocal ingredientsEJB;
     @EJB
    private StepsFacadeLocal stepsEJB;

    private int idRecipe;
    private Recipe recipe;

    private StreamedContent image;

    private List<otherIngredient> ingredientsList;
    private List<Steps> stepsList;

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

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public StreamedContent getImage() {

        Blob bl = null;

        try {
            bl = new SerialBlob(recipe.getImage());
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

    public List<otherIngredient> getIngredientsList() {
        ingredientsList = new ArrayList<>();
        List<Recipes_ingredients> listIds = rec_ingEJB.findAll();

        for (Recipes_ingredients ingre : listIds) {

            if (ingre.getRecipe() == recipe.getId()) {

                Ingredients ing = ingredientsEJB.find(ingre.getIngredients());

                ingredientsList.add(new otherIngredient(ing.getName(), ing.getCalories(), ingre.getAmmount()));

            }

        }
        return ingredientsList;
    }
     public List<Steps> getStepsList() {
        stepsList = new ArrayList<>();
        List<Steps> allSteps = stepsEJB.findAll();

        for (Steps step : allSteps) {

            if (step.getRecipeId()== recipe.getId()) {

                stepsList.add(step);

            }

        }
        return stepsList;
    }

    public class otherIngredient {

        private String name;
        private int calories;
        private int quantity;

        public otherIngredient(String name, int calories, int quantity) {
            this.name = name;
            this.calories = calories;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }
   
    
   

}
