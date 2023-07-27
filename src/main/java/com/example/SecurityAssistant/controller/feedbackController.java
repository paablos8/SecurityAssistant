package com.example.SecurityAssistant.controller;

import java.util.ArrayList;
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

    static String[] titles = { "7.1.4 Datensicherung bei mobile IT-Systemen",
            "A.10.1.1 Documented operating procedures",
            "A.11.7.1 Mobile computing and communications", "A.10.5.1 Information back-up",
            "7.1.1 Datensicherungskonzept und -planung", "8.1.4 Entry Check Point",
            "A.9.1.1 Physical security perimeter", "8.2 Fire Protection", "8.1.1 Schützenswerte",
            "8.2.1 Handfeuerlöscher", "A.9.1.4 Protecting against external and environmental threats",
            "A.13.2.1 Responsibilities and procedures", "A.6.1.2 Information security co-ordination",
            "5.1 Password Selection", "A.11.2.3 User password management", "A.11.5.1 Secure log-on procedures",
            "A.11.3.1 Password use", "A.11.5.3 Password management system",
            "4.4 Sicherheitssensibilisierung und -schulung", "6.5 Internet Access Risks",
            "5.8.2 Virus Avoidance and Recogniton by the User",
            "A.8.2.2 Information security awareness, education, and training", "4.5 Social Engineering Defense",
            "6.2 Personal Firewalls", "6.1 Firewalls", "A.15.2.2 Technical compliance checking" };

    static String[] questions = { "pwChange", "pwProperties", "backup", "incidentResponse" };

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
        // Feedback zur Input Form
        /*
         * calculatePwChangeFeedback(model);
         * calculatePwPropertiesFeedback(model);
         * calculatebackupFeedback(model);
         * calculateIncidentResponseFeedback(model);
         */
        calculateFormFeedback(model);
        model.addAttribute("questions", questions);

        // Feedback zu den Recommendations
        calculateRecommendationFeedback(model);
        model.addAttribute("titles", titles);
        return ("admin");
    }

    // Here the answers concerning the feedback related towards a specific question
    // or recommendation are
    // counted and saved in three different variables
    public int[] getFeedback(Model model, String relatedTo) {
        List<Feedback> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        int goodCount = 0;
        int badCount = 0;
        int neutralCount = 0; // zurücksetzen der Zählervariablen

        for (Feedback item : dataList) {

            if (item.getRelatedTo().equals(relatedTo)) {
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

    // Calculates all Feedback concerning the Input Form Questions and save them in
    // a List to hand it over to the model
    public void calculateFormFeedback(Model model) {
        List<int[]> formCounts = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            int[] feedbackCount = getFeedback(model, questions[i]);
            formCounts.add(i, feedbackCount);
        }
        model.addAttribute("formCounts", formCounts);
    }

    // Calculates all Feedback concerning the Recommendations and save them in a
    // List to hand it over to the model
    public void calculateRecommendationFeedback(Model model) {
        List<int[]> recommendationCounts = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            int[] feedbackCount = getFeedback(model, titles[i]);
            recommendationCounts.add(i, feedbackCount);
        }
        model.addAttribute("recommendationCounts", recommendationCounts);
    }

}
