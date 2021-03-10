
public class Tournesol extends Plante {
	
	private Timer popSoleil;
	private static Timer indispo;
	
	public Tournesol (double x,double y) {
		super(300, 50, x,y);
		popSoleil = new Timer(24000);
		indispo = new Timer(5000);
	}

	public void step() {
		if(popSoleil.hasFinished()) {
			popSoleil.restart();
		}
	}
	
	public Entite stepAjoute() {
		if(popSoleil.hasFinished()) return new Soleil(super.getX(),super.getY());
		else return null;
	}
	
	public boolean stepSupprime() {
		if(this.getPv() <= 0) return true;
		else return false;
	}
	
	public static void resetTimer() {
		indispo.restart();
	}
	
	public static void setIndispo(int indispo) {
		Tournesol.indispo = new Timer(indispo);
	}

	public static boolean estAchetable () {
		return (Argent.getMonnaie()>=50 && isDispo());
	}
	
	public static boolean isDispo() {
		if(indispo == null) return true;
		else return indispo.hasFinished();
	}

	public void dessine() {
		if(this.getPv()>200) StdDraw.picture(this.getX(), this.getY(), "/images/tournesol.png", 0.13, 0.13);
		else if(this.getPv()>100) StdDraw.picture(this.getX(), this.getY(), "/images/tournesolMort1.png", 0.13, 0.13);
		else StdDraw.picture(this.getX(), this.getY(), "/images/tournesolMort2.png", 0.13, 0.13);
	}
}
