
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.ContratDeLocation" %>
<%
    ContratDeLocation contrat = (ContratDeLocation) request.getAttribute("contrat");
    List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier contrat</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">✏ Modifier Contrat</h2>

    <form action="${pageContext.request.contextPath}/contrat" method="post" class="border p-4 rounded shadow-sm bg-white">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%=contrat.getId()%>">

        <div class="mb-3">
            <label class="form-label">Code Contrat :</label>
            <input type="text" name="codeContrat" value="<%=contrat.getCodeContrat()%>" class="form-control" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">Unité de location :</label>
            <select name="uniteId" class="form-select" required>
                <% for (UniteDeLocation u : unites) { %>
                <option value="<%=u.getId()%>" <%= (u.getId() == contrat.getUniteDeLocation().getId()) ? "selected" : "" %>>
                    <%=u.getNom()%> - <%=u.getLoyerMensuel()%> €/mois
                </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Date de début :</label>
            <input type="date" name="dateDebut" value="<%=contrat.getDateDebut()%>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Date de fin :</label>
            <input type="date" name="dateFin" value="<%=contrat.getDateFin()%>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Montant du loyer (€) :</label>
            <input type="number" step="0.01" name="montantLoyer" value="<%=contrat.getMontantLoyer()%>" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-warning">💾 Mettre à jour</button>
        <a href="${pageContext.request.contextPath}/contrat" class="btn btn-secondary">⬅ Retour</a>
    </form>
</div>
</body>
</html>

