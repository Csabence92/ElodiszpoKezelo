package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Szabvissza.ControllerSzabvissza;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.R;

public class SzabvisszaActivity extends AppCompatActivity {

    public EditText editViewSzabviszaUjHossz;
    public EditText editTextSzabvisszaSzelesseg;
    public EditText editTextSzabvisszaRendeles;

    public Switch szelJavSwitch;
    public Switch rendelesJavSwitch;

    public LinearLayout linearLayoutSzabvisszaUj;
    public LinearLayout linerLayoutSzabvisszaSzelesseg;
    public LinearLayout linearLayoutSzabvisszaUjSzelesseg;

    public TextView textViewSzabivisszaConnection;
    public TextView textViewSzabvisszaRendelesAdatai;
    public TextView textViewSzabvisszaCikkszam;
    public TextView textViewSzabvisszaLokacio;
    public TextView textViewSzabvisszaRegiHossz;
    public TextView textViewSzabvisszaSzelesseg;
    public TextView textViewSzabvisszaRendeles;

    public TextView textViewSzabvisszaUjID;
    public TextView textViewSzabvisszaUjCikkszam;
    public TextView textViewSzabvisszaUjRendeles;
    public TextView textViewSzabvisszaUjSzelesseg;
    public TextView textViewSzabvisszaUjHossz;

