/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.CategoryFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Category;
import modelo.Recipe;
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

    private String preUser;
    private String prePass;

    @EJB
    private UserFacadeLocal userEJB;



    @PostConstruct
    public void inicio() {

        System.out.println("FUERA");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();//Nos borra la sesion poara que al iniciar o al hacer logout se limpie

        try {

            user = new User();

        } catch (Exception e) {
            System.out.println("Fallo al crear el IndexControler: " + e.getMessage());
        }
    }

    public String getPreUser() {
        return preUser;
    }

    public void setPreUser(String preUser) {
        this.preUser = preUser;
    }

    public String getPrePass() {
        return prePass;
    }

    public void setPrePass(String prePass) {
        this.prePass = prePass;
    }

    public String login() {

        User us = null;
        int createrUser = 0;
        int createPass = 0;
        //TODO ?faces-redirect=true
        String navegacion = "private/mainPage.xhtml?faces-redirect=true"; //?faces-redirect=true se encarga de mostrarnos en el navegador a que pagina vamos, es ideal para desarrollos pero es conveniente quitarlo cuando salga al publico

        for (int i = 0; i < preUser.length(); i++) {

            char character = preUser.charAt(i);
            if (character == '=' || character == '?' || character == '*' || character == ' ' || character == '"' || character == '/') {
                System.out.println("MAL CARACTER US");
                createrUser++;
            }

        }

        for (int i = 0; i < prePass.length(); i++) {

            char character = prePass.charAt(i);
            if (character == '=' || character == '?' || character == '*' || character == ' ' || character == '"' || character == '/') {
                System.out.println("MAL CARACTER PASS");
                createPass++;
            }
        }

        if ((createrUser == 0) && (createPass == 0)) {
            try {

                user.setUserName(preUser);
                user.setPassword(prePass);

                us = userEJB.getUserURL(user);

                //Ponemos el usuari como variable global para toda la aplicaion
            } catch (Exception e) {
                System.out.println("FAllo al tarer el usuario: " + e.getMessage());
            }

        }

        if (us.isActivated()) {
            System.out.println("Usuario activado");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
            return navegacion;
        } else {
            System.out.println("Usuario no activado");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("code", us);
            return "activate.xhtml";
        }

    }


}
