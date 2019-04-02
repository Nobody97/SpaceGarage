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

/**
 *
 * @author florianhess
 */

import dhbwka.wwi.vertsys.javaee.spacegarage.common.jpa.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "profil_ids")
    @TableGenerator(name = "profil_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Profil muss einem Benutzer zugeordnet werden.")
    private User owner;

    public Profil (User owner) {
        this.owner = owner;
    }
    
    public Profil() {
        
    }
    
    public void setUser(User owner){
        this.owner = owner;
    }
    
    public User getUser(){
        return owner;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public long getId(){
        return id;
    }
}

