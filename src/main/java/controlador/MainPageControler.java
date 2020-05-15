/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.RecipeFacadeLocal;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import modelo.Recipe;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class MainPageControler implements Serializable {

    @EJB
    private RecipeFacadeLocal recipeEJB;

    List<Recipe> recipes;

    private List<String> images;

    @PostConstruct
    public void init() {
        
         images = new ArrayList<String>();
        
        try {
            recipes = recipeEJB.findAll();

        } catch (Exception e) {

            System.out.println("Fallo al obtener todas las recetas: " + e.getMessage());
        }

        for (int i = 0; i < recipes.get(0).getImage().length; i++) {

            //System.out.print(recipes.get(0).getImage()[i]);
        }

        for (int i = 0; i < recipes.size(); i++) {

            ByteArrayInputStream bis = new ByteArrayInputStream(recipes.get(i).getImage());
            BufferedImage bImage2 = null;

            try {
                bImage2 = ImageIO.read(bis);
                File outputfile = new File("saved.png");
                ImageIO.write(bImage2, "jpg", new File("img/" + i + ".jpg"));

            } catch (IOException ex) {
                Logger.getLogger(MainPageControler.class.getName()).log(Level.SEVERE, null, ex);

            }
            System.out.println(System.getProperty("user.dir") + "\\img\\" + i + ".jpg");
            
            images.add(System.getProperty("user.dir") + "\\img\\" + i + ".jpg");
            //System.out.println(images.get(i));
        }

    }

    public List<String> getImages() {
        return images;
    }

}
