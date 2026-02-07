package src.enums;

public enum TransactionStatus {
    SUCCES("✅ Transaction réussie"),
    ERREUR_SOLDE_INSUFFISANT("❌ Solde insuffisant"),
    ERREUR_PIN_INCORRECT("❌ Code PIN incorrect"),
    ERREUR_LIMITE_DEPASSEE("❌ limite quotidienne dépassée"),
    ERREUR_COMPTE_BLOQUE("❌ Compte bloqué");
    

    

    private final String message;
    
    // constructeur
    TransactionStatus(String message) {
        this.message = message;
    }

    //getter
    public String getMessage() {
        return message;
    }
}
