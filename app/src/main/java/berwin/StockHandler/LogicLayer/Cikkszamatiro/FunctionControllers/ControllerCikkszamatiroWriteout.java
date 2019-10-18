package berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.ControllerCikkszamatiro;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.Reversed;
import berwin.StockHandler.PresentationLayer.CikkszamSzerkesztoActivity;
import berwin.StockHandler.R;

public class ControllerCikkszamatiroWriteout extends ControllerCikkszamatiro implements IRunnable {

    private static ControllerCikkszamatiroWriteout instance;
    public static ControllerCikkszamatiroWriteout getInstance(CikkszamSzerkesztoActivity cikkszamSzerkesztoActivity,
                                                              ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak) {
        if (instance == null) {
            instance = new ControllerCikkszamatiroWriteout();
        }
        instance.setCikkszamSzerkesztoActivity(cikkszamSzerkesztoActivity);
        instance.setCikkszamAtiroBeolvasottak(cikkszamAtiroBeolvasottak);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        this.cikkszamAtiroKitoltes(((Parameter<BeolvasottLeiras>) parameters).getElsoParam());
        this.cikkszamAtiroTorlesBtnVisibility();
    }

    @SuppressLint("SetTextI18n")
    public void cikkszamAtiroKitoltes(BeolvasottLeiras beolvasottLeiras) {
        if (beolvasottLeiras != null) {
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottCikkszam.setText(beolvasottLeiras.getCikkszam());
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottLokacio.setText(beolvasottLeiras.getLokacio());
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottBin.setText(beolvasottLeiras.getBin());
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottHossz.setText(getDecimalFormat().format(beolvasottLeiras.getBeolvasottHossz()) + "m");
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottSzelesseg.setText(getDecimalFormat().format(beolvasottLeiras.getSzelesseg()) + "m");
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoBeolvasottDesszin.setText(beolvasottLeiras.getLeiras());
            if (beolvasottLeiras.getCikkszamPar() != null && beolvasottLeiras.getCikkszamPar().equals("Nem találtam!")) {
                getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoUjCikkszam.setTextColor(Color.RED);
            }
            getCikkszamSzerkesztoActivity().textViewCikkszSzerkesztoUjCikkszam.setText(beolvasottLeiras.getCikkszamPar());
        }
    }

    public void fillSpinnerBeolvasottak() {
        ArrayList<String> cikkszamAtiroBeolvasottItems = new ArrayList<>();
        if (getCikkszamAtiroBeolvasottak() != null && getCikkszamAtiroBeolvasottak().size() > 0) {
            for (BeolvasottLeiras i : Reversed.reversed(getCikkszamAtiroBeolvasottak())) {
                cikkszamAtiroBeolvasottItems.add(i.getId());
            }
            getCikkszamSzerkesztoActivity().spinnerCikkszSzerkesztoBeolvasottID.setAdapter(new ArrayAdapter<>(getCikkszamSzerkesztoActivity(),
                    R.layout.item_list, R.id.TxtItem, cikkszamAtiroBeolvasottItems));
        }
    }

    public void cikkszamAtiroTorlesBtnVisibility() {
        if (getCikkszamAtiroBeolvasottak().size() > 0) {
            getCikkszamSzerkesztoActivity().btnCikkszSzerkesztoBeolvasottTorles.setVisibility(View.VISIBLE);
        } else {
            getCikkszamSzerkesztoActivity().btnCikkszSzerkesztoBeolvasottTorles.setVisibility(View.GONE);
        }
    }

    public void beolvasottIdEllenrozesKiiras(IDCheckResult idCheckResult) {
        getCikkszamSzerkesztoActivity().textViewIDHelyesE.setText(idCheckResult.toString());
        getCikkszamSzerkesztoActivity().textViewIDHelyesE.setTextColor(Color.RED);
    }
}
