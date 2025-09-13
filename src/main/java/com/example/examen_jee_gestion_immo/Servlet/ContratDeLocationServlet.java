package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.ContratDeLocation;
import com.example.examen_jee_gestion_immo.Models.UniteDeLocation;
import com.example.examen_jee_gestion_immo.Models.Paiement;
import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.ContratDeLocationImpl;
import com.example.examen_jee_gestion_immo.Repository.UniteDeLocationImpl;
import com.example.examen_jee_gestion_immo.Repository.PaiementImpl;
import com.example.examen_jee_gestion_immo.Repository.UtilisateurImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/contrat")
public class ContratDeLocationServlet extends HttpServlet {

    private ContratDeLocationImpl contratImpl;
    private UniteDeLocationImpl uniteDeLocationImpl;
    private PaiementImpl paiementImpl;
    private UtilisateurImpl utilisateurImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        contratImpl = new ContratDeLocationImpl();
        uniteDeLocationImpl = new UniteDeLocationImpl();
        paiementImpl = new PaiementImpl();
        utilisateurImpl = new UtilisateurImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        Utilisateur currentUser = (Utilisateur) req.getSession().getAttribute("user");

        switch (action) {
            case "delete":
                int idDelete = Integer.parseInt(req.getParameter("id"));
                contratImpl.delete(idDelete);
                resp.sendRedirect(req.getContextPath() + "/contrat");
                break;

            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                ContratDeLocation contrat = contratImpl.getById(idEdit);
                List<UniteDeLocation> unitesEdit = uniteDeLocationImpl.getAll();

                req.setAttribute("contrat", contrat);
                req.setAttribute("unites", unitesEdit);

                if ("ADMIN".equals(currentUser.getRole().name())) {
                    req.setAttribute("locataires", utilisateurImpl.getAll());
                    req.getRequestDispatcher("/Admin/Contrat/editContrat.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/Utilisateur/Contrat/editContrat.jsp").forward(req, resp);
                }
                break;

            case "add":
                List<UniteDeLocation> unites = uniteDeLocationImpl.getAll();
                req.setAttribute("unites", unites);

                if ("ADMIN".equals(currentUser.getRole().name())) {
                    req.setAttribute("locataires", utilisateurImpl.getAll());
                    req.getRequestDispatcher("/Admin/Contrat/addContrat.jsp").forward(req, resp);
                } else {
                    req.setAttribute("locataire", currentUser);
                    req.getRequestDispatcher("/Utilisateur/Contrat/addContrat.jsp").forward(req, resp);
                }
                break;

            default: // list
                if ("ADMIN".equals(currentUser.getRole().name())) {
                    List<ContratDeLocation> contrats = contratImpl.getAll();
                    req.setAttribute("contrats", contrats);
                    req.getRequestDispatcher("/Admin/Contrat/contrat.jsp").forward(req, resp);
                } else {
                    List<ContratDeLocation> contrats = contratImpl.getByLocataire(currentUser.getId());
                    req.setAttribute("contrats", contrats);
                    req.getRequestDispatcher("/Utilisateur/Contrat/contrat.jsp").forward(req, resp);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String codeContrat = req.getParameter("codeContrat");
            String dateDebut = req.getParameter("dateDebut");
            String dateFin = req.getParameter("dateFin");
            double montant = Double.parseDouble(req.getParameter("montantLoyer"));
            int uniteId = Integer.parseInt(req.getParameter("uniteId"));

            UniteDeLocation unite = uniteDeLocationImpl.getById(uniteId);

            ContratDeLocation contrat = contratImpl.getById(id);
            contrat.setCodeContrat(codeContrat);
            contrat.setDateDebut(LocalDate.parse(dateDebut));
            contrat.setDateFin(LocalDate.parse(dateFin));
            contrat.setMontantLoyer(montant);
            contrat.setUniteDeLocation(unite);

            Utilisateur currentUser = (Utilisateur) req.getSession().getAttribute("user");
            if ("ADMIN".equals(currentUser.getRole().name())) {
                int locataireId = Integer.parseInt(req.getParameter("locataireId"));
                Utilisateur locataire = utilisateurImpl.getById(locataireId);
                contrat.setLocataire(locataire);
            } else {
                contrat.setLocataire(currentUser);
            }

            contratImpl.update(contrat);
            resp.sendRedirect(req.getContextPath() + "/contrat");

        } else if ("add".equals(action)) {
            String codeContrat = "CTR-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String dateDebut = req.getParameter("dateDebut");
            String dateFin = req.getParameter("dateFin");
            double montant = Double.parseDouble(req.getParameter("montantLoyer"));
            int uniteId = Integer.parseInt(req.getParameter("uniteId"));

            UniteDeLocation unite = uniteDeLocationImpl.getById(uniteId);

            Utilisateur currentUser = (Utilisateur) req.getSession().getAttribute("user");
            Utilisateur locataire;
            if ("ADMIN".equals(currentUser.getRole().name())) {
                int locataireId = Integer.parseInt(req.getParameter("locataireId"));
                locataire = utilisateurImpl.getById(locataireId);
            } else {
                locataire = currentUser;
            }

            ContratDeLocation contrat = ContratDeLocation.builder()
                    .codeContrat(codeContrat)
                    .dateDebut(LocalDate.parse(dateDebut))
                    .dateFin(LocalDate.parse(dateFin))
                    .montantLoyer(montant)
                    .uniteDeLocation(unite)
                    .locataire(locataire)
                    .build();

            contratImpl.add(contrat);

            // Génération paiements mensuels
            LocalDate debut = LocalDate.parse(dateDebut);
            LocalDate fin = LocalDate.parse(dateFin);
            LocalDate current = debut;

            while (!current.isAfter(fin)) {
                Paiement paiement = Paiement.builder()
                        .datePaiement(current)
                        .montant(montant)
                        .status(Paiement.Status.EN_ATTENTE)
                        .contratDeLocation(contrat)
                        .build();

                paiementImpl.add(paiement);
                current = current.plusMonths(1);
            }

            resp.sendRedirect(req.getContextPath() + "/contrat");
        }
    }
}
