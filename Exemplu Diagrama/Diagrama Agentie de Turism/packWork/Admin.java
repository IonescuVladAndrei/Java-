package packWork;

public class Admin extends Persoana {
	private String CNP;
	private Permisiuni nivelPerm;

	public Admin() {
		super();
		this.CNP = "";
		this.nivelPerm = Permisiuni.nivel1;
	}

	public Admin(String idPers, String mail, String parola, String nume, String prenume, String nrTelefon, String CNP, Permisiuni nivelPerm) {
		super(idPers, nume, prenume, nrTelefon);
		this.CNP = CNP;
		this.nivelPerm = nivelPerm;
	}

	@Override
	public void afisare() {
		if (!this.getID().isEmpty())
			System.out.println("\n-----Admin-----\n\n\tidPers: " + this.getID() + "\n\tnume: " + this.getNume() + "\n\tprenume: " + this.getPrenume() + "\n\tnrTelefon: " + this.getNrTelefon() + "\n\tCNP: " + this.CNP + "\n\tnivelPerm: " + this.nivelPerm);
		else
			System.out.println("afisare(): Obiectul de tip Admin nu este populat");
	}

	public void setNivel(int nivel) {
		if (nivel == 1)
			this.nivelPerm = Permisiuni.nivel1;
		else if (nivel == 2)
			this.nivelPerm = Permisiuni.nivel2;
		else
			this.nivelPerm = Permisiuni.nivel3;
	}

	public void setCNP(String CNP) {
		this.CNP = CNP;
	}

	public String getCNP() {
		return this.CNP;
	}
};
