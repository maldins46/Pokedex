package it.riccardomaldini.pokedexapi.service.clients;

import it.riccardomaldini.pokedexapi.exceptions.ExternalApiException;
import it.riccardomaldini.pokedexapi.model.funtranslationsapi.FunTranslationsResponse;
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

class FunTranslationsApiClientTest {
    private static MockWebServer mockWebServer;
    private static FunTranslationsApiClient funTranslationsApiClient;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Inject Mock Server URL into WebClient
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString()) // Override base URL for test
                .build();

        funTranslationsApiClient = new FunTranslationsApiClient(webClient, mockWebServer.url("/").toString());
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }


    @DisplayName("Given a Yoda translation request, when API responds correctly, verify the response is correctly outputted.")
    @Test
    void testFetchYodaTranslationSuccess() {
        String jsonResponse = """
             {
                 "contents": { "translated": "Yoda Translation" }
             }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        FunTranslationsResponse response = funTranslationsApiClient.fetchYodaTranslation("pikachu");

        assertNotNull(response);
        assertEquals("Yoda Translation", response.getContents().getTranslated());
    }

    @DisplayName("Given a Yoda translation request, when API responds with 500 error code, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchYodaTranslationInternalServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("Internal Server Error"));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchYodaTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("500"));
    }


    @DisplayName("Given a Yoda translation request, when API responds with 404 error code, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchYodaTranslationNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody("Not found"));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchYodaTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("404"));
    }

    @DisplayName("Given a Yoda translation request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchYodaTranslationConnectionFailure() {
        mockWebServer.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchYodaTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("Connection prematurely closed"));
    }

    @DisplayName("Given a Yoda translation request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchFetchYodaTranslationInvalidJson() {
        mockWebServer.enqueue(new MockResponse().setBody("{ invalid json }").setResponseCode(200));

        assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchYodaTranslation("pikachu"));
    }


    @DisplayName("Given a Shakespeare translation request, when API responds correctly, verify the response is correctly outputted.")
    @Test
    void testFetchShakespeareTranslationSuccess() {
        String jsonResponse = """
             {
                 "contents": { "translated": "Shakespeare Translation" }
             }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        FunTranslationsResponse response = funTranslationsApiClient.fetchShakespeareTranslation("pikachu");

        assertNotNull(response);
        assertEquals("Shakespeare Translation", response.getContents().getTranslated());
    }

    @DisplayName("Given a Shakespeare translation request, when API responds with 500 error code, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchShakespeareTranslationInternalServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("Internal Server Error"));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchShakespeareTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("500"));
    }


    @DisplayName("Given a Shakespeare translation request, when API responds with 404 error code, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchShakespeareTranslationNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody("Not found"));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchShakespeareTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("404"));
    }

    @DisplayName("Given a Shakespeare translation request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchShakespeareTranslationConnectionFailure() {
        mockWebServer.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        Exception exception = assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchShakespeareTranslation("pikachu"));
        assertTrue(exception.getCause().getMessage().contains("Connection prematurely closed"));
    }

    @DisplayName("Given a Shakespeare translation request, when the client is not able to contact the API, verify a generic API exception with specific details is arisen.")
    @Test
    void testFetchFetchShakespeareTranslationInvalidJson() {
        mockWebServer.enqueue(new MockResponse().setBody("{ invalid json }").setResponseCode(200));

        assertThrows(ExternalApiException.class, () -> funTranslationsApiClient.fetchShakespeareTranslation("pikachu"));
    }
}