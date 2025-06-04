package com.alanngeorge1.desafiovotacao.repository;

import com.alanngeorge1.desafiovotacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByPautaIdAndAssociadoId(Long pautaId, String associadoId);

    Long countByPautaIdAndVoto(Long pautaId, String voto);
}
