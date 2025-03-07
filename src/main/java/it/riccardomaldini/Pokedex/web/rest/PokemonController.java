package it.riccardomaldini.Pokedex.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.riccardomaldini.Pokedex.service.PokemonTranslatedInfoService;
import it.riccardomaldini.Pokedex.web.rest.dto.PokemonInfo;
import it.riccardomaldini.Pokedex.service.PokemonBasicInfoService;
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
    private final PokemonBasicInfoService pokemonBasicService;
    private final PokemonTranslatedInfoService pokemonTranslatedInfoService;

    @Operation(summary = "Given a Pokemon name, returns standard Pokemon description and additional information.")
    @GetMapping("/{name}")
    public ResponseEntity<PokemonInfo> getPokemon(@PathVariable String name) {
        return ResponseEntity.ok(pokemonBasicService.getPokemonInfo(name));
    }

    @Operation(summary = "Given a Pokemon name, return translated Pokemon description and other basic information.")
    @GetMapping("/translated/{name}")
    public ResponseEntity<PokemonInfo> getTranslatedPokemon(@PathVariable String name) {
        return ResponseEntity.ok(pokemonTranslatedInfoService.getPokemonInfo(name));
    }
}
