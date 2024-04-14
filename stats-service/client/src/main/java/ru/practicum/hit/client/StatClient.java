package ru.practicum.hit.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.hit.dto.EndpointHitDto;
import ru.practicum.hit.dto.ViewStatsDto;
import ru.practicum.hit.dto.exception.DateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatClient {
    private final WebClient webClient;
    private final DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatClient(String serverUrl) {
        this.webClient = WebClient.create(serverUrl);
    }

    public EndpointHitDto postEndpointHit(EndpointHitDto endpointHitDto) {
        return webClient.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(endpointHitDto)
                .retrieve()
                .bodyToMono(EndpointHitDto.class)
                .block();
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(dateTimeFormat))
                        .queryParam("end", end.format(dateTimeFormat))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(DateException::new))
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .block();
    }
}