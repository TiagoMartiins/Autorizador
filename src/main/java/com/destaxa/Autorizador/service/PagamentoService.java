package com.destaxa.Autorizador.service;

import com.destaxa.Autorizador.entity.Transacao;

import java.util.concurrent.CompletableFuture;

public interface PagamentoService {

    CompletableFuture<Transacao> autorizarTransacao(Transacao request);
}
