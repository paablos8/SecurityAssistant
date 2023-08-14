package com.example.SecurityAssistant.controller;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.Recommendation;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;
import com.example.SecurityAssistant.service.EditStringService;
import com.example.SecurityAssistant.service.dataPrivacy;
import com.example.SecurityAssistant.service.fileGenerator;
import com.example.SecurityAssistant.service.statisticalService;
import com.example.ontology.InitJena;
import com.example.ontology.ReasoningJena;

//The Controller handles the request when the user input form is submitted
@Controller
public class submitController {

    int eintragNR;
    EditStringService editString;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/recommendation")
    public String submitForm() {
        return "recommendation";
    }

    @PostMapping("/recommendation")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model) {
        if (!checkUsername(model, infra.getUserName())) {
        	editString = new EditStringService();
            String userName = infra.getUserName();
            String companyName = editString.removeWhitespaces(infra.getCompanyName());
            String backup = editString.removeWhitespaces(infra.getBackup());
            String incidentResponse = editString.removeWhitespaces(infra.getIncidentResponse());
            String firewall = editString.removeWhitespaces(infra.getFirewall());
            String os = editString.removeWhitespaces(infra.getOS());

            // Mapping and adding the SME into the base ontology
            InitJena initJena = new InitJena();
            initJena.loadOntology();

            initJena.addOrganization(userName, companyName, infra.getEmployeeNR(),
            		editString.removeWhitespaces(infra.getBranche()), editString.removeWhitespaces(infra.getRegion()));
            initJena.addPasswordPolicy(editString.removeWhitespaces(infra.getPwChange()),
            		editString.removeWhitespaces(infra.getPwProperties()));
            
            if (infra.getTrainings().equals("Yes"))
                initJena.addPolicy("SecurityTrainingPolicy", "SecurityTrainingPolicyOf" + companyName);

            switch (backup) {
                case "NodefinedBackupstrategysporadicalbackups":
                    initJena.addPolicy("DataBackupPolicyC", "BackupPolicyOf" + companyName);
                    break;
                case "FullBackupStrategyforthemostimportantprotected systems":
                    initJena.addPolicy("DataBackupPolicyB", "BackupPolicyOf" + companyName);
                    break;
                case "FullBackupStrategyforallprotectedsystems":
                    initJena.addPolicy("DataBackupPolicyA", "BackupPolicyOf" + companyName);
                    break;
                default:
                    System.out.println("No Backup Policy implemented");
            }
            System.out.println("Backup Policy part finished");

            if (incidentResponse.equals("Wellspecified")) // auch betroffen von dem anderen Ontologie Prefix
                initJena.addPolicy("SecurityIncidentPolicy", "SecurityIncidentPolicyOf" + companyName);

            if (infra.getPolicyDoc().equals("Yes"))
                initJena.addPolicy("InformationSecurityCompliancePolicy",
                        "InformationSecurityCompliancePolicyOf" + companyName);

            if (infra.getFireEx().equals("Yes"))
                initJena.addAssetToBuilding("BuildingOf" + companyName, "FireExtinguisherOf" + companyName,
                        "FireExtinguisher");

            if (infra.getSmokeDet().equals("Yes"))
                initJena.addAssetToBuilding("BuildingOf" + companyName, "SmokeDetectorOf" + companyName,
                        "SmokeDetector");

            if (infra.getCriticalInfra().equals("Yes"))
                initJena.addAssetToBuilding("BuildingOf" + companyName, "EntryCheckpointOf" + companyName,
                        "EntryCheckpoint");

            if (infra.getAlarm().equals("Yes"))
                initJena.addAssetToBuilding("BuildingOf" + companyName, "AlarmSystemOf" + companyName, "AlarmSystem");

            switch (firewall) {
                case "complexFirewall":
                    initJena.addAsset("ComplexFirewall", "ComplexFirewallOf" + companyName);
                    break;
                case "multifunctionalFirewall":
                    initJena.addAsset("MultifunctionalFirewall", "MultifunctionalFirewallOf" + companyName);
                    break;
                case "local Firewall":
                    initJena.addAsset("FirewallB", "LocalFirewallOf" + companyName);
                    break;
                default:
                    System.out.println("No Firewall implemented");
            }

            if (infra.getFirewallPolicy().equals("Yes"))
                initJena.addPolicy("FirewallPolicy", "FirewallPolicyOf" + companyName);

            initJena.addAsset("OS", os);
           
            
            // Reasoning
            ReasoningJena reasoning = new ReasoningJena(initJena.getOntModel(), companyName);
            
            // Get the current implemented controls
            System.out.println("Your business implements the following controls: ");
            reasoning.listImplementedControls();
            // List the current mitigated vulnerabilities
            System.out.println("These are the current mitigated Vulnerabilities: " + reasoning.getMitigatedVulnerabilities());
            // List the current vulnerabilities
            reasoning.listCurrentVulnerabilities();
            
       
            int complianceScore = reasoning.createOverallComplianceScore();
            model.addAttribute("complianceScore", complianceScore);

            // Generation of the recommendations
            ArrayList<Recommendation> recommendations = reasoning.generateRecommendations();

            //Generation of text file and saving in a byteArray
            fileGenerator fileGenerator = new fileGenerator();
            byte[] fileBytes = fileGenerator.generateFile(recommendations, complianceScore);
            infra.setFile(fileBytes);
            model.addAttribute("fileBytes", fileBytes);

            // Adding the created recommendations to the model to display them with
            // Thymeleaf in the frontend
            model.addAttribute("recommendations", recommendations);

            // Pseudonymisation of the company name, username and region string before it is stored in the
            // database 
            infra.setUserName(dataPrivacy.pseudonymizeString(infra.getUserName()));
            infra.setCompanyName(dataPrivacy.pseudonymizeString(infra.getCompanyName()));
            infra.setRegion(dataPrivacy.pseudonymizeString(infra.getRegion()));
            // Saving of the input data, SecurityInfrastructure Object in the database
            repo.save(infra);

            // Load statistical Data from the StatisticalService class
            statisticalService statistics = new statisticalService();
            statistics.showStatisticalInfo(model, repo, infra.getBranche(), infra.getEmployeeNR());

            return "recommendation";
        } else {
            model.addAttribute("errorMessage", "Der Username ist bereits vergeben. Bitte wähle einen anderen.");
            return "infrastructure";
        }
    }

    // Method checks whether username is already used by another user
    public boolean checkUsername(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (item.getUserName().equals(username)) {
                System.out.println("Der Username ist bereits vorhanden");
                return true;
            }
        }
        System.out.println("Der Username ist nicht vorhanden");
        return false;
    }
}
