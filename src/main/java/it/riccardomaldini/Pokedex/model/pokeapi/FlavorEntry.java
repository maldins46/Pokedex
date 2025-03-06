package it.riccardomaldini.Pokedex.model.pokeapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlavorEntry {
    private String flavorText;
    private Language language;
}
