package com.example.ontology;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.util.iterator.ExtendedIterator;

import com.example.SecurityAssistant.entities.Recommendation;
import com.example.SecurityAssistant.service.EditStringService;

public class ReasoningJena {
	
	OntModel base;
	String NS = InitJena.NS;
	String NSimported = InitJena.NSimported;
	String businessIRI;
	String nameOfBusiness;
	Resource businessResource;
	Resource buildingResource;
	Resource sectionResource;
	
	Property controlMitigatesVulnerability; 
    Property vulnerabilityExploitedByThreat;
    Property threatGivesRiseToThreat;
	
	ArrayList<Resource> implementedControls = new ArrayList<Resource>();
	ArrayList<String> implementedControlsString = new ArrayList<String>();
	ArrayList<String> notImplementedControlsString = new ArrayList<String>();
	ArrayList<Resource> notImplementedControls = new ArrayList<Resource>();
	ArrayList<Resource> mitigatedVulnerabilites = new ArrayList<Resource>();
	ArrayList<String> mitigatedVulnerabilitesString = new ArrayList<String>();
	ArrayList<Resource> currentVulnerabilities = new ArrayList<Resource>();
	ArrayList<String> currentVulnerabilitiesString = new ArrayList<String>();
	ArrayList<Resource> currentLowLevelThreats = new ArrayList<Resource>();
	ArrayList<String> currentLowLevelThreatsString = new ArrayList<String>();
	ArrayList<Resource> currentTopLevelThreats = new ArrayList<Resource>();
	ArrayList<String> currentTopLevelThreatsString = new ArrayList<String>();
	ArrayList<Resource> loweredThreats = new ArrayList<Resource>();
	ArrayList<String> loweredThreatsString = new ArrayList<String>();
	ArrayList<Resource> controlsThatMitigateVulnerabilities = new ArrayList<Resource>();
	ArrayList<Resource> recommendationsAsClass = new ArrayList<Resource>();
	ArrayList<Recommendation> recommendations = new ArrayList<Recommendation>();



	
	// Constructor, receives an OntModel where the SME was mapped into
	public ReasoningJena (OntModel ontModel, String businessName) {
		base = ontModel;
		nameOfBusiness = businessName;
		businessIRI = NS + businessName;
		businessResource = base.getResource(businessIRI);
		buildingResource = base.getResource(NS + "BuildingOf" + businessName);
		sectionResource = base.getResource(NS + "SectionOne");
	}
	
	public ArrayList<String> getImplementedControls () {
		return implementedControlsString;
	}
	
	public ArrayList<String> getMitigatedVulnerabilities () {
		return mitigatedVulnerabilitesString;
	}
	
		public ArrayList<String> getLoweredThreats () {
		return loweredThreatsString;
	}
	
	
	
