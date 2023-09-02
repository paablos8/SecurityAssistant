package com.example.ontology;

import java.util.ArrayList;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

public class InitJena {

	String userName;
	String nameOfOrganization;
	Individual organizationIndividual;

	ArrayList<OntClass> listOfAssets = new ArrayList<>();

	// The source of the ontology
	static String SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
	// The prefix of the IRI used by this ontology
	static String NS = SOURCE + "#";
	static String SOURCEIMPORTED = "http://securityontology.com/secontMueller.owl";
	// The prefix of the IRI used by the imported/modified ontology.
	static String NSimported = SOURCEIMPORTED + "#";
	static String filePath;
	OntModel base;
	OntDocumentManager dm;
	InfModel infModel;

	public OntModel getOntModel() {
		return this.base;
	}

	// Loads the base ontology by Fenz (2016)
	public void loadOntology() {

		// the Mueller2023_Security_shortened ontology is replicated locally on the
		// disk.
		// filePath =
		// "file:///C:/Users/tscha/Development/SecurityAssistant/src/main/java/com/example/ontology/files/Mueller2023_Security.owl";
		filePath = "file:/C:/Users/pablo/SecurityAssistant/src/main/java/com/example/ontology/files/Mueller2023_Security.owl";

		// Create the base model - creates an OWL-FUll, in-memory Ontology Model with an
		// optimised rule-based reasoner with OWL rules.
		base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);

