package berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.ControllerCikkszamatiro;

public class ControllerCikkszamatiroDelete extends ControllerCikkszamatiro {

    private static ControllerCikkszamatiroDelete instance;
    public static ControllerCikkszamatiroDelete getInstance(ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak) {
        if (instance == null) {
            instance = new ControllerCikkszamatiroDelete();
        }
        instance.setCikkszamAtiroBeolvasottak(cikkszamAtiroBeolvasottak);
        return instance;
    }

    public boolean cikkszamAtiroBeolvasottTorles(String id)
    {
        return getCikkszamAtiroBeolvasottak().remove(getCikkszamAtiroBeolvasottTorlendo(id) != -1);
    }

    private int getCikkszamAtiroBeolvasottTorlendo(String id) {
        int torlendo = 0;
        for (BeolvasottLeiras i: getCikkszamAtiroBeolvasottak()) {
            if (i.getId().equals(id)) {
                return torlendo;
            } else {
                torlendo++;
            }
        }
        return -1;
    }

}
