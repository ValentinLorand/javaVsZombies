import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

public class GameWorld {

	// l'ensemble des entites, pour gerer (notamment) l'affichage
	private static List<Entite> entites;
	private static Argent cagnotte;
	private static Timer randomSoleil;
	private static int zombiesTues;
	
	//Manches courante et celle à laquelle le jeux doit s'arreter ( aventure ou non).
	private static int manche;
	private static int mancheFinale;
	static Manche lvl = new Manche();
	
	//Derniere touche du clavier utilisee
	private char lastKeyUsed;

	// Pour savoir si la partie est gagnee ou pas / perdu ou pas
	private static boolean gameWon;
	private static boolean gameLost;

	// constructeur du mode aventure
	public GameWorld() {
		gameWon = false;
		gameLost = false;

		// on cree la cagnotte de soleils
		cagnotte = new Argent();

		// on cree les collections
		entites = new LinkedList<Entite>();

		randomSoleil = new Timer(6500);

		zombiesTues = 0;
		manche = 1;
		mancheFinale = 10;
		lvl.creationManche(manche);
	}

	// constructeur du mode non aventure.
	public GameWorld(int manche) {
		gameWon = false;
		gameLost = false;

		// on cree la cagnotte de soleils
		cagnotte = new Argent();

		// on cree les collections
		entites = new LinkedList<Entite>();

		randomSoleil = new Timer(6500);

		zombiesTues = 0;
		GameWorld.manche = manche;
		mancheFinale = manche + 1;
		lvl.creationManche(manche);
	}

	/**
	 * Gestion des interactions clavier avec l'utilisateur
	 * 
	 * @param key Touche pressee par l'utilisateur
	 */
	public void processUserInput(char key) {
		if (key == lastKeyUsed) {
			lastKeyUsed = '\0';
		} else
			lastKeyUsed = key;

		// Cheat code 1 -> obtenir 1000 unites de soleil
		if (key == '1')
			Argent.setMonnaie(Argent.getMonnaie() + 1000);
		
		// Cheat code 2 -> Toutes les plantes deviennent disponible
		if (key == '2') {
			Tournesol.setIndispo(-1);
			TirePois.setIndispo(-1);
			Noix.setIndispo(-1);
			BombeCerise.setIndispo(-1);
		}
	}

	/**
	 * Gestion des interactions souris avec l'utilisateur (la souris a Ã©tÃ©
	 * cliquee)
	 * 
	 * @param x position en x de la souris au moment du clic
	 * @param y position en y de la souris au moment du clic
	 */
	public void processMouseClick(double x, double y) {
		if (x >= 0.1 && y >= 0.2 && y <= 0.7) {
			switch (lastKeyUsed) {
			case 't':
				double posX1 = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
				double posY1 = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
				if (Tournesol.estAchetable() && !estOccupee(posX1, posY1)) {
					Plante tournesol = new Tournesol(posX1, posY1);
					entites.add(tournesol);
					Argent.enleverMonnaie(50);
					Tournesol.resetTimer();
				}
				lastKeyUsed = '\0';
				break;
			case 'p':
				double posX2 = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
				double posY2 = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
				if (TirePois.estAchetable() && !estOccupee(posX2, posY2)) {
					Plante tirePois = new TirePois(posX2, posY2);
					entites.add(tirePois);
					Argent.enleverMonnaie(100);
					TirePois.resetTimer();
				}
				lastKeyUsed = '\0';
				break;
			case 'n':
				double posX3 = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
				double posY3 = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
				if (Noix.estAchetable() && !estOccupee(posX3, posY3)) {
					Plante noix = new Noix(posX3, posY3);
					entites.add(noix);
					Argent.enleverMonnaie(50);
					Noix.resetTimer();
				}
				lastKeyUsed = '\0';
				break;

			case 'c':
				double posX4 = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
				double posY4 = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
				if (BombeCerise.estAchetable()) {
					Plante bombe = new BombeCerise(posX4, posY4);
					entites.add(bombe);
					Argent.enleverMonnaie(150);
					BombeCerise.resetTimer();
				}
				lastKeyUsed = '\0';
				break;

			// Cheat code 3 -> spawn zombie
			case 'b':
				double pos = Math.random() * (0.7 - 0.2) + 0.2;
				double z1 = ((double) (int) (pos * 10) / 10 + 0.05);
				Zombie debase = new DeBase(1, z1);
				entites.add(debase);
				lastKeyUsed = '\0';
				break;

			case 'z':
				double pos2 = Math.random() * (0.7 - 0.2) + 0.2;
				double z2 = ((double) (int) (pos2 * 10) / 10 + 0.05);
				Zombie blinde = new Blinde(1, z2);
				entites.add(blinde);
				lastKeyUsed = '\0';
				break;

			case 'r':
				double pos3 = Math.random() * (0.7 - 0.2) + 0.2;
				double z3 = ((double) (int) (pos3 * 10) / 10 + 0.05);
				Zombie runner = new Runner(1, z3);
				entites.add(runner);
				lastKeyUsed = '\0';
				break;

			default:
				break;
			}
		}
	}

