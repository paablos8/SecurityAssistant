package com.example.SecurityAssistant.entities;

public class Business {
    private String companyName;
    private String industry;
    private String employeeNr;
    private String location;
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getEmployeeNr() {
        return employeeNr;
    }
    public void setEmployeeNr(String employeeNr) {
        this.employeeNr = employeeNr;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }


    public Business(String companyName, String industry, String employeeNr, String location) {
        this.companyName = companyName;
        this.industry = industry;
        this.employeeNr = employeeNr;
        this.location = location;
    }
    public Business() {
    }
}
