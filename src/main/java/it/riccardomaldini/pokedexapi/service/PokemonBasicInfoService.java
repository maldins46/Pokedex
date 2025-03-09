package it.riccardomaldini.pokedexapi.service;

import it.riccardomaldini.pokedexapi.model.pokeapi.FlavorEntry;
import it.riccardomaldini.pokedexapi.model.pokeapi.PokeApiResponse;
import it.riccardomaldini.pokedexapi.web.rest.dto.PokemonInfo;
import it.riccardomaldini.pokedexapi.service.clients.PokeApiClient;
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
        return new PokemonInfo(
                apiResponse.getName(),
                obtainDescription(apiResponse),
                    apiResponse.getHabitat().getName(),
                apiResponse.isLegendary());
    }

    private String obtainDescription(PokeApiResponse apiResponse) {
        return apiResponse.getFlavorTextEntries().stream()
                .filter(entry -> "en".equals(entry.getLanguage().getName()))
                .findFirst()
                .map(FlavorEntry::getFlavorText)
                .map(x -> x.replace("\n", " ").replace("\f", " "))
                .orElse(null);
    }
}
