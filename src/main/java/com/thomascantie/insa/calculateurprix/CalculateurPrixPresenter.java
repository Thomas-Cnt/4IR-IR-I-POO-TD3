package com.thomascantie.insa.calculateurprix;

import java.math.BigDecimal;
import java.text.ParseException;

public class CalculateurPrixPresenter {
    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(String montantArticleAsText, String quantiteArticleAsText) {
        float montant = 0f;
        int quantite = 0;
        try {
            montant = Float.parseFloat(montantArticleAsText);
            quantite = Integer.parseInt(quantiteArticleAsText);
        } catch (Exception e) {
            this.calculateurPrixView.afficherErreur(e.getMessage());
        }
        BigDecimal res = new BigDecimal(montant);
        res = res.multiply(new BigDecimal(quantite));

        this.calculateurPrixView.afficherMontantHT(res.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
    }
}
