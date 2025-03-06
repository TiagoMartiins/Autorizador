package com.destaxa.Autorizador.mapper;

import com.destaxa.Autorizador.dto.TransacaoDTO;
import com.destaxa.Autorizador.dto.TransacaoResponseDTO;
import com.destaxa.Autorizador.entity.Transacao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T09:26:54-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class TransacaoMapperImpl implements TransacaoMapper {

    @Override
    public Transacao fromDTO(TransacaoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transacao transacao = new Transacao();

        transacao.setNsu( dto.getExternalId() );
        transacao.setNumeroCartao( dto.getCardNumber() );
        transacao.setValorTransacao( dto.getValue() );
        transacao.setNumeroParcelas( dto.getInstallments() );
        if ( dto.getCvv() != null ) {
            transacao.setCodigoSeguranca( String.valueOf( dto.getCvv() ) );
        }
        transacao.setDataVencimentoCartao( formatExpDate( dto.getExpMonth() ) );

        return transacao;
    }

    @Override
    public TransacaoResponseDTO toResponse(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }

        TransacaoResponseDTO transacaoResponseDTO = new TransacaoResponseDTO();

        transacaoResponseDTO.setValue( toBrl( transacao.getValorTransacao() ) );
        transacaoResponseDTO.setResponseCode( transacao.getCodigoResposta() );
        transacaoResponseDTO.setTransactionDate( dateToStringDate( transacao.getDataHoraTransmissao() ) );
        transacaoResponseDTO.setTransactionHour( dateToStringHours( transacao.getDataHoraTransmissao() ) );

        after( transacao, transacaoResponseDTO );

        return transacaoResponseDTO;
    }
}
