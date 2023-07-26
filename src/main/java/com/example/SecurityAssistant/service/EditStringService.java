package com.example.SecurityAssistant.service;

public class EditStringService {
	
	
	
	public static String replaceUmlaut(String input) {
		 
	     // replace all lower Umlauts
	     String output = input.replace("ü", "ue")
	                          .replace("ö", "oe")
	                          .replace("ä", "ae")
	                          .replace("ß", "ss");
	     output = output.replace("Ü", "Ue")
                		.replace("Ö", "Oe")
                		.replace("A", "Ae")
                		.replace("ß", "ss");
	     
	     return output;
	}
}