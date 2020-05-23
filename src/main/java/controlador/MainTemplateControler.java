/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author jadaxi
 *
 * clase encargada de mantener la seguridad de la pagina, compurba si el usurio
 * que esta en el sistema esta permitido, de lo contrario redireciona a otra
 * pagina
 */
@Named
@ViewScoped
public class MainTemplateControler implements Serializable {

    @PostConstruct
    public void inicio() {

    }

    public void checkAndShow() throws IOException {

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }

    public String out() {
        System.out.println("logOut");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "../index.xhtml";

    }

    public String goToCreateRecipe() {

        System.out.println("ENTRA IU");

        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 2) {
            System.out.println("NIVEL MENOR");

            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("ERROR", "No tienes nivel para acceder a esta parte, necesitas nivel 2. Tu nivel actual: " + ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv()));

            return "";

        } else {
            return "createRecipe.xhtml";
        }

    }
    public String goToAdmin() {

        System.out.println("ENTRA Isdcfvbnm,");

        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 100) {
            System.out.println("NIVEL MENOR");

            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("ERROR", "No tienes nivel para acceder a esta parte, necesitas nivel 100. Tu nivel actual: " + ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv()));

            return "";

        } else {
            return "admin.xhtml";
        }

    }

}
