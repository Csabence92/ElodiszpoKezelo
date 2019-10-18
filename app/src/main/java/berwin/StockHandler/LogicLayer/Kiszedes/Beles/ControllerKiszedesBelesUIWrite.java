package berwin.StockHandler.LogicLayer.Kiszedes.Beles;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.Enums.AlertDialogActions;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.Reversed;
import berwin.StockHandler.R;

public class ControllerKiszedesBelesUIWrite extends ControllerKiszedes implements IRunnable {

    private ArrayList<String> beolvasasBelesListItems;

    public ArrayList<String> virtualisBelesekListItems;

    public ControllerKiszedesBelesUIWrite(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity, Beles aktualisBeles) {
        beolvasasBelesListItems = new ArrayList<>();
        setAktualisBeles(aktualisBeles);
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameter) {
        if (getAktualisBeles() != null) {
            kitoltesBelesAlapAdatok();
            kitoltesBelesEddigBeolvasott(((Parameter<Boolean>) parameter).getElsoParam());
            kitoltesMentveE();
            valasztottBeolvasottAdatKitoltes();
        }
    }

    public void runBeolvasasKitoltes(boolean szuksegeshosszTullepveE, boolean ertesiteniKellE) {
        valasztottBeolvasottAdatKitoltes();
        if (szuksegeshosszTullepveE && ertesiteniKellE) {
            super.alertDialogBuilder(AlertDialogActions.SzuksegesHosszTullepveBeles, null);
        }
        kitoltesBelesEddigBeolvasott(szuksegeshosszTullepveE);
        kitoltesMentveE();
        setBeolvasottTorlesBtnVisibility();
    }

    @SuppressLint("SetTextI18n")
    public void kitoltesBelesAlapAdatok()
    {
        if (getAktualisBeles() != null) {
            getKiszedesActivity().textViewKiszedesSzuksegesHossz.setText(getDecimalFormat().format(getAktualisBeles().getSzuksegesHossz())+ "m");
            getKiszedesActivity().textViewKiszedesLokacio.setText(getAktualisBeles().getLokacio());
            getKiszedesActivity().textViewKiszedesKeszletenHossz.setText(getDecimalFormat().format(getAktualisBeles().getKeszletenHossz()) + "m");
        }
    }

