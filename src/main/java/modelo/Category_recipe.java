/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "category_recipe")
public class Category_recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO PREGUNTAR
    //@ManyToMany
    @Column(name = "category_id")
    private int category;

    //TODO PREGUNTAR
    //@ManyToMany
    @Column(name = "recipe_id")
    private int recipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRecipe() {
        return recipe;
    }

    public void setRecipe(int recipe) {
        this.recipe = recipe;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.category;
        hash = 29 * hash + this.recipe;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category_recipe other = (Category_recipe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        if (this.recipe != other.recipe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category_recipe{" + "id=" + id + ", category=" + category + ", recipe=" + recipe + '}';
    }

}
