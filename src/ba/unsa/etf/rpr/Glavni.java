package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Glavni {

    public ListView listaGradova;
    public TextField nazivGrada;
    public TextField brojStanovnikaGrada;
    public TextField idDrzave;
    public GeografijaDAO gd = new GeografijaDAO();
    public TextField nazivDrzave;
    public TextField idGlavnog;

    public void klikIspisiGrad(ActionEvent actionEvent) {
        ArrayList<Grad> gradovi = gd.gradovi();
        ArrayList<Drzava> drzave = gd.drzavaId();

        for(Grad g: gradovi){
            for(Drzava d: drzave) {
                if(g.getDrzava().getId() == d.getId()) {
                    g.getDrzava().setNaziv(d.getNaziv());
                    String s = "";
                    s += g.getNaziv() + " (" + d.getNaziv() + ") - " + g.getBrojStanovnika() + "\n";
                    listaGradova.getItems().add(s);
                }
            }
        }
    }

    public void dodajGradDB(ActionEvent actionEvent) {
        Grad g = new Grad();
        g.setNaziv(nazivGrada.getText());
        g.setBrojStanovnika(Integer.parseInt(brojStanovnikaGrada.getText()));
        Drzava d = new Drzava();
        d.setId(Integer.parseInt(idDrzave.getText()));
        g.setDrzava(d);

        gd.dodajGrad(g);
    }

    public void dodajDrzavuDB(ActionEvent actionEvent) {
        Drzava d = new Drzava();
        d.setNaziv(nazivDrzave.getText());
        Grad g = new Grad();
        g.setId(Integer.parseInt(idGlavnog.getText()));
        d.setGlavniGrad(g);

        gd.dodajDrzavu(d);
    }
}
