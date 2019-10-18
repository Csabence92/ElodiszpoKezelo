package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

/**
 * Created by Röhberg Péter on 2018. 10. 31..
 */

public class Beolvasott
{
    private String id;
    public void setID(String  id) { this.id = id; }
    public String  getId() { return id; }

    private String cikkszam;
    public void setCikkszam(String cikkszam) { this.cikkszam = cikkszam; }
    public String getCikkszam() { return cikkszam; }

    private String lokacio;
    public void setLokacio(String lokacio) { this.lokacio = lokacio; }
    public String getLokacio() { return lokacio; }

    private String bin;
    public void setBin(String  bin) { this.bin = bin; }
    public String getBin() { return this.bin; }

    private String nev;
    public void setNev(String  nev) { this.nev = nev; }
    public String getNev() { return this.nev; }

    private String mozgas;
    public void setMozgas(String  mozgas) { this.mozgas = mozgas; }
    public String getMozgas() { return this.mozgas; }

    private double szelesseg;
    public void setSzelesseg(double szelesseg) { this.szelesseg = szelesseg; }
    public double getSzelesseg() { return szelesseg; }

    private double beolvasottHossz;
    public void setBeolvasottHossz(double beolvasottHossz) { this.beolvasottHossz = beolvasottHossz; }
    public double getBeolvasottHossz() { return beolvasottHossz; }

    private String legnagyobbRendeles;
    public String getLegnagyobbRendeles() { return legnagyobbRendeles; }
    public void setLegnagyobbRendeles(String legnagyobbRendeles) { this.legnagyobbRendeles = legnagyobbRendeles; }

    private boolean virtualVegE;
    public void setVirtualVegE(boolean virtualVegE) { this.virtualVegE = virtualVegE; }
    public boolean isVirtualVegE() { return this.virtualVegE; }

    public Beolvasott(String id, String cikkszam, String lokacio, double szelesseg, double beolvasottHossz)
    {
        this.id = id;
        this.cikkszam = cikkszam;
        this.lokacio = lokacio;
        this.szelesseg = szelesseg;
        this.beolvasottHossz = beolvasottHossz;
    }

    public Beolvasott() {}

    @Override
    public String toString() {
        return  "ID: " + id + " Cikkszám: " + cikkszam + " Szélesség: "
                + szelesseg + "m Hossz: " + beolvasottHossz + "m";
    }
}
