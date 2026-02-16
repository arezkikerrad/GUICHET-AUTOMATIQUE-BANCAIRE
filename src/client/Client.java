package src.client;

public class Client {
    private String nom;
    private String prenom;
    private String numeroCarte;
    private String codePin;

    // Constructeur

    public Client(String nom, String prenom, String numeroCarte, String codePin) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroCarte = numeroCarte;
        this.codePin = codePin;
    }

    // Methodes
    // verifier le pin
    public boolean verifierPin(String pinSaisi) {
        return this.codePin.equals(pinSaisi);
    }

    // afficher information
    public void afficherInfos() {
        System.out.println("Client : " + prenom + " " + nom);
        System.out.println("Carte : " + numeroCarte);

    }

    // Getters pour acceder aux données
    public String getNom() {return nom; }
    public String getPrenom() {return prenom; }
    public String getNumeroCarte() {return numeroCarte; }
}
