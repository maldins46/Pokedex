package it.riccardomaldini.pokedexapi.service;

import it.riccardomaldini.pokedexapi.web.rest.dto.PokemonInfo;

public interface PokemonInfoService {
    PokemonInfo getPokemonInfo(String name);
}
