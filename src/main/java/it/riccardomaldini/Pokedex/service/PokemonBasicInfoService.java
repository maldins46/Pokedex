package it.riccardomaldini.Pokedex.service;

import it.riccardomaldini.Pokedex.model.pokeapi.FlavorEntry;
import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import it.riccardomaldini.Pokedex.web.rest.dto.PokemonInfo;
import it.riccardomaldini.Pokedex.service.clients.PokeApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class PokemonBasicInfoService implements PokemonInfoService {
    private final PokeApiClient pokeApiClient;

    public PokemonInfo getPokemonInfo(String name) {
        PokeApiResponse apiResponse = pokeApiClient.fetchPokemon(name);
        return obtainPokemonInfo(apiResponse);
    }

    private PokemonInfo obtainPokemonInfo(PokeApiResponse apiResponse) {
        String habitat = obtainHabitat(apiResponse);
        String description = obtainDescription(apiResponse);

        return new PokemonInfo(apiResponse.getName(), description, habitat, apiResponse.isLegendary());
    }

    private String obtainDescription(PokeApiResponse apiResponse) {
        return apiResponse.getFlavorTextEntries().stream()
                .filter(entry -> "en".equals(entry.getLanguage().getName()))
                .findFirst()
                .map(FlavorEntry::getFlavorText)
                .orElse("No description available.");
    }

    private String obtainHabitat(PokeApiResponse apiResponse) {
        return apiResponse.getHabitat() != null ? apiResponse.getHabitat().getName() : "unknown";
    }
}
