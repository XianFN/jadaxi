package controlador;

import EJB.UserFacadeLocal;
import jadaxi.Email;
import java.io.IOException;

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
 * @author jadaxi
 */
@Named
@ViewScoped
public class ProfileControler implements Serializable {

    private User us;
    private int progreso;

    private String name;
    private String surname1;
    private String surname2;
    private Date date;
    private String about;
    private String userName;
    private String password;
    private String email;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {

        us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        System.out.println("us:" + us.toString());

        int level = us.getLv();
        int exp = (int) us.getXp();
        progreso = exp % 100;

        userName = us.getUserName();
        email = us.getEmail();

        name = us.getName();
        surname1 = us.getSurname();
        surname2 = us.getSurname2();
        date = us.getBirthDate();
        about = us.getAbaut();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * 
     * Modificamos los datos baiscos del usuario sin ninguna comprobacion
     */
    public void modifyData() {

        System.out.println(this.name + " " + this.surname1 + " " + this.surname2 + " " + this.date.toString() + " " + this.about);

        us.setName(this.name);
        us.setSurname(this.surname1);
        us.setSurname2(this.surname2);
        us.setBirthDate(this.date);
        us.setAbaut(this.about);

        userEJB.edit(us);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Datos personales cambiados correctamente"));

    }

    /**
     * 
     * @param user
     * @return 
     * 
     * Comprobamos si el usuario existe
     */
    private boolean checkUserName(String user) {
        System.out.println("aspoidjpaisdu'paosudjp`:" + user);
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
     * Modificamos el nombre de usuario despues de comprobar que no esta repetido
     */
    public void modifyUserName() {

        if (checkUserName(this.userName)) {

            us.setUserName(this.userName);

            userEJB.edit(us);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Nombre de usuario cambiado correctamente"));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", userEJB.find(us));

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Nombre de usuario ya existe"));
        }

    }

    /**
     * 
     * Modificamos la contraseña
     */
    public void modifyPassword() {
        
        System.out.println("password:" + this.password);
        us.setPassword(this.password);

        userEJB.edit(us);
        // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", userEJB.find(us));
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Contraseña cambiada"));

    }
    
    /**
     * 
     * @return 
     * 
     * Generamos un codigo aleatorio
     */
    private String genCode() {

        String alphabet = "1234567890";
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < 6) {
            int rand = (int) ((Math.random() * ((10 - 0))));// Con el random generamos la contraseña sacando los
            // careacteres del array alphabet
            char char2 = (char) rand;
            System.out.println("cahr: " + char2);
            sb.append(alphabet.charAt(char2));

            i++;
        }

        return sb.toString();
    }

    /**
     * 
     * @return 
     * 
     * Modificamos el email, desactivamos la cuenta y mandamos otro codigo de confirmacion
     */
    public String modifyEmail() {

        System.out.println("email: " + this.email);

        if (!(this.email.equals(us.getEmail()))) {
            System.out.println("NO IGUAL");
            us.setEmail(email);
            us.setAcCode(genCode());
            us.setActivated(false);
            userEJB.edit(us);

            try {
                Email email = new Email(us.getEmail(), "Confirmación de cuenta", "Para conformar tu cuenta, introduce el siguiente codigo en la pagina http://cocinalotodo.servebeer.com/cocinalotodo/  : \n Codigo:  \n " + us.getAcCode());

                email.send();
            } catch (IOException ex) {
                System.out.println("Error al enviar el email al usuario: " + ex.getMessage());
            }

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("code", us);
            return "/activate.xhtml";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No has actualizado tu correo"));

            System.out.println("IGUAL");
            return "";
        }

    }

}
