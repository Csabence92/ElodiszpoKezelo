package berwin.StockHandler.PresentationLayer;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.Enums.AlertDialogActions;
import berwin.StockHandler.R;

public class KiszedesActivity extends AppCompatActivity {

    private ControllerKiszedes controllerKiszedes;

    public TextView textViewRendelessszam;
    public TextView textViewKiszedesCikkszam;
    public TextView textViewKiszedesSzuksegesHossz;
    public TextView textViewKiszedesBin;
    public TextView textViewKiszedesHossz;
    public TextView textViewEddigBeolvasottHossz;
    public TextView textViewEddigBeolvasottDB;
    public TextView textViewKiszedesCikkszamText;
    public TextView textViewKiszedesBeolvasottCikkszamText;
    public TextView textViewKiszedesSzelesseg;
    public TextView textViewRendelesiSzelesseg;
    public TextView textViewSzelessegHelyesE;
    public TextView textViewIDHelyesE;
    public TextView textViewKiszedesLokacio;
    public TextView textViewKiszedesKeszletenHossz;
    public TextView textViewKiszedesLezarva;
    public TextView textViewVanAdatBeles;
    public TextView textViewVirtualVegE;
    public TextView textViewConnection;

    public EditText editTextID;

    public Switch szovetBelesSwitch;

    public Spinner spinnerKiszedesCikkszamBeolvasottID;
    public Spinner spinnerKiszedesAnyagBeolvasottID;
    public Spinner spinnerKiszedesBelesek;

    public Button btnKiszedesQRScan;
    public Button btnKiszedesMentesBefejezes;
    public Button btnBeolvasottTorles;
    public Button btnVirtualVegek;
    public Button btnKiszedesMentesLezaras;
    public Button btnVirtualVegMegtekinto;


