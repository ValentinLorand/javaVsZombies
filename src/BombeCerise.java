
public class BombeCerise extends Plante {
	
	private double vitesse;
	private double arret;
	private static Timer indispo;
	
	public BombeCerise(double x, double y) {
		super(1000,150,0,y);
		arret = x;
		vitesse = 0.03;
		indispo = new Timer(30000);
	}
	
	public void step() {
		if(this.getX()<arret) {
			this.setPosition(new Position(this.getX() + vitesse,this.getY()));
		}
		else vitesse = 0;
	}

	public Entite stepAjoute() {
		return null;
	}
	
	public boolean stepSupprime() {
		return (this.getX() >= arret);
	}
	
	public static boolean estAchetable () {
		return (Argent.getMonnaie()>=150 && isDispo());
	}
	
	public static void resetTimer() {
		indispo.restart();
	}
	
	public static void setIndispo(int indispo) {
		BombeCerise.indispo = new Timer(indispo);
	}
	
	public static boolean isDispo() {
		if(indispo == null) return true;
		else return indispo.hasFinished();
	}
	
	public double getArretX() {
		return arret;
	}
	
	public double getVitesse() {
		return vitesse;
	}
	
	public void dessine () {
		StdDraw.picture(this.getX(),this.getY(),"gifs/bombeCerise.gif",0.1,0.1);
	}
	
}
