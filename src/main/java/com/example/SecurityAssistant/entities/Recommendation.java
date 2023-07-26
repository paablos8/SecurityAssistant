package com.example.SecurityAssistant.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Recommendation {
	
	private String title;
	private String information;
	private String originDocument;
	private ArrayList<String> risksIfNotImplemented;
	private ArrayList<String> mitigatesVulnerabilities;
	
	
	public Recommendation (String title, String information, String originDocument) {
		this.title = title;
		this.information = information;
		this.originDocument = originDocument;
		this.risksIfNotImplemented = new ArrayList<String> ();
		this.mitigatesVulnerabilities = new ArrayList<String> ();
	}
	
	public String getTitle () {
		return title;
	}

	public String getInformation() {
		return information;
	}
	
	public String getOriginDocument() {
		return originDocument;
	}
	
	public ArrayList<String> getRiskIfNotImplemented() {
		return risksIfNotImplemented;
	}
	
	public void addRiskIfNotImplemented (String risk) {
		risksIfNotImplemented.add(risk);
	}

	public ArrayList<String> getMitigatesVulnerabilities() {
		return mitigatesVulnerabilities;
	}

	public void addMitigatesVulnerabilities(String mitigatesVulnerability) {
		mitigatesVulnerabilities.add(mitigatesVulnerability);
	}

	
	
	

}
 