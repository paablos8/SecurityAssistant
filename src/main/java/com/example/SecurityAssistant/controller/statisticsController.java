package com.example.SecurityAssistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Controller
public class statisticsController {

    //OS Count Variables
    int macOSCount, windowsCount, linuxCount;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/recommendation")
    public String showStatisticalInfo(Model model){
        List <SecurityInfrastructure> userData = repo.findAll();
        int [] OSCount = getOSCount(model, userData);
        return "recommendation";
    }

    public int[] getOSCount(Model model, List<SecurityInfrastructure> userDataList) {
        macOSCount = windowsCount = linuxCount = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {

                if (item.getOS().equals("MacOS")) {
                    macOSCount= macOSCount + 1;
                } else if (item.getOS().equals("Windows")) {
                    windowsCount = windowsCount + 1;
                } else if (item.getOS().equals("Linux")) {
                    linuxCount = linuxCount + 1;
                }
            }
            int[] OSCount = { macOSCount, windowsCount, linuxCount };
            System.out.println(OSCount[0]);
            return OSCount;
        }
        
        

    
}
