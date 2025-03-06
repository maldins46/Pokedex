package it.riccardomaldini.Pokedex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PokedexApplication implements CommandLineRunner {
	private final int port;

    public PokedexApplication(@Value("${server.port:8080}") int port) {
        this.port = port;
    }

    public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Swagger UI available at: http://localhost:{}/swagger-ui.html", port);
	}
}
