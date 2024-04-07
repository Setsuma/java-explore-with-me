package ru.practicum.ewm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.hit.client.StatClient;

@Configuration
public class StatClientConf {
    @Value("${stats-service.url}")
    private String url;

    @Bean
    StatClient statClient() {
        return new StatClient(url);
    }
}
