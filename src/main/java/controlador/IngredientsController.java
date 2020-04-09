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

    private List<Ingredients> listaDeCategorias;
    private Ingredients cat;

    @EJB
    private IngredientsFacadeLocal categoriaEJB;

    @PostConstruct
    public void inicio() {
        try {
            listaDeCategorias = categoriaEJB.findAll();
            cat = new Ingredients();
        } catch (Exception e) {
        }

    }

    public Ingredients getCat() {
        return cat;
    }

    public void setCat(Ingredients cat) {
        this.cat = cat;
    }

    public void insertarCategoria() {
        try {
            categoriaEJB.create(cat);
        } catch (Exception e) {
            System.out.println("Error al insertar la cetegoria" + e.getMessage());
        }
    }

    public void eliminarCategoria() {
        try {
            System.out.println("DROTIUM");
            for(Ingredients c:listaDeCategorias){
                if (c.getIdIngredients()==cat.getIdIngredients()) {
                    cat=c;
                    break;
                }
            }
            categoriaEJB.remove(cat);
        } catch (Exception e) {
            System.out.println("Error al eliminar la cetegoria" + e.getMessage());
        }
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

    public List<Ingredients> getListaDeCategorias() {
        return listaDeCategorias;
    }

    public void setListaDeCategorias(List<Ingredients> listaDeCategorias) {
        this.listaDeCategorias = listaDeCategorias;
    }

}
