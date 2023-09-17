package com.test.crudjava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");

        Info info = new Info()
                .title("Teste backend WeFin")
                .version("1.0");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}
