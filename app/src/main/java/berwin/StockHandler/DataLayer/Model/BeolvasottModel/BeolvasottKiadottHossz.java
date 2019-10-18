package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

public class BeolvasottKiadottHossz extends BeolvasottQRCode {

    private double kiadottHossz;
    public void setKiadottHossz(double kiadottHossz) { this.kiadottHossz = kiadottHossz; }
    public double getKiadottHossz() { return kiadottHossz; }

    private String leiras;
    public void setLeiras(String leiras) { this.leiras = leiras; }
    public String getLeiras() { return leiras; }

    @Override
    public String toString() {
        return "ID: " + getId() + " Cikkszám: " + getCikkszam() + " Szélesség: " + getSzelesseg()
                + "m Lokáció: " + getLokacio() + " Régihossz: " + getBeolvasottHossz()
                + "m Kiadotthossz: " + this.kiadottHossz + "m Újhossz: " + (getBeolvasottHossz() - this.kiadottHossz)
                + "m Leírás: " + this.leiras;
    }
}
