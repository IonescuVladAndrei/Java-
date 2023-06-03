package packWork;

import java.time.LocalDate;

public class Rezervare {
	private String ID_rezerv;
	private String ID_pers;
	private Transport transport;
	private Locatie locatie;
	private TipPlata tipPlata;
	private boolean achitat;
	private int pret;
	private int pretCuReducere;
	private Perioada perioada;

	public Rezervare(String ID_rezerv, String ID_pers, Transport transport, Locatie locatie, TipPlata tipPlata, boolean achitat, int pret, int pretCuReducere, Perioada perioada) {
		this.ID_rezerv = ID_rezerv;
		this.ID_pers = ID_pers;
		this.transport = transport;
		this.locatie = locatie;
		this.tipPlata = tipPlata;
		this.achitat = achitat;
		this.pret = pret;
		this.pretCuReducere = pretCuReducere;
		this.perioada = perioada;
	}

	public void afisare(int tip) {
		if (tip == 1)
			System.out.println("\n-----Rezervare-----\n\tID_rezerv: " + this.ID_rezerv + "\n\tID_pers: " + this.ID_pers + "\n\t---Transport---\n\t  transport.ID_trans: " + this.transport.getIdTrans() + "\n\t  transport.numeComp: " + this.transport.getNumeComp() + "\n\t  transport.tipTrans: " + this.transport.getTipTrans() + "\n\t---Locatie---\n\t  locatie.locatieAbsoluta: " + this.locatie.getLocAbs() + "\n\t  locatie.tara: " + this.locatie.getTara() + "\n\t  locatie.judet: " + this.locatie
					.getJudet() + "\n\ttipPlata: " + this.tipPlata + "\n\tachitat: " + this.achitat + "\n\tpret: " + this.pret + "\n\tretCuReducere: " + this.pretCuReducere + "\n\t---Perioada---\n\t  perioada.dataInceput: " + this.perioada.getDataIncep() + "\n\t  perioada.dataSfarsit: " + this.perioada.getDataSfar() + "\n\t  perioada.valabilitate: " + this.perioada.getValabilitate());
		else
			System.out.println("\tID_rezerv: " + this.ID_rezerv + "\n\ttipPlata: " + this.tipPlata + "\n\tpret: " + this.pret + "\n\t---Transport---\n\t  transport.ID_trans: " + this.transport.getIdTrans() + "\n\t  transport.numeComp: " + this.transport.getNumeComp() + "\n\t  transport.tipTrans: " + this.transport.getTipTrans() + "\n\t---Locatie---\n\t  locatie.locatieAbsoluta: " + this.locatie.getLocAbs() + "\n\t  locatie.tara: " + this.locatie.getTara() + "\n\t  locatie.judet: " + this.locatie
					.getJudet() + "\n\t---Perioada---\n\t  perioada.dataInceput: " + this.perioada.getDataIncep() + "\n\t  perioada.dataSfarsit: " + this.perioada.getDataSfar());
	}

	public String getIdRezerv() {
		return this.ID_rezerv;
	}

	public String getIdPers() {
		return this.ID_pers;
	}

	public TipPlata getTipPlata() {
		return this.tipPlata;
	}

	public boolean getAchitat() {
		return this.achitat;
	}

	public int getPret() {
		return this.pret;
	}

	public int getPretCuReducere() {
		return this.pretCuReducere;
	}

	public void setTipPlata(TipPlata plata) {
		this.tipPlata = plata;
	}

	public void setAchitat(boolean achitat) {
		this.achitat = achitat;
	}

	// un punct = 25 de ron reducere
	// reducere maxima: 50%
	public int setPretCuReducere(int pctReducere) {
		int pretMin = this.pret / 2;
		int pct = pretMin / 25;
		if (pct >= pctReducere) { // cazul in care clientul are mai multe puncte decat este posibila reducerea
									// (sau egal)
			pctReducere = pctReducere - pct;
			this.pretCuReducere = this.pretCuReducere - pct * 25;
		} else {
			this.pretCuReducere = this.pretCuReducere - pctReducere * 25;
			pctReducere = 0;
		}
		return pctReducere;
	}

	public boolean getValabilitate() {
		return this.perioada.getValabilitate();
	}

	public void setValabilitate(boolean val) {
		this.perioada.setVal(val);
	}

	public String getLocatieAbsol() {
		return this.locatie.getLocAbs();
	}

	public LocalDate getDataIncep() {
		return this.perioada.getDataIncep();
	}

	public LocalDate getDataSfar() {
		return this.perioada.getDataSfar();
	}

	public String getTara() {
		return this.locatie.getTara();
	}

	public String getJudet() {
		return this.locatie.getJudet();
	}

	public void setClientId(String ID_pers) {
		this.ID_pers = ID_pers;
	}

	public long getNrNopti() {
		return this.perioada.getNrNopti();
	}

	public boolean isPassportNeededEU(String locatie) {
		return this.locatie.isPassportNeededEU(locatie);
	}
};
