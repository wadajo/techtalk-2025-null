package com.wadajo.nulltechtalk.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wadajo.nulltechtalk.model.Obra;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

@Controller
public class GeneralController {

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

        // advierte que la respuesta puede llegar siendo null -ver docs de Spring
        var obraRandomEnLista = getObra(rawResponse);
        // error de compilación: el parámetro debe ser @NonNull por package-info

        return ResponseEntity.ok(obraRandomEnLista.getFirst());
    }

    private static List<Obra> getObra(String rawResponse) throws IOException {
        var mapper = getObjectMapper();
        var dataRawField = mapper.readTree(rawResponse).get("data");
        return mapper.readValue(dataRawField.traverse(), new TypeReference<>() {
        });
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
