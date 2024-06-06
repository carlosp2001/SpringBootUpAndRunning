package com.example.planefinder.service;

import com.example.planefinder.model.Aircraft;
import com.example.planefinder.repository.AircraftRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private final WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory connectionFactory;
    //    private final RedisOperations<String, Aircraft> redisOperations;
    private final AircraftRepository repository;

    public PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                             AircraftRepository repository) {
        this.connectionFactory = connectionFactory;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 15000)
    private void pollPlanes() {
        // Ayuda a eliminar toda la información de la base de datos, limpia todas las claves y sus valores
//        connectionFactory.getConnection().serverCommands().flushDb();

        // Obtenemos los datos de la API y los almacenamos en redis
        // Implemetanción usando templates de redis
//        client.get().retrieve().bodyToFlux(Aircraft.class).filter(ac -> !ac.getReg().isEmpty())
//                .toStream().forEach(ac  -> {
//                    redisOperations.opsForValue().set(ac.getReg(), ac);
//                });

        // Implementación usando repositorios de Spring Data
        client.get().retrieve().bodyToFlux(Aircraft.class).filter(ac -> !ac.getReg().isEmpty())
                .toStream().forEach(repository::save);

        // Obtenemos los datos de redis y los mostramos en consola, implementación realizada con templates de redis
//        Objects.requireNonNull(redisOperations.opsForValue()
//                        .getOperations()
//                        .keys("*"), "No aircraft found in Redis!")
//                .forEach(ac -> {
//                    System.out.println("Aircraft found in Redis: " + ac);
//                    System.out.println(redisOperations.opsForValue().get(ac));
//                });

        // Obtenemos los datos de redis y los mostramos en consola, implementación realizada con repositorios de Spring Data
        repository.findAll().forEach(System.out::println);
    }
}
