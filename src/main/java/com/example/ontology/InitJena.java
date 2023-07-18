package com.example.ontology;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
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

public class InitJena {

			String userName;
			String nameOfBusiness;
			Individual organizationIndividual;
	//The source of the ontology
			static String SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
			static String NS = SOURCE + "#";
			static String filePath;
			OntModel base;
			OntDocumentManager dm;
			InfModel infModel;


	// Loads the base ontology by Fenz (2016)
	public  void loadOntology () {

			//the Fenz (2016) ontology is replicated locally on the disk which gets loaded when the normal URL gets called
				filePath = "file:///C:/Users/Wiwi-Admin/eclipse-workspace/SecurityAssistant/src/main/java/com/example/ontology/files/fenz2016AsRDF.owl.rdf";

			//Create the base model - creates an OWL-FUll, in-memory Ontology Model
				base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);


			//DocumentManager created:
				dm = base.getDocumentManager();
				dm.addAltEntry( "http://securityontology.sba-research.org/securityontology.owl", filePath);
				base.read("http://securityontology.sba-research.org/securityontology.owl");

				System.out.println("Ontology was successfully loaded.");
		}


	public void addOrganization (String userName, String nameOfOrganization) {

			this.userName = userName;
		// Create the individual organization
			String organizationURI = NS + "Organization";
			organizationIndividual = base.createIndividual(NS + nameOfOrganization, base.createClass(organizationURI));
			System.out.println("Individual successfully created: " + nameOfOrganization);
	}



	public void addPolicy (String typeOfPolicy, String implementedPolicy) {
		Individual implementedPolicyIndividual = base.createIndividual(NS + implementedPolicy, base.createClass(NS + typeOfPolicy));
	// Add the Object Property
		ObjectProperty organizationImplementsPolicy = base.getObjectProperty(NS + "organization_implements_Policy");
		organizationIndividual.addProperty(organizationImplementsPolicy, implementedPolicyIndividual);
		System.out.println("Individual successfully created: " + implementedPolicy);

		/**
		Resource resource = infModel.getResource(NS + "Asset");
		StmtIterator types = infModel.listStatements(resource, RDF.type, (RDFNode) null);
		while (types.hasNext()) {
		    Statement typeStatement = types.next();
		    Resource individual = typeStatement.getSubject();
		    Resource inferredClass = typeStatement.getObject().asResource();
		    // Do something with the inferred class membership
		}
		**/

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
			Individual antiVirusSoftwareIndividual = base.createIndividual(NS + antiVirusSoftware, base.createClass(antiVirusSoftwareURI));
			System.out.println("Individual successfully created: " + antiVirusSoftware);

		// Do some inference stuff
			/**
			Resource computerResource = infModel.getResource(NS + computer);
			System.out.println("This is all we know about: " + computer);
			printStatements(infModel, computerResource, null, null);
			**/



		// Object Properties:
		// Create and add ObjectProperty system_hasInstalled_Software and connect the individual AntiVirusSoftware to the added OS
			ObjectProperty hasInstalledSoftware = base.getObjectProperty(NS + "system_hasInstalled_Software");
			osIndividual.addProperty(hasInstalledSoftware, antiVirusSoftwareIndividual);
		 	System.out.println("Object Properties successfully created: system_hasInstalled_Software" );


		// Create and add ObjectProperty ITComponent_connectedTo_System and connect the individual OS to the added Workstation
		 	//ObjectProperty connectedToSystem = base.getObjectProperty(NS + "ITComponent_connectedTo_System");
		 	//computerIndividual.addProperty(connectedToSystem, osIndividual);
		 	//System.out.println("Object Properties successfully created: ITComponent_connectedTo_System" );

		// Create and add ObjectProperty organization_owns_Asset and connect the individual workstation to the added nameOfTheSME
			ObjectProperty organizationOwnsAsset = base.getObjectProperty(NS + "organization_owns_Asset");
			organizationIndividual.addProperty(organizationOwnsAsset, computerIndividual);
			System.out.println("Object Properties successfully created: organization_owns_Asset" );
	}


	public InfModel applyInference () {

		// Create an instance of the reasoner:
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		infModel = ModelFactory.createInfModel(reasoner, base); // Builds an inferred model by attaching the given RDF model to the given reasoner.
		System.out.println("Reasoner was created.");
		// Enable the reasoning
		infModel.setNsPrefixes(base.getNsPrefixMap());
		infModel.prepare();
		System.out.println("infMode.prepare() successfully finished.");
		//infModel.validate();
		//System.out.println("infMode.validate() successfully finished.");

		return infModel;
	}

	public void listCompliantImplementations () {

		System.out.println("listCompliantImplementations method has started.");
		Resource complianceResource = base.getResource(NS + "PrivateSoftwareAndHardwareControlCompliantOrganization");
		Property controlCompliantWithControl = base.getProperty(NS + "control_compliantWith_Control");
		Resource PrivateSWAndHWControl = base.getResource(NS + "PrivateSoftwareAndHardwareControl");
		//StmtIterator stmtIterator = infModel.listStatements(organizationResource, RDF.type, (RDFNode) null);
		//StmtIterator stmtIterator = base.listStatements(organizationResource, null, (RDFNode) null);
		ExtendedIterator<Individual> iter = base.listIndividuals(complianceResource);
		// Iterate over the statements and print the types (classes) of the instance
        while (iter.hasNext()) {
            Individual statement = iter.next();
            //Individual type = statement.
            System.out.println("The following businesses implement the control: " + statement.getURI());
        }
        System.out.println("listCompliantImplementations has finished!");
	}


	public String saveOntology (String userName) {

				String outputFilePath = "C:\\Users\\Wiwi-Admin\\Desktop\\" + userName + "_InferenceTest.owl.xml";

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


	public void printStatements(Model m, Resource s, Property p, Resource o) {
		for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
			Statement stmt = i.nextStatement(); System.out.println(" - " + PrintUtil.print(stmt));
			}
		}

}
