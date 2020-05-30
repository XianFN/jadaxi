package controlador;

import EJB.UserFacadeLocal;
import jadaxi.Email;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author jadaxi
 */
@Named
@ViewScoped
public class RegisterConroler implements Serializable {

    private User user;
    @EJB
    private UserFacadeLocal userEJB;

    private boolean skip;

    @PostConstruct
    public void inicio() {
        try {

            user = new User();

        } catch (Exception e) {
        }

    }

    public void info() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @param user
     * @return 
     * 
     * Comprovamos que el nombre de suaurio no esta repetido
     */
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
     * @return 
     * 
     * Insertamos el usuario en la bbdd
     */
    public String insertUser() {

        if (checkUserName(user.getUserName())) {

            user.setAcCode(genCode());

            System.out.println("asdasd: " + user.getAcCode());
            try {

                userEJB.create(user);

            } catch (Exception e) {
                System.out.println("Error al insertar el usuario: " + e.getMessage());
            }
            Email email = new Email(user.getEmail(), "Confirmación de cuenta", "Para conformar tu cuenta, introduce el siguiente codigo en la pagina http://cocinalotodo.servebeer.com/cocinalotodo/  : \n Codigo:  \n " + user.getAcCode());
            try {
                email.send();
            } catch (IOException ex) {
                System.out.println("Error al enviar el email al usuario: " + ex.getMessage());
            }

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("code", user);
            return "activate.xhtml?faces-redirect=true";
        }
        return "";

    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    /**
     * 
     * @return 
     * 
     * Formatemamos la forma de ver la fecha
     */
    public String birthdateFormat() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(user.getBirthDate());
    }
}
