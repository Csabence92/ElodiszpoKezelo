package berwin.StockHandler.DataLayer.Model.PlanSzedes;

import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import berwin.StockHandler.LogicLayer.Enums.KiszedesStatus;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;

public class Szovet {

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCount() { return count; }
    public void setCount(String count) { this.count = count; }
    private String count;
    private String message;
    private String cikkszam;
    public String getCikkszam() { return cikkszam; }
    public void setCikkszam(String cikkszam) { this.cikkszam = cikkszam; }

    private String lokacio;
    public String getLokacio() { return lokacio; }
    public void setLokacio(String lokacio) { this.lokacio = lokacio; }

    private String bin;
    public String getBin() { return bin; }
    public void setBin(String bin) { this.bin = bin; }

    private double szelesseg;
    public void setSzelesseg(double szelesseg) { this.szelesseg = szelesseg; }
    public double getSzelesseg() { return szelesseg; }

    private double keszletenHossz;
    public double getKeszletenHossz() { return keszletenHossz; }
    public void setKeszletenHossz(double keszletenHossz) { this.keszletenHossz = keszletenHossz; }

    private KiszedesStatus kiszedesStatus;
    public KiszedesStatus getKiszedesStatus() { return this.kiszedesStatus; }
    public void setKiszedesStatus(KiszedesStatus kiszedesStatus) { this.kiszedesStatus = kiszedesStatus; }

    private ArrayList<Rendeles> rendelesek;
    public ArrayList<Rendeles> getRendelesek() { return rendelesek; }
    public void setRendelesek(ArrayList<Rendeles> rendelesek) { this.rendelesek = rendelesek; }


    public String getLegnagyobbRendeles() {
        return rendelesek.get(getLegnagyobbRendelesIndex()).toString();
    }

    private int getLegnagyobbRendelesIndex() {
        int legnagyobbRendelesIndex = 0;
        if (rendelesek != null && rendelesek.size() > 0) {
            double max = rendelesek.get(0).getSzuksegesHossz();
            for (int i = 1; i < rendelesek.size(); i++) {
                if (rendelesek.get(i).getSzuksegesHossz() > max) {
                    max = rendelesek.get(i).getSzuksegesHossz();
                    legnagyobbRendelesIndex = i;
                }
            }
        }
        return legnagyobbRendelesIndex;
    }

    public double getSzuksegesHossz() {
        double osszHossz = 0;
        if (rendelesek != null && rendelesek.size() > 0) {
            for (Rendeles i: rendelesek) {
                osszHossz += i.getSzuksegesHossz();
            }
        }
        return osszHossz;
    }

    public String getRendelesSzamok() {
        String rendelesSzamok = "";
        if (rendelesek != null) {
            for (int i = 0; i < rendelesek.size(); i++) {
                if (i == rendelesek.size() - 1) {
                    rendelesSzamok += rendelesek.get(i).toString();
                } else {
                    rendelesSzamok += rendelesek.get(i).toString() + ", ";
                }
            }
        }
        return rendelesSzamok;
    }

    private ArrayList<Beolvasott> szovetBeolvasottak;
    public ArrayList<Beolvasott> getSzovetBeolvasottak() { return szovetBeolvasottak; }
    public void setSzovetBeolvasottak(ArrayList<Beolvasott> szovetBeolvasottak) { this.szovetBeolvasottak = szovetBeolvasottak; }
    public void addToSzovetBeolvasottak(Beolvasott beolvasott) { this.szovetBeolvasottak.add(beolvasott); setMessage(String.valueOf(this.szovetBeolvasottak.size())); }

    private int szovetBeolvasottVegekSzama;
    public int getSzovetBeolvasottVegekSzama() {
        //return szovetBeolvasottVegekSzama + szovetBeolvasottak.size();
        return szovetBeolvasottak != null ? szovetBeolvasottak.size() : 0;
    }
    public void setSzovetBeolvasottVegekSzama(int szovetBeolvasottVegekSzama) { this.szovetBeolvasottVegekSzama = szovetBeolvasottVegekSzama; }

    private double osszSzovetBeolvasottHossz;
    public void setOsszSzovetBeolvasottHossz(double osszSzovetBeolvasottHossz) { this.osszSzovetBeolvasottHossz = osszSzovetBeolvasottHossz; }

    public double getOsszSzovetBeolvasottHossz() {
        double osszBeolvasott = 0;
        if (szovetBeolvasottak != null && szovetBeolvasottak.size() > 0) {
            for (Beolvasott i: szovetBeolvasottak) {
                osszBeolvasott = i.getBeolvasottHossz() + osszBeolvasott;
            }
        }
       return osszSzovetBeolvasottHossz + osszBeolvasott;
      // return osszBeolvasott;
    }

    public Beolvasott getBeolvasottByID(String id) {
        return szovetBeolvasottak.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    private DecimalFormat df = new DecimalFormat("#.##");

    public Szovet() {

        this.rendelesek = new ArrayList<>();
        this.szovetBeolvasottak = new ArrayList<>();
    }

    public Szovet(String cikkszam, String lokacio, String bin) {
        this.cikkszam = cikkszam;
        this.lokacio = lokacio;
        this.bin = bin;
        this.rendelesek = new ArrayList<>();
        this.szovetBeolvasottak = new ArrayList<>();
    }
    public Szovet (double szelesseg)
    {
        this.szelesseg = szelesseg;
        this.rendelesek = new ArrayList<>();
        this.szovetBeolvasottak = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cikkszám: " + cikkszam +
                "\nLokáció: " + lokacio +
                "\nBin: " + bin +
                "\nKészleten hossz: " + df.format(keszletenHossz) +
                "m\nSzükséges hossz: " + df.format(getSzuksegesHossz()) +
                "m\nSzélesség: " + df.format(szelesseg) +
                "m\nBeolvasott hossz: " + df.format(getOsszSzovetBeolvasottHossz()) +
                "m\nRendelésszám(ok): " + getRendelesSzamok() +
                "\nÁllapot: " + getKiszedesStatus().toString() +
                "\nVégek száma: " + getCount() + " db";
    }
}
