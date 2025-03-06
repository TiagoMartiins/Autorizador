package com.destaxa.Autorizador.controller;

import com.destaxa.Autorizador.dto.TransacaoDTO;
import com.destaxa.Autorizador.dto.TransacaoResponseDTO;
import com.destaxa.Autorizador.exception.BusinessException;
import com.destaxa.Autorizador.mapper.TransacaoMapper;
import com.destaxa.Autorizador.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/payment")
public class PagamentoController {

    private final PagamentoService pagamentoService;


    @RequestMapping(method = RequestMethod.POST)
    @Operation(description = "Make a transaction with credit card" ,summary = "Make a transaction with credit card")
    public ResponseEntity<TransacaoResponseDTO> autorizarTransacao(@RequestBody @Valid TransacaoDTO request) throws BusinessException {

        var response = pagamentoService.autorizarTransacao(TransacaoMapper.instance.fromDTO(request));

        try {
            var transacao = response.get();
            return ResponseEntity.ok(TransacaoMapper.instance.toResponse(transacao));
        } catch (InterruptedException | ExecutionException e) {
            log.error("[PagamentoController] Erro ao salvar transacao ->",e);
            throw new BusinessException("Erro ao salvar transacao, por favor tente novamente");
        }
    }
}
