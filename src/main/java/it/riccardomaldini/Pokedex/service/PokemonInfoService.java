package it.riccardomaldini.Pokedex.service;

import it.riccardomaldini.Pokedex.web.rest.dto.PokemonInfo;

public interface PokemonInfoService {
    PokemonInfo getPokemonInfo(String name);
}
