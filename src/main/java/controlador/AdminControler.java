/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author Javier
 */

@Named
@ViewScoped
public class AdminControler implements Serializable {
    
    
    public void checkLevel() throws IOException{

         System.out.println("ENTRA QUI ADMIN");
          
         
        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 100) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado por tener un nivel menor a 100");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }
    
}