	// Fait agir toutes les entites
	public void step() {
		List<Entite> ajout = new LinkedList<Entite>();
		List<Entite> suppr = new LinkedList<Entite>();

		for (Entite entite : GameWorld.entites) {
			// Gestion des pois
			if (entite instanceof Pois && presentZombie(entite.getX(), entite.getY()) != null) {
				suppr.add(entite);
				Pois pois = (Pois) entite;
				presentZombie(entite.getX(), entite.getY()).retirePv(pois.getDegats());
			}
			// Gestion si un TirePois doit tirer ou non
			if (entite instanceof TirePois && !zombieEnVue(entite.getX(), entite.getY())) {
				TirePois tp = (TirePois) entite;
				tp.setDoitTirer(false);
			} else if (entite instanceof TirePois && zombieEnVue(entite.getX(), entite.getY())) {
				TirePois tp = (TirePois) entite;
				tp.setDoitTirer(true);
			}
			// Gestion attaque de zombie
			if (entite instanceof Runner && presentPlante(entite.getX(), entite.getY()) == null) {
				Zombie zomb = (Runner) entite;
				if(zomb.getPv()<560) zomb.setVitesse(0);
			}
			if (entite instanceof Zombie && presentPlante(entite.getX(), entite.getY()) != null) {
				Zombie zomb = (Zombie) entite;
				zomb.setVitesse(0);
				if (zomb.doitFrapper())
					presentPlante(entite.getX(), entite.getY()).retirePv(zomb.getDegat());
			} else if (entite instanceof Zombie && ((Zombie) entite).getVitesse() == 0) {
				Zombie zomb = (Zombie) entite;
				if(zomb instanceof Runner && zomb.getPv()>560) zomb.setVitesse(0.001);
				else zomb.setVitesse(0.0005);
			}

			// Gestion explosion Bombe Cerise
			if (entite instanceof BombeCerise) {
				BombeCerise bombe = (BombeCerise) entite;
				if (bombe.getX() >= bombe.getArretX()) {
					for (Entite entite2 : entites) {
						if ((Math.abs(bombe.getArretX() - entite2.getX()) <= 0.15)
								&& (Math.abs(bombe.getY() - entite2.getY()) <= 0.15)) {
							suppr.add(entite2);
						}
					}
				}
			}
			
			//Ajout des entites a ajouter ou a supprimer du jeux.
			if (entite.stepAjoute() != null)
				ajout.add(entite.stepAjoute());
			if (entite.stepSupprime())
				suppr.add(entite);
			entite.step();
		}

		// Gestion des manches
		Entite ZombieTempo = lvl.ajoutZb(manche);
		if (ZombieTempo != null) {
			ajout.add(ZombieTempo);
		}
		// Cas ou la manche est terminee (tous les zombies tues),
		// passage a la manche suivante.
		if (lvl.mancheFinie(zombiesTues)) {
			if (manche + 1 != mancheFinale) {
				this.dessine();
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setFont(new Font("Wreckers", Font.BOLD, 30));
				StdDraw.picture(0.5, 0.45, "/images/transitionManche.png",1.1,0.65);
				StdDraw.text(0.5, 0.58, "GAGNE !!!");
				StdDraw.setFont(new Font("Wreckers", Font.ITALIC, 25));
				StdDraw.text(0.5, 0.48, "Pour passer à la manche suivante,");
				StdDraw.text(0.5, 0.40, "appuie sur une Touche !");
				StdDraw.show();
				while (!StdDraw.hasNextKeyTyped()) {
				}
			}
			// Mise en place de la prochaine manche
			manche += 1;
			lvl.creationManche(manche);
			zombiesTues = 0;
			randomSoleil.restart();
			lvl.reinitializeCompteur();
			lvl.setDelais(20000);
			Argent.setMonnaie(50);
			entites.clear();
			
			// Reset disponibilites des plantes.
			Tournesol.setIndispo(-1);
			TirePois.setIndispo(-1);
			Noix.setIndispo(-1);
			BombeCerise.setIndispo(-1);
			
		}

		// Gestion de l'apparition aleatoire d'un soleil
		if (randomSoleil.hasFinished()) {
			double posX = ((double) (int) ((Math.random() * 0.9 + 0.1) * 10) / 10 + 0.05);
			double posY = ((double) (int) ((Math.random() * 0.5 + 0.2) * 10) / 10 + 0.05);
			Soleil newSun = new Soleil(posX, 1);
			newSun.setVitesse(0.005);
			newSun.setArret(posY);
			entites.add(newSun);
			randomSoleil.restart();
		}

		// action des listes d'ajout et de suppression
		if (ajout != null) {
			for (int i = 0; i < ajout.size(); i++)
				entites.add(ajout.get(i));
		}
		if (suppr != null) {
			for (int i = 0; i < suppr.size(); i++) {
				if (suppr.get(i) instanceof Zombie) {
					zombiesTues += 1;
				}
				entites.remove(suppr.get(i));
			}
		}
	}