    public LinearLayout linerLayoutKiszedesSzelesseg;
    public LinearLayout linearLayoutRendelesiSzelesseg;
    public LinearLayout linearLayoutKiszedesMainLay;
    public LinearLayout linearLayoutBelesSpinner;
    public LinearLayout linerLayoutVanVirtVeg;
    public LinearLayout linearLayoutBeolvasottAdatok;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kiszedes);

        textViewRendelessszam = findViewById(R.id.TextViewRendelessszam);
        textViewKiszedesCikkszam = findViewById(R.id.TextViewKiszedesCikkszam);
        textViewKiszedesSzuksegesHossz = findViewById(R.id.TextViewKiszedesSzuksegesHossz);
        textViewKiszedesBin =  findViewById(R.id.TextViewKiszedesBin);
        textViewKiszedesHossz = findViewById(R.id.TextViewKiszedesHossz);
        textViewEddigBeolvasottHossz = findViewById(R.id.TextViewEddigBeolvasottHossz);
        textViewEddigBeolvasottDB = findViewById(R.id.TextViewEddigBeolvasottDB);
        textViewKiszedesCikkszamText = findViewById(R.id.TextViewKiszedesCikkszamText);
        textViewKiszedesBeolvasottCikkszamText = findViewById(R.id.TextViewKiszedesBeolvasottCikkszamText);
        textViewKiszedesSzelesseg = findViewById(R.id.TextViewKiszedesSzelesseg);
        textViewRendelesiSzelesseg = findViewById(R.id.TextViewRendelesiSzelesseg);
        textViewSzelessegHelyesE = findViewById(R.id.TextViewSzelessegHelyesE);
        textViewIDHelyesE = findViewById(R.id.TextViewIDHelyesE);
        textViewKiszedesLokacio = findViewById(R.id.TextViewKiszedesLokacio);
        textViewKiszedesKeszletenHossz = findViewById(R.id.TextViewKiszedesKeszletenHossz);
        textViewKiszedesLezarva = findViewById(R.id.TextViewKiszedesLezarva);
        textViewVanAdatBeles = findViewById(R.id.TextViewVanAdatBeles);
        textViewVirtualVegE = findViewById(R.id.TextViewVirtualVegE);
        textViewConnection = findViewById(R.id.TextViewConnection);

        editTextID = findViewById(R.id.EditTextID);
        editTextID.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 7) {
                    if (szovetBelesSwitch.isChecked()) {
                        controllerKiszedes.runBelesBeolvasasEllenorzes(s.toString());
                    } else {
                        controllerKiszedes.runSzovetBeolvasasEllenorzes(s.toString());
                    }
                } else {
                    textViewIDHelyesE.setText("");
                }
            }
        });

        szovetBelesSwitch = findViewById(R.id.SzovetBelesSwitch);

        spinnerKiszedesCikkszamBeolvasottID = findViewById(R.id.SpinnerKiszedesCikkszamBeolvasottID);
        spinnerKiszedesAnyagBeolvasottID = findViewById(R.id.SpinnerKiszedesAnyagBeolvasottID);
        spinnerKiszedesBelesek = findViewById(R.id.SpinnerKiszedesBelesek);

        btnKiszedesQRScan = findViewById(R.id.btnKiszedesQRScan);
        btnKiszedesQRScan.setOnClickListener(view -> controllerKiszedes.beolvasasDialogBuilder());
        btnKiszedesMentesBefejezes =  findViewById(R.id.btnKiszedesMentesBefejezes);
        btnKiszedesMentesBefejezes.setOnClickListener(view -> controllerKiszedes.kiszedesMentes(true));
        btnBeolvasottTorles = findViewById(R.id.btnBeolvasottTorles);
        btnBeolvasottTorles.setOnClickListener(view -> this.torlesInditas());
        btnVirtualVegek = findViewById(R.id.btnVirtualVegek);
        btnVirtualVegek.setOnClickListener(view -> controllerKiszedes.activityStarter(this, VirtualVegekActivity.class));
        btnKiszedesMentesLezaras = findViewById(R.id.btnKiszedesMentesLezaras);
        btnKiszedesMentesLezaras.setOnClickListener(view -> controllerKiszedes.kiszedesLezaras());
        btnVirtualVegMegtekinto = findViewById(R.id.btnVirtualVegMegtekinto);
        btnVirtualVegMegtekinto.setOnClickListener(v -> btnVirtualVegMegtekintoClicked());


        linerLayoutKiszedesSzelesseg =  findViewById(R.id.LinerLayoutKiszedesSzelesseg);
        linearLayoutRendelesiSzelesseg =  findViewById(R.id.LinearLayoutRendelesiSzelesseg);
        linearLayoutKiszedesMainLay =  findViewById(R.id.LinearLayoutKiszedesMainLay);
        linearLayoutBelesSpinner = findViewById(R.id.LinearLayoutBelesSpinner);
        linerLayoutVanVirtVeg = findViewById(R.id.LinerLayoutVanVirtVeg);
        linearLayoutBeolvasottAdatok = findViewById(R.id.LinearLayoutBeolvasottAdatok);

        controllerKiszedes = ControllerKiszedes.getInstanceControllerKiszedes();
        controllerKiszedes.setKiszedesActivity(this);
        controllerKiszedes.runKiszedes();

        szovetBelesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                switchedToSzovet();
            } else {
                switchedToBeles();
            }
        });

        spinnerKiszedesBelesek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editTextID.setText("");
                controllerKiszedes.clearBeolvasottAdatok();
                controllerKiszedes.setControllersAktualisBeles(spinnerKiszedesBelesek.getSelectedItem().toString());
                controllerKiszedes.runBelesKitoltes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinnerKiszedesCikkszamBeolvasottID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controllerKiszedes.setControllersAktualisBeolvasott(spinnerKiszedesCikkszamBeolvasottID.getSelectedItem().toString(), true);
                controllerKiszedes.runSzovetBeolvasottKitoltes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinnerKiszedesAnyagBeolvasottID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controllerKiszedes.setControllersAktualisBeolvasott(spinnerKiszedesAnyagBeolvasottID.getSelectedItem().toString(), false);
                controllerKiszedes.runBelesBeolvasottKitoltes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kiszedes, menu);
        MenuItem itemTextViewLehetosegek = menu.findItem(R.id.KiszedesMenuTextViewLehetosegek);
        itemTextViewLehetosegek.setActionView(R.layout.actionbar_textview);
        MenuItem itemReconnect = menu.findItem(R.id.KiszedesMenuItemReconnect);
        itemReconnect.setOnMenuItemClickListener(menuItem -> {
            //controllerKiszedes.reconnectSQL();
            return true;
        });
