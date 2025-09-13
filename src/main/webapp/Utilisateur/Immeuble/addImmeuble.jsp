<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>

<%
    List<Utilisateur> proprietaires = (List<Utilisateur>) request.getAttribute("proprietaires");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title> Ajouter un immeuble</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">🏢 Ajouter un immeuble</h2>

    <form action="${pageContext.request.contextPath}/immeuble" method="post" class="border rounded p-4 shadow-sm bg-light">
        <input type="hidden" name="action" value="add" />

        <div class="mb-3">
            <label class="form-label">Nom :</label>
            <input type="text" name="nom" class="form-control" placeholder="Ex: Immeuble A" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Adresse :</label>
            <input type="text" name="adresse" class="form-control" placeholder="Ex: Dakar Plateau" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Équipement :</label>
            <input type="text" name="equipement" class="form-control" placeholder="Ex: Ascenseur, Parking" />
        </div>

        <div class="form-check mb-3">
            <input type="checkbox" name="disponible" id="disponible" class="form-check-input" />
            <label for="disponible" class="form-check-label">Disponible</label>
        </div>

        <% if (proprietaires != null && !proprietaires.isEmpty()) { %>
        <div class="mb-3">
            <label class="form-label">Propriétaire :</label>
            <select name="proprietaireId" class="form-select">
                <option value="">-- Sélectionner un propriétaire --</option>
                <% for (Utilisateur u : proprietaires) { %>
                <option value="<%= u.getId() %>">
                    <%= u.getNom() %> <%= u.getPrenom() %> (<%= u.getEmail() %>)
                </option>
                <% } %>
            </select>
        </div>
        <% } %>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">💾 Enregistrer</button>
            <a href="${pageContext.request.contextPath}/immeuble" class="btn btn-secondary">⬅ Retour à la liste</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
