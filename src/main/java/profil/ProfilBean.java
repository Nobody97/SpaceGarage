/*
 * Copyright © 2019 Emil Schilberg & Florian Heß
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package profil;

import dhbwka.wwi.vertsys.javaee.spacegarage.common.ejb.EntityBean;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author florianhess
 */


@Stateless
@RolesAllowed("app-user")
public class ProfilBean extends EntityBean<Profil, Long> { 
   
    @PersistenceContext
    EntityManager em;
    
    public ProfilBean() {
        super(Profil.class);
    }
  
    public Profil update(Profil profil){
        return em.merge(profil);
    }

}

