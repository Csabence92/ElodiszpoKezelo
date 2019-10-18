package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Cikkszamatiro.ControllerCikkszamatiro;
import berwin.StockHandler.R;

public class CikkszamSzerkesztoActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView textViewCikkszSzerkesztoBeolvasottCikkszam;
    public TextView textViewCikkszSzerkesztoBeolvasottLokacio;
    public TextView textViewCikkszSzerkesztoBeolvasottBin;
    public TextView textViewCikkszSzerkesztoBeolvasottHossz;
    public TextView textViewCikkszSzerkesztoBeolvasottSzelesseg;
    public TextView textViewCikkszSzerkesztoBeolvasottDesszin;
    public TextView textViewCikkszSzerkesztoUjCikkszam;
    public TextView textViewCikkszSzerkesztoBeolvasottUjLokacioAdat;
    public TextView textViewCikkszSzerkesztoBeolvasottLokacioAdat;
    public TextView textViewConnection;
    public TextView textViewIDHelyesE;

    public EditText editTextCikkszSzerkesztoID;

    public Button btnCikkszSzerkesztoQRScan;
    public Button btnCikkszSzerkesztoBeolvasottTorles;
    public Button btnCikkszSzerkesztoBefejezes;

    public Spinner spinnerCikkszSzerkesztoBeolvasottID;
    public Spinner spinnerCikkszSzerkesztoUjLokacio;

    public Switch switchCikkszSzerkesztoLokacio;

    private ControllerCikkszamatiro controllerCikkszamatiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cikkszamszerkeszto);

        textViewCikkszSzerkesztoBeolvasottCikkszam = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottCikkszam);
        textViewCikkszSzerkesztoBeolvasottLokacio = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottLokacio);
        textViewCikkszSzerkesztoBeolvasottBin = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottBin);
        textViewCikkszSzerkesztoBeolvasottHossz = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottHossz);
        textViewCikkszSzerkesztoBeolvasottSzelesseg = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottSzelesseg);
        textViewCikkszSzerkesztoBeolvasottDesszin = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottDesszin);
        textViewCikkszSzerkesztoUjCikkszam = findViewById(R.id.TextViewCikkszSzerkesztoUjCikkszam);
        textViewCikkszSzerkesztoBeolvasottUjLokacioAdat = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottUjLokacioAdat);
        textViewCikkszSzerkesztoBeolvasottLokacioAdat = findViewById(R.id.TextViewCikkszSzerkesztoBeolvasottLokacioAdat);
        textViewConnection = findViewById(R.id.TextViewConnection);
        textViewIDHelyesE = findViewById(R.id.TextViewIDHelyesE);

        editTextCikkszSzerkesztoID = findViewById(R.id.EditTextCikkszSzerkesztoID);
        editTextCikkszSzerkesztoID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (text.length() == 7) {
                    cikkszamAtiroIDBeolvasas(text.toString());
                }
            }
        });

        btnCikkszSzerkesztoQRScan = findViewById(R.id.btnCikkszSzerkesztoQRScan);
        btnCikkszSzerkesztoQRScan.setOnClickListener(this);
        btnCikkszSzerkesztoBeolvasottTorles = findViewById(R.id.btnCikkszSzerkesztoBeolvasottTorles);
        btnCikkszSzerkesztoBeolvasottTorles.setOnClickListener(this);
        btnCikkszSzerkesztoBefejezes = findViewById(R.id.btnCikkszSzerkesztoBefejezes);
        btnCikkszSzerkesztoBefejezes.setOnClickListener(this);

        spinnerCikkszSzerkesztoBeolvasottID = findViewById(R.id.SpinnerCikkszSzerkesztoBeolvasottID);
        spinnerCikkszSzerkesztoBeolvasottID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controllerCikkszamatiro.runBeolvasas(parentView.getSelectedItem().toString());
                //cikkszamAtiroKitoltes(getControllerMain().getBeolvasottLeirasFromList(spinnerCikkszSzerkesztoBeolvasottID.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinnerCikkszSzerkesztoUjLokacio = findViewById(R.id.SpinnerCikkszSzerkesztoUjLokacio);

        switchCikkszSzerkesztoLokacio = findViewById(R.id.SwitchCikkszSzerkesztoLokacio);
        switchCikkszSzerkesztoLokacio.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewCikkszSzerkesztoBeolvasottUjLokacioAdat.setVisibility(View.VISIBLE);
                textViewCikkszSzerkesztoBeolvasottLokacioAdat.setVisibility(View.GONE);
                spinnerCikkszSzerkesztoUjLokacio.setVisibility(View.VISIBLE);
                textViewCikkszSzerkesztoBeolvasottLokacio.setVisibility(View.GONE);
            } else {
                textViewCikkszSzerkesztoBeolvasottUjLokacioAdat.setVisibility(View.GONE);
                textViewCikkszSzerkesztoBeolvasottLokacioAdat.setVisibility(View.VISIBLE);
                spinnerCikkszSzerkesztoUjLokacio.setVisibility(View.GONE);
                textViewCikkszSzerkesztoBeolvasottLokacio.setVisibility(View.VISIBLE);
            }
        });

        controllerCikkszamatiro = ControllerCikkszamatiro.getInstance();
        controllerCikkszamatiro.setCikkszamSzerkesztoActivity(this);
        controllerCikkszamatiro.run(null);
        //getControllerMain().KapcsolatAllasKijelzes(ActivitiesEnum.CikkszamSzerkesztoActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cikkszamszerkeszto, menu);
        MenuItem itemTextViewLehetosegek = menu.findItem(R.id.CikkszSzerkesztoMenuTextViewLehetosegek);
        itemTextViewLehetosegek.setActionView(R.layout.actionbar_textview);
        MenuItem itemReconnect = menu.findItem(R.id.CikkszSzerkesztoMenuItemReconnect);
        itemReconnect.setOnMenuItemClickListener(menuItem -> {
            //getControllerMain().reconnectSQL();
            //getControllerMain().KapcsolatAllasKijelzes(ActivitiesEnum.CikkszamSzerkesztoActivity);
            return true;
        });
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btnCikkszSzerkesztoQRScan) {
            //controller.QROlvasasInditas(ActivitiesEnum.CikkszamSzerkesztoActivity);
        } else if (v == btnCikkszSzerkesztoBeolvasottTorles) {
            cikkszamAtiroBeolvasottTorles(spinnerCikkszSzerkesztoBeolvasottID.getSelectedItem().toString());
        } else if (v == btnCikkszSzerkesztoBefejezes) {
            mentesBtnClicked();
        }
    }

    private void mentesBtnClicked(){
        if (controllerCikkszamatiro.cikkszamAtiroMentes(switchCikkszSzerkesztoLokacio.isChecked(), spinnerCikkszSzerkesztoUjLokacio.getSelectedItem().toString())) {
            controllerCikkszamatiro.MessageBox("Sikeres mentés!");
            this.finish();
        } else {
            controllerCikkszamatiro.MessageBox("Mentés sikertelen!");
        }
    }

