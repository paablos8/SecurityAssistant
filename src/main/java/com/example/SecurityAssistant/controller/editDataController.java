package com.example.SecurityAssistant.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SecurityAssistant.entities.Recommendation;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;
import com.example.SecurityAssistant.service.dataPrivacy;
import com.example.SecurityAssistant.service.fileGenerator;
import com.example.SecurityAssistant.service.statisticalService;
import com.example.ontology.InitJena;
import com.example.ontology.ReasoningJena;

@Controller
public class editDataController {

    @Autowired
    private InfrastructureRepository repo;

    int userID;
    private String username;

    // If Button "Edit my Data" is used this method displays the current userdata
    // and enables the user to edit this data
    @PostMapping("/displayData")
    public String displayData(Model model, @RequestParam("userName") String username) {
        if (checkUsername(model, username)) {
            userID = getUserID(model, username);
            SecurityInfrastructure userData = repo.findById(userID).orElse(null);
            this.username = username;
            model.addAttribute("userName", username);
            model.addAttribute("employeeNR", userData.getEmployeeNR());
            model.addAttribute("pwChange", userData.getPwChange());
            model.addAttribute("pwProperties", userData.getPwProperties());
            model.addAttribute("trainings", userData.getTrainings());
            model.addAttribute("backup", userData.getBackup());
            model.addAttribute("incidentResponse", userData.getIncidentResponse());
            model.addAttribute("policyDoc", userData.getPolicyDoc());
            model.addAttribute("storage", userData.getStorage());
            model.addAttribute("fireEx", userData.getFireEx());
            model.addAttribute("smokeDet", userData.getSmokeDet());
            model.addAttribute("criticalInfra", userData.getCriticalInfra());
            model.addAttribute("alarm", userData.getAlarm());
            model.addAttribute("firewall", userData.getFirewall());
            model.addAttribute("firewallPolicy", userData.getFirewallPolicy());
            model.addAttribute("externalProvider", userData.getExternalProvider());
            model.addAttribute("PCAnzahl", userData.getPCAnzahl());
            model.addAttribute("printer", userData.getPrinter());
            model.addAttribute("OS", userData.getOS());
            return "displayData";
        } else {
            model.addAttribute("errorMessage",
                    "Der Username ist nicht vergeben. Bitte geben Sie Ihren Username ein oder erstellen Sie eine neue Empfehlung.");
            return "edit";
        }
    }

    // When the user has edited his data and presses the submit button, teh user
    // data is replaced in the database and a new recommendation is generated
    @PostMapping("/editSuccess")
    public String editData(@ModelAttribute SecurityInfrastructure infra, Model model) {
        infra.setId(userID);
        String userName = infra.getUserName();
        String companyName = removeWhitespaces(infra.getCompanyName());
        String backup = removeWhitespaces(infra.getBackup());
        String incidentResponse = removeWhitespaces(infra.getIncidentResponse());
        String firewall = removeWhitespaces(infra.getFirewall());
        String os = removeWhitespaces(infra.getOS());

        // Mapping and adding the SME into the base ontology
        InitJena initJena = new InitJena();
        initJena.loadOntology();

        initJena.addOrganization(userName, removeWhitespaces(companyName), infra.getEmployeeNR(),
                removeWhitespaces(infra.getBranche()), removeWhitespaces(infra.getRegion()));
        initJena.addPasswordPolicy(removeWhitespaces(infra.getPwChange()),
                removeWhitespaces(infra.getPwProperties()));
        // initJena.addComputer("ComputerTim_1",
        // "Windows10_Tim","Tims_Antivirus_Software");
        if (infra.getTrainings().equals("Yes"))
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

        reasoning.listImplementedControls();
        // reasoning.listNotImplementedControls();
        System.out.println(
                "These are the current mitigated Vulnerabilities: " + reasoning.getMitigatedVulnerabilities());
        reasoning.listCurrentVulnerabilities();
        System.out.println("These are the current lowered Threats: " + reasoning.getLoweredThreats());
        // reasoning.listCurrentTopLevelThreats();
        // reasoning.listCurrentLowLevelThreats();

        int complianceScore = reasoning.createOverallComplianceScore();
        model.addAttribute("complianceScore", complianceScore);

        // Generation of recommendations
        ArrayList<Recommendation> recommendations = reasoning.generateRecommendations();

        // Generation of text file and saving in a byteArray
        fileGenerator fileGenerator = new fileGenerator();
        byte[] fileBytes = fileGenerator.generateFile(recommendations, complianceScore);
        infra.setFile(fileBytes);
        model.addAttribute("fileBytes", fileBytes);
        System.out.println(fileBytes);

        // Adding the created recommendations to the model to display them with
        // Thymeleaf in the frontend.
        model.addAttribute("recommendations", recommendations);

        // Pseudonymisation of the company name string before it is then stored in the
        // database
        infra.setUserName(dataPrivacy.pseudonymizeString(username));
        infra.setCompanyName(dataPrivacy.pseudonymizeString(infra.getCompanyName()));
        infra.setRegion(dataPrivacy.pseudonymizeString(infra.getRegion()));

        repo.save(infra);

        // Load statistical Data from the StatisticalService class
        statisticalService statistics = new statisticalService();
        statistics.showStatisticalInfo(model, repo, infra.getBranche(), infra.getEmployeeNR());

        return "recommendation";
    }

    // Here the user ID of the user to be edited is determined and returned to the
    // "editData" method
    public int getUserID(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (dataPrivacy.checkUsername(username, item.getUserName())) {
                userID = item.getId();
                break;
            } else {
                System.out.println("Der Username ist nicht vorhanden");
                // Exception muss aufgefangen werden
            }
        }
        return userID;
    }

    // Method should check the database to see if the username is already in use.
    public boolean checkUsername(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (dataPrivacy.checkUsername(username, item.getUserName())) {
                System.out.println("Der Username ist bereits vorhanden");
                return true;
            }
        }
        System.out.println("Der Username ist nicht vorhanden");
        return false;
    }

    // Get rid of all the whitespaces in the Strings of the inputs
    public String removeWhitespaces(String input) {
        String sanitizedInput = input.replaceAll("\\s+", "");
        String sanitizedInput2 = sanitizedInput.replaceAll(";", "");
        String fullySanitizedInput = sanitizedInput2.replaceAll(",", "");
        return fullySanitizedInput;
    }
}
