package com.destaxa.Autorizador.repository;

import com.destaxa.Autorizador.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
