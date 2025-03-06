package com.destaxa.Autorizador.config;

import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PackagerFieldsConfig {

    @Bean
    public ISOPackager serverSocket() throws ISOException {
        try{
            return new GenericPackager(getClass().getClassLoader().getResourceAsStream("fields.xml"));
        }catch (ISOException e) {
            log.error("[PackagerFieldsConfig] Erro ao injetar bean GenericPackager");
            throw e;
        }
    }
}
