package com.example.SecurityAssistant.service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

@Service
public class dataPrivacy {

    // Method gets called to pseudonymize the String which is handed over by hashing
    // it with SHA-256. In our case we use this for the username, companyName and
    // the location
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

    // Checks whether the hashed username entered is equal to a stored username
    public static boolean checkUsername(String enteredUsername, String storedUsernameHash) {
        String enteredUsernameHash = pseudonymizeString(enteredUsername);
        return enteredUsernameHash.equals(storedUsernameHash);
    }
}
