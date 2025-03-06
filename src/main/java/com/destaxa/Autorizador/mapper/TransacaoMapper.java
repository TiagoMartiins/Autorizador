package com.destaxa.Autorizador.mapper;

import com.destaxa.Autorizador.dto.TransacaoDTO;
import com.destaxa.Autorizador.dto.TransacaoResponseDTO;
import com.destaxa.Autorizador.entity.Transacao;
import com.destaxa.Autorizador.utils.DateUtil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Mapper(componentModel = "spring",
builder = @Builder(disableBuilder = true))
public interface TransacaoMapper {

    TransacaoMapper instance = Mappers.getMapper(TransacaoMapper.class);


    @Mappings({
            @Mapping(source = "externalId", target = "nsu"),
            @Mapping(source = "cardNumber", target = "numeroCartao"),
            @Mapping(source = "value", target = "valorTransacao"),
            @Mapping(source = "installments", target = "numeroParcelas"),
            @Mapping(source = "cvv", target = "codigoSeguranca"),
            @Mapping(source = "expMonth", target = "dataVencimentoCartao", qualifiedByName = "formatExpDate")
    })
    Transacao fromDTO(TransacaoDTO dto);

    @Named(value = "formatExpDate")
    default String formatExpDate(Integer expMonth) {
        return expMonth != null ? String.format("%02dXX", expMonth) : null;
    }

    @Mappings({
            @Mapping(source = "valorTransacao", target = "value", qualifiedByName = "toBrl"),
            @Mapping(source = "codigoResposta", target = "responseCode"),
            @Mapping(source = "dataHoraTransmissao", target = "transactionDate", qualifiedByName = "dateToStringDate"),
            @Mapping(source = "dataHoraTransmissao", target = "transactionHour", qualifiedByName = "dateToStringHours")
    })
    TransacaoResponseDTO toResponse(Transacao transacao);

    @AfterMapping
    default void after(Transacao transacao, @MappingTarget TransacaoResponseDTO response) {
        response.setPaymentId(Objects.nonNull(transacao.getId()) ? transacao.getId().toString() : null);
    }


    @Named("toBrl")
    default String toBrl(Double valor){
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(valor);
    }

    @Named("dateToStringDate")
    default String dateToStringDate(Date data){
        return DateUtil.pegarDataFormato(data,"yy-MM-dd");
    }

    @Named("dateToStringHours")
    default String dateToStringHours(Date data){
        return DateUtil.pegarDataFormato(data,"HH:mm:ss");
    }
}
