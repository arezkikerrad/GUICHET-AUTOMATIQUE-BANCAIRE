import src.enums.OperationType;
import src.enums.TransactionStatus;
import src.central.CompteBancaire;
import src.central.Transaction;
import src.client.Client;
public class Main {
    public static void main(String[] args ){
        Client client = new Client("Kerrad", "Arezki", "1234-5678-9012-3456", "1248");

       CompteBancaire compte = new CompteBancaire("12345", 0, client);
       Transaction tr = new Transaction(OperationType.DEPOT, 1000, TransactionStatus.SUCCES, "1234");

       tr.afficher();
       System.out.println(compte.getProprietaire().getNumerocarte());
    }
}
    
