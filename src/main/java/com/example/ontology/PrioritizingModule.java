package com.example.ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

import com.example.SecurityAssistant.entities.Recommendation;

public class PrioritizingModule {
	
	OntModel base;
	String NS = InitJena.NS;
	String NSimported = InitJena.NSimported;
	String businessIRI;
	Resource businessResource;
	
	Property vulnerabilityExploitedByThreat;
	Property givesRiseToThreat;
	

	
	// Constructor, receives an OntModel where the SME was mapped into
		public PrioritizingModule (OntModel ontModel, String businessIRI) {
			base = ontModel;
			this.businessIRI = businessIRI;
			businessResource = base.getResource(businessIRI);
			vulnerabilityExploitedByThreat = base.getProperty(NS + "vulnerability_exploitedBy_Threat");
			givesRiseToThreat = base.getProperty(NS + "threat_givesRiseTo_Threat");
		}
		
		
		public ArrayList<Recommendation> prioritizeRecommendations (ArrayList<Recommendation> recommendations) {
			
			for (int i = 0; i < recommendations.size(); i++) {
				Recommendation currentRecommendation = recommendations.get(i);
				int priorityScore = 0;
				
				ArrayList<Resource> threats = new ArrayList<Resource>();
				threats = currentRecommendation.getRiskIfNotImplementedResource();
				
				for (int j = 0; j < threats.size(); j++) {
					String currentThreat = threats.get(j).getURI();
					Individual currentThreatIndividual = base.getIndividual(currentThreat);
					ExtendedIterator<OntClass> iter = currentThreatIndividual.listOntClasses(false);
					while (iter.hasNext()) {
						OntClass threatType = iter.next();		
						String typeOfThreat = threatType.getURI();
						
						if (typeOfThreat.equals("http://securityontology.sba-research.org/securityontology.owl#TopLevelThreat")) {
							priorityScore = priorityScore + 20;
						}
						
						else if (typeOfThreat.equals("http://securityontology.sba-research.org/securityontology.owl#LowLevelThreat")) {
							priorityScore = priorityScore + 10;
						}
					}
					
					if (currentThreatIndividual.hasProperty(givesRiseToThreat)) {
						StmtIterator iter_2 = currentThreatIndividual.listProperties(givesRiseToThreat);
					
						while (iter_2.hasNext()) {
							Statement stmt_2 = iter_2.next();
							Resource threatCanBeConsequence = stmt_2.getObject().asResource();
							Individual threatCanBeConsequenceIndividual = base.getIndividual(threatCanBeConsequence.getURI());
							
							ExtendedIterator<OntClass> iter_3 = threatCanBeConsequenceIndividual.listOntClasses(false);
							while (iter_3.hasNext()) {
								OntClass threatType_2 = iter_3.next();
								String typeOfThreat_2 = threatType_2.getURI();
							
								if (typeOfThreat_2.equals("http://securityontology.sba-research.org/securityontology.owl#TopLevelThreat")) {
									priorityScore = priorityScore + 5;
									System.out.println("The threat also gives rise to the threat: " + threatCanBeConsequence.getLocalName() + " which is a " + threatType_2.getLocalName());
									System.out.println("This gives additional 5 Priority Points - current Score is: " + priorityScore);
								}
								
								else if (typeOfThreat_2.equals("http://securityontology.sba-research.org/securityontology.owl#LowLevelThreat")) {
									priorityScore = priorityScore + 3;
									System.out.println("The threat also gives rise to the threat: " + threatCanBeConsequence.getLocalName() + " which is a " + threatType_2.getLocalName());
									System.out.println("This gives additional 3 Priority Points - current Score is: " + priorityScore);
								
								}
							}
						}
					}
						
					System.out.println("The recommendation: " + currentRecommendation.getTitle() + " has the threat: " + currentThreatIndividual.getLocalName());
					System.out.println("For this threat it gets the Priority Score: " + priorityScore);
					System.out.println(" ");
				}
				
				currentRecommendation.setPriorityScore(priorityScore);
			} 
			// Every PriorityScore is now assigned, now sort the list:
			// Sort the list based on priorityScore in descending order
	        Collections.sort(recommendations, new Comparator<Recommendation>() {
	            @Override
	            public int compare(Recommendation r1, Recommendation r2) {
	                // Sort in descending order (highest to lowest)
	                return Integer.compare(r2.getPriorityScore(), r1.getPriorityScore());
	            }
	        });
	        return recommendations;
		}
	
}