package berwin.StockHandler.DataLayer.Model.PlanSzedes;

public class Rendeles {

    private String rendelesSzam;
    public String getRendelesSzam() { return rendelesSzam; }
    public void setRendelesSzam(String rendelesSzam) { this.rendelesSzam = rendelesSzam; }

    private double szuksegesHossz;
    public double getSzuksegesHossz() { return szuksegesHossz; }
    public void setSzuksegesHossz(double szuksegesHossz) { this.szuksegesHossz = szuksegesHossz; }

    private String cikkszam;
    public String getCikkszam() { return cikkszam; }
    public void setCikkszam(String cikkszam) { this.cikkszam = cikkszam; }

    public Rendeles() { }

    public Rendeles(String rendelesSzam, double szuksegesHossz, String cikkszam) {
        this.rendelesSzam = rendelesSzam;
        this.szuksegesHossz = szuksegesHossz;
        this.cikkszam = cikkszam;
    }

    @Override
    public String toString() {
        return rendelesSzam;
    }
}
