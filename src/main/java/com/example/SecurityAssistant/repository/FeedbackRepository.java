package com.example.SecurityAssistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SecurityAssistant.entities.Feedback;

public interface FeedbackRepository extends JpaRepository <Feedback, Integer>{
    
}
