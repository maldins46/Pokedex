package it.riccardomaldini.pokedexapi.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.riccardomaldini.pokedexapi.model.pokeapi.FlavorEntry;
import it.riccardomaldini.pokedexapi.model.pokeapi.PokeApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonInfo {
    private String name;
    private String description;
    private String habitat;

    @JsonProperty("isLegendary")
    private boolean legendary;

    /**
     * Factory method to obtain a PokemonResponse from a PokeApiResponse.
     */
    public static PokemonInfo of(PokeApiResponse pokeApiResponse) {
        String habitat = pokeApiResponse.getHabitat() != null ? pokeApiResponse.getHabitat().getName() : "unknown";
        String description = pokeApiResponse.getFlavorTextEntries().stream()
                .filter(entry -> "en".equals(entry.getLanguage().getName()))
                .findFirst()
                .map(FlavorEntry::getFlavorText)
                .orElse("No description available.");

        return new PokemonInfo(pokeApiResponse.getName(), description, habitat, pokeApiResponse.isLegendary());
    }
}