/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.User_recipesFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Category;
import modelo.Recipe;
import modelo.User;
import modelo.User_recipes;

/**
 *
 * @author Javier
 */

@Named
@ViewScoped
public class AdminControler implements Serializable {
    
    private List<Recipe> listRecipes;
    
    private Recipe recipe;
    
    private List<User_recipes> userRecipes;
    
    private User user;
    
    private Category category;
    
    @EJB
    private RecipeFacadeLocal recipeEJB;
     
    @EJB
    private User_recipesFacadeLocal user_recipesEJB;
     
    @EJB
    private CategoryFacadeLocal categoryEJB;
    
    @PostConstruct
    public void inicio() {
        
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        listRecipes=recipeEJB.findAll();
        recipe = new Recipe();
        category = new Category();
        /*userRecipes=user_recipesEJB.findByUserId(user.getId());     
        
        for(int i=0; i<userRecipes.size(); i++){
           listRecipes.add(recipeEJB.find(userRecipes.get(i).getRecipe_id()));
        }*/
       
    }
    
    public List<Recipe> getListRecipes(){
        return listRecipes;
    }
    
    public void setListRecipes(List<Recipe> listRecipes){
        this.listRecipes=listRecipes;
    }
    
    public Recipe getRecipe(){
        return recipe;
    }
    
    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }
    
    public Category getCategory(){
        return category;
    }
    
    public void setCategory(Category category){
        this.category=category;
    }
    
    
    
    public void checkLevel() throws IOException{

         System.out.println("ENTRA QUI ADMIN");
          
         
        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 100) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado por tener un nivel menor a 100");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }
    
    public void crearCategoria(){
        
        try {
            categoryEJB.create(category);
        } catch (Exception e) {
            System.out.println("Error al crear la categoría: " + e.getMessage());
       }
        destroyWorld();
    }
    
    public void eliminarReceta(Recipe r){
        System.out.println(r.toString());
    }
    
    public void destroyWorld() {
        addMessage("Éxito", "Se ha creado una nueva categoría.");
    }
    
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