		// DocumentManager created:
		dm = base.getDocumentManager();
		// The local ontology will get loaded when the ontology URL gets called:
		// The locally stored ontology is used because: 1.) no risk for a delay when
		// accessing URLs
		// 2.) no risk of extra work to cope with intervening firewalls or web proxies.
		dm.addAltEntry("http://securityontology.sba-research.org/securityontology.owl", filePath);
		base.read("http://securityontology.sba-research.org/securityontology.owl");
		System.out.println("Ontology was successfully loaded.");
	}

	public void addOrganization(String userName, String nameOfOrganization, int numberOfEmployees, String industry,
			String bundesland) {

		this.userName = userName;
		this.nameOfOrganization = nameOfOrganization;
		// The IRI of the class 'Organization' in the ontology.
		String organizationURI = NS + "Organization";
		// Creates an instance of the type 'Industry' in the ontology.
		Individual industryIndividual = base.createIndividual(NSimported + industry, base.createClass(NS + "Industry"));
		System.out.println("Industry Individual was created.");
		// Creates an instance of the type 'Organization' with the IRI consisting of the
		// Prefix + the name provided by the user.
		organizationIndividual = base.createIndividual(NS + nameOfOrganization, base.createClass(organizationURI));
		System.out.println("Organization Individual was created.");
		// Get the object property assertion 'organization_has_NumberOfEmployeesto' to
		// later relate the number of employees to the organization instance.
		Property organizationHasNumberOfEmployees = base.getProperty(NS + "organization_has_NumberOfEmployees");
		System.out.println("Property has employees was retrieved.");
		// Creates an object property assertion to relate the number of employees to the
		// just added organization instance.
		organizationIndividual.addLiteral(organizationHasNumberOfEmployees, numberOfEmployees);
		Property organization_isLocatedIn_Industry = base.getProperty(NSimported + "organization_isLocatedIn_Industry");
		organizationIndividual.addProperty(organization_isLocatedIn_Industry, industryIndividual);
		// Add the bundesland to the ontology and relate it to the organization.
		Individual bundeslandIndvidual = base.createIndividual(NS + bundesland, base.createClass(NS + "Location"));
		Property organizationHasLocation = base.getProperty(NS + "organization_has_Location");
		organizationIndividual.addProperty(organizationHasLocation, bundeslandIndvidual);
	}

	public void addPasswordPolicy(String pwChange, String pwProperties) {
		System.out.println(pwChange);
		System.out.println(pwProperties);
		// Depending on the answers the user gave, the system decides whether the
		// organization meets the requirements for a password policy or not.
		// These decision rules are based on ISO 27001 Question 11.3.1 e) and on our
		// individual thoughts.
		// The official ISO 27001 document askes over 20 question to determine the
		// strength of the password security management in companies -
		// this could not be realized here.
		boolean pwChangeCompliant = (pwChange.equals("Monthly") || pwChange.equals("Multipletimesayear"));
		boolean pwPropertiesCompliant = (pwProperties.equals("Passwordmustberandomlygenerated")
				|| pwProperties.equals("Passwordlengthmustincludenumbersandspecialcharacters"));
		System.out.println(pwChangeCompliant);
		System.out.println(pwPropertiesCompliant);
		if (pwChangeCompliant && pwPropertiesCompliant) {
			Individual passwordPolicyIndividual = base.createIndividual(NS + "PasswordPolicyOf" + nameOfOrganization,
					base.createClass(NSimported + "PasswordPolicy"));
			// Add the Object Property
			ObjectProperty organizationImplementsPolicy = base.getObjectProperty(NS + "organization_implements_Policy");
			organizationIndividual.addProperty(organizationImplementsPolicy, passwordPolicyIndividual);
		}
	}

	public void addPolicy(String typeOfPolicy, String implementedPolicy) {
		// Because the ontology has different prefixes (depending which (imported)
		// ontology provided the specific type of asset),
		// we need to check which prefix is used for the specific policy class.
		// The following checks which prefix the IRI of the policy type contains, which
		// the instance should be the type of.
		// Unfortunately this increases the runtime.
		String currentNS;
		if ((base.getIndividual(NS + typeOfPolicy)) == null) {
			System.out.println("base.getIndividual(NS + typeOfPolicy: " + base.getIndividual(NS + typeOfPolicy));
			currentNS = NSimported;
			System.out.println("currentNS: " + currentNS);
		} else {
			System.out.println("base.getIndividual(NS + typeOfPolicy: " + base.getIndividual(NS + typeOfPolicy));
			currentNS = NS;
			System.out.println("currentNS: " + currentNS);
		}
		Individual implementedPolicyIndividual = base.createIndividual(NS + implementedPolicy,
				base.createClass(currentNS + typeOfPolicy));
		// Add the Object Property
		ObjectProperty organizationImplementsPolicy = base.getObjectProperty(NS + "organization_implements_Policy");
		organizationIndividual.addProperty(organizationImplementsPolicy, implementedPolicyIndividual);
		System.out.println("Individual successfully created: " + implementedPolicy);
	}

	public void addAsset(String typeOfAsset, String addedAsset) {
		// Because the ontology has different prefixes (depending which (imported)
		// ontology provided the specific type of asset),
		// we need to check which prefix is used for the specific asset class.
		// The following checks which prefix the IRI of the asset type contains, which
		// the instance should be the type of.
		// Unfortunately this increases the runtime.
		String currentNS;
		if ((base.getIndividual(NS + typeOfAsset)) == null) {
			System.out.println("base.getIndividual(NS + typeOfAsset: " + base.getIndividual(NS + typeOfAsset));
			currentNS = NSimported;
			System.out.println("currentNS: " + currentNS);
		} else {
			System.out.println("base.getIndividual(NS + typeOfAsset: " + base.getIndividual(NS + typeOfAsset));
			currentNS = NS;
			System.out.println("currentNS: " + currentNS);
		}
		Individual addedAssetIndividual = base.createIndividual(NS + addedAsset,
				base.createClass(currentNS + typeOfAsset));
		ObjectProperty organizationOwnsAsset = base.getObjectProperty(NS + "organization_owns_Asset");
		organizationIndividual.addProperty(organizationOwnsAsset, addedAssetIndividual);
		System.out.println("Individual successfully created: " + addedAsset);
	}

	// Some assets needs to be related to a section instance, where the asset is
	// located.
	// Additionally, the section needs to be related to a building instance, where
	// the section is located.
	// Additionally, the building needs to be related to an organization, to which
	// the building belongs.
	public void addAssetToBuilding(String building, String implementedAsset, String typeOfAsset) {

		Individual buildingIndividual = base.createIndividual(NS + building, base.createClass(NS + "Building"));
		ObjectProperty buildingHousesOrganization = base.getObjectProperty(NSimported + "building_houses_organization");
		Individual sectionIndividual = base.createIndividual(NS + "SectionOne", base.createClass(NS + "Section"));
		ObjectProperty sectionBelongsToBuilding = base.getObjectProperty(NSimported + "section_belongsTo_Building");
		Individual assetIndividual = base.createIndividual(NS + implementedAsset, base.createClass(NS + typeOfAsset));
		ObjectProperty assetLocatedInAsset = base.getObjectProperty(NS + "asset_locatedIn_Asset");

		// The EntryCheckpoint does not have to be related to a section - thus, we can
		// skip that for every EntryCheckpoint instance.
		if (typeOfAsset.equals("EntryCheckpoint")) {
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
