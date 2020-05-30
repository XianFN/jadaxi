package controlador;

import EJB.CategoryFacadeLocal;
import EJB.Category_recipeFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
import EJB.UserFacadeLocal;
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
import modelo.Category_recipe;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import modelo.User;
import modelo.User_recipes;

/**
 *
 * @author jadaxi
 */
@Named
@ViewScoped
public class AdminControler implements Serializable {

    private List<Recipe> listRecipes;

    private Recipe recipe;

    private List<User_recipes> userRecipes;

    private User user;

    private Category category;

    private List<Category> listCategory;

    private Category_recipe categoryRecipe;

    @EJB
    private RecipeFacadeLocal recipeEJB;

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private User_recipesFacadeLocal user_recipesEJB;

    @EJB
    private CategoryFacadeLocal categoryEJB;

    @EJB
    private Category_recipeFacadeLocal categoryRecipeEJB;

    @EJB
    private StepsFacadeLocal stepsEJB;

    @EJB
    private Recipes_ingredientsFacadeLocal recipe_ingredientsEJB;

    @PostConstruct
    public void inicio() {

        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        listRecipes = recipeEJB.findAll();
        listCategory = categoryEJB.findAll();
        recipe = new Recipe();
        category = new Category();

    }

    public List<Recipe> getListRecipes() {
        return listRecipes;
    }

    public void setListRecipes(List<Recipe> listRecipes) {
        this.listRecipes = listRecipes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void checkLevel() throws IOException {

        System.out.println("ENTRA QUI ADMIN");

        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 100) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado por tener un nivel menor a 100");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }

    /**
     *
     * Creamos una categoria
     */
    public void crearCategoria() {

        try {
            categoryEJB.create(category);
        } catch (Exception e) {
            System.out.println("Error al crear la categoría: " + e.getMessage());
        }
        destroyWorld();
    }

    /**
     *
     * @param r
     *
     * Eliminamos la receta que pasasmos por parametro
     */
    public void eliminarReceta(Recipe r) {

        try {
            List<Category_recipe> cr = categoryRecipeEJB.findByRecipeId(r.getId());
            for (int i = 0; i < cr.size(); i++) {
                Category aux = categoryEJB.find(cr.get(i).getCategory());
                aux.setAmmount(aux.getAmmount() - 1);
                categoryEJB.edit(aux);
                categoryRecipeEJB.remove(cr.get(i));

            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "INFO", "Receta borrada correctramente"));

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "INFO", "Error al borrar category recipes"));
            System.out.println("Error al borrar category recipes: " + e.getMessage());
        }

        try {
            List<Steps> s = stepsEJB.findByRecipeId(r.getId());
            for (int i = 0; i < s.size(); i++) {
                stepsEJB.remove(s.get(i));
            }

        } catch (Exception e) {
            System.out.println("Error al borrar pasos: " + e.getMessage());
        }

        try {
            List<User_recipes> u = user_recipesEJB.findByRecipeId(r.getId());
            for (int i = 0; i < u.size(); i++) {
                user_recipesEJB.remove(u.get(i));
            }

        } catch (Exception e) {
            System.out.println("Error al borrar user recipes: " + e.getMessage());
        }

        try {
            List<Recipes_ingredients> in = recipe_ingredientsEJB.findByRecipeId(r.getId());
            for (int i = 0; i < in.size(); i++) {
                recipe_ingredientsEJB.remove(in.get(i));
            }

        } catch (Exception e) {
            System.out.println("Error al borrar ingredients recipes: " + e.getMessage());
        }

        try {
            recipeEJB.remove(r);

        } catch (Exception e) {
            System.out.println("Error al borrar receta: " + e.getMessage());
        }

        try {
            user.setRecipes(user.getRecipes() - 1);
            userEJB.edit(user);

        } catch (Exception e) {
            System.out.println("Error al decrementar ocntador: " + e.getMessage());
        }

    }

    public void destroyWorld() {
        addMessage("Éxito", "Se ha creado una nueva categoría.");
    }

    public void destroyRecipe() {
        addMessage("Éxito", "Se ha eliminado la receta.");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
