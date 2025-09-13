package com.example.examen_jee_gestion_immo.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "immeubles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Immeuble {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;

   @Column(name = "adresse", nullable = false)
   private String adresse;

   @Column(name="nom")
   private String nom;

   @Column(name = "equipement")
   private String equipement;

   @Column(name = "disponible")
   private boolean disponible;

   @OneToMany(mappedBy = "immeuble", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<UniteDeLocation> unites;

   @ManyToOne
   @JoinColumn(name = "proprietaire_id")
   private Utilisateur proprietaire;
}
