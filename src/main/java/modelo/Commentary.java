/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Javier
 */
@Entity
@Table(name = "commentary")
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "rate")
    private int rate;

    @Column(name = "isComentary")
    private boolean isComentary;

    @Column(name = "isRated")
    private boolean isRated;

    @Column(name = "recipe_id")
    private int recipeId;

    @Column(name = "user_id")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isIsComentary() {
        return isComentary;
    }

    public void setIsComentary(boolean isComentary) {
        this.isComentary = isComentary;
    }

    public boolean isIsRated() {
        return isRated;
    }

    public void setIsRated(boolean isRated) {
        this.isRated = isRated;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.commentary);
        hash = 79 * hash + this.rate;
        hash = 79 * hash + (this.isComentary ? 1 : 0);
        hash = 79 * hash + (this.isRated ? 1 : 0);
        hash = 79 * hash + this.recipeId;
        hash = 79 * hash + this.userId;
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
        final Commentary other = (Commentary) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.rate != other.rate) {
            return false;
        }
        if (this.isComentary != other.isComentary) {
            return false;
        }
        if (this.isRated != other.isRated) {
            return false;
        }
        if (this.recipeId != other.recipeId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (!Objects.equals(this.commentary, other.commentary)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commentary{" + "id=" + id + ", commentary=" + commentary + ", rate=" + rate + ", isComentary=" + isComentary + ", isRated=" + isRated + ", recipeId=" + recipeId + ", userId=" + userId + '}';
    }

}
