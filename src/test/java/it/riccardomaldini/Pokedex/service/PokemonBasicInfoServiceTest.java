package it.riccardomaldini.Pokedex.service;

import it.riccardomaldini.Pokedex.model.pokeapi.FlavorEntry;
import it.riccardomaldini.Pokedex.model.pokeapi.Habitat;
import it.riccardomaldini.Pokedex.model.pokeapi.Language;
import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import it.riccardomaldini.Pokedex.service.clients.PokeApiClient;
import it.riccardomaldini.Pokedex.web.rest.dto.PokemonInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PokemonBasicInfoServiceTest {
    @Mock
    private PokeApiClient mockPokeApiClient;

    @InjectMocks
    private PokemonBasicInfoService pokemonBasicInfoService;

    @Test
    @DisplayName("Given a valid API response, when pokemon info is requested, verify the response is given correctly.")
    void givenValidApiResponseWhenInfoRequestedVerifyCorrectResponse() {
        Mockito.when(mockPokeApiClient.fetchPokemon("mewtwo")).thenAnswer(i ->  {
            PokeApiResponse response = new PokeApiResponse();
            response.setName("mewtwo");
            response.setHabitat(new Habitat("habitat"));
            response.setLegendary(true);
            response.setFlavorTextEntries(List.of(new FlavorEntry("description", new Language("en"))));
            return response;
        });

        PokemonInfo actual = pokemonBasicInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("mewtwo", actual.getName(), "Name is correctly populated");
        Assertions.assertEquals("habitat", actual.getHabitat(), "Habitat is correctly populated");
        Assertions.assertTrue(actual.isLegendary(), "Legendary field is correctly populated");
        Assertions.assertEquals("description", actual.getDescription(), "Description field is correctly populated");
    }

    @Test
    @DisplayName("Given an API response without a valid description field, when pokemon info is requested, verify a fallback empty description is given.")
    void givenResponseWithoutValidDescriptionWhenInfoRequestedVerifyFallbackDescription() {
        Mockito.when(mockPokeApiClient.fetchPokemon("mewtwo")).thenAnswer(i ->  {
            PokeApiResponse response = new PokeApiResponse();
            response.setFlavorTextEntries(List.of());
            return response;
        });

        PokemonInfo actual = pokemonBasicInfoService.getPokemonInfo("mewtwo");

        Assertions.assertNull(actual.getDescription(), "Description field is correctly populated as empty");
    }

    @Test
    @DisplayName("Given an API response without an english description field, when pokemon info is requested, verify a fallback empty description is given.")
    void givenResponseWithoutEnglishDescriptionWhenInfoRequestedVerifyFallbackDescription() {
        Mockito.when(mockPokeApiClient.fetchPokemon("mewtwo")).thenAnswer(i ->  {
            PokeApiResponse response = new PokeApiResponse();
            response.setFlavorTextEntries(List.of(new FlavorEntry("description", new Language("it"))));
            return response;
        });

        PokemonInfo actual = pokemonBasicInfoService.getPokemonInfo("mewtwo");

        Assertions.assertNull(actual.getDescription(), "Description field is correctly populated as empty");
    }

    @Test
    @DisplayName("Given an API response with description field that contains carriage return characters, when pokemon info is requested, verify characters are correctly replaced with space.")
    void givenResponseWithReturnCharactersDescriptionWhenInfoRequestedVerifyReturnCharactersRemoved() {
        Mockito.when(mockPokeApiClient.fetchPokemon("mewtwo")).thenAnswer(i ->  {
            PokeApiResponse response = new PokeApiResponse();
            response.setFlavorTextEntries(List.of(new FlavorEntry("description\n\fdescription", new Language("en"))));
            return response;
        });

        PokemonInfo actual = pokemonBasicInfoService.getPokemonInfo("mewtwo");

        Assertions.assertEquals("description  description", actual.getDescription(), "Description field is correctly populated without carriage returns");
    }

    @Test
    @DisplayName("Given an API response with invalid habitat field, when pokemon info is requested, verify a fallback empty value is given correctly.")
    void givenApiResponseWithInvalidHabitatWhenInfoRequestedVerifyCorrectFallback() {
        Mockito.when(mockPokeApiClient.fetchPokemon("mewtwo")).thenAnswer(i -> new PokeApiResponse());

        PokemonInfo actual = pokemonBasicInfoService.getPokemonInfo("mewtwo");

        Assertions.assertNull(actual.getHabitat(), "Habitat empty value is correctly populated");
    }
}