package com.wadajo.nulltechtalk.acceptance;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceCriteriaIT {

    @LocalServerPort
    protected int port;

    @Test
    void debeDevolverUnaObraConFormatoCorrecto() {
        when()
                .get(String.format("http://localhost:%s/obras",port))
                .prettyPeek()
        .then()
                .assertThat()
                .body("[0].artist_title", Matchers.equalTo("Nasca"))
                .statusCode(200);

    }

}
