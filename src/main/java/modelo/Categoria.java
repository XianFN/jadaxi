package modelo;


import java.io.Serializable;
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
@Table(name="Categorias")
public class Categoria implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int IdCategoria;
    @Column(name="nombre")
    private String name;
    @Column(name="estado")
    private boolean estado=true;

    public int getIdCategoria() {
        return IdCategoria;
    }

    public void setIdCategoria(int IdCategoria) {
        this.IdCategoria = IdCategoria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.IdCategoria;
        return hash;
    }

    @Override
    public String toString() {
        return "Categoria{" + "IdCategoria=" + IdCategoria + ", name=" + name + ", estado=" + estado + '}';
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
        final Categoria other = (Categoria) obj;
        if (this.IdCategoria != other.IdCategoria) {
            return false;
        }
        return true;
    }
    
    
}
