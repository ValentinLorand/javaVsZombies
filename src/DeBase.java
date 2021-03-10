
public class DeBase extends Zombie {
	
	public DeBase(double x, double y) {
		super(200,0.0005, x, y);
	}
	
	public void step() {
		this.setPosition(new Position(this.getX()-super.getVitesse(), this.getY()));
	}
	
	public Entite stepAjoute() {
		return null;
	}
	
	public boolean stepSupprime() {
		return (this.getPv()<=0);
	}
	
	public void dessine() {
		if(this.getVitesse() == 0) {
			if(this.getPv() <70) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/deBaseFrappeMort2.gif",0.11,0.11);
			else if(this.getPv()< 140) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/deBaseFrappeMort1.gif",0.11,0.11);
			else StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/deBaseFrappe.gif",0.11,0.11);
		}
		else {
			if(this.getPv() <70) StdDraw.picture(this.getX(), this.getY()+ 0.01, "gifs/deBaseMort2.gif",0.11,0.11);
			else if(this.getPv() <140) StdDraw.picture(this.getX(), this.getY()+ 0.01, "gifs/deBaseMort1.gif",0.11,0.11);
			else StdDraw.picture(this.getX(), this.getY()+ 0.01, "gifs/deBase.gif",0.11,0.11); 
		}
	}
}
