package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.ReseptiDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.ReseptiLisays;
//import tikape.runko.domain.Drinkki;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:drinkki.db");
        database.init();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        ReseptiDao reseptiDao = new ReseptiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        get("drinkit/:id", (req, res) -> {
            System.out.println(">> tehdaan map");
            HashMap map = new HashMap<>();
            System.out.println(">> haetaan tietokannasta yksi");
            
            Drinkki d = drinkkiDao.findOne(Integer.parseInt(req.params("id")));
            map.put("drinkkiresepti", d.getNimi());
            map.put("drinkkiId", d.getId());
            
            map.put("ainekset", reseptiDao.fetchAinekset(Integer.parseInt(req.params("id"))));
            map.put("raakikset", reseptiDao.fetchAllRaakaAineet());
            System.out.println(">> saatiin arvo tietokannasta " + map.get("drinkkiresepti").toString());
            System.out.println(">> saatiin arvo tietokannasta " + map.get("ainekset").toString());
            System.out.println(">> saatiin arvo tietokannasta " + map.get("raakikset").toString());
            return new ModelAndView(map, "drinkkiresepti");
        }, new ThymeleafTemplateEngine());
        
        
        get("/tilasto", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tilasto", reseptiDao.fetchAllRaakaAineet());

            return new ModelAndView(map, "tilasto");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/drinkit", (req, res) -> {
            String nimi = req.queryParams("lisattyDrinkki");
            System.out.println("Vastaanotettiin " + nimi);
            if (nimi != null & nimi.length() > 2) {
                drinkkiDao.save(nimi);
                res.redirect("/drinkit");
            }
            return "Anna kuvaavampi nimi, pääset takaisin selaimen peruutus-napilla " + nimi;
        });
        
        Spark.post("/drinkit/:id", (req, res) -> {
            Integer luku = Integer.parseInt(req.params("id"));
            System.out.println("Vastaanotettiin " + luku);
            
            Integer raakaAineId = Integer.parseInt(req.queryParams("raakikset"));
            Integer maara = Integer.parseInt(req.queryParams("maara"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String ohje = req.queryParams("ohje");
            System.out.println(">> raakaAine " + raakaAineId);
            System.out.println(">> maara " + maara);
            System.out.println(">> jarjestys " + jarjestys);
            System.out.println(">> ohje " + ohje);
            System.out.println("**************");
            ReseptiLisays re = new ReseptiLisays(raakaAineId, luku, jarjestys, maara, ohje);
            re.toString();
            
            if (re.getRakaa_aine_id() > 0) {
                reseptiDao.save(re);
            }
            
            
//            
//            if (!raakisNimi.isEmpty() && raakisNimi.length() > 0) {
//                reseptiDao.lisaaRaakis(raakisNimi);
//            }
            
            res.redirect("/drinkit/" + luku);
            
            return "";
        });
        
        Spark.post("/tilasto", (req, res) -> {
            String ohje = req.queryParams("raakaAine");
            System.out.println(">> saatiin raakis: " + ohje);
            reseptiDao.lisaaRaakis(ohje);
            
            res.redirect("/tilasto");
            return "";
        });
        
        Spark.post("drinkit/:id/raakis", (req, res) -> {
            Integer luku = Integer.parseInt(req.params("id"));
            
            String raakisNimi = req.queryParams("lisaaUusiRaakis");
            System.out.println(">> saatiin raakis " + raakisNimi);
            reseptiDao.lisaaRaakis(raakisNimi);
            
            res.redirect("/drinkit/" + luku);
            
           return ""; 
        }); 
//        Spark.post("/drinkit/:id?lisaaUusiRaakis=:txt", (req, res) -> {
//            Integer luku = Integer.parseInt(req.params("id"));
//            System.out.println("Vastaanotettiin " + luku);
//            
//            Integer raakaAineId = Integer.parseInt(req.queryParams("raakikset"));
//            Integer maara = Integer.parseInt(req.queryParams("maara"));
//            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
//            String ohje = req.queryParams("ohje");
//            System.out.println(">> raakaAine " + raakaAineId);
//            System.out.println(">> maara " + maara);
//            System.out.println(">> jarjestys " + jarjestys);
//            System.out.println(">> ohje " + ohje);
//            System.out.println("**************");
//            ReseptiLisays re = new ReseptiLisays(raakaAineId, luku, jarjestys, maara, ohje);
//            re.toString();
//            
//            reseptiDao.save(re);
//            
//            res.redirect("/drinkit/" + luku);
//            
//            return "";
//        });
    }
}
