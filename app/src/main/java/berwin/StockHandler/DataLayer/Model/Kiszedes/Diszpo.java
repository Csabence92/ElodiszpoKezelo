package berwin.StockHandler.DataLayer.Model.Kiszedes;

import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.LogicLayer.Enums.OrderStatus;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;

/**
 * Created by Röhberg Péter on 2018. 10. 26..
 */

public class Diszpo
{
    private int lezarva;
    public void setLezarva(int lezarva) { this.lezarva = lezarva; }
    public int getLezarva() { return lezarva; }

    private OrderStatus allapot;
    public void setAllapot(OrderStatus allapot) { this.allapot = allapot; }
    public OrderStatus getAllapot() { return allapot; }

    private String rendelesSzam;
    public void setRendelesSzam(String rendelesSzam) { this.rendelesSzam = rendelesSzam; }
    public String getRendelesSzam() { return rendelesSzam; }

    private String cikkSzam;
    public void setCikkSzam(String cikkSzam) { this.cikkSzam = cikkSzam; }
    public String getCikkSzam() { return cikkSzam; }

    private String vevo;
    public void setVevo(String vevo) { this.vevo = vevo; }
    public String getVevo() { return vevo; }

    private String lokacio;
    public void setLokacio(String lokacio) { this.lokacio = lokacio; }
    public String getLokacio() { return lokacio; }

    private double szelesseg;
    public void setSzelesseg(double szelesseg) { this.szelesseg = szelesseg; }
    public double getSzelesseg() { return szelesseg; }

    private double keszletenHossz;
    public void setKeszletenHossz(double keszletenHossz) { this.keszletenHossz = keszletenHossz; }
    public double getKeszletenHossz() { return keszletenHossz; }

    private double szuksegesHossz;
    public void setSzuksegesHossz(double szuksegesHossz) { this.szuksegesHossz = szuksegesHossz; }
    public double getSzuksegesHossz() { return szuksegesHossz; }

    private List<Beles> belesek;
    public void setBelesek(List<Beles> belesek) { this.belesek = belesek; }
    public List<Beles> getBelesek() { return belesek; }
    public void addToBelesek(Beles beles) {this.belesek.add(beles); }

    private List<Beolvasott> cikkszamBeolvasott;
    public void setCikkszamBeolvasott(List<Beolvasott> cikkszamBeolvasott) { this.cikkszamBeolvasott = cikkszamBeolvasott; }
    public List<Beolvasott> getCikkszamBeolvasott() { return cikkszamBeolvasott; }
    public void addToCikkszamBeolvasott(Beolvasott beolvasott) {this.cikkszamBeolvasott.add(beolvasott); }

    private int beolvasottSzovetVegekSzama;
    public void setBeolvasottSzovetVegekSzama(int beolvasottVegekSzama) { this.beolvasottSzovetVegekSzama = beolvasottVegekSzama; }
    public int getBeolvasottSzovetVegekSzama() { return beolvasottSzovetVegekSzama + cikkszamBeolvasott.size(); }

    private int beolvasottVirtualVegekSzovetSzama;
    public void setBeolvasottVirtualVegekSzovetSzama(int beolvasottVirtualVegekSzovetSzama) { this.beolvasottVirtualVegekSzovetSzama = beolvasottVirtualVegekSzovetSzama; }
    public int getBeolvasottVirtualVegekSzovetSzama() { return beolvasottVirtualVegekSzovetSzama + getVirtualisBeolvasottakSzama(); }

    private int getVirtualisBeolvasottakSzama() {
        int db = 0;
        if (cikkszamBeolvasott != null && cikkszamBeolvasott.size() > 0) {
            for (Beolvasott i: cikkszamBeolvasott) {
                if (i.isVirtualVegE()) {
                    db++;
                }
            }
        }
        return db;
    }

    private double osszCikkszamBeolvasott;
    public void setOsszCikkszamBeolvasott(double osszCikkszamBeolvasott) { this.osszCikkszamBeolvasott = osszCikkszamBeolvasott; }
    public double getOsszCikkszamBeolvasott() {
        double ossz = 0;
        for (Beolvasott i: cikkszamBeolvasott)
        {
            ossz += i.getBeolvasottHossz();
        }
        return osszCikkszamBeolvasott + ossz;
    }

    private double osszVirtualVegSzovetBeolvasott;
    public void setOsszVirtualVegSzovetBeolvasott(double osszVirtualVegSzovetBeolvasott) { this.osszVirtualVegSzovetBeolvasott = osszVirtualVegSzovetBeolvasott; }
    public double getOsszVirtualVegSzovetBeolvasott() { return osszVirtualVegSzovetBeolvasott + getVirtualisBeolvasottakHossz(); }

    private double getVirtualisBeolvasottakHossz() {
        double hossz = 0;
        if (cikkszamBeolvasott != null && cikkszamBeolvasott.size() > 0) {
            for (Beolvasott i: cikkszamBeolvasott) {
                if (i.isVirtualVegE()) {
                    hossz += i.getBeolvasottHossz();
                }
            }
        }
        return hossz;
    }


    public Diszpo()
    {
        this.belesek = new ArrayList<>();
        this.cikkszamBeolvasott = new ArrayList<>();
    }

    public Diszpo(String rendelesSzam, String cikkSzam, String vevo, String lokacio, double szelesseg, double keszletenHossz, double szuksegesHossz)
    {
        this.rendelesSzam = rendelesSzam;
        this.cikkSzam = cikkSzam;
        this.vevo = vevo;
        this.lokacio = lokacio;
        this.szelesseg = szelesseg;
        this.keszletenHossz = keszletenHossz;
        this.szuksegesHossz = szuksegesHossz;
        this.belesek = new ArrayList<Beles>();
        this.cikkszamBeolvasott = new ArrayList<>();
    }
}
