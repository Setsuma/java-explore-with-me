package ru.practicum.hit.server.endpointhit.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.hit.dto.ViewStatsDto;
import ru.practicum.hit.server.endpointhit.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value = "SELECT NEW ru.practicum.hit.dto.ViewStatsDto(hits.app, hits.uri, COUNT(hits.ip)) "
            + "FROM EndpointHit AS hits "
            + "WHERE (((:uris) IS NULL) OR (hits.uri IN (:uris))) "
            + "      AND (hits.timestamp BETWEEN (:start) AND (:end)) "
            + "GROUP BY hits.app, "
            + "         hits.uri ")
    List<ViewStatsDto> collectEndpointHitStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris,
            Sort sort);

    @Query(value = "SELECT NEW ru.practicum.hit.dto.ViewStatsDto(hits.app, hits.uri, COUNT(DISTINCT hits.ip)) "
            + "FROM EndpointHit AS hits "
            + "WHERE (((:uris) IS NULL) OR (hits.uri IN (:uris))) "
            + "      AND (hits.timestamp BETWEEN (:start) AND (:end)) "
            + "GROUP BY hits.app, "
            + "         hits.uri ")
    List<ViewStatsDto> collectUniqueEndpointStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris,
            Sort sort);
}