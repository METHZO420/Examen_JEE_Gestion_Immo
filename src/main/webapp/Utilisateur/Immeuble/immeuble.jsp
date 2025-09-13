<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Immeuble" %>

<%
    List<Immeuble> immeubles = (List<Immeuble>) request.getAttribute("immeubles");
%>

<!DOCTYPE html>
<html>
<head>
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
    <meta charset="UTF-8">
    <title>Liste des Immeubles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2> Gestion des Immeubles</h2>

    <!-- Barre de recherche -->
    <form action="<%= request.getContextPath() %>/immeuble" method="get" class="mb-3">
        <input type="hidden" name="action" value="search"/>
        <input type="text" name="keyword" placeholder="Rechercher..." class="form-control w-50 d-inline-block"/>
        <button type="submit" class="btn btn-primary">Chercher</button>
    </form>

    <!-- Bouton d'ajout -->
    <p>
        <a href="immeuble?action=add" class="btn btn-success"> Ajouter un immeuble</a>
    </p>

    <% if (immeubles == null || immeubles.isEmpty()) { %>
    <div class="alert alert-info">Aucun immeuble trouvé.</div>
    <% } else { %>
    <table class="table table-bordered table-hover">
        <thead class="table-primary">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Adresse</th>
            <th>Équipement</th>
            <th>Disponible</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Immeuble im : immeubles) { %>
        <tr>
            <td><%= im.getId() %></td>
            <td><%= im.getNom() %></td>
            <td><%= im.getAdresse() %></td>
            <td><%= im.getEquipement() %></td>
            <td><%= im.isDisponible() ? "Disponible" : "Indisponible" %></td>
            <td>
                <a href="immeuble?action=edit&id=<%= im.getId() %>"
                   class="btn btn-sm btn-warning">✏ Modifier</a>
                <a href="immeuble?action=delete&id=<%= im.getId() %>"
                   class="btn btn-sm btn-danger" onclick="return confirm('Supprimer cet immeuble ?');">🗑 Supprimer</a>
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