	/**
	 * Affichage des
	 */
	public void dessine() {

		// Arriere plan statique
		StdDraw.picture(0.5, 0.5, "/images/ArrierePlanJeu.png", 1, 1);

		// --Affichage dynamique--//
		
		// Affichage de la cagnotte.
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setFont(new Font("Wreckers", Font.BOLD, 15));
		StdDraw.text(0.475, 0.805, cagnotte.toString());
		
		// Affichage de la vague finale.
		lvl.dessineVagueFinale();

		// Affichage du nombre de zombies tues lors de la manche.
		StdDraw.setFont(new Font("Wreckers", Font.BOLD, 30));
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(0.055, 0.155, "" + zombiesTues);

		// Affichage du nombre de zombies restants pour la manche.
		int zombiesRestant = lvl.getNombreZombie() - zombiesTues;
		if (zombiesRestant < 0) {
			zombiesRestant = 0;
		}
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.text(0.105, 0.155, "" + (zombiesRestant));

		// Affichage du numéro de la manche
		StdDraw.setPenColor(StdDraw.BLACK);
		Font fontManche = new Font("Wreckers", Font.BOLD, 37);
		StdDraw.setFont(fontManche);
		StdDraw.text(0.76, 0.1, "Manche " + manche, 6.5);

		// Affichage des cartes disponible
		// (au niveau du temps de recharge, non de l'argent)
		affichageDispo();
		affichageSelection();

		// affiche les entites
		for (Entite entite : entites)
			entite.dessine();
	}

