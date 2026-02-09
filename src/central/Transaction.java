package src.central;
import java.time.Month;
import java.util.Date;

import src.enums.OperationType;
import src.enums.TransactionStatus;

public class Transaction {
    private String id;
    private Date date;
    private OperationType type;
    private double montant;
    private TransactionStatus status;
    private String compteSource;

    // constructeur

    public Transaction (OperationType type, double montant, 
                            TransactionStatus status, String compteSource){
        this.id = "T" + System.currentTimeMillis(); // genere id unique a base du temsp
        this.date = new Date();
        this.type = type;
        this.montant = montant;
        this.status = status;
        this.compteSource = compteSource;
    }

    //afficher la transaction
    public void afficher() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("|Transaction: " + id);
        System.out.println("|Date:" + date);
        System.out.println("|Type:" + type);
        System.out.println("|Montant:" + montant + "€");
        System.out.println("|Status:" + status.getMessage());
        System.out.println("|Compte:" + compteSource);
        System.out.println("└─────────────────────────────────┘");
    }

    //getters
    public OperationType getType() { return type; }
    public double getMontant() { return montant; }
    public TransactionStatus getStatus() { return status; }
    public Date getdate() { return date; }
}
