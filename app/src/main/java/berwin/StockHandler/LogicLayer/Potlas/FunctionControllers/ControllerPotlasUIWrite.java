package berwin.StockHandler.LogicLayer.Potlas.FunctionControllers;

import android.annotation.SuppressLint;
import android.view.View;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Potlas.ControllerPotlas;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.PresentationLayer.PotlasActivity;

public class ControllerPotlasUIWrite extends ControllerPotlas implements IRunnable {

    private static ControllerPotlasUIWrite instance;
    public static ControllerPotlasUIWrite getInstance(PotlasActivity potlasActivity, BeolvasottKiadottHossz potlasBeolvasott) {
        if (instance == null) {
            instance = new ControllerPotlasUIWrite();
        }
        instance.setPotlasActivity(potlasActivity);
        instance.setAktualisBeolvasott(potlasBeolvasott);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        this.potlasAdatokKitoltes();
    }

    @SuppressLint("SetTextI18n")
    private void potlasAdatokKitoltes() {
        if (getAktualisBeolvasott() != null) {
            getPotlasActivity().textViewPotlasRendelesAdatai.setText(getAktualisBeolvasott().getId() + " vég adatai:");
            getPotlasActivity().textViewPotlasCikkszam.setText(getAktualisBeolvasott().getCikkszam());
            getPotlasActivity().textViewPotlasSzelesseg.setText(String.valueOf(getAktualisBeolvasott().getSzelesseg()) + "m");
            getPotlasActivity().textViewPotlasBin.setText(getAktualisBeolvasott().getBin());
            getPotlasActivity().textViewPotlasHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getBeolvasottHossz()) + "m");
        } else {
            MessageBox("Vég letöltése sikertelen! Próbáld újra...");
            getPotlasActivity().finish();
        }
    }

    @SuppressLint("SetTextI18n")
    public void potlasMatricaKitoltes() {
        if (getAktualisBeolvasott() != null) {
            getPotlasActivity().potlasMatricaLinearLayout.setVisibility(View.VISIBLE);
            getPotlasActivity().textViewPotlasQRCode.setText("QR Kód:\n" + getAktualisBeolvasott().getQRCode());
            getPotlasActivity().textViewPotlasMatricaCikkszam.setText(getAktualisBeolvasott().getCikkszam());
            getPotlasActivity().textViewPotlasMatricaRendeles.setText(getAktualisBeolvasott().getLeiras());
            getPotlasActivity().textViewPotlasMatricaSzelesseg.setText(String.valueOf(getAktualisBeolvasott().getSzelesseg()) + "m");
            getPotlasActivity().textViewPotlasMatricaHossz.setText(getDecimalFormat().format(getAktualisBeolvasott().getKiadottHossz()) + "m");
        }
    }
}
