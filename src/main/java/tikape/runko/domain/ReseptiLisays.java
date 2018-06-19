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
public class ReseptiLisays {
    private Integer rakaa_aine_id;
    private Integer annos_id;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;

    public ReseptiLisays(Integer rakaa_aine_id, Integer annos_id, Integer jarjestys, Integer maara, String ohje) {
        this.rakaa_aine_id = rakaa_aine_id;
        this.annos_id = annos_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getRakaa_aine_id() {
        return rakaa_aine_id;
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
        return "Resepti{" + "rakaa_aine_id=" + rakaa_aine_id + ", annos_id=" + annos_id + ", jarjestys=" + jarjestys + ", maara=" + maara + ", ohje=" + ohje + '}';
    }
    
    
}
