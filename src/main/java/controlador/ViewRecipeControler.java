package controlador;

import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
import EJB.UserFacadeLocal;
import EJB.User_recipesFacadeLocal;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;
import modelo.Ingredients;
import modelo.Recipe;
import modelo.Recipes_ingredients;
import modelo.Steps;
import modelo.User;
import modelo.User_recipes;
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
    private UserFacadeLocal userEJB;

    @EJB
    private Recipes_ingredientsFacadeLocal rec_ingEJB;

    @EJB
    private IngredientsFacadeLocal ingredientsEJB;
    @EJB
    private StepsFacadeLocal stepsEJB;

    @EJB
    private User_recipesFacadeLocal us_reEJB;

    private int idRecipe;
    private Recipe recipe;

    private StreamedContent image;

    private List<otherIngredient> ingredientsList;
    private List<Steps> stepsList;
    private Integer rating;
    private boolean flagRate = true;

    private StreamedContent file;

    @PostConstruct
    public void inicio() {
        flagRate = true;
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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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

        stepsList = stepsEJB.findByRecipeId(recipe.getId());

        return stepsList;
    }

    /**
     *
     * @param rateEvent
     */
    public void onrate(RateEvent rateEvent) {

        if (flagRate) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rateEvent.getRating());
            FacesContext.getCurrentInstance().addMessage(null, message);
            int ratings = recipe.getTotalRating();
            int peopleRated = recipe.getPeopleRating();
            ratings = ratings + (int) rateEvent.getRating();
            peopleRated++;
            recipe.setTotalRating(ratings);
            recipe.setPeopleRating(peopleRated);
            recipe.setMedia((double) ratings / (peopleRated + 0.0));

            recipeEJB.edit(recipe);
            flagRate = false;

            /**
             * Conjunto de subir de nivel
             */
            User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
            int xpadd2 = user.getLv() < 5 ? 10 - user.getLv() : 5;
            user.setXp(user.getXp() + xpadd2);
            user.setLv((int) (user.getXp() / 100));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            userEJB.edit(user);

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "Ya has votado esta receta");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void oncancel() {

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addToMyRecipes() {

        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));

        if (user.getLv() >= 1) {

            int idUser = user.getId();
            User_recipes userRecipes = new User_recipes();

            userRecipes.setRecipe_id(recipe.getId());
            userRecipes.setUser_id(idUser);
            userRecipes.setCreated(false);

            if (!us_reEJB.isJustStored(idUser, recipe.getId())) {
                System.out.println("Creamos la receta");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se ha añadido la receta");
                FacesContext.getCurrentInstance().addMessage(null, message);
                try {
                    us_reEJB.create(userRecipes);
                } catch (Exception e) {
                    System.out.println("Error al añadir a favoritos:" + e.getMessage());
                }

            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "La receta ya esta en tus recetas");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No tienes nivel para guardar la receta, el nicel necesario es: 1 , y tu nivel es: " + user.getLv());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean viewEditButton() {

        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        boolean ret = false;
        try {
            ret = us_reEJB.isCreated(user.getId(), recipe.getId());
        } catch (Exception e) {
            System.out.println("Fallo al intentar invocar el boton de editar receta");
        }

        return ret;
    }

    public boolean viewUserLabel() {

        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        boolean ret = false;
        try {
            ret = !(us_reEJB.isCreated(user.getId(), recipe.getId()));
            return false;
        } catch (Exception e) {
            System.out.println("Fallo al intentar invocar el label de usuario");
            return true;

        }

    }

    public String gerUserName() {

        return userEJB.find(us_reEJB.getRecipeOwnerID(recipe.getId())).getUserName();

    }

    public StreamedContent getFile() throws FileNotFoundException, IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf, PageSize.LETTER);

        Paragraph inicio = new Paragraph();
        inicio.add(recipe.getName());
        inicio.setTextAlignment(TextAlignment.CENTER);
        inicio.setFontSize(22);

        document.add(inicio);

        ImageData data2;
        data2 = ImageDataFactory.create(recipe.getImage());
        Image image = new Image(data2);
        image.setBorder(Border.NO_BORDER);
        image.scaleToFit(400, 250);
        image.setMarginLeft(50);

        document.add(image);

        document.add(new Paragraph("Ingredientes"));

        com.itextpdf.layout.element.Table tabla = new com.itextpdf.layout.element.Table(2);
        tabla.setWidth(500);
        tabla.setHeight(300);

        for (int i = 0; i < ingredientsList.size(); i++) {

            Cell cell1 = new Cell();
            cell1.add(ingredientsList.get(i).getName());
            tabla.addCell(cell1);
            Cell cell2 = new Cell();
            cell2.add("" + ingredientsList.get(i).getQuantity());
            tabla.addCell(cell2);

        }

        document.add(tabla);

         document.add(new Paragraph("Pasos"));

        com.itextpdf.layout.element.Table tabla2 = new com.itextpdf.layout.element.Table(2);
        tabla2.setWidth(500);
        tabla2.setHeight(300);

        for (int i = 0; i < stepsList.size(); i++) {

            Cell cell1 = new Cell();
            cell1.add(stepsList.get(i).getTitle());
            tabla2.addCell(cell1);
            Cell cell2 = new Cell();
            cell2.add("" + stepsList.get(i).getDescription());
            tabla2.addCell(cell2);

        }

        document.add(tabla2);
        
        
        
        
        
        document.close();
        String fileName = recipe.getName() + ".pdf";
        InputStream stream = new ByteArrayInputStream(baos.toByteArray());
        file = new DefaultStreamedContent(stream, "application/pdf", fileName);

        return file;
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
