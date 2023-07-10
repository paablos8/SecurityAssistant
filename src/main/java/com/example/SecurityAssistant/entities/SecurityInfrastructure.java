package com.example.SecurityAssistant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "Companies")
public class SecurityInfrastructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String companyName;
    
    private String PCAnzahl;
    private String PasswordPolicy;
    private String Server;
    private String Firewall;

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getPCAnzahl() {
        return PCAnzahl;
    }
    public void setPCAnzahl(String pCAnzahl) {
        PCAnzahl = pCAnzahl;
    }
   
    public String getPasswordPolicy() {
        return PasswordPolicy;
    }
    public void setPasswordPolicy(String passwordPolicy) {
        PasswordPolicy = passwordPolicy;
    }
    
    public String getServer() {
        return Server;
    }
    public void setServer(String Server) {
        this.Server = Server;
    }
    
    public String getFirewall() {
        return Firewall;
    }
    public void setFirewall(String Firewall) {
        this.Firewall = Firewall;
    }
    @Override
    public String toString() {
        return "SecurityInfrastructure [id=" + id + ", companyName=" + companyName + ", PCAnzahl=" + PCAnzahl
                + ", PasswordPolicy=" + PasswordPolicy + ", Server=" + Server + ", Firewall=" + Firewall + "]";
    }
  

    
}
