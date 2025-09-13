<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 05:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.ContratDeLocation" %>

<html>
<head>
    <title>Liste des Contrats</title>
</head>
<body>
<h2>Liste des Contrats</h2>
<a href="<%=request.getContextPath()%>/contrat?action=add">➕ Nouveau Contrat</a>
<table border="1" width="100%">
    <tr>
        <th>ID</th>
        <th>Code</th>
        <th>Date Début</th>
        <th>Date Fin</th>
        <th>Loyer</th>
        <th>Unité</th>
        <th>Locataire</th>
        <th>Actions</th>
    </tr>
    <%
        List<ContratDeLocation> contrats = (List<ContratDeLocation>) request.getAttribute("contrats");
        if (contrats != null) {
            for (ContratDeLocation contrat : contrats) {
    %>
    <tr>
        <td><%=contrat.getId()%></td>
        <td><%=contrat.getCodeContrat()%></td>
        <td><%=contrat.getDateDebut()%></td>
        <td><%=contrat.getDateFin()%></td>
        <td><%=contrat.getMontantLoyer()%></td>
        <td><%= contrat.getUniteDeLocation() != null ? contrat.getUniteDeLocation().getNom() : "N/A" %></td>
        <td><%= contrat.getLocataire() != null ? contrat.getLocataire().getNom() : "N/A" %></td>
        <td>
            <a href="<%=request.getContextPath()%>/contrat?action=edit&id=<%=contrat.getId()%>">Modifier</a>
            <a href="<%=request.getContextPath()%>/contrat?action=delete&id=<%=contrat.getId()%>"
               onclick="return confirm('Supprimer ce contrat ?')">Supprimer</a>
        </td>
    </tr>
    <%      }
    }
    %>
</table>
</body>
</html>
