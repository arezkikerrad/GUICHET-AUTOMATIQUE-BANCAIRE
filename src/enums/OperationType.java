package src.enums;

public enum OperationType {
    // les 5 operations possibles
    RETRAIT,
    DEPOT,
    CONSULTATION,
    IMPRESSION,
    CHANGEMENT_PIN;


    // Methode pour avoir la description

    public String getDescription() {
        if(this == RETRAIT) {
            return "retrait d'argent";
        }else if(this == DEPOT) {
            return "Dépot d'argent";
        }else if(this == CONSULTATION) {
            return "Voir son solde";
        }else if(this == IMPRESSION){
            return "Imprimé un relevé";
        }else{
            return "Changer son code PIN";
        }
    }
}
