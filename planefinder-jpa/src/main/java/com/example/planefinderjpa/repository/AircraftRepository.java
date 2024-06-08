package com.example.planefinderjpa.repository;

import com.example.planefinderjpa.model.Aircraft;
import org.springframework.data.repository.CrudRepository;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