	/**
	 * Affichage des disponibilitees temporelles des plantes
	 */
	private void affichageDispo() {
		if (!Tournesol.isDispo()) {
			StdDraw.setPenColor(StdDraw.RED);
			Font font2 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font2);
			StdDraw.text(0.588, 0.79, "INDISPO");
		} else {
			StdDraw.setPenColor(StdDraw.GREEN);
			Font font3 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font3);
			StdDraw.text(0.588, 0.79, "DISPO");
		}
		if (!TirePois.isDispo()) {
			StdDraw.setPenColor(StdDraw.RED);
			Font font2 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font2);
			StdDraw.text(0.7, 0.812, "INDISPO");
		} else {
			StdDraw.setPenColor(StdDraw.GREEN);
			Font font3 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font3);
			StdDraw.text(0.7, 0.812, "DISPO");
		}
		if (!Noix.isDispo()) {
			StdDraw.setPenColor(StdDraw.RED);
			Font font2 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font2);
			StdDraw.text(0.81, 0.79, "INDISPO");
		} else {
			StdDraw.setPenColor(StdDraw.GREEN);
			Font font3 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font3);
			StdDraw.text(0.81, 0.79, "DISPO");
		}
		if (!BombeCerise.isDispo()) {
			StdDraw.setPenColor(StdDraw.RED);
			Font font2 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font2);
			StdDraw.text(0.915, 0.812, "INDISPO");
		} else {
			StdDraw.setPenColor(StdDraw.GREEN);
			Font font3 = new Font("Comic Sans MS", Font.BOLD, 13);
			StdDraw.setFont(font3);
			StdDraw.text(0.915, 0.812, "DISPO");
		}

	}
	/**
	 * Affichage de la plante selectionnee.
	 */
	private void affichageSelection() {
		switch (lastKeyUsed) {
		case 't':
			if (Tournesol.estAchetable()) {
				StdDraw.picture(0.59, 0.848, "/images/selectionCarte.png", 0.1, 0.144);
				if (StdDraw.mouseX() > 0.1 && StdDraw.mouseY() > 0.2 && StdDraw.mouseY() < 0.7) {
					double posX = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
					double posY = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
					if (!estOccupee(posX, posY)) {
						StdDraw.picture(posX, posY, "/images/tournesolEfface.png", 0.13, 0.13);
					}
				}
			}
			break;
		case 'p':
			if (TirePois.estAchetable()) {
				StdDraw.picture(0.7, 0.87, "/images/selectionCarte.png", 0.1, 0.144);
				if (StdDraw.mouseX() > 0.1 && StdDraw.mouseY() > 0.2 && StdDraw.mouseY() < 0.7) {
					double posX = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
					double posY = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
					if (!estOccupee(posX, posY)) {
						StdDraw.picture(posX, posY, "/images/tirePoisEfface.png", 0.13, 0.13);
					}
				}
			}
			break;
		case 'n':
			if (Noix.estAchetable()) {
				StdDraw.picture(0.81, 0.848, "/images/selectionCarte.png", 0.1, 0.144);
				if (StdDraw.mouseX() > 0.1 && StdDraw.mouseY() > 0.2 && StdDraw.mouseY() < 0.7) {
					double posX = ((double) (int) (StdDraw.mouseX() * 10) / 10 + 0.05);
					double posY = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
					if (!estOccupee(posX, posY)) {
						StdDraw.picture(posX, posY, "/images/noixEfface.png", 0.13, 0.13);
					}
				}
			}
			break;
		case 'c':
			if (BombeCerise.estAchetable()) {
				StdDraw.picture(0.918, 0.871, "/images/selectionCarte.png", 0.1, 0.144);
				if (StdDraw.mouseX() > 0.1 && StdDraw.mouseY() > 0.2 && StdDraw.mouseY() < 0.7) {
					double posY = ((double) (int) (StdDraw.mouseY() * 10) / 10 + 0.05);
					StdDraw.picture(StdDraw.mouseX(), posY, "/images/bombeCeriseEfface.png", 0.13, 0.13);
					if (posY > 0.6)
						StdDraw.picture(StdDraw.mouseX(), posY, "/images/zoneDeFrappeSup.png", 0.3, 0.3);
					else if (posY < 0.3)
						StdDraw.picture(StdDraw.mouseX(), posY, "/images/zoneDeFrappeInf.png", 0.3, 0.3);
					else
						StdDraw.picture(StdDraw.mouseX(), posY, "/images/zoneDeFrappe.png", 0.3, 0.3);
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @param x position en x (horizontal)
	 * @param y position en y (vertical)
	 * @return La case en (x,y) est occupee par une plante.
	 */
	private boolean estOccupee(double x, double y) {
		for (Entite entite : GameWorld.entites) {
			if (entite.getX() == x && entite.getY() == y && entite instanceof Plante) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param x position en x (horizontal)
	 * @param y position en y (vertical)
	 * @return Le zombie qui se situe a moins de 0.02 de distance de (x,y), null
	 *         s'il y en a pas.
	 */
	private Zombie presentZombie(double x, double y) {
		for (Entite entite : GameWorld.entites) {
			if ((Math.abs(entite.getX() - x) < 0.02 && Math.abs(entite.getY() - y) < 0.05)
					&& entite instanceof Zombie) {
				return (Zombie) entite;
			}
		}
		return null;
	}

	/**
	 * @param x position en x (horizontal)
	 * @param y position en y (vertical)
	 * @return Il y a un zombie sur la meme colonne que (x,y).
	 */
	private boolean zombieEnVue(double x, double y) {
		for (Entite entite : GameWorld.entites) {
			if ((entite.getX() - x > 0) && (Math.abs(entite.getY() - y) < 0.05) && entite instanceof Zombie) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param x position en x (horizontal)
	 * @param y position en y (vertical)
	 * @return la plante qui se situe a moins de 0.05 de distance de (x,y), null
	 *         s'il y en a pas.
	 */
	private Plante presentPlante(double x, double y) {
		for (Entite entite : GameWorld.entites) {
			if ((Math.abs(entite.getX() - x) < 0.05 && Math.abs(entite.getY() - y) < 0.05)
					&& entite instanceof Plante) {
				return (Plante) entite;
			}
		}
		return null;
	}

	/**
	 * @return la partie est gagnee (derniere manche remportee).
	 */
	public static boolean gameWon() {
		if (manche == mancheFinale) {
			gameWon = true;
		}
		return gameWon;
	}

	/**
	 * @return la partie est perdu (zombie arrive a droite de l'ecran).
	 */
	public static boolean gameLost() {
		for (Entite entite : entites) {
			if (entite instanceof Zombie && entite.getX() <= 0.05)
				gameLost = true;
		}
		return gameLost;
	}

}