package berwin.StockHandler.LogicLayer.Enums;

public enum  OrderStatus {

    NincsAdat, VanAdat, Lezarva;

    @Override
    public String toString() {
        switch (this) {
            case VanAdat: return  "Ehez a rendeléshez van mentett adat!";
            case NincsAdat: return "A rendelés kiszedésre vár!";
            case Lezarva: return "Ez a diszpó le van zárva!";
        }
        return null;
    }
}


