package src.security;

import java.security.MessageDigest;


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

    // verifier la signature 

    public static boolean verifierSignature(String message, String signature) {
        String bonneSignature = signerMessage(message);
        return bonneSignature.equals(signature);
    }

    // creer un message sécurisé

    public static String creerMessageSecurise(String message) {
        String signature = signerMessage(message);
        return message + "|SIG" + signature;
    }

    // lire et verifier un message securisé

    public static String lireMessageSecurise(String messageSecurise) {
        // separer message et signature
        int sigIndex = messageSecurise.indexOf("|SIG=");
        if (sigIndex == -1) {
            System.out.println("❌ Format invalide : pas de signature");
            return null;
        }
        
        String message = messageSecurise.substring(0, sigIndex);
        String signature = messageSecurise.substring(sigIndex + 5);
        
        if (verifierSignature(message, signature)) {
            return message;
        } else {
            System.out.println("❌ Signature invalide !");
            return null;
        }
    }
}


