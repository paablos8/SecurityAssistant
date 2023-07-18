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

    @PostMapping("/feedback")
    public String feedbackSubmition(@RequestBody Feedback feedback) {
        repo.save(feedback);
        return "admin";
    }

    @GetMapping("/admin")
    public String getAdminPage( Model model) {
        getPwChangeFeedback(model);
        getPwPropertiesFeedback(model);
        return ("admin");
    }


    // Here the answers concerning the feedback towards one specific question are counted and saved in three different variables
    public int [] getFeedback(Model model, String question) {
        List<Feedback> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        goodCount = badCount = neutralCount = 0; // zurücksetzen der Zählervariablen

        for (Feedback item : dataList) {
            
            if (item.getQuestion().equals(question)) {
                if (item.getValue().equals("good")){
                    goodCount = goodCount + 1;
                } else if (item.getValue().equals("neutral")) {
                    neutralCount = neutralCount + 1;
                } else if (item.getValue().equals("bad")) {
                    badCount = badCount + 1;
                }
        }
    } 
    int [] feedbackCount = {goodCount, neutralCount, badCount};
    return feedbackCount;
}

    public String getPwChangeFeedback(Model model){
        int [] feedbackCount = getFeedback(model, "pwChangeFeedback");
        model.addAttribute("feedbackCountPwChange", feedbackCount); 
        return "admin";
    }
    
    public String getPwPropertiesFeedback(Model model) {
        int[] feedbackCount = getFeedback(model, "pwPropertiesFeedback");
        model.addAttribute("feedbackCountPwProperties", feedbackCount);
        return "admin";
    }
}
