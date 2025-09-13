package com.example.examen_jee_gestion_immo.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UniteDeLocations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UniteDeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Column(unique = true, nullable = false)
    private String codeUnite;

    @Column (name = "description")
    private String description;

    @Column (name = "nbrPieces")
    private int nbrPieces;

    @Column (name = "loyerMensuel")
    private double loyerMensuel;

    @Column(name = "disponible")
    private boolean disponible=true;

    @ManyToOne
    @JoinColumn(name = "immeuble_id")
    private Immeuble immeuble;




}
