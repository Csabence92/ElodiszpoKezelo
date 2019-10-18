package berwin.StockHandler.PresentationLayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Enums.KeszletKivetEnum;
import berwin.StockHandler.LogicLayer.KeszletKivet.ControllerKeszletKivet;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.R;

public class KeszletKivetActivity extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout layoutKeszletKivetRendelesSzam;
    public ListView listViewKeszletKivetAdatok;

    public Switch switchKeszletKivetListaUj;

    public Spinner spinnerKeszletKivetCikkszam;

    public TextView textViewKeszletKivetLokacio;
    public TextView textViewKeszletKivetRaktaronHossz;
    public TextView textViewKeszletKivetEddigKiadottHossz;
    public TextView textViewKeszletKivetConnection;

    public EditText editTextKeszletKivetKiadottHossz;
    public EditText editTextKeszletKivetRendelesSzam;
    public EditText editTextKeszletKivetCikkszam;

    public Button btnKeszletKivetOK;
    public Button btnKeszletKivetKereses;

    private ControllerKeszletKivet controllerKeszletKivet;

    private AsyncLoading asyncLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_keszletkivet);

        layoutKeszletKivetRendelesSzam = findViewById(R.id.LayoutKeszletKivetRendelesSzam);
        listViewKeszletKivetAdatok = findViewById(R.id.ListViewKeszletKivetAdatok);
        listViewKeszletKivetAdatok.setOnItemClickListener((parent, view, position, arg3) -> {
            view.setSelected(true);
            String seged = (String) listViewKeszletKivetAdatok.getItemAtPosition(position);
            asyncLoading = new AsyncLoading(seged.substring(10, seged.indexOf("Ki")).trim(), KeszletKivetActivity.this, ActivitiesEnum.KeszletKivetActivity, KeszletKivetEnum.JavitasKitoltes);
            asyncLoading.execute();
        });

        switchKeszletKivetListaUj = findViewById(R.id.SwitchKeszletKivetListaUj);
        switchKeszletKivetListaUj.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                spinnerKeszletKivetCikkszam.setVisibility(View.GONE);
                editTextKeszletKivetCikkszam.setVisibility(View.VISIBLE);
                layoutKeszletKivetRendelesSzam.setVisibility(View.VISIBLE);
                btnKeszletKivetKereses.setVisibility(View.VISIBLE);
                textViewKeszletKivetLokacio.setText("");
                textViewKeszletKivetRaktaronHossz.setText("");
                textViewKeszletKivetEddigKiadottHossz.setText("");
            } else {
                spinnerKeszletKivetCikkszam.setVisibility(View.VISIBLE);
                editTextKeszletKivetCikkszam.setVisibility(View.GONE);
                layoutKeszletKivetRendelesSzam.setVisibility(View.GONE);
                btnKeszletKivetKereses.setVisibility(View.GONE);
                textViewKeszletKivetLokacio.setText("");
                textViewKeszletKivetRaktaronHossz.setText("");
                textViewKeszletKivetEddigKiadottHossz.setText("");
            }
        });

        spinnerKeszletKivetCikkszam = findViewById(R.id.SpinnerKeszletKivetCikkszam);
        spinnerKeszletKivetCikkszam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                asyncLoading = new AsyncLoading(spinnerKeszletKivetCikkszam.getSelectedItem().toString(),
                        KeszletKivetActivity.this, ActivitiesEnum.KeszletKivetActivity, KeszletKivetEnum.MainKitoltes);
                asyncLoading.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        textViewKeszletKivetLokacio = findViewById(R.id.TextViewKeszletKivetLokacio);
        textViewKeszletKivetRaktaronHossz = findViewById(R.id.TextViewKeszletKivetRaktaronHossz);
        textViewKeszletKivetEddigKiadottHossz = findViewById(R.id.TextViewKeszletKivetEddigKiadottHossz);
        textViewKeszletKivetConnection = findViewById(R.id.TextViewKeszletKivetConnection);

        editTextKeszletKivetKiadottHossz = findViewById(R.id.EditTextKeszletKivetKiadottHossz);
        editTextKeszletKivetRendelesSzam = findViewById(R.id.EditTextKeszletKivetRendelesSzam);
        editTextKeszletKivetCikkszam = findViewById(R.id.EditTextKeszletKivetCikkszam);

        btnKeszletKivetOK = findViewById(R.id.btnKeszletKivetOK);
        btnKeszletKivetOK.setOnClickListener(this);
        btnKeszletKivetKereses = findViewById(R.id.btnKeszletKivetKereses);
        btnKeszletKivetKereses.setOnClickListener(this);

        controllerKeszletKivet = ControllerKeszletKivet.getInstance();
        controllerKeszletKivet.setKeszletKivetActivity(this);
        controllerKeszletKivet.run(null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        String meter;
        if (v == btnKeszletKivetOK) {
            if (switchKeszletKivetListaUj.isChecked()) {
                if (!editTextKeszletKivetCikkszam.getText().toString().isEmpty() && !editTextKeszletKivetKiadottHossz.getText().toString().isEmpty()) {
                    if (editTextKeszletKivetKiadottHossz.getText().toString().contains(",")) {
                        meter = editTextKeszletKivetKiadottHossz.getText().toString().replace(",", ".");
                    } else {
                        meter = editTextKeszletKivetKiadottHossz.getText().toString();
                    }
                    controllerKeszletKivet.getBeolvasottKeszletKivet().setCikkszam(editTextKeszletKivetCikkszam.getText().toString().toUpperCase());
                    controllerKeszletKivet.getBeolvasottKeszletKivet().setUtolsoKiadottHossz(Double.parseDouble(meter));
                    controllerKeszletKivet.getBeolvasottKeszletKivet().setRendelesSzam(editTextKeszletKivetRendelesSzam.getText().toString());
                    controllerKeszletKivet.befejezesAdatFeltoltesKeszletKivet();
                    controllerKeszletKivet.run(null);
                } else {
                    controllerKeszletKivet.MessageBox("Nincs elég adat a feltöltéshez!");
                }
            } else {
                if (!editTextKeszletKivetKiadottHossz.getText().toString().isEmpty()) {
                    if (editTextKeszletKivetKiadottHossz.getText().toString().contains(",")) {
                        meter = editTextKeszletKivetKiadottHossz.getText().toString().replace(",", ".");
                    } else {
                        meter = editTextKeszletKivetKiadottHossz.getText().toString();
                    }
                    controllerKeszletKivet.getBeolvasottKeszletKivet().setCikkszam(spinnerKeszletKivetCikkszam.getSelectedItem().toString());
                    controllerKeszletKivet.getBeolvasottKeszletKivet().setUtolsoKiadottHossz(Double.parseDouble(meter));
                    controllerKeszletKivet.befejezesAdatFeltoltesKeszletKivet();
                    controllerKeszletKivet.getControllerKeszletKivetUIWrite().listViewAdatokKitoltes();
                } else {
                    controllerKeszletKivet.MessageBox("Nincs elég adat a feltöltéshez!");
                }
            }
            editTextKeszletKivetKiadottHossz.setText("");
        } else if (v == btnKeszletKivetKereses) {
            textViewKeszletKivetLokacio.setText("");
            textViewKeszletKivetRaktaronHossz.setText("");
            asyncLoading = new AsyncLoading(editTextKeszletKivetCikkszam.getText().toString().toUpperCase(), KeszletKivetActivity.this, ActivitiesEnum.KeszletKivetActivity, KeszletKivetEnum.Kereses);
            asyncLoading.execute();
        }
    }

