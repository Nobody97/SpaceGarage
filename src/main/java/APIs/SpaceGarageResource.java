/*
 * Copyright © 2019 Emil Schilberg & Florian Heß
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package APIs;

import dhbwka.wwi.vertsys.javaee.spacegarage.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.spacegarage.tasks.jpa.Task;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author florianhess
 */
@Path("/Reparatur/")
@Consumes("application/json")
@Produces("application/json")
public class SpaceGarageResource {
    
    @EJB
    private TaskBean taskBean;
    
    @GET
    @Path("{id}")
    public Task getTask(@PathParam("id") long id) {
        return this.taskBean.findById(id);
    }
    
    @GET
    public List<Task> alleReparaturen(){
        return this.taskBean.findAll();
    }
}
