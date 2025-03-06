package com.destaxa.Autorizador.entity;

import com.destaxa.Autorizador.enums.CodigoProcessamentoEnum;
import com.destaxa.Autorizador.utils.DateUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Slf4j
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 19)
    private String numeroCartao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CodigoProcessamentoEnum codigoProcessamento;

    @Column(nullable = false)
    private Double valorTransacao;

    @Column(nullable = false)
    private Date dataHoraTransmissao;

    @Column(nullable = false, length = 6)
    private String nsu = "nsuHost";

    @Column(nullable = false, length = 6)
    private String horaLocalTransacao;

    @Column(nullable = false, length = 10)
    private String dataLocalTransacao;

    @Column(length = 4)
    private String dataVencimentoCartao;

    @Column(nullable = false, length = 3)
    private String modoEntrada;

    @Column(nullable = false, length = 15)
    private String codigoEstabelecimento;

    @Column(length = 10)
    private String codigoSeguranca;

    @Column(nullable = false, length = 2)
    private Integer numeroParcelas;

    @Column(nullable = false)
    private String codigoResposta;

    public Transacao() {
        iniciar();
    }

    @PrePersist
    public void beforeInsert() {
        this.codigoProcessamento = this.numeroParcelas <= 1 ? CodigoProcessamentoEnum.CREDITO_A_VISTA : CodigoProcessamentoEnum.CREDITO_PARCELADO;
        this.codigoEstabelecimento = "estabelecimento";
        this.modoEntrada = "lol";
    }

    @PostConstruct
    public void iniciar() {
        try{
            this.dataHoraTransmissao = DateUtil.getDataAtual();
            this.horaLocalTransacao = DateUtil.pegarhora(dataHoraTransmissao);
            this.dataLocalTransacao = DateUtil.pegarData(dataHoraTransmissao);
        } catch (Exception e) {
            log.error("[Transacao] Erro ao inicializar valores dataHora e horaLocal Transacao ->",e);
        }
    }
}
