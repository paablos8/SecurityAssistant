package com.example.SecurityAssistant.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.Recommendation;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;
import com.example.SecurityAssistant.service.statisticalService;
import com.example.ontology.InitJena;
import com.example.ontology.ReasoningJena;

@Controller
public class submitController {

    int eintragNR;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/recommendation")
    public String submitForm() {
        return "recommendation";
    }

    @PostMapping("/recommendation")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model) {
        if (!checkUsername(model, infra.getUserName())) {
            String userName = infra.getUserName();
            String companyName = removeWhitespaces(infra.getCompanyName());
            String backup = removeWhitespaces(infra.getBackup());
            String incidentResponse = removeWhitespaces(infra.getIncidentResponse());
            String firewall = removeWhitespaces(infra.getFirewall());
            String os = removeWhitespaces(infra.getOS());

            
           // Mapping and adding the SME into the base ontology
            InitJena initJena = new InitJena();
            initJena.loadOntology();
   
            initJena.addOrganization(userName, removeWhitespaces(companyName), infra.getEmployeeNR(), removeWhitespaces(infra.getBranche()), removeWhitespaces(infra.getRegion()));
            initJena.addPasswordPolicy(removeWhitespaces(infra.getPwChange()), removeWhitespaces(infra.getPwProperties()));
            //initJena.addComputer("ComputerTim_1", "Windows10_Tim","Tims_Antivirus_Software");
            if(infra.getTrainings().equals("Yes"))
            	initJena.addPolicy("SecurityTrainingPolicy", "SecurityTrainingPolicyOf" + companyName);
            
            switch (backup) {
	            case "NodefinedBackupstrategysporadicalbackups":
	            	initJena.addPolicy("DataBackupPolicyC", "BackupPolicyOf" + companyName);
	            	break;
	            case "FullBackuponceperweekincrementalorfullBackupdailyStrategyisdefinedanddocumentedisimplementedforthemostimportantprotectedsystems":
	            	initJena.addPolicy("DataBackupPolicyB", "BackupPolicyOf" + companyName);
	            	break;
	            case "FullBackuponceperweekincrementalorfullBackupdailyStrategyisdefinedanddocumentedisimplementedforallprotectedsystems":
	            	initJena.addPolicy("DataBackupPolicyA", "BackupPolicyOf" + companyName);
	            	break;
	            default:
	            	System.out.println("No Backup Policy implemented");
            }
            System.out.println("Backup Policy part finished");
            
            if (incidentResponse.equals("Wellspecified")) // auch betroffen von dem anderen Ontologie Prefix
            	initJena.addPolicy("SecurityIncidentPolicy", "SecurityIncidentPolicyOf" + companyName);
            
            if (infra.getPolicyDoc().equals("Yes"))
            	initJena.addPolicy("InformationSecurityCompliancePolicy", "InformationSecurityCompliancePolicyOf" + companyName);
            
            if (infra.getFireEx().equals("Yes"))
            	initJena.addAssetToBuilding("BuildingOf" + companyName, "FireExtinguisherOf" + companyName, "FireExtinguisher");
            
            if (infra.getSmokeDet().equals("Yes"))
            	initJena.addAssetToBuilding("BuildingOf" + companyName, "SmokeDetectorOf" + companyName, "SmokeDetector");
            
            if (infra.getCriticalInfra().equals("Yes"))
            	initJena.addAssetToBuilding("BuildingOf" + companyName, "EntryCheckpointOf" + companyName, "EntryCheckpoint");
            
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
           
       
            String pathToSavedOntology = initJena.saveOntology(userName);
            System.out.println("The ontology for " + companyName + " was successfully and stored under: " + pathToSavedOntology);

          
         // Reasoning
            ReasoningJena reasoning = new ReasoningJena(initJena.getOntModel(), companyName);
            
            reasoning.listImplementedControls();
            // reasoning.listNotImplementedControls(); 
            System.out.println("These are the current mitigated Vulnerabilities: " + reasoning.getMitigatedVulnerabilities());
            reasoning.listCurrentVulnerabilities();
            System.out.println("These are the current lowered Threats: " + reasoning.getLoweredThreats());
            //reasoning.listCurrentTopLevelThreats();
            //reasoning.listCurrentLowLevelThreats();
            
            List <Recommendation> recommendations = reasoning.generateRecommendations();
            model.addAttribute("recommendations",recommendations);
	 
            
            // Pseudonymisierung des Firmennamen Strings bevor dieser dann in der Datenbank
            // abgespeichert wird
            infra.setCompanyName(pseudonymizeString(infra.getCompanyName()));
            // Speicherung des Form Inputs in der MySQL Datenbank
            repo.save(infra);

            //Load statistical Data from the StatisticalService class
            statisticalService statistics = new statisticalService();
            statistics.showStatisticalInfo(model, repo);

            return "recommendation";
        } else {
            model.addAttribute("errorMessage", "Der Username ist bereits vergeben. Bitte wähle einen anderen.");
            return "infrastructure";
        }
    }

    // Methode soll die Datenbank abgleichen, ob der Username bereits vergeben ist
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

    // Methode die aufgerufen wird um den Firmennamen zu Pseudonymisieren
    public static String pseudonymizeString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Konvertiere das Byte-Array in einen Hexadezimal-String
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
 // Get rid of all the whitespaces in the Strings of the inputs
    public String removeWhitespaces (String input) {
    	String sanitizedInput = input.replaceAll("\\s+", "");
    	String sanitizedInput2 = sanitizedInput.replaceAll(";", "");
    	String fullySanitizedInput = sanitizedInput2.replaceAll(",", "");
    	return fullySanitizedInput;
    }

}
