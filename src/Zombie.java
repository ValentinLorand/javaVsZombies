
public abstract class Zombie extends Entite{
	private int pv;
	private double vitesse;
	private int degat;
	private Timer frappe;
	
	public Zombie(int pv, double vitesse, double x, double y) {
		super(x,y);
		this.pv = pv;
		this.vitesse = vitesse;
		degat = 30;
		frappe = new Timer(1000);
	}

	public int getPv() {
		return pv;
	}

	public void retirePv(int degats) {
		this.pv -= degats;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	
	public boolean doitFrapper() {
		if(frappe.hasFinished()) {
			frappe.restart();
			return true;
		}
		return false;
	}
	
}
