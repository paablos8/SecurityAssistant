/*
 * @author SchimSlady
 */
package com.example.SecurityAssistant.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

//Makes all the statistical calculations for the enrichment of the recommendations
@Service
public class statisticalService {

    String branche;
    String companySizeCategory;
    // Count Variables
    int count1, count2, count3, count4, count5;

    // Method is called with the generation of the recommendations to trigger the
    // calculation
    public void showStatisticalInfo(Model model, InfrastructureRepository repo, String branche, int employeeNR) {
        // Save all database entries of the user data in a list
        List<SecurityInfrastructure> userData = repo.findAll();
        // Catergorize the company
        this.branche = branche;
        this.companySizeCategory = categorizeCompanySize(employeeNR);

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

        // Add to the model to display in thymelaf
        model.addAttribute("branche", branche);
        model.addAttribute("companySize", companySizeCategory);
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

    // Calculates the number of companies with different password change policies
    // saved in the database
    public double[] pwChangeCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getPwChange().equals("Never")) {
                    count1 = count1 + 1;
                } else if (item.getPwChange().equals("Monthly")) {
                    count2 = count2 + 1;
                } else if (item.getPwChange().equals("Multiple times a year")) {
                    count3 = count3 + 1;
                } else if (item.getPwChange().equals("After a few years")) {
                    count4 = count4 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, (count2 + count3), count4 });
    }

    // Calculates the number of companies with a Backup Policy saved in the database
    public double[] backupCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getBackup().equals("No Backups are made")) {
                    count1 = count1 + 1;
                } else if (item.getBackup().equals("No defined Backup strategy, sporadical backups")) {
                    count2 = count2 + 1;
                } else {
                    count3 = count3 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Calculates how many companies use different data storing technologies saved
    // in the database
    public double[] storageCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getStorage().equals("Locally on our own Server")) {
                    count1 = count1 + 1;
                } else if (item.getStorage().equals("In a hybrid cloud")) {
                    count2 = count2 + 1;
                } else if (item.getStorage().equals("In the Cloud")) {
                    count3 = count3 + 1;
                } else if (item.getStorage().equals("In paper form")) {
                    count4 = count4 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4 });
    }

    // Calculates the number of companies with different firewalls saved in the
    // database
    public double[] firewallCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getFirewall().equals("complex Firewall")) {
                    count1 = count1 + 1;
                } else if (item.getFirewall().equals("multifunctional Firewall")) {
                    count2 = count2 + 1;
                } else if (item.getFirewall().equals("local Firewall")) {
                    count3 = count3 + 1;
                } else if (item.getFirewall().equals("no Firewall")) {
                    count4 = count4 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4 });
    }

    // Calculates the number of companies with different password requirements saved
    // in the database
    public double[] pwPropertiesCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = count4 = count5 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getPwProperties().equals("No")) {
                    count1 = count1 + 1;
                } else if (item.getPwProperties().equals("Password length")) {
                    count2 = count2 + 1;
                } else if (item.getPwProperties().equals("Password length and must include numbers")) {
                    count3 = count3 + 1;
                } else if (item.getPwProperties()
                        .equals("Password length must include numbers and special characters")) {
                    count4 = count4 + 1;
                } else if (item.getPwProperties().equals("Password must be randomly generated")) {
                    count5 = count5 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3, count4, count5 });
    }

    // Calculates the number of companies with different operating systems saved
    // in the database
    public double[] OSCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getOS().equals("MacOS")) {
                    count1 = count1 + 1;
                } else if (item.getOS().equals("Windows")) {
                    count2 = count2 + 1;
                } else if (item.getOS().equals("Linux")) {
                    count3 = count3 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Calculates the number of companies with an implemented incident response plan
    // that are saved
    // in the database
    public double[] incidentResponseCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = count3 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getIncidentResponse().equals("Well specified")) {
                    count1 = count1 + 1;
                } else if (item.getIncidentResponse().equals("There is a rough plan")) {
                    count2 = count2 + 1;
                } else if (item.getIncidentResponse().equals("There is no plan")) {
                    count3 = count3 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2, count3 });
    }

    // Calculates the number of companies with a security policy document
    // that are saved in the database
    public double[] policyDocCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getBranche().equals(branche)) {
                    if (item.getPolicyDoc().equals("Yes")) {
                        count1 = count1 + 1;
                    } else if (item.getPolicyDoc().equals("No")) {
                        count2 = count2 + 1;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that offer training as a measure in
    // the company
    public double[] TrainingsCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getTrainings().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getTrainings().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have a printer
    public double[] printerCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getPrinter().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getPrinter().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that are working together with an external
    // provider for IT security
    public double[] externalProviderCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getExternalProvider().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getExternalProvider().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have implemented a firewall policy
    public double[] firewallPolicyCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getFirewallPolicy().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getFirewallPolicy().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have installed an alarm system for
    // their buildings
    public double[] alarmCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getAlarm().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getAlarm().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have limited access to critical
    // infrastructure
    public double[] criticalInfraCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getCriticalInfra().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getCriticalInfra().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have smoke detection installed in
    // their buildings
    public double[] smokeDetCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getSmokeDet().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getSmokeDet().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // Calculates the number of companies that have a fire extinguisher to protect
    // critical infrastructure
    public double[] fireExCount(Model model, List<SecurityInfrastructure> userDataList) {
        count1 = count2 = 0; // reset the counting variables
        for (SecurityInfrastructure item : userDataList) {
            if (categorizeCompanySize(item.getEmployeeNR()).equals(companySizeCategory) && item.getBranche()
                    .equals(branche)) {
                if (item.getFireEx().equals("Yes")) {
                    count1 = count1 + 1;
                } else if (item.getFireEx().equals("No")) {
                    count2 = count2 + 1;
                }
            } else {
                continue;
            }
        }
        return calculatePercentage(new double[] { count1, count2 });
    }

    // function to calculate the percentage of the data
    public double[] calculatePercentage(double[] data) {
        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total = total + data[i];
        }
        // rounding to two digits
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.round((data[i] / total) * 100 * 100.0) / 100.0;
        }
        return data;

    }

    // Method categorizes the size of the company using the employee number
    public String categorizeCompanySize(int employeeNR) {
        if (employeeNR < 10) {
            return "unter 10 Mitarbeiter";
        } else if (employeeNR > 50) {
            return "über 50 Mitarbeiter";
        } else {
            return "zwischen 10 und 50 Mitarbeiter";
        }
    }

}
