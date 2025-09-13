package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.Paiement;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaiementImpl implements IDAO<Paiement>{

    EntityManager em;
    public PaiementImpl() {
        this.em= JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    @Override
    public List<Paiement> getAll() {

        List<Paiement> paiements = em.createQuery("from Paiement", Paiement.class).getResultList();
        return paiements;
    }

    @Override
    public Paiement getById(int id) {

        return em.find(Paiement.class, id);
    }

    @Override
    public void add(Paiement paiement) {
        em.getTransaction().begin();
        em.persist(paiement);
        em.getTransaction().commit();


    }

    @Override
    public void update(Paiement paiement) {
        em.getTransaction().begin();
        em.merge(paiement);
        em.getTransaction().commit();

    }

    @Override
    public void delete(int id) {
        em.getTransaction().begin();
        Paiement paiement = em.find(Paiement.class, id);
        em.remove(paiement);

    }

    @Override
    public List<Paiement> search(String libelle) {
        return List.of();
    }
    public int countAll() {
        return ((Number) em.createQuery("SELECT COUNT(p) FROM Paiement p")
                .getSingleResult()).intValue();
    }
    public int countPaiementsDuMois() {
        // Exemple : SELECT COUNT(*) FROM Paiement WHERE MONTH(datePaiement) = MONTH(CURRENT_DATE)
        // Implémente selon ton framework (JPA, JDBC, etc.)
        return em.createQuery(
                        "SELECT COUNT(p) FROM Paiement p WHERE FUNCTION('MONTH', p.datePaiement) = FUNCTION('MONTH', CURRENT_DATE)", Long.class)
                .getSingleResult().intValue();
    }

    public int countContratsActifs() {
        return ((Number) em.createQuery(
                        "SELECT COUNT(c) FROM ContratDeLocation c WHERE c.dateFin >= CURRENT_DATE")
                .getSingleResult()).intValue();
    }

    public int countPaiementsMois(int mois, int annee) {
        return ((Number) em.createQuery(
                        "SELECT COUNT(p) FROM Paiement p WHERE MONTH(p.datePaiement) = :mois AND YEAR(p.datePaiement) = :annee")
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .getSingleResult()).intValue();
    }

    public double getSommePaiements() {
        Double somme = (Double) em.createQuery(
                        "SELECT SUM(p.montant) FROM Paiement p WHERE p.status = :status")
                .setParameter("status", Paiement.Status.PAYER)
                .getSingleResult();
        return somme != null ? somme : 0.0;
    }
    public List<Paiement> findByCode(String filter) {
        return em.createQuery(
                        "SELECT p FROM Paiement p WHERE LOWER(p.codePaiement) LIKE LOWER(:filter)",
                        Paiement.class
                )
                .setParameter("filter", "%" + filter + "%")
                .getResultList();
    }
    public List<Paiement> getByLocataire(int locataireId) {
        return em.createQuery(
                        "SELECT p FROM Paiement p WHERE p.contratDeLocation.locataire.id = :locataireId",
                        Paiement.class
                ).setParameter("locataireId", locataireId)
                .getResultList();
    }

}
