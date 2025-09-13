package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.DemandeLocation;
import com.example.examen_jee_gestion_immo.Models.Paiement;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class DemandeLocationImpl implements IDAO<DemandeLocation>{

    EntityManager em;
    public DemandeLocationImpl( ) {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<DemandeLocation> getAll() {
        List<DemandeLocation> demandeLocations = em.createQuery("from DemandeLocation ", DemandeLocation.class).getResultList();
        return demandeLocations;
    }

    @Override
    public DemandeLocation getById(int id) {
       return em.find(DemandeLocation.class, id);
    }

    @Override
    public void add(DemandeLocation demandeLocation) {
    em.getTransaction().begin();
    em.persist(demandeLocation);
    em.getTransaction().commit();
    }

    @Override
    public void update(DemandeLocation demandeLocation) {
    em.getTransaction().begin();
    em.merge(demandeLocation);
    em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
    em.getTransaction().begin();
    DemandeLocation demandeLocation = getById(id);
    em.remove(demandeLocation);
    }

    @Override
    public List<DemandeLocation> search(String libelle) {
        return List.of();
    }
}
