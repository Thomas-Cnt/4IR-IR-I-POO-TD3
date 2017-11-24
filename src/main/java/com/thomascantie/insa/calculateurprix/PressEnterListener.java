package com.thomascantie.insa.calculateurprix;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PressEnterListener implements KeyListener {

	private CalculateurPrixView view;

	public PressEnterListener(CalculateurPrixView view) {
		this.view = view;
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyChar() == '\n') {
			Article article = this.view.getInputedArticle();

			if (!article.hasValidMontant())
				this.view.afficherErreur("Le montant n'est pas valide !");
			else if (!article.hasValidQuantite())
				this.view.afficherErreur("La quantité n'est pas valide !");
			else
				this.displayMontants(article);
		}

	}

	private void displayMontants(Article article) {

		this.view.afficherMontantHT(article.getMontantHT());

		this.view.afficherMontantTTC(article.getMontantTTC());
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
