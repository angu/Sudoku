package engine;

import java.util.ArrayList;
import engine.BruteForcer;

public class Tabellone {

	private Casella[][] griglia = new Casella[9][9];

	public Tabellone(String ls) {
		// Crea il tabellone a partire da una stringa di 81 numeri.
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				int index = r * 9 + c;
				char a = ls.charAt(index);
				griglia[r][c] = new Casella(Character.digit(a, 10));

			}
		}

	}

	public Tabellone(Tabellone old) // Used to duplicate tabellone.
	{
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				griglia[r][c] = new Casella(old.griglia[r][c]);

			}
		}
	}

	public void stampaTabellone() {

		String result = "";
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				result += griglia[r][c].getPossibiliValori().toString() + "\t";

			}// Fine for colonne
			result += "\n";
		}// fine for righe

		 System.out.println(result);
	}

	public Boolean semplifica() // Ritorna vero se è stata effettuata almeno una
								// modifica.
	{
		Boolean result = false;

		// Per prima cosa passo in rassegna ogni casella certa e rimuove ogni
		// valore già assunto nella colonna e nella riga corrispondente. per
		// ultimo considero il blocco
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				// Controllo se per ogni riga/colonna/riquadro c'è solo una
				// casella che può avere un determinato valore, in quel caso
				// assegno a quella cella quel valore.
				// Inizio con le righe, ottengo i valori possibili della cella e
				// li confronto con quelli della stessa riga.
				// Devo controllare che il valore non sia già singolo.

				if (!griglia[r][c].isCerta()) {
					Boolean control = true;
					ArrayList<Integer> cella = griglia[r][c]
							.getPossibiliValori();

					// Controllo se è l'unico valore possibile per una data
					// riga. Tengo fissa la riga e ciclo le colonne.
					for (int v = 0; v < cella.size(); v++) {// per ogni valore
															// possibile devo
															// controllare tutta
															// la riga
						control = true;
						// RIGHE FISSE
						for (int i = 0; i < 9; i++) {// inizio a ciclare le
														// colonne
							if (i != c) {// non sono nella mia stessa casella.
								ArrayList<Integer> controllo = griglia[r][i]
										.getPossibiliValori();
								if (controllo.contains(cella.get(v))) {// se
																		// un'altra
																		// cella
																		// ha lo
																		// stesso
																		// possibile
																		// valore,
																		// esco
																		// e
																		// imposto
																		// a
																		// falso.
									control = false;
								}// fine if
							}
						}// fine ciclo delle colonne
							// Se a questo punto il valore è true, posso
							// impostarlo
						if (control) {
							griglia[r][c].impostaNumero(cella.get(v));
							return true;
						}

						control = true;
						// COLONNE FISSE

						for (int i = 0; i < 9; i++) {// inizio a ciclare le
														// righe
							if (i != r) {// non sono nella mia stessa casella.
								ArrayList<Integer> controllo = griglia[i][c]
										.getPossibiliValori();
								if (controllo.contains(cella.get(v))) {// se
																		// un'altra
																		// cella
																		// ha lo
																		// stesso
																		// possibile
																		// valore,
																		// esco
																		// e
																		// imposto
																		// a
																		// falso.
									control = false;
								}// fine if
							}
						}// fine ciclo delle righe

						// Se a questo punto il valore è true, posso impostarlo
						if (control) {
							griglia[r][c].impostaNumero(cella.get(v));
							return true;
						}

						control = true;
						// CONTROLLO BLOCCO
						int a = r / 3; // Indice delle righe
						int b = c / 3; // Indice delle colonne

						// Devo ciclare il quadrato 3*3
						for (int rq = 0; rq < 3; rq++) {
							for (int cq = 0; cq < 3; cq++) {
								if ((a * 3) + rq != r || (b * 3) + cq != c) {// Controllo
																				// di
																				// non
																				// essere
																				// nella
																				// mia
																				// casella
									ArrayList<Integer> controllo = griglia[(a * 3)
											+ rq][(b * 3) + cq]
											.getPossibiliValori();
									if (controllo.contains(cella.get(v))) {// se
																			// un'altra
																			// cella
																			// ha
																			// lo
																			// stesso
																			// possibile
																			// valore,
																			// esco
																			// e
																			// imposto
																			// a
																			// falso.
										control = false;
									}// fine if
										// se sono qui, allora è single;
								}

							}// end quadrato colonna
						}// end quadrato riga
							// Se a questo punto il valore è true, posso
							// impostarlo
						if (control) {
							griglia[r][c].impostaNumero(cella.get(v));
							return true;
						}

					}// end ciclo possibili valori
				}// fine if certa
					// CONTROLLO SE IL VALORE E' BEN DEFINITO!
				if (griglia[r][c].isCerta()) // La casella ha un solo valore,
												// rimuovo questo valore da
												// tutte le caselle nelle righe
												// e nelle colonne vicine
				{

					int value = griglia[r][c].ottieniValoreCerto();

					for (int c2 = 0; c2 < 9; c2++) {// Rimuovo da tutte le
													// colonne
						if (c2 != c) { // Evito di cancellare la casella che sto
										// guardando.
							if (griglia[r][c2].rimuoviNumero(value)) {

								return true;
							}
						}
					}

					for (int r2 = 0; r2 < 9; r2++) {// Rimuovo da tutte le righe
						if (r2 != r) { // Evito di cancellare la casella che sto
										// guardando.
							if (griglia[r2][c].rimuoviNumero(value)) {
								return true;
							}
						}
					}

					// Per il controllo del blocco devo prima calcolare in che
					// blocco mi trovo. i blocchi sono rappresentati da una
					// coppia [a,b] con a e b da 0 a 2.
					int a = r / 3;
					int b = c / 3;
					// Posso variare di solo 3 righe e tre colonne.
					for (int rb = 0; rb < 3; rb++) {
						for (int cb = 0; cb < 3; cb++) {
							if ((((a * 3) + rb) != r || ((b * 3) + cb) != c)) {

								if (griglia[(a * 3) + rb][(b * 3) + cb]
										.rimuoviNumero(value)) { // Rimuovo il
																	// valore da
																	// tutte
																	// quelle
																	// righe/colonne
																	// del
																	// riquadro.
									return true;
								}
							}
						}
					}

				}

			} // fine colonne
		} // fine righe
		return result;
	}

	public Casella[][] getGriglia() {
		return griglia;
	}

	public void setGriglia(Casella[][] griglia) {
		this.griglia = griglia;
	}

	/*
	 * Controllo di integrità del tabellone sudoku. Viene usato per controllare
	 * che sia integro.
	 */
	public Boolean isOk() // Per ora controlla solamente che non ci siano valori
							// a 0
	{
		Boolean result = true;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				if (griglia[r][c].possibiliValoriCount() == 0) {
					return false;
				}

				//
				// if(griglia[r][c].isCerta())
				// {
				// int val=(int)griglia[r][c].getPossibiliValori().indexOf(0);
				//
				//
				// /*
				// * Riuso l'algoritmo di eliminazione dei numeri con
				// * alcune modifiche.
				// *
				// * */
				// for(int c2=0;c2<9;c2++){//Rimuovo da tutte le colonne
				// if(c2!=c){ //Evito di cancellare la casella che sto
				// guardando.
				// if(griglia[c2][r].isCerta() &&
				// griglia[c2][r].getPossibiliValori().get(0)==val){
				//
				// return false;
				// }
				// }
				// }
				//
				// for(int r2=0;r2<9;r2++){//Rimuovo da tutte le righe
				// if(r2!=r){ //Evito di cancellare la casella che sto
				// guardando.
				// if(griglia[c][r2].isCerta() &&
				// griglia[c][r2].getPossibiliValori().get(0)==val){
				//
				// return false;
				// }
				// }
				// }
				//
				// //Per il controllo del blocco devo prima calcolare in che
				// blocco mi trovo. i blocchi sono rappresentati da una coppia
				// [a,b] con a e b da 0 a 2.
				// int a=r/3;
				// int b=c/3;
				// //Posso variare di solo 3 righe e tre colonne.
				// for(int rb=0;rb<3;rb++){
				// for(int cb=0;cb<3;cb++){
				// if((((a*3)+rb)!=r || ((b*3)+cb)!=c)){
				// if(griglia[(a*3)+rb][(b*3)+cb].isCerta() &&
				// griglia[(a*3)+rb][(b*3)+cb].getPossibiliValori().get(0)==val){
				//
				// return false;
				// }
				//
				// }
				// }
				// }
				// }

			}
		}
		return result;
	}

	public Boolean isSolved() {

		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				if (griglia[r][c].possibiliValoriCount() > 1)
					return false;

			}
		}
		if (this.isOk())
			return true;
		else
			return false;

	}

	public Tabellone forzaTabelloneGrado(int grado) {

		int[] pos = this.getCasellaMinima();
		int r = pos[0];
		int c = pos[1];
		// System.out.println("Uso index: "+c+" "+r+"\nVoglio impostare "+(this.griglia[r][c].getPossibiliValori().get(grado)));
		this.griglia[r][c].impostaNumero((this.griglia[r][c]
				.getPossibiliValori().get(grado)));

		return this;
	}

	/*
	 * Ottengo le coordinate della casella meno incerta.
	 */
	public int[] getCasellaMinima() {
		int[] result = { 0, 0 };

		int minimo = 2; // condizione migliore è che la casella sia indecisa
						// solo su 2 numeri.

		while (true) {
			for (int r = 0; r < 9; r++) {
				for (int c = 0; c < 9; c++) {

					if (this.griglia[r][c].possibiliValoriCount() == minimo) {
						result[0] = r;
						result[1] = c;
						// System.out.println("Trovata casella minima: "+r+" "+c+" minimo: "+minimo);
						return result;

					}

				}
			}
			minimo++;
		}

	}

	/*
	 * Metodo che risolve il tabellone. Si compone di 2 fasi: 1)Semplifica le
	 * caselle finchè è possibile farlo (controllo riga,colonna, quadrato) 2)Se
	 * il sudoku è completo ho finito, altrimenti eseguo un bruteforce per le
	 * possibili soluzioni e mi arresto alla prima.
	 */
	public Tabellone risolviTabellone() {

		do {

		} while (this.semplifica());

		if (this.isSolved()) {

			return this;

		} else {
			// System.out.println("Problema non risolto!\nProvo con il force!\n");

			/*
			 * Eseguo il bruteforce
			 * 
			 * */
			BruteForcer brute = new BruteForcer(this);
			return brute.risolvi();
			

		}

	}

}
