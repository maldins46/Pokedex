package it.riccardomaldini.Pokedex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server()
                .url("http://localhost:8080")
                .description("Development");

        Contact contact = new Contact()
                .name("Riccardo Maldini")
                .email("riccardo.maldini@gmail.com")
                .url("https://www.riccardomaldini.it");

        Info information = new Info()
                .title("Pokedex API")
                .version("1.0")
                .description("Pokedex software engineering challenge.")
                .contact(contact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}