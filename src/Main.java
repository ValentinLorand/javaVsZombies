
public class Main {
	
	public static void main(String[] args) {
		
		GameWorld world;
		int num_manche =-1;
		// reglage de la taille de la fenetre de jeu, en pixels (nb: un écran fullHD = 1980x1050 pixels)
		StdDraw.setCanvasSize(600, 600);
		boolean debut = false;
		// permet le double buffering, pour permettre l'animation
		StdDraw.enableDoubleBuffering();
		
		// Affichage de l'ecran d'accueil au lancement du programme.
		StdDraw.picture(0.5, 0.5, "/images/EcranAccueil.png",1,1);
		StdDraw.show();
		
		//Gestion de l'interface post gameWorld
		while(!debut) {
			if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.150 && StdDraw.mouseX()<0.460 && StdDraw.mouseY()>0.180 && StdDraw.mouseY()<0.275) {
				StdDraw.clear();
				StdDraw.picture(0.5, 0.5, "/images/Plantes&Zombies.png", 1, 1);
				StdDraw.show();
				char key = '\0';
				while(key != ' ') {
					if (StdDraw.hasNextKeyTyped()) key = StdDraw.nextKeyTyped();
					presentationPZ();
					StdDraw.show();
				}
				debut = true;
			}
			else if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.580 && StdDraw.mouseX()<0.890 && StdDraw.mouseY()>0.180 && StdDraw.mouseY()<0.275) {
				StdDraw.clear();
				StdDraw.pause(50);
				StdDraw.picture(0.5, 0.5, "/images/choixNiveau.png", 1, 1);
				StdDraw.show();
				while(choixNiveau() == -1) {
					if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.830 && StdDraw.mouseY()>0.840 && StdDraw.mouseY()<0.905) {
						StdDraw.clear();
						StdDraw.picture(0.5, 0.5, "/images/Plantes.png", 1, 1);		
						StdDraw.show();
						boolean retour = false;
						while(!retour) {
							presentationPlantes();
							StdDraw.show();
							if(StdDraw.isMousePressed() && StdDraw.mouseX()<0.150 && StdDraw.mouseY()>0.905 && StdDraw.mouseY()<0.970) retour = true;
						}
						StdDraw.clear();
						StdDraw.picture(0.5, 0.5, "/images/choixNiveau.png", 1, 1);
						StdDraw.show();
					}
					else if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.830 && StdDraw.mouseY()>0.760 && StdDraw.mouseY()<0.825) {
						StdDraw.clear();
						StdDraw.picture(0.5, 0.5, "/images/Zombies.png", 1, 1);
						StdDraw.show();
						boolean retour = false;
						while(!retour) {
							presentationZombies();
							StdDraw.show();
							if(StdDraw.isMousePressed() && StdDraw.mouseX()<0.150 && StdDraw.mouseY()>0.905 && StdDraw.mouseY()<0.970) retour = true;
						}
						StdDraw.clear();
						StdDraw.picture(0.5, 0.5, "/images/choixNiveau.png", 1, 1);
						StdDraw.show();
					}
				}
				num_manche = choixNiveau();
				debut = true;
			}
		}
		
		//Creation du monde en mode aventure ou non
		if(num_manche != -1) {
			world = new GameWorld(num_manche);
		}
		else world = new GameWorld();
		
		
		// la boucle principale de notre jeu
		while (!GameWorld.gameLost() && !GameWorld.gameWon()) {
			// Efface la fenetre graphique
			StdDraw.clear();
			
			
			// Capture et traite les eventuelles interactions clavier du joueur
			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				world.processUserInput(key);
			}
			
			// Capture et traite les eventuelles interactions souris du joueur
			if (StdDraw.isMousePressed()) {
				world.processMouseClick(StdDraw.mouseX(), StdDraw.mouseY());
			}
			
			// fait agir toutes les entites
			world.step();
			
			// dessine le monde
			world.dessine();
			
			// Montre la fenetre graphique mise a jour et attends 20 millisecondes
			StdDraw.show();
			StdDraw.pause(20);
			
		}
		
		//La partie est gagnee, arret du programme.
		if (GameWorld.gameWon()) {
			System.out.println("Game won !");
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, "/images/victoire.png", 1, 1);
			StdDraw.show();
		}
		
		//La partie est perdue, arret du programme.
		if (GameWorld.gameLost()) {
			System.out.println("Game lost...");
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, "/images/defaite.png", 1, 1);
			StdDraw.show();
		}
		
	}
	
	/**
	 * @return le niveau choisi par l'utilisateur.
	 */
	private static int choixNiveau() {
		if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.120 && StdDraw.mouseX()<0.460) {
			if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.690 && StdDraw.mouseY()<0.790) return 1;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.550 && StdDraw.mouseY()<0.650) return 3;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.415 && StdDraw.mouseY()<0.515) return 5;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.275 && StdDraw.mouseY()<0.375) return 7;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.135 && StdDraw.mouseY()<0.235) return 9;
			else return -1;
		}
		else if(StdDraw.isMousePressed() && StdDraw.mouseX()>0.550 && StdDraw.mouseX()<0.880) {
			if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.610 && StdDraw.mouseY()<0.710) return 2;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.470 && StdDraw.mouseY()<0.570) return 4;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.335 && StdDraw.mouseY()<0.435) return 6;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.195 && StdDraw.mouseY()<0.295) return 8;
			else if(StdDraw.isMousePressed() && StdDraw.mouseY()>0.060 && StdDraw.mouseY()<0.160) return 10;
			else return -1;
		}
		else return -1;
	}
	
	/**
	 * Gestion de la presentation des plantes.
	 */
	private static void presentationPlantes() {
		if(StdDraw.mouseX()>0.160 && StdDraw.mouseX()<0.400 &&StdDraw.mouseY()>0.470 && StdDraw.mouseY()<0.830) 
			StdDraw.picture(0.276, 0.652, "/images/descriptionT.png", 0.244, 0.356);
		else if(StdDraw.mouseX()>0.595 && StdDraw.mouseX()<0.845 &&StdDraw.mouseY()>0.470 && StdDraw.mouseY()<0.830)
			StdDraw.picture(0.716, 0.654, "/images/descriptionP.png", 0.244, 0.356);
		else if(StdDraw.mouseX()>0.160 && StdDraw.mouseX()<0.400 &&StdDraw.mouseY()>0.040 && StdDraw.mouseY()<0.390)
			StdDraw.picture(0.276, 0.217, "/images/descriptionN.png", 0.244, 0.356);
		else if(StdDraw.mouseX()>0.595 && StdDraw.mouseX()<0.845 &&StdDraw.mouseY()>0.040 && StdDraw.mouseY()<0.390)
			StdDraw.picture(0.716, 0.217, "/images/descriptionC.png", 0.244, 0.356);
		else {StdDraw.clear(); StdDraw.picture(0.5, 0.5, "/images/Plantes.png", 1, 1); StdDraw.show();}
	}
	
	/**
	 * Gestion de la presentation des zombies.
	 */
	private static void presentationZombies() {
		if(StdDraw.mouseX()>0.045 && StdDraw.mouseX()<0.245 &&StdDraw.mouseY()>0.265 && StdDraw.mouseY()<0.695) 
			StdDraw.picture(0.145, 0.415, "/images/descriptionDB.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.360 && StdDraw.mouseX()<0.560 &&StdDraw.mouseY()>0.265 && StdDraw.mouseY()<0.710)
			StdDraw.picture(0.460, 0.415, "/images/descriptionB.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.580 && StdDraw.mouseX()<0.980 &&StdDraw.mouseY()>0.265 && StdDraw.mouseY()<0.710)
			StdDraw.picture(0.780, 0.415, "/images/descriptionR.png", 0.2, 0.3);
		else {StdDraw.clear(); StdDraw.picture(0.5, 0.5, "/images/Zombies.png", 1, 1); StdDraw.show();}
	}
	
	/**
	 * Gestion de la presentation des plantes et des zombies.
	 */
	private static void presentationPZ() {
		if(StdDraw.mouseX()>0.055 && StdDraw.mouseX()<0.185 &&StdDraw.mouseY()>0.460 && StdDraw.mouseY()<0.730) 
			StdDraw.picture(0.120, 0.595, "/images/descriptionT.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.245 && StdDraw.mouseX()<0.445 &&StdDraw.mouseY()>0.460 && StdDraw.mouseY()<0.740)
			StdDraw.picture(0.345, 0.600, "/images/descriptionP.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.485 && StdDraw.mouseX()<0.685 &&StdDraw.mouseY()>0.460 && StdDraw.mouseY()<0.740)
			StdDraw.picture(0.585, 0.600, "/images/descriptionN.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.745 && StdDraw.mouseX()<0.945 &&StdDraw.mouseY()>0.460 && StdDraw.mouseY()<0.740)
			StdDraw.picture(0.845, 0.600, "/images/descriptionC.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.130 && StdDraw.mouseX()<0.270 &&StdDraw.mouseY()>0.060 && StdDraw.mouseY()<0.330)
			StdDraw.picture(0.200, 0.215, "/images/descriptionDB.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.370 && StdDraw.mouseX()<0.570 &&StdDraw.mouseY()>0.030 && StdDraw.mouseY()<0.330)
			StdDraw.picture(0.470, 0.180, "/images/descriptionB.png", 0.2, 0.3);
		else if(StdDraw.mouseX()>0.600 && StdDraw.mouseX()<0.920 &&StdDraw.mouseY()>0.030 && StdDraw.mouseY()<0.330)
			StdDraw.picture(0.760, 0.180, "/images/descriptionR.png", 0.2, 0.3);
		else {StdDraw.clear(); StdDraw.picture(0.5, 0.5, "/images/Plantes&Zombies.png", 1, 1); StdDraw.show();}
	}

}
