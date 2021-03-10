
public class Argent {
	
	//Nombre de soleils
	private static int monnaie;
	
	public Argent() {
		monnaie = 50;
	}
	
	/**
	 * Ajoute 25 soleil à la reserve de soleil
	 */
	public static void ajouterMonnaie() {
		monnaie += 25;
	}
	
	/**
	 * Retire des soleils de la reserve.
	 * @param montant, le montant à prelever de la reserve.
	 */
	public static void enleverMonnaie(int montant) {
		monnaie -= montant;
	}

	/**
	 * @return le montant de la réserve.
	 */
	public static int getMonnaie() {
		return monnaie;
	}
	
	/**
	 * Remplace le montant de la reserve par autre montant
	 * @param reserve, nouveau solde de la reserve
	 */
	public static void setMonnaie(int reserve) {
		monnaie = reserve;
	}
	
	public String toString() {
		return Integer.toString(monnaie);
	}
	
}
