<%--
  Created by IntelliJ IDEA.
  User: Mouhamed Basse
  Date: 12/09/2025
  Time: 01:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tableau de bord - Admin</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #2c3e50;
            color: white;
            padding: 1rem;
            text-align: center;
        }
        nav {
            background-color: #34495e;
            padding: 1rem;
            display: flex;
            justify-content: space-around;
        }
        nav a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }
        nav a:hover {
            text-decoration: underline;
        }
        main {
            padding: 2rem;
        }
        .card-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 1.5rem;
        }
        .card {
            background-color: white;
            padding: 1rem;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            text-align: center;
        }
        .logout-btn {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            cursor: pointer;
        }
        .logout-btn:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

<header>
    <h1>Tableau de bord - Administration</h1>
    <p>Bienvenue, <strong>${user.nom} ${user.prenom}</strong> (${user.role})</p>
</header>

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

<main>
    <h2>Statistiques rapides</h2>
    <div class="card-container">
        <div class="card">
            <h3>Total des immeubles</h3>
            <p>${totalImmeubles}</p>
        </div>
        <div class="card">
            <h3>Total des utilisateurs</h3>
            <p>${totalUtilisateurs}</p>
        </div>
        <div class="card">
            <h3>Contrats actifs</h3>
            <p>${contratsActifs}</p>
        </div>
        <div class="card">
            <h3>Paiements du mois</h3>
            <p>${paiementsMois}</p>
        </div>
        <div class="card">
            <h3>Somme totale des paiements</h3>
            <p>${sommePaiements} FCFA</p>
        </div>
    </div>
</main>

</body>
</html>

