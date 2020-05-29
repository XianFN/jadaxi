/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Category;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.User;

/**
 *
 */
@Named
@ViewScoped
public class MainPageControler implements Serializable {

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private CategoryFacadeLocal categoryEJB;

    @EJB
    private IngredientsFacadeLocal ingredientsEJB;

    private List<Recipe> recipes;

    private List<Category> categories;
    private List<Ingredients> ingredients;

    private List<Recipe> recipesTop10;

    private boolean link;

    @PostConstruct
    public void init() {

        recipesTop10 = new ArrayList<>();
        link = false;

        try {
            recipes = recipeEJB.orderBymedia();
            System.out.println(recipes);

        } catch (Exception e) {

            System.out.println("Fallo al obtener todas las recetas: " + e.getMessage());
        }

        try {
            //TODO cambiar a 10 recetas
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                recipesTop10.add(recipes.get(i));

            }

        } catch (Exception e) {
            System.out.println("Para variar algo ha fallado con las p**** imagenes" + e.getMessage());
        }

    }

    public List<Recipe> getRecipesTop10() {
        return recipesTop10;
    }

    public void setRecipesTop10(List<Recipe> recipesTop10) {
        this.recipesTop10 = recipesTop10;
    }

    public String goToviewRecipesInCategories(String category) {

        Object ob = category;
        System.out.println("cat: " + category);
        Category cat = categoryEJB.findByName(category);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryToShow", cat.getId());//guardamos el id en el contexto
        //TODO ?faces-redirect=true
        return "viewRecipesList.xhtml?faces-redirect=true";

    }

    public String goToviewRecipesIngredients(String ingredient) {

        int usLv = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv();

        if (usLv < 5) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("ERROR", "No tienes nivel para acceder a esta parte, necesitas nivel 5. Tu nivel actual: " + ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv()));
            System.out.println("NIvel menor que 5");
            return "";
        } else {
            Object ob = ingredient;
            System.out.println("cat: " + ingredient);
            Ingredients ing = ingredientsEJB.findByName(ingredient);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ingredientToShow", ing.getId());//guardamos el id en el contexto
            //TODO ?faces-redirect=true
            return "viewRecipeListIngredients.xhtml?faces-redirect=true";
        }

    }

    public List<Category> getCategories() {

        categories = new ArrayList<>();

        categories = categoryEJB.findAll();

        return categories;

    }

    public List<Ingredients> getIngredients() {

        ingredients = new ArrayList<>();

        ingredients = ingredientsEJB.findAll();

        return ingredients;

    }

    public String click() {

        System.out.println("CLICK");
        FacesContext context = FacesContext.getCurrentInstance();
        String reciepId = context.getExternalContext().getRequestParameterMap().get("recipeId2");
        System.out.println(reciepId);
        Recipe rec = recipeEJB.find(Integer.valueOf(reciepId));

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recipe", rec.getId());

        return "/private/viewRecipe.xhtml";

    }

}
