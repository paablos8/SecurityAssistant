package com.example.SecurityAssistant.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

public class Recommendation {
	
	private String title;
	private String information;
	private String originDocument;
	private ArrayList<String> risksIfNotImplemented;
	private ArrayList<Resource> risksIfNotImplementedResource;
	private ArrayList<String> mitigatesVulnerabilitiesString;
	private ArrayList<Resource> mitigatesVulnerabilitiesResource;

	private int priorityScore;
	
	
	public Recommendation (String title, String information, String originDocument) {
		this.title = title;
		this.information = information;
		this.originDocument = originDocument;
		this.risksIfNotImplemented = new ArrayList<String> ();
		this.risksIfNotImplementedResource = new ArrayList<Resource>();
		this.mitigatesVulnerabilitiesString = new ArrayList<String> ();
		this.mitigatesVulnerabilitiesResource = new ArrayList<Resource> ();
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
	
	public ArrayList<Resource> getRiskIfNotImplementedResource() {
		return risksIfNotImplementedResource;
	}
	
	public void addRiskIfNotImplementedResource (Resource risk) {
		risksIfNotImplementedResource.add(risk);
	}

	public ArrayList<String> getMitigatesVulnerabilitiesString() {
		return mitigatesVulnerabilitiesString;
	}

	public void addMitigatesVulnerabilitiesString(String mitigatesVulnerability) {
		mitigatesVulnerabilitiesString.add(mitigatesVulnerability);
	}
	
	public ArrayList<Resource> getMitigatesVulnerabilitiesResource() {
		return mitigatesVulnerabilitiesResource;
	}

	public void addMitigatesVulnerabilitiesResource(Resource mitigatesVulnerability) {
		mitigatesVulnerabilitiesResource.add(mitigatesVulnerability);
	}
	
	public void setPriorityScore (int priorityScore) {
		this.priorityScore = priorityScore;
	}
	
	public int getPriorityScore () {
		return priorityScore;
	}

	
	
	

}
 