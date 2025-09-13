package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.DemandeLocation;
import com.example.examen_jee_gestion_immo.Models.UniteDeLocation;
import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.DemandeLocationImpl;
import com.example.examen_jee_gestion_immo.Repository.UniteDeLocationImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/demande")
public class DemandeLocationServlet extends HttpServlet {

    private DemandeLocationImpl demandeImpl;
    private UniteDeLocationImpl uniteImpl;

    @Override
    public void init() throws ServletException {
        demandeImpl = new DemandeLocationImpl();
        uniteImpl = new UniteDeLocationImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                int uniteId = Integer.parseInt(req.getParameter("uniteId"));

                 Utilisateur locataire = (Utilisateur) req.getSession().getAttribute("user");


                DemandeLocation demande = DemandeLocation.builder()
                        .dateDemande(LocalDate.now())
                        .statut(DemandeLocation.StatutDemande.EN_ATTENTE)
                        .locataire(locataire)
                        .unite(uniteImpl.getById(uniteId))
                        .build();

                demandeImpl.add(demande);
                resp.sendRedirect(req.getContextPath() + "/accueil");
                break;

            case "update":
                int id = Integer.parseInt(req.getParameter("id"));
                String statut = req.getParameter("statut");

                DemandeLocation demandeToUpdate = demandeImpl.getById(id);
                if (demandeToUpdate != null) {
                    demandeToUpdate.setStatut(DemandeLocation.StatutDemande.valueOf(statut));
                    demandeImpl.update(demandeToUpdate);

                    if ("ACCEPTEE".equals(statut)) {
                        resp.sendRedirect(req.getContextPath()
                                + "/contrat?action=add"
                                + "&locataireId=" + demandeToUpdate.getLocataire().getId()
                                + "&uniteId=" + demandeToUpdate.getUnite().getId()
                                + "&montant=" + demandeToUpdate.getUnite().getLoyerMensuel());
                        return;
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/demande");
                break;

            default: // list
                List<DemandeLocation> demandes = demandeImpl.getAll();
                req.setAttribute("demandes", demandes);
                req.getRequestDispatcher("/Utilisateur/DemandeLocation/listeDemandes.jsp").forward(req, resp);
                break;
        }
    }
}
