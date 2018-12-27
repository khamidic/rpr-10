package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class GradoviReport implements Initializable {
    public GeografijaDAO model;
    public TextField nazivGrada;
    public Menu View, Language;
    public TextField brojStanovnikaGrada;
    public TextField idDrzave;
    public TextField nazivDrzave;
    public TextField izGlavnog;
    public MenuItem Bosanski, Engleski, Francuski, Njemacki;
    public ResourceBundle b;
    public Locale l;
    public Label grad, brStanovnika;
    public Button stam, dodajDrz, dodajGr;

    public void initialize(URL url, ResourceBundle bundle){
        b = bundle;
    }

    public GradoviReport(GeografijaDAO m) { model = m; }

    public void dodajGradDB(javafx.event.ActionEvent actionEvent) {
        Grad g = new Grad();
        g.setNaziv(nazivGrada.getText());
        g.setBrojStanovnika(Integer.parseInt(brojStanovnikaGrada.getText()));
        Drzava d = new Drzava();
        d.setId(Integer.parseInt(idDrzave.getText()));
        g.setDrzava(d);

        model.dodajGrad(g);
    }

    public void dodajDrzavuDB(javafx.event.ActionEvent actionEvent) {
        Drzava d = new Drzava();
        d.setNaziv(nazivDrzave.getText());
        Grad g = new Grad();
        g.setId(Integer.parseInt(izGlavnog.getText()));
        d.setGlavniGrad(g);

        model.dodajDrzavu(d);
    }

    public void PDF(ActionEvent actionEvent){
        saveAs("pdf");
    }

    public void DOCX(ActionEvent actionEvent){
        saveAs("docx");
    }

    public void XMLX(ActionEvent actionEvent){
        saveAs("xmlx");
    }

    public void saveAs(String format){
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        if(format.contains("pdf")){
            try {
                jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/gradovi.jrxml"));
                JRDataSource jrDataSource = new JREmptyDataSource();
                jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrDataSource);
                File file = new File("resources/reports/gradovi.jrxml");
                String s = file.getAbsolutePath();
                String s1 = s.substring(0, s.length()-5) + "pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, s1);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
        if(format.contains("docx")){
           /* Exporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(String.valueOf(jasperPrint)));
            File file = new File("resources/reports/gradovi.jrxml");
            String s = file.getAbsolutePath();
            String s1 = s.substring(0, s.length()-5) + "docx";
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(s1));
            try {
                exporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }*/
        } else{

        }
    }

    public void showReport(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(model.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void bosanskiJezik(ActionEvent actionEvent) {
        l = new Locale("bs");
        b = ResourceBundle.getBundle("Translation", l);
        View.setText(b.getString("ViewPogled"));
        Bosanski.setText(b.getString("Bos"));
        Engleski.setText(b.getString("Eng"));
        Njemacki.setText(b.getString("Njem"));
        Francuski.setText(b.getString("Fr"));
        Language.setText(b.getString("LanugagePrevod"));
        String s = b.getString("NazivGrada");
        String s1 = b.getString("brSt");
        grad.setText(s);
        brStanovnika.setText(s1);
        stam.setText(b.getString("stampaj"));
        dodajDrz.setText(b.getString("drz"));
        dodajGr.setText(b.getString("gr"));
    }

    public void engleskiJezik(ActionEvent actionEvent) {
        l = new Locale("en", "US");
        b = ResourceBundle.getBundle("Translation", l);
        View.setText(b.getString("ViewPogled"));
        Bosanski.setText(b.getString("Bos"));
        Engleski.setText(b.getString("Eng"));
        Njemacki.setText(b.getString("Njem"));
        Francuski.setText(b.getString("Fr"));
        Language.setText(b.getString("LanugagePrevod"));
        String s = b.getString("NazivGrada");
        String s1 = b.getString("brSt");
        grad.setText(s);
        brStanovnika.setText(s1);
        stam.setText(b.getString("stampaj"));
        dodajDrz.setText(b.getString("drz"));
        dodajGr.setText(b.getString("gr"));
    }

    public void njemackiJezik(ActionEvent actionEvent) {
        l = new Locale("de");
        b = ResourceBundle.getBundle("Translation", l);
        View.setText(b.getString("ViewPogled"));
        Bosanski.setText(b.getString("Bos"));
        Engleski.setText(b.getString("Eng"));
        Njemacki.setText(b.getString("Njem"));
        Francuski.setText(b.getString("Fr"));
        Language.setText(b.getString("LanugagePrevod"));
        String s = b.getString("NazivGrada");
        String s1 = b.getString("brSt");
        grad.setText(s);
        brStanovnika.setText(s1);
        stam.setText(b.getString("stampaj"));
        dodajDrz.setText(b.getString("drz"));
        dodajGr.setText(b.getString("gr"));
    }

    public void francuskiJezik(ActionEvent actionEvent) {
        l = new Locale("fr");
        b = ResourceBundle.getBundle("Translation", l);
        View.setText(b.getString("ViewPogled"));
        Bosanski.setText(b.getString("Bos"));
        Engleski.setText(b.getString("Eng"));
        Njemacki.setText(b.getString("Njem"));
        Francuski.setText(b.getString("Fr"));
        Language.setText(b.getString("LanugagePrevod"));
        String s = b.getString("NazivGrada");
        String s1 = b.getString("brSt");
        grad.setText(s);
        brStanovnika.setText(s1);
        stam.setText(b.getString("stampaj"));
        dodajDrz.setText(b.getString("drz"));
        dodajGr.setText(b.getString("gr"));
    }
}
