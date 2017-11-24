package com.thomascantie.insa.calculateurprix;

import com.sun.javafx.font.FontFactory;
import com.thomascantie.insa.util.Country;
import com.thomascantie.insa.util.SpringUtilities;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

public class CalculateurPrixView extends JFrame {

    private final CalculateurPrixPresenter presenter;
    private final PressEnterListener enterListener;
    private JTextField fieldPrixArticle;
    private JTextField fieldQuantite;
    private JFormattedTextField fieldMontantHT;
    private JFormattedTextField fieldMontantTTC;
    private Country country = Country.FRANCE;

    public CalculateurPrixView() throws HeadlessException {
        super("Calculateur de prix");

        // association de la fenêtre au controleur
        this.presenter = new CalculateurPrixPresenter(this);

        this.enterListener = new PressEnterListener(this);

        /*
         * Création des différents composants
         */
        Font fontStyle = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

        // panneau pour les champs de saisie
        JPanel pLeft = new JPanel(new SpringLayout());

        /* prix de l'article */
        JLabel labelPrixArticle = new JLabel("Prix d'un article (€) : ", JLabel.TRAILING);
        labelPrixArticle.setFont(fontStyle);
        pLeft.add(labelPrixArticle);
        this.fieldPrixArticle = new JTextField(10);
        this.fieldPrixArticle.setToolTipText("Entrez ici le montant d'un article");
        this.fieldPrixArticle.setFont(fontStyle);
        this.fieldPrixArticle.addKeyListener(this.enterListener);
        labelPrixArticle.setLabelFor(this.fieldPrixArticle);
        pLeft.add(this.fieldPrixArticle);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* quantité d'articles */
        JLabel labelQuantite = new JLabel("Quantité : ", JLabel.TRAILING);
        labelQuantite.setFont(fontStyle);
        pLeft.add(labelQuantite);
        this.fieldQuantite = new JTextField(10);
        this.fieldQuantite.setToolTipText("Entrez ici la quantité de l'article");
        this.fieldQuantite.setFont(fontStyle);
        this.fieldQuantite.addKeyListener(this.enterListener);
        labelQuantite.setLabelFor(this.fieldQuantite);
        pLeft.add(this.fieldQuantite);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* choix du pays */
        JLabel labelPays = new JLabel("Pays : ", JLabel.TRAILING);
        labelPays.setFont(fontStyle);
        pLeft.add(labelPays);
        JComboBox<Country> comboPays = new JComboBox<Country>();
        for (Country c : Country.values()) {
            comboPays.addItem(c);
            if (c.equals(Country.FRANCE))
                comboPays.setSelectedItem(c);
        }
        comboPays.addActionListener(e -> this.country = (Country) comboPays.getSelectedItem());
        comboPays.setToolTipText("Sélectionnez le pays");
        comboPays.setFont(fontStyle);
        comboPays.addKeyListener(this.enterListener);
        labelPays.setLabelFor(comboPays);
        pLeft.add(comboPays);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* montant hors taxes */
        JLabel labelMontantHT = new JLabel("Montant HT : ", JLabel.TRAILING);
        labelMontantHT.setFont(fontStyle);
        pLeft.add(labelMontantHT);
        this.fieldMontantHT = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        this.fieldMontantHT.setValue(0.00);
        this.fieldMontantHT.setFont(fontStyle);
        this.fieldMontantHT.setEditable(false);
        labelMontantHT.setLabelFor(this.fieldMontantHT);
        pLeft.add(this.fieldMontantHT);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        /* montant toutes taxes comprises */
        JLabel labelMontantTTC = new JLabel("Montant TTC : ", JLabel.TRAILING);
        labelMontantTTC.setFont(fontStyle);
        pLeft.add(labelMontantTTC);
        this.fieldMontantTTC = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        this.fieldMontantTTC.setValue(0.00);
        this.fieldMontantTTC.setFont(fontStyle);
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
        buttonCompute.setFont(fontStyle.deriveFont(Font.BOLD));
        buttonCompute.addActionListener(e -> this.presenter.onComputeButtonClicked(new Article(this.fieldPrixArticle.getText(), this.fieldQuantite.getText(), this.country)));
        buttonCompute.addKeyListener(this.enterListener);
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

    public Article getInputedArticle() {
        return new Article(this.fieldPrixArticle.getText(), this.fieldQuantite.getText(), this.country);
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
        this.setSize(560, 300);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
