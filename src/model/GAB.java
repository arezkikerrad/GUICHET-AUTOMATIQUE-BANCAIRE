package src.model;

import src.security.*;
public class GAB {
    private String idGuichet;
    private double niveauBillets;
    private double niveauEncre;
    private int niveauPapier;
    private boolean cameraOk;

    // constructeur
    public GAB(String idGuichet) {
        this.idGuichet = idGuichet;
        this.niveauBillets = 100.0;
        this.niveauEncre = 100.0;
        this.niveauPapier = 100;
        this.cameraOk = true;
    }
    // afficher le menu
    public void afficherMenu() {
        System.out.println("\n=== GAB " + idGuichet + " ===");
        System.out.println("1. Retrait");
        System.out.println("2. Dépôt");
        System.out.println("3. Consultation solde");
        System.out.println("4. Imprimer relevé");
        System.out.println("5. Quitter");
        System.out.print("Choix : ");
    }
    // demander le montant
    public double demanderMontant(String operation) {
        System.out.print("Montant à " + operation + " (€) : ");
        return 100; // Simulé
    }

    // verifier état du gab
    public String getEtat() {
        if (niveauBillets < 10) return "BILLETS_BAS";
        if (niveauEncre < 15) return "ENCRE_BASSE";
        if (niveauPapier < 5) return "PAPIER_BAS";
        if (!cameraOk) return "CAMERA_PANNE";
        if (niveauBillets == 0) return "HORS_SERVICE";
        return "OK";
    }

    // envoyer message d'etat
    public String envoyerMessageetat() {
        String etat = getEtat();
        String message = "GAB_" + idGuichet + "|ETAT|" + etat + "|" + System.currentTimeMillis();
        return Securite.creerMessageSecurise(message);
    }

// === 3. GESTION DES RESSOURCES ===
    
    // Utiliser des billets
    public void utiliserBillets(double montant) {
        niveauBillets -= montant / 1000.0; // 1000€ = -1%
        if (niveauBillets < 0) niveauBillets = 0;
    }
    
    // Utiliser du papier
    public void utiliserPapier() {
        niveauPapier -= 1;
        if (niveauPapier < 0) niveauPapier = 0;
    }
    
    // reponse aux clients 
    
    // afficher resultat au client
    public void afficherResultat(String operation, boolean succes, String message) {
        System.out.println("\n📟 GAB " + idGuichet);
        if (succes) {
            System.out.println("✅ " + operation + " réussi !");
            System.out.println("   " + message);
        } else {
            System.out.println("❌ " + operation + " refusé !");
            System.out.println("   " + message);
        }
    }
    
    // === getters ===
    public String getId() { return idGuichet; }
    public double getBillets() { return niveauBillets; }
    public String getEtatString() { return getEtat(); }


}
