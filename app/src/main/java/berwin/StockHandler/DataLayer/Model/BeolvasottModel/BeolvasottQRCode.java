package berwin.StockHandler.DataLayer.Model.BeolvasottModel;

public class BeolvasottQRCode extends BeolvasottRendelesSzam {

    private String QRCode;
    public String getQRCode() {
        return this.QRCode;
    }
    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    @Override
    public String toString() {
        return "QRCode: " + QRCode + " Cikkszám: " + getCikkszam() + " Rendelés: "
                + getRendelesSzam() + " Hossz: " + getBeolvasottHossz()
                + "m Szélesség" + getSzelesseg() + "m";
    }
}