//    @SuppressLint("SetTextI18n")
//    public void keszletKivetKeresesKitoltes() {
//        if (controllerKeszletKivet.getBeolvasottKeszletKivet() != null) {
//            controllerKeszletKivet.MessageBox("A(z) " + editTextKeszletKivetCikkszam.getText().toString().toUpperCase() + " létezik!");
//            adatokKitoltesKeszletKivet();
//        } else {
//            controllerKeszletKivet.MessageBox("A(z) " + editTextKeszletKivetCikkszam.getText().toString().toUpperCase() + " NEM létezik!");
//            textViewKeszletKivetLokacio.setText("Nem található!");
//            textViewKeszletKivetRaktaronHossz.setText("Nem található!");
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    public void adatokKitoltesKeszletKivet() {
//        BeolvasottKeszletKivet beolvasottKeszletKivet = controllerKeszletKivet.getBeolvasottKeszletKivet();
//        if (beolvasottKeszletKivet != null) {
//            textViewKeszletKivetLokacio.setText(beolvasottKeszletKivet.getLokacio());
//            textViewKeszletKivetRaktaronHossz.setText(String.valueOf(beolvasottKeszletKivet.getBeolvasottHossz()) + "m");
//            textViewKeszletKivetEddigKiadottHossz.setText(String.valueOf(beolvasottKeszletKivet.getEddigkiadotthossz()) + "m");
//        } else {
//            controllerKeszletKivet.MessageBox("Adatok lekérése sikertelen! Próbáld újra...");
//            textViewKeszletKivetLokacio.setText("Nincs");
//            textViewKeszletKivetRaktaronHossz.setText("Nincs");
//            textViewKeszletKivetEddigKiadottHossz.setText("Nincs");
//        }
//    }
}
