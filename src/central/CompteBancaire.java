package src.central;
import src.client.*;
import src.enums.TransactionStatus;

public class CompteBancaire {
    private String numeroCompte;
    private double solde;
    private double limiteRetraitJournalier;
    private double retraitAujourdhui;
    private Client proprietaire;

    // Constructeur
    public CompteBancaire(String numeroCompte, double soldeInitial, Client proprio){
        this.numeroCompte = numeroCompte;
        this.solde = soldeInitial;
        this.limiteRetraitJournalier = 300; // par defaut
        this.retraitAujourdhui = 0;
        this.proprietaire = proprio;
    }

    // Methodes 

    // retirer de l'argent
    public TransactionStatus retirer(double montant) {
        // verifier solde suffisant
        if(montant > solde){
            return TransactionStatus.ERREUR_SOLDE_INSUFFISANT;
        }
        // verifier limite journalier
        if(montant > (limiteRetraitJournalier - retraitAujourdhui)){
            return TransactionStatus.ERREUR_LIMITE_DEPASSEE;
        }

        // sinon effectuer le retrait
        solde -= montant;
        retraitAujourdhui += montant;
        return TransactionStatus.SUCCES;

    }

    // deposer de l'argent
    public void deposer(double montant){
        solde += montant;
        System.out.println("Dépot de " + montant + "€ effectué.");
    }

    // consulter le solde
    public double consulter() {
        return solde;
    }

    // afficher les infos du compte
    public void afficherInfos() {
        System.out.println("\n=== Infos Compte ===");
        proprietaire.afficherInfos();
        System.out.println("Compte: " + numeroCompte);
        System.out.println("Solde: " + solde + "€");
        System.out.println("Limite retrait/jour: " + limiteRetraitJournalier + "€");
        System.out.println("Retrait aujourd'hui: " + retraitAujourdhui + "€");
    }

    //getters
    public Client getProprietaire() {return proprietaire; }
    public double getSolde() {return solde; }
}


