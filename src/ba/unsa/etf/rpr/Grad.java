package ba.unsa.etf.rpr;

public class Grad implements Comparable{
    private int id;
    private String naziv;
    private int BrojStanovnika;
    private Drzava drzava;

    public Grad(){};

    public Grad(int id, String naziv, int broj_stanovnika, Drzava drzava) {
        this.id = id;
        this.naziv = naziv;
        this.BrojStanovnika = broj_stanovnika;
        this.drzava = drzava;
    }

    public int compareTo(Object o){
        Grad g = (Grad) o;
        if(this.BrojStanovnika > g.getBrojStanovnika()) return -1;
        if(this.BrojStanovnika < g.getBrojStanovnika()) return 1;
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return BrojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.BrojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
}