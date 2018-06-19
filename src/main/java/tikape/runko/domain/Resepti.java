/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Pete
 */
public class Resepti {
    private Integer rakaa_aine_id;
    private String raakaAineNimi;
    private Integer annos_id;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;

    public Resepti(Integer rakaa_aine_id, String raakaAineNimi, Integer annos_id, Integer jarjestys, Integer maara, String ohje) {
        this.rakaa_aine_id = rakaa_aine_id;
        this.raakaAineNimi = raakaAineNimi;
        this.annos_id = annos_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getRakaa_aine_id() {
        return rakaa_aine_id;
    }

    public String getRaakaAineNimi() {
        return raakaAineNimi;
    }
    
    public Integer getAnnos_id() {
        return annos_id;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public Integer getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }

    @Override
    public String toString() {
        return "Resepti{" + "rakaa_aine_id=" + rakaa_aine_id + ", raakaAineNimi=" + raakaAineNimi + ", annos_id=" + annos_id + ", jarjestys=" + jarjestys + ", maara=" + maara + ", ohje=" + ohje + '}';
    }
    
    
}
