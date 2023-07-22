package com.example.ontology;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

public class ReasoningJena {
	
	OntModel base;
	String NS = InitJena.NS;
	String businessIRI;
	String nameOfBusiness;
	Resource businessResource;
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

	
	// Constructor, receives an OntModel where the SME was mapped into
	public ReasoningJena (OntModel ontModel, String businessName) {
		base = ontModel;
		nameOfBusiness = businessName;
		businessIRI = NS + businessName;
		businessResource = base.getResource(businessIRI);
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
            System.out.println("The Business " + nameOfBusiness + " is compliant with the following Controls: " + isSubtypeOfClass.getLocalName());
        }
        
        // Get the controls the Organization is compliant with
        Property controlCompliantWithControlProperty = base.getProperty(NS + "control_compliantWith_Control");        
        OntClass control = base.getOntClass(NS + "Control");
        ExtendedIterator<? extends OntResource> listInstancesIter = control.listInstances(false);
        System.out.println("The Instance Iterator was initalized.");
        
        Property controlMitigatesVulnerability = base.getProperty(NS + "control_mitigates_Vulnerability");
        Property vulnerabilityExploitedByThreat = base.getProperty(NS + "vulnerability_exploitedBy_Threat");
        
       
        while (listInstancesIter.hasNext()) {
        	Resource controlInstance = listInstancesIter.next();
        	if (controlInstance.hasProperty(controlCompliantWithControlProperty, businessResource)) {
        		implementedControls.add(controlInstance);
        		implementedControlsString.add(controlInstance.getLocalName());
        		System.out.print("The business " + nameOfBusiness + " implements the control " + controlInstance.getLocalName());
        		System.out.println(" this mitigates the following vulnerabilities: ");
        	// The implemented controls mitigate vulnerabilities
        		if (controlInstance.hasProperty(controlMitigatesVulnerability)) {
        			StmtIterator listMitgatedVulnerabilitesIter = base.listStatements(controlInstance, controlMitigatesVulnerability, (RDFNode) null);
        			while (listMitgatedVulnerabilitesIter.hasNext()) {
        				Statement stmt = listMitgatedVulnerabilitesIter.next();
        				Resource mitigatedVulnerability = (Resource) stmt.getObject();
        				mitigatedVulnerabilites.add(mitigatedVulnerability);
        				mitigatedVulnerabilitesString.add(mitigatedVulnerability.getLocalName());
        				System.out.println(mitigatedVulnerability.getLocalName());
        				
        				// The mitigated vulnerabilities also lower threats
        				if (mitigatedVulnerability.hasProperty(vulnerabilityExploitedByThreat)) {
        					StmtIterator listExploitedByThreatIter = base.listStatements(mitigatedVulnerability, vulnerabilityExploitedByThreat, (RDFNode) null);
        					while (listExploitedByThreatIter.hasNext()) {
        						Statement stmt_2 = listExploitedByThreatIter.next();
        						Resource threat = stmt_2.getObject().asResource();
        						loweredThreats.add(threat);
        						loweredThreatsString.add(threat.getLocalName());
        						Individual threatIndividual = base.getIndividual(threat.getURI());
        						OntClass typeThreat = threatIndividual.getOntClass(true);
        						System.out.println("By the implemented control, the business lowers its exposed risk of " + threat.getLocalName() +", that is a " + typeThreat.getLocalName());
        					}
        				}
        			}
        		}	
        		
        	}
        }   
        System.out.println("listImplementedControls has finished!");
	}
	
	
	public ArrayList<String> listNotImplementedControls () {
		OntClass control = base.getOntClass(NS + "Control");
		ExtendedIterator<? extends OntResource> listInstancesIter = control.listInstances(false);
		
		while (listInstancesIter.hasNext()) {
			Resource controlInstance = listInstancesIter.next();
			
			for (int i = 0; i < implementedControls.size(); i++ )
				if (implementedControls.get(i).getURI().equals(controlInstance.getURI()) == false) {
					notImplementedControls.add(controlInstance);
					notImplementedControlsString.add(controlInstance.getLocalName());
				}
		}
		System.out.println("These are the controls that are not implemented by the business: ");
		System.out.println(notImplementedControlsString);
		return notImplementedControlsString;
	}
	
	
	public ArrayList<String> listCurrentLowLevelThreats () {
		OntClass lowLevelThreat = base.getOntClass(NS + "LowLevelThreat");
		ExtendedIterator<? extends OntResource> listInstancesIter = lowLevelThreat.listInstances(false);
		
		while (listInstancesIter.hasNext()) {
			Resource lowLevelThreatInstance = listInstancesIter.next();
		
		for (int i = 0; i < loweredThreats.size(); i++) {
			if(loweredThreats.get(i).getURI().equals(lowLevelThreatInstance.getURI()) == false) {
				currentLowLevelThreats.add(lowLevelThreatInstance);
				currentLowLevelThreatsString.add(lowLevelThreatInstance.getLocalName());
			}
		}
		}
		System.out.println("These are the current Low Level Threats that threaten the business: ");
		System.out.println(currentLowLevelThreatsString);
		return currentLowLevelThreatsString;
	}
	
	
	public ArrayList<String> listCurrentTopLevelThreats () {
		OntClass topLevelThreat = base.getOntClass(NS + "TopLevelThreat");
		ExtendedIterator<? extends OntResource> listInstancesIter = topLevelThreat.listInstances(false);
		
		while (listInstancesIter.hasNext()) {
			Resource topLevelThreatInstance = listInstancesIter.next();
		
		for (int i = 0; i < loweredThreats.size(); i++) {
			if(loweredThreats.get(i).getURI().equals(topLevelThreatInstance.getURI()) == false) {
				currentTopLevelThreats.add(topLevelThreatInstance);
				currentTopLevelThreatsString.add(topLevelThreatInstance.getLocalName());
			}
		}
		}
		System.out.println("These are the current Top Level Threats that threaten the business: ");
		System.out.println(currentTopLevelThreatsString);
		return currentTopLevelThreatsString;
	}
	
	
	
	
	
	
	
	
	
	
	// Funktioniert noch nicht so wirklich, weil der Organization automatisch keine 
	public ArrayList<String> listCurrentVulnerabilities () {
		OntClass vulnerability = base.getOntClass(NS + "Vulnerability");
		ExtendedIterator<? extends OntResource> listInstancesIter = vulnerability.listInstances(false);
		
		while (listInstancesIter.hasNext()) {
			Resource vulnerabilityInstance = listInstancesIter.next();
		
		for (int i = 0; i < loweredThreats.size(); i++) {
			if(mitigatedVulnerabilites.get(i).getURI().equals(vulnerabilityInstance.getURI()) == false) {
				currentVulnerabilities.add(vulnerabilityInstance);
				currentVulnerabilitiesString.add(vulnerabilityInstance.getLocalName());
			}
		}
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
	
}