<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 05:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Ajouter un contrat</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
</head>
<body class="container mt-5">

<h2>Ajouter un contrat</h2>

<form action="<%=request.getContextPath()%>/contrat" method="post">
    <input type="hidden" name="action" value="add">

    <div class="mb-3">
        <label class="form-label">Date début :</label>
        <input type="date" name="dateDebut" class="form-control" required>
    </div>

    <div class="mb-3">
        <label class="form-label">Date fin :</label>
        <input type="date" name="dateFin" class="form-control" required>
    </div>

    <div class="mb-3">
        <label class="form-label">Montant du loyer :</label>
        <input type="number" name="montantLoyer" step="0.01" class="form-control" required>
    </div>

    <div class="mb-3">
        <label class="form-label">Unité de location :</label>
        <select name="uniteId" class="form-select" required>
            <option value="">-- Sélectionner une unité --</option>
            <%
                List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
                if (unites != null) {
                    for (UniteDeLocation u : unites) {
            %>
            <option value="<%=u.getId()%>">
                <%=u.getNom()%> - <%=u.getLoyerMensuel()%> €/mois
            </option>
            <%
                    }
                }
            %>
        </select>
    </div>

    <div class="mb-3">
        <label class="form-label">Locataire :</label>
        <input type="text" class="form-control" value="<%= ((Utilisateur)request.getAttribute("locataire")).getNom() %>" readonly>
    </div>

    <button type="submit" class="btn btn-success">Enregistrer</button>
</form>

</body>
</html>


