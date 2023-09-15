/*
 * @author SchimSlady
 */
package com.example.SecurityAssistant.controller;

import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;
import com.example.SecurityAssistant.service.dataPrivacy;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Controller
public class downloadController {
    @Autowired
    private InfrastructureRepository repo;

    int userID;

    @GetMapping("/downloadController")
    public ResponseEntity<byte[]> download(Model model, @RequestParam("userName") String username) throws IOException {
        if (!checkUsername(model, username)){
        userID = getUserID(model, username);
        SecurityInfrastructure userData = repo.findById(userID).orElse(null);
        byte[] file = userData.getFile();

        // Set content type
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", "filename.txt"); // You can change the filename here
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    } else {
        System.out.println("Username nicht vorhanden");
        return ResponseEntity.notFound().build();
    }
}


    // Here the user ID of the user to be edited is determined and returned to the
    // "editData" method
    public int getUserID(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (dataPrivacy.checkUsername(username, item.getUserName())) {
                userID = item.getId();
                break;
            } else {
                System.out.println("Der Username ist nicht vorhanden");
                // Exception muss aufgefangen werden
            }
        }
        return userID;
    }

    // Method checks whether username is already used by another user
    public boolean checkUsername(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (item.getUserName().equals(username)) {
                System.out.println("Der Username ist bereits vorhanden");
                return true;
            }
        }
        System.out.println("Der Username ist nicht vorhanden");
        return false;
    }

}
