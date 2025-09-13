package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.Immeuble;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ImmeubleImpl implements IDAO<Immeuble> {
private EntityManager entityManager;
    public ImmeubleImpl() {
        this.entityManager= JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    @Override
    public List<Immeuble> getAll() {
        List<Immeuble> immeubles=entityManager.createQuery("from Immeuble", Immeuble.class).getResultList();
        return immeubles;
    }

    @Override
    public Immeuble getById(int id) {
       Immeuble immeuble = entityManager.find(Immeuble.class, id);
       return immeuble;
    }

    @Override
    public void add(Immeuble immeuble) {
        entityManager.getTransaction().begin();
        entityManager.persist(immeuble);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Immeuble immeuble) {

        entityManager.getTransaction().begin();
        entityManager.merge(immeuble);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        entityManager.getTransaction().begin();
        Immeuble immeuble= getById(id);
        entityManager.remove(immeuble);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Immeuble> search(String libelle) {
        return entityManager.createQuery(
                        "SELECT i FROM Immeuble i " +
                                "WHERE LOWER(i.adresse) LIKE :libelle " +
                                "   OR LOWER(i.nom) LIKE :libelle", Immeuble.class)
                .setParameter("libelle", "%" + libelle.toLowerCase() + "%")
                .getResultList();
    }
    public int countAll() {
        return ((Number) entityManager.createQuery("SELECT COUNT(i) FROM Immeuble i")
                .getSingleResult()).intValue();
    }
    public int countContratsActifs() {
        // Exemple : SELECT COUNT(c) FROM Contrat c WHERE c.actif = true
        return entityManager.createQuery(
                        "SELECT COUNT(c) FROM UniteDeLocation c WHERE c.disponible = true", Long.class)
                .getSingleResult().intValue();
    }
    public List<Immeuble> getByProprietaire(int proprietaireId) {

        List<Immeuble> immeubles = null;
            immeubles = entityManager.createQuery(
                            "SELECT i FROM Immeuble i WHERE i.proprietaire.id = :proprioId", Immeuble.class)
                    .setParameter("proprioId", proprietaireId)
                    .getResultList();

        return immeubles;
    }

}
