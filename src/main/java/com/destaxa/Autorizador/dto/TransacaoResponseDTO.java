package com.destaxa.Autorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransacaoResponseDTO {

    private String paymentId;

    private String value;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("transaction_hour")
    private String transactionHour;

    @JsonProperty("authorization_code")
    private String authorizationCode;
}
