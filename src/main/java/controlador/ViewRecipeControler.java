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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import org.primefaces.event.RateEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 *
 */
@Named

@RequestScoped
public class ViewRecipeControler implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer rating;

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

        try {
            int ratings = recipe.getTotalRating();
            int peopleRated = recipe.getPeopleRating();

            rating = ratings / peopleRated;

        } catch (Exception e) {

            System.out.println("La gente es 0 asique atora: " + e.getMessage());
        }

    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

            if (step.getRecipeId() == recipe.getId()) {

                stepsList.add(step);

            }

        }
        return stepsList;
    }

    /**
     *
     * @param rateEvent
     */
    public void onrate(RateEvent rateEvent) {
        System.out.println("ljkahsdoñihasoñlidhaoiñsld");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rateEvent.getRating());
        FacesContext.getCurrentInstance().addMessage(null, message);

        int ratings = recipe.getTotalRating();
        int peopleRated = recipe.getPeopleRating();
        ratings = ratings + (int) rateEvent.getRating();
        peopleRated++;
        recipe.setTotalRating(ratings);
        recipe.setPeopleRating(peopleRated);
        recipeEJB.edit(recipe);
    }

    public void oncancel() {
        System.out.println("ljkahsdoñihasoñlidhaoiñsld");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
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
