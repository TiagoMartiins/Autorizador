package com.destaxa.Autorizador.service.impl;

import com.destaxa.Autorizador.entity.Transacao;
import com.destaxa.Autorizador.exception.BusinessException;
import com.destaxa.Autorizador.factory.IsoMsgFactory;
import com.destaxa.Autorizador.repository.TransacaoRepository;
import com.destaxa.Autorizador.service.PagamentoService;
import com.destaxa.Autorizador.utils.FieldUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {

    private final TransacaoRepository transacaoRepository;

    private final String MEU_COMPUTADOR = "localhost";

    private final ISOPackager packager;

    @Override
    @Transactional
    @SneakyThrows
    @Async
    public CompletableFuture<Transacao> autorizarTransacao(Transacao request) {
        try {
            ASCIIChannel channel = new ASCIIChannel(MEU_COMPUTADOR, 5050, packager);
            channel.connect();
            channel.setTimeout(5000);

            ISOMsg msg = IsoMsgFactory.toIsoMsg(request, packager);

            channel.send(msg);
            ISOMsg response = channel.receive();

            String responseCode = response.getString(FieldUtil.CODIGO_RESPOSTA);
            request.setCodigoResposta(responseCode);

            return CompletableFuture.completedFuture(transacaoRepository.save(request));

        } catch (IOException | ISOException e ) {

            if (e instanceof java.net.SocketTimeoutException) {
                log.error("[PagamentoServiceImpl] Erro valor superior a 1000");
                throw new RuntimeException("Timeout atingido na comunicação com o servidor, valor superior a 1000", e);
            }

            log.error("Erro ao autorizar transação", e);
            throw new BusinessException("Erro inesperado");
        }
    }
}
