package packWork;

public class AgentDeTurism extends Persoana {
	private String[] limbiStiute;
	private String locatieAgent;
	private String mailPublic;
	private String nrTelPublic;

	public AgentDeTurism() {
		super();
		this.limbiStiute = new String[] { "" };
		this.locatieAgent = "";
		this.mailPublic = "";
		this.nrTelPublic = "";
	}

	public AgentDeTurism(String idPers, String mail, String parola, String nume, String prenume, String nrTelefon, String[] limbiStiute, String locatieAgent, String mailPublic, String nrTelPublic) {
		super(idPers, nume, prenume, nrTelefon);
		this.limbiStiute = new String[limbiStiute.length];
		for (int i = 0; i < limbiStiute.length; i++)
			this.limbiStiute[i] = limbiStiute[i];
		this.locatieAgent = locatieAgent;
		this.mailPublic = mailPublic;
		this.nrTelPublic = nrTelPublic;
	}

	@Override
	public void afisare() {
		if (!this.getID().isEmpty()) {
			System.out.println("\n-----AgentieDeTurism-----\n\n\tidPers: " + this.getID() + "\n\tnume: " + this.getNume() + "\n\tprenume: " + this.getPrenume() + "\n\tnrTelefon: " + this.getNrTelefon() + "\n\tlocatieAgent: " + this.locatieAgent + "\n\tlimbiStiute: ");
			for (int i = 0; i < this.limbiStiute.length; i++)
				System.out.print(this.limbiStiute[i] + "  ");
			System.out.print("\n\tlocatieAgent: " + this.locatieAgent + "\n\tnrTelPublic: " + this.nrTelPublic + "\n\tmailPublic: " + this.mailPublic);
		} else
			System.out.println("afisare(): Obiectul de tip AgentieDeTurism nu este populat");
	}

	public boolean isForeign() {
		if (!this.locatieAgent.contains("Romania"))
			return true;
		return false;
	}

	public void setNume(String nume) {
		this.setNume(nume);
	}

	public void setLocatie(String locatie) {
		this.locatieAgent = locatie;
	}

	public String getLocatieAgen() {
		return this.locatieAgent;
	}

	public String getNrTelPublic() {
		return this.nrTelPublic;
	}

	public String getMailPublic() {
		return this.mailPublic;
	}

	public void setDateOnline(String nrTelPublic, String mailPublic) {
		this.nrTelPublic = nrTelPublic;
		this.mailPublic = mailPublic;
	}
};

