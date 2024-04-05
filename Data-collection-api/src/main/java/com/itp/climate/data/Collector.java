package com.itp.climate.data;

import com.itp.openapi.model.V1ForecastGet200Response;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Collector {
    private final WebClient webClient;
    public Collector(){
        this.webClient = WebClient.create("https://api.open-meteo.com/v1/forecast");
}

    public Mono<V1ForecastGet200Response> getForecast() {
        return this.webClient.get()
                .uri(builder -> builder
                        .queryParam("latitude", "52.52")
                        .queryParam("longitude", "13.41")
                        .queryParam("hourly", "temperature_2m,weather_code,pressure_msl,cloud_cover,cloud_cover_low")
                        .build())
                .retrieve()
                .bodyToMono(V1ForecastGet200Response.class); // Get the response as a String
    }
    
}
