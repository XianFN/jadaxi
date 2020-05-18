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
import EJB.User_recipesFacadeLocal;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import modelo.User;
import modelo.User_recipes;

/**
 *
 * @author Javier
 */

@Named
@ViewScoped
public class CreateRecipeControler implements Serializable{
    
    private Recipe recipe;
     
    private Recipes_ingredients recipeIngredients;
    
    private User_recipes userRecipes;
    
    private Steps steps;
    
    private Ingredients ingredient;
    
    private List<Ingredients> ingredients;
    
    private List<Ingredients> selectedIngredients;
    

    @EJB
    private RecipeFacadeLocal recipeEJB;
    
    @EJB
    private Recipes_ingredientsFacadeLocal recipes_ingredientsEJB;
    
    @EJB
    private User_recipesFacadeLocal user_recipesEJB;
    
    @EJB
    private StepsFacadeLocal stepsEJB;
    
    @EJB
    private IngredientsFacadeLocal ingredientsEJB;

    
    @PostConstruct
    public void inicio() {
        try {
            sleep(500);
            recipe = new Recipe();
            recipeIngredients = new Recipes_ingredients();
            userRecipes = new User_recipes();
            ingredient = new Ingredients();
            steps = new Steps();
            ingredients = ingredientsEJB.findAll();
            System.out.println(ingredients.toString());
            
            steps.setRecipeId(15);

        } catch (Exception e) {
            System.out.println("Fallo al crear el CreateRecipeControler: " + e.getMessage());
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public Steps getSteps() {
        return steps;
    }

    public void setSteps(Steps steps) {
        this.steps = steps;
    }
    
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients (List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
    
     public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient (Ingredients ingredient) {
        this.ingredient = ingredient;
    }
    
    public List<Ingredients> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients (List<Ingredients> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }
    
    
    public void insertRecipe() {
        int id=000;
        try {
            recipeEJB.create(recipe);
            id = recipe.getId();
        } catch (Exception e) {
            System.out.println("Error al insertar la receta: " + e.getMessage());
        }
        
        try {
            recipeIngredients.setRecipe(id);
            for (int i=0; i<selectedIngredients.size(); i++){
                recipeIngredients.setIngredients(selectedIngredients.get(i).getId());
                recipes_ingredientsEJB.create(recipeIngredients);
            }
            
        } catch (Exception e) {
            System.out.println("Error al agregar los ingredientes: " + e.getMessage());
        }
        
         try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            userRecipes.setRecipe_id(id);
            userRecipes.setUser_id(user.getId());
            user_recipesEJB.create(userRecipes);
        } catch (Exception e) {
            System.out.println("Error al asignar al usuario: " + e.getMessage());
        }
    }
    
     public void insertSteps() {
        try {
            steps.setRecipeId(recipe.getId());
            stepsEJB.create(steps);
        } catch (Exception e) {
            System.out.println("Error al introducir los pasos: " + e.getMessage());
        }
    }
     
      public void insertIngredient() {
        try {
            ingredientsEJB.create(ingredient);
        } catch (Exception e) {
            System.out.println("Error al crear ingrediente: " + e.getMessage());
        }
    }
      
    
}
