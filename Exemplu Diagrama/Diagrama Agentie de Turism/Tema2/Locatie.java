package Tema2;

public class Locatie {
	private String locatieAbsoluta;
	private String tara;
	private String judet;

	public Locatie() {
		this.locatieAbsoluta = "";
		this.tara = "";
		this.judet = "";
	}

	public Locatie(String locatieAbsoluta, String tara, String judet) {
		this.locatieAbsoluta = locatieAbsoluta;
		this.judet = judet;
		this.tara = tara;
	}

	public void afisare() {
		if (!this.locatieAbsoluta.isEmpty())
			System.out.println("\n-----Locatie-----\n\n\tlocatieAbsoluta: " + this.locatieAbsoluta + "\n\ttara: " + this.tara + "\n\tjudet: " + this.judet);
		else
			System.out.println("afisare(): Obiectul de tip Locatie nu este populat");
	}

	public String getTara() {
		return this.tara;
	}

	public String getJudet() {
		return this.judet;
	}

	public String getLocAbs() {
		return this.locatieAbsoluta;
	}

	// comparam daca locatia curenta a clientului este in EU sau spatiul Schengen si
	// daca locatia aleasa este de asemenea in EU sau spatiul Schengen
	public boolean isPassportNeededEU(String locatie) {
		boolean clientC = false, destinationC = false;
		for (TariEuShen country : TariEuShen.values()) {
			if (locatie.contains(country.toString()))
				clientC = true;
			if (this.tara.equals(country.toString()))
				destinationC = true;
		}
		if (clientC && destinationC)
			return false;
		return true;
	}
}
