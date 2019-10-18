package berwin.StockHandler.DataLayer.Enums;

import android.graphics.Color;

public enum ServerState {

    Online, Offline;

    @Override
    public String toString() {
        switch (this) {
            case Online: return "A szerver elérhető!";
            default: return "A szerver nem elérhető!";
        }
    }

    public int toColor() {
        switch (this) {
            case Online: return Color.GREEN;
            default: return Color.RED;
        }
    }
}
