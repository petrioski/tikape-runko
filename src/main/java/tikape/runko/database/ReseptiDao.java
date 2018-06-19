/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Resepti;
import tikape.runko.domain.ReseptiLisays;

/**
 *
 * @author Pete
 */
public class ReseptiDao implements Dao<Resepti, Integer> {
    private Database database;
    
    public ReseptiDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Resepti findOne(Integer annosId) throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //select a.nimi, b.maara, b.jarjestys, b.ohje from AnnosRaakaAine b left join RaakaAine a on b.rakaa_aine_id = a.id where b.annos_id = 1;
        System.out.println("starting findOne()" + annosId);
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "Select a.nimi, b.maara, b.rakaa_aine_id, b.jarjestys, b.ohje "
                        + "from AnnosRaakaAine b "
                        + "left join RaakaAine a "
                        + "on b.rakaa_aine_id = a.id "
                        + "where b.annos_id = ?");
        stmt.setInt(1, annosId);
        
        System.out.println(">> " + stmt);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
               
        String nimi = rs.getString("nimi");
        Integer maara = rs.getInt("maara");
        Integer raakaAineId = rs.getInt("rakaa_aine_id");
        Integer jarjestys = rs.getInt("jarjestys");
        String ohje = rs.getString("ohje");
        
        
        Resepti resepti = new Resepti(raakaAineId, nimi, annosId, jarjestys, maara, ohje);
        
        rs.close();
        stmt.close();
        connection.close();

        return resepti;
    }

    @Override
    public List findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public List<Resepti> fetchAinekset(Integer annosId) throws SQLException {
        System.out.println("starting findOne()" + annosId);
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "Select a.nimi, b.maara, b.rakaa_aine_id, b.jarjestys, b.ohje "
                        + "from AnnosRaakaAine b "
                        + "left join RaakaAine a "
                        + "on b.rakaa_aine_id = a.id "
                        + "where b.annos_id = ? "
                        + "order by b.jarjestys;");
        stmt.setInt(1, annosId);
        
        System.out.println(">> " + stmt);
        ResultSet rs = stmt.executeQuery();
        
        List<Resepti> ainekset = new ArrayList<>();
        while (rs.next()) {
               
            String nimi = rs.getString("nimi");
            Integer maara = rs.getInt("maara");
            Integer raakaAineId = rs.getInt("rakaa_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            String ohje = rs.getString("ohje");

            ainekset.add(new Resepti(raakaAineId, nimi, annosId, jarjestys, maara, ohje));
        }
        rs.close();
        stmt.close();
        connection.close();

        return ainekset;
    }
    
    public List<RaakaAine> fetchAllRaakaAineet() throws SQLException {
        System.out.println("starting findall()");
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "select a.id, a.nimi, count(distinct annos_id) as kayttoKpl, sum(coalesce(maara, 0)) maaraYht "
                        + "from RaakaAine a "
                        + "left join AnnosRaakaAine b "
                        + "on a.id = b.rakaa_aine_id "
                        + "group by a.id, a.nimi "
                        + "order by kayttoKpl desc;");
        
        
        System.out.println(">> " + stmt);
        ResultSet rs = stmt.executeQuery();
        
        List<RaakaAine> raakikset = new ArrayList<>();
        while (rs.next()) {
            
            Integer raakaAineId = rs.getInt("id");   
            String nimi = rs.getString("nimi");
            RaakaAine ra = new RaakaAine(raakaAineId, nimi);
            ra.setEsiintymisKerrat(rs.getInt("kayttoKpl"));
            ra.setKaytettyMaara(rs.getInt("maaraYht"));
            raakikset.add(ra);
        }
        rs.close();
        stmt.close();
        connection.close();

        return raakikset;
    }
    
    public void save(ReseptiLisays re) throws SQLException {
        System.out.println("starting save()");
        
        Connection connection = database.getConnection();
        PreparedStatement stmt 
                = connection.prepareStatement("INSERT INTO AnnosRaakaAine (rakaa_aine_id"
                        + ", annos_id"
                        + ", jarjestys"
                        + ", maara"
                        + ", ohje) "
                        + "VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, re.getRakaa_aine_id());
        stmt.setInt(2, re.getAnnos_id());
        stmt.setInt(3, re.getJarjestys());
        stmt.setInt(4, re.getMaara());
        stmt.setString(5, re.getOhje());
        System.out.println(">> " + stmt);
        stmt.execute();

        stmt.close();
        connection.close();
        
    }
    
    public void lisaaRaakis(String nimi) throws SQLException {
        System.out.println(">> starting lisaaRaakis");
        Connection connection = database.getConnection();
        PreparedStatement stmt 
                = connection.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        stmt.setString(1, nimi);
        
        stmt.execute();

        stmt.close();
        connection.close();
    }
}
