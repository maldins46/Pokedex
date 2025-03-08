package it.riccardomaldini.Pokedex.exceptions;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String pokemonName) {
        super(String.format("Pokemon '%s' not found.", pokemonName));
    }
}
