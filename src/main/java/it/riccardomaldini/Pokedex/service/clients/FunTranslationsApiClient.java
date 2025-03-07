package it.riccardomaldini.Pokedex.service.clients;

import it.riccardomaldini.Pokedex.model.funtranslationsapi.FunTranslationsResponse;
import org.springframework.stereotype.Service;

@Service
public class FunTranslationsApiClient {
    public FunTranslationsResponse fetchYodaTranslation(String description) {
       throw new RuntimeException("Not implemented");
    }

    public FunTranslationsResponse fetchShakespeareTranslation(String description) {
        throw new RuntimeException("Not implemented");
    }
}
