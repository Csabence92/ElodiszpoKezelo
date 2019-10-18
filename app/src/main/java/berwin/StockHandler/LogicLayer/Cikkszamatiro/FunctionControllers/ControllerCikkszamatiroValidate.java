package berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.ControllerCikkszamatiro;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;

public class ControllerCikkszamatiroValidate extends ControllerCikkszamatiro {

    private static ControllerCikkszamatiroValidate instance;
    public static ControllerCikkszamatiroValidate getInstance(ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak) {
        if (instance == null) {
            instance = new ControllerCikkszamatiroValidate();
        }
        instance.setCikkszamAtiroBeolvasottak(cikkszamAtiroBeolvasottak);
        return instance;
    }

    public IDCheckResult checkBeolvashatoE(BeolvasottLeiras beolvasottUj) {
        if (beolvasottUj != null) {
            return checkEgyezoCikkszamE(beolvasottUj);
        }
        return IDCheckResult.NemTalalhatoVeg;

    }

    @NonNull
    private IDCheckResult checkEgyezoCikkszamE(BeolvasottLeiras beolvasottUj) {
        if (getCikkszamAtiroBeolvasottak().size() == 0 || beolvasottUj.getCikkszam().equals(getCikkszamAtiroBeolvasottak().get(0).getCikkszam())) {
            return checkBeolvasvaE(beolvasottUj);
        } else {
            return IDCheckResult.NemEgyezoCikksz;
        }
    }

    @NonNull
    private IDCheckResult checkBeolvasvaE(BeolvasottLeiras beolvasottUj) {
        if (getCikkszamAtiroBeolvasottak().size() == 0 || !isBeolvasva(beolvasottUj.getId())) {
            return IDCheckResult.OK;
        } else {
            return IDCheckResult.MarBeolvasva;
        }
    }

    private boolean isBeolvasva(String id) {
        for (BeolvasottLeiras i: getCikkszamAtiroBeolvasottak()) {
            if (i.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
