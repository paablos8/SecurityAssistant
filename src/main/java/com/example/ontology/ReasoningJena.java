package com.example.ontology;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

public class ReasoningJena {
	
	OntModel base;
	String NS = InitJena.NS;
	String companyIRI;
	String nameOfBusiness;
	
	// Constructor, receives an OntModel where the SME was mapped into
	public ReasoningJena (OntModel ontModel, String companyName) {
		base = ontModel;
		nameOfBusiness = companyName;
		companyIRI = NS + companyName;
	}
	
	
	
	public void listSomething () {

		System.out.println("listCompliantImplementations method has started.");
		Individual companyResource = base.getIndividual(companyIRI);
		
		ExtendedIterator<OntClass> iter = companyResource.listOntClasses(true);
		
        while (iter.hasNext()) {
            OntClass isSubtypeOfClass = iter.next();
            System.out.println("The Business " + nameOfBusiness + " is compliant with the following Controls: " + isSubtypeOfClass.getLocalName());
        }
        System.out.println("listCompliantImplementations has finished");
	}
	
	
	public void listCompliantImplementations () {

		System.out.println("listSomething method has started.");
		Resource complianceResource = base.getResource(NS + "PrivateSoftwareAndHardwareControlCompliantOrganization");
		String compliantName =  base.getOntResource(NS + "PrivateSoftwareAndHardwareControlCompliantOrganization").getLocalName();
		// Property controlCompliantWithControl = base.getProperty(NS + "control_compliantWith_Control");
		// Resource PrivateSWAndHWControl = base.getResource(NS + "PrivateSoftwareAndHardwareControl");
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
	
	
	/**
	public InfModel applyInference () {
	
			
			// Create an instance of the reasoner:
			Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
			System.out.println("Reasoner was created.");
			// Enable the reasoning
			infModel.setNsPrefixes(base.getNsPrefixMap());
			infModel.prepare();
			System.out.println("infMode.prepare() successfully finished.");
			//infModel.validate();
			//System.out.println("infMode.validate() successfully finished.");
	
			return infModel;
		}
		**/
	
	
	
}