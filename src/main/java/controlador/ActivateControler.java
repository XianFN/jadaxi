package controlador;

import EJB.UserFacadeLocal;
import jadaxi.Email;
import java.io.IOException;
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
 */
@Named
@ViewScoped
public class ActivateControler implements Serializable {

    User us;

    String introducedCode;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void inicio() {
        us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("code");
    }

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public String getIntroducedCode() {
        return introducedCode;
    }

    public void setIntroducedCode(String introducedCode) {
        this.introducedCode = introducedCode;
    }

    /**
     * 
     * @return 
     * 
     * Comprobamos que el codigo de activacion es correcto
     */
    public String check() {
        System.out.println("codigo que tiene que ser: " + us.getAcCode() + " VS " + "codigo introducido: " + introducedCode);

        if (introducedCode != null) {

            if (us.getAcCode().equals(introducedCode)) {

                us.setActivated(true);

                userEJB.edit(us);
                return "index.xhtml";
            } else {

                return "activate.xhtml";
            }
        }

        System.out.println("Introduzca el numero de activacion");
        return "activate.xhtml";
    }

    /**
     * 
     * @return
     * 
     * Generamos un codigo aleatorio
     */
    private String genCode() {

        String alphabet = "1234567890";
        StringBuilder password = new StringBuilder();

        int i = 0;
        while (i < 6) {
            int rand = (int) ((Math.random() * ((10 - 0))));// Con el random generamos la contraseña sacando los
            // careacteres del array alphabet
            char char2 = (char) rand;
            System.out.println("cahr: " + char2);
            password.append(alphabet.charAt(char2));

            i++;
        }

        return password.toString();
    }

    /**
     * 
     * Reenviamos el email al usuario
     */
    public void resentEmail() {

        us.setAcCode(genCode());

        try {
            userEJB.edit(us);
            Email email = new Email(us.getEmail(), "Confirmación de cuenta", "Para conformar tu cuenta, introduce el siguiente codigo en la pagina http://cocinalotodo.servebeer.com/cocinalotodo/  : \n Codigo:  \n " + us.getAcCode());
            email.send();
        } catch (IOException e) {
            System.out.println("Fallos al actualizar el email y gencode o reenviar el correo: " + e.getMessage());
        }

    }

}
