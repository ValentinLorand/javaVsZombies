
public class Pois extends Entite {
	private int degats;
	private static final double vitesse = 0.005;

	public Pois(double x, double y ) {
		super(x,y);
		degats = 30;
	}
public boolean stepSupprime(){
	if(this.getX()>1) return true;
	return false;
}
	
	public Entite stepAjoute() {
		return null;
	}
	
	// met a jour l'entite : deplacement, effectuer une action
	public void step() {
		this.setPosition(new Position(this.getX()+vitesse,this.getY()));
	}
	
	public int getDegats() {
		return degats;
	}
	public void setDegats(int degats) {
		this.degats = degats;
	}
	
	// dessine l'entite, aux bonnes coordonnees
	public void dessine() {
		StdDraw.picture(this.getX(), this.getY(), "/images/pois.png");
	}


}