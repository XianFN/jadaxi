/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.UserFacadeLocal;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author David Rosales
 */
@Named
@ViewScoped
public class ProfileControler implements Serializable {

    private User us;
    private int progreso;
    
    private String name;
    
    
    
    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {

        us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        System.out.println("us:" + us.toString());

        int level = us.getLv();
        int exp = (int) us.getXp();
        progreso = exp % 100;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
    
    private boolean checkUserName(String user) {

        List<User> users = null;
        try {
            users = userEJB.findAll();
        } catch (Exception e) {
            System.out.println("Fallo al traer los nombres de usuario: " + e.getMessage());
        }

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserName().equals(user)) {
                System.out.println("Se repiten " + user + " y " + users.get(i).getUserName());
                return false;
            }

        }

        return true;

    }
    
    
    public String modifyUserName(){
        
        
        if(checkUserName(us.getUserName())){
            
        }else{
            
        }
        
        
        return "";
    }
    
    
    

}
