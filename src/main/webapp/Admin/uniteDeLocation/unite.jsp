<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<%
    List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Unités de Location</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">🏢 Liste des unités de location</h2>
    <a href="${pageContext.request.contextPath}/uniteDeLocation?action=add" class="btn btn-success mb-3">➕ Ajouter</a>
    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Code</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Pièces</th>
            <th>Loyer Mensuel</th>
            <th>Disponible</th>
            <th>Immeuble</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (UniteDeLocation u : unites) { %>
        <tr>
            <td><%= u.getCodeUnite() %></td>
            <td><%= u.getNom()%></td>
            <td><%= u.getDescription() %></td>
            <td><%= u.getNbrPieces() %></td>
            <td><%= u.getLoyerMensuel() %> CFA</td>
            <td><%= u.isDisponible() ? "Disponible" : "Indiponible" %></td>
            <td><%= u.getImmeuble().getNom() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/uniteDeLocation?action=edit&id=<%=u.getId()%>" class="btn btn-warning btn-sm">✏</a>
                <a href="${pageContext.request.contextPath}/uniteDeLocation?action=delete&id=<%=u.getId()%>" class="btn btn-danger btn-sm"
                   onclick="return confirm('Supprimer cette unité ?')">🗑</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">⬅ Retour</a>
</div>
</body>
</html>

