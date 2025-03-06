package it.riccardomaldini.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponse {
    private String name;
    private String description;
    private String habitat;

    @JsonProperty("isLegendary")
    private boolean legendary;
}