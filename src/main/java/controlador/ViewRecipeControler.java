package controlador;

import EJB.CommentaryFacadeLocal;
import EJB.IngredientsFacadeLocal;
import EJB.RecipeFacadeLocal;
import EJB.Recipes_ingredientsFacadeLocal;
import EJB.StepsFacadeLocal;
import EJB.UserFacadeLocal;
import EJB.User_recipesFacadeLocal;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
import modelo.Commentary;
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
 * @author jadaxi
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

    @EJB
    private CommentaryFacadeLocal commenEJB;

    private int idRecipe;
    private Recipe recipe;

    private StreamedContent image;

    private List<otherIngredient> ingredientsList;
    private List<Steps> stepsList;
    private Integer rating;
    private boolean flagRate = true;

    private StreamedContent file;

    private List<Commentary> allComentaries;
    private List<Commentary> commentaries;

    private String comment;

    @PostConstruct
    public void inicio() {
        flagRate = true;
        recipe = null;
        commentaries = new ArrayList<>();
        idRecipe = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("recipe");

        try {
            recipe = recipeEJB.find(idRecipe);
        } catch (Exception e) {
            System.out.println("Error al traer la receta con el id: " + idRecipe + ".:" + e.getMessage());
        }

        System.out.println("RECIPE: " + recipe.toString());

        rating = 0;

        try {

            allComentaries = commenEJB.findByRecipe(recipe.getId());

        } catch (Exception e) {
            System.out.println("fallo al ter los comentarios: " + e.getMessage());
        }

        System.out.println("all: " + allComentaries.size());

        for (int i = 0; i < allComentaries.size(); i++) {
            System.out.println("I: " + i);
            if (allComentaries.get(i).isIsComentary()) {
                commentaries.add(allComentaries.get(i));
            }

        }
        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        Commentary comm = commenEJB.findRelation(recipe.getId(), user.getId());
        rating = comm==null? 0: comm.getRate();
      
       

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

    public List<Commentary> getAllComentaries() {
        return allComentaries;
    }

    public void setAllComentaries(List<Commentary> allComentaries) {
        this.allComentaries = allComentaries;
    }

    public List<Commentary> getCommentaries() {

        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     * 
     * Imprimimos la imagen
     */
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

    /**
     * 
     * @return 
     * 
     * Obtenemos los ingredientes de la receta
     */
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

        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        Commentary comm = commenEJB.findRelation(recipe.getId(), user.getId());

        if (comm == null) {

            Commentary coment = new Commentary();

            coment.setRate((int) rateEvent.getRating());
            coment.setIsRated(true);
            coment.setRecipeId(recipe.getId());
            coment.setUserId(user.getId());

            commenEJB.create(coment);

            int ratings = recipe.getTotalRating();
            int peopleRated = recipe.getPeopleRating();
            ratings = ratings + (int) rateEvent.getRating();
            peopleRated++;
            recipe.setTotalRating(ratings);
            recipe.setPeopleRating(peopleRated);
            recipe.setMedia((double) ratings / (peopleRated + 0.0));

            recipeEJB.edit(recipe);
            int xpadd2 = user.getLv() < 5 ? 20 - (user.getLv() * 3) : 8;
            xpadd2 = user.getLv() == 99 ? 0 : xpadd2;
            user.setXp(user.getXp() + xpadd2);
            user.setLv((int) (user.getXp() / 100));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            userEJB.edit(user);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votacion correcta", "Tu valoracion fue: " + rateEvent.getRating());
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else if (comm.isIsRated()) {
            System.out.println("NO COMENTAR");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Ya has votado previamente esta receta");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {

            comm.setRate((int) rateEvent.getRating());
            comm.setIsRated(true);

            commenEJB.edit(comm);

            int ratings = recipe.getTotalRating();
            int peopleRated = recipe.getPeopleRating();
            ratings = ratings + (int) rateEvent.getRating();
            peopleRated++;
            recipe.setTotalRating(ratings);
            recipe.setPeopleRating(peopleRated);
            recipe.setMedia((double) ratings / (peopleRated + 0.0));

            recipeEJB.edit(recipe);
            int xpadd2 = user.getLv() < 5 ? 10 - user.getLv() : 5;
            xpadd2 = user.getLv() == 99 ? 0 : xpadd2;
            user.setXp(user.getXp() + xpadd2);
            user.setLv((int) (user.getXp() / 100));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            userEJB.edit(user);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votacion correcta", "Tu valoracion fue: " + rateEvent.getRating());
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

    }

    public void oncancel() {
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelada la votacion", "Votacion reseteada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * 
     * Añadimos la receta a la lista personal del usuario
     */
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
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No tienes nivel para guardar la receta, el nivel necesario es: 1 , y tu nivel es: " + user.getLv());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /**
     * 
     * @return 
     * 
     * Si la receta pertenecea al usuario imprimimos un boton para editarla
     */
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

    public String getComentaryUser(int id) {

        return userEJB.find(id).getUserName();
    }

    /**
     * 
     * Comprobamos los parametros necesarios para poder comentar en la receta
     */
    public void comentRecipe() {

        User user = ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));

        Commentary comm = commenEJB.findRelation(recipe.getId(), user.getId());

        if (comm == null) {

            Commentary coment = new Commentary();

            coment.setCommentary(this.comment);
            coment.setIsComentary(true);
            coment.setRecipeId(recipe.getId());
            coment.setUserId(user.getId());

            commenEJB.create(coment);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Comentario guardado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else if (comm.isIsComentary()) {
            System.out.println("NO COMENTAR");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Ya has comentado previamente esta receta");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {

            comm.setCommentary(this.comment);
            comm.setIsComentary(true);

            commenEJB.edit(comm);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Comentario guardado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

    }

    /**
     * 
     * @return
     * @throws FileNotFoundException 
     * 
     * Imprimos la receta en un pdf y se descarga
     */
    public StreamedContent getFile() throws FileNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf, PageSize.LETTER);
        Color verde = new DeviceRgb(152, 210, 128);
        document.setBackgroundColor(verde);

        Paragraph inicio = new Paragraph();
        inicio.add(recipe.getName());

        inicio.setTextAlignment(TextAlignment.CENTER);
        inicio.setFontSize(26);

        document.add(inicio);

        com.itextpdf.layout.element.Table tablaPrincipal = new com.itextpdf.layout.element.Table(new float[]{356, 194});
        tablaPrincipal.setWidth(550);
        tablaPrincipal.setBorder(Border.NO_BORDER);
        tablaPrincipal.setPadding(0);
        tablaPrincipal.setMargin(0);

        ImageData data2;
        data2 = ImageDataFactory.create(recipe.getImage());
        Image image = new Image(data2);
        image.setBorder(new SolidBorder(verde, 3));
        image.scaleToFit(350, 225);

        Cell cellfoto = new Cell();
        cellfoto.add(image);
        cellfoto.setBorder(Border.NO_BORDER);
        tablaPrincipal.addCell(cellfoto);

        Cell todosIngredientes = new Cell();
        todosIngredientes.setBorder(Border.NO_BORDER);
        todosIngredientes.setMarginRight(5);
        todosIngredientes.add(new Paragraph("Ingredientes: ").setBold());
        todosIngredientes.add(new Paragraph(""));

        todosIngredientes.add(new Paragraph(""));

        for (int i = 0; i < ingredientsList.size(); i++) {
            com.itextpdf.layout.element.Table tabla = new com.itextpdf.layout.element.Table(new float[]{140, 50});
            tabla.setWidth(194);
            tabla.setBorder(Border.NO_BORDER);
            tabla.setPadding(0);
            tabla.setMargin(0);

            Cell cell1 = new Cell();
            cell1.setBorder(Border.NO_BORDER);
            cell1.setPadding(0);
            cell1.add(ingredientsList.get(i).getName());
            tabla.addCell(cell1);
            Cell cell2 = new Cell();
            cell2.setBorder(Border.NO_BORDER);
            cell2.setPadding(0);
            cell2.add(ingredientsList.get(i).getQuantity() + " g.");
            tabla.addCell(cell2);

            todosIngredientes.add(tabla);

        }

        tablaPrincipal.addCell(todosIngredientes);
        document.add(tablaPrincipal);

        document.add(new Paragraph("Pasos :").setBold().setFontSize(32));

        for (int i = 0; i < stepsList.size(); i++) {
            com.itextpdf.layout.element.Table tabla2 = new com.itextpdf.layout.element.Table(1);
            tabla2.setWidth(500);
            tabla2.setBorder(Border.NO_BORDER);
            tabla2.setMarginTop(10);
            tabla2.setPadding(0);

            Cell cell1 = new Cell();
            cell1.setMargin(0);
            cell1.setBorder(Border.NO_BORDER);
            cell1.setBold();
            cell1.setPadding(0);
            cell1.add((i + 1) + ") " + stepsList.get(i).getTitle());
            tabla2.addCell(cell1);
            Cell cell2 = new Cell();
            cell2.setBorder(Border.NO_BORDER);
            cell2.setPadding(0);
            cell2.add("      " + stepsList.get(i).getDescription());
            tabla2.addCell(cell2);

            document.add(tabla2);
        }

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