	public void listImplementedControls () {

		System.out.println("listImplementedControls () has started.");
		Individual businessIndividual = base.getIndividual(businessIRI);
		
		ExtendedIterator<OntClass> iter = businessIndividual.listOntClasses(true);
		
        while (iter.hasNext()) {
            OntClass isSubtypeOfClass = iter.next();
            System.out.println("The Business is compliant with the following Controls: " + isSubtypeOfClass.getLocalName());
        }
        
        // Get the controls the Organization is compliant with
        Property controlCompliantWithControlProperty = base.getProperty(NS + "control_compliantWith_Control");        
        OntClass control = base.getOntClass(NS + "Control");
        ExtendedIterator<? extends OntResource> listInstancesIter = control.listInstances(false);
        System.out.println("The Instance Iterator was initalized.");
        
        controlMitigatesVulnerability = base.getProperty(NS + "control_mitigates_Vulnerability");
        vulnerabilityExploitedByThreat = base.getProperty(NS + "vulnerability_exploitedBy_Threat");
        threatGivesRiseToThreat = base.getProperty(NS + "threat_givesRiseTo_Threat");
        
       
        while (listInstancesIter.hasNext()) {
        	Resource controlInstance = listInstancesIter.next();
        	boolean controlCompliantOrganization = controlInstance.hasProperty(controlCompliantWithControlProperty, businessResource);
        	boolean controlCompliantBuilding = controlInstance.hasProperty(controlCompliantWithControlProperty, buildingResource);
        	boolean controlCompliantSection = controlInstance.hasProperty(controlCompliantWithControlProperty, sectionResource);
        	
        	if (controlCompliantOrganization || controlCompliantBuilding || controlCompliantSection) {
        		implementedControls.add(controlInstance);
        		implementedControlsString.add(controlInstance.getLocalName());
        		System.out.print("The business implements the control " + controlInstance.getLocalName());
        		System.out.print(" this mitigates the vulnerability ");
        	// The implemented controls mitigate vulnerabilities
        		if (controlInstance.hasProperty(controlMitigatesVulnerability)) {
        			StmtIterator listMitgatedVulnerabilitesIter = base.listStatements(controlInstance, controlMitigatesVulnerability, (RDFNode) null);
        			while (listMitgatedVulnerabilitesIter.hasNext()) {
        				Statement stmt = listMitgatedVulnerabilitesIter.next();
        				Resource mitigatedVulnerability = (Resource) stmt.getObject();
        				mitigatedVulnerabilites.add(mitigatedVulnerability);
        				mitigatedVulnerabilitesString.add(mitigatedVulnerability.getLocalName());
        				System.out.print("'" + mitigatedVulnerability.getLocalName() + "'");
        				
        				// The mitigated vulnerabilities also lower threats
        				if (mitigatedVulnerability.hasProperty(vulnerabilityExploitedByThreat)) {
        					StmtIterator listExploitedByThreatIter = base.listStatements(mitigatedVulnerability, vulnerabilityExploitedByThreat, (RDFNode) null);
        					System.out.print("thereby the exposed risk to ");
        					while (listExploitedByThreatIter.hasNext()) {
        						Statement stmt_2 = listExploitedByThreatIter.next();
        						Resource threat = stmt_2.getObject().asResource();
        						loweredThreats.add(threat);
        						loweredThreatsString.add(threat.getLocalName());
        						Individual threatIndividual = base.getIndividual(threat.getURI());
        						OntClass typeThreat = threatIndividual.getOntClass(true);
        						System.out.print(threat.getLocalName() +" (" + typeThreat.getLocalName() + ") ");
        					}
        					System.out.print("is lowered.");
        				}
        			}
        		}	
        		
        	}
        }   
        System.out.println("These are the controls that are implemented by the business: ");
        System.out.println(implementedControlsString);
	}
	
	/**
	public ArrayList<String> listNotImplementedControls () {
		OntClass control = base.getOntClass(NS + "Control");
		ExtendedIterator<? extends OntResource> listInstancesIter = control.listInstances(false);
		boolean controlImplemented = false;
		
		while (listInstancesIter.hasNext()) {
			Resource controlInstance = listInstancesIter.next();
			
			for (int i = 0; i < implementedControls.size(); i++ ) {
				if (implementedControls.get(i).getURI().equals(controlInstance.getURI()) == true) {
					controlImplemented = true;
				}
			}
			if (controlImplemented == false) {
				notImplementedControls.add(controlInstance);
				notImplementedControlsString.add(controlInstance.getLocalName());
			}
			controlImplemented = false;
		}
		System.out.println("These are the controls that are not implemented by the business: ");
		System.out.println(notImplementedControlsString);
		return notImplementedControlsString;
	}
	**/
	
	// Does not make sense with only the small excerpt of input values we use
	public ArrayList<String> listCurrentLowLevelThreats () {
		OntClass lowLevelThreat = base.getOntClass(NS + "LowLevelThreat");
		ExtendedIterator<? extends OntResource> listInstancesIter = lowLevelThreat.listInstances(false);
		boolean lowLevelThreatLowered = false;
		
		while (listInstancesIter.hasNext()) {
			Resource lowLevelThreatInstance = listInstancesIter.next();
		
			for (int i = 0; i < loweredThreats.size(); i++) {
				if((loweredThreats.get(i).getURI().equals(lowLevelThreatInstance.getURI())) == true) {
					lowLevelThreatLowered = true;
				}
			}
			if (lowLevelThreatLowered == false) {
			currentLowLevelThreats.add(lowLevelThreatInstance);
			currentLowLevelThreatsString.add(lowLevelThreatInstance.getLocalName());
			}
			lowLevelThreatLowered = false;
		}
		System.out.println("These are the current Low Level Threats that threaten the business: ");
		System.out.println(currentLowLevelThreatsString);
		return currentLowLevelThreatsString;
	}
	
	
	public ArrayList<String> listCurrentTopLevelThreats () {
		OntClass topLevelThreat = base.getOntClass(NS + "TopLevelThreat");
		ExtendedIterator<? extends OntResource> listInstancesIter = topLevelThreat.listInstances(false);
		boolean topLevelThreatLowered = false;
		
		while (listInstancesIter.hasNext()) {
			Resource topLevelThreatInstance = listInstancesIter.next();
		
			for (int i = 0; i < loweredThreats.size(); i++) {
				if(loweredThreats.get(i).getURI().equals(topLevelThreatInstance.getURI()) == true) {
					topLevelThreatLowered = true;
				}
			}
			if (topLevelThreatLowered == false) {
				currentTopLevelThreats.add(topLevelThreatInstance);
				currentTopLevelThreatsString.add(topLevelThreatInstance.getLocalName());
			}
		}
		System.out.println("These are the current Top Level Threats that threaten the business: ");
		System.out.println(currentTopLevelThreatsString);
		return currentTopLevelThreatsString;
	}
	
	
	
