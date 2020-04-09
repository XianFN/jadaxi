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
 * @author xiann
 */
@Entity
@Table(name="roles")
public class Rol implements Serializable{
     @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int IdRol;
    @Column(name="tipousuario")
    private String tipoUsuario;
    @Column(name="descripcion")
    private String descripcion;

    @Override
    public String toString() {
        return "Rol{" + "IdRol=" + IdRol + ", tipoUsuario=" + tipoUsuario + ", descripcion=" + descripcion + '}';
    }

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.IdRol;
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
        final Rol other = (Rol) obj;
        if (this.IdRol != other.IdRol) {
            return false;
        }
        return true;
    }
    
}
