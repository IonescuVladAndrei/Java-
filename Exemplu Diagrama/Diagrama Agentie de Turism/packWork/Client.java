package packWork;

public class Client extends Persoana {
	private String gen;
	private String locatie;
	private int pctReducere;

	public Client() {
		super();
		this.gen = "";
		this.locatie = "";
		this.pctReducere = 0;
	}

	public Client(String idPers, String mail, String parola, String nume, String prenume, String nrTelefon, String gen, String locatie, int pctReducere) {
		super(idPers, nume, prenume, nrTelefon);
		this.gen = gen;
		this.locatie = locatie;
		this.pctReducere = pctReducere;
	}

	@Override
	public void afisare() {
		if (!this.getID().isEmpty())
			System.out.println("\n-----Client-----\n\n\tidPers: " + this.getID() + "\n\tnume: " + this.getNume() + "\n\tprenume: " + this.getPrenume() + "\n\tnrTelefon: " + this.getNrTelefon() + "\n\tgen: " + this.gen + "\n\tlocatie: " + this.locatie + "\n\tpctReducere: " + this.pctReducere);
		else
			System.out.println("afisare(): Obiectul de tip Client nu este populat");
	}

	public int getNrPctRed() {
		return this.pctReducere;
	}

	public String getLocatie() {
		return this.locatie;
	}

	public void setNrPctRed(int pctReducere) {
		this.pctReducere = pctReducere;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}
};