    private ControllerSzabvissza controllerSzabvissza;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_szabivssza);

        Button btnSzabvisszaUj = findViewById(R.id.btnSzabvisszaUj);
        btnSzabvisszaUj.setOnClickListener(view -> ujBtnClicked());
        Button btnSzabvisszaMehet = findViewById(R.id.btnSzabvisszaMehet);
        btnSzabvisszaMehet.setOnClickListener(view -> mehetBtnClicked());

        editViewSzabviszaUjHossz = findViewById(R.id.EditViewSzabviszaUjHossz);
        editTextSzabvisszaSzelesseg = findViewById(R.id.EditTextSzabvisszaSzelesseg);
        editTextSzabvisszaRendeles = findViewById(R.id.EditTextSzabvisszaRendeles);

        szelJavSwitch = findViewById(R.id.SzelJavSwitch);
        szelJavSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editTextSzabvisszaSzelesseg.setVisibility(View.VISIBLE);
                textViewSzabvisszaSzelesseg.setVisibility(View.GONE);
            }
            else {
                editTextSzabvisszaSzelesseg.setVisibility(View.GONE);
                textViewSzabvisszaSzelesseg.setVisibility(View.VISIBLE);
            }
        });

        rendelesJavSwitch = findViewById(R.id.RendelesJavSwitch);
        rendelesJavSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editTextSzabvisszaRendeles.setVisibility(View.VISIBLE);
                textViewSzabvisszaRendeles.setVisibility(View.GONE);
            } else {
                editTextSzabvisszaRendeles.setVisibility(View.GONE);
                textViewSzabvisszaRendeles.setVisibility(View.VISIBLE);
            }
        });

        linearLayoutSzabvisszaUj = findViewById(R.id.LinearLayoutSzabvisszaUj);
        linerLayoutSzabvisszaSzelesseg = findViewById(R.id.LinerLayoutSzabvisszaSzelesseg);
        linearLayoutSzabvisszaUjSzelesseg = findViewById(R.id.LinearLayoutSzabvisszaUjSzelesseg);

        textViewSzabivisszaConnection = findViewById(R.id.TextViewSzabivisszaConnection);
        textViewSzabvisszaRendelesAdatai = findViewById(R.id.TextViewSzabvisszaRendelesAdatai);
        textViewSzabvisszaCikkszam = findViewById(R.id.TextViewSzabvisszaCikkszam);
        textViewSzabvisszaLokacio = findViewById(R.id.TextViewSzabvisszaBin);
        textViewSzabvisszaRegiHossz = findViewById(R.id.TextViewSzabvisszaHossz);
        textViewSzabvisszaSzelesseg = findViewById(R.id.TextViewSzabvisszaSzelesseg);
        textViewSzabvisszaRendeles = findViewById(R.id.TextViewSzabvisszaRendeles);

        textViewSzabvisszaUjID = findViewById(R.id.TextViewSzabvisszaUjID);
        textViewSzabvisszaUjCikkszam = findViewById(R.id.TextViewSzabvisszaUjCikkszam);
        textViewSzabvisszaUjRendeles = findViewById(R.id.TextViewSzabvisszaUjRendeles);
        textViewSzabvisszaUjSzelesseg = findViewById(R.id.TextViewSzabvisszaUjSzelesseg);
        textViewSzabvisszaUjHossz = findViewById(R.id.TextViewSzabvisszaUjHossz);

        controllerSzabvissza = ControllerSzabvissza.getInstance();
        controllerSzabvissza.setSzabvisszaActivity(this);
        controllerSzabvissza.run(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_szabvissza, menu);
        MenuItem itemTextViewLehetosegek = menu.findItem(R.id.SzabvisszaMenuTextViewLehetosegek);
        itemTextViewLehetosegek.setActionView(R.layout.actionbar_textview);
        MenuItem itemReconnect = menu.findItem(R.id.SzabvisszaMenuItemReconnect);
        itemReconnect.setOnMenuItemClickListener(menuItem -> {
            //controllerSzabvissza.reconnectSQL();
            return true;
        });
        MenuItem itemUjLokacio = menu.findItem(R.id.SzabvisszaMenuUjLokacio);
        itemUjLokacio.setOnMenuItemClickListener(menuItem -> {
            ujLokacioItemClicked();
            return false;
        });
        return true;
    }

    private void ujLokacioItemClicked() {
        controllerSzabvissza.ujLokacioAlertDialogBuilder(this);
    }

    private void mehetBtnClicked() {
        if  (!editViewSzabviszaUjHossz.getText().toString().equals("")) {
            Double ujHossz;
            if (editViewSzabviszaUjHossz.getText().toString().contains(","))
                ujHossz = Double.parseDouble(editViewSzabviszaUjHossz.getText().toString().replace(",","."));
            else
                ujHossz = Double.parseDouble(editViewSzabviszaUjHossz.getText().toString());

            if (szelJavSwitch.isChecked()) {
                controllerSzabvissza.getAktualisBeolvasott().setSzelesseg(Double.parseDouble(editTextSzabvisszaSzelesseg.getText().toString()));
            }

            if  (rendelesJavSwitch.isChecked()) {
                controllerSzabvissza.getAktualisBeolvasott().setRendelesSzam(editTextSzabvisszaRendeles.getText().toString());
            }

            controllerSzabvissza.getAktualisBeolvasott().setBeolvasottHossz(ujHossz);
            if (controllerSzabvissza.getAktualisBeolvasott().getRendelesSzam() == null) controllerSzabvissza.getAktualisBeolvasott().setRendelesSzam("");

            controllerSzabvissza.alertDialogBuilder(ActivitiesEnum.SzabvisszaActivity, null);

        } else {
            controllerSzabvissza.MessageBox("Nem adtad meg az új hosszt!");
        }
    }

    private void ujBtnClicked() {
        this.finish();
        linearLayoutSzabvisszaUj.setVisibility(View.GONE);
        controllerSzabvissza.getMainActivity().beolvasasDialogBuilder(ActivitiesEnum.SzabvisszaActivity);
    }

