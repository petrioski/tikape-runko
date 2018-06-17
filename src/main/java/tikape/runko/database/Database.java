package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        //return DriverManager.getConnection(databaseAddress); 
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection(databaseAddress);
        
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Annos (id serial primary key, nimi varchar(255));");
        lista.add("CREATE TABLE RaakaAine (id serial primary key, nimi varchar(255));");
        lista.add("CREATE TABLE AnnosRaakaAine (\n" +
                    " rakaa_aine_id integer REFERENCES RaakaAine (id),\n" +
                    " annos_id integer REFERENCES Annos (id),\n" +
                    " jarjestys integer,\n" +
                    " maara integer,\n" +
                    " ohje varchar(500)\n" +
                    ");");
//        lista.add("INSERT INTO Annos (nimi) VALUES ('Kossuvissy');");
//        lista.add("INSERT INTO Annos (nimi) VALUES ('Jekkupatteri');");
//        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Vissy');");
//        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Koskenkorva');");
//        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Jägermaister');");
//        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Battery+ energiajuoma');");

        return lista;
    }
}
