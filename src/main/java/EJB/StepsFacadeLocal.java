/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Steps;

/**
 *
 * @author Javier
 */
@Local
public interface StepsFacadeLocal {

    void create(Steps steps);

    void edit(Steps steps);

    void remove(Steps steps);

    Steps find(Object id);

    List<Steps> findAll();

    List<Steps> findRange(int[] range);

    int count();
    
}
