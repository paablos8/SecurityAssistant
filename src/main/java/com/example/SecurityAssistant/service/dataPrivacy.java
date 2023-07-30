package com.example.SecurityAssistant.service;

import org.mindrot.jbcrypt.BCrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class dataPrivacy {
    
    // Methode die aufgerufen wird um bestimmte Firmeneigenschaften zu Pseudonymisieren
    public static String pseudonymizeString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Konvertiere das Byte-Array in einen Hexadezimal-String
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static boolean checkUsername(String enteredUsername, String storedUsernameHash) {
        String enteredUsernameHash = pseudonymizeString(enteredUsername);
        return enteredUsernameHash.equals(storedUsernameHash);
    }

    public String hashpw(String password) {
        String salt = BCrypt.gensalt(); // Erzeugt ein zufälliges Salz
        return BCrypt.hashpw(password, salt); // Hash des Passworts mit dem Salz erzeugen
    }

    // BCrypt kümmert sich automatisch um das Salz, wenn die checkpw()-Methode
    // verwendet wird, was die Verwendung und Überprüfung von Passwörtern in Ihrer
    // Anwendung sehr einfach macht und gleichzeitig die Sicherheit erhöht.
    public boolean checkpw(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword); // Überprüfung des Passworts gegen den gehashten Wert
    }

}