	// Funktioniert noch nicht so wirklich, weil der Organization automatisch keine 
	public ArrayList<String> listCurrentVulnerabilities () {
		OntClass vulnerability = base.getOntClass(NS + "Vulnerability");
		ExtendedIterator<? extends OntResource> listInstancesIter = vulnerability.listInstances(true);
		boolean mitigatedVulnerability = false;
		
		while (listInstancesIter.hasNext()) {
			Resource vulnerabilityInstance = listInstancesIter.next();
		
			for (int i = 0; i < mitigatedVulnerabilites.size(); i++) {
				if(mitigatedVulnerabilites.get(i).getURI().equals(vulnerabilityInstance.getURI()) == true) {
					mitigatedVulnerability = true;
				}
			}
			if (mitigatedVulnerability == false) {
			currentVulnerabilities.add(vulnerabilityInstance);
			currentVulnerabilitiesString.add(vulnerabilityInstance.getLocalName());
			}
			mitigatedVulnerability = false;
		}
		System.out.println("These are the current Vulnerabilities of the business: ");
		System.out.println(currentVulnerabilitiesString);
		return currentVulnerabilitiesString;
    }
	
	
	
	public void listCompliantImplementations () {

		System.out.println("listSomething method has started.");
		Resource complianceResource = base.getResource(NS + "PrivateSoftwareAndHardwareControlCompliantOrganization");
		String compliantName =  base.getOntResource(NS + "PrivateSoftwareAndHardwareControlCompliantOrganization").getLocalName();
		
		ExtendedIterator<Individual> iter = base.listIndividuals(complianceResource);
		// Iterate over the statements and print the types (classes) of the instance
        while (iter.hasNext()) {
            Individual statement = iter.next();
            Resource nameOfCompliantBusinessResource = base.getResource(statement.getURI());
            String nameOfCompliantBusiness = base.getOntResource(nameOfCompliantBusinessResource).getLocalName();
            System.out.println("The Business " + nameOfCompliantBusiness + " is compliant with the Control: " + compliantName);
        }
        System.out.println("listCompliantImplementations has finished!");
	}
	
	
	
