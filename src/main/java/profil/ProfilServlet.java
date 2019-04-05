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

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import dhbwka.wwi.vertsys.javaee.spacegarage.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.spacegarage.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.spacegarage.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.spacegarage.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.spacegarage.common.web.WebUtils;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author florianhess
 */
@WebServlet(urlPatterns = "/app/profil/profil/")
public class ProfilServlet extends HttpServlet {

    @EJB
    ProfilBean profilBean;

    @EJB
    UserBean userBean;
    
    
    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        


        // Anfrage an die JSP weiterleiten
        //request.setAttribute("user", this.user.getFirstname());
        //request.setAttribute("user", this.user.getLastname());
        request.setAttribute("user", this.userBean.getCurrentUser());
        request.getRequestDispatcher("/WEB-INF/profil/profil.jsp").forward(request, response);
        
        
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    User user = userBean.getCurrentUser();
        
        String firstname = request.getParameter("profilFirstname");
        String lastname = request.getParameter("profilLastname");
        user.setFirstname(firstname);
        user.setLastname(lastname);
        userBean.update(user);
        response.sendRedirect(WebUtils.appUrl(request, "/app/profil/profil/"));
        
        
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveProfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        
        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        
        String profilFirstname = request.getParameter("profilFirstname");
        String profilLastname = request.getParameter("profilLastname");
        
        Profil profil = this.getRequestedProfil(request);


        if(profilFirstname != null) {
            profil.getUser().setFirstname(profilFirstname);
        }
        
        if(profilLastname != null) {
            profil.getUser().setLastname(profilLastname);
        }
      

        this.validationBean.validate(profil, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.profilBean.update(profil);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/profil/profil/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("profil_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteProfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Profil profil = this.getRequestedProfil(request);
        this.profilBean.delete(profil);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/profil/profil/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Profil getRequestedProfil(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Profil profil = new Profil();
        profil.setUser(this.userBean.getCurrentUser());
       

        // ID aus der URL herausschneiden
        String profilId = request.getPathInfo();

        if (profilId == null) {
            profilId = "";
        }

        profilId = profilId.substring(1);

        if (profilId.endsWith("/")) {
            profilId = profilId.substring(0, profilId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            profil = this.profilBean.findById(Long.parseLong(profilId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return profil;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param profil Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createProfilForm(Profil profil) {
        Map<String, String[]> values = new HashMap<>();

        values.put("profil_owner", new String[]{
            profil.getUser().getUsername()
        });


        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
