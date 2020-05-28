/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.Category_recipeFacadeLocal;
import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
import EJB.UserFacadeLocal;
import EJB.User_recipesFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Category;
import modelo.Category_recipe;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import modelo.User;
import modelo.User_recipes;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Javier
 */

@Named
@ViewScoped
public class CreateRecipeControler implements Serializable{
    
    private Recipe recipe;
     
    private Recipes_ingredients recipeIngredients;
    
    private List<Recipes_ingredients> listRI;
    
    private User_recipes userRecipes;
    
    private Steps steps;
    
    private Ingredients ingredient;
    
    private List<Ingredients> ingredients;
    
    private List<Ingredients> selectedIngredients;
    
    private List<Category> categorys;
    
    private List<Category> category;
    
    private Category_recipe categoryrecipe;
    
    private List<Steps> stepsList;
    
    private UploadedFile file;
    
    private byte[] pixel;
    
    private double calorias;
    


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
    
    @EJB
    private CategoryFacadeLocal categoryEJB;
    
    @EJB
    private Category_recipeFacadeLocal categoryrecipeEJB;
    
    @EJB
    private UserFacadeLocal userEJB;

    
    @PostConstruct
    public void inicio() {
        System.out.println("CONST");
        try {
            sleep(500);
            recipe = new Recipe();
            recipeIngredients = new Recipes_ingredients();
            categoryrecipe = new Category_recipe();
            userRecipes = new User_recipes();
            ingredient = new Ingredients();
            steps = new Steps();
            
            listRI = new ArrayList<Recipes_ingredients>();
            
            stepsList = new ArrayList<Steps>();
            
            categorys=categoryEJB.findAll();
            ingredients = ingredientsEJB.findAll();
            

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
    
    public void setStepsList (List<Steps> stepsList) {
        this.stepsList = stepsList;
    }
    
    public List<Steps> getStepsList() {
        return stepsList;
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
    
    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys (List<Category> categorys) {
        this.categorys = categorys;
    }
    
    public List<Category> getCategory() {
        return category;
    }

    public void setCategory (List<Category> category) {
        this.category = category;
    }
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    public void setListRI (List<Recipes_ingredients> listRI) {
        this.listRI = listRI;
    }
    
    public List<Recipes_ingredients> getListRI() {
        return listRI;
    }
    
    
    
    
    
    public void insertRecipe() {
        int id=000;
        try {
            //recipe.setCategory(category.get(0).getId());
            recipe.setImage(file.getContents());
            //System.out.println(recipe.getImage().toString());
            for (int i=0; i<selectedIngredients.size(); i++){
                calorias=calorias + selectedIngredients.get(i).getCalories()*listRI.get(i).getAmmount()/100;
            }
            recipe.setCountCaloroies(calorias);
            recipeEJB.create(recipe);
            id = recipe.getId();
        } catch (Exception e) {
            System.out.println("Error al insertar la receta: " + e.getMessage());
       }
        
       try {
           User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
           
            user.setXp(user.getXp() + 80);
            user.setLv((int) (user.getXp() / 100));
            user.setRecipes(user.getRecipes()+1);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
             userEJB.edit(user);
        } catch (Exception e) {
            System.out.println("Error al añadir EXP: " + e.getMessage());
        }
        
        try {
             System.out.println(selectedIngredients.toString());
            recipeIngredients.setRecipe(id);
            for (int i=0; i<selectedIngredients.size(); i++){
                calorias=calorias + selectedIngredients.get(i).getCalories();
                recipeIngredients.setIngredients(selectedIngredients.get(i).getId());
                recipeIngredients.setAmmount(listRI.get(i).getAmmount());
                recipes_ingredientsEJB.create(recipeIngredients);
            }
            
        } catch (Exception e) {
            System.out.println("Error al agregar los ingredientes: " + e.getMessage());
        }
        
        try {
            categoryrecipe.setRecipe(id);
            for (int i=0; i<category.size(); i++){
                categoryrecipe.setCategory(category.get(i).getId());
                categoryrecipeEJB.create(categoryrecipe);
            }
            
        } catch (Exception e) {
            System.out.println("Error al agregar las categorias " + e.getMessage());
        }
        
        try {
            for (int i=0; i<category.size(); i++){
                category.get(i).setAmmount(category.get(i).getAmmount()+1);
                categoryEJB.edit(category.get(i));
            }
            
        } catch (Exception e) {
            System.out.println("Error al sumar las categorias " + e.getMessage());
        }

         try {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            userRecipes.setRecipe_id(id);
            userRecipes.setUser_id(user.getId());
            userRecipes.setCreated(true);
            user_recipesEJB.create(userRecipes);
        } catch (Exception e) {
            System.out.println("Error al asignar al usuario: " + e.getMessage());
        }
        
        try {
            steps.setRecipeId(id);
            System.out.println(stepsList.size());
            for (int i=0; i<stepsList.size(); i++){
                steps=stepsList.get(i);
                steps.setRecipeId(id);
                steps.setOrder(i+1);
                stepsEJB.create(steps);
            }
        } catch (Exception e) {
            System.out.println("Error al introducir los pasos: " + e.getMessage());
        }
        
        destroyWorld();
    }
    
     public void insertSteps() {
         Steps s = new Steps();
         s.setRecipeId(steps.getRecipeId());
         s.setTitle(steps.getTitle());
         s.setDescription(steps.getDescription());
        stepsList.add(s);
        steps.setTitle("");
        System.out.println(stepsList.toString());
        destroyWorld();
    }
     
      public void insertIngredient() {
        try {
            ingredientsEJB.create(ingredient);
        } catch (Exception e) {
            System.out.println("Error al crear ingrediente: " + e.getMessage());
        }
    }
      
    public void insertImage(){
        pixel=file.getContents();
    }
      
    public void destroyWorld() {
        addMessage("Añadido con éxito", "Su receta se ha actualizado correctamente.");
    }
    
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
        
    public void prepararTabla(){
        for (int i=0; i<selectedIngredients.size(); i++){
                listRI.add(new Recipes_ingredients());
                listRI.get(i).setIngredients(i);
            }
    }
    
   
    
     public void checkLevel() throws IOException{

         System.out.println("ENTRA QUI");
          
         
        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 2) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado por tener un nivel menor a 2");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }
}
