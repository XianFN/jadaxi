/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.IngredientsFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jdk.nashorn.internal.ir.TryNode;
import modelo.Ingredients;

/**
 *
 * @author xiann
 */
@Named
@ViewScoped

public class IngredientsController implements Serializable {

    private List<Ingredients> ingredientsList;
    private Ingredients ingredient;

    @EJB
    private IngredientsFacadeLocal ingredientsEJB;

    @PostConstruct
    public void inicio() {
        try {
            ingredientsList = ingredientsEJB.findAll();
            ingredient = new Ingredients();
        } catch (Exception e) {
        }

    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }


    public void insertIngredient() {
        try {
            System.out.println("DROTIUMJ");
            ingredientsEJB.create(ingredient);
        } catch (Exception e) {
            System.out.println("Error al insertar el ingrediente" + e.getMessage());
        }
    }

    public void eliminarCategoria() {
        try {
            System.out.println("DROTIUM");
            for(Ingredients c:ingredientsList){
                if (c.getIdIngredients()==ingredient.getIdIngredients()) {
                    ingredient=c;
                    break;
                }
            }
            ingredientsEJB.remove(ingredient);
        } catch (Exception e) {
            System.out.println("Error al eliminar  el ingredient" + e.getMessage());
        }
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public IngredientsFacadeLocal getIngredientsEJB() {
        return ingredientsEJB;
    }

    public void setIngredientsEJB(IngredientsFacadeLocal ingredientsEJB) {
        this.ingredientsEJB = ingredientsEJB;
    }
    
    /*
      public void modificarCategoria() {
        try {
            String newName= cat.getIngredientName();
            System.out.println("DROTIUMv2");
            for(Ingredients c:listaDeCategorias){
                if (c.getIdIngredients()==cat.getIdIngredients()) {
                    cat=c;
                    cat.setName(newName);
                    break;
                }
            }
            categoriaEJB.edit(cat);
        } catch (Exception e) {
            System.out.println("Error al eliminar la cetegoria" + e.getMessage());
        }
    }
*/


}