/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author jadaxi
 *
 * Parte controlador de la vista index, la caul contiene el login de la
 * aplicacion
 */
@Named
@ViewScoped
public class IndexControler implements Serializable {

    private User user;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void inicio() {
        try {

            user = new User();

        } catch (Exception e) {
            System.out.println("Fallo al crear el IndexControler: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login() {

        User us = null;
        //TODO ?faces-redirect=true
        String navegacion = "private/mainPage.xhtml?faces-redirect=true"; //?faces-redirect=true se encarga de mostrarnos en el navegador a que pagina vamos, es ideal para desarrollos pero es conveniente quitarlo cuando salga al publico
        try {
            us = userEJB.getUserURL(user);
            //Ponemos el usuari como variable global para toda la aplicaion
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
            return navegacion;

        } catch (Exception e) {

            System.out.println("Fallo al pasar los datos de logueo al facade: " + e.getMessage());
            return ""; //TODO poner aqui a donde ira la pagina si se falla el login
        }
       

        

    }

}
