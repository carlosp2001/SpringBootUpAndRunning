package com.example.planefinderjpa.component;

import com.example.planefinderjpa.model.Aircraft;
import com.example.planefinderjpa.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class PlaneFinder {
    WebClient client = WebClient.create("http://localhost:7634/aircraft");
    private final AircraftRepository repository;

    @Scheduled(fixedRate = 5000)
    public void pollPlanes() {
        System.out.println("Polling planes");
        repository.deleteAll();
        System.out.println("Deleted all planes");

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(aircraft -> !aircraft.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);

        repository.findAll().forEach(System.out::println);
    }
}
