
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.ContratDeLocation" %>
<%
    List<ContratDeLocation> contrats = (List<ContratDeLocation>) request.getAttribute("contrats");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes contrats</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">📜 Mes Contrats de Location</h2>

    <table class="table table-bordered table-striped bg-white shadow-sm">
        <thead class="table-dark">
        <tr>
            <th>Code Contrat</th>
            <th>Unité</th>
            <th>Date Début</th>
            <th>Date Fin</th>
            <th>Loyer (€)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (contrats != null && !contrats.isEmpty()) {
            for (ContratDeLocation c : contrats) { %>
        <tr>
            <td><%=c.getCodeContrat()%></td>
            <td><%=c.getUniteDeLocation().getNom()%></td>
            <td><%=c.getDateDebut()%></td>
            <td><%=c.getDateFin()%></td>
            <td><%=c.getMontantLoyer()%></td>
            <td>
                <a href="${pageContext.request.contextPath}/contrat?action=edit&id=<%=c.getId()%>" class="btn btn-sm btn-warning">✏ Modifier</a>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="6" class="text-center">Aucun contrat trouvé.</td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/contrat?action=add" class="btn btn-success"> Nouveau Contrat</a>
</div>
</body>
</html>