//        MenuItem itemVegszerkeszto = menu.findItem(R.id.KiszedesMenuItemVegszerkeszto);
//        itemVegszerkeszto.setOnMenuItemClickListener(menuItem -> {
//            controllerKiszedes.getMainActivity().beolvasasDialogBuilder(ActivitiesEnum.VegszerkesztoActivity);
//            return false;
//        });
        MenuItem itemVirtVegek = menu.findItem(R.id.KiszedesMenuItemVirtualVegek);
        itemVirtVegek.setOnMenuItemClickListener(menuItem -> {
            controllerKiszedes.activityStarter(this, VirtualVegekActivity.class);
            return false;
        });
        return true;
    }


    private void torlesInditas() {
        if (!szovetBelesSwitch.isChecked()) {
            controllerKiszedes.runSzovetBeolvasottTorles(spinnerKiszedesCikkszamBeolvasottID.getSelectedItem().toString());
        } else {
            controllerKiszedes.runBelesBeolvasottTorles(spinnerKiszedesAnyagBeolvasottID.getSelectedItem().toString());
        }
    }

    private void switchedToBeles() {
        linearLayoutBelesSpinner.setVisibility(View.VISIBLE);
        textViewKiszedesCikkszam.setVisibility(View.GONE);
        textViewKiszedesCikkszamText.setText("Anyagkód:");
        linerLayoutKiszedesSzelesseg.setVisibility(View.GONE);
        spinnerKiszedesCikkszamBeolvasottID.setVisibility(View.GONE);
        spinnerKiszedesAnyagBeolvasottID.setVisibility(View.VISIBLE);
        linearLayoutRendelesiSzelesseg.setVisibility(View.GONE);
        textViewSzelessegHelyesE.setVisibility(View.GONE);
        editTextID.setText("");

        controllerKiszedes.setControllersAktualisBeles(spinnerKiszedesBelesek.getSelectedItem().toString());
        if (spinnerKiszedesAnyagBeolvasottID.getSelectedItem() != null) {
            controllerKiszedes.setControllersAktualisBeolvasott(spinnerKiszedesAnyagBeolvasottID.getSelectedItem().toString(), false);
        }
        controllerKiszedes.runBelesKitoltes();
    }

    private void switchedToSzovet() {
        linearLayoutBelesSpinner.setVisibility(View.GONE);
        textViewKiszedesCikkszam.setVisibility(View.VISIBLE);
        textViewKiszedesCikkszamText.setText("Cikkszám:");
        linerLayoutKiszedesSzelesseg.setVisibility(View.VISIBLE);
        textViewKiszedesSzelesseg.setText("");
        spinnerKiszedesCikkszamBeolvasottID.setVisibility(View.VISIBLE);
        spinnerKiszedesAnyagBeolvasottID.setVisibility(View.GONE);
        linearLayoutRendelesiSzelesseg.setVisibility(View.VISIBLE);
        textViewSzelessegHelyesE.setVisibility(View.VISIBLE);
        editTextID.setText("");

        if (spinnerKiszedesCikkszamBeolvasottID.getSelectedItem() != null) {
            controllerKiszedes.setControllersAktualisBeolvasott(spinnerKiszedesCikkszamBeolvasottID.getSelectedItem().toString(), true);
        }
        controllerKiszedes.runSzovetKitoltes();
    }

    private void btnVirtualVegMegtekintoClicked() {
        if (szovetBelesSwitch.isChecked()) {
            controllerKiszedes.alertDialogBuilder(AlertDialogActions.BelesBeolvasas, null);
        } else {
            controllerKiszedes.alertDialogBuilder(AlertDialogActions.SzovetBeolvasas, null);
        }
    }
}