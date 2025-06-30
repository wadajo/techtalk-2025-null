package com.wadajo.nulltechtalk;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;

@Controller
public class GeneralController {

    @GetMapping("/obras")
    ResponseEntity<Obra> getRandomArtwork() {

        return ResponseEntity.ok(
                new Obra("Nasca","Fragment", Year.of(600),null));
    }

}
