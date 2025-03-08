package it.riccardomaldini.Pokedex.service.clients;

import it.riccardomaldini.Pokedex.exceptions.ExternalApiException;
import it.riccardomaldini.Pokedex.exceptions.PokemonNotFoundException;
import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class PokeApiClientTest {
    private static MockWebServer mockWebServer;
    private static PokeApiClient pokeApiClient;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Inject Mock Server URL into WebClient
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString()) // Override base URL for test
                .build();

        pokeApiClient = new PokeApiClient(webClient, mockWebServer.url("/").toString());
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("Given a PokeAPI request, when API responds correctly, verify the response is correctly outputted.")
    @Test
    void testFetchPokemonSuccess() {
        String jsonResponse = """
            {
                "name": "pikachu",
                "habitat": { "name": "forest" },
                "is_legendary": false,
                "flavor_text_entries": [
                    { "flavor_text": "Pikachu is an electric mouse.", "language": { "name": "en" } }
                ]
            }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        PokeApiResponse response = pokeApiClient.fetchPokemon("pikachu");

        assertNotNull(response);
        assertEquals("pikachu", response.getName());
        assertEquals("forest", response.getHabitat().getName());
        assertFalse(response.isLegendary());
        assertEquals(1, response.getFlavorTextEntries().size());
        assertEquals("Pikachu is an electric mouse.", response.getFlavorTextEntries().get(0).getFlavorText());
        assertEquals("en", response.getFlavorTextEntries().get(0).getLanguage().getName());
    }

    @DisplayName("Given a PokeAPI request, when API responds 404, verify a specific not-found-exception is arisen.")
    @Test
    void testFetchPokemonNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        Exception exception = assertThrows(PokemonNotFoundException.class, () -> pokeApiClient.fetchPokemon("unknownPokemon"));
        assertEquals("Pokemon 'unknownPokemon' not found.", exception.getMessage());
    }

    @DisplayName("Given a PokeAPI request, when API responds 500, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchPokemonInternalServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("Internal Server Error"));

        Exception exception = assertThrows(ExternalApiException.class, () -> pokeApiClient.fetchPokemon("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("500"));
    }

    @DisplayName("Given a PokeAPI request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchPokemonConnectionFailure() {
        mockWebServer.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        Exception exception = assertThrows(ExternalApiException.class, () -> pokeApiClient.fetchPokemon("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("Connection prematurely closed"));
    }

    @DisplayName("Given a PokeAPI request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchPokemonInvalidJson() {
        mockWebServer.enqueue(new MockResponse().setBody("{ invalid json }").setResponseCode(200));

        assertThrows(ExternalApiException.class, () -> pokeApiClient.fetchPokemon("pikachu"));
    }
}
