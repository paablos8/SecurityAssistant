package com.example.SecurityAssistant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class SecurityInfrastructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Company related data
    @Column(name = "Name")
    private String companyName;

    private int employeeNR;
    private String branche;
    private String region;

    // Policy related input data
    private String pwChange;
    private String pwProperties;
    private String trainings;
    private String backup;
    private String incidentResponse;
    private String policyDoc;

    // Input data concerning the physical environment
    private String storage;
    private String fireEx;
    private String smokeDet;
    private String criticalInfra;
    private String alarm;

    // Input data concerning the netwrok security
    private String firewall;
    private String externalProvider;

    // Input data concerning the IT systems
    private String PCAnzahl;
    private String printer;
    private String OS;

    // müssen rausgeschmissen werden
    private String PasswordPolicy;
    private String Server;


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
    public int getEmployeeNR() {
        return employeeNR;
    }
    public void setEmployeeNR(int employeeNR) {
        this.employeeNR = employeeNR;
    }
    public String getBranche() {
        return branche;
    }
    public void setBranche(String branche) {
        this.branche = branche;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getPwChange() {
        return pwChange;
    }
    public void setPwChange(String pwChange) {
        this.pwChange = pwChange;
    }
    public String getPwProperties() {
        return pwProperties;
    }
    public void setPwProperties(String pwProperties) {
        this.pwProperties = pwProperties;
    }
    public String getTrainings() {
        return trainings;
    }
    public void setTrainings(String trainings) {
        this.trainings = trainings;
    }
    public String getBackup() {
        return backup;
    }
    public void setBackup(String backup) {
        this.backup = backup;
    }
    public String getIncidentResponse() {
        return incidentResponse;
    }
    public void setIncidentResponse(String incidentResponse) {
        this.incidentResponse = incidentResponse;
    }
    public String getPolicyDoc() {
        return policyDoc;
    }
    public void setPolicyDoc(String policyDoc) {
        this.policyDoc = policyDoc;
    }
    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }
    public String getFireEx() {
        return fireEx;
    }
    public void setFireEx(String fireEx) {
        this.fireEx = fireEx;
    }
    public String getSmokeDet() {
        return smokeDet;
    }
    public void setSmokeDet(String smokeDet) {
        this.smokeDet = smokeDet;
    }
    public String getCriticalInfra() {
        return criticalInfra;
    }
    public void setCriticalInfra(String criticalInfra) {
        this.criticalInfra = criticalInfra;
    }
    public String getAlarm() {
        return alarm;
    }
    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
    public String getFirewall() {
        return firewall;
    }
    public void setFirewall(String firewall) {
        this.firewall = firewall;
    }
    public String getExternalProvider() {
        return externalProvider;
    }
    public void setExternalProvider(String externalProvider) {
        this.externalProvider = externalProvider;
    }
    public String getPCAnzahl() {
        return PCAnzahl;
    }
    public void setPCAnzahl(String pCAnzahl) {
        PCAnzahl = pCAnzahl;
    }
    public String getPrinter() {
        return printer;
    }
    public void setPrinter(String printer) {
        this.printer = printer;
    }
    public String getOS() {
        return OS;
    }
    public void setOS(String oS) {
        OS = oS;
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
    public void setServer(String server) {
        Server = server;
    }
    @Override
    public String toString() {
        return "SecurityInfrastructure [id=" + id + ", companyName=" + companyName + ", employeeNR=" + employeeNR
                + ", branche=" + branche + ", region=" + region + ", pwChange=" + pwChange + ", pwProperties="
                + pwProperties + ", trainings=" + trainings + ", backup=" + backup + ", incidentResponse="
                + incidentResponse + ", policyDoc=" + policyDoc + ", storage=" + storage + ", fireEx=" + fireEx
                + ", smokeDet=" + smokeDet + ", criticalInfra=" + criticalInfra + ", alarm=" + alarm + ", firewall="
                + firewall + ", externalProvider=" + externalProvider + ", PCAnzahl=" + PCAnzahl + ", printer="
                + printer + ", OS=" + OS + ", PasswordPolicy=" + PasswordPolicy + ", Server=" + Server + "]";
    }
 
    
}
