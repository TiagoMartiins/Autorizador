package com.destaxa.Autorizador.socket;

import com.destaxa.Autorizador.factory.IsoMsgFactory;
import com.destaxa.Autorizador.utils.FieldUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
@RequiredArgsConstructor
public class AutorizadorSocketSimulator {

    private final ISOPackager packager;

    private static final String CODIGO_ERRO_RESPONSE = "051";
    private static final String CODIGO_SUCESSO_RESPONSE = "000";

    private static final Long MIL_EM_CENTAVOS = 100000l;

    @PostConstruct
    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5050)) {
                log.info("Servidor ISO8583 rodando na porta 5050...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    clientSocket.setSoTimeout(5000);
                    MyASCIIChannel channel = new MyASCIIChannel(packager, clientSocket);
                    processarCliente(channel);
                }

            } catch (Exception e) {
                log.error("[AutorizadorSocketSimulator] Erro no servidor ISO8583", e);
            }
        }).start();
    }

    private void processarCliente(ASCIIChannel channel) {
        try {
            ISOMsg request = channel.receive();
            log.info("Mensagem Recebida via Socket: " + ISOUtil.hexString(request.pack()));

            var valor = request.getString(FieldUtil.VALOR_TRANSACAO);
            AtomicLong valorEmCentavos = new AtomicLong();

            try{
                valorEmCentavos.set(Long.parseLong(valor));
            }catch ( NumberFormatException e) {
                log.error("[AutorizadorSocketSimulator] Erro ao transformar valor da transacao ->",e);
                channel.send(IsoMsgFactory.toIsoMsgResponse(request,packager,CODIGO_ERRO_RESPONSE));
            }

            if(valorEmCentavos.get() > MIL_EM_CENTAVOS) {
                throw new RuntimeException();
            }

            if(ehPar(valorEmCentavos.get())) {
                log.info("[AutorizadorSocketSimulator] Sucesso na resposta valor eh par {}",valorEmCentavos.get());
                channel.send(IsoMsgFactory.toIsoMsgResponse(request,packager,CODIGO_SUCESSO_RESPONSE));
            }else{
                log.info("[AutorizadorSocketSimulator] Sucesso na resposta valor eh impar {}",valorEmCentavos.get());
                channel.send(IsoMsgFactory.toIsoMsgResponse(request,packager,CODIGO_ERRO_RESPONSE));
            }

        } catch (Exception e) {
            log.error("[AutorizadorSocketSimulator] Erro ao processar cliente", e);
            throw new RuntimeException();
        }
    }

    private boolean ehPar(long valor) {
        return valor % 2 == 0;
    }
}

