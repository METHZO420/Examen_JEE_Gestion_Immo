package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.UtilisateurImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/utilisateur")
public class UtilisateurServlet extends HttpServlet {
    private UtilisateurImpl utilisateurImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurImpl = new UtilisateurImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "delete":
                int idDelete = Integer.parseInt(req.getParameter("id"));
                utilisateurImpl.delete(idDelete);
                resp.sendRedirect(req.getContextPath() + "/admin/utilisateurs");
                break;

            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                Utilisateur utilisateur = utilisateurImpl.getById(idEdit);
                req.setAttribute("utilisateur", utilisateur);
                req.getRequestDispatcher("/Admin/Utilisateur/editUtilisateur.jsp").forward(req, resp);
                break;

            case "add":
                req.getRequestDispatcher("/Admin/Utilisateur/addUtilisateur.jsp").forward(req, resp);
                break;

            default: // list
                List<Utilisateur> utilisateurs = utilisateurImpl.getAll();
                req.setAttribute("utilisateurs", utilisateurs);
                req.getRequestDispatcher("/Admin/Utilisateur/utilisateur.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String matricule = req.getParameter("matricule");
            String password = req.getParameter("password");
            String roleStr = req.getParameter("role");

            Utilisateur utilisateur = utilisateurImpl.getById(id);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setMatricule(matricule);
            utilisateur.setPassword(password);
            utilisateur.setRole(Utilisateur.Role.valueOf(roleStr));

            utilisateurImpl.update(utilisateur);
            resp.sendRedirect(req.getContextPath() + "/Admin/Utilisateur/utilisateur.jsp");

        } else if ("add".equals(action)) {

            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String matricule = "MAT-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String password = req.getParameter("password");
            String roleStr = req.getParameter("role");

            Utilisateur utilisateur = Utilisateur.builder()
                    .nom(nom)
                    .prenom(prenom)
                    .email(email)
                    .matricule(matricule)
                    .password(password)
                    .role(Utilisateur.Role.valueOf(roleStr))
                    .build();

            utilisateurImpl.add(utilisateur);
            resp.sendRedirect(req.getContextPath() + "/Admin/Utilisateur/utilisateur.jsp");
        }
    }

}
