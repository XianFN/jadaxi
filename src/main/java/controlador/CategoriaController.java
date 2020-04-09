/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jdk.nashorn.internal.ir.TryNode;
import modelo.Categoria;

/**
 *
 * @author xiann
 */
@Named
@ViewScoped

public class CategoriaController implements Serializable {

    private List<Categoria> listaDeCategorias;
    private Categoria cat;

    @EJB
    private CategoriaFacadeLocal categoriaEJB;

    @PostConstruct
    public void inicio() {
        try {
            listaDeCategorias = categoriaEJB.findAll();
            cat = new Categoria();
        } catch (Exception e) {
        }

    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
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
            for(Categoria c:listaDeCategorias){
                if (c.getIdCategoria()==cat.getIdCategoria()) {
                    cat=c;
                    break;
                }
            }
            categoriaEJB.remove(cat);
        } catch (Exception e) {
            System.out.println("Error al eliminar la cetegoria" + e.getMessage());
        }
    }
      public void modificarCategoria() {
        try {
            String newName= cat.getName();
            System.out.println("DROTIUMv2");
            for(Categoria c:listaDeCategorias){
                if (c.getIdCategoria()==cat.getIdCategoria()) {
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

    public List<Categoria> getListaDeCategorias() {
        return listaDeCategorias;
    }

    public void setListaDeCategorias(List<Categoria> listaDeCategorias) {
        this.listaDeCategorias = listaDeCategorias;
    }

}
