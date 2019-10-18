package berwin.StockHandler.DataLayer.Model;

public class VegcedulaNaplo {

    private String id;
    public void setID(String id) { this.id = id; }
    public String  getId() { return id; }

    private String mozgas_datum;
    public void setMozgas_datum(String  mozgas_datum) { this.mozgas_datum = mozgas_datum; }
    public String getMozgas_datum() { return mozgas_datum; }

    private double mennyiseg;
    public void setMennyiseg(double mennyiseg) { this.mennyiseg = mennyiseg; }
    public double getMennyiseg() { return mennyiseg; }

    private String nev_regi;
    public void setNev_regi(String nev_regi) { this.nev_regi = nev_regi; }
    public String getNev_regi() { return nev_regi; }

    private String nev_uj;
    public void setNev_uj(String nev_uj) { this.nev_uj = nev_uj; }
    public String getNev_uj() { return nev_uj; }

    private String mozgas_regi;
    public void setMozgas_regi(String mozgas_regi) { this.mozgas_regi = mozgas_regi; }
    public String getMozgas_regi() { return mozgas_regi; }

    private String mozgas_uj;
    public void setMozgas_uj(String mozgas_uj) { this.mozgas_uj = mozgas_uj; }
    public String getMozgas_uj() { return mozgas_uj; }

    private String bin_regi;
    public void setBin_regi(String bin_regi) { this.bin_regi = bin_regi; }
    public String getBin_regi() { return bin_regi; }

    private String bin_uj;
    public void setBin_uj(String bin_uj) { this.bin_uj = bin_uj; }
    public String getBin_uj() { return bin_uj; }

    private String rendeles_regi;
    public void setRendeles_regi(String rendeles_regi) { this.rendeles_regi = rendeles_regi; }
    public String getRendeles_regi() { return rendeles_regi; }

    private String rendeles_uj;
    public void setRendeles_uj(String rendeles_uj) { this.rendeles_uj = rendeles_uj; }
    public String getRendeles_uj() { return rendeles_uj; }

    private String kiadva;
    public void setKiadva(String kiadva) { this.kiadva = kiadva; }
    public String getKiadva() { return kiadva; }

    @Override
    public String toString() {
        return "Dátum: " + mozgas_datum + ", Mennyiség: " + mennyiseg
                + "m\nRégi mozgás: " + mozgas_regi + ", Új mozgás: " + mozgas_uj + "\nRégi rendelés: "
                + rendeles_regi + ", Új rendelés: " + rendeles_uj;
    }
}
