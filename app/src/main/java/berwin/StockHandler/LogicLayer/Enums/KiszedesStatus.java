package berwin.StockHandler.LogicLayer.Enums;

import android.graphics.Color;

public enum  KiszedesStatus {

    NincsAdat, MentveHosszabb, MentveRovidebb;

    @Override
    public String toString() {
        switch (this) {
            case MentveHosszabb: return "Teljesen kiszedve!";
            case MentveRovidebb: return "Kiszedve, kevesebb hosszal!";
            default: return "Kiszedésre vár!";
        }
    }

    public int toInt() {
        switch (this) {
            case MentveHosszabb: return 1;
            case MentveRovidebb: return 2;
            default: return 0;
        }
    }

    public int toColor() {
        switch (this) {
            case MentveHosszabb: return Color.GREEN;
            case MentveRovidebb: return Color.RED;
            default: return Color.WHITE;
        }
    }
}
