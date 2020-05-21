/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.RecipeFacadeLocal;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;
import modelo.Category;
import modelo.Recipe;
import org.primefaces.component.contentflow.ContentFlow;
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

    private List<Recipe> recipes;

    private List<StreamedContent> images;

    @PostConstruct
    public void init() {

        images = new ArrayList<StreamedContent>();

        try {
            recipes = recipeEJB.findAll();

        } catch (Exception e) {

            System.out.println("Fallo al obtener todas las recetas: " + e.getMessage());
        }

        for (int i = 0; i < recipes.size(); i++) {

            images.add(getImage(i));

        }

        ContentFlow a = new ContentFlow();

    }

    public List<StreamedContent> getImages() {
        return images;
    }

    //TODO PREGUNTAR si se pueden crear botones desde la vista de java
    public String goToviewRecipesInCategories(String category) {

        Object ob = category;

        Category cat = categoryEJB.findByName(category);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryToShow", cat.getId());//guardamos el id en el contexto
        //TODO ?faces-redirect=true
        return "viewRecipesList.xhtml?faces-redirect=true";

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

        System.out.println(dbImage.getName());
        System.out.println("jsahbdfñoiuhasñoiudfhñpoiaushdfñiouahdfñ.i");
        return dbImage;
    }

}
