package it.riccardomaldini.pokedexapi.service.clients;

import it.riccardomaldini.pokedexapi.exceptions.ExternalApiException;
import it.riccardomaldini.pokedexapi.model.funtranslationsapi.FunTranslationsRequest;
import it.riccardomaldini.pokedexapi.model.funtranslationsapi.FunTranslationsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class FunTranslationsApiClient {
    private final WebClient webClient;

    public FunTranslationsApiClient(WebClient webClient, @Value("${funtranslationsapi.url}") String pokeApiUrl) {
        this.webClient = webClient.mutate().baseUrl(pokeApiUrl).build();
    }

    public FunTranslationsResponse fetchYodaTranslation(String description) {
        return fetchTranslation(description, "yoda");
    }

    public FunTranslationsResponse fetchShakespeareTranslation(String description) {
        return fetchTranslation(description, "shakespeare");
    }

    private FunTranslationsResponse fetchTranslation(String description, String translationType) {
        try {
            FunTranslationsRequest request = new FunTranslationsRequest(description);
            return webClient.post()
                    .uri("/{translation-type}", translationType)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(FunTranslationsResponse.class)
                    .blockOptional()
                    .orElseThrow(() -> new ExternalApiException("Empty description returned for " + description, null));

        } catch (WebClientResponseException | WebClientRequestException | ExternalApiException e) {
            throw new ExternalApiException("Failed to fetch data from FunTranslationsAPI.", e);
        }
    }
}
