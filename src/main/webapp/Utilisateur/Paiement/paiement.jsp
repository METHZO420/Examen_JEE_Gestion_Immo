
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Paiement" %>

<html>
<head>
    <title>Liste des Paiements</title>
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/immeuble">Gestion des immeubles</a>
    <a href="${pageContext.request.contextPath}/utilisateur">Gestion des utilisateurs</a>
    <a href="${pageContext.request.contextPath}/contrat">Contrats de location</a>
    <a href="${pageContext.request.contextPath}/uniteDeLocation">Unite de location</a>
    <a href="${pageContext.request.contextPath}/paiement">Paiements</a>
    <form action="auth" method="get" style="display:inline;">
        <input type="hidden" name="action" value="logout"/>
        <button type="submit" class="logout-btn">Déconnexion</button>
    </form>
</nav>
<h2>Liste des Paiements</h2>

<form method="get" action="<%=request.getContextPath()%>/paiement">
    <input type="text" name="filter" placeholder="Rechercher par code paiement"
           value="<%= request.getParameter("filter") != null ? request.getParameter("filter") : "" %>"/>
    <button type="submit">Filtrer</button>
    <a href="<%=request.getContextPath()%>/paiement">Réinitialiser</a>
</form>

<br/>

<a href="<%=request.getContextPath()%>/paiement?action=add"> Ajouter un Paiement</a>

<br/><br/>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Code Paiement</th>
        <th>Date Paiement</th>
        <th>Montant</th>
        <th>Status</th>
        <th>Contrat</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        Object obj = request.getAttribute("paiements");
        if (obj != null) {
            List<Paiement> paiements = (List<Paiement>) obj;
            if (paiements.isEmpty()) {
    %>
    <tr><td colspan="7">Aucun paiement trouvé</td></tr>
    <%
    } else {
        for (Paiement p : paiements) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getCodePaiement() %></td>
        <td><%= p.getDatePaiement() %></td>
        <td><%= p.getMontant() %></td>
        <td><%= p.getStatus() %></td>
        <td><%= p.getContratDeLocation() != null ? p.getContratDeLocation().getCodeContrat() : "-" %></td>
        <td>
            <a href="<%=request.getContextPath()%>/paiement?action=edit&id=<%=p.getId()%>">Modifier</a> |
            <a href="<%=request.getContextPath()%>/paiement?action=delete&id=<%=p.getId()%>"
               onclick="return confirm('Voulez-vous vraiment supprimer ce paiement ?');">Supprimer</a>
        </td>
    </tr>
    <%
                }
            }
        }
    %>
    </tbody>
</table>

</body>
</html>




