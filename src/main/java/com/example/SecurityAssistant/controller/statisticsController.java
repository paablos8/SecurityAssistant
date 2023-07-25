package com.example.SecurityAssistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Controller
public class statisticsController {

    public String[] questions = { "pwChange", "pwProperties", "trainings", "backup", "incidentResponse",
            "policyDoc", "storage", "fireEx", "smokeDet", "criticalInfra", "alarm",
            "firewall", "externalProvider", "PCAnzahl", "printer", "OS"};

    // Count Variables
    int count1, count2, count3;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/recommendation")
    public String showStatisticalInfo(Model model) {
        List<SecurityInfrastructure> userData = repo.findAll();
        double[] OSCount = getOSCount(model, userData);
        double[] policyDocCount = getpolicyDocCount(model, userData);
        double[] trainingsCount = getTrainingsCount(model, userData);
        model.addAttribute("OSCount", OSCount);
        model.addAttribute("policyDocCount", policyDocCount);
        model.addAttribute("trainingsCount", trainingsCount);
        return "recommendation";
    }

    public double[] getOSCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getOS().equals("MacOS")) {
                count1 = count1 + 1;
            } else if (item.getOS().equals("Windows")) {
                count2 = count2 + 1;
            } else if (item.getOS().equals("Linux")) {
                count3 = count3 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Liest Anzahl an Firmen aus, die einen Incident Reponse Plan definiert haben
    public double[] getpolicyDocCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getPolicyDoc().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getPolicyDoc().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[]{count1, count2});
    }

    //Liest Anzahl an Firmen aus, die Trainings als Maßnahme im Unternehmen anbieten
    public double[] getTrainingsCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getTrainings().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getTrainings().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Funktion zum Berechnen des Prozentsatzes der Daten mit zwei Nachkommestellen
    public double[] calculatePercentage(double[] data) {
        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total = total + data[i];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.round((data[i] / total) * 100 * 100.0) / 100.0;
        }
        return data;

    }

}
