package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.UtilisateurImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UtilisateurImpl utilisateurImpl;


    @Override
    public void init() throws ServletException {
        utilisateurImpl = new UtilisateurImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect("Auth/login.jsp");

        } else if ("register".equals(action)) {
            req.getRequestDispatcher("Auth/register.jsp").forward(req, resp);

        } else {
            req.getRequestDispatcher("Auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("login".equals(action)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String contextPath = req.getContextPath();
            Utilisateur user = utilisateurImpl.login(email, password);
            System.out.println("on y est");
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                if (user.getRole() == Utilisateur.Role.ADMIN) {
                    System.out.println("Redirection vers : " + req.getContextPath() + "/Admin/Dashboard.jsp");
                    resp.sendRedirect(contextPath + "/dashboard");
                } else {
                    resp.sendRedirect(contextPath + "/accueil");
                }
            } else {
                req.setAttribute("error", "Email ou mot de passe incorrect !");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                System.out.println(req.getContextPath() + "/login.jsp");
            }

        } else if ("register".equals(action)) {
            try {
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String roleParam = req.getParameter("role"); // Peut être null ou "UTILISATEUR"

                HttpSession session = req.getSession(false);
                Utilisateur currentUser = (session != null) ? (Utilisateur) session.getAttribute("user") : null;

                Utilisateur.Role roleToAssign;

                if (roleParam != null && roleParam.equalsIgnoreCase("ADMIN")) {

                    roleToAssign = Utilisateur.Role.ADMIN;
                } else {
                    roleToAssign = Utilisateur.Role.UTILISATEUR;
                }

                String matricule = "MAT-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                Utilisateur newUser = new Utilisateur();
                newUser.setMatricule(matricule);
                newUser.setNom(nom);
                newUser.setPrenom(prenom);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setRole(roleToAssign);

                // 4️⃣ Sauvegarde
                utilisateurImpl.add(newUser);

                // Si c’est un utilisateur qui s’auto-inscrit → rediriger vers login
                resp.sendRedirect("Auth/login.jsp");

            } catch (Exception e) {
                req.setAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }
        }

    }
}
