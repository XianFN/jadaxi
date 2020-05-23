/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author David Rosales
 */

@Named
@ViewScoped
public class ProfileControler implements Serializable {
    
    private User user;
    
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @PostConstruct
    public void inicio() {

        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
       
                
    }
    
    public void setUser(User user){
        this.user=user;
    }
    
    public User getUser(){
        return user;
    }
    
  
    
    
}
