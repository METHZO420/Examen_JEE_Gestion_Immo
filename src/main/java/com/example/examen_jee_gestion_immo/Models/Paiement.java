package com.example.examen_jee_gestion_immo.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code_paiement",unique = true)
    private String codePaiement;

    private LocalDate datePaiement;

    private double montant;

    @Enumerated(EnumType.STRING) // <-- stocké en texte en BDD
    private Status status;


    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratDeLocation contratDeLocation;

    public enum Status {
        EN_ATTENTE, PAYER, EN_RETARD
    }
}
