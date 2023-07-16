package com.example.SecurityAssistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

import java.util.List;

@Controller
public class deleteDataController {
    
    @Autowired
    private InfrastructureRepository repo;

    int userID;

    //If Button "Delete my Data" is used this method deletes the entry of the associated userName
    @PostMapping("/delete")
    public String deleteData(Model model, @RequestParam("userName") String username){   
        userID = getUserID(model, username); 
        model.addAttribute("username", username); 
        repo.deleteById(userID);
        return "delete";   
    }

   // Here the user ID of the user to be deleted is determined and returned to the "deleteData" method
    public int getUserID(Model model, String username) {
        List <SecurityInfrastructure> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);
        
        //Username out of the Input field is compared to the userNames stored in the database
        for (SecurityInfrastructure item : dataList){
            if (item.getUserName().equals(username)){
                userID = item.getId();
                break;
            } else{
                System.out.println("Der Username ist nicht vorhanden");
                //Exception muss aufgefangen werden
            }
        }
        return userID;
    } 
}
    

