
public class TirePois extends Plante {
	
	private Timer freqPois;
	private static Timer indispo;
	private boolean doitTirer;
	
	public TirePois(double x, double y) {
		super(300, 100, x, y);
		freqPois = new Timer(1500);
		indispo = new Timer(5000);
		doitTirer = false;
	}
	
	@Override
	public void step() {
		if(freqPois.hasFinished()) freqPois.restart();
	}

	public Entite stepAjoute() {
		if(freqPois.hasFinished() && doitTirer) {
			return new Pois (super.getX(),super.getY());
		}
		else return null;
	}
	
	public boolean stepSupprime() {
		if(this.getPv() <= 0) return true;
		else return false;
	}
	
	public static boolean estAchetable () {
		return (Argent.getMonnaie()>=100 && isDispo());
	}
	
	public static boolean isDispo() {
		if(indispo == null) return true;
		else return indispo.hasFinished();
	}
	
	public static void resetTimer() {
		indispo.restart();
	}
	
	public static void setIndispo(int indispo) {
		TirePois.indispo = new Timer(indispo);
	}

	public void setDoitTirer(boolean doitTirer) {
		this.doitTirer = doitTirer;
	}

	@Override
	public void dessine() {
		if(this.doitTirer) {
			if(this.getPv() > 200 )
				StdDraw.picture(this.getX(), this.getY(), "gifs/tirePoisFrappe.gif", 0.13, 0.13);
			else if (this.getPv() > 100) 
				StdDraw.picture(this.getX(), this.getY(), "gifs/tirePoisFrappeMort1.gif", 0.13, 0.13);
			else
				StdDraw.picture(this.getX(), this.getY(), "gifs/tirePoisFrappeMort2.gif", 0.13, 0.13);
		}
		else {
			if (this.getPv() > 200)
				StdDraw.picture(this.getX(), this.getY(), "/images/tirePois.png", 0.13, 0.13);
			else if (this.getPv() > 100)
				StdDraw.picture(this.getX(), this.getY(), "/images/tirePoisMort1.png", 0.13, 0.13);
			else
				StdDraw.picture(this.getX(), this.getY(), "/images/tirePoisMort2.png", 0.13, 0.13);
		}
	}
}
