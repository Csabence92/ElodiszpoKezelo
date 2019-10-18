package berwin.StockHandler.LogicLayer.Enums;

import android.graphics.Color;

public enum PlanStatus {

    Nyitott, Zart;

    @Override
    public String toString() {
        switch (this) {
            case Zart: return "Állapot: Zárt";
            default: return "Állapot: Nyitott";
        }
    }

    public int toColor() {
        switch (this) {
            case Zart: return Color.RED;
            default: return Color.GREEN;
        }
    }
}
