<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 05:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.ContratDeLocation" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>

<html>
<head>
    <title>Modifier Contrat</title>
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
<h2>Modifier Contrat</h2>

<%
    ContratDeLocation contrat = (ContratDeLocation) request.getAttribute("contrat");
%>

<form action="<%=request.getContextPath()%>/contrat" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%=contrat.getId()%>"/>

    <label>Code :</label>
    <input type="text" name="codeContrat" value="<%=contrat.getCodeContrat()%>" readonly/><br/>

    <label>Date Début :</label>
    <input type="date" name="dateDebut" value="<%=contrat.getDateDebut()%>" required/><br/>

    <label>Date Fin :</label>
    <input type="date" name="dateFin" value="<%=contrat.getDateFin()%>" required/><br/>

    <label>Loyer :</label>
    <input type="number" step="0.01" name="montantLoyer" value="<%=contrat.getMontantLoyer()%>" required/><br/>

    <label>Unité :</label>
    <select name="uniteId" required>
        <%
            List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
            for (UniteDeLocation u : unites) {
        %>
        <option value="<%=u.getId()%>" <%= (contrat.getUniteDeLocation()!=null && contrat.getUniteDeLocation().getId()==u.getId()) ? "selected" : "" %>>
            <%=u.getNom()%>
        </option>
        <% } %>
    </select><br/>

    <label>Locataire :</label>
    <select name="locataireId" required>
        <%
            List<Utilisateur> locataires = (List<Utilisateur>) request.getAttribute("locataires");
            for (Utilisateur l : locataires) {
        %>
        <option value="<%=l.getId()%>" <%= (contrat.getLocataire()!=null && contrat.getLocataire().getId()==l.getId()) ? "selected" : "" %>>
            <%=l.getNom()%> <%=l.getPrenom()%>
        </option>
        <% } %>
    </select><br/>

    <button type="submit">Mettre à jour</button>
</form>

</body>
</html>
