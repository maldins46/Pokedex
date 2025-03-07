package it.riccardomaldini.Pokedex.model.pokeapi;

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
    private Habitat habitat;
    private boolean isLegendary;
    private List<FlavorEntry> flavorTextEntries = new ArrayList<>();
}