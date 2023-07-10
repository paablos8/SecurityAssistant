package com.example.SecurityAssistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.ontology.InitJena;



@Controller
public class submitController {
    

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }   

    @PostMapping(path="/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model){
        System.out.println(infra.toString());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("PasswordPolicy", infra.getPasswordPolicy());
        model.addAttribute("Server", infra.getServer());
        model.addAttribute("Firewall", infra.getFirewall());
        
        InitJena initJena = new InitJena();
        //String baseOntologyFilePath = "file:///C:/Users/Wiwi-Admin/eclipse-workspace/SecurityAssistant/src/main/java/com/example/ontology/files/fenz2016_test.owl.xml";
        initJena.loadOntology ();
		initJena.addComputer("ComputerMuller_1");
        //InitJena.writeInOntology("Pablo");
        return "success";
    }
}
