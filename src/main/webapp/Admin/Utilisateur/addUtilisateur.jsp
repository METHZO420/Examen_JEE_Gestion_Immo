<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.Utilisateur" %>

<%
    Utilisateur currentUser = (Utilisateur) session.getAttribute("user");
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
%>

<form action="${pageContext.request.contextPath}/immeuble" method="post">
    <input type="hidden" name="action" value="add" />

    <div class="mb-3">
        <label>Nom :</label>
        <input type="text" name="nom" class="form-control" required />
    </div>

    <div class="mb-3">
        <label>Adresse :</label>
        <input type="text" name="adresse" class="form-control" required />
    </div>

    <div class="mb-3">
        <label>Équipement :</label>
        <textarea name="equipement" class="form-control"></textarea>
    </div>

    <div class="form-check mb-3">
        <input class="form-check-input" type="checkbox" name="disponible" id="disponible">
        <label class="form-check-label" for="disponible">Disponible</label>
    </div>

    <% if (currentUser != null && "ADMIN".equals(currentUser.getRole())) { %>
    <div class="mb-3">
        <label>Propriétaire :</label>
        <select name="proprietaireId" class="form-control" required>
            <% if (utilisateurs != null) {
                for (Utilisateur u : utilisateurs) { %>
            <option value="<%= u.getId() %>"><%= u.getNom() %> /<%= u.getMatricule() %></option>
            <%     }
            } %>
        </select>
    </div>
    <% } else { %>
    <p><strong>Propriétaire :</strong> Vous-même (<%= currentUser.getNom() %> <%= currentUser.getPrenom() %>)</p>
    <% } %>

    <button type="submit" class="btn btn-primary">Enregistrer</button>
    <a href="${pageContext.request.contextPath}/immeuble" class="btn btn-secondary">⬅ Retour</a>
</form>
