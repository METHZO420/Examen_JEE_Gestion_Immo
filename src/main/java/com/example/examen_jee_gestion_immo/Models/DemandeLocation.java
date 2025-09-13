package com.example.examen_jee_gestion_immo.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "demandeLocation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Utilisateur locataire;

    @ManyToOne
    @JoinColumn(name = "unite_id")
    private UniteDeLocation unite;

    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    public enum StatutDemande {
        EN_ATTENTE, ACCEPTEE, REFUSEE
    }
}
