package berwin.StockHandler.LogicLayer.Kiszedes.Beles;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;

public class ControllerKiszedesBelesValidate extends ControllerKiszedes {

    public ControllerKiszedesBelesValidate(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity, Beles aktualisBeles) {
        setAktualisBeles(aktualisBeles);
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    public IDCheckResult ellenorzesIDBeles(String id) {
        if (id.length() == 7) {
            ArrayList<String> ellenorzesEredmeny = getDAO().IDEllenorzes(id);
            return checkLetezoVegE(id, ellenorzesEredmeny);
        } else {
            return IDCheckResult.NemMegfeloHossz;
        }
    }

    @NonNull
    private IDCheckResult checkLetezoVegE(String id, ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny != null) {
            return checkBeolvasvaE(id, ellenorzesEredmeny);
        } else {
            return IDCheckResult.NemTalalhatoVeg;
        }
    }

    @NonNull
    private IDCheckResult checkBeolvasvaE(String id, ArrayList<String> ellenorzesEredmeny) {
        if (!voltBeolvasvaBeles(id)) {
            return checkBeleseE(ellenorzesEredmeny);
        } else {
            return IDCheckResult.MarBeolvasva;
        }
    }

    @NonNull
    private IDCheckResult checkBeleseE(ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny.get(0).toUpperCase().startsWith("T")) {
            return checkSzabadE(ellenorzesEredmeny);
        } else {
            return IDCheckResult.NemBeles;
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
        if (ellenorzesEredmeny.get(0).toUpperCase().equals(getAktualisBeles().getAnyagKod().toUpperCase())) {
            return IDCheckResult.OK;
        } else {
            return IDCheckResult.NemEgyezoCikksz;
        }
    }

    private boolean voltBeolvasvaBeles(String ID) {
        for (Beolvasott i : getAktualisBeles().getBelesBeolvasottak()) {
            if (i.getId().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    private boolean virtualisBelesBeolvasvE(String cikkszam, Beles instance) {
        ArrayList<BeolvasottRendelesSzam> virtualVegek = getDAO().getVirtualVegek(cikkszam, true);
        if (virtualVegek != null) {
            for (Beolvasott i : instance.getBelesBeolvasottak()) {
                for (BeolvasottRendelesSzam j : virtualVegek) {
                    if (i.getId().trim().equals(j.getId())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean belesSzuksegesHosszTullepveE() {
        if (getAktualisBeles() != null) {
            return (getAktualisBeles().getSzuksegesHossz() < getAktualisBeles().getOsszBelesBeolvasott());
        } else {
            return false;
        }
    }

    public boolean vanEBelesVirtalVeg() {
        ArrayList<BeolvasottRendelesSzam> virtualVegek = getDAO().getVirtualVegek(getAktualisBeles().getAnyagKod(), false);
        if (virtualVegek != null) {
            return (getDAO().getVirtualVegek(getAktualisBeles().getAnyagKod(), false).size() > 0);
        } else {
            return false;
        }
    }

    public boolean belesSzuksHosszTullepveErtesithetoE() {
        return getAktualisBeles().getBelesBeolvasottak().size() > 0;
    }
}
