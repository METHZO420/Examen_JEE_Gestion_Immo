<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 05:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>

<%
    Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier l’Utilisateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>✏ Modifier l’Utilisateur</h2>

    <form action="utilisateur" method="post">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="id" value="<%= utilisateur.getId() %>"/>

        <div class="mb-3">
            <label>Matricule :</label>
            <input type="text" name="matricule" value="<%= utilisateur.getMatricule() %>" class="form-control" required readonly/>
        </div>
        <div class="mb-3">
            <label>Nom :</label>
            <input type="text" name="nom" value="<%= utilisateur.getNom() %>" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Prénom :</label>
            <input type="text" name="prenom" value="<%= utilisateur.getPrenom() %>" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Email :</label>
            <input type="email" name="email" value="<%= utilisateur.getEmail() %>" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Mot de passe :</label>
            <input type="password" name="password" value="<%= utilisateur.getPassword() %>" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Rôle :</label>
            <select name="role" class="form-control"  required>
                <option value="ADMIN" <%= utilisateur.getRole() == Utilisateur.Role.ADMIN ? "selected" : "" %>>ADMIN</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Mettre à jour</button>
        <a href="utilisateurs" class="btn btn-secondary">⬅ Retour à la liste</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

