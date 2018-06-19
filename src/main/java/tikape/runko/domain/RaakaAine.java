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
public class RaakaAine {
    private Integer id;
    private String nimi;
    private Integer kaytettyMaara;
    private Integer esiintymisKerrat;

    public RaakaAine(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
            
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getKaytettyMaara() {
        return kaytettyMaara;
    }

    public void setKaytettyMaara(Integer kaytettyMaara) {
        this.kaytettyMaara = kaytettyMaara;
    }

    public Integer getEsiintymisKerrat() {
        return esiintymisKerrat;
    }

    public void setEsiintymisKerrat(Integer esiintymisKerrat) {
        this.esiintymisKerrat = esiintymisKerrat;
    }

    @Override
    public String toString() {
        return "RaakaAine{" + "id=" + id + ", nimi=" + nimi + ", kaytettyMaara=" + kaytettyMaara + ", esiintymisKerrat=" + esiintymisKerrat + '}';
    }

    
    
    
    
    
}