//    @SuppressLint("SetTextI18n")
//    public void cikkszamAtiroKitoltes(BeolvasottLeiras beolvasottLeiras) {
//        if (beolvasottLeiras != null) {
//            textViewCikkszSzerkesztoBeolvasottCikkszam.setText(beolvasottLeiras.getCikkszam());
//            textViewCikkszSzerkesztoBeolvasottLokacio.setText(beolvasottLeiras.getLokacio());
//            textViewCikkszSzerkesztoBeolvasottBin.setText(beolvasottLeiras.getBin());
//            textViewCikkszSzerkesztoBeolvasottHossz.setText(getControllerMain().getDecimalFormat().format(beolvasottLeiras.getBeolvasottHossz()) + "m");
//            textViewCikkszSzerkesztoBeolvasottSzelesseg.setText(getControllerMain().getDecimalFormat().format(beolvasottLeiras.getSzelesseg()) + "m");
//            textViewCikkszSzerkesztoBeolvasottDesszin.setText(beolvasottLeiras.getLeiras());
//            if (beolvasottLeiras.getCikkszamPar() != null && beolvasottLeiras.getCikkszamPar().equals("Nem találtam!")) {
//                textViewCikkszSzerkesztoUjCikkszam.setTextColor(Color.RED);
//            }
//            textViewCikkszSzerkesztoUjCikkszam.setText(beolvasottLeiras.getCikkszamPar());
//
//            btnCikkszSzerkesztoBeolvasottTorles.setVisibility(View.VISIBLE);
//        }
//
//    }

    private void cikkszamAtiroIDBeolvasas(String id)
    {
        controllerCikkszamatiro.runBeolvasas(id);
//        ArrayAdapter<String> beolvasottLeirasArrayAdapter = getControllerMain().cikkszamAtiroBeolvasas(id);
//        if (beolvasottLeirasArrayAdapter != null) {
//            spinnerCikkszSzerkesztoBeolvasottID.setAdapter(beolvasottLeirasArrayAdapter);
//            getControllerMain().hideKeyboard(CikkszamSzerkesztoActivity.this);
//        }
//        editTextCikkszSzerkesztoID.setText("");
    }

//    public void cikkszamAtiroTorlesBtnVisibility()
//    {
//        if (getControllerMain().getCikkszamAtiroBeolvasottak().size() > 0) {
//            btnCikkszSzerkesztoBeolvasottTorles.setVisibility(View.VISIBLE);
//        } else {
//            btnCikkszSzerkesztoBeolvasottTorles.setVisibility(View.GONE);
//        }
//    }

    public void cikkszamAtiroBeolvasottTorles(String id)
    {
//        if (getControllerMain().cikkszamAtiroBeolvasottTorles(id)) {
//            //cikkszamAtiroKitoltes(getControllerKiszedes().getBeolvasottLeirasFromList(spinnerCikkszSzerkesztoBeolvasottID.getSelectedItem().toString()));
//            cikkszamAtiroTorlesBtnVisibility();
//        } else {
//            getControllerMain().MessageBox("Sikertelen törlés! Probáld újra...");
//        }
    }
}
