package it.riccardomaldini.Pokedex.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.riccardomaldini.Pokedex.model.PokemonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pokemon Controller", description = "Retrieve data about Pokemon.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pokemon")
public class PokemonController {

    @Operation(summary = "Given a Pokemon name, returns standard Pokemon description and additional information.")
    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable String name) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "Given a Pokemon name, return translated Pokemon description and other basic information.")
    @GetMapping("/translated/{name}")
    public ResponseEntity<PokemonResponse> getTranslatedPokemon(@PathVariable String name) {
        throw new RuntimeException("Not implemented");
    }
}
