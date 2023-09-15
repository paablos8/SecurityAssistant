/*
 * @author SchimSlady
 */
package com.example.SecurityAssistant.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SecurityAssistant.entities.Feedback;

// Define the FeedbackRepository interface that extends JpaRepository for the Feedback entity.
// JpaRepository provides common database operations like save, delete, findAll, etc. for the Feedback entity.
public interface FeedbackRepository extends JpaRepository <Feedback, Integer>{
    
    // No additional code is needed here since JpaRepository already provides the
    // necessary methods.
    // The Feedback entity class and the primary key type (Integer) are specified as
    // type parameters.
    // JpaRepository takes care of the rest by automatically generating the
    // implementation for the CRUD operations.
    // In this case, FeedbackRepository can be used to perform CRUD operations on
    // the Feedback entity.
}
