s
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.examen_jee_gestion_immo.Models.UniteDeLocation" %>
<html>
<head>
    <title>Accueil - Unités de Location</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        .card-header {
            background: linear-gradient(135deg, #0d6efd, #4dabf7);
            color: white;
            font-weight: bold;
            text-align: center;
        }
        .price-tag {
            font-size: 1.2rem;
            font-weight: bold;
            color: #0d6efd;
        }
        .badge-dispo {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<!-- ✅ Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="<%= request.getContextPath() %>/accueil">🏠 ImmoGestion</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <!-- Liens à gauche -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="<%= request.getContextPath() %>/accueil">Accueil</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/contrat?mes=true">Mes Contrats</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/immeuble?mes=true">Mes Immeubles</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/demande?mes=true">Mes Demandes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/uniteDeLocation?mes=true">Mes Unités De Location</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/uniteDeLocation?mes=true">Gestion des Paiements</a>
                </li>
            </ul>

            <!-- Liens à droite -->
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link"
                       href="<%= request.getContextPath() %>/compte">Mon Compte</a>
                </li>
                <form action="auth" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="logout"/>
                    <button type="submit" class="logout-btn">Déconnexion</button>
                </form>
            </ul>
        </div>
    </div>
</nav>

<!-- ✅ Contenu principal -->
<div class="container mt-5">
    <h1 class="text-center mb-4 fw-bold">✨ Découvrez nos Unités de Location ✨</h1>

    <div class="row">
        <%
            List<UniteDeLocation> unites = (List<UniteDeLocation>) request.getAttribute("unites");
            if (unites != null && !unites.isEmpty()) {
                for (UniteDeLocation unite : unites) {
        %>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-header">
                    <%= unite.getNom() %>
                </div>
                <div class="card-body">
                    <p class="card-text"><strong>Description :</strong> <%= unite.getDescription() %></p>
                    <p class="card-text"><strong>Nombre de pièces :</strong> <%= unite.getNbrPieces() %></p>
                    <p class="card-text price-tag"><%= unite.getLoyerMensuel() %> € / mois</p>
                    <p>
                        <% if (unite.isDisponible()) { %>
                        <span class="badge bg-success badge-dispo">Disponible</span>
                        <% } else { %>
                        <span class="badge bg-danger badge-dispo">Indisponible</span>
                        <% } %>
                    </p>
                </div>
                <div class="card-footer text-center">
                    <% if (unite.isDisponible()) { %>
                    <a href="<%= request.getContextPath() %>/demande?action=add&uniteId=<%= unite.getId() %>"
                       class="btn btn-primary">📩 Faire une demande</a>
                    <% } else { %>
                    <button class="btn btn-secondary" disabled>Indisponible</button>
                    <% } %>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p class="text-center">Aucune unité disponible pour le moment.</p>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
