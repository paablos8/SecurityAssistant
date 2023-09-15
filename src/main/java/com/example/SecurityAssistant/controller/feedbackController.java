/*
 * @author SchimSlady
 */
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

//Controller class to process the submitted user feedback and return it to the operator /admin.
@Controller
public class feedbackController {

    @Autowired
    private FeedbackRepository repo;

    //Array with the titles of all possible security recommendations
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

    // This method empties the database of user feedbacks.
    @PostMapping("/resetUserFeedback")
    public String resetUserFeedback() {
        repo.deleteAll();
        return "admin";
    }

    // This method saves the feedback given in the input form when the emojis are
    // clicked into our database.
    @PostMapping("/feedback")
    public String feedbackSubmition(@RequestBody Feedback feedback) {
        repo.save(feedback);
        return "admin";
    }

    // The method calls the getFeedback methods for the different feedback
    // mechanisms in the input field.
    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        // Feedback regarding the Input Form
        calculateFormFeedback(model);
        model.addAttribute("questions", questions);

        // Feedback regarding the displayed Recommendations
        calculateRecommendationFeedback(model);
        model.addAttribute("titles", titles);

        // Overall Feedback
        calculateOverallFeedback(model);
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
        int neutralCount = 0; // reset of counter variables

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

    // Here the answers concerning the feedback related towards a specific question
    // or recommendation are
    // counted and saved in three different variables
    public int[] getStarsCount(Model model) {
        List<Feedback> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        int one = 0, two = 0, three = 0, four = 0, five = 0;// reset of counter variables

        for (Feedback item : dataList) {

            if (item.getRelatedTo().equals("OverallFeedback")) {
                if (item.getValue().equals("1")) {
                    one++;
                } else if (item.getValue().equals("2")) {
                    two++;
                } else if (item.getValue().equals("3")) {
                    three++;
                } else if (item.getValue().equals("4")) {
                    four++;
                } else if (item.getValue().equals("5")) {
                    five++;
                }
            }
        }

        int[] feedbackCount = { one, two, three, four, five };
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

    // Calculates overall Feedback concerning the assistant and save them in a
    // the averageVariable to hand it over to the model
    public void calculateOverallFeedback(Model model) {
            int[] feedbackCount = getStarsCount(model);
            model.addAttribute("ratingListe", feedbackCount);
            int totalStars = 0;
            int totalFeedbacks = 0;

            for (int i = 0; i < feedbackCount.length; i++) {
                totalStars += (i + 1) * feedbackCount[i];
                totalFeedbacks += feedbackCount[i];
            }
            //rounding the rating
            double rating = totalStars / (double) totalFeedbacks;
            double roundedRating = Math.round(rating * 10.0) / 10.0;
            model.addAttribute("averageRating", roundedRating);
        }

}
