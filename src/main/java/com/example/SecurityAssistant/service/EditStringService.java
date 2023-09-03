package com.example.SecurityAssistant.service;

// Service class to modify the strings of the input form.
// Ontologies only can incorporate IRIs without whitespaces and umlauts.
public class EditStringService {

	// Replace all the umlauts in a string of an input.
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

	// Remove all whitespaces in the string of an input.
	public String removeWhitespaces(String input) {
		String sanitizedInput = input.replaceAll("\\s+", "");
		String sanitizedInput2 = sanitizedInput.replaceAll(";", "");
		String fullySanitizedInput = sanitizedInput2.replaceAll(",", "");
		return fullySanitizedInput;
	}
}