    @SuppressLint("SetTextI18n")
    public void kitoltesBelesEddigBeolvasott(boolean szuksegesHosszTullepveE) {
        if (szuksegesHosszTullepveE) {
            getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.GREEN);
            getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.GREEN);
        } else {
            getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.RED);
            getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.RED);
        }
        //getKiszedesActivity().textViewEddigBeolvasottHossz.setText(getDecimalFormat().format(getAktualisBeles().getOsszBelesBeolvasott()) + "m | ");
        //getKiszedesActivity().textViewEddigBeolvasottDB.setText(String.valueOf(getAktualisBeles().getBelesBeolvasottDB()) + "db | " + getAktualisBeles().getVirtualisVegDB() + "db");
        double osszHosszVirtVegNelkul = getAktualisBeles().getOsszBelesBeolvasott()- getAktualisBeles().getOsszVirtualVegBelesBeolvasott();
        getKiszedesActivity().textViewEddigBeolvasottHossz.setText(getDecimalFormat().format(getAktualisBeles().getOsszBelesBeolvasott()) + "m | "
                + getDecimalFormat().format(osszHosszVirtVegNelkul) + "m");
        getKiszedesActivity().textViewEddigBeolvasottDB.setText(String.valueOf(getAktualisBeles().getBelesBeolvasottDB()) + "db | "
                + getAktualisBeles().getBeolvasottVirtualVegekBelesSzama() + "db");
    }

    public void kitoltesMentveE() {
        if (!getAktualisBeles().isMentve()) {
            getKiszedesActivity().textViewVanAdatBeles.setVisibility(View.VISIBLE);
        } else {
            getKiszedesActivity().textViewVanAdatBeles.setVisibility(View.GONE);
        }
    }

    public String getVirtualBelesekMessage()
    {
        final ArrayList<BeolvasottRendelesSzam> virtualVegek = getDAO().getVirtualVegek(getAktualisBeles().getAnyagKod(), false);

        virtualisBelesekListItems = new ArrayList<>();
        for (BeolvasottRendelesSzam i : virtualVegek) {
            virtualisBelesekListItems.add("\nID: " + i.getId() + " Cikkszám: " + i.getCikkszam() + " Rendelésszám: " + i.getRendelesSzam() + " Szélesség: "
                    + i.getSzelesseg() + "m Hossz: " + i.getBeolvasottHossz() + "m");
        }

        StringBuilder uzenet;
        if (virtualisBelesekListItems.size() == 1) {
            uzenet = new StringBuilder("Van virtuális Bélés!\n");
            for (int i = 0; i < virtualisBelesekListItems.size(); i++) {
                uzenet.append(virtualisBelesekListItems.get(i));
            }
            uzenet.append("\n\nBeolvassam a virtuális bélést?");
        } else {
            uzenet = new StringBuilder("Vannak virtuális Bélések!\n");
            for (int i = 0; i < virtualisBelesekListItems.size(); i++) {
                uzenet.append(virtualisBelesekListItems.get(i));
            }
            uzenet.append("\n\nBeolvassam a virtuális béléseket?");
        }

        return uzenet.toString();
    }

    @SuppressLint("SetTextI18n")
    public void ellenorzesEredmenyKiiro(IDCheckResult idCheckResult)
    {
        switch (idCheckResult) {
            case MarBeolvasva:
                getKiszedesActivity().textViewIDHelyesE.setText(idCheckResult.toString());
                getKiszedesActivity().textViewIDHelyesE.setTextColor(Color.rgb(247, 74, 0));
                break;
            case NemBeles:
                getKiszedesActivity().textViewIDHelyesE.setText(idCheckResult.toString());
                getKiszedesActivity().textViewIDHelyesE.setTextColor(Color.CYAN);
                break;
            case Kiadva:
                getKiszedesActivity().textViewIDHelyesE.setText(idCheckResult.toString());
                getKiszedesActivity().textViewIDHelyesE.setTextColor(Color.rgb(255, 0, 0));
                break;
            case NemEgyezoCikksz:
                getKiszedesActivity().textViewIDHelyesE.setText(idCheckResult.toString());
                getKiszedesActivity().textViewIDHelyesE.setTextColor(Color.rgb(255, 0, 0));
                break;
            case NemTalalhatoVeg:
                getKiszedesActivity().textViewIDHelyesE.setText(idCheckResult.toString());
                getKiszedesActivity().textViewIDHelyesE.setTextColor(Color.rgb(166, 196, 0));
                break;
            case OK:
                getKiszedesActivity().editTextID.setText("");
                break;
        }
    }

    private void valasztottBeolvasottAdatKitoltes() {
        if (getAktualisBeles() != null && getAktualisBeles().getBelesBeolvasottak().size() != 0) {
            for (Beolvasott i : Reversed.reversed(getAktualisBeles().getBelesBeolvasottak())) {
                beolvasasBelesListItems.add(i.getId());
            }
            ArrayAdapter<String> beolvasasBelesAdapter = new ArrayAdapter<>(getKiszedesActivity(), R.layout.item_list, R.id.TxtItem, beolvasasBelesListItems);
            beolvasasBelesListItems = new ArrayList<>();
            getKiszedesActivity().spinnerKiszedesAnyagBeolvasottID.setAdapter(beolvasasBelesAdapter);

            if (getAktualisBeles().getBelesBeolvasottak().size() > 0) {
                Beolvasott beolvasott = getBelesBeolvasott(getAktualisBeles(), getKiszedesActivity().spinnerKiszedesAnyagBeolvasottID.getSelectedItem().toString());
                getKiszedesActivity().textViewKiszedesSzelesseg.setText(getDecimalFormat().format(beolvasott.getSzelesseg()));
                getKiszedesActivity().textViewKiszedesBin.setText(beolvasott.getLokacio());
                getKiszedesActivity().textViewKiszedesHossz.setText(getDecimalFormat().format(beolvasott.getBeolvasottHossz()));
            }
        } else {
            getKiszedesActivity().spinnerKiszedesAnyagBeolvasottID.setAdapter(null);
            getKiszedesActivity().textViewKiszedesBin.setText("");
            getKiszedesActivity().textViewKiszedesHossz.setText("");
            //getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.RED);
            //getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.RED);
            getKiszedesActivity().textViewSzelessegHelyesE.setText("");
        }
    }

    // ID Spinnerből kitölti a beolvasott adatokat. (Bélés)
    @SuppressLint("SetTextI18n")
    public void belesBeolvasottAdatKiiras() {
        if (getAktualisBeolvasott() != null) {
            getKiszedesActivity().textViewKiszedesSzelesseg.setText(getDecimalFormat().format(getAktualisBeolvasott().getSzelesseg()) + "m");
            getKiszedesActivity().textViewKiszedesBin.setText(getAktualisBeolvasott().getLokacio());
            getKiszedesActivity().textViewKiszedesHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getBeolvasottHossz()) + "m");
            if (getAktualisBeolvasott().isVirtualVegE()) {
                getKiszedesActivity().textViewVirtualVegE.setVisibility(View.VISIBLE);
            } else {
                getKiszedesActivity().textViewVirtualVegE.setVisibility(View.GONE);
            }
        }
    }
}
