import javax.swing.*;

import src.central.Banque;
import src.central.CompteBancaire;
import src.client.Client;
import src.model.GAB;
import src.security.Securite;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GABInterface extends JFrame {
    
    // Composants
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Écrans
    private JPanel accueilPanel;
    private JPanel pinPanel;
    private JPanel menuPanel;
    private JPanel operationPanel;
    private JPanel resultatPanel;
    
    // Données
    private Banque banque;
    private GAB gab;
    private Client clientCourant;
    private CompteBancaire compteCourant;
    
    // Labels
    private JLabel bienvenueLabel;
    private JTextArea resultatArea;
    
    public GABInterface() {
        // Configuration fenêtre
        setTitle("GAB - Guichet Automatique Bancaire");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialiser données
        banque = new Banque("BNP Paribas");
        gab = new GAB("GAB001");
        banque.ajouterGAB(gab);
        
        // Ajouter clients test
        Client client1 = new Client("Dupont", "Jean", "1111-2222-3333-4444", "1234");
        CompteBancaire compte1 = new CompteBancaire("FR76 1234 5678", 1000.0, client1);
        banque.ajouterCompte(compte1);
        
        Client client2 = new Client("Martin", "Marie", "5555-6666-7777-8888", "2580");
        CompteBancaire compte2 = new CompteBancaire("FR76 8765 4321", 500.0, client2);
        banque.ajouterCompte(compte2);
        
        // Créer interface
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        creerEcranAccueil();
        creerEcranPIN();
        creerEcranMenu();
        creerEcranOperation();
        creerEcranResultat();
        
        add(mainPanel);
        cardLayout.show(mainPanel, "accueil");
        
        setVisible(true);
    }
    
    private void creerEcranAccueil() {
        accueilPanel = new JPanel(new GridBagLayout());
        accueilPanel.setBackground(Color.WHITE);
        
        JLabel titre = new JLabel("🏦 GAB BANCAIRE");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        
        JLabel instruction = new JLabel("Entrez votre numéro de carte :");
        instruction.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JTextField carteField = new JTextField(20);
        carteField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton continuerBtn = new JButton("Continuer");
        continuerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        continuerBtn.setBackground(new Color(0, 100, 200));
        continuerBtn.setForeground(Color.WHITE);
        
        continuerBtn.addActionListener(e -> {
            String carte = carteField.getText().trim();
            if (carte.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un numéro de carte");
                return;
            }
            
            compteCourant = banque.trouverCompteParCarte(carte);
            if (compteCourant != null) {
                clientCourant = compteCourant.getProprietaire();
                carteField.setText("");
                cardLayout.show(mainPanel, "pin");
            } else {
                JOptionPane.showMessageDialog(this, "Carte inconnue !");
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridy = 0;
        accueilPanel.add(titre, gbc);
        gbc.gridy = 1;
        accueilPanel.add(instruction, gbc);
        gbc.gridy = 2;
        accueilPanel.add(carteField, gbc);
        gbc.gridy = 3;
        accueilPanel.add(continuerBtn, gbc);
        
        mainPanel.add(accueilPanel, "accueil");
    }
    
    private void creerEcranPIN() {
        pinPanel = new JPanel(new GridBagLayout());
        pinPanel.setBackground(Color.WHITE);
        
        JLabel titre = new JLabel("🔐 Code PIN");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        
        JLabel instruction = new JLabel("Entrez votre code secret :");
        instruction.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JPasswordField pinField = new JPasswordField(10);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton validerBtn = new JButton("Valider");
        validerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        validerBtn.setBackground(new Color(0, 100, 200));
        validerBtn.setForeground(Color.WHITE);
        
        JButton annulerBtn = new JButton("Annuler");
        annulerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        validerBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            
            String pin = new String(pinField.getPassword());
            if (clientCourant.verifierPin(pin)) {
                bienvenueLabel.setText("Bienvenue " + clientCourant.getPrenom() + " " + clientCourant.getNom());
                pinField.setText("");
                cardLayout.show(mainPanel, "menu");
            } else {
                JOptionPane.showMessageDialog(this, "Code PIN incorrect !");
                pinField.setText("");
            }
        });
        
        annulerBtn.addActionListener(e -> {
            pinField.setText("");
            cardLayout.show(mainPanel, "accueil");
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridy = 0;
        pinPanel.add(titre, gbc);
        gbc.gridy = 1;
        pinPanel.add(instruction, gbc);
        gbc.gridy = 2;
        pinPanel.add(pinField, gbc);
        gbc.gridy = 3;
        
        JPanel boutons = new JPanel(new FlowLayout());
        boutons.add(validerBtn);
        boutons.add(annulerBtn);
        pinPanel.add(boutons, gbc);
        
        mainPanel.add(pinPanel, "pin");
    }
    
    private void creerEcranMenu() {
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);
        
        JLabel titre = new JLabel("🏧 Menu Principal");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        
        bienvenueLabel = new JLabel("Bienvenue");
        bienvenueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Boutons
        JButton retraitBtn = new JButton("💰 Retrait");
        JButton depotBtn = new JButton("📥 Dépôt");
        JButton soldeBtn = new JButton("📊 Consultation solde");
        JButton releveBtn = new JButton("🖨️ Imprimer relevé");
        JButton quitterBtn = new JButton("🚪 Quitter");
        
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        retraitBtn.setFont(btnFont);
        depotBtn.setFont(btnFont);
        soldeBtn.setFont(btnFont);
        releveBtn.setFont(btnFont);
        quitterBtn.setFont(btnFont);
        
        // Actions
        retraitBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            cardLayout.show(mainPanel, "operation");
        });
        
        depotBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            cardLayout.show(mainPanel, "operation");
        });
        
        soldeBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            
            String demande = "SOLDE|0|" + clientCourant.getNumeroCarte() + "|" + System.currentTimeMillis();
            String demandeSec = Securite.creerMessageSecurise(demande);
            String reponse = banque.traiterDemande(demandeSec);
            String reponseClaire = Securite.lireMessageSecurise(reponse);
            
            resultatArea.setText("=== CONSULTATION SOLDE ===\n\n" + reponseClaire);
            cardLayout.show(mainPanel, "resultat");
        });
        
        releveBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            
            String demande = "RELEVE|0|" + clientCourant.getNumeroCarte() + "|" + System.currentTimeMillis();
            String demandeSec = Securite.creerMessageSecurise(demande);
            String reponse = banque.traiterDemande(demandeSec);
            String reponseClaire = Securite.lireMessageSecurise(reponse);
            
            resultatArea.setText("=== IMPRESSION RELEVÉ ===\n\n" + reponseClaire);
            cardLayout.show(mainPanel, "resultat");
        });
        
        quitterBtn.addActionListener(e -> {
            clientCourant = null;
            compteCourant = null;
            bienvenueLabel.setText("Bienvenue");
            cardLayout.show(mainPanel, "accueil");
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridy = 0;
        menuPanel.add(titre, gbc);
        gbc.gridy = 1;
        menuPanel.add(bienvenueLabel, gbc);
        gbc.gridy = 2;
        menuPanel.add(retraitBtn, gbc);
        gbc.gridy = 3;
        menuPanel.add(depotBtn, gbc);
        gbc.gridy = 4;
        menuPanel.add(soldeBtn, gbc);
        gbc.gridy = 5;
        menuPanel.add(releveBtn, gbc);
        gbc.gridy = 6;
        menuPanel.add(quitterBtn, gbc);
        
        mainPanel.add(menuPanel, "menu");
    }
    
    private void creerEcranOperation() {
        operationPanel = new JPanel(new GridBagLayout());
        operationPanel.setBackground(Color.WHITE);
        
        JLabel titre = new JLabel("💵 Montant");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        
        JLabel instruction = new JLabel("Entrez le montant (€) :");
        instruction.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JTextField montantField = new JTextField(10);
        montantField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton validerBtn = new JButton("Valider");
        validerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        validerBtn.setBackground(new Color(0, 100, 200));
        validerBtn.setForeground(Color.WHITE);
        
        JButton annulerBtn = new JButton("Annuler");
        annulerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        validerBtn.addActionListener(e -> {
            if (clientCourant == null) {
                cardLayout.show(mainPanel, "accueil");
                return;
            }
            
            try {
                double montant = Double.parseDouble(montantField.getText().trim());
                if (montant <= 0) {
                    JOptionPane.showMessageDialog(this, "Montant invalide");
                    return;
                }
                
                String demande = "RETRAIT|" + montant + "|" + clientCourant.getNumeroCarte() + "|" + System.currentTimeMillis();
                String demandeSec = Securite.creerMessageSecurise(demande);
                String reponse = banque.traiterDemande(demandeSec);
                String reponseClaire = Securite.lireMessageSecurise(reponse);
                
                resultatArea.setText("=== RÉSULTAT OPÉRATION ===\n\n" + 
                                    "Montant: " + montant + "€\n" +
                                    "Résultat: " + reponseClaire);
                
                montantField.setText("");
                cardLayout.show(mainPanel, "resultat");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Montant invalide !");
            }
        });
        
        annulerBtn.addActionListener(e -> {
            montantField.setText("");
            cardLayout.show(mainPanel, "menu");
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridy = 0;
        operationPanel.add(titre, gbc);
        gbc.gridy = 1;
        operationPanel.add(instruction, gbc);
        gbc.gridy = 2;
        operationPanel.add(montantField, gbc);
        gbc.gridy = 3;
        
        JPanel boutons = new JPanel(new FlowLayout());
        boutons.add(validerBtn);
        boutons.add(annulerBtn);
        operationPanel.add(boutons, gbc);
        
        mainPanel.add(operationPanel, "operation");
    }
    
    private void creerEcranResultat() {
        resultatPanel = new JPanel(new GridBagLayout());
        resultatPanel.setBackground(Color.WHITE);
        
        JLabel titre = new JLabel("📋 Résultat");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        
        resultatArea = new JTextArea(10, 30);
        resultatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultatArea);
        
        JButton continuerBtn = new JButton("Continuer");
        continuerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        continuerBtn.setBackground(new Color(0, 100, 200));
        continuerBtn.setForeground(Color.WHITE);
        
        continuerBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "menu");
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.gridy = 0;
        resultatPanel.add(titre, gbc);
        gbc.gridy = 1;
        resultatPanel.add(scrollPane, gbc);
        gbc.gridy = 2;
        resultatPanel.add(continuerBtn, gbc);
        
        mainPanel.add(resultatPanel, "resultat");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GABInterface();
        });
    }
}