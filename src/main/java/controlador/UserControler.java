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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author Javier
 */
@Named
@ViewScoped
public class UserControler implements Serializable {

    private User user;
    private String checkPassword;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void inicio() {
        try {

            user = new User();
            checkPassword = "";
        } catch (Exception e) {
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public void insertUser() {
        System.out.println("knsamdfòisbndfpiubn");
        try {

            userEJB.create(user);
        } catch (Exception e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
        }
    }
}
