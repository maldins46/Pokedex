package it.riccardomaldini.Pokedex.service;

import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import org.springframework.stereotype.Service;

@Service
public class FunTranslationsApiClient {
    public PokeApiResponse fetchYodaTranslation(String description) {
       throw new RuntimeException("Not implemented");
    }

    public PokeApiResponse fetchShakespeareTranslation(String description) {
        throw new RuntimeException("Not implemented");
    }
}
