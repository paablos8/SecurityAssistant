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

@Controller
public class feedbackController {

    int goodCount;
    int neutralCount;
    int badCount;

    @Autowired
    private FeedbackRepository repo;

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
        getPwChangeFeedback(model);
        getPwPropertiesFeedback(model);
        getbackupFeedback(model);
        getincidentResponseFeedback(model);
        return ("admin");
    }

    // Here the answers concerning the feedback towards one specific question are
    // counted and saved in three different variables
    public int[] getFeedback(Model model, String question) {
        List<Feedback> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        goodCount = badCount = neutralCount = 0; // zurücksetzen der Zählervariablen

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

    public String getPwChangeFeedback(Model model) {
        int[] feedbackCount1 = getFeedback(model, "pwChangeFeedback");
        model.addAttribute("feedbackCountPwChange", feedbackCount1);
        return "admin";
    }

    public String getPwPropertiesFeedback(Model model) {
        int[] feedbackCount2 = getFeedback(model, "pwPropertiesFeedback");
        model.addAttribute("feedbackCountPwProperties", feedbackCount2);
        return "admin";
    }

    public String getbackupFeedback(Model model) {
        int[] feedbackCount4 = getFeedback(model, "backupFeedback");
        System.out.println("feeedbackCount4 ist gleich :" + feedbackCount4);
        model.addAttribute("feedbackCountBackup", feedbackCount4);
        return "admin";
    }

    public String getincidentResponseFeedback(Model model) {
        int[] feedbackCount5 = getFeedback(model, "incidentResponseFeedback");
        model.addAttribute("feedbackCountIncidentResponse", feedbackCount5);
        return "admin";
    }

}
