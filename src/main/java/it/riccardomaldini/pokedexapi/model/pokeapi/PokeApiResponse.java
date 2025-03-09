package it.riccardomaldini.pokedexapi.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokeApiResponse {
    private String name;

    private Habitat habitat = new Habitat();

    @JsonProperty("is_legendary")
    private boolean legendary;

    @JsonProperty("flavor_text_entries")
    private List<FlavorEntry> flavorTextEntries = new ArrayList<>();
}