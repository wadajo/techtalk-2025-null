package com.wadajo.nulltechtalk.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
        @ConfigureWireMock(port = 8888, baseUrlProperties = "${artworks.base-url}")
)
public class AcceptanceCriteriaIT {

    @LocalServerPort
    protected int port;

    public static final String OBRA_RAW_STUBBING_FILE = "src/test/resources/stubbing/obra-raw.json";

    @Test
    void debeDevolverUnaObraConFormatoCorrecto() throws IOException {
        JsonNode obraRaw = new ObjectMapper().readTree(new File(OBRA_RAW_STUBBING_FILE));

        stubFor(get("/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withJsonBody(obraRaw)
                ));

        when()
                .get(String.format("http://localhost:%s/obras",port))
                .prettyPeek()
        .then()
                .assertThat()
                .body("artist_title", Matchers.equalTo("Nasca"))
                .statusCode(200);
    }

}
