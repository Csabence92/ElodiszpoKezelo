package berwin.StockHandler.LogicLayer.Kiszedes.Szovet;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.Enums.SzelessegCheckResult;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;

public class ControllerKiszedesSzovetValidate extends ControllerKiszedes implements IRunnable{

    public ControllerKiszedesSzovetValidate(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity) {
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable paramable) {
        //Validate
    }

    @SuppressLint("SetTextI18n")
    public IDCheckResult ellenorzesIDSzovet(String id) {
        if (id.length() == 7)
        {
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
        if (!voltBeolvasvaCikkszam(id, getAktualisDiszpo().getCikkszamBeolvasott())) {
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
        if (ellenorzesEredmeny.get(1) == null || ellenorzesEredmeny.get(1).isEmpty() || ellenorzesEredmeny.get(1).equals("-")) {
            return checkCikkszam(ellenorzesEredmeny);
        } else {
            return IDCheckResult.Kiadva;
        }
    }

    @NonNull
    private IDCheckResult checkCikkszam(ArrayList<String> ellenorzesEredmeny) {
        if (ellenorzesEredmeny.get(0).toUpperCase().equals(getKiszedesActivity().textViewKiszedesCikkszam.getText().toString().toUpperCase())) {
            return IDCheckResult.OK;
        } else {
            return IDCheckResult.NemEgyezoCikksz;
        }
    }

    private boolean voltBeolvasvaCikkszam(String ID, List<Beolvasott> beolvasottak)
    {
        return beolvasottak.stream().filter(x -> x.getId().equals(ID)).findFirst().orElse(null) != null;
    }

    public boolean szovetSzuksegesHosszTullepveE() {
        if (getAktualisDiszpo() != null) {
            return !(getAktualisDiszpo().getSzuksegesHossz() > getAktualisDiszpo().getOsszCikkszamBeolvasott());
        }
        return false;
    }

    public boolean vanESzovetVirtualVeg(String cikkszam) {
        ArrayList<BeolvasottRendelesSzam> virtualVegek = getDAO().getVirtualVegek(cikkszam, false);
        if (virtualVegek != null) {
            return (getDAO().getVirtualVegek(cikkszam, false).size() > 0);
        } else {
            return false;
        }
    }

    public SzelessegCheckResult szelessegEllenorzes(final String ellenorizendoID)
    {
        int beolvasottSzelesseg = getDAO().getSzelessegByID(ellenorizendoID);
        if (beolvasottSzelesseg > 0) {
            if (getAktualisDiszpo().getSzelesseg() < beolvasottSzelesseg) {
                return SzelessegCheckResult.Nagyobb;
            } else if (getAktualisDiszpo().getSzelesseg() > beolvasottSzelesseg) {
                return SzelessegCheckResult.Kisebb;
            } else{
                return SzelessegCheckResult.Egyenlo;
            }
        }
        return null;
    }

    public boolean szovetSzuksHosszTullepveErtesithetoE() {
        return getAktualisDiszpo().getCikkszamBeolvasott().size() > 0;
    }
}
