package src.central;
import java.time.Month;
import java.util.Date;

import src.enums.OperationType;
import src.enums.TransactionStatus;

public class Transaction {
    private String id;
    private String date;
    private OperationType type;
    private double montant;
    private TransactionStatus status;
    private String numeroCarte;

    // constructeur

    public Transaction(String numeroCarte, OperationType type, double montant) {
        this.id = "T" + System.currentTimeMillis(); // ID unique
        this.date = java.time.LocalDateTime.now().toString();
        this.numeroCarte = numeroCarte;
        this.type = type;
        this.montant = montant;
        this.status = TransactionStatus.SUCCES; // Par défaut
    }

    //afficher la transaction
    public void afficher() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("|Transaction: " + id);
        System.out.println("|Date:" + date);
        System.out.println("|Type:" + type.getDescription());
        System.out.println("|Montant:" + montant + "€");
        System.out.println("|Status:" + status.getMessage());
        System.out.println("|Carte:" + numeroCarte);
        System.out.println("└─────────────────────────────────┘");
    }

    //changer le statut
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    //getters
    public String getId() { return id; }
    public String getDate() {return date; }
    public OperationType getType() { return type; }
    public double getMontant() { return montant; }
    public TransactionStatus getStatus() { return status; }
    
}
