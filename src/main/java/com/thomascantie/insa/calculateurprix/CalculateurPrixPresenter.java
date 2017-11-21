package com.thomascantie.insa.calculateurprix;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.thomascantie.insa.calculateurprix.Country.*;

public class CalculateurPrixPresenter {

    private static final Map<Country, Double> TAXES;
    static {
        Map<Country, Double> map = new HashMap<Country, Double>();
        map.put(BELGIQUE,   0.21);
        map.put(DANEMARK,   0.25);
        map.put(ALLEMAGNE,  0.19);
        map.put(GRECE,      0.23);
        map.put(ESPAGNE,    0.21);
        map.put(FRANCE,     0.20);
        map.put(IRLANDE,    0.23);
        map.put(ITALIE,     0.22);
        map.put(CHYPRE,     0.19);
        map.put(LUXEMBOURG, 0.15);
        map.put(PAYS_BAS,   0.21);
        map.put(AUTRICHE,   0.20);
        map.put(PORTUGAL,   0.23);
        map.put(SUEDE,      0.25);
        TAXES = Collections.unmodifiableMap(map);
    }

    private final CalculateurPrixView calculateurPrixView;

    public CalculateurPrixPresenter(CalculateurPrixView calculateurPrixView) {
        this.calculateurPrixView = calculateurPrixView;
    }

    public void onComputeButtonClicked(String montantArticleAsText, String quantiteArticleAsText, Country country) {
        if (!montantArticleAsText.matches("[0-9]+(.[0-9]{1,2})?"))
            this.calculateurPrixView.afficherErreur("Le montant n'est pas valide !");
        else if (!quantiteArticleAsText.matches("[0-9]+"))
            this.calculateurPrixView.afficherErreur("La quantité n'est pas valide !");
        else
            this.compute(montantArticleAsText, quantiteArticleAsText, country);
    }

    private void compute(String montantArticleAsText, String quantiteArticleAsText, Country country) {
        double montant = Float.parseFloat(montantArticleAsText);
        int quantite = Integer.parseInt(quantiteArticleAsText);

        BigDecimal res = new BigDecimal(montant);
        res = res.multiply(new BigDecimal(quantite));

        this.calculateurPrixView.afficherMontantHT(res.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());

        res = res.add(res.multiply(new BigDecimal(TAXES.get(country))));

        this.calculateurPrixView.afficherMontantTTC(res.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
    }
}
