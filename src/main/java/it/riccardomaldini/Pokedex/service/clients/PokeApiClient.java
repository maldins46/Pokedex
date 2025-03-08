package it.riccardomaldini.Pokedex.service.clients;

import it.riccardomaldini.Pokedex.exceptions.ExternalApiException;
import it.riccardomaldini.Pokedex.exceptions.PokemonNotFoundException;
import it.riccardomaldini.Pokedex.model.pokeapi.PokeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class PokeApiClient {
    private final WebClient webClient;

    public PokeApiClient(WebClient webClient, @Value("${pokeapi.url}") String pokeApiUrl) {
        this.webClient = webClient.mutate().baseUrl(pokeApiUrl).build();
    }

    public PokeApiResponse fetchPokemon(String name) {
        try {
            return webClient.get()
                    .uri("/{name}", name)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode() == HttpStatus.NOT_FOUND) {
                            return Mono.error(new PokemonNotFoundException(name));
                        }
                        return response.createException();
                    })
                    .bodyToMono(PokeApiResponse.class)
                    .blockOptional()
                    .orElseThrow(() -> new PokemonNotFoundException(name));

        } catch (WebClientResponseException | WebClientRequestException | ExternalApiException e) {
           throw new ExternalApiException("Failed to fetch data from PokeAPI.", e);
        }
    }
}
