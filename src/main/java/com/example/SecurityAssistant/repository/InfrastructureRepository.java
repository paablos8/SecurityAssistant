package com.example.SecurityAssistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;

public interface InfrastructureRepository extends JpaRepository<SecurityInfrastructure, Integer>{
    
}
