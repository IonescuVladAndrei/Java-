package packWork;

public class Transport {
	private String ID_trans;
	private String numeComp;
	private TipTransport tipTrans;

	public Transport() {
		this.ID_trans = "";
		this.numeComp = "";
		this.tipTrans = TipTransport.masinaPersonala;
	}

	public Transport(String ID_trans, String numeComp, TipTransport tipTrans) {
		this.ID_trans = ID_trans;
		this.numeComp = numeComp;
		this.tipTrans = tipTrans;
	}

	public void afisare() {
		if (!this.ID_trans.isEmpty())
			System.out.println("\n-----Transport-----\n\n\tID_trans: " + this.ID_trans + "\n\tnumeComp: " + this.numeComp + "\n\ttipTrans: " + this.tipTrans);
		else
			System.out.println("afisare(): Obiectul de tip Transport nu este populat");
	}

	public String getIdTrans() {
		return this.ID_trans;
	}

	public String getNumeComp() {
		return this.numeComp;
	}

	public TipTransport getTipTrans() {
		return this.tipTrans;
	}

	public void setTipTrans(TipTransport tipTrans) {
		this.tipTrans = tipTrans;
	}
};
