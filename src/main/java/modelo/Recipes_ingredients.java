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
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 *
 * @author Javier
 */
@Entity
@Table(name="recipes_ingredients")
public class Recipes_ingredients implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    //TODO PREGUNTAR
    @ManyToMany
    @Column(name = "recipe_id")
    private int recipe;
    
    //TODO PREGUNTAR
    @ManyToMany
    @Column(name = "ingredient_id")
    private int ingredients;
    
    
    @Column(name = "ammount")
    private int ammount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipe() {
        return recipe;
    }

    public void setRecipe(int recipe) {
        this.recipe = recipe;
    }

    public int getIngredients() {
        return ingredients;
    }

    public void setIngredients(int ingredients) {
        this.ingredients = ingredients;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.recipe;
        hash = 23 * hash + this.ingredients;
        hash = 23 * hash + this.ammount;
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
        final Recipes_ingredients other = (Recipes_ingredients) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.recipe != other.recipe) {
            return false;
        }
        if (this.ingredients != other.ingredients) {
            return false;
        }
        if (this.ammount != other.ammount) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipes_ingredients{" + "id=" + id + ", recipe=" + recipe + ", ingredients=" + ingredients + ", ammount=" + ammount + '}';
    }
    
    
    
   
    
    
}
