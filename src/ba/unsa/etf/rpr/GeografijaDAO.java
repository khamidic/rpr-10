package ba.unsa.etf.rpr;

import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class GeografijaDAO {
    private static GeografijaDAO ourInstance = new GeografijaDAO();
    private Connection conn;

    public static GeografijaDAO getInstance() {
        return ourInstance;
    }

    public GeografijaDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza");

            String upit1 = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) " +
                    "VALUES (1, 'Pariz', 2206488, 6)";

            String upit2 = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) " +
                    "VALUES (2, 'London', 8825000, 7)";

            String upit3 = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) " +
                    "VALUES (3, 'Beč', 1899055, 8)";

            String upit4 = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) " +
                    "VALUES (4, 'Manchester', 545500, 7)";

            String upit5 = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) " +
                    "VALUES (5, 'Graz',  280200, 8)";

            String upit6 = "insert into drzava(id, naziv, glavni_grad) " +
                    "values (6, 'Francuska', 1)";
            String upit7 = "insert into drzava(id, naziv, glavni_grad)" +
                    "values(7, 'Velika Britanija', 2)";
            String upit8 = "insert into drzava(id, naziv, glavni_grad)" +
                    "values(8, 'Austrija', 3)";

            String upitPocetni1 = "delete from drzava";
            String upitPocetni2 = "delete from grad";

            Statement stmt = conn.createStatement();

            try {
                stmt.execute(upitPocetni1);
                stmt.execute(upitPocetni2);
                stmt.executeUpdate(upit1);
                stmt.executeUpdate(upit2);
                stmt.executeUpdate(upit3);
                stmt.executeUpdate(upit4);
                stmt.executeUpdate(upit5);
                stmt.executeUpdate(upit6);
                stmt.executeUpdate(upit7);
                stmt.executeUpdate(upit8);
            } catch (SQLiteException e){

            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeInstance() {
    }

    private static void initialize() {
        ourInstance = new GeografijaDAO();
    }

    public static GeografijaDAO dajInstancu() {
        if (ourInstance == null) initialize();
        return ourInstance;
    }

    public Grad glavniGrad(String drzava) {
        Grad g = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * from drzava where naziv =  ?");
            statement.setString(1, drzava);
            ResultSet result = statement.executeQuery();

            if(!result.isClosed()) {
                int id1 = result.getInt(3);
                Drzava d = new Drzava();
                PreparedStatement srmt2 = conn.prepareStatement("select * from main.grad where id = ?");
                srmt2.setInt(1, id1);
                ResultSet res2 = srmt2.executeQuery();
                d.setId(res2.getInt(4));
                d.setNaziv(drzava);
                g = new Grad(res2.getInt(1), res2.getString(2), res2.getInt(3), d);
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return g;
    }

    public void obrisiDrzavu(String drzava){
        try {
            PreparedStatement st = conn.prepareStatement("select id from main.drzava where naziv = ?");
            st.setString(1, drzava);
            ResultSet result = st.executeQuery();

            PreparedStatement st1 = conn.prepareStatement("delete from drzava where naziv = ?");
            st1.setString(1, drzava);
            st1.executeUpdate();

            if(!result.isClosed()){
                int idDrzave = result.getInt(1);
                PreparedStatement gradovi = conn.prepareStatement("delete from grad where drzava = ?");
                gradovi.setInt(1, idDrzave);
                gradovi.executeUpdate();
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public ArrayList<Grad> gradovi(){
        Set<Grad> gradovi = new TreeSet<>();
        ArrayList<Grad> pov = new ArrayList<>();

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT id, naziv, broj_stanovnika, drzava from grad");
            Statement drugiS = conn.createStatement();
            ArrayList<Drzava> drzave = drzavaId();

            while(result.next()){
                Grad g = new Grad();
                Drzava d = new Drzava();
                g.setId(result.getInt(1));
                g.setNaziv(result.getString(2));
                g.setBrojStanovnika(result.getInt(3));
                d.setId(result.getInt(4));
                for(Drzava dd: drzave){
                    if(d.getId() == dd.getId()) d.setNaziv(dd.getNaziv());
                }
                g.setDrzava(d);
                gradovi.add(g);
            }

            pov.addAll(gradovi);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pov;
    }

    public ArrayList<Drzava> drzavaId(){
        ArrayList<Drzava> d = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from drzava");

            while (rs.next()){
                Drzava d1 = new Drzava();
                Grad g = new Grad();
                d1.setId(rs.getInt(1));
                d1.setNaziv(rs.getString(2));
                g.setId(rs.getInt(3));
                d1.setGlavniGrad(g);
                d.add(d1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return d;
    }

    public void dodajGrad(Grad grad) {
        try {
            PreparedStatement st = conn.prepareStatement("insert into grad (id, naziv, broj_stanovnika, drzava) values ( ?, ?, ?, ?)");
            st.setInt(1, grad.getId());
            st.setString(2, grad.getNaziv());
            st.setInt(3, grad.getBrojStanovnika());
            st.setInt(4, grad.getDrzava().getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  void dodajDrzavu(Drzava drzava){
        try {
            PreparedStatement st = conn.prepareStatement("insert into drzava(id, naziv, glavni_grad) values ( ?, ?, ?)");
            st.setInt(1, drzava.getId());
            st.setString(2, drzava.getNaziv());
            st.setInt(3, drzava.getGlavniGrad().getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad){
        try {
            PreparedStatement ps = conn.prepareStatement("update grad set naziv = ?, broj_stanovnika = ?, drzava = ? where id = ? ");
            ps.setString(1, grad.getNaziv());
            ps.setInt(2, grad.getBrojStanovnika());
            ps.setInt(3, grad.getDrzava().getId());
            ps.setInt(4, grad.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String drzava){
        Drzava d = new Drzava();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * from main.drzava where naziv = ?");
            st.setString(1, drzava);
            ResultSet result = st.executeQuery();
            if(!result.next()) return null;

            d.setId(result.getInt(1));
            d.setNaziv(result.getString(2));
            Grad g = new Grad();
            g.setId(result.getInt(3));
            d.setGlavniGrad(g);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return d;
    }

    public Connection getConn() { return conn; }
}