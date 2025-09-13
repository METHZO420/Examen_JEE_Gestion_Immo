
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<%
    List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un contrat</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Nouveau Contrat</h2>

    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/contrat" method="post" class="border p-4 rounded shadow-sm bg-white">
        <input type="hidden" name="action" value="add">

        <div class="mb-3">
            <label class="form-label">Unité de location :</label>
            <select name="uniteId" class="form-select" required>
                <option value="">-- Sélectionner une unité --</option>
                <% for (UniteDeLocation u : unites) { %>
                <option value="<%=u.getId()%>"><%=u.getNom()%> - <%=u.getLoyerMensuel()%> €/mois</option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Date de début :</label>
            <input type="date" name="dateDebut" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Date de fin :</label>
            <input type="date" name="dateFin" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Montant du loyer (€) :</label>
            <input type="number" step="0.01" name="montantLoyer" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="${pageContext.request.contextPath}/contrat" class="btn btn-secondary">⬅ Retour</a>
    </form>
</div>
</body>
</html>

