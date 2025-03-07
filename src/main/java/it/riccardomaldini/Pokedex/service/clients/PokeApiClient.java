package it.riccardomaldini.Pokedex.service.clients;

import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import org.springframework.stereotype.Service;

@Service
public class PokeApiClient {
    public PokeApiResponse fetchPokemon(String name) {
       throw new RuntimeException("Not implemented");
    }
}
