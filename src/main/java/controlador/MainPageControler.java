/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.IngredientsFacadeLocal;
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
import modelo.Category;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.User;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    private List<StreamedContent> images;
    private List<Category> categories;
    private List<Ingredients> ingredients;

    @PostConstruct
    public void init() {

        images = new ArrayList<StreamedContent>();

        try {
            recipes = recipeEJB.orderBymedia();

        } catch (Exception e) {

            System.out.println("Fallo al obtener todas las recetas: " + e.getMessage());
        }

        try {
            for (int i = 0; i < 10; i++) {

                images.add(getImage(i));

            }

        } catch (Exception e) {
            System.out.println("Para variar algo ha fallado con las p**** imagenes");
        }

    }

    public List<StreamedContent> getImages() {
        return images;
    }
//TODO actualizar a top 15/10

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

            context.addMessage(null, new FacesMessage("ERROR", "No tienes nivel para buscar por ingrediente, necesitas nivel 5. Tu nivel actual: " + ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv()));
            System.out.println("Nivel menor que 5");
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

    public StreamedContent getImage(int i) {

        Blob bl = null;

        try {
            bl = new SerialBlob(recipes.get(i).getImage());
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

}
