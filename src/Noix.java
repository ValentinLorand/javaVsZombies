
public class Noix extends Plante {

	private static Timer indispo;

	
	public Noix(double x, double y) {
		super(1500, 50, x, y);
		indispo = new Timer(20000);
	}
	
	@Override
	public void step() {
		
	}
	
	public Entite stepAjoute() {
		return null;
	}
	
	public boolean stepSupprime() {
		if(this.getPv() <= 0) return true;
		else return false;
	}
	
	public static void resetTimer() {
		indispo.restart();
	}
	
	public static void setIndispo(int indispo) {
		Noix.indispo = new Timer(indispo);
	}
	public static boolean estAchetable () {
		return (Argent.getMonnaie()>=50 && isDispo());
	}
	
	public static boolean isDispo() {
		if(indispo == null) return true;
		else return indispo.hasFinished();
	}
	
	@Override
	public void dessine() {
		if(this.getPv()>1100) StdDraw.picture(this.getX(), this.getY(), "gifs/noix.gif",0.12,0.12);
		else if(this.getPv()>700) StdDraw.picture(this.getX(), this.getY(), "gifs/noixMort1.gif",0.12,0.12);
		else if(this.getPv()>300) StdDraw.picture(this.getX(), this.getY(), "gifs/noixMort2.gif",0.12,0.12);
		else StdDraw.picture(this.getX(), this.getY(), "gifs/noixMort3.gif",0.12,0.12);
	}
}
