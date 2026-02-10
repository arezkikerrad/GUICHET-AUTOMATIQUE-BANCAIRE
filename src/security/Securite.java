package src.security;

import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Securite {
    
    // clé sucrete pour la signature
    private static final String CLE_SECRETE = "Maclesecrete";

    // hach un pin
    public static String hasherPIN(String pin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pin.getBytes());
            
            // convertir bytes en hexa
            StringBuilder hex = new StringBuilder();
            for(byte b :hash) {
                String hexByte = String.format("%02x", b);
                hex.append(hexByte);
            }
            return hex.toString();
        } catch (Exception e) {
            // fallback si possible
            return "HASH_" + pin.hashCode();
        }
    }
    // verifier un pin
    public static boolean verifierPIN(String pinSaisi, String pinStockeHash) {
        String pinSaisiHash = hasherPIN(pinSaisi);
        return pinSaisiHash.equals(pinStockeHash);
    }
    // signature
    public static String signerMessage(String message) {
        int hash = message.hashCode();
        int secrethash = CLE_SECRETE.hashCode();
        int signature = hash ^ secrethash; //XOR

        return "SIG_" + Integer.toHexString(signature);
    }
 
}


