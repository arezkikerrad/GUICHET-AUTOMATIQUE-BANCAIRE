package src.central;
import src.client.*;
import src.enums.TransactionStatus;
import java.util.List;
import java.util.ArrayList;

public class CompteBancaire {
    private String numeroCompte;
    private double solde;
    private double limiteRetraitJournalier;
    private double retraitAujourdhui;
    private Client proprietaire;

    //historique des transactions
    private List<Transaction> historique;

    // Constructeur
    public CompteBancaire(String numeroCompte, double soldeInitial, Client proprio){
        this.numeroCompte = numeroCompte;
        this.solde = soldeInitial;
        this.limiteRetraitJournalier = 300; // par defaut
        this.retraitAujourdhui = 0;
        this.proprietaire = proprio;
        //initialiser l'historique 
        this.historique = new ArrayList<>();
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

    // ajouter une transaction à l'historique
    public void ajouterTransaction(Transaction transaction) {
        historique.add(transaction);
    }

    //afficher l'historique
    public void afficherHistorique() {
        System.out.println("\n=== HISTORIQUE DES TRANSACTIONS ===");
        if(historique.isEmpty()) {
            System.out.println("Aucune transaction.");
        } else {
            for(Transaction t : historique) {
                t.afficher();
            }
        }
        System.out.println("Total transactions: " + historique.size());
    }
    //getters
    public Client getProprietaire() {return proprietaire; }
    public double getSolde() {return solde; }
}


