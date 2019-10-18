package berwin.StockHandler.LogicLayer.Szabvissza.FunctionControllers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Szabvissza.ControllerSzabvissza;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.PresentationLayer.SzabvisszaActivity;

public class ControllerSzabvisszaUIWrite extends ControllerSzabvissza implements IRunnable {

    private static ControllerSzabvisszaUIWrite instance;
    public static ControllerSzabvisszaUIWrite getInstance(SzabvisszaActivity szabvisszaActivity, BeolvasottRendelesSzam beolvasottRendelesSzam) {
        if (instance == null) {
            instance = new ControllerSzabvisszaUIWrite();
        }
        instance.setSzabvisszaActivity(szabvisszaActivity);
        instance.setAktualisBeolvasott(beolvasottRendelesSzam);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        szabvisszaAdatokKitoltes();
    }

    @SuppressLint("SetTextI18n")
    public void szabvisszaAdatokKitoltes() {
        if (getAktualisBeolvasott() != null) {
            if (getAktualisBeolvasott().getRendelesSzam() != null && !getAktualisBeolvasott().getRendelesSzam().equals("-")) {
                if (getAktualisBeolvasott().getRendelesSzam().trim().length() != 3) {
                    getSzabvisszaActivity().textViewSzabvisszaRendeles.setTextColor(Color.WHITE);
                    getSzabvisszaActivity().textViewSzabvisszaRendeles.setText(getAktualisBeolvasott().getRendelesSzam());
                    getSzabvisszaActivity().editTextSzabvisszaRendeles.setText(getAktualisBeolvasott().getRendelesSzam());
                } else {
                    getSzabvisszaActivity().textViewSzabvisszaRendeles.setTextColor(Color.WHITE);
                    getSzabvisszaActivity().textViewSzabvisszaRendeles.setText(getAktualisBeolvasott().getLegnagyobbRendeles());
                    getSzabvisszaActivity().editTextSzabvisszaRendeles.setText(getAktualisBeolvasott().getLegnagyobbRendeles());
                }
            } else {
                getSzabvisszaActivity().textViewSzabvisszaRendeles.setTextColor(Color.BLACK);
                getSzabvisszaActivity().textViewSzabvisszaRendeles.setText("Nincs");
            }
            getSzabvisszaActivity().textViewSzabvisszaRendelesAdatai.setText(getAktualisBeolvasott().getId() + "-es" +
                    " vég adatai:");
            getSzabvisszaActivity().textViewSzabvisszaCikkszam.setText(getAktualisBeolvasott().getCikkszam());
            if (getAktualisBeolvasott().getLokacio() != null && !getAktualisBeolvasott().getLokacio().equals("-")) {
                getSzabvisszaActivity().textViewSzabvisszaLokacio.setTextColor(Color.WHITE);
                getSzabvisszaActivity().textViewSzabvisszaLokacio.setText(getAktualisBeolvasott().getLokacio());
            } else {
                getSzabvisszaActivity().textViewSzabvisszaLokacio.setTextColor(Color.BLACK);
                getSzabvisszaActivity().textViewSzabvisszaLokacio.setText("Nincs");
            }

            getSzabvisszaActivity().textViewSzabvisszaRegiHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getBeolvasottHossz()) + "m");
            if (getAktualisBeolvasott().getSzelesseg() != 0) {
                getSzabvisszaActivity().linerLayoutSzabvisszaSzelesseg.setVisibility(View.VISIBLE);
                getSzabvisszaActivity().textViewSzabvisszaSzelesseg.setText(String.valueOf(getAktualisBeolvasott().getSzelesseg()) + "m");
                getSzabvisszaActivity().editTextSzabvisszaSzelesseg.setText(String.valueOf(getAktualisBeolvasott().getSzelesseg()));
            } else {
                getSzabvisszaActivity().linerLayoutSzabvisszaSzelesseg.setVisibility(View.GONE);
            }

        } else {
            MessageBox("Hiba történt az adatmezők kitöltése során! Próbáld újra...");
        }
    }

    @SuppressLint("SetTextI18n")
    public void szabvisszaUjKartyaAdatokKitoltes() {
        if (getAktualisBeolvasott() != null) {
            getSzabvisszaActivity().linearLayoutSzabvisszaUj.setVisibility(View.VISIBLE);
            getSzabvisszaActivity().textViewSzabvisszaUjID.setText(getAktualisBeolvasott().getId());
            getSzabvisszaActivity().textViewSzabvisszaUjCikkszam.setText(getAktualisBeolvasott().getCikkszam());
            if (getAktualisBeolvasott().getRendelesSzam() != null && !getAktualisBeolvasott().getRendelesSzam().equals("-") ) {
                getSzabvisszaActivity().textViewSzabvisszaUjRendeles.setTextColor(Color.WHITE);
                getSzabvisszaActivity().textViewSzabvisszaUjRendeles.setText(getAktualisBeolvasott().getKiadva());
            } else {
                getSzabvisszaActivity().textViewSzabvisszaUjRendeles.setTextColor(Color.BLACK);
                getSzabvisszaActivity().textViewSzabvisszaUjRendeles.setText("Nincs");
            }
            getSzabvisszaActivity().textViewSzabvisszaUjHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getBeolvasottHossz())+ "m");
            if (getAktualisBeolvasott().getSzelesseg() != 0) {
                getSzabvisszaActivity().textViewSzabvisszaUjSzelesseg.setText(getDecimalFormat().format(getAktualisBeolvasott().getSzelesseg())+ "m");
            } else {
                getSzabvisszaActivity().linearLayoutSzabvisszaUjSzelesseg.setVisibility(View.GONE);
            }

        } else {
            MessageBox("Hiba történt az új kártya kitöltése során! Próbáld újra...");
        }
    }
}
