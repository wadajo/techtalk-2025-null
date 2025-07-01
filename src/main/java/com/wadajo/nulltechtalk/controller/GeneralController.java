package com.wadajo.nulltechtalk.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wadajo.nulltechtalk.model.Obra;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class GeneralController {

    private static final Logger log = LoggerFactory.getLogger(GeneralController.class);

    @Value("${artworks.base-url}")
    @SuppressWarnings("NullAway.Init")
    private String endpointUrl;

    @SuppressWarnings("NullAway.Init")
    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(endpointUrl)
                .build();
    }

    @GetMapping("/obras")
    ResponseEntity<Obra> getRandomArtwork() throws IOException {
        String rawResponse = restClient.get()
                .retrieve()
                .body(String.class);

        Assert.notNull(rawResponse, "rawResponse is null");
        var obraRandom = getObra(rawResponse);

        log.info("Título de la obra: "+obraRandom.title());
        log.info("¿El año de la obra fue bisiesto?: "+obraRandom.date_end().isLeap());
        if (Objects.nonNull(obraRandom.description())) {
            log.info("Longitud de la descripción de la obra: "+obraRandom.description().length());
        }

        return ResponseEntity.ok(obraRandom);
    }

    private static Obra getObra(String rawResponse) throws IOException {
        var mapper = getObjectMapper();
        var dataRawField = mapper.readTree(rawResponse).get("data");
        return mapper.readValue(dataRawField.traverse(), new TypeReference<List<Obra>>() {
        }).getFirst();
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
