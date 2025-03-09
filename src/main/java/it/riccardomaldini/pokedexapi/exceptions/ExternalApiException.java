package it.riccardomaldini.pokedexapi.exceptions;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String text, Exception e) {
        super(text, e);
    }
}
