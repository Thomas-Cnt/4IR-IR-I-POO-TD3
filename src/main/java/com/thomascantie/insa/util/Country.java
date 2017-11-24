package com.thomascantie.insa.util;

public enum Country {
    BELGIQUE("Belgique"),
    DANEMARK("Danemark"),
    ALLEMAGNE("Allemagne"),
    GRECE("Grèce"),
    ESPAGNE("Espagne"),
    FRANCE("France"),
    IRLANDE("Irlande"),
    ITALIE("Italie"),
    CHYPRE("Chypre"),
    LUXEMBOURG("Luxembourg"),
    PAYS_BAS("Pays-Bas"),
    AUTRICHE("Autriche"),
    PORTUGAL("Portugal"),
    SUEDE("Suède");

    private String text;

    Country(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
