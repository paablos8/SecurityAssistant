package com.example.SecurityAssistant.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;

// Define the InfrastructureRepository interface that extends JpaRepository for the SecurityInfrastructure entity.
// JpaRepository provides common database operations like save, delete, findAll, etc. for the SecurityInfrastructure entity.
public interface InfrastructureRepository extends JpaRepository<SecurityInfrastructure, Integer>{

    // No additional code is needed here since JpaRepository already provides the
    // necessary methods.
    // The SecurityInfrastructure entity class and the primary key type (Integer)
    // are specified as type parameters.
    // JpaRepository takes care of the rest by automatically generating the
    // implementation for the CRUD operations.
    // In this case, InfrastructureRepository can be used to perform CRUD operations
    // on the SecurityInfrastructure entity.

}
