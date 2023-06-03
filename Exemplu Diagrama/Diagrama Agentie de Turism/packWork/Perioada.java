
package packWork;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Perioada {

	private LocalDate dataInceput;
	private LocalDate dataSfarsit;
	private boolean valabilitate;

	public Perioada(LocalDate dataInceput, LocalDate dataSfarsit) {
		this.dataInceput = dataInceput;
		this.dataSfarsit = dataSfarsit;
		this.valabilitate = true; // nu ar avea sens sa fie introudsa o perioada care a fost deja rezervata
	}

	public void afisare() {
		System.out.println("\n-----Perioada-----\n\n\tdataInceput: " + this.dataInceput + "\n\tdataSfarsit: " + this.dataSfarsit + "\n\tvalabilitate: " + this.valabilitate + "\n");
	}

	public LocalDate getDataIncep() {
		return this.dataInceput;
	}

	public LocalDate getDataSfar() {
		return this.dataSfarsit;
	}

	public boolean getValabilitate() {
		return this.valabilitate;
	}

	public void setDataIncep(LocalDate dateInceput) {
		this.dataInceput = dateInceput;
	}

	public void setDataSfar(LocalDate dataSfarsit) {
		this.dataSfarsit = dataSfarsit;
	}

	public void setVal(boolean valabilitate) {
		this.valabilitate = valabilitate;
	}

	public long getNrNopti() {
		long daysBetween = ChronoUnit.DAYS.between(this.dataInceput, this.dataSfarsit);
		return daysBetween;
	}
};
