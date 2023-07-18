package com.example.SecurityAssistant.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   private String value;
   private String question;
   
   public String getValue() {
    return value;
}
public void setValue(String value) {
    this.value = value;
}
public String getQuestion() {
    return question;
}
public void setQuestion(String question) {
    this.question = question;
}
@Override
public String toString() {
    return "Feedback [id=" + id + ", value=" + value + ", question=" + question + "]";
}



    
}
