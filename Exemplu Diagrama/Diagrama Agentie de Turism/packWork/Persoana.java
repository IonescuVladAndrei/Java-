package packWork;

public class Persoana {
	private String idPers;
	private String nume;
	private String prenume;
	private String nrTelefon;

	public Persoana() {
		this.idPers = "";
		this.nume = "";
		this.prenume = "";
		this.nrTelefon = "";
	}

	public Persoana(String idPers, String nume, String prenume, String nrTelefon) {
		this.idPers = idPers;
		this.nume = nume;
		this.prenume = prenume;
		this.nrTelefon = nrTelefon;
	}

	public void afisare() {
		if (!this.idPers.isEmpty())
			System.out.println("\n-----Persoana-----\n\n\tidPers: " + this.idPers + "\n\tnume: " + this.nume + "\n\tprenume: " + this.prenume + "\n\tnrTelefon: " + this.nrTelefon + "\n");
		else
			System.out.println("afisare(): Obiectul de tip Persoana nu este populat");
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public void setNrTelefon(String nrTelefon) {
		this.nrTelefon = nrTelefon;
	}

	public String getID() {
		return this.idPers;
	}

	public String getNume() {
		return this.nume;
	}

	public String getPrenume() {
		return this.prenume;
	}

	public String getNrTelefon() {
		return this.nrTelefon;
	}
};
