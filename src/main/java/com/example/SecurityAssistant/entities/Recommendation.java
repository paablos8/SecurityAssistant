package com.example.SecurityAssistant.entities;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
	
	private String title;
	private String information;
	private String originDocument;
	private ArrayList<String> risksIfNotImplemented;
	
	
	public Recommendation (String title, String information, String originDocument) {
		this.title = title;
		this.information = information;
		this.originDocument = originDocument;
		this.risksIfNotImplemented = new ArrayList<String> ();
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

	
	
	

}
 