package com.example.examen_jee_gestion_immo.Models;


import com.example.examen_jee_gestion_immo.Models.ContratDeLocation;
import com.example.examen_jee_gestion_immo.Models.Immeuble;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String matricule;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, UTILISATEUR
    }

    // 🔹 Si l’utilisateur possède des immeubles → il est PROPRIETAIRE
    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    private List<Immeuble> immeubles;

    // 🔹 Si l’utilisateur a signé des contrats → il est LOCATAIRE
    @OneToMany(mappedBy = "locataire", cascade = CascadeType.ALL)
    private List<ContratDeLocation> contrats;
}
