package com.example.SecurityAssistant.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Controller
public class editDataController {
    
    @Autowired
    private InfrastructureRepository repo;

    int userID;
    private String username;

    // If Button "Edit my Data" is used this method displays the current userdata and enables the user to edit this data
    @PostMapping("/displayData")
    public String displayData(Model model, @RequestParam("userName") String username) {
        userID = getUserID(model, username);
        SecurityInfrastructure userData = repo.findById(userID).orElse(null);
        this.username = username; 
        model.addAttribute("userName", username);
        model.addAttribute("companyName", userData.getCompanyName());
        model.addAttribute("employeeNR", userData.getEmployeeNR());
        model.addAttribute("branche", userData.getBranche());
        model.addAttribute("region", userData.getRegion());
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
    }

    
    @PostMapping("/editSuccess")
    public String editData(@ModelAttribute SecurityInfrastructure infra, Model model){
        infra.setId(userID);
        infra.setUserName(username);

        // Pseudonymisierung des Firmennamen Strings bevor dieser dann in der Datenbank
        // abgespeichert wird
        infra.setCompanyName(submitController.pseudonymizeString(infra.getCompanyName()));

        repo.save(infra);
        model.addAttribute("userName", username);
        model.addAttribute("companyName", infra.getCompanyName());
        model.addAttribute("employeeNR", infra.getEmployeeNR());
        model.addAttribute("branche", infra.getBranche());
        model.addAttribute("region", infra.getRegion());
        model.addAttribute("pwChange", infra.getPwChange());
        model.addAttribute("pwProperties", infra.getPwProperties());
        model.addAttribute("trainings", infra.getTrainings());
        model.addAttribute("backup", infra.getBackup());
        model.addAttribute("incidentResponse", infra.getIncidentResponse());
        model.addAttribute("policyDoc", infra.getPolicyDoc());
        model.addAttribute("storage", infra.getStorage());
        model.addAttribute("fireEx", infra.getFireEx());
        model.addAttribute("smokeDet", infra.getSmokeDet());
        model.addAttribute("criticalInfra", infra.getCriticalInfra());
        model.addAttribute("alarm", infra.getAlarm());
        model.addAttribute("firewall", infra.getFirewall());
        model.addAttribute("firewallPolicy", infra.getFirewallPolicy());
        model.addAttribute("externalProvider", infra.getExternalProvider());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("printer", infra.getPrinter());
        model.addAttribute("OS", infra.getOS());
        return "editSuccess";
    }

    // Here the user ID of the user to be edited is determined and returned to the
    // "editData" method
    public int getUserID(Model model, String username) {
        List<SecurityInfrastructure> dataList = repo.findAll();
        model.addAttribute("dataList", dataList);

        // Username out of the Input field is compared to the userNames stored in the
        // database
        for (SecurityInfrastructure item : dataList) {
            if (item.getUserName().equals(username)) {
                userID = item.getId();
                break;
            } else {
                System.out.println("Der Username ist nicht vorhanden");
                // Exception muss aufgefangen werden
            }
        }
        return userID;
    }
}
    

