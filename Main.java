import src.enums.OperationType;
import src.enums.TransactionStatus;
import src.central.CompteBancaire;
import src.central.Transaction;
import src.client.Client;
public class Main {
    public static void main(String[] args ){
        Client client = new Client("Kerrad", "Arezki", "1234-5678-9012-3456", "1248");
        CompteBancaire compte = new CompteBancaire(client.getNumerocarte(), 0, client);

        Transaction tr = new Transaction(client.getNumerocarte(), OperationType.RETRAIT, 2000);
        Transaction tr2 = new Transaction(client.getNumerocarte(), OperationType.DEPOT, 1000);
        
        tr.setStatus(compte.retirer(2000));
        compte.ajouterTransaction(tr);
        compte.ajouterTransaction(tr2);

        compte.afficherHistorique();
    }

}
    
