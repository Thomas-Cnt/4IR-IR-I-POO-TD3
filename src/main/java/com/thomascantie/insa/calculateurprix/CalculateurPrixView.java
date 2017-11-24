package com.thomascantie.insa.calculateurprix;

import com.thomascantie.insa.util.Country;
import com.thomascantie.insa.util.SpringUtilities;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

public class CalculateurPrixView extends JFrame {

    private final CalculateurPrixPresenter presenter;
    private JFormattedTextField fieldMontantHT;
    private JFormattedTextField fieldMontantTTC;
    private Country country = Country.FRANCE;

    public CalculateurPrixView() throws HeadlessException {
        super("Calculateur de prix");

        // association de la fenêtre au controleur
        this.presenter = new CalculateurPrixPresenter(this);


        /*
         * Création des différents composants
         */

        // panneau pour les champs de saisie
        JPanel pLeft = new JPanel(new SpringLayout());

        /* prix de l'article */
        JLabel labelPrixArticle = new JLabel("Prix d'un article (€) : ", JLabel.TRAILING);
        pLeft.add(labelPrixArticle);
        JTextField fieldPrixArticle = new JTextField(10);
        fieldPrixArticle.setToolTipText("Entrez ici le montant d'un article");
        labelPrixArticle.setLabelFor(fieldPrixArticle);
        pLeft.add(fieldPrixArticle);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* quantité d'articles */
        JLabel labelQuantite = new JLabel("Quantité : ", JLabel.TRAILING);
        pLeft.add(labelQuantite);
        JTextField fieldQuantite = new JTextField(10);
        fieldQuantite.setToolTipText("Entrez ici la quantité de l'article");
        labelQuantite.setLabelFor(fieldQuantite);
        pLeft.add(fieldQuantite);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* choix du pays */
        JLabel labelPays = new JLabel("Pays : ", JLabel.TRAILING);
        pLeft.add(labelPays);
        JComboBox<Country> comboPays = new JComboBox<Country>();
        for (Country c : Country.values()) {
            comboPays.addItem(c);
            if (c.equals(Country.FRANCE))
                comboPays.setSelectedItem(c);
        }
        comboPays.addActionListener(e -> this.country = (Country) comboPays.getSelectedItem());
        comboPays.setToolTipText("Sélectionnez le pays");
        labelPays.setLabelFor(comboPays);
        pLeft.add(comboPays);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* montant hors taxes */
        JLabel labelMontantHT = new JLabel("Montant HT : ", JLabel.TRAILING);
        pLeft.add(labelMontantHT);
        this.fieldMontantHT = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        this.fieldMontantHT.setValue(0.00);
        this.fieldMontantHT.setEditable(false);
        labelMontantHT.setLabelFor(this.fieldMontantHT);
        pLeft.add(this.fieldMontantHT);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* montant toutes taxes comprises */
        JLabel labelMontantTTC = new JLabel("Montant TTC : ", JLabel.TRAILING);
        pLeft.add(labelMontantTTC);
        this.fieldMontantTTC = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        this.fieldMontantTTC.setValue(0.00);
        this.fieldMontantTTC.setEditable(false);
        labelMontantTTC.setLabelFor(this.fieldMontantTTC);
        pLeft.add(this.fieldMontantTTC);

        SpringUtilities.makeCompactGrid(
                pLeft,      // component
                9, 2,       // rows, cols
                6, 6,       // initX, initY
                6, 6);      // xPad, yPad

        
        JPanel pRight = new JPanel();
        pRight.setLayout(new BoxLayout(pRight, BoxLayout.X_AXIS));

        pRight.add(Box.createRigidArea(new Dimension(20, 200)));

        pRight.add(new JSeparator(SwingConstants.VERTICAL));

        /* calcul des montants */
        JButton buttonCompute = new JButton("Calculer");
        buttonCompute.addActionListener(e -> this.presenter.onComputeButtonClicked(new Article(fieldPrixArticle.getText(), fieldQuantite.getText(), this.country)));
        pRight.add(buttonCompute);

        /*
         * Ajout des composants à la fenêtre
         */

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS ));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.setContentPane(contentPane);

        contentPane.add(pLeft);
        contentPane.add(pRight);

        fieldPrixArticle.requestFocus();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void afficherMontantHT(double montant) {
        this.fieldMontantHT.setText(NumberFormat.getCurrencyInstance().format(montant));
    }

    public void afficherMontantTTC(double montant) {
        this.fieldMontantTTC.setText(NumberFormat.getCurrencyInstance().format(montant));
    }

    public void afficherErreur(String message) {
        showMessageDialog(this, message, "Erreur", ERROR_MESSAGE);
    }

    public void display() {
        this.pack();
        this.setSize(460, 300);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
