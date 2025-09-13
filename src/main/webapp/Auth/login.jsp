<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

<div class="card shadow-lg p-4" style="width: 400px; border-radius: 15px;">
    <h3 class="text-center mb-3 text-primary">Connexion</h3>



    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="login"/>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" placeholder="Votre email" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Mot de passe</label>
            <input type="password" name="password" class="form-control" placeholder="Votre mot de passe" required>
        </div>

        <button type="submit" class="btn btn-primary w-100">Se connecter</button>
    </form>

    <p class="text-center mt-3 mb-0">
        Pas encore de compte ?
        <a href="${pageContext.request.contextPath}/auth?action=register" class="text-decoration-none">Créer un compte</a>
    </p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
