package berwin.StockHandler.LogicLayer.Kiszedes.Szovet;

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
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.Reversed;
import berwin.StockHandler.R;

public class ControllerKiszedesSzovetUIWrite extends ControllerKiszedes implements IRunnable {

    private ArrayList<String> beolvasasSzovetListItems;
    private ArrayAdapter<String> beolvasasSzovetAdapter;

    public ArrayList<String> virtualisSzovetListItems;

    public ControllerKiszedesSzovetUIWrite(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity) {
        beolvasasSzovetListItems = new ArrayList<>();
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameters) {
        if (getAktualisDiszpo() != null) {
            kitoltesSzovetAlapAdatok();
            kitoltesSzovetEddigBeolvasott(((Parameter<Boolean>) parameters).getElsoParam());
            szovetBeolvasottKiiras();
            //vanESzovetVirtualVeg(getAktualisDiszpo().getCikkSzam());
        } else {
            getKiszedesActivity().finish();
            MessageBox("Diszpó letöltése sikertelen volt! Próbáld újra...");
        }
    }

    public void runBeolvasasKitoltes(boolean szuksegehosszTullepveE, boolean ertesiteniKellE) {
        beolvasottSzovetAdatokKitoltes();
        if (szuksegehosszTullepveE && ertesiteniKellE) {
            super.alertDialogBuilder(AlertDialogActions.SzuksegesHosszTullepveSzovet, null);
        }
        kitoltesSzovetEddigBeolvasott(szuksegehosszTullepveE);
        setBeolvasottTorlesBtnVisibility();
    }


    // Kitölti a szükséges hossz mezőt a rendelés cikkszámának megfelelően.
    @SuppressLint("SetTextI18n")
    public void kitoltesSzovetAlapAdatok() {
        if (getAktualisDiszpo() != null) {
            getKiszedesActivity().textViewKiszedesSzuksegesHossz.setText(getDecimalFormat().format(getAktualisDiszpo().getSzuksegesHossz())  + "m");
            getKiszedesActivity().textViewKiszedesLokacio.setText(getAktualisDiszpo().getLokacio());
            getKiszedesActivity().textViewKiszedesKeszletenHossz.setText(getDecimalFormat().format(getAktualisDiszpo().getKeszletenHossz()) + "m");
        }
    }

    @SuppressLint("SetTextI18n")
    public void kitoltesSzovetEddigBeolvasott(boolean szuksegeshosszTullepveE) {
        if (szuksegeshosszTullepveE) {
            getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.GREEN);
            getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.GREEN);
        } else {
            getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.RED);
            getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.RED);
        }
        double osszHosszVirtVegNelkul = getAktualisDiszpo().getOsszCikkszamBeolvasott()- getAktualisDiszpo().getOsszVirtualVegSzovetBeolvasott();
        getKiszedesActivity().textViewEddigBeolvasottHossz.setText(getDecimalFormat().format(getAktualisDiszpo().getOsszCikkszamBeolvasott()) + "m | "
                + getDecimalFormat().format(osszHosszVirtVegNelkul) + "m");
        getKiszedesActivity().textViewEddigBeolvasottDB.setText(String.valueOf(getAktualisDiszpo().getBeolvasottSzovetVegekSzama()) + "db | "
                + getAktualisDiszpo().getBeolvasottVirtualVegekSzovetSzama() + "db");
    }

    public String getVirtualSzovetMessage()
    {
        final ArrayList<BeolvasottRendelesSzam> virtualVegek = getDAO().getVirtualVegek(getAktualisDiszpo().getCikkSzam(), false);

        virtualisSzovetListItems = new ArrayList<>();
        for (Beolvasott i: virtualVegek) {
            virtualisSzovetListItems.add("\nID: " + i.getId() + " Cikkszám: " + i.getCikkszam() + " Szélesség: "
                    + i.getSzelesseg() + "m Hossz: " + i.getBeolvasottHossz() + "m");
        }

        StringBuilder uzenet;
        if (virtualisSzovetListItems.size() == 1)
        {
            uzenet = new StringBuilder("Van virtuális Szövet!\n");
            uzenet.append(virtualisSzovetListItems.get(0));
            uzenet.append("\n\nBeolvassam a virtuális cikkszámot?");
        }
        else
        {
            uzenet = new StringBuilder("Vannak virtuális Szövetek!\n");
            for (int i = 0; i < virtualisSzovetListItems.size(); i++)
            {
                uzenet.append(virtualisSzovetListItems.get(i));
            }
            uzenet.append("\n\nBeolvassam a virtuális cikkszámokat?");
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
            case NemSzovet:
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

    @SuppressLint("SetTextI18n")
    public void beolvasottSzovetAdatokKitoltes()
    {
        if (getAktualisDiszpo().getCikkszamBeolvasott().size() > 0)
        {
            for (Beolvasott i:  Reversed.reversed(getAktualisDiszpo().getCikkszamBeolvasott()))
            {
                beolvasasSzovetListItems.add(i.getId());
            }
            beolvasasSzovetAdapter = new ArrayAdapter<>(getKiszedesActivity(), R.layout.item_list, R.id.TxtItem, beolvasasSzovetListItems);
            beolvasasSzovetListItems = new ArrayList<>();
            getKiszedesActivity().spinnerKiszedesCikkszamBeolvasottID.setAdapter(beolvasasSzovetAdapter);
        }
        else
        {
            getKiszedesActivity().spinnerKiszedesCikkszamBeolvasottID.setAdapter(null);
            getKiszedesActivity().textViewKiszedesBin.setText("");
            getKiszedesActivity().textViewKiszedesSzelesseg.setText("");
            getKiszedesActivity().textViewKiszedesHossz.setText("");
            getKiszedesActivity().textViewEddigBeolvasottHossz.setTextColor(Color.RED);
            getKiszedesActivity().textViewEddigBeolvasottDB.setTextColor(Color.RED);
            getKiszedesActivity().textViewEddigBeolvasottHossz.setText(getDecimalFormat().format(0) + "m");
            getKiszedesActivity().textViewEddigBeolvasottDB.setText(getDecimalFormat().format(0) + "db");
            getKiszedesActivity().textViewSzelessegHelyesE.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    public void szovetBeolvasottKiiras()
    {
        if (getAktualisBeolvasott() != null) {
            if (getAktualisDiszpo().getSzelesseg() != getAktualisBeolvasott().getSzelesseg())
            {
                getKiszedesActivity().textViewSzelessegHelyesE.setText("Nem egyeznek a szélességek!");
                getKiszedesActivity().textViewSzelessegHelyesE.setTextColor(Color.RED);
            }
            else
            {
                getKiszedesActivity().textViewSzelessegHelyesE.setText("Egyező szélesség!");
                getKiszedesActivity().textViewSzelessegHelyesE.setTextColor(Color.GREEN);
            }
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
