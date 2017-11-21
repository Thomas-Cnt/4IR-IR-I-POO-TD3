package com.thomascantie.insa.calculateurprix;

import com.thomascantie.insa.util.SpringUtilities;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;

import javax.swing.*;

public class CalculateurPrixView extends JFrame {

    private final CalculateurPrixPresenter presenter;
    private JFormattedTextField fieldMontantHT;

    public CalculateurPrixView() throws HeadlessException {
        super("Calculateur de prix");

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println(CalculateurPrixView.this.getSize());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        this.presenter = new CalculateurPrixPresenter(this);


        JPanel pLeft = new JPanel(new SpringLayout());

        JLabel labelPrixArticle = new JLabel("Prix d'un article (€) : ", JLabel.TRAILING);
        pLeft.add(labelPrixArticle);
        JTextField fieldPrixArticle = new JTextField(10);
        fieldPrixArticle.setToolTipText("Entrez ici le montant d'un article");
        labelPrixArticle.setLabelFor(fieldPrixArticle);
        pLeft.add(fieldPrixArticle);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        JLabel labelQuantite = new JLabel("Quantité : ", JLabel.TRAILING);
        pLeft.add(labelQuantite);
        JTextField fieldQuantite = new JTextField(10);
        fieldQuantite.setToolTipText("Entrez ici la quantité de l'article");
        labelQuantite.setLabelFor(fieldQuantite);
        pLeft.add(fieldQuantite);

        pLeft.add(Box.createVerticalGlue());
        pLeft.add(Box.createVerticalGlue());

        JLabel labelMontantHT = new JLabel("Montant HT : ", JLabel.TRAILING);
        pLeft.add(labelMontantHT);
        this.fieldMontantHT = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        fieldMontantHT.setValue(0.00);
        fieldMontantHT.setEditable(false);
        labelMontantHT.setLabelFor(fieldMontantHT);
        pLeft.add(fieldMontantHT);

        SpringUtilities.makeCompactGrid(pLeft,
                5, 2,          //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


        JPanel pRight = new JPanel();
        pRight.setLayout(new BoxLayout(pRight, BoxLayout.Y_AXIS));

        pRight.add(Box.createVerticalGlue());
        pRight.add(Box.createVerticalGlue());

        JButton buttonCompute = new JButton("Calculer");
        buttonCompute.addActionListener(e -> this.presenter.onComputeButtonClicked(fieldPrixArticle.getText(), fieldQuantite.getText()));
        pRight.add(buttonCompute);

        pRight.add(Box.createVerticalGlue());
        pRight.add(Box.createVerticalGlue());

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

    public void afficherErreur(String message) {
        showMessageDialog(this, message, "Erreur", ERROR_MESSAGE);
    }

    public void display() {
        this.pack();
        this.setSize(450, 181);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
