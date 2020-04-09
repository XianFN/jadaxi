/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author xiann
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdUsuario;
    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @Column(name = "ultimaconexion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaconexion;
    @Column(name = "estado")
    private boolean estado = true;

    @JoinColumn(name = "idPersona")
    @OneToOne
    private Persona persona;
    
    @JoinColumn(name = "idRol")
    @ManyToOne
    private Rol rol;
    
    // @Transient sirve para añadir un atributo a l clase, sin que se suba a la base de datos

    @Override
    public String toString() {
        return "Usuario{" + "IdUsuario=" + IdUsuario + ", user=" + user + ", password=" + password + ", ultimaconexion=" + ultimaconexion + ", estado=" + estado + ", persona=" + persona + ", rol=" + rol + '}';
    }

    
    
    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUltimaconexion() {
        return ultimaconexion;
    }

    public void setUltimaconexion(Date ultimaconexion) {
        this.ultimaconexion = ultimaconexion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.IdUsuario;
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
        final Usuario other = (Usuario) obj;
        if (this.IdUsuario != other.IdUsuario) {
            return false;
        }
        return true;
    }
    
    
    

}
