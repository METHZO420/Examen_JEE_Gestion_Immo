package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Repository.ImmeubleImpl;
import com.example.examen_jee_gestion_immo.Repository.PaiementImpl;
import com.example.examen_jee_gestion_immo.Repository.UtilisateurImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private UtilisateurImpl utilisateurImpl;
    private ImmeubleImpl immeubleImpl;
    private PaiementImpl paiementImpl;

    @Override
    public void init() throws ServletException {
        utilisateurImpl = new UtilisateurImpl();
        immeubleImpl = new ImmeubleImpl();
        paiementImpl = new PaiementImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Utilisateur user = (session != null) ? (Utilisateur) session.getAttribute("user") : null;

        // 🔐 Vérification d'accès
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        if (user.getRole() != Utilisateur.Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé à l'administrateur");
            return;
        }

        int totalUtilisateurs = utilisateurImpl.countAll();
        int totalImmeubles = immeubleImpl.countAll();
        int totalPaiements = paiementImpl.countAll();
        System.out.println(totalUtilisateurs);
        int contratsActifs = paiementImpl.countContratsActifs();
        int paiementsMois = paiementImpl.countPaiementsMois(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        double sommePaiements = paiementImpl.getSommePaiements();

        List<Utilisateur> derniersUtilisateurs = utilisateurImpl.getDerniers(5);

        req.setAttribute("totalUtilisateurs", totalUtilisateurs);
        req.setAttribute("totalImmeubles", totalImmeubles);
        req.setAttribute("totalPaiements", totalPaiements);
        req.setAttribute("contratsActifs", contratsActifs);
        req.setAttribute("paiementsMois", paiementsMois);

        req.setAttribute("sommePaiements", sommePaiements);
        req.setAttribute("derniersUtilisateurs", derniersUtilisateurs);

        req.getRequestDispatcher("Admin/Dashboard.jsp").forward(req, resp);
    }
}
