package com.example.sburrestdemo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SburRestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SburRestDemoApplication.class, args);
    }

    // Podemos crear un bean dentro de spring boot application runner porque posee la anotación @Configuration
    @Bean
    @ConfigurationProperties(prefix = "droid")
    Droid createDroid() {
        return new Droid();
    }
}

@Setter
@Getter
@AllArgsConstructor
@Entity
class Coffee {
    @Id
    String id;
    String name;

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Coffee() {

    }

}

interface CoffeeRepository extends CrudRepository<Coffee, String> {
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
    //Implementación sin base de datos
//    private final List<Coffee> coffees = new ArrayList<>();
    final CoffeeRepository coffeeRepository;

    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        // Implementación sin base de datos
//        coffees.addAll(List.of(
//                new Coffee("Café Cereza"),
//                new Coffee("Café Ganador"),
//                new Coffee("Café Lareño"),
//                new Coffee("Café Três Pontas")
//        ));

        // Implementación con base de datos
        this.coffeeRepository = coffeeRepository;
        this.coffeeRepository.saveAll(List.of(
                new Coffee("Café Cereza"),
                new Coffee("Café Ganador"),
                new Coffee("Café Lareño"),
                new Coffee("Café Três Pontas")
        ));


    }

    //    @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping()
    // La anotación GetMapping es una abreviación de RequestMapping con el método GET
    Iterable<Coffee> getCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return coffeeRepository.findById(id);
    }

    // Crea el recurso
    @PostMapping()
    Coffee postCoffee(@RequestBody Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {

        return (!coffeeRepository.existsById(id)) ?
                new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffeeRepository.deleteById(id);
    }
}

@RestController
@RequestMapping("/greeting")
class GreetingController {
//    @Value("${greeting-name: Mirage}")
//    private String name;
//
//    @Value("${greeting-coffee: ${greeting-name} is drinking Café Ganador}")
//    private String coffee;
    final Greeting greeting;

    GreetingController(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping
    String getGreeting() {
//        return this.name;
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameAndCoffee() {
//        return this.coffee;
        return greeting.getCoffee();
    }
}

@Setter
@Getter
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "greeting-props")
class Greeting {
    String name;
    String coffee;

}

@Setter
@Getter
class Droid {
    String id, description;
}

