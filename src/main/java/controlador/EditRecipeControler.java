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
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class EditRecipeControler implements Serializable {
    
    @EJB
    private Recipes_ingredientsFacadeLocal rec_ingEJB;
    
    @EJB
    private IngredientsFacadeLocal ingredientsEJB;
    
    @EJB
    private RecipeFacadeLocal recipeEJB;
    
    @EJB
    private StepsFacadeLocal stepsEJB;
    
    private Recipe recipe;
    
    private int idRecipe;
    
    private otherIngredient2 ingredient;
    
    private List<otherIngredient2> ingredientsList;
    
    private List<Ingredients> selectedIngredients;
    
    private List<Ingredients> ingredients;
    
    private List<Recipes_ingredients> listRI;
    
    private Recipes_ingredients recipeIngredients;
    
    private List<Steps> stepsList;
    private List<Steps> stepsList2;
    
    private Steps step;
    
    private Steps steptoCreate;
    
    private List<Steps> stepsToAdd;
    
    private StreamedContent image;
    
    private UploadedFile file;
    
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
        
        stepsList = stepsEJB.findByRecipeId(recipe.getId());
        ingredients = ingredientsEJB.findAll();
        listRI = new ArrayList<>();
        recipeIngredients = new Recipes_ingredients();
        stepsList2 = stepsList;
        
        steptoCreate = new Steps();
        
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
    
    public List<Ingredients> getSelectedIngredients() {
        return selectedIngredients;
    }
    
    public void setSelectedIngredients(List<Ingredients> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }
    
    public List<Ingredients> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
    
    public List<Recipes_ingredients> getListRI() {
        return listRI;
    }
    
    public void setListRI(List<Recipes_ingredients> listRI) {
        this.listRI = listRI;
    }
    
    public List<Steps> getStepsList() {
        
        return stepsList;
    }
    
    public void setStepsList(List<Steps> stepsList) {
        
        this.stepsList = stepsList;
    }
    
    public Steps getStep() {
        return step;
    }
    
    public void setStep(Steps step) {
        this.step = step;
    }
    
    public List<Steps> getStepsList2() {
        return stepsList2;
    }
    
    public void setStepsList2(List<Steps> stepsList2) {
        this.stepsList2 = stepsList2;
    }
    
    public Steps getSteptoCreate() {
        return steptoCreate;
    }
    
    public void setSteptoCreate(Steps steptoCreate) {
        this.steptoCreate = steptoCreate;
    }
    
    public List<Steps> getStepsToAdd() {
        return stepsToAdd;
    }
    
    public void setStepsToAdd(List<Steps> stepsToAdd) {
        this.stepsToAdd = stepsToAdd;
    }
    
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
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
    
    public void setImage(StreamedContent image) {
        this.image = image;
    }
    
    public void deleteIngredient(otherIngredient2 ing) {
        
        System.out.println("asd: " + ing.getId());
        
        try {
            rec_ingEJB.remove(rec_ingEJB.find(ing.getId()));
        } catch (Exception e) {
            System.out.println("2");
        }

        //TODO restar calorias
        double caloriasRstar = ing.calories * ing.quantity / 100;
        
        recipe.setCountCaloroies(recipe.getCountCaloroies() - caloriasRstar);
        
        recipeEJB.edit(recipe);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Ingrediente borrado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public List<otherIngredient2> getIngredientsList() {
        ingredientsList = new ArrayList<>();
        List<Recipes_ingredients> listIds = rec_ingEJB.findAll();
        
        for (Recipes_ingredients ingre : listIds) {
            
            if (ingre.getRecipe() == recipe.getId()) {
                
                Ingredients ing = ingredientsEJB.find(ingre.getIngredients());
                
                ingredientsList.add(new otherIngredient2(ing.getName(), ing.getCalories(), ingre.getAmmount(), ingre.getId()));
                
            }
            
        }
        return ingredientsList;
    }
    
    public void prepararTabla() {
        System.out.println("Preparamos la tabla");
        for (int i = 0; i < selectedIngredients.size(); i++) {
            listRI.add(new Recipes_ingredients());
            listRI.get(i).setIngredients(i);
            System.out.println("i: " + i);
        }
    }
    
    public void insertNewIngredients() {
        
        double calorias = 0;
        System.out.println("1");
        System.out.println("S:" + selectedIngredients);
        System.out.println("S2: " + listRI);
        recipeIngredients.setRecipe(recipe.getId());
        System.out.println("1");
        for (int i = 0; i < selectedIngredients.size(); i++) {
            System.out.println("I: " + i);
            calorias = calorias + selectedIngredients.get(i).getCalories() * listRI.get(i).getAmmount() / 100;
            recipeIngredients.setIngredients(selectedIngredients.get(i).getId());
            recipeIngredients.setAmmount(listRI.get(i).getAmmount());
            System.out.println("POPOPO: " + listRI.get(i).getAmmount());
            System.out.println("ING: " + i + " " + recipeIngredients);
            rec_ingEJB.create(recipeIngredients);
        }
        
        recipe.setCountCaloroies(recipe.getCountCaloroies() + calorias);
        
        recipeEJB.edit(recipe);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Nuevo ingrediente añadido correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public void setNewOrder() {
        
        for (int i = 0; i < this.stepsList.size(); i++) {
            
            String a2 = "" + this.stepsList.get(i);
            int a = Integer.parseInt(a2);
            Steps stp = stepsEJB.find(a);
            stp.setOrder(i + 1);
            
            stepsEJB.edit(stp);
            
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Pasos reordenados");
        FacesContext.getCurrentInstance().addMessage(null, message);
        stepsList = stepsEJB.findByRecipeId(recipe.getId());
        
    }
    
    public void deleteStep(Steps step) {
        
        System.out.println("estep: " + step);
        try {
            
            stepsEJB.remove(step);
        } catch (Exception e) {
            System.out.println("Fallo al borrar el paso: " + e.getMessage());
        }
        orderList();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Paso borrado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        stepsList = stepsEJB.findByRecipeId(recipe.getId());
    }
    
    public void orderList() {
        stepsList2 = stepsEJB.findByRecipeId(recipe.getId());
        for (int i = 0; i < stepsList2.size(); i++) {
            
            Steps stp = stepsList2.get(i);
            stp.setOrder(i + 1);
            
            stepsEJB.edit(stp);
            
        }
    }
    
    public String insertSteps() {
        stepsList2 = stepsEJB.findByRecipeId(recipe.getId());
        steptoCreate.setOrder(stepsList2.size() + 1);
        steptoCreate.setRecipeId(idRecipe);
        
        System.out.println(stepsList.toString());
        stepsEJB.create(steptoCreate);
        steptoCreate = new Steps();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Añadido paso correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        return "editRecipe.xhtml";
        
    }
    
    public void editImage() {
        
        recipe.setImage(file.getContents());
        
        recipeEJB.edit(recipe);
    }

    /**
     *
     */
    public class otherIngredient2 {
        
        private String name;
        private int calories;
        private int quantity;
        private int id;
        
        public otherIngredient2(String name, int calories, int quantity, int id) {
            this.name = name;
            this.calories = calories;
            this.quantity = quantity;
            this.id = id;
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
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
    }
    
}
