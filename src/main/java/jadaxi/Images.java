/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jadaxi;

import EJB.RecipeFacadeLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Recipe;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Javier
 */
@ManagedBean
@ApplicationScoped
public class Images implements Serializable {

    @EJB
    private RecipeFacadeLocal recipeEJB;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            System.out.println("render 1");
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String reciepId = context.getExternalContext().getRequestParameterMap().get("recipeId");
            System.out.println("render 2");
            Recipe rec = recipeEJB.find(Integer.valueOf(reciepId));

            return new DefaultStreamedContent(new ByteArrayInputStream(rec.getImage()));
        }
    }

}
