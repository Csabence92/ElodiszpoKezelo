package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

public class BeolvasottRendelesSzam extends Beolvasott {

    private String rendelesSzam;
    public String getRendelesSzam() { return rendelesSzam; }
    public void setRendelesSzam(String rendelesSzam) { this.rendelesSzam = rendelesSzam; }

    private String kiadva;
    public String getKiadva() { return kiadva; }
    public void setKiadva(String kiadva) { this.kiadva = kiadva; }

    public BeolvasottRendelesSzam(String rendelesSzam, String kiadva)
    {
        super();
        setRendelesSzam(rendelesSzam);
        setKiadva(kiadva);
    }

    public BeolvasottRendelesSzam() { super(); }

    @Override
    public String toString() {
        return "ID: " + getId() + " Cikkszám: " + getCikkszam() + " Rendelésszám: " + this.rendelesSzam + " Szélesség: "
                + getSzelesseg() + "m Lokáció: " + getLokacio() + " Hossz: " + getBeolvasottHossz() + "m";
    }
}
