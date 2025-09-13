package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.ContratDeLocation;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ContratDeLocationImpl implements IDAO<ContratDeLocation> {

    private final EntityManager entityManager;

    public ContratDeLocationImpl() {
        this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<ContratDeLocation> getAll() {
        return entityManager.createQuery("from ContratDeLocation", ContratDeLocation.class)
                .getResultList();
    }

    @Override
    public ContratDeLocation getById(int id) {
        return entityManager.find(ContratDeLocation.class, id);
    }

    @Override
    public void add(ContratDeLocation contratDeLocation) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(contratDeLocation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void update(ContratDeLocation contratDeLocation) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(contratDeLocation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        try {
            entityManager.getTransaction().begin();
            ContratDeLocation contrat = entityManager.find(ContratDeLocation.class, id);
            if (contrat != null) {
                entityManager.remove(contrat);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<ContratDeLocation> search(String libelle) {
        return List.of(); // pas utilisé pour le moment
    }

    // ✅ Récupérer les contrats par locataire
    public List<ContratDeLocation> getByLocataire(int locataireId) {
        TypedQuery<ContratDeLocation> query = entityManager.createQuery(
                "SELECT c FROM ContratDeLocation c WHERE c.locataire.id = :locataireId",
                ContratDeLocation.class
        );
        query.setParameter("locataireId", locataireId);
        return query.getResultList();
    }

    // ✅ Vérifier si une unité est déjà associée à un contrat
    public boolean existsByUniteId(int uniteId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM ContratDeLocation c WHERE c.uniteDeLocation.id = :uniteId",
                        Long.class
                )
                .setParameter("uniteId", uniteId)
                .getSingleResult();

        return count != null && count > 0;
    }
}
