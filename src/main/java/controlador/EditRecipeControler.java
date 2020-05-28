/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
import EJB.UserFacadeLocal;
import EJB.User_recipesFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 */
@Named
@RequestScoped
public class EditRecipeControler implements Serializable {

    @EJB
    private Recipes_ingredientsFacadeLocal rec_ingEJB;

    @EJB
    private IngredientsFacadeLocal ingredientsEJB;

    @EJB
    private RecipeFacadeLocal recipeEJB;

    private Recipe recipe;

    private int idRecipe;
    
    private otherIngredient2 ingredient;

    private List<otherIngredient2> ingredientsList;

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

        } catch (Exception e) {

            System.out.println("La gente es 0 asique atora: " + e.getMessage());
        }

    }

    public List<otherIngredient2> getIngredientsList() {
        ingredientsList = new ArrayList<>();
        List<Recipes_ingredients> listIds = rec_ingEJB.findAll();

        for (Recipes_ingredients ingre : listIds) {

            if (ingre.getRecipe() == recipe.getId()) {

                Ingredients ing = ingredientsEJB.find(ingre.getIngredients());

                ingredientsList.add(new otherIngredient2(ing.getName(), ing.getCalories(), ingre.getAmmount()));

            }

        }
        return ingredientsList;
    }

    public Recipes_ingredientsFacadeLocal getRec_ingEJB() {
        return rec_ingEJB;
    }

    public void setRec_ingEJB(Recipes_ingredientsFacadeLocal rec_ingEJB) {
        this.rec_ingEJB = rec_ingEJB;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public otherIngredient2 getIngredient() {
        return ingredient;
    }

    public void setIngredient(otherIngredient2 ingredient) {
        this.ingredient = ingredient;
    }
    
    

    public class otherIngredient2 {

        private String name;
        private int calories;
        private int quantity;

        public otherIngredient2(String name, int calories, int quantity) {
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
