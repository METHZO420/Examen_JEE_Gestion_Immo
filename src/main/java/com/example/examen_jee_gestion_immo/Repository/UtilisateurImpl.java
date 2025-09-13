package com.example.examen_jee_gestion_immo.Repository;

import com.example.examen_jee_gestion_immo.Models.Utilisateur;
import com.example.examen_jee_gestion_immo.Utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class UtilisateurImpl implements IDAO<Utilisateur> {
    EntityManager entityManager;
    public UtilisateurImpl() {
        this.entityManager= JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = entityManager.createQuery("from Utilisateur").getResultList();
        return utilisateurs;
     }

    @Override
    public Utilisateur getById(int id) {

        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        return utilisateur;
    }

    @Override
    public void add(Utilisateur utilisateur) {

        entityManager.getTransaction().begin();
        entityManager.persist(utilisateur);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Utilisateur utilisateur) {
      entityManager.getTransaction().begin();
      entityManager.merge(utilisateur);
      entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        entityManager.getTransaction().begin();
        Utilisateur utilisateur = getById(id);
        entityManager.remove(utilisateur);
    }

    @Override
    public List<Utilisateur> search(String libelle) {
        return List.of();
    }

    public Utilisateur login(String email, String password) {
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email AND u.password = :pass", Utilisateur.class)
                    .setParameter("email", email)
                    .setParameter("pass", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public int countAll() {
        return ((Number) entityManager.createQuery("SELECT COUNT(u) FROM Utilisateur u")
                .getSingleResult()).intValue();
    }

    public List<Utilisateur> getDerniers(int limite) {
        return entityManager.createQuery("SELECT u FROM Utilisateur u ORDER BY u.id DESC", Utilisateur.class)
                .setMaxResults(limite)
                .getResultList();
    }
}
