package com.alanngeorge1.desafiovotacao.repository;

import com.alanngeorge1.desafiovotacao.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository  extends JpaRepository<Pauta, Long> {

}
