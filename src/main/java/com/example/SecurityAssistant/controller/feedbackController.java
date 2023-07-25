package com.example.SecurityAssistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.SecurityAssistant.entities.Feedback;
import com.example.SecurityAssistant.repository.FeedbackRepository;

//Controller Klasse um das abgebene User feedback zu verarbeiten und an den Betreiber/Admin zurückzugeben
@Controller
public class feedbackController {

    @Autowired
    private FeedbackRepository repo;

    // Diese Methode leer die Datenbank mit den User Feedbacks
    @PostMapping("/resetUserFeedback")
    public String resetUserFeedback() {
        repo.deleteAll();
        return "admin";
    }

    // Diese Methode speichert das abgegebene Feedback aus der Input Form beim
    // betätigen der Emojis in unserer Datenbank ab
    @PostMapping("/feedback")
    public String feedbackSubmition(@RequestBody Feedback feedback) {
        repo.save(feedback);
        return "admin";
    }

    // Die Methode ruft die getFeedback Methoden zu den verschiedenen Feedback
    // Mechanismen im Input field auf
    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        calculatePwChangeFeedback(model);
        calculatePwPropertiesFeedback(model);
        calculatebackupFeedback(model);
        calculateIncidentResponseFeedback(model);
        return ("admin");
    }

    // Here the answers concerning the feedback towards one specific question are
    // counted and saved in three different variables
    public int[] getFeedback(Model model, String question) {
        List<Feedback> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        int goodCount = 0;
        int badCount = 0;
        int neutralCount = 0; // zurücksetzen der Zählervariablen

        for (Feedback item : dataList) {

            if (item.getQuestion().equals(question)) {
                if (item.getValue().equals("good")) {
                    goodCount = goodCount + 1;
                } else if (item.getValue().equals("neutral")) {
                    neutralCount = neutralCount + 1;
                } else if (item.getValue().equals("bad")) {
                    badCount = badCount + 1;
                }
            }
        }
        int[] feedbackCount = { goodCount, neutralCount, badCount };
        return feedbackCount;
    }

    // Diese Methoden rufen die getFeedback Methode auf und speichern die erechneten
    // Werte im Feedback Count array welches dann an das model übergeben und somit
    // im Frontend aufgerufen werden kann
    public void calculatePwChangeFeedback(Model model) {
        int[] feedbackCount1 = getFeedback(model, "pwChangeFeedback");
        model.addAttribute("feedbackCountPwChange", feedbackCount1);
    }

    public void calculatePwPropertiesFeedback(Model model) {
        int[] feedbackCount2 = getFeedback(model, "pwPropertiesFeedback");
        model.addAttribute("feedbackCountPwProperties", feedbackCount2);
    }

    public void calculatebackupFeedback(Model model) {
        int[] feedbackCount4 = getFeedback(model, "backupFeedback");
        System.out.println("feeedbackCount4 ist gleich :" + feedbackCount4);
        model.addAttribute("feedbackCountBackup", feedbackCount4);
    }

    public void calculateIncidentResponseFeedback(Model model) {
        int[] feedbackCount5 = getFeedback(model, "incidentResponseFeedback");
        model.addAttribute("feedbackCountIncidentResponse", feedbackCount5);
    }

}
