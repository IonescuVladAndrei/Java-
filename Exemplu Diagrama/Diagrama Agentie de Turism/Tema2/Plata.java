package Tema2;

public class Plata {
    private String idPlata;
    private String idRezervare;
    private TipPlata tipPlata;
    private int val;

    public Plata(String idPlata, String idRezervare, TipPlata tipPlata, int val) {
        this.idPlata = idPlata;
        this.idRezervare = idRezervare;
        this.tipPlata = tipPlata;
        this.val = val;
    }

    public void afisare(int tip) {
        System.out.println("\n-----Plata-----\n\n\tidPlata: " + this.idPlata + "\n\tIdRezervare: " + this.idRezervare + "\n\ttipPlata: " + this.tipPlata + "\n\tval: " + this.val);
    }

    public String getIdPlata() {
        return this.idPlata;
    }

    public String getIdRezervare() {
        return this.idRezervare;
    }
}
