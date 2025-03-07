package it.riccardomaldini.Pokedex.service;

import it.riccardomaldini.Pokedex.model.funtranslationsapi.FunTranslationsResponse;
import it.riccardomaldini.Pokedex.web.rest.dto.PokemonInfo;
import it.riccardomaldini.Pokedex.service.clients.FunTranslationsApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PokemonTranslatedInfoService implements PokemonInfoService {
    private final PokemonBasicInfoService decoratedBasicInfoService;
    private final FunTranslationsApiClient translationsApiClient;

    @Override
    public PokemonInfo getPokemonInfo(String name) {
       throw new RuntimeException("To be implemented");
    }
}
