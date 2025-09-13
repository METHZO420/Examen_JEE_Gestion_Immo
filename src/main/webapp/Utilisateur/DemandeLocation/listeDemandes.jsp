
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.DemandeLocation" %>
<%
    List<DemandeLocation> demandes = (List<DemandeLocation>) request.getAttribute("demandes");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes demandes de location</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2> Mes demandes de location</h2>
    <table class="table table-bordered table-hover mt-3">
        <thead class="table-light">
        <tr>
            <th>Locataire</th>
            <th>Unité</th>
            <th>Date</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (demandes != null) {
            for (DemandeLocation d : demandes) { %>
        <tr>
            <td><%= d.getLocataire().getNom() %></td>
            <td><%= d.getUnite().getNom() %> (<%= d.getUnite().getImmeuble().getNom() %>)</td>
            <td><%= d.getDateDemande() %></td>
            <td>
                <% if (d.getStatut() == DemandeLocation.StatutDemande.EN_ATTENTE) { %>
                <span class="badge bg-warning text-dark">En attente</span>
                <% } else if (d.getStatut() == DemandeLocation.StatutDemande.ACCEPTEE) { %>
                <span class="badge bg-success">Acceptée</span>
                <% } else { %>
                <span class="badge bg-danger">Rejetée</span>
                <% } %>
            </td>
            <td>
                <% if (d.getStatut() == DemandeLocation.StatutDemande.EN_ATTENTE) { %>
                <a href="${pageContext.request.contextPath}/demande?action=update&id=<%=d.getId()%>&statut=ACCEPTEE"
                   class="btn btn-success btn-sm">✔ Accepter</a>
                <a href="${pageContext.request.contextPath}/demande?action=update&id=<%=d.getId()%>&statut=REFUSEE"
                   class="btn btn-danger btn-sm">✖ Refuser</a>
                <% } %>
            </td>
        </tr>
        <% }} %>
        </tbody>
    </table>
</div>
</body>
</html>

