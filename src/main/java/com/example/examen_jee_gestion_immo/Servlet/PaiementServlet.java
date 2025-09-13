package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.ContratDeLocation;
import com.example.examen_jee_gestion_immo.Models.Paiement;
import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.ContratDeLocationImpl;
import com.example.examen_jee_gestion_immo.Repository.PaiementImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet("/paiement")
public class PaiementServlet extends HttpServlet {

    private PaiementImpl paiementImpl;
    private ContratDeLocationImpl contratImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        paiementImpl = new PaiementImpl();
        contratImpl = new ContratDeLocationImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        Utilisateur currentUser = (Utilisateur) req.getSession().getAttribute("user");

        switch (action) {
            case "delete":
                if ("ADMIN".equals(currentUser.getRole().name())) {
                    int idDelete = Integer.parseInt(req.getParameter("id"));
                    paiementImpl.delete(idDelete);
                }
                resp.sendRedirect(req.getContextPath() + "/paiement");
                break;

            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                Paiement paiement = paiementImpl.getById(idEdit);

                if ("ADMIN".equals(currentUser.getRole().name())) {
                    req.setAttribute("paiement", paiement);
                    req.getRequestDispatcher("/Admin/Paiement/editPaiement.jsp").forward(req, resp);
                } else {
                    if (paiement.getContratDeLocation().getLocataire().getId() == currentUser.getId()) {
                        req.setAttribute("paiement", paiement);
                        req.getRequestDispatcher("/Utilisateur/Paiement/editPaiement.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/paiement");
                    }
                }
                break;

            case "add":
                if ("ADMIN".equals(currentUser.getRole().name())) {
                    List<ContratDeLocation> contrats = contratImpl.getAll();
                    String codePaiement = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    req.setAttribute("contrats", contrats);
                    req.setAttribute("codePaiement", codePaiement);
                    req.getRequestDispatcher("/Admin/Paiement/addPaiement.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/paiement");
                }
                break;

            default:
                if ("ADMIN".equals(currentUser.getRole().name())) {
                    String filter = req.getParameter("filter");
                    List<Paiement> paiements;
                    if (filter != null && !filter.trim().isEmpty()) {
                        paiements = paiementImpl.findByCode(filter);
                    } else {
                        paiements = paiementImpl.getAll();
                    }
                    req.setAttribute("paiements", paiements);
                    req.getRequestDispatcher("/Admin/Paiement/paiement.jsp").forward(req, resp);
                } else {
                    List<Paiement> paiements = paiementImpl.getByLocataire(currentUser.getId());
                    req.setAttribute("paiements", paiements);
                    req.getRequestDispatcher("/Utilisateur/Paiement/paiement.jsp").forward(req, resp);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur currentUser = (Utilisateur) req.getSession().getAttribute("user");

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Paiement paiement = paiementImpl.getById(id);

            if ("ADMIN".equals(currentUser.getRole().name())) {
                paiement.setDatePaiement(LocalDate.parse(req.getParameter("datePaiement")));
                paiement.setStatus(Paiement.Status.valueOf(req.getParameter("status")));
                paiement.setMontant(Double.parseDouble(req.getParameter("montant")));
                paiementImpl.update(paiement);
            } else {
                // L’utilisateur peut seulement changer son statut en PAYE
                if (paiement.getContratDeLocation().getLocataire().getId() == currentUser.getId()) {
                    paiement.setStatus(Paiement.Status.PAYER);
                    paiement.setDatePaiement(LocalDate.now());
                    paiementImpl.update(paiement);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/paiement");

        } else if ("add".equals(action) && "ADMIN".equals(currentUser.getRole().name())) {
            int contratId = Integer.parseInt(req.getParameter("contratId"));
            ContratDeLocation contrat = contratImpl.getById(contratId);

            Paiement paiement = Paiement.builder()
                    .codePaiement(req.getParameter("codePaiement"))
                    .datePaiement(LocalDate.parse(req.getParameter("datePaiement")))
                    .montant(contrat.getMontantLoyer())
                    .status(Paiement.Status.valueOf(req.getParameter("status")))
                    .contratDeLocation(contrat)
                    .build();

            paiementImpl.add(paiement);
            resp.sendRedirect(req.getContextPath() + "/paiement");
        }
    }
}
