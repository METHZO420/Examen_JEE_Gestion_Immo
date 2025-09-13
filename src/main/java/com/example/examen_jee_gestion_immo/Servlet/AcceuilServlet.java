package com.example.examen_jee_gestion_immo.Servlet;

import com.example.examen_jee_gestion_immo.Models.UniteDeLocation;
import com.example.examen_jee_gestion_immo.Repository.UniteDeLocationImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/accueil")
public class AcceuilServlet extends HttpServlet {

    private UniteDeLocationImpl uniteImpl;

    @Override
    public void init() throws ServletException {
        uniteImpl = new UniteDeLocationImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UniteDeLocation> unites = uniteImpl.getAll();
        req.setAttribute("unites", unites);
        req.getRequestDispatcher("/Utilisateur/acceuil.jsp").forward(req, resp);
    }
}