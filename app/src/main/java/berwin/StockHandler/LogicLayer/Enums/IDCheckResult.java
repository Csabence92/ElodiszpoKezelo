package berwin.StockHandler.LogicLayer.Enums;

public enum IDCheckResult {
    OK, MarBeolvasva, NemSzovet, NemBeles, Kiadva, NemEgyezoCikksz, NemMegfeloHossz, NemTalalhatoVeg;

    @Override
    public String toString() {
        switch (this)
        {
            case MarBeolvasva: return  "Ez a vég már be lett olvasva!";
            case NemSzovet: return "Ez a vég nem szövet!";
            case NemBeles: return "Ez a vég nem bélés!";
            case Kiadva: return  "A vég már foglalt!";
            case NemEgyezoCikksz: return "Nem egyező cikkszám!";
            case NemTalalhatoVeg: return "Nem találok ilyen véget!";
            case NemMegfeloHossz: return "Nem megfelelő hossz!";
            default: return "";
        }
    }
}
