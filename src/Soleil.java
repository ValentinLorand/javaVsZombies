
public class Soleil extends Entite {
	private Timer disparition;
	private double vitesse;
	private double arret;

	public Soleil(double x, double y) {
		super(x,y);
		disparition = new Timer(20000);
		vitesse = 0;
		arret = 0;
	}
	
	public void step() {
		if(disparition.hasFinished()) {
			Argent.ajouterMonnaie();
		}
		this.setPosition(new Position(this.getX(),this.getY()-vitesse));
		if(this.vitesse !=0 && this.getY()<=arret) this.vitesse =0;
	}
	
	public Entite stepAjoute() {
		return null;
	}
	
	public boolean stepSupprime() {
		if(disparition.hasFinished() || ( StdDraw.isMousePressed() && Math.abs(this.getX()-StdDraw.mouseX())<0.05 && (Math.abs(this.getY()-StdDraw.mouseY())<0.05))) {
			Argent.ajouterMonnaie();
			return true;
		}
		else return false;
	}
	
	public void dessine() {
		StdDraw.picture(this.getX(), this.getY(), "/images/soleil.png",0.1,0.1);
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public void setArret(double arret) {
		this.arret = arret;
	}
	
	
}
