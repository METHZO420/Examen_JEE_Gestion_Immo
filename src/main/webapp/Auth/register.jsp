<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

<div class="card shadow-lg p-4" style="width: 450px; border-radius: 15px;">
    <h3 class="text-center mb-3 text-success">Créer un compte</h3>



    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="register"/>

        <div class="mb-3">
            <label class="form-label">Nom</label>
            <input type="text" name="nom" class="form-control" placeholder="Votre nom" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Prénom</label>
            <input type="text" name="prenom" class="form-control" placeholder="Votre prénom" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" placeholder="Votre email" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Mot de passe</label>
            <input type="password" name="password" class="form-control" placeholder="Choisissez un mot de passe" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Rôle</label>
            <select name="role" class="form-select" required>
                <option value="">-- Sélectionner un rôle --</option>
                <option value="LOCATAIRE">Locataire</option>
                <option value="PROPRIETAIRE">Propriétaire</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success w-100">S'inscrire</button>
    </form>

    <p class="text-center mt-3 mb-0">
        Déjà un compte ?
        <a href="${pageContext.request.contextPath}/auth?action=login" class="text-decoration-none">Se connecter</a>
    </p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