	public ArrayList<Recommendation> generateRecommendations () {
		
		System.out.println("generateRecommendations() has started");
		
		Property vulnerabilityMitigateyByControl = base.getProperty(NS + "vulnerability_mitigatedBy_Control");
		Property controlImplmented = base.getProperty(NS + "control_compliantWith_Control");
		Property correspondsToStandard = base.getProperty(NS + "control_correspondsTo_StandardControl");
		Property annotationControl = base.getProperty(NSimported + "control");
		Property annotationInfo = base.getProperty(NSimported + "otherInformation");
		Property annotationLabel = base.getProperty("http://www.w3.org/2000/01/rdf-schema#label");
		
		String annotationControlString;
		String annotationInfoString;
		
		// könnte hier dann noch priorisieren indem man unterscheidet zwischen current Low und Top LevelThreats.
		for (int i = 0; i < currentVulnerabilities.size(); i++) {
			Resource currentVulnerability = currentVulnerabilities.get(i);
			
			if (currentVulnerability.hasProperty(vulnerabilityMitigateyByControl)) {
				StmtIterator listMitigatedByControlIter = base.listStatements(currentVulnerability, vulnerabilityMitigateyByControl, (RDFNode) null);
				
				while (listMitigatedByControlIter.hasNext()) {
				Statement stmt_3 = listMitigatedByControlIter.next();
				Resource controlThatMitigates = stmt_3.getObject().asResource();
				// Check if the control that would mitigate the vulnerability is already implemented by the business
					if (controlThatMitigates.hasProperty(controlImplmented, businessResource) == false) {
						controlsThatMitigateVulnerabilities.add(controlThatMitigates);
						
						if (controlThatMitigates.hasProperty(correspondsToStandard)) {
							StmtIterator listStandardsIter = base.listStatements(controlThatMitigates, correspondsToStandard, (RDFNode) null);
							
							while (listStandardsIter.hasNext()) {
							Statement stmt_4 = listStandardsIter.next();
							Resource standardControl = stmt_4.getObject().asResource();
							String thisControlURI = standardControl.getURI();
							Individual standardControlIndividual = base.getIndividual(thisControlURI);
							boolean recAlreadyInList = false;
							// Check if the recommendation was already issued
							for (int j = 0; j < recommendationsAsClass.size(); j++) {
								String recommendationMade = recommendationsAsClass.get(j).getURI();
								if (recommendationMade.equals(thisControlURI)) {
									recAlreadyInList = true;
								}
							}
								if (recAlreadyInList == false) {
									recommendationsAsClass.add(standardControl);
									
									Statement recommendationTitleStatement = standardControl.getProperty(annotationLabel);
									String recommendationTitle = recommendationTitleStatement.getObject().toString().replace("@en", "").replace("@de", "");
									
									
									if (standardControl.hasProperty(annotationInfo)) {
										annotationInfoString = standardControl.getProperty(annotationInfo).getObject().toString().replace("@en", "").replace("@de", "");
										annotationInfoString = EditStringService.replaceUmlaut(annotationInfoString);
									}
									else { annotationInfoString = "";
									}
									
									if (standardControl.hasProperty(annotationControl)) {
										annotationControlString = standardControl.getProperty(annotationControl).getObject().toString().replace("@en", "").replace("@de", "");
										
									}
									else { annotationControlString = "";
									}
									
									String otherInformation = annotationControlString + "\n" + annotationInfoString;
									
									
									String originDocument = standardControlIndividual.getOntClass().getLocalName();
									
									
									Recommendation recommendation = new Recommendation (recommendationTitle, otherInformation, originDocument);
									
									String threatsIfNotImplemented = "";
									StmtIterator iter_5 = currentVulnerability.listProperties(vulnerabilityExploitedByThreat);
	
									while (iter_5.hasNext()) {
										Statement stmt_5 = iter_5.next();
										Resource threatIfNotImplemented = stmt_5.getObject().asResource();
										threatsIfNotImplemented = threatIfNotImplemented.getLocalName();
	
										String threatsThatAreRised = "";
										StmtIterator iter_6 = threatIfNotImplemented.listProperties(threatGivesRiseToThreat);
										while (iter_6.hasNext()) {
											Statement stmt_6 = iter_6.next();
											Resource threatCanBeConsequence = stmt_6.getObject().asResource();
											threatsThatAreRised = threatsThatAreRised + " " + threatCanBeConsequence.getLocalName();
										}
										threatsIfNotImplemented = threatsIfNotImplemented + " which also can increase the threat of" + threatsThatAreRised;
										recommendation.addRiskIfNotImplemented(threatsIfNotImplemented);
									}
									
									recommendation.addMitigatesVulnerabilities(currentVulnerability.getLocalName());
									recommendations.add(recommendation);
									System.out.println("A recommendation has been generated: " + recommendationTitle + ", it is based on the source " + originDocument);
									System.out.println("This recommendation mitigates the vulnerability: " + currentVulnerability.getLocalName());
									System.out.println("If the recommendation won't be implemented it gives rise to the treat: ");
										for (String item : recommendation.getRiskIfNotImplemented()) {
										            System.out.println("- " + item);
										}
								}
							}
						}
					}
					}
				}
			}
		System.out.println("These are your recommendations: ");
			for (Recommendation item : recommendations) {
	            System.out.println("- " + item.getTitle());
	            System.out.println("More Info: " + item.getInformation());
	            System.out.println(" ");
	            System.out.println(" The recommendation was generated because your business has the vulnerability: " + item.getMitigatesVulnerabilities());
	            System.out.println(" ");
	            System.out.println("-------------------------------------------------------------------------------------------------- ");
	        }
		return recommendations;
		}	
}