package com.example.ontology;

import java.util.Iterator;
import java.io.*;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class InitJena {

	
			String nameOfBusiness;
			Individual organizationIndividual;
			
	//The source of the ontology
			static String SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
			static String NS = SOURCE + "#";
			
			static String filePath;
			OntModel base;
			OntDocumentManager dm;
	
	
	// Loads the base ontology by Fenz (2016)
	public  void loadOntology () {
			
			//the Fenz (2016) ontology is replicated locally on the disk which gets loaded when the normal URL gets called
				String filePath = "file:///C:/Users/Wiwi-Admin/eclipse-workspace/SecurityAssistant/src/main/java/com/example/ontology/files/fenz2016_test.owl.xml";
		
			//Create the base model - creates an OWL-FUll, in-memory Ontology Model
				base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
				
			//DocumentManager created:
				dm = base.getDocumentManager();
				
				dm.addAltEntry( "http://securityontology.sba-research.org/securityontology.owl", filePath);
				base.read("http://securityontology.sba-research.org/securityontology.owl");
				
				System.out.println("Ontology was successfully loaded.");
		}
	
	public void addOrganization (String nameOfOrganization) {
		// Create the individual organization
			String organizationURI = NS + "Organization";
			Individual organizationIndividual = base.createIndividual(NS + nameOfOrganization, base.createClass(organizationURI));
			System.out.println("Individual successfully created: " + nameOfOrganization);
	}

	
	
	// Adds a computer to the ontology.
	public void addComputer (String computer, String os, String antiVirusSoftware) {
		
		// Create the individual computer
			String workstationURI = NS + "Workstation";
			Individual computerIndividual = base.createIndividual(NS + computer, base.createClass(workstationURI));
			System.out.println("Individual successfully created: " + computer);
		// Create the individual OS
			String osURI = NS + "OS";
			Individual osIndividual = base.createIndividual(NS + os, base.createClass(osURI));
			System.out.println("Individual successfully created: " + os);
		// Create the individual Anti Virus Software
			String antiVirusSoftwareURI = NS + "TransactionSecurityAndVirusProtectionSoftware";
			Individual antiVirusSoftwareIndividual = base.createIndividual(NS + os, base.createClass(antiVirusSoftwareURI));
		
		// Object Properties:
		// Create and add ObjectProperty system_hasInstalled_Software and connect the individual AntiVirusSoftware to the added OS
			String system_hasInstalled_SoftwareURI = NS + "system_hasInstalled_Software";
			ObjectProperty systemHasInstalledSoftware = base.createObjectProperty(system_hasInstalled_SoftwareURI);
		// Set the domain and range of the object property
			systemHasInstalledSoftware.setDomain(osIndividual);
			systemHasInstalledSoftware.setRange(antiVirusSoftwareIndividual);
		// Create and add ObjectProperty ITComponent_connectedTo_System and connect the individual OS to the added Workstation
			String itComponent_connectedTo_SystemURI = NS + "ITComponent_connectedTo_System";
			ObjectProperty itComponent_connectedTo_System = base.createObjectProperty(itComponent_connectedTo_SystemURI);
		// Set the domain and range of the object property
			itComponent_connectedTo_System.setDomain(computerIndividual);
			itComponent_connectedTo_System.setRange(osIndividual);
		// Create and add ObjectProperty organization_owns_Asset and connect the individual workstation to the added nameOfTheSME
			String organization_owns_AssetURI = NS + "organization_owns_Asset";
			ObjectProperty organization_owns_Asset = base.createObjectProperty(organization_owns_AssetURI);
		// Set the domain and range of the object property
			organization_owns_Asset.setDomain(organizationIndividual);
			organization_owns_Asset.setRange(computerIndividual);
			
			
		// Save the modified ontology to the output file
			String outputFilePath = "C:\\Users\\Wiwi-Admin\\Desktop\\fenz2016_modified.owl.xml";
			try (OutputStream out = new FileOutputStream(outputFilePath)) {
			    base.write(out, "RDF/XML");
			    System.out.println("Ontology successfully saved to " + outputFilePath);
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}
			
			
	public void writeInOntology (String instance) { 
	
		// create a dummy paper for this example
			OntClass person = base.getOntClass( NS + "Person" );
			
		// Create a new individual with a unique identifier
	        Individual individual = base.createIndividual(NS + "Pablo", base.createClass(NS + "Person"));
	        System.out.println("Individual has been created!");
	        
	        
		//this adds the individual 'person' in the class 'Person'
			Individual pablo = base.createIndividual( NS + "pablo", person );
			
			
		// In our example ontology we can print a list of the subclasses of an Artefact as follows:
				OntClass artefact = base.getOntClass( NS + "Person" );
				for (Iterator<OntClass> i = artefact.listSubClasses(); i.hasNext(); ) {
				  OntClass c = i.next();
				  System.out.println( c.getURI() );
				}
				
		// Print the ontology out
				/*
				System.out.println("The onotlogy is printed here:");
				base.write(System.out);
				*/
	}
				
				
				
	
	
	
	
	
	public String saveOntology (String nameOfOrganization) {
		// Save the ontology
				String outputFilePath = "C:\\Users\\Wiwi-Admin\\Desktop\\" + nameOfOrganization + ".owl.xml";
			
				 try {
			           // Create a new File object with the specified file path
			            File file = new File(outputFilePath);

			            // Create the file if it doesn't exist
			            if (!file.exists()) {
			                file.createNewFile();
			                System.out.println("New file created successfully!");
			            } else {
			                System.out.println("File already exists. It will now be overwritten.");
			            } 
			           } catch (IOException e) {
			                e.printStackTrace();
			            }

		// Save the modified ontology to the output file
				try (OutputStream out = new FileOutputStream(outputFilePath)) {
				    base.write(out, "RDF/XML");
				    System.out.println("Ontology successfully saved to " + outputFilePath);
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
			return outputFilePath;

								
			
	}
			
		
			
}
