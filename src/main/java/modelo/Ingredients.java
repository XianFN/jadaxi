package modelo;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiann
 */
@Entity
@Table(name="ingredients")
public class Ingredients implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;
    @Column(name="name")
    private String name;
    
    
    
    
    
    
    

    public int getIdIngredients() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String ingredientName) {
        this.name = ingredientName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.Id;
        hash = 59 * hash + Objects.hashCode(this.name);
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
        final Ingredients other = (Ingredients) obj;
        return true;
    }
    
    
   
    
    
    
    
    
}
