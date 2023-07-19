package com.example.SecurityAssistant.controller;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;
import com.example.ontology.InitJena;
import com.example.ontology.ReasoningJena;

@Controller
public class submitController {

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

    int eintragNR;

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }

    @PostMapping("/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model){
    	model.addAttribute("userName", infra.getUserName());
        String userName = infra.getUserName();
        model.addAttribute("companyName", infra.getCompanyName());
        String companyName = infra.getCompanyName();
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
        model.addAttribute("externalProvider", infra.getExternalProvider());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("printer", infra.getPrinter());
        model.addAttribute("OS", infra.getOS());

        
        // Mapping and adding the SME into the base ontology
        InitJena initJena = new InitJena();
        initJena.loadOntology();
        initJena.addOrganization(userName, companyName);
		//initJena.addComputer("ComputerTim_1", "Windows10_Tim", "Tims_Antivirus_Software");
        initJena.addPolicy("PrivateSoftwareAndHardwareUsePolicy", "Tims_PrivateSoftwareAndHardwareUsePolicy");
        String pathToSavedOntology = initJena.saveOntology(userName);
		System.out.println ("The ontology for " + companyName + " was successfully and stored under: " + pathToSavedOntology);
        
		
		// Reasoning 
        ReasoningJena reasoning = new ReasoningJena(initJena.getOntModel(), companyName);
        
        reasoning.listSomething();

		


        //Pseudonymisierung des Firmennamen Strings bevor dieser dann in der Datenbank abgespeichert wird
        infra.setCompanyName(pseudonymizeString(companyName));
        
        //Speicherung des Form Inputs in der MySQL Datenbank
        repo.save(infra);
        //Hier versuche ich die ID zurückzugeben um später evtl. wieder auf den Eintrag zugreifen zu können
        System.out.println("Anzahl an Einträgen in MySQL: " + repo.count());
        eintragNR = (int) (repo).count();
        System.out.println(repo.findById(eintragNR).toString());
		

        return "success";
    }

}
