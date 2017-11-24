package com.thomascantie.insa.calculateurprix;

public class CalculateurPrixPresenter {

    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(Article article) {
        if (!article.hasValidMontant())
            this.calculateurPrixView.afficherErreur("Le montant n'est pas valide !");
        else if (!article.hasValidQuantite())
            this.calculateurPrixView.afficherErreur("La quantité n'est pas valide !");
        else
            this.displayMontants(article);
    }

    private void displayMontants(Article article) {

        this.calculateurPrixView.afficherMontantHT(article.getMontantHT());

        this.calculateurPrixView.afficherMontantTTC(article.getMontantTTC());
    }

}
