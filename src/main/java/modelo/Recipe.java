/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jadaxi
 */
@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {
    //TODO quitar default 0 a countcalories

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "countcalories")
    private double countCaloroies;
    
    @Column(name = "totalrating")
    private int totalRating;
    
    @Column(name = "peoplerating")
    private int peopleRating;
    
    

// hascer join table a la intermedia
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getCountCaloroies() {
        return countCaloroies;
    }

    public void setCountCaloroies(double countCaloroies) {
        this.countCaloroies = countCaloroies;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getPeopleRating() {
        return peopleRating;
    }

    public void setPeopleRating(int peopleRating) {
        this.peopleRating = peopleRating;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Arrays.hashCode(this.image);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.countCaloroies) ^ (Double.doubleToLongBits(this.countCaloroies) >>> 32));
        hash = 37 * hash + this.totalRating;
        hash = 37 * hash + this.peopleRating;
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
        final Recipe other = (Recipe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.countCaloroies) != Double.doubleToLongBits(other.countCaloroies)) {
            return false;
        }
        if (this.totalRating != other.totalRating) {
            return false;
        }
        if (this.peopleRating != other.peopleRating) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.equals(this.image, other.image)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", image=" + image + ", countCaloroies=" + countCaloroies + ", totalRating=" + totalRating + ", peopleRating=" + peopleRating + '}';
    }
    
    

}