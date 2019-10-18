package berwin.StockHandler.LogicLayer.Szerkesztes.FunctionControllers;

import android.annotation.SuppressLint;
import android.graphics.Color;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottTeljes;
import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.PresentationLayer.VegszerkesztoActivity;

public class ControllerVegSzerkesztoUIWrite extends ControllerSzerkeszto implements IRunnable {

    private static ControllerVegSzerkesztoUIWrite instance;
    public static ControllerVegSzerkesztoUIWrite getInstance(VegszerkesztoActivity vegszerkesztoActivity, BeolvasottTeljes beolvasottTeljes) {
        if (instance == null) {
            instance = new ControllerVegSzerkesztoUIWrite();
        }
        instance.setVegszerkesztoActivity(vegszerkesztoActivity);
        instance.setAktualisBeolvasottTeljes(beolvasottTeljes);
        return instance;
    }

    @SuppressLint("SetTextI18n")
    public void fillVegszerkeszto() {
        if (getAktualisBeolvasottTeljes() != null) {
            getVegszerkesztoActivity().textViewRendelesSzerkesztoAdatai.setText(getAktualisBeolvasottTeljes().getId() + "-es vég adatai:");

            getVegszerkesztoActivity().textViewSzerkesztoCikkszam.setText(getAktualisBeolvasottTeljes().getCikkszam());
            getVegszerkesztoActivity().editTextSzerkesztoCikkszam.setText(getAktualisBeolvasottTeljes().getCikkszam());

            if ((getAktualisBeolvasottTeljes().getBin() != null) && !getAktualisBeolvasottTeljes().getBin().isEmpty()
                    && !getAktualisBeolvasottTeljes().getBin().equals("-")) {
                getVegszerkesztoActivity().textViewSzerkesztoBin.setText(getAktualisBeolvasottTeljes().getBin());
                getVegszerkesztoActivity().editTextSzerkesztoBin.setText(getAktualisBeolvasottTeljes().getBin());
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoBin.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoBin.setTextColor(Color.BLACK);
            }

            if ((getAktualisBeolvasottTeljes().getLokacio() != null) && !getAktualisBeolvasottTeljes().getLokacio().isEmpty()
                    && !getAktualisBeolvasottTeljes().getLokacio().equals("-")) {
                getVegszerkesztoActivity().textViewSzerkesztoLokacio.setText(getAktualisBeolvasottTeljes().getLokacio());
                getVegszerkesztoActivity().editTextSzerkesztoLokacio.setText(getAktualisBeolvasottTeljes().getLokacio());
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoLokacio.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoLokacio.setTextColor(Color.BLACK);
            }

            if ((getAktualisBeolvasottTeljes().getSzelesseg() != 0) && getAktualisBeolvasottTeljes().getSzelesseg() != 0.0) {
                getVegszerkesztoActivity().textViewSzerkesztoSzelesseg.setText(String.valueOf(getAktualisBeolvasottTeljes().getSzelesseg()) + "m");
                getVegszerkesztoActivity().editTextSzerkesztoSzelesseg.setText(String.valueOf(getAktualisBeolvasottTeljes().getSzelesseg()));
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoSzelesseg.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoSzelesseg.setTextColor(Color.BLACK);
            }

            if ((getAktualisBeolvasottTeljes().getBeolvasottHossz() != 0) && getAktualisBeolvasottTeljes().getBeolvasottHossz() != 0.0) {
                getVegszerkesztoActivity().textViewSzerkesztoHossz.setText(String.valueOf(getAktualisBeolvasottTeljes().getBeolvasottHossz()) + "m");
                getVegszerkesztoActivity().editTextSzerkesztoHossz.setText(String.valueOf(getAktualisBeolvasottTeljes().getBeolvasottHossz()));
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoHossz.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoHossz.setTextColor(Color.BLACK);
            }

            getVegszerkesztoActivity().textViewSzerkesztoBevNap.setText(getAktualisBeolvasottTeljes().getBevNap());
            getVegszerkesztoActivity().editTextSzerkesztoBevNap.setText(getAktualisBeolvasottTeljes().getBevNap());

            if ((getAktualisBeolvasottTeljes().getVevo() != null) && !getAktualisBeolvasottTeljes().getVevo().isEmpty()
                    && !getAktualisBeolvasottTeljes().getVevo().equals("-")) {
                getVegszerkesztoActivity().textViewSzerkesztoVevo.setText(getAktualisBeolvasottTeljes().getVevo());
                getVegszerkesztoActivity().editTextSzerkesztoVevo.setText(getAktualisBeolvasottTeljes().getVevo());
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoVevo.setText("Nincs");
                getVegszerkesztoActivity(). textViewSzerkesztoVevo.setTextColor(Color.BLACK);
            }

            if ((getAktualisBeolvasottTeljes().getAllapot() != null) && !getAktualisBeolvasottTeljes().getAllapot().isEmpty()
                    && !getAktualisBeolvasottTeljes().getAllapot().equals("-")) {
                getVegszerkesztoActivity().textViewSzerkesztoAllapot.setText(getAktualisBeolvasottTeljes().getAllapot());
                getVegszerkesztoActivity().editTextSzerkesztoAllapot.setText(getAktualisBeolvasottTeljes().getAllapot());
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoAllapot.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoAllapot.setTextColor(Color.BLACK);
            }

            if ((getAktualisBeolvasottTeljes().getRendelesSzam() != null) && !getAktualisBeolvasottTeljes().getRendelesSzam().isEmpty()
                    && !getAktualisBeolvasottTeljes().getRendelesSzam().equals("-")) {
                getVegszerkesztoActivity().textViewSzerkesztoRendeles.setText(getAktualisBeolvasottTeljes().getRendelesSzam());
                getVegszerkesztoActivity().editTextSzerkesztoRendeles.setText(getAktualisBeolvasottTeljes().getRendelesSzam());
            } else {
                getVegszerkesztoActivity().textViewSzerkesztoRendeles.setText("Nincs");
                getVegszerkesztoActivity().textViewSzerkesztoRendeles.setTextColor(Color.BLACK);
            }

            getVegszerkesztoActivity().textViewSzerkesztoNev.setText(getAktualisBeolvasottTeljes().getNev());
            getVegszerkesztoActivity().editTextSzerkesztoNev.setText(getAktualisBeolvasottTeljes().getNev());

            getVegszerkesztoActivity().textViewSzerkesztoMozgas.setText(getAktualisBeolvasottTeljes().getMozgas());
            getVegszerkesztoActivity().editTextSzerkesztoMozgas.setText(getAktualisBeolvasottTeljes().getMozgas());

            getVegszerkesztoActivity().switchSzerkesztoCikkszam.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoBin.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoLokacio.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoSzelesseg.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoHossz.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoBevNap.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoVevo.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoAllapot.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoRendeles.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoNev.setChecked(false);
            getVegszerkesztoActivity().switchSzerkesztoMozgas.setChecked(false);
        } else {
            MessageBox("Vég letöltése sikertelen! Próbáld újra...");
            getMainActivity().beolvasasDialogBuilder(ActivitiesEnum.VegszerkesztoActivity);
            getVegszerkesztoActivity().finish();
        }
    }
}
