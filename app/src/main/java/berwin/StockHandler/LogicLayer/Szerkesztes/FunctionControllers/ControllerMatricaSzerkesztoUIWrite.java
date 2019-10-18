package berwin.StockHandler.LogicLayer.Szerkesztes.FunctionControllers;

import android.annotation.SuppressLint;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottQRCode;
import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.PresentationLayer.MatricaSzerkesztoActivity;

public class ControllerMatricaSzerkesztoUIWrite extends ControllerSzerkeszto implements IRunnable {
    public static ControllerMatricaSzerkesztoUIWrite instance;
    public static ControllerMatricaSzerkesztoUIWrite getInstance(MatricaSzerkesztoActivity matricaSzerkesztoActivity,
                                                                 BeolvasottQRCode aktualisBeolvasottQRCode) {
        if (instance == null) {
            instance = new ControllerMatricaSzerkesztoUIWrite();
        }
        instance.setMatricaSzerkesztoActivity(matricaSzerkesztoActivity);
        instance.setAktualisBeolvasottQRCode(aktualisBeolvasottQRCode);
        return instance;
    }

    @SuppressLint("SetTextI18n")
    public void fillMatirca() {
        if (getAktualisBeolvasottQRCode() != null) {
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoHossz.setText(getDecimalFormat().format(getAktualisBeolvasottQRCode().getBeolvasottHossz()) + "m");
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoSzelesseg.setText(String.valueOf(getAktualisBeolvasottQRCode().getSzelesseg()) + "m");
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoRendeles.setText(getAktualisBeolvasottQRCode().getRendelesSzam());
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoCikkszam.setText(getAktualisBeolvasottQRCode().getCikkszam());
            getMatricaSzerkesztoActivity().textViewmatricaSzerkesztoQRCode.setText("QR Code:\n" + getAktualisBeolvasottQRCode().getQRCode());

            getMatricaSzerkesztoActivity().editTextMatricaSzerkesztoHossz.setText(String.valueOf(getAktualisBeolvasottQRCode().getBeolvasottHossz()));
            getMatricaSzerkesztoActivity().editTextMatricaSzerkesztoSzelesseg.setText(String.valueOf(getAktualisBeolvasottQRCode().getSzelesseg()));
            getMatricaSzerkesztoActivity().editTextMatricaSzerkesztoRendeles.setText(getAktualisBeolvasottQRCode().getRendelesSzam());
            getMatricaSzerkesztoActivity().editTextMatricaSzerkesztoCikkszam.setText(getAktualisBeolvasottQRCode().getCikkszam());

            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoHossz.setChecked(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoSzelesseg.setChecked(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoRendeles.setChecked(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoCikkszam.setChecked(false);
        } else {
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoHossz.setText("Nem található!");
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoSzelesseg.setText("Nem található!");
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoRendeles.setText("Nem található!");
            getMatricaSzerkesztoActivity().textViewMatricaSzerkesztoCikkszam.setText("Nem található!");
            getMatricaSzerkesztoActivity().textViewmatricaSzerkesztoQRCode.setText("Nem található!");

            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoHossz.setClickable(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoSzelesseg.setClickable(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoRendeles.setClickable(false);
            getMatricaSzerkesztoActivity().switchMatricaSzerkesztoCikkszam.setClickable(false);
        }
    }
}
