import src.enums.OperationType;
import src.enums.TransactionStatus;
import src.client.Client;
public class Main {
    public static void main(String[] args ){
        Client client = new Client("Kerrad", "Arezki", "1234-5678-9012-3456", "1248");

        client.afficherInfos();

        System.out.println("Test PIN : " + client.verifierPin("1248"));

        System.out.println("Nom : " + client.getNom() + "\nPrenom : " + client.getPrenom());
    }
}
    
