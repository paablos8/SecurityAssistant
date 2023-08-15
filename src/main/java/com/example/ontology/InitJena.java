package com.example.ontology;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
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
			String nameOfOrganization;
			Individual organizationIndividual;
			
			ArrayList<OntClass> listOfAssets = new ArrayList<>();

			
	//The source of the ontology
			static String SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
			static String NS = SOURCE + "#";
			static String SOURCEIMPORTED = "http://securityontology.com/secontMueller.owl";
			static String NSimported = SOURCEIMPORTED + "#";
			static String filePath;
			OntModel base;
			OntDocumentManager dm;
			InfModel infModel;
			
	public OntModel getOntModel() {
		return this.base;
	}


	// Loads the base ontology by Fenz (2016)
	public  void loadOntology () {

			//the Mueller2023_Security_shortened ontology is replicated locally on the disk which gets loaded when the normal URL gets called
				//filePath = "file:///C:/Users/tscha/Development/SecurityAssistant/src/main/java/com/example/ontology/files/Mueller2023_Security_shortened.rdf";
				filePath = "file:/C:/Users/Wiwi-Admin/eclipse-workspace/SecurityAssistant/src/main/java/com/example/ontology/files/Mueller2023_Security_shortened.rdf";
		
		
			//Create the base model - creates an OWL-FUll, in-memory Ontology Model with an optimised rule-based reasoner with OWL rules.
				base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);

			//DocumentManager created:
				dm = base.getDocumentManager();
				dm.addAltEntry( "http://securityontology.sba-research.org/securityontology.owl", filePath);
				base.read("http://securityontology.sba-research.org/securityontology.owl");

				System.out.println("Ontology was successfully loaded.");
		}


	public void addOrganization (String userName, String nameOfOrganization, int numberOfEmployees, String industry, String bundesland) {

			this.userName = userName;
			this.nameOfOrganization = nameOfOrganization;
		
			String organizationURI = NS + "Organization";
			Individual industryIndividual = base.createIndividual(NSimported + industry, base.createClass(NS + "Industry"));	
			System.out.println("Industry Individual was created.");
			organizationIndividual = base.createIndividual(NS + nameOfOrganization, base.createClass(organizationURI));
			System.out.println("Organization Individual was created.");
			Property organizationHasNumberOfEmployees = base.getProperty(NS + "organization_has_NumberOfEmployees");
			System.out.println("Property has employees was retrieved.");
			organizationIndividual.addLiteral(organizationHasNumberOfEmployees, numberOfEmployees);
			Property organization_isLocatedIn_Industry = base.getProperty(NSimported + "organization_isLocatedIn_Industry");
			organizationIndividual.addProperty(organization_isLocatedIn_Industry, industryIndividual);
		// Add the bundesland to the ontology and connect it to the organization
			Individual bundeslandIndvidual = base.createIndividual(NS + bundesland, base.createClass(NS + "Location"));
			Property organizationHasLocation = base.getProperty(NS + "organization_has_Location");
			organizationIndividual.addProperty(organizationHasLocation, bundeslandIndvidual);
	}
	
	public void addPasswordPolicy (String pwChange, String pwProperties) {
		System.out.println(pwChange);
		System.out.println(pwProperties);
		boolean pwChangeCompliant = (pwChange.equals("Monthly") || pwChange.equals("Multipletimesayear"));
		boolean pwPropertiesCompliant = (pwProperties.equals("Passwordmustberandomlygenerated") || pwProperties.equals("Passwordlengthmustincludenumbersandspecialcharacters"));
		System.out.println(pwChangeCompliant);
		System.out.println(pwPropertiesCompliant);
		if (pwChangeCompliant && pwPropertiesCompliant) {
			Individual passwordPolicyIndividual = base.createIndividual(NS + "PasswordPolicyOf" + nameOfOrganization, base.createClass(NSimported + "PasswordPolicy"));
		// Add the Object Property
			ObjectProperty organizationImplementsPolicy = base.getObjectProperty(NS + "organization_implements_Policy");
			organizationIndividual.addProperty(organizationImplementsPolicy, passwordPolicyIndividual);
			
		}
	}



	public void addPolicy (String typeOfPolicy, String implementedPolicy) {
		String currentNS; // Die if-else Schleife hat extreme Laufzeit Probleme
		if((base.getIndividual(NS + typeOfPolicy)) == null) {
			System.out.println("base.getIndividual(NS + typeOfPolicy: " + base.getIndividual(NS + typeOfPolicy));
			currentNS = NSimported;
			System.out.println("currentNS: " + currentNS);
		} else {
			System.out.println("base.getIndividual(NS + typeOfPolicy: " + base.getIndividual(NS + typeOfPolicy));
			currentNS = NS;
			System.out.println("currentNS: " + currentNS);
		}
		Individual implementedPolicyIndividual = base.createIndividual(NS + implementedPolicy, base.createClass(currentNS + typeOfPolicy));
	// Add the Object Property
		ObjectProperty organizationImplementsPolicy = base.getObjectProperty(NS + "organization_implements_Policy");
		organizationIndividual.addProperty(organizationImplementsPolicy, implementedPolicyIndividual);
		System.out.println("Individual successfully created: " + implementedPolicy);
	}
	
	public void addAsset (String typeOfAsset, String addedAsset) {
		String currentNS; // Die if-else Schleife hat extreme Laufzeit Probleme
		if((base.getIndividual(NS + typeOfAsset)) == null) {
			System.out.println("base.getIndividual(NS + typeOfAsset: " + base.getIndividual(NS + typeOfAsset));
			currentNS = NSimported;
			System.out.println("currentNS: " + currentNS);
		} else {
			System.out.println("base.getIndividual(NS + typeOfAsset: " + base.getIndividual(NS + typeOfAsset));
			currentNS = NS;
			System.out.println("currentNS: " + currentNS);
		}
		Individual addedAssetIndividual = base.createIndividual(NS + addedAsset, base.createClass(currentNS + typeOfAsset));
		ObjectProperty organizationOwnsAsset = base.getObjectProperty(NS + "organization_owns_Asset");
		organizationIndividual.addProperty(organizationOwnsAsset, addedAssetIndividual);
		System.out.println("Individual successfully created: " + addedAsset);
	}
	
	
	public void addAssetToBuilding (String building, String implementedAsset, String typeOfAsset) {
		
		Individual buildingIndividual = base.createIndividual(NS + building, base.createClass(NS + "Building"));
		ObjectProperty buildingHousesOrganization = base.getObjectProperty(NSimported + "building_houses_organization");
		Individual sectionIndividual = base.createIndividual(NS + "SectionOne", base.createClass(NS + "Section"));
		ObjectProperty sectionBelongsToBuilding = base.getObjectProperty(NSimported + "section_belongsTo_Building");
		Individual assetIndividual = base.createIndividual(NS + implementedAsset, base.createClass(NS + typeOfAsset));
		ObjectProperty assetLocatedInAsset = base.getObjectProperty(NS + "asset_locatedIn_Asset");
		
		if(typeOfAsset.equals("EntryCheckpoint")) {
		assetIndividual.addProperty(assetLocatedInAsset, buildingIndividual);
		buildingIndividual.addProperty(buildingHousesOrganization, organizationIndividual);
		}
		
		else {
		assetIndividual.addProperty(assetLocatedInAsset, sectionIndividual);
		sectionIndividual.addProperty(sectionBelongsToBuilding, buildingIndividual);
		buildingIndividual.addProperty(buildingHousesOrganization, organizationIndividual);
		}
	}	

}
