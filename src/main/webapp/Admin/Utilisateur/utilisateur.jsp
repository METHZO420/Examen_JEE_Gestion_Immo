<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 05:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>

<%
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/immeuble">Gestion des immeubles</a>
    <a href="${pageContext.request.contextPath}/utilisateur">Gestion des utilisateurs</a>
    <a href="${pageContext.request.contextPath}/contrat">Contrats de location</a>
    <a href="${pageContext.request.contextPath}/uniteDeLocation">Unite de location</a>
    <a href="${pageContext.request.contextPath}/paiement">Paiements</a>
    <form action="auth" method="get" style="display:inline;">
        <input type="hidden" name="action" value="logout"/>
        <button type="submit" class="logout-btn">Déconnexion</button>
    </form>
</nav>
<div class="container mt-5">
    <h2>👤 Gestion des Utilisateurs</h2>

    <!-- Bouton d'ajout -->
    <p>
        <a href="utilisateur?action=add" class="btn btn-success"> Ajouter un utilisateur</a>
    </p>

    <% if (utilisateurs == null || utilisateurs.isEmpty()) { %>
    <div class="alert alert-info">Aucun utilisateur trouvé.</div>
    <% } else { %>
    <table class="table table-bordered table-hover">
        <thead class="table-primary">
        <tr>
            <th>ID</th>
            <th>Matricule</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Rôle</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Utilisateur u : utilisateurs) { %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getMatricule() %></td>
            <td><%= u.getNom() %></td>
            <td><%= u.getPrenom() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getRole() %></td>
            <td>
                <a href="utilisateur?action=edit&id=<%= u.getId() %>" class="btn btn-sm btn-warning">✏ Modifier</a>
                <a href="utilisateur?action=delete&id=<%= u.getId() %>" class="btn btn-sm btn-danger"
                   onclick="return confirm('Supprimer cet utilisateur ?');">🗑 Supprimer</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

