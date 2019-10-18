package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

public class BeolvasottLeiras extends Beolvasott {

    private String leiras;
    public void setLeiras(String leiras) { this.leiras = leiras; }
    public String getLeiras() { return leiras; }

    private String cikkszamPar;
    public void setCikkszamPar(String cikkszamPar) { this.cikkszamPar = cikkszamPar; }
    public String getCikkszamPar() { return cikkszamPar; }

    public BeolvasottLeiras(String leiras, String cikkszamPar)
    {
        super();
        setLeiras(leiras);
        setCikkszamPar(cikkszamPar);
    }

    public BeolvasottLeiras() { super(); }
}
