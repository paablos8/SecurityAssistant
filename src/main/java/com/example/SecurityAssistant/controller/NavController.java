/*
 * @author SchimSlady
 */
package com.example.SecurityAssistant.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//Controller for the navigation through the menu bar
@Controller
public class NavController {

    //Mapping on /infrastructure
    @GetMapping("/infrastructure")
    public String getInfrastructure(Model model){
        model.addAttribute("activePage", "info");
        return "infrastructure";
    }
    
    // Mapping on /edit
    @GetMapping("/edit")
    public String getEditPage(Model model) {
        model.addAttribute("activePage", "value");
        return ("edit");
    }

    // Mapping on /glossar
    @GetMapping("/glossar")
    public String getGlossarPage(Model model) {
        model.addAttribute("activePage", "value");
        return ("glossar");
    }
    
    // Mapping on /delete
    @GetMapping("/delete")
    public String getDeletePage(Model model) {
        model.addAttribute("activePage", "value");
        return ("delete");
    }
}
