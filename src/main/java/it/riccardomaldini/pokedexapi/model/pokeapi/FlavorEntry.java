package it.riccardomaldini.pokedexapi.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlavorEntry {
    @JsonProperty("flavor_text")
    private String flavorText;

    private Language language = new Language();
}
