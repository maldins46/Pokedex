package it.riccardomaldini.pokedexapi.service;

import it.riccardomaldini.pokedexapi.model.funtranslationsapi.Contents;
import it.riccardomaldini.pokedexapi.model.funtranslationsapi.FunTranslationsResponse;
import it.riccardomaldini.pokedexapi.service.clients.FunTranslationsApiClient;
import it.riccardomaldini.pokedexapi.web.rest.dto.PokemonInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PokemonTranslatedInfoServiceTest {
    @InjectMocks
    private PokemonTranslatedInfoService pokemonTranslatedInfoService;

    @Mock
    private PokemonBasicInfoService pokemonBasicInfoService;

    @Mock
    private FunTranslationsApiClient funTranslationsApiClient;

    @DisplayName("Given a Pokemon without cave habitat or legendary, when pokemon info is requested, verify the description is translated in shakespeare style.")
    @Test
    void givenPokemonWithoutCaveHabitatOrLegendaryWhenPokemonInfoRequestedVerifyDescriptionTranslatedInShakespeareStyle() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setHabitat("forest");
            pInfo.setLegendary(false);
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchShakespeareTranslation(Mockito.anyString())).thenReturn(new FunTranslationsResponse(new Contents("shakespeareTranslation")));

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("shakespeareTranslation", actual.getDescription());
    }

    @DisplayName("Given a Pokemon with cave habitat, when pokemon info is requested, verify the description is translated in yoda style.")
    @Test
    void givenPokemonWithCaveHabitatWhenPokemonInfoRequestedVerifyDescriptionTranslatedInYodaStyle() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setHabitat("cave");
            pInfo.setLegendary(false);
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchYodaTranslation(Mockito.anyString())).thenReturn(new FunTranslationsResponse(new Contents("yodaTranslation")));

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("yodaTranslation", actual.getDescription());
    }

    @DisplayName("Given a Pokemon legendary, when pokemon info is requested, verify the description is translated in yoda style.")
    @Test
    void givenPokemonLegendaryWhenPokemonInfoRequestedVerifyDescriptionTranslatedInYodaStyle() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setLegendary(true);
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchYodaTranslation(Mockito.anyString())).thenReturn(new FunTranslationsResponse(new Contents("yodaTranslation")));

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("yodaTranslation", actual.getDescription());
    }

    @DisplayName("Given a Pokemon that need yoda translation, when pokemon info is requested and translation fails, verify the description returned untranslated.")
    @Test
    void givenPokemonThatNeedsYodaTranslationWhenTranslationFailsVerifyDescriptionReturnedUntranslated() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setHabitat("forest");
            pInfo.setLegendary(true);
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchYodaTranslation(Mockito.anyString())).thenThrow(new RuntimeException());

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("untranslatedDescription", actual.getDescription());
    }

    @DisplayName("Given a Pokemon that need shakespeare translation, when pokemon info is requested and translation fails, verify the description returned untranslated.")
    @Test
    void givenPokemonThatNeedsShakespeareTranslationWhenTranslationFailsVerifyDescriptionReturnedUntranslated() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setHabitat("forest");
            pInfo.setLegendary(false);
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchShakespeareTranslation(Mockito.anyString())).thenThrow(new RuntimeException());

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("untranslatedDescription", actual.getDescription());
    }


    @DisplayName("Given any pokemon with valid fields, when pokemon info is requested, verify any other field beside description is returned without modifications.")
    @Test
    void givenAnyPokemonWhenPokemonInfoRequestedVerifyAnyOtherFieldBesideDescriptionIsReturned() {
        Mockito.when(pokemonBasicInfoService.getPokemonInfo("mewtwo")).thenAnswer(i -> {
            PokemonInfo pInfo = new PokemonInfo();
            pInfo.setHabitat("forest");
            pInfo.setLegendary(false);
            pInfo.setName("mewtwo");
            pInfo.setDescription("untranslatedDescription");
            return pInfo;
        });
        Mockito.when(funTranslationsApiClient.fetchShakespeareTranslation(Mockito.anyString())).thenReturn(new FunTranslationsResponse(new Contents("shakespeareTranslation")));

        PokemonInfo actual = pokemonTranslatedInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("mewtwo", actual.getName());
        Assertions.assertEquals("forest", actual.getHabitat());
        Assertions.assertFalse(actual.isLegendary());
    }

}