//    @SuppressLint("SetTextI18n")
//    public void szabvisszaAdatokKitoltes()
//    {
//        BeolvasottRendelesSzam szabvisszaBeolvasott = getControllerMain().getSzabvisszaBeolvasott();
//        if (szabvisszaBeolvasott != null) {
//            if (szabvisszaBeolvasott.getRendelesSzam() != null && !szabvisszaBeolvasott.getRendelesSzam().equals("-")) {
//                if (szabvisszaBeolvasott.getRendelesSzam().trim().length() != 3) {
//                    textViewSzabvisszaRendeles.setTextColor(Color.WHITE);
//                    textViewSzabvisszaRendeles.setText(szabvisszaBeolvasott.getRendelesSzam());
//                    editTextSzabvisszaRendeles.setText(szabvisszaBeolvasott.getRendelesSzam());
//                } else {
//                    textViewSzabvisszaRendeles.setTextColor(Color.WHITE);
//                    textViewSzabvisszaRendeles.setText(szabvisszaBeolvasott.getLegnagyobbRendeles());
//                    editTextSzabvisszaRendeles.setText(szabvisszaBeolvasott.getLegnagyobbRendeles());
//                }
//            } else {
//                textViewSzabvisszaRendeles.setTextColor(Color.BLACK);
//                textViewSzabvisszaRendeles.setText("Nincs");
//            }
//            textViewSzabvisszaRendelesAdatai.setText(szabvisszaBeolvasott.getId() + "-es" +
//                    " vég adatai:");
//            textViewSzabvisszaCikkszam.setText(szabvisszaBeolvasott.getCikkszam());
//            if (szabvisszaBeolvasott.getLokacio() != null && !szabvisszaBeolvasott.getLokacio().equals("-")) {
//                textViewSzabvisszaLokacio.setTextColor(Color.WHITE);
//                textViewSzabvisszaLokacio.setText(szabvisszaBeolvasott.getLokacio());
//            } else {
//                textViewSzabvisszaLokacio.setTextColor(Color.BLACK);
//                textViewSzabvisszaLokacio.setText("Nincs");
//            }
//
//            textViewSzabvisszaRegiHossz.setText(getControllerMain().getDecimalFormat().format(szabvisszaBeolvasott.getBeolvasottHossz()) + "m");
//            if (szabvisszaBeolvasott.getSzelesseg() != 0) {
//                linerLayoutSzabvisszaSzelesseg.setVisibility(View.VISIBLE);
//                textViewSzabvisszaSzelesseg.setText(String.valueOf(szabvisszaBeolvasott.getSzelesseg()) + "m");
//                editTextSzabvisszaSzelesseg.setText(String.valueOf(szabvisszaBeolvasott.getSzelesseg()));
//            } else {
//                linerLayoutSzabvisszaSzelesseg.setVisibility(View.GONE);
//            }
//
//        } else {
//            getControllerMain().MessageBox("Hiba történt az adatmezők kitöltése során! Próbáld újra...");
//        }
//
//    }
//
//    @SuppressLint("SetTextI18n")
//    public void szabvisszaUjKartyaAdatokKitoltes()
//    {
//        getControllerMain().setSzabvisszaBeolvasott(getControllerMain().getSzabvisszaBeolvasott().getId());
//
//        BeolvasottRendelesSzam szabvisszaUjKartyaBeolvasott = getControllerMain().getSzabvisszaBeolvasott();
//        if (szabvisszaUjKartyaBeolvasott != null) {
//            linearLayoutSzabvisszaUj.setVisibility(View.VISIBLE);
//            textViewSzabvisszaUjID.setText(controller.getSzabvisszaBeolvasott().getId());
//            textViewSzabvisszaUjCikkszam.setText(controller.getSzabvisszaBeolvasott().getCikkszam());
//            if (szabvisszaUjKartyaBeolvasott.getRendelesSzam() != null && !szabvisszaUjKartyaBeolvasott.getRendelesSzam().equals("-") ) {
//                textViewSzabvisszaUjRendeles.setTextColor(Color.WHITE);
//                textViewSzabvisszaUjRendeles.setText(controller.getSzabvisszaBeolvasott().getKiadva());
//            } else {
//                textViewSzabvisszaUjRendeles.setTextColor(Color.BLACK);
//                textViewSzabvisszaUjRendeles.setText("Nincs");
//            }
//            textViewSzabvisszaUjHossz.setText(controller.getDecimalFormat().format(controller.getSzabvisszaBeolvasott().getBeolvasottHossz())+ "m");
//            if (szabvisszaUjKartyaBeolvasott.getSzelesseg() != 0) {
//                textViewSzabvisszaUjSzelesseg.setText(controller.getDecimalFormat().format(controller.getSzabvisszaBeolvasott().getSzelesseg())+ "m");
//            } else {
//                linearLayoutSzabvisszaUjSzelesseg.setVisibility(View.GONE);
//            }
//
//        } else {
//            getControllerMain().MessageBox("Hiba történt az új kártya kitöltése során! Próbáld újra...");
//        }
//
//    }
}
