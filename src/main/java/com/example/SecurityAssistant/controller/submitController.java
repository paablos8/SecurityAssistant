package com.example.SecurityAssistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Controller
public class submitController {

    long eintragNR;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }   

    @PostMapping("/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model){
        System.out.println(infra.toString());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("PasswordPolicy", infra.getPasswordPolicy());
        model.addAttribute("Server", infra.getServer());
        model.addAttribute("Firewall", infra.getFirewall());

        repo.save(infra);
        System.out.println(repo.count());
        eintragNR = repo.count();

        return "success";
    } 

    /* @RequestMapping("/success")
     public ModelAndView showData(){
        ModelAndView mv = new ModelAndView();
        List <SecurityInfrastructure> dataList = repo.findAll();
        mv.addObject("data",dataList);
        mv.setViewName("success");
        return mv;
     } */


}
