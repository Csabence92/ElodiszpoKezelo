package berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.PresentationLayer.PlanSzedesKiszedesActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.Reversed;
import berwin.StockHandler.R;

public class ControllerPlanSzedesKiszedesUIWrite extends ControllerPlanSzedes implements IRunnable {

    private static ControllerPlanSzedesKiszedesUIWrite instance;
    public static ControllerPlanSzedesKiszedesUIWrite getInstance(Szovet aktualisSzovet, Plan aktualisPlan, PlanSzedesKiszedesActivity planSzedesKiszedesActivity, MainActivity mainActivity) {
        if (instance == null) {
            instance = new ControllerPlanSzedesKiszedesUIWrite();
        }
        instance.setAktualisSzovet(aktualisSzovet);
        instance.setPlanSzedesKiszedesActivity(planSzedesKiszedesActivity);
        instance.setMainActivity(mainActivity);
        instance.setAktualisPlan(aktualisPlan);
        return instance;
    }

    private ArrayList<String> beolvasasSzovetListItems;
    private ArrayAdapter<String> beolvasasSzovetAdapter;

    private ControllerPlanSzedesKiszedesUIWrite() {
        beolvasasSzovetListItems = new ArrayList<>();
    }

    @Override
    public void run(IParamable parameters) {
        if (getAktualisSzovet() != null) {
            kitoltesSzovetAlapAdatok();
            kitoltesSzovetEddigBeolvasott(((Parameter<Boolean>) parameters).getElsoParam());
        } else {
            getPlanSzedesKiszedesActivity().finish();
            MessageBox("Plan letöltése sikertelen volt! Próbáld újra...");
        }
    }

    public void runBeolvasasKitoltes(boolean szuksegehosszTullepveE, boolean ertesiteniKellE) {
        kitoltesSzovetEddigBeolvasott(szuksegehosszTullepveE);
        if (szuksegehosszTullepveE && ertesiteniKellE) {
            //alertDialogBuilder(null,null);
            mentesPlanSzedes();
        }
        kitoltesSzovetEddigBeolvasott(szuksegehosszTullepveE);
        setBeolvasottTorlesBtnVisibility();
    }

    @SuppressLint("SetTextI18n")
    public void kitoltesSzovetAlapAdatok() {
        if (getAktualisSzovet() != null) {
            getPlanSzedesKiszedesActivity().textViewPlanSzedesSzuksegesHossz.setText(getDecimalFormat().format(getAktualisSzovet().getSzuksegesHossz())  + "m");
            getPlanSzedesKiszedesActivity().textViewPlanSzedesBin.setText(getAktualisSzovet().getBin());
            getPlanSzedesKiszedesActivity().textViewPlanSzedesKeszletenHossz.setText(getDecimalFormat().format(getAktualisSzovet().getKeszletenHossz()) + "m");
        }
    }

    @SuppressLint("SetTextI18n")
    public void ellenorzesEredmenyKiiro(IDCheckResult idCheckResult) {
        switch (idCheckResult) {
            case MarBeolvasva:
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setText(idCheckResult.toString());
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setTextColor(Color.rgb(247, 74, 0));
                break;
            case NemSzovet:
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setText(idCheckResult.toString());
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setTextColor(Color.CYAN);
                break;
            case Kiadva:
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setText(idCheckResult.toString());
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setTextColor(Color.rgb(255, 0, 0));
                break;
            case NemEgyezoCikksz:
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setText(idCheckResult.toString());
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setTextColor(Color.rgb(255, 0, 0));
                break;
            case NemTalalhatoVeg:
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setText(idCheckResult.toString());
                getPlanSzedesKiszedesActivity().textViewPlanSzedesIDHelyesE.setTextColor(Color.rgb(166, 196, 0));
                break;
            case OK:
                getPlanSzedesKiszedesActivity().editTextPlanSzedesID.setText("");
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void kitoltesSzovetEddigBeolvasott(boolean szuksegeshosszTullepveE) {
        if (getAktualisSzovet().getSzovetBeolvasottak() != null && getAktualisSzovet().getSzovetBeolvasottak().size() > 0) {
            for (Beolvasott i:  Reversed.reversed(getAktualisSzovet().getSzovetBeolvasottak()))
            {
                beolvasasSzovetListItems.add(i.getId());
            }
            beolvasasSzovetAdapter = new ArrayAdapter<>(getPlanSzedesKiszedesActivity(), R.layout.item_list, R.id.TxtItem, beolvasasSzovetListItems);
            beolvasasSzovetListItems = new ArrayList<>();
            getPlanSzedesKiszedesActivity().spinnerPlanSzedesBeolvasottID.setAdapter(beolvasasSzovetAdapter);
        }
        if (szuksegeshosszTullepveE) {
            getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottHossz.setTextColor(Color.GREEN);
            getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottDB.setTextColor(Color.GREEN);
        } else {
            getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottHossz.setTextColor(Color.RED);
            getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottDB.setTextColor(Color.RED);
        }
        getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottHossz.setText(getDecimalFormat().format(getAktualisSzovet().getOsszSzovetBeolvasottHossz()) + "m");
        getPlanSzedesKiszedesActivity().textViewPlanSzedesEddigBeolvasottDB.setText(String.valueOf(getAktualisSzovet().getSzovetBeolvasottVegekSzama()) + "db");
    }

    @SuppressLint("SetTextI18n")
    public void szovetBeolvasottKiiras() {
        if (getAktualisSzovet() != null) {
            getPlanSzedesKiszedesActivity().textViewPlanSzedesBeolvasottBin.setText(getAktualisBeolvasott().getLokacio());
            getPlanSzedesKiszedesActivity().textViewPlanSzedesBeolvasottHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getBeolvasottHossz()) + "m");
            getPlanSzedesKiszedesActivity().TextViewPlanSzedesBeolvasottSzelesseg.setText(getDecimalFormat().format(getAktualisBeolvasott().getSzelesseg()) + "m");
        }
    }
}
