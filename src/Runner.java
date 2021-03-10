
public class Runner extends Zombie {
	
	public Runner(double x, double y) {
		super(630,0.001, x, y);
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
			if(this.getPv()<140) StdDraw.picture(this.getX()-0.01, this.getY()+0.01, "gifs/runnerFrappeMort4.gif",0.11,0.11);
			else if(this.getPv()<280) StdDraw.picture(this.getX()-0.01, this.getY()+0.01, "gifs/runnerFrappeMort3.gif",0.11,0.11);
			else if(this.getPv()<420) StdDraw.picture(this.getX()-0.01, this.getY()+0.01, "gifs/runnerFrappeMort2.gif",0.11,0.11);
			else if(this.getPv()<560) StdDraw.picture(this.getX()-0.01, this.getY()+0.01, "gifs/runnerFrappeMort1.gif",0.11,0.11);
			else StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runner.gif",0.11,0.11);
		}
		else {
			if(this.getPv()<140) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runnerMort4.gif",0.11,0.11);
			else if(this.getPv()<280) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runnerMort3.gif",0.11,0.11);
			else if(this.getPv()<420) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runnerMort2.gif",0.11,0.11);
			else if(this.getPv()<560) StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runnerMort1.gif",0.11,0.11);
			else StdDraw.picture(this.getX(), this.getY()+0.01, "gifs/runner.gif",0.11,0.11);
		}
	}
}
