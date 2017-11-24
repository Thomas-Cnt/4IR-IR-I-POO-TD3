package com.thomascantie.insa.calculateurprix;

import com.thomascantie.insa.util.Country;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.thomascantie.insa.util.Country.*;
import static com.thomascantie.insa.util.Country.SUEDE;

public class Article {

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

	private double montant;
	private int quantite;
	private Country country;

	public Article(String montantAsText, String quantiteAsText, Country country) {
		this.montant = Double.parseDouble(montantAsText);
		this.quantite = Integer.parseInt(quantiteAsText);
		this.country = country;
	}

	public boolean hasValidMontant() {
		return Double.toString(this.montant).matches("[0-9]+(.[0-9]{1,2})?");
	}

	public boolean hasValidQuantite() {
		return Integer.toString(this.quantite).matches("[0-9]+");
	}

	public double getMontantHT() {
		return new BigDecimal(this.montant)
				.multiply(new BigDecimal(this.quantite))
				.setScale(2, BigDecimal.ROUND_HALF_EVEN)
				.doubleValue();
	}

	public double getMontantTTC() {
		BigDecimal montant = new BigDecimal(this.getMontantHT());
		return montant
				.add(montant.multiply(new BigDecimal(TAXES.get(this.country))))
				.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

}
