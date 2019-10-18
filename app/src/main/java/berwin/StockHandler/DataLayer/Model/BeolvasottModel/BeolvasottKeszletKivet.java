package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

public class BeolvasottKeszletKivet extends BeolvasottRendelesSzam {

    private double eddigkiadotthossz;
    public double getEddigkiadotthossz() { return this.eddigkiadotthossz; }
    public void setEddigkiadotthossz(double eddigkiadotthossz) { this.eddigkiadotthossz = eddigkiadotthossz; }

    private double utolsoKiadottHossz;
    public double getUtolsoKiadottHossz() { return utolsoKiadottHossz; }
    public void setUtolsoKiadottHossz(double utolsoKiadottHossz) { this.utolsoKiadottHossz = utolsoKiadottHossz; }

    private String utolsoKiadasDatum;
    public String getUtolsoKiadasDatum() { return this.utolsoKiadasDatum; }
    public void setUtolsoKiadasDatum(String utolsoKiadasDatum) { this.utolsoKiadasDatum = utolsoKiadasDatum; }

    public BeolvasottKeszletKivet() { super(); }

    public BeolvasottKeszletKivet(double eddigkiadotthossz, double utolsoKiadottHossz)
    {
        super();
        setEddigkiadotthossz(eddigkiadotthossz);
        setUtolsoKiadottHossz(utolsoKiadottHossz);
    }
}
