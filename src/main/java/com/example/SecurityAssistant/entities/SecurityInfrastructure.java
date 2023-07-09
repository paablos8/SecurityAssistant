package com.example.SecurityAssistant.entities;

public class SecurityInfrastructure extends Business{
    private int PCAnzahl;
    private String PasswordPolicy;
    private String Server;
    private String Firewall;

    public int getPCAnzahl() {
        return PCAnzahl;
    }
    public void setPCAnzahl(int pCAnzahl) {
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
        return "SecurityInfrastructure [PCAnzahl=" + PCAnzahl + ", PasswordPolicy=" + PasswordPolicy + ", Server="
                + Server + ", Firewall=" + Firewall + "]";
    }
    
}
