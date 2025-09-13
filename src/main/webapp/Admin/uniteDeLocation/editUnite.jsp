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
<%@ page import="com.example.examen_jee_gestion_immo.Models.Immeuble" %>
<%
    UniteDeLocation unite = (UniteDeLocation) request.getAttribute("unite");
    List<Immeuble> immeubles = (List<Immeuble>) request.getAttribute("immeubles");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une unité</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">✏ Modifier l'unité</h2>
    <form action="${pageContext.request.contextPath}/uniteDeLocation" method="post" class="border p-4 rounded shadow-sm bg-light">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= unite.getId() %>">
        <div class="mb-3">
            <div class="mb-3">
                <label class="form-label"> Nom</label>
                <input type="text" name="nom" class="form-control" value="<%= unite.getNom() %>" required/>
            </div>
            <label class="form-label">Code :</label>
            <input type="text" name="codeUnite" class="form-control" value="<%= unite.getCodeUnite() %>" required readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">Description :</label>
            <textarea name="description" class="form-control"><%= unite.getDescription() %></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Nombre de pièces :</label>
            <input type="number" name="nbrPieces" class="form-control" value="<%= unite.getNbrPieces() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Loyer mensuel :</label>
            <input type="number" step="0.01" name="loyerMensuel" class="form-control" value="<%= unite.getLoyerMensuel() %>" required>
        </div>
        <div class="form-check mb-3">
            <input type="checkbox" name="disponible" id="disponible" class="form-check-input"
                <%= unite.isDisponible() ? "checked" : "" %>>
            <label for="disponible" class="form-check-label">Disponible</label>
        </div>
        <div class="mb-3">
            <label class="form-label">Immeuble :</label>
            <select name="immeuble_id" class="form-select" required>
                <% for (Immeuble i : immeubles) { %>
                <option value="<%=i.getId()%>" <%= (unite.getImmeuble().getId() == i.getId()) ? "selected" : "" %>>
                    <%=i.getNom()%>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Mettre à jour</button>
        <a href="${pageContext.request.contextPath}/uniteDeLocation" class="btn btn-secondary">⬅ Retour</a>
    </form>
</div>
</body>
</html>

