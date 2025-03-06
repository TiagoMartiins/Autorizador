package com.destaxa.Autorizador.factory;

import com.destaxa.Autorizador.entity.Transacao;
import com.destaxa.Autorizador.enums.CodigoProcessamentoEnum;
import com.destaxa.Autorizador.utils.FieldUtil;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import java.util.Objects;

import static com.destaxa.Autorizador.utils.DateUtil.pegarDataFormato;

public class IsoMsgFactory {

    public static ISOMsg toIsoMsg(Transacao transacao, ISOPackager packager) throws ISOException {
        ISOMsg isoMsg = new ISOMsg();

        isoMsg.setPackager(packager);
        isoMsg.set(FieldUtil.NUMERO_CARTAO, transacao.getNumeroCartao());
        isoMsg.set(FieldUtil.CODIGO_PROCESSAMENTO, Objects.nonNull(transacao.getNumeroParcelas()) ? retornarCodigoProcessamentoAcordoParcelas(transacao.getNumeroParcelas()) : "");
        double valorEmReais = transacao.getValorTransacao();
        long valorEmCentavos = Math.round(valorEmReais * 100);
        isoMsg.set(FieldUtil.VALOR_TRANSACAO, String.format("%012d", valorEmCentavos));
        isoMsg.set(FieldUtil.DATA_HORA_TRANSMISSAO, pegarDataFormato(transacao.getDataHoraTransmissao(),"MMddHHmmss"));
        isoMsg.set(FieldUtil.NSU, transacao.getNsu());
        isoMsg.set(FieldUtil.HORA_LOCAL, pegarDataFormato(transacao.getDataHoraTransmissao(),"HHmmss"));
        isoMsg.set(FieldUtil.DATA_LOCAL, pegarDataFormato(transacao.getDataHoraTransmissao(),"MMdd"));
        isoMsg.set(FieldUtil.DATA_VENCIMENTO, transacao.getDataVencimentoCartao());
        isoMsg.set(FieldUtil.NUMERO_PARCELAS, transacao.getNumeroParcelas().toString());
        isoMsg.set(FieldUtil.NSU_HOST, "Host");
        isoMsg.set(FieldUtil.MODO_ENTRADA, "000");
        isoMsg.set(FieldUtil.CODIGO_ESTABELECIMENTO, "MUITOCAMPO");
        isoMsg.set(FieldUtil.CODIGO_SEGURANCA, "muitaCOISA");
        isoMsg.set(FieldUtil.NUMERO_PARCELAS, retornarNumeroParcelas(transacao.getNumeroParcelas()));

        return isoMsg;
    }

    private static String retornarCodigoProcessamentoAcordoParcelas(Integer numeroParcelas) {
        return numeroParcelas <= 1 ? CodigoProcessamentoEnum.CREDITO_A_VISTA.getCode() : CodigoProcessamentoEnum.CREDITO_PARCELADO.getCode();
    }

    private static String retornarNumeroParcelas(Integer numeroParcelas) {
        return Objects.nonNull(numeroParcelas) && numeroParcelas <= 1 ? "01" : "";
    }

    public static ISOMsg toIsoMsgResponse(ISOMsg request, ISOPackager packager, String codigoResposta) {
        ISOMsg isoMsg = new ISOMsg();

        isoMsg.setPackager(packager);
        isoMsg.set(FieldUtil.VALOR_TRANSACAO, request.getString(FieldUtil.VALOR_TRANSACAO));
        isoMsg.set(FieldUtil.DATA_HORA_TRANSMISSAO, request.getString(FieldUtil.DATA_HORA_TRANSMISSAO));
        isoMsg.set(FieldUtil.NSU, request.getString(FieldUtil.NSU));
        isoMsg.set(FieldUtil.HORA_LOCAL, request.getString(FieldUtil.HORA_LOCAL));
        isoMsg.set(FieldUtil.DATA_LOCAL, request.getString(FieldUtil.DATA_LOCAL));
        isoMsg.set(FieldUtil.CODIGO_AUTORIZACAO, "OK");
        isoMsg.set(FieldUtil.CODIGO_RESPOSTA, codigoResposta);
        isoMsg.set(FieldUtil.CODIGO_ESTABELECIMENTO, "PQTANTOCAMPO");
        isoMsg.set(FieldUtil.NSU_HOST, "Host");

        return isoMsg;
    }
}
