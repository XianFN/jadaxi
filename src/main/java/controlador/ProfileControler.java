/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import EJB.UserFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;


/**
 *
 * @author David Rosales
 */

@Named
@ViewScoped
public class ProfileControler implements Serializable {
    
    private User user;
    
    private int progreso;
    
    private String myDate;
    
    private String pass;
    
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @PostConstruct
    public void inicio() {

        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        int level = user.getLv();
        int exp = (int) user.getXp();
        progreso=exp%100;
        
        myDate = new SimpleDateFormat("dd-MM-yyyy").format(user.getBirthDate());
                
    }
    
    public void setUser(User user){
        this.user=user;
    }
    
    public User getUser(){
        return user;
    }
    
    public int getProgreso(){
        return progreso;
    }
    
    public String getMyDate(){
        return myDate;
    }
    
     public void setPass(String pass){
        this.pass=pass;
    }
    
    public String getPass(){
        return pass;
    }
    
    public void checkPassword() throws IOException{

        if (!((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getPassword().equals(pass)) {

            System.out.println("Fallo al introducir la contraseña");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }
    
    public void guardar(){
        userEJB.edit(user);
    }
    
}
