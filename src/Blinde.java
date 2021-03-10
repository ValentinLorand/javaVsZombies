
public class Blinde extends Zombie {

	public Blinde(double x, double y) {
		super(560,0.0005, x, y);
	}
	
	public void step() {
		this.setPosition(new Position(this.getX()-super.getVitesse(), this.getY()));
	}
	
	public Entite stepAjoute() {
		return null;
	}
	
	public boolean stepSupprime() {
		return (this.getPv()<=0 || this.getX()<=0);
	}
	
	public void dessine() {
		if(this.getVitesse() == 0) {
			if(this.getPv()<140) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeFrappeMort3.gif",0.13,0.13);
			else if(this.getPv()<280) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeFrappeMort2.gif",0.13,0.13);
			else if(this.getPv()<420) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeFrappeMort1.gif",0.13,0.13);
			else StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeFrappe.gif",0.13,0.13);
		}
		else {
			if(this.getPv()<140) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeMort3.gif",0.13,0.13);
			else if(this.getPv()<280) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeMort2.gif",0.13,0.13);
			else if(this.getPv()<420) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blindeMort1.gif",0.13,0.13);
			else StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/blinde.gif",0.13,0.13);
		}
	}

}
