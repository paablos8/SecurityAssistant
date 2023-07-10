package com.example.ontology;

import java.util.Iterator;
import java.io.*;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class InitJena {

	static //The source of the ontology
			String SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
			static String NS = SOURCE + "#";
			
			static String filePath;
			OntModel base;
			OntDocumentManager dm;
	
	
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

	
	
	// Adds a computer to the ontology.
	public void addComputer (String computer) {
		
			String classURI = NS + "Workstation";
		
		// Create the individual
			Individual computerMuller = base.createIndividual(NS + "Computer1_Müller", base.createClass(classURI));
			System.out.println("Individual successfully created: ");
			OntClass artefact = base.getOntClass( NS + "Workstation" );
			for (Iterator<OntClass> i = artefact.listSubClasses(); i.hasNext(); ) {
			  OntClass c = i.next();
			  System.out.println( c.getURI() );
			}
		
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
	        
	        
		//this adds the individual 'person' in the class 'Paper'
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
				
		// Save the ontology
				String outputFilePath = "C:\\Users\\Wiwi-Admin\\Desktop\\fenz2016_modified.owl.xml";
			
				 try {
			           // Create a new File object with the specified file path
			            File file = new File(outputFilePath);

			            // Create the file if it doesn't exist
			            if (!file.exists()) {
			                file.createNewFile();
			                System.out.println("New file created successfully!");
			            } else {
			                System.out.println("File already exists.");
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
			
								
			
	}
			
		
			
}
