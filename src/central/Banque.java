package src.central;

import java.util.ArrayList;
import java.util.List;

import src.enums.TransactionStatus;
import src.model.GAB;
import src.security.Securite;
public class Banque  {
    private String nomBanque;
    private List<CompteBancaire> comptes;
    private List<GAB> gabList;
    private List<String> logs;

    public Banque(String nomBanque) {
        this.nomBanque =nomBanque;
        this.comptes = new ArrayList<>();
        this.gabList = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
    // gestion des comptes

    public void ajouterCompte(CompteBancaire compte) {
        comptes.add(compte);
        //log("nouveau compte ajouté: " + compte.getNumeroCompte());
    }

    public CompteBancaire trouverCompteParCarte(String numeroCarte) {
        for(CompteBancaire compte : comptes) {
            if(compte.getProprietaire().getNumeroCarte().equals(numeroCarte)) {
                return compte;
            }
        }
        return null;
    }

    public String traiterDemande(String messageSecurise) {
        log("Réception d'une demande");

        // verifier la signature
        String message = Securite.lireMessageSecurise(messageSecurise);
        if(message == null) {
            log("Signature invalide - rejeté");
            return Securite.creerMessageSecurise("ERREUR|SIGNATURE INVALIDE");
        }

        // analyser le message
        //format : OPERATION|MONTANT|CARTE|TIMESTAMP
        String[] parties = message.split(("\\|"));
        if(parties.length < 3) {
            log("Format message invalide");
            return Securite.creerMessageSecurise("ERREUR|FORMAT INVALIDE");
        }

        // mettre nos variable
        String operation = parties[0];
        double montant = Double.parseDouble(parties[1]);
        String carte = parties[2];

        // trouver le compte
        CompteBancaire compte = trouverCompteParCarte(carte);
        if (compte == null) {
            log("Compte non trouvé: " + carte);
            return Securite.creerMessageSecurise("ERREUR|COMPTE_INCONNU");
        }
        // traiter selon loperation
        String resultat = traiterOperation(compte, operation, montant);
        log("Opération traitée: " + resultat);
        
        return Securite.creerMessageSecurise(resultat);
    }

    // traiter selon loperation 
    private String traiterOperation(CompteBancaire compte, String operation, double montant){
        switch(operation) {
            case "RETRAIT":
                TransactionStatus statusRetrait = compte.retirer(montant);
                if (statusRetrait == TransactionStatus.SUCCES) {
                    return "SUCCES|RETRAIT|" + montant + "|" + compte.consulter();
                } else {
                    return "ECHEC|RETRAIT|" + statusRetrait.getMessage();
                }
                
            case "DEPOT":
                compte.deposer(montant);
                return "SUCCES|DEPOT|" + montant + "|" + compte.consulter();
                
            case "SOLDE":
                return "SUCCES|SOLDE|" + compte.consulter();
                
            case "RELEVE":
                return "SUCCES|RELEVE|Compte: " + compte.getNumeroCompte() + "|Solde: " + compte.consulter();
                
            default:
                return "ECHEC|OPERATION_INCONNUE";
        }
    }
    // loggins
    private void log(String message) {
        String logMessage = "[" + java.time.LocalDateTime.now() + "] " + message;
        logs.add(logMessage);
        System.out.println(logMessage);
    }
    
    public void afficherLogs() {
        System.out.println("\n=== LOGS BANQUE ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }
    
    // getters
    public String getNom() { return nomBanque; }
}
