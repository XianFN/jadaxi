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
@Table(name = "user_recipes")
public class User_recipes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO PREGUNTAR
    //@ManyToMany
    @Column(name = "recipe_Id")
    private int recipe_id;

    //TODO PREGUNTAR
    //@ManyToMany
    @Column(name = "user_Id")
    private int user_id;

    @Column(name = "created")
    private boolean created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        hash = 71 * hash + this.recipe_id;
        hash = 71 * hash + this.user_id;
        hash = 71 * hash + (this.created ? 1 : 0);
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
        final User_recipes other = (User_recipes) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.recipe_id != other.recipe_id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.created != other.created) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User_recipes{" + "id=" + id + ", recipe_id=" + recipe_id + ", user_id=" + user_id + ", created=" + created + '}';
    }
    
    



}
