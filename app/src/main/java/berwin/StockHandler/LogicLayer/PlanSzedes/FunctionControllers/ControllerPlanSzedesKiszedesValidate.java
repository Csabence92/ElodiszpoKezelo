package berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;

public class ControllerPlanSzedesKiszedesValidate extends ControllerPlanSzedes implements IRunnable {

    private static ControllerPlanSzedesKiszedesValidate instance;
    public static ControllerPlanSzedesKiszedesValidate getInstance(Szovet aktualisSzovet) {
        if (instance == null) {
            instance = new ControllerPlanSzedesKiszedesValidate();
        }
        instance.setAktualisSzovet(aktualisSzovet);
        return instance;
    }
    @Override
    public void run(IParamable paramable) {
        //Validate
    }

    @SuppressLint("SetTextI18n")
    public IDCheckResult ellenorzesIDSzovet(String id) {
        if (id.length() == 7) {
            ArrayList<String> ellenorzesEredmeny = getDAO().IDEllenorzes(id);
            return checkLetezoVegE(id, ellenorzesEredmeny);
        }  else {
            return IDCheckResult.NemMegfeloHossz;
        }
    }

    @NonNull
    private IDCheckResult checkLetezoVegE(String id, ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny != null) {
            return checkBeolvasvaE(id, ellenorzesEredmeny);
        } else {
            return  IDCheckResult.NemTalalhatoVeg;
        }
    }

    @NonNull
    private IDCheckResult checkBeolvasvaE(String id, ArrayList<String> ellenorzesEredmeny) {
        if (!voltBeolvasvaCikkszam(id, getAktualisSzovet().getSzovetBeolvasottak())) {
            return checkSzovetE(ellenorzesEredmeny);
        } else {
            return IDCheckResult.MarBeolvasva;
        }
    }

    @NonNull
    private IDCheckResult checkSzovetE(ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny.get(0).toUpperCase().startsWith("C")) {
            return checkSzabadE(ellenorzesEredmeny);
        } else {
            return IDCheckResult.NemSzovet;
        }
    }

    @NonNull
    private IDCheckResult checkSzabadE(ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny.get(1) == null || ellenorzesEredmeny.get(1).isEmpty() || ellenorzesEredmeny.get(1).equals("-") || ellenorzesEredmeny.get(3).equals("Szabvissza")) {
            return checkCikkszam(ellenorzesEredmeny);
        } else {
            return IDCheckResult.Kiadva;
        }
    }

    @NonNull
    private IDCheckResult checkCikkszam(ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny.get(0).toUpperCase().equals(getAktualisSzovet().getCikkszam())) {
            return IDCheckResult.OK;
        } else {
            return IDCheckResult.NemEgyezoCikksz;
        }
    }

    private boolean voltBeolvasvaCikkszam(String ID, ArrayList<Beolvasott> beolvasottak)
    {
        return beolvasottak.stream().filter(x -> x.getId().equals(ID)).findFirst().orElse(null) != null;
    }

    public boolean szovetSzuksegesHosszTullepveE() {
        if (getAktualisSzovet() != null) {
            return !(getAktualisSzovet().getSzuksegesHossz() > getAktualisSzovet().getOsszSzovetBeolvasottHossz());
        }
        return false;
    }

    public boolean szovetSzuksHosszTullepveErtesithetoE() {
        return getAktualisSzovet().getSzovetBeolvasottak().size() > 0;
    }

}
