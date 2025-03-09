package it.riccardomaldini.pokedexapi.exceptions;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String pokemonName) {
        super(String.format("Pokemon '%s' not found.", pokemonName));
    }
}
