
public abstract class Plante extends Entite {
	private int pv;
	private int prix;
	
	public Plante(int pv, int prix,double x, double y) {
		super(x,y);
		this.pv = pv;
		this.prix = prix;
	}
	
	public int getPv() {
		return pv;
	}
	
	public int getPrix() {
		return prix;
	}
	
	public void retirePv(int degats) {
		this.pv -= degats;
	}
	
}
