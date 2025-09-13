package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.Immeuble;
import com.example.examen_jee_gestion_immo.Models.UniteDeLocation;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transaction;

import java.util.List;

public class UniteDeLocationImpl implements IDAO<UniteDeLocation>{
    EntityManager em;

    public UniteDeLocationImpl() {
        this.em= JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    @Override
    public List<UniteDeLocation> getAll() {

        List<UniteDeLocation> uniteDeLocations = em.createQuery("from UniteDeLocation", UniteDeLocation.class).getResultList();
        return uniteDeLocations;
    }

    @Override
    public UniteDeLocation getById(int id) {
        UniteDeLocation uniteDeLocation = em.find(UniteDeLocation.class, id);
        return uniteDeLocation;
    }

    @Override
    public void add(UniteDeLocation uniteDeLocation) {
        em.getTransaction().begin();
        em.persist(uniteDeLocation);
        em.getTransaction().commit();
    }

    @Override
    public void update(UniteDeLocation uniteDeLocation) {
            em.getTransaction().begin();
            em.merge(uniteDeLocation);
            em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
    em.getTransaction().begin();
    UniteDeLocation uniteDeLocation= getById(id);
    em.remove(uniteDeLocation);
    em.getTransaction().commit();
    }

    @Override
    public List<UniteDeLocation> search(String libelle) {
        return List.of();
    }
    public List<UniteDeLocation> getByProprietaire(int proprietaireId) {
        return em.createQuery(
                        "SELECT u FROM UniteDeLocation u WHERE u.immeuble.proprietaire.id = :propId", UniteDeLocation.class)
                .setParameter("propId", proprietaireId)
                .getResultList();
    }
    public List<UniteDeLocation> getByImmeuble(int immeubleId) {
        return em.createQuery(
                        "SELECT u FROM UniteDeLocation u WHERE u.immeuble.id = :immeubleId",
                        UniteDeLocation.class)
                .setParameter("immeubleId", immeubleId)
                .getResultList();
    }

    // Récupérer toutes les unités appartenant à une liste d’immeubles
    public List<UniteDeLocation> getByImmeubles(List<Immeuble> immeubles) {
        if (immeubles == null || immeubles.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        List<Integer> ids = immeubles.stream().map(Immeuble::getId).toList();
        return em.createQuery(
                        "SELECT u FROM UniteDeLocation u WHERE u.immeuble.id IN (:ids)",
                        UniteDeLocation.class)
                .setParameter("ids", ids)
                .getResultList();
    }

}
