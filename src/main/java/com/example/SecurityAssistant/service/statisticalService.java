package com.example.SecurityAssistant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Service
public class statisticalService {

    // Count Variables
    int count1, count2, count3, count4, count5;

    public void showStatisticalInfo(Model model, InfrastructureRepository repo) {
        List<SecurityInfrastructure> userData = repo.findAll();
        double[] pwChangeCount = pwChangeCount(model, userData);
        double[] backupCount = backupCount(model, userData);
        double[] storageCount = storageCount(model, userData);
        double[] firewallCount = firewallCount(model, userData);
        double[] pwPropertiesCount = pwPropertiesCount(model, userData);
        double[] OSCount = OSCount(model, userData);
        double[] incidentResponseCount = incidentResponseCount(model, userData);
        double[] policyDocCount = policyDocCount(model, userData);
        double[] trainingsCount = TrainingsCount(model, userData);
        double[] printerCount = printerCount(model, userData);
        double[] externalProviderCount = externalProviderCount(model, userData); 
        double[] firewallPolicyCount = firewallPolicyCount(model, userData); 
        double[] alarmCount = alarmCount(model, userData);
        double[] criticalInfraCount = criticalInfraCount(model, userData);
        double[] smokeDetCount = smokeDetCount(model, userData);
        double[] fireExCount = fireExCount(model, userData);
        model.addAttribute("pwChangeCount", pwChangeCount);
        model.addAttribute("backupCount", backupCount);
        model.addAttribute("storageCount", storageCount);
        model.addAttribute("firewallCount", firewallCount);
        model.addAttribute("pwPropertiesCount", pwPropertiesCount);
        model.addAttribute("OSCount", OSCount);
        model.addAttribute("incidentResponseCount", incidentResponseCount);
        model.addAttribute("policyDocCount", policyDocCount);
        model.addAttribute("trainingsCount", trainingsCount);
        model.addAttribute("printerCount", printerCount);
        model.addAttribute("externalProviderCount", externalProviderCount); 
        model.addAttribute("firewallPolicyCount", firewallPolicyCount); 
        model.addAttribute("alarmCount", alarmCount);
        model.addAttribute("criticalInfraCount", criticalInfraCount);
        model.addAttribute("smokeDetCount", smokeDetCount);
        model.addAttribute("fireExCount", fireExCount);
    }


    // Liest die Anzahl an Firmen mit unterschiedlichen Passwort Change Policies aus
    public double[] pwChangeCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getPwChange().equals("Never")) {
                count1 = count1 + 1;
            } else if (item.getPwChange().equals("Monthly")) {
                count2 = count2 + 1;
            } else if (item.getPwChange().equals("Multiple times a year")) {
                count3 = count3 + 1;
            } else if (item.getPwChange().equals("After a few years")){
                count4 = count4 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, (count2 + count3), count4 });
    }

    // Liest die Anzahl an Firmen mit Backup Policy aus
    public double[] backupCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getBackup().equals("No Backups are made")) {
                count1 = count1 + 1;
            } else if (item.getBackup().equals("No defined Backup strategy, sporadical backups")) {
                count2 = count2 + 1;
            } else {
                count3 = count3 + 1;
            } 
        }
        return calculatePercentage(new double[] { count1 , count2, count3 });
    }
    
    // Liest die verschiedenen Datensicherungskonzepte der Firmen aus
    public double[] storageCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getStorage().equals("Locally on our own Server")) {
                count1 = count1 + 1;
            } else if (item.getStorage().equals("In a hybrid cloud")) {
                count2 = count2 + 1;
            } else if (item.getStorage().equals("In the Cloud")) {
                count3 = count3 + 1;
            } else if (item.getStorage().equals("In paper form")) {
                count4 = count4 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4 });
    }

    // Liest die verschiedenen Firewall Implementierungen der Firmen aus
    public double[] firewallCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getFirewall().equals("complex Firewall")) {
                count1 = count1 + 1;
            } else if (item.getFirewall().equals("multifunctional Firewall")) {
                count2 = count2 + 1;
            } else if (item.getFirewall().equals("local Firewall")) {
                count3 = count3 + 1;
            } else if (item.getFirewall().equals("no Firewall")) {
                count4 = count4 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4 });
    }

    // Liest die verschiedenen Anforderungen an die PW Properties der Firmen aus
    public double[] pwPropertiesCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = count5 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getPwProperties().equals("No")) {
                count1 = count1 + 1;
            } else if (item.getPwProperties().equals("Password length")) {
                count2 = count2 + 1;
            } else if (item.getPwProperties().equals("Password length and must include numbers")) {
                count3 = count3 + 1;
            } else if (item.getPwProperties().equals("Password length must include numbers and special characters")) {
                count4 = count4 + 1;
            } else if (item.getPwProperties().equals("Password must be randomly generated")) {
                count5 = count5 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4, count5 });
    }

    // Liest Anzahl der Firmen mit den jeweiligen Betreibssystemen aus
    public double[] OSCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getOS().equals("MacOS")) {
                count1 = count1 + 1;
            } else if (item.getOS().equals("Windows")) {
                count2 = count2 + 1;
            } else if (item.getOS().equals("Linux")) {
                count3 = count3 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Liest Anzahl an Firmen aus, die einen Incident Response Plan definiert haben
    public double[] incidentResponseCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getIncidentResponse().equals("Well specified")) {
                count1 = count1 + 1;
            } else if (item.getIncidentResponse().equals("There is a rough plan")) {
                count2 = count2 + 1;
            } else if (item.getIncidentResponse().equals("There is no plan")) {
                count3 = count3 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Liest Anzahl an Firmen aus, die ein Security Policy Document pflegen
    public double[] policyDocCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getPolicyDoc().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getPolicyDoc().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Liest Anzahl an Firmen aus, die Trainings als Maßnahme im Unternehmen
    // anbieten
    public double[] TrainingsCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getTrainings().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getTrainings().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Liest Anzahl an Firmen aus, die einen Printer haben
    public double[] printerCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getPrinter().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getPrinter().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Liest Anzahl an Firmen aus, die mit einem externen Dienstleister arbeiten
    public double[] externalProviderCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getExternalProvider().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getExternalProvider().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

   // Liest Anzahl an Firmen aus, die eine Firewall Policy umgesetzt haben
    public double[] firewallPolicyCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getFirewallPolicy().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getFirewallPolicy().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    } 
    
    // Liest Anzahl an Firmen aus, die eine Alarmanlage haben
    public double[] alarmCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getAlarm().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getAlarm().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }
    
    // Liest Anzahl an Firmen aus, die den Zugang zu kritischer Infrastruktur begrenzt haben
    public double[] criticalInfraCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getCriticalInfra().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getCriticalInfra().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Liest Anzahl an Firmen aus, die Rauchdetektorem installiert haben
    public double[] smokeDetCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getSmokeDet().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getSmokeDet().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Liest Anzahl an Firmen aus, die Feuerlöscher parat haben
    public double[] fireExCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // zurücksetzen der Zählervariablen
        for (SecurityInfrastructure item : userDataList) {
            if (item.getFireEx().equals("Yes")) {
                count1 = count1 + 1;
            } else if (item.getFireEx().equals("No")) {
                count2 = count2 + 1;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Funktion zum Berechnen des Prozentsatzes der Daten mit zwei Nachkommestellen
    public double[] calculatePercentage(double[] data) {
        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total = total + data[i];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.round((data[i] / total) * 100 * 100.0) / 100.0;
        }
        return data;

    }
    
}
