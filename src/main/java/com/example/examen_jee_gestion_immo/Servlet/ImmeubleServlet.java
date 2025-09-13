package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.Immeuble;
import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.ImmeubleImpl;
import com.example.examen_jee_gestion_immo.Repository.UtilisateurImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/immeuble")
public class ImmeubleServlet extends HttpServlet {

    private ImmeubleImpl immeubleImpl;
    private UtilisateurImpl utilisateurImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        immeubleImpl = new ImmeubleImpl();
        utilisateurImpl = new UtilisateurImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        HttpSession session = req.getSession();
        Utilisateur currentUser = (Utilisateur) session.getAttribute("user");

        String role = "";
        if (currentUser != null && currentUser.getRole() != null) {
            role = currentUser.getRole().name();

        }

        String basePath = "ADMIN".equals(role) ? "/Admin/Immeuble/" : "/Utilisateur/Immeuble/";

        switch (action) {
            case "delete":
                int idDelete = Integer.parseInt(req.getParameter("id"));
                immeubleImpl.delete(idDelete);
                resp.sendRedirect(req.getContextPath() + "/immeuble");
                break;

            case "search":
                String keyword = req.getParameter("keyword");
                List<Immeuble> results = immeubleImpl.search(keyword);
                req.setAttribute("immeubles", results);
                req.getRequestDispatcher(basePath + "immeuble.jsp").forward(req, resp);
                break;

            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                Immeuble immeuble = immeubleImpl.getById(idEdit);
                req.setAttribute("immeuble", immeuble);
                req.getRequestDispatcher(basePath + "editImmeuble.jsp").forward(req, resp);
                break;

            case "add":
                if ("ADMIN".equals(role)) {
                    List<Utilisateur> utilisateurs = utilisateurImpl.getAll();
                    req.setAttribute("proprietaires", utilisateurs);
                }
                req.getRequestDispatcher(basePath + "addImmeuble.jsp").forward(req, resp);
                break;

            default:
                List<Immeuble> immeubles;
                if ("ADMIN".equals(role)) {
                    immeubles = immeubleImpl.getAll();
                } else {
                    immeubles = immeubleImpl.getByProprietaire(currentUser.getId());
                }
                req.setAttribute("immeubles", immeubles);
                req.getRequestDispatcher(basePath + "immeuble.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        HttpSession session = req.getSession();
        Utilisateur currentUser = (Utilisateur) session.getAttribute("user");

        String role = "";
        if (currentUser != null && currentUser.getRole() != null) {
            role = currentUser.getRole().name();
        }

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nom = req.getParameter("nom");
            String adresse = req.getParameter("adresse");
            String equipement = req.getParameter("equipement");
            boolean disponible = req.getParameter("disponible") != null;

            Immeuble immeuble = immeubleImpl.getById(id);
            immeuble.setNom(nom);
            immeuble.setAdresse(adresse);
            immeuble.setEquipement(equipement);
            immeuble.setDisponible(disponible);

            immeubleImpl.update(immeuble);
            resp.sendRedirect(req.getContextPath() + "/immeuble");

        } else if ("add".equals(action)) {
            String nom = req.getParameter("nom");
            String adresse = req.getParameter("adresse");
            String equipement = req.getParameter("equipement");
            boolean disponible = req.getParameter("disponible") != null;

            Utilisateur proprietaire;

            if ("ADMIN".equals(role)) {
                int proprioId = Integer.parseInt(req.getParameter("proprietaireId"));
                proprietaire = utilisateurImpl.getById(proprioId);
            } else {
                proprietaire = currentUser;
            }

            Immeuble immeuble = Immeuble.builder()
                    .nom(nom)
                    .adresse(adresse)
                    .equipement(equipement)
                    .disponible(disponible)
                    .proprietaire(proprietaire)
                    .build();

            immeubleImpl.add(immeuble);
            resp.sendRedirect(req.getContextPath() + "/immeuble");
        }
    }
}
