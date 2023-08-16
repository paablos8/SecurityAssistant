package com.example.SecurityAssistant.service;

import java.util.ArrayList;

import com.example.SecurityAssistant.entities.Recommendation;


public class fileGenerator {

    public byte[] generateFile(ArrayList<Recommendation> recommendations, int complianceScore, ArrayList<String> listStatusQuo, ArrayList<String> listCurrentVulnerabilities) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Status Quo IT Security Analysis: ").append("\n\n");
        sb.append("Your Compliance Score: ").append(complianceScore).append("%" + "\n\n");
        
        if (listStatusQuo != null && !listStatusQuo.isEmpty()) {
	        sb.append("These are your IT security measures you currently already have implemented: ").append("\n\n");
	        for (String statusQuo : listStatusQuo) {
	        	sb.append("- ").append(statusQuo).append("\n");
	        }
	        sb.append("\n\n");
        }
        
        if (listCurrentVulnerabilities != null && !listCurrentVulnerabilities.isEmpty()) {
	        sb.append("These are your current vulnerabilities: ").append("\n");
	        for (String vulnerability: listCurrentVulnerabilities) {
	        	sb.append("- ").append(vulnerability).append("\n");
	        }
	        sb.append("\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
        }
        
        if (recommendations != null && !recommendations.isEmpty()) {
        	sb.append("Based on the individual Status Quo IT Security Analysis, the system recommends the following actions to improve your IT security: ").append("\n\n");
	        for (Recommendation recommendation : recommendations) {
	            String title = recommendation.getTitle();
	            String info = recommendation.getInformation();
	            int priority = recommendation.getPriorityScore();
	            String originDoc = recommendation.getOriginDocument();
	            ArrayList<String> risks = recommendation.getRisksIfNotImplemented();
	
	            title = title.replace("\n", "").replace("\r", "");
	            info = info.replace("\n", "").replace("\r", "");
	            originDoc = originDoc.replace("\n", "").replace("\r", "");
	
	            
	            sb.append("Recommendation: ").append(title).append(" (Priority Score: ").append(priority).append(")").append("\n\n");
	            sb.append("More Info: ").append(info).append("\n\n");
	            sb.append("Origin Documents: ").append(originDoc).append("\n\n");
	            
	            if (risks != null && !risks.isEmpty()) {
	                sb.append("Risks if Not Implemented: ").append("\n");
	                for (String risk : risks) {
	                    sb.append(risk);
	                }
	                sb.append("\n");
	            }
	
	            // Add a separator line between recommendations
	            sb.append("------------------------------------------------------------------------------------------------------\n\n");
	        }
        }

        // Retrieve the generated text content as a byte array
        return sb.toString().getBytes();
    }
}