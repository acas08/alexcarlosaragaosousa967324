package com.seplag.acervo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component("argus")
public class ArgusHealthCheckIndicator implements HealthIndicator {

    private final String url;
    private final HttpClient httpClient;

    public ArgusHealthCheckIndicator(@Value("${integracoes.argus.base-url}") String url) {
        this.url = url;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    @Override
    public Health health() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(3))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> resp = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
            int code = resp.statusCode();

            if (code >= 200 && code < 400) {
                return Health.up().withDetail("statusCode", code).build();
            }

            return Health.down()
                    .withDetail("statusCode", code)
                    .build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
