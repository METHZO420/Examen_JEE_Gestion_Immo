<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.ContratDeLocation" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Paiement" %>
<html>
<head>
    <title>Ajouter Paiement</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="container mt-4">

<h2>Ajouter un Paiement</h2>

<form method="post" action="${pageContext.request.contextPath}/paiement">
    <input type="hidden" name="action" value="add"/>

    <div class="mb-3">
        <label>Date Paiement</label>
        <input type="date" name="datePaiement" class="form-control" required/>
    </div>

    <div class="mb-3">
        <label>Contrat</label>
        <select name="contratId" class="form-control" required>
            <%
                List<ContratDeLocation> contrats = (List<ContratDeLocation>) request.getAttribute("contrats");
                if (contrats != null) {
                    for (ContratDeLocation c : contrats) {
            %>
            <option value="<%= c.getId() %>">Contrat #<%= c.getId() %></option>
            <%
                    }
                }
            %>
        </select>
    </div>

    <div class="mb-3">
        <label>Status</label>
        <select name="status" class="form-control">
            <option value="EN_ATTENTE">EN_ATTENTE</option>
            <option value="PAYER">PAYER</option>
            <option value="EN_RETARD">EN_RETARD</option>
        </select>
    </div>

    <button type="submit" class="btn btn-success">Ajouter</button>
    <a href="${pageContext.request.contextPath}/paiement" class="btn btn-secondary">Annuler</a>
</form>

</body>
</html>
