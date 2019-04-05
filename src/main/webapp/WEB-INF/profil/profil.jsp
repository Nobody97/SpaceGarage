<%-- 
    Document   : profil
    Created on : 31.03.2019, 14:32:36
    Author     : florianhess
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Aufgaben
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/task/new/"/>">Reparaturauftrag anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" accept-charset="ISO-8859-1" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">
                    <div class ="profil">
                    <%-- Eingabefelder --%>
                    <label for="profilFirstname">
                        
                        Vorname:
                        <span class="required">*</span>
                         <input type="text" name="profilFirstname" value = "${user.firstname}">
                    </label>
             

                    <label for="profilLastname">
                        Name:
                        <span class="required">*</span>
                        <input type="text" name="profilLastname" value="${user.lastname}">

                    </label>
                        <br></br>
                    <%-- Button zum Abschicken --%>
                    <center>
                    <button type="submit" class="profilSave">
                        Speichern
                    </button>
                    </center>
                    </div>
                   
                </div>
            </form>

    </jsp:attribute>
</template:base>
