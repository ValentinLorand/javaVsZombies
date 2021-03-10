import java.awt.Font;

public class Manche {
	private static Timer delais;
	private static int compteur;
	private static int nombreZombie;
	private static double tauxBlinde;
	private static double tauxRunner;
	private static int tpsMin;
	private static int frequence;

	public Manche() {
		delais = new Timer(20000);
		compteur = 0;
		nombreZombie = 1;
		tauxBlinde = 0;
		tauxRunner = 0;
		tpsMin = 0;
		frequence = 0;
	}

	/**
	 * Implementation des differents attributs en fonction du numero de la manche.
	 * @param manche, le numero de la manche.
	 */
	public void creationManche(int manche) {
		switch (manche) {
		case 1:
			nombreZombie = 10;
			tauxBlinde = 0;
			tauxRunner = 0;
			frequence = 4000;
			tpsMin = 14000;
			break;
		case 2:
			nombreZombie = 18;
			tauxBlinde = 0.05;
			tauxRunner = 0;
			frequence = 7000;
			tpsMin = 10000;
			break;
		case 3:
			nombreZombie = 25;
			tauxBlinde = 0.1;
			tauxRunner = 0;
			frequence = 7000;
			tpsMin = 8000;
			break;
		case 4:
			nombreZombie = 30;
			tauxBlinde = 0.15;
			tauxRunner = 0.05;
			frequence = 6000;
			tpsMin = 7000;
			break;
		case 5:
			nombreZombie = 40;
			tauxBlinde = 0.20;
			tauxRunner = 0.07;
			frequence = 6000;
			tpsMin = 6500;
			break;
		case 6:
			nombreZombie = 50;
			tauxBlinde = 0.25;
			tauxRunner = 0.1;
			frequence = 5000;
			tpsMin = 6000;
			break;
		case 7:
			nombreZombie = 57;
			tauxBlinde = 0.30;
			tauxRunner = 0.15;
			frequence = 5000;
			tpsMin = 5500;
			break;
		case 8:
			nombreZombie = 62;
			tauxBlinde = 0.35;
			tauxRunner = 0.2;
			frequence = 5000;
			tpsMin = 5000;
			break;
		case 9:
			nombreZombie = 65;
			tauxBlinde = 0.4;
			tauxRunner = 0.25;
			frequence = 4500;
			tpsMin = 4500;
			break;
		case 10:
			nombreZombie = 70;
			tauxBlinde = 0.5;
			tauxRunner = 0.3;
			frequence = 4000;
			tpsMin = 4000;
			break;
		default:
			break;
		}
	}

	/**
	 * Gestion des apparitions de zombies (plus ou moins aléatoires).
	 * 
	 * @param manche, le numero de la manche.
	 * @return un zombie si les timers correspondant sont terminés
	 */
	public Entite ajoutZb(int manche) {
		Zombie zombieTempo = null;
		// Gestion ligne d'apparition, type du zombie et frequence d'apparition.
		if (delais.hasFinished() && compteur < nombreZombie) {
			setDelais((int) (Math.random() * frequence) + tpsMin);
			compteur += 1;
			double pos = Math.random() * (0.7 - 0.2) + 0.2;
			double z1 = ((double) (int) (pos * 10) / 10 + 0.05);
			if (Math.random() < tauxRunner && compteur >= manche * 2)
				zombieTempo = new Runner(1, z1);
			else if (Math.random() < tauxBlinde && compteur >= manche)
				zombieTempo = new Blinde(1, z1);
			else
				zombieTempo = new DeBase(1, z1);
		}
		// Vague finale de la manche.
		if (((int) compteur * 1.5 >= nombreZombie)) {
			tpsMin = 0;
			frequence = 3000;
		}
		return zombieTempo;
	}

	/**
	 * @param zombiesTues, le nombre de zombies tuees durant la manche
	 * @return la manche est terminee.
	 */
	public boolean mancheFinie(int zombiesTues) {
		return zombiesTues >= nombreZombie;
	}

	/**
	 * modifie le delais d'apparition.
	 * 
	 * @param tps, le nouveau delais d'apparition des zombies
	 */
	public void setDelais(int tps) {
		delais = new Timer(tps);
	}

	/**
	 * @return le nombre de zombie dans la manche.
	 */
	public int getNombreZombie() {
		return nombreZombie;
	}

	/**
	 * initialise le comteur de zombies lancés.
	 */
	public void reinitializeCompteur() {
		compteur = 0;
	}

	public void dessineVagueFinale() {
		if (((int) compteur * 1.5 >= nombreZombie)) {
			// Affichage annonce vague finale
			StdDraw.setFont(new Font("Wreckers", Font.CENTER_BASELINE, 30));
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(0.37, 0.12, "Vague Finale");
		}
	}
}
