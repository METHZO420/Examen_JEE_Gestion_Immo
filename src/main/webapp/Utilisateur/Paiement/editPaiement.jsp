<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Paiement" %>
<html>
<head>
    <title>Modifier Paiement</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="container mt-4">

<%
    Paiement paiement = (Paiement) request.getAttribute("paiement");
%>

<h2>Modifier Paiement</h2>

<form method="post" action="${pageContext.request.contextPath}/paiement">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%= paiement.getId() %>"/>

    <div class="mb-3">
        <label>Date Paiement</label>
        <input type="date" name="datePaiement" class="form-control"
               value="<%= paiement.getDatePaiement() %>" required/>
    </div>

    <div class="mb-3">
        <label>Montant</label>
        <input type="number" step="0.01" name="montant" class="form-control"
               value="<%= paiement.getMontant() %>" required/>
    </div>

    <div class="mb-3">
        <label>Status</label>
        <select name="status" class="form-control">
            <option value="EN_ATTENTE" <%= paiement.getStatus().toString().equals("EN_ATTENTE") ? "selected" : "" %>>EN_ATTENTE</option>
            <option value="PAYER" <%= paiement.getStatus().toString().equals("PAYER") ? "selected" : "" %>>PAYER</option>
            <option value="EN_RETARD" <%= paiement.getStatus().toString().equals("EN_RETARD") ? "selected" : "" %>>EN_RETARD</option>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">Mettre à jour</button>
    <a href="${pageContext.request.contextPath}/paiement" class="btn btn-secondary">Annuler</a>
</form>

</body>
</html>

