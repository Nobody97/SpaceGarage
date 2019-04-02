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
        <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">
                
                    <%-- Eingabefelder --%>
                    <label for="j_firstname">
                        
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="profilFirstname" value = "${user.firstname}">

                    <label for="j_lastname">
                        Name:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="profilLastname" value="${user.lastname}">

                    <%-- Button zum Abschicken --%>
                    <button type="submit">
                        Speichern
                    </button>
                </div>
            </form>

    </jsp:attribute>
</template:base>
