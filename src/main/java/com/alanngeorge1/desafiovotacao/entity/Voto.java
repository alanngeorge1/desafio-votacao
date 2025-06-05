package com.alanngeorge1.desafiovotacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Pauta pauta;

    @Column(nullable = false)
    private String associadoId;

    @Column(nullable = false)
    private String voto;
}
