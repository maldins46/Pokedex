package it.riccardomaldini.pokedexapi.service;

import it.riccardomaldini.pokedexapi.model.funtranslationsapi.FunTranslationsResponse;
import it.riccardomaldini.pokedexapi.web.rest.dto.PokemonInfo;
import it.riccardomaldini.pokedexapi.service.clients.FunTranslationsApiClient;
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
        PokemonInfo pokemonInfo = decoratedBasicInfoService.getPokemonInfo(name);
        pokemonInfo.setDescription(obtainTranslatedDescription(pokemonInfo));
        return pokemonInfo;
    }

    private String obtainTranslatedDescription(PokemonInfo pokemonInfo) {
        String description;

        try {
            FunTranslationsResponse translationsResponse = needsYodaDescription(pokemonInfo)
                    ? translationsApiClient.fetchYodaTranslation(pokemonInfo.getDescription())
                    : translationsApiClient.fetchShakespeareTranslation(pokemonInfo.getDescription());

            description = translationsResponse.getContents().getTranslated();
        
        } catch (Exception e) {
            description = pokemonInfo.getDescription();
        }

        return description;
    }

    private boolean needsYodaDescription(PokemonInfo pokemonInfo) {
        return Objects.equals(pokemonInfo.getHabitat(), "cave") || pokemonInfo.isLegendary();
    }
}
