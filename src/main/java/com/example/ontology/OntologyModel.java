package com.example.ontology;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

public class OntologyModel {

	private static String SOURCE = null;
	private static String NS = null;
	private static String filePath = null;
	private static OntModel base = null;
	private static OntDocumentManager dm = null;



	private OntologyModel () {

		SOURCE = "http://securityontology.sba-research.org/securityontology.owl";
		NS = SOURCE + "#";
		filePath = "file:///C:/Users/Wiwi-Admin/eclipse-workspace/SecurityAssistant/src/main/java/com/example/ontology/files/fenz2016_test.owl.xml";
		// Create the base model - creates an OWL-FUll, in-memory Ontology Model
			base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		// Create a DocumentManager
		dm = base.getDocumentManager();
		dm.addAltEntry( "http://securityontology.sba-research.org/securityontology.owl", filePath);
		base.read("http://securityontology.sba-research.org/securityontology.owl");

		System.out.println("Ontology was successfully loaded.");
	}


	// A singleton which returns the current OntModel if already one exists, or creates a new one
	public static OntModel get()
    {
        if (base == null) {
            base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            dm = base.getDocumentManager();
    		dm.addAltEntry( "http://securityontology.sba-research.org/securityontology.owl", filePath);
    		base.read("http://securityontology.sba-research.org/securityontology.owl");
        }
        return base;
    }
}