package berwin.StockHandler.LogicLayer.KeszletKivet;

import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKeszletKivet;
import berwin.StockHandler.PresentationLayer.KeszletKivetActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.R;

public class ControllerKeszletKivetUIWrite extends ControllerKeszletKivet {

    private static ControllerKeszletKivetUIWrite instance;
    public static ControllerKeszletKivetUIWrite getInstance(KeszletKivetActivity keszletKivetActivity,
                                                            BeolvasottKeszletKivet beolvasottKeszletKivet,
                                                            MainActivity mainActivity) {
        if (instance == null) {
            instance = new ControllerKeszletKivetUIWrite();
        }
        instance.setKeszletKivetActivity(keszletKivetActivity);
        instance.setBeolvasottKeszletKivet(beolvasottKeszletKivet);
        instance.setMainActivity(mainActivity);
        return instance;
    }

    private ArrayList<String> keszletKivetCikkszamSpinnerItems;
    private ArrayList<String> listViewAdatokItems;

    public void spinnerCikkszamKitoltes() {
        keszletKivetCikkszamSpinnerItems = getDAO().getSpinnerCikkszamok();
        getKeszletKivetActivity().spinnerKeszletKivetCikkszam.setAdapter(new ArrayAdapter<>(getKeszletKivetActivity(), R.layout.item_list, R.id.TxtItem, keszletKivetCikkszamSpinnerItems));
    }

    public void listViewAdatokKitoltes() {
        listViewAdatokItems = getDAO().getListViewAdatok();
        getKeszletKivetActivity().listViewKeszletKivetAdatok.setAdapter(new ArrayAdapter<>(getKeszletKivetActivity(), android.R.layout.simple_expandable_list_item_1, listViewAdatokItems));
    }

    @SuppressLint("SetTextI18n")
    public void keszletKivetKeresesKitoltes() {
        if (getBeolvasottKeszletKivet() != null) {
            MessageBox("A(z) " +  getKeszletKivetActivity().editTextKeszletKivetCikkszam.getText().toString().toUpperCase() + " létezik!");
            adatokKitoltesKeszletKivet();
        } else {
            MessageBox("A(z) " +  getKeszletKivetActivity().editTextKeszletKivetCikkszam.getText().toString().toUpperCase() + " NEM létezik!");
            getKeszletKivetActivity().textViewKeszletKivetLokacio.setText("Nem található!");
            getKeszletKivetActivity().textViewKeszletKivetRaktaronHossz.setText("Nem található!");
        }
    }

    @SuppressLint("SetTextI18n")
    public void adatokKitoltesKeszletKivet() {
        if (getBeolvasottKeszletKivet() != null) {
            getKeszletKivetActivity().textViewKeszletKivetLokacio.setText(getBeolvasottKeszletKivet().getLokacio());
            getKeszletKivetActivity().textViewKeszletKivetRaktaronHossz.setText(String.valueOf(getBeolvasottKeszletKivet().getBeolvasottHossz()) + "m");
            getKeszletKivetActivity().textViewKeszletKivetEddigKiadottHossz.setText(String.valueOf(getBeolvasottKeszletKivet().getEddigkiadotthossz()) + "m");
        } else {
            MessageBox("Adatok lekérése sikertelen! Próbáld újra...");
            getKeszletKivetActivity().textViewKeszletKivetLokacio.setText("Nincs");
            getKeszletKivetActivity().textViewKeszletKivetRaktaronHossz.setText("Nincs");
            getKeszletKivetActivity().textViewKeszletKivetEddigKiadottHossz.setText("Nincs");
        }
    }
}
