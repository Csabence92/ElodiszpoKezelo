package berwin.StockHandler.DataLayer.Model.Kiszedes;

import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;

/**
 * Created by Röhberg Péter on 2018. 10. 26..
 */

public class Beles
{
    private String anyagKod;
    public void setAnyagKod(String anyagKod) { this.anyagKod = anyagKod; }
    public String getAnyagKod() { return anyagKod; }

    private String lokacio;
    public void setLokacio(String lokacio) { this.lokacio = lokacio; }
    public String getLokacio() { return lokacio; }

    private double keszletenHossz;
    public void setKeszletenHossz(double keszletenHossz) { this.keszletenHossz = keszletenHossz; }
    public double getKeszletenHossz() { return keszletenHossz; }

    private double szuksegesHossz;
    public void setSzuksegesHossz(double szuksegesHossz) { this.szuksegesHossz = szuksegesHossz; }
    public double getSzuksegesHossz() { return szuksegesHossz; }

    private List<Beolvasott> belesBeolvasottak;
    public void setBelesBeolvasottak(List<Beolvasott> belesBeolvasottak) { this.belesBeolvasottak = belesBeolvasottak; }
    public List<Beolvasott> getBelesBeolvasottak() { return belesBeolvasottak; }
    public void addToAnyagBeolvasott(Beolvasott beolvasott) {this.belesBeolvasottak.add(beolvasott); }

    private boolean mentve = true;
    public void setMentve(boolean mentve) { this.mentve = mentve; }
    public boolean isMentve() { return mentve; }

    private int belesBeolvasottDB;
    public void setBelesBeolvasottDB(int belesBeolvasottDB) { this.belesBeolvasottDB = belesBeolvasottDB; }
    public int getBelesBeolvasottDB() { return belesBeolvasottDB + belesBeolvasottak.size(); }

    private int beolvasottVirtualVegekBelesSzama;
    public void setBeolvasottVirtualVegekBelesSzama(int beolvasottVirtualVegekBelesSzama) { this.beolvasottVirtualVegekBelesSzama = beolvasottVirtualVegekBelesSzama; }
    public int getBeolvasottVirtualVegekBelesSzama() { return beolvasottVirtualVegekBelesSzama + getVirtualisBeolvasottakSzama(); }

    private int getVirtualisBeolvasottakSzama() {
        int virtDarab = 0;
        if (belesBeolvasottak != null && belesBeolvasottak.size() > 0) {
            for (Beolvasott i: belesBeolvasottak) {
                if (i.isVirtualVegE()) {
                    virtDarab++;
                }
            }
        }
        return virtDarab;
    }

    private double osszBelesBeolvasott;
    public void setOsszBelesBeolvasott(double osszBelesBeolvasott) { this.osszBelesBeolvasott = osszBelesBeolvasott; }
    public double getOsszBelesBeolvasott() {
        double ossz = 0;
        for (Beolvasott i: belesBeolvasottak)
        {
            ossz += i.getBeolvasottHossz();
        }
        return osszBelesBeolvasott + ossz;
    }

    private double osszVirtualVegBelesBeolvasott;
    public void setOsszVirtualVegBelesBeolvasott(double osszVirtualVegBelesBeolvasott) { this.osszVirtualVegBelesBeolvasott = osszVirtualVegBelesBeolvasott; }
    public double getOsszVirtualVegBelesBeolvasott() { return osszVirtualVegBelesBeolvasott + getVirtualisBeolvasottakHossz(); }

    private double getVirtualisBeolvasottakHossz() {
        double virtHossz = 0;
        if (belesBeolvasottak != null && belesBeolvasottak.size() > 0) {
            for (Beolvasott i: belesBeolvasottak) {
                if (i.isVirtualVegE()) {
                    virtHossz += i.getBeolvasottHossz();
                }
            }
        }
        return virtHossz;
    }


    public Beles()
    {
        this.belesBeolvasottak = new ArrayList<>();
    }

    public Beles(String anyagKod, String lokacio, double keszletenHossz, double szuksegesHossz)
    {
        this.anyagKod = anyagKod;
        this.lokacio = lokacio;
        this.keszletenHossz = keszletenHossz;
        this.szuksegesHossz = szuksegesHossz;
        this.belesBeolvasottak = new ArrayList<>();
    }
}
