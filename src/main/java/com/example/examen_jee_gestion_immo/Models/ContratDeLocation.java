package com.example.examen_jee_gestion_immo.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.*;

import javax.xml.namespace.QName;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "ContratDeLocations")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ContratDeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(unique = true, nullable = false)
    private String codeContrat;

    @Column(name = "dateDebut")
    private LocalDate dateDebut;

    @Column(name = "dateFin")
    private LocalDate dateFin;

    @Column(name = "montantLoyer")
    private double montantLoyer;

    @OneToOne
    @JoinColumn(name = "unite_id")
    private UniteDeLocation uniteDeLocation;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Utilisateur locataire;

    @OneToMany(mappedBy = "contratDeLocation", cascade = CascadeType.ALL)
    private List<Paiement> paiements;
}
