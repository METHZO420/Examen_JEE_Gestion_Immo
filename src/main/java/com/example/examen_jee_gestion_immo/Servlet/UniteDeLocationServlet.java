package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.Immeuble;
import com.example.examen_jee_gestion_immo.Models.UniteDeLocation;
import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.ImmeubleImpl;
import com.example.examen_jee_gestion_immo.Repository.UniteDeLocationImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/uniteDeLocation")
public class UniteDeLocationServlet extends HttpServlet {

    private UniteDeLocationImpl uniteImpl;
    private ImmeubleImpl immeubleImpl;

    @Override
    public void init() throws ServletException {
        uniteImpl = new UniteDeLocationImpl();
        immeubleImpl = new ImmeubleImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Utilisateur currentUser = (Utilisateur) session.getAttribute("user");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                List<Immeuble> immeublesAdd;
                if (currentUser != null && "ADMIN".equals(currentUser.getRole().name())) {
                    immeublesAdd = immeubleImpl.getAll();
                } else {
                    immeublesAdd = immeubleImpl.getByProprietaire(currentUser.getId());
                }
                req.setAttribute("immeubles", immeublesAdd);

                if (currentUser != null && "ADMIN".equals(currentUser.getRole().name())) {
                    req.getRequestDispatcher("/Admin/uniteDeLocation/addUnite.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/Utilisateur/uniteDeLocation/addUnite.jsp").forward(req, resp);
                }
                break;

            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                UniteDeLocation unite = uniteImpl.getById(idEdit);

                List<Immeuble> immeublesEdit;
                if (currentUser != null && "ADMIN".equals(currentUser.getRole().name())) {
                    immeublesEdit = immeubleImpl.getAll();
                } else {
                    immeublesEdit = immeubleImpl.getByProprietaire(currentUser.getId());
                }
                req.setAttribute("immeubles", immeublesEdit);
                req.setAttribute("unite", unite);

                if (currentUser != null && "ADMIN".equals(currentUser.getRole().name())) {
                    req.getRequestDispatcher("/Admin/uniteDeLocation/editUnite.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/Utilisateur/uniteDeLocation/editUnite.jsp").forward(req, resp);
                }
                break;

            case "delete":
                int idDelete = Integer.parseInt(req.getParameter("id"));
                uniteImpl.delete(idDelete);
                resp.sendRedirect(req.getContextPath() + "/uniteDeLocation");
                break;

            default: // list
                List<UniteDeLocation> unites;
                //System.out.println(currentUser);
                if (currentUser != null && "PROPRIETAIRE".equals(currentUser.getRole().name())) {
                    // récupérer uniquement les unités des immeubles appartenant au proprio connecté
                    unites = uniteImpl.getByProprietaire(currentUser.getId());
                    System.out.println(unites.size());
                    req.setAttribute("unites", unites);
                    req.getRequestDispatcher("/Utilisateur/uniteDeLocation/unite.jsp").forward(req, resp);
                } else {
                    // Admin -> toutes les unités
                    unites = uniteImpl.getAll();
                    req.setAttribute("unites", unites);
                    req.getRequestDispatcher("/Admin/uniteDeLocation/unite.jsp").forward(req, resp);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        String description = req.getParameter("description");
        String nom = req.getParameter("nom");
        int nbrPieces = Integer.parseInt(req.getParameter("nbrPieces"));
        double loyerMensuel = Double.parseDouble(req.getParameter("loyerMensuel"));
        boolean disponible = req.getParameter("disponible") != null;
        int immeubleId = Integer.parseInt(req.getParameter("immeuble_id"));
        Immeuble immeuble = immeubleImpl.getById(immeubleId);

        if ("add".equals(action)) {
            String codeUnite = "UDL-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            UniteDeLocation unite = UniteDeLocation.builder()
                    .codeUnite(codeUnite)
                    .nom(nom)
                    .description(description)
                    .nbrPieces(nbrPieces)
                    .loyerMensuel(loyerMensuel)
                    .disponible(disponible)
                    .immeuble(immeuble)
                    .build();
            uniteImpl.add(unite);
            resp.sendRedirect(req.getContextPath() + "/uniteDeLocation");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            UniteDeLocation unite = uniteImpl.getById(id);
            unite.setNom(nom);
            unite.setDescription(description);
            unite.setNbrPieces(nbrPieces);
            unite.setLoyerMensuel(loyerMensuel);
            unite.setDisponible(disponible);
            unite.setImmeuble(immeuble);

            uniteImpl.update(unite);
            resp.sendRedirect(req.getContextPath() + "/uniteDeLocation");
        }
    }
}
