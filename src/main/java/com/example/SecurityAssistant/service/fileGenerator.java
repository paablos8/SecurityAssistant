package com.example.SecurityAssistant.service;

import java.util.ArrayList;

import com.example.SecurityAssistant.entities.Recommendation;


public class fileGenerator {

    public byte[] generateFile(ArrayList<Recommendation> recommendations, int complianceScore) {
        StringBuilder sb = new StringBuilder();

        for (Recommendation recommendation : recommendations) {
            String title = recommendation.getTitle();
            String info = recommendation.getInformation();
            int priority = recommendation.getPriorityScore();
            String originDoc = recommendation.getOriginDocument();
            ArrayList<String> risks = recommendation.getRiskIfNotImplemented();

            title = title.replace("\n", "").replace("\r", "");
            info = info.replace("\n", "").replace("\r", "");
            originDoc = originDoc.replace("\n", "").replace("\r", "");

            sb.append("Ihr Compliance Score: ").append(complianceScore).append("\n");
            sb.append("Recommendation: ").append(title).append("(").append(priority).append(")").append("\n");
            sb.append("More Info: ").append(info).append("\n");
            sb.append("Origin Documents: {").append(originDoc).append("}").append("\n");
            
            if (risks != null && !risks.isEmpty()) {
                sb.append("Risks if Not Implemented: ").append("\n");
                for (String risk : risks) {
                    sb.append("- ").append(risk).append("\n");
                }
            }

            // Add a separator line between recommendations
            sb.append("--------------------------\n");
        }

        // Retrieve the generated text content as a byte array
        return sb.toString().getBytes();
    }
}