package com.destaxa.Autorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {


    @NotNull(message = "External Id can't be null")
    @NotEmpty(message = "External Id can't be empty")
    @JsonProperty(value = "external_id")
    private String externalId;

    @NotNull(message = "Card Number can't be null")
    @NotEmpty(message = "Card Number can't be empty")
    @JsonProperty(value = "card_number")
    private String cardNumber;

    @NotNull(message = "Transaction value can't be null")
    @Length(min = 1, max = 12, message = "You Have to ajust Transaction value")
    private Double value;

    @NotNull(message = "Installments can't be null")
    private Integer installments;

    @NotNull(message = "Security Code can't be null")
    private Integer cvv;

    @NotNull(message = "Expiration month can't be null")
    @JsonProperty(value = "exp_month")
    private Integer expMonth;

    @NotNull(message = "Expiration Year can't be null")
    @JsonProperty(value = "exp_year")
    private Integer expYear;

    @NotNull(message = "Holder Name can't be null")
    @NotEmpty(message = "Holder Name can't be empty")
    @JsonProperty(value = "holder_name")
    private String holderName;
}

