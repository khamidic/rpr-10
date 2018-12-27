package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Main extends Application {
    static String ispisiGradove() {
        String s = "";
        GeografijaDAO gd = new GeografijaDAO();
        ArrayList<Grad> gradovi = gd.gradovi();
        ArrayList<Drzava> drzave = gd.drzavaId();

        for (Grad g : gradovi) {
            for (Drzava d : drzave) {
                if (g.getDrzava().getId() == d.getId())
                    s += g.getNaziv() + " (" + d.getNaziv() + ") - " + g.getBrojStanovnika() + "\n";
            }
        }

        return s;
    }

    static void glavniGrad() {
        System.out.println("Unesite naziv drzave: ");
        String unos;
        Scanner ulaz = new Scanner(System.in);
        unos = ulaz.nextLine();

        GeografijaDAO gd = new GeografijaDAO();

        Grad g = gd.glavniGrad(unos);
        if (g == null) System.out.println("Nepostojeća država");
        else System.out.println("Glavni grad države " + unos + " je " + g.getNaziv());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GeografijaDAO model = GeografijaDAO.dajInstancu();
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("glavni.fxml"), bundle);
        loader.setController(new GradoviReport(model));
        Parent root = loader.load();

        /*Parent root = FXMLLoader.load(getClass().getResource("glavni.fxml"));*/
        primaryStage.setScene(new Scene(root, 339.0, 332.0));
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
        glavniGrad();
        ispisiGradove();
        System.out.println(ispisiGradove());
    }
}
