package berwin.StockHandler.PresentationLayer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.R;

public class MatricaSzerkesztoActivity extends AppCompatActivity {

    private ControllerSzerkeszto controllerSzerkeszto;

    public TextView textViewConnection;
    public TextView textViewMatricaSzerkesztoHossz;
    public TextView textViewMatricaSzerkesztoSzelesseg;
    public TextView textViewMatricaSzerkesztoRendeles;
    public TextView textViewMatricaSzerkesztoCikkszam;
    public TextView textViewmatricaSzerkesztoQRCode;

    public TextView textViewMatricaSzerkesztoCikkszamText;
    public TextView textViewMatricaSzerkesztoRendelesText;
    public TextView textViewMatricaSzerkesztoSzelessegText;
    public TextView textViewMatricaSzerkesztoHosszText;

    public EditText editTextMatricaSzerkesztoHossz;
    public EditText editTextMatricaSzerkesztoSzelesseg;
    public EditText editTextMatricaSzerkesztoRendeles;
    public EditText editTextMatricaSzerkesztoCikkszam;

    public Switch switchMatricaSzerkesztoHossz;
    public Switch switchMatricaSzerkesztoSzelesseg;
    public Switch switchMatricaSzerkesztoRendeles;
    public Switch switchMatricaSzerkesztoCikkszam;

    public Button btnMatricaSzereksztoMentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_matricaszerkeszto);

        textViewConnection = findViewById(R.id.TextViewConnection);
        textViewMatricaSzerkesztoHossz = findViewById(R.id.TextViewMatricaSzerkesztoHossz);
        textViewMatricaSzerkesztoSzelesseg = findViewById(R.id.TextViewMatricaSzerkesztoSzelesseg);
        textViewMatricaSzerkesztoRendeles = findViewById(R.id.TextViewPMatricaSzerkesztoRendeles);
        textViewMatricaSzerkesztoCikkszam = findViewById(R.id.TextViewMatricaSzerkesztoCikkszam);
        textViewmatricaSzerkesztoQRCode = findViewById(R.id.TextViewmatricaSzerkesztoQRCode);

        textViewMatricaSzerkesztoCikkszamText = findViewById(R.id.TextViewMatricaSzerkesztoCikkszamText);
        textViewMatricaSzerkesztoRendelesText = findViewById(R.id.TextViewMatricaSzerkesztoRendelesText);
        textViewMatricaSzerkesztoSzelessegText = findViewById(R.id.TextViewMatricaSzerkesztoSzelessegText);
        textViewMatricaSzerkesztoHosszText = findViewById(R.id.TextViewMatricaSzerkesztoHosszText);

        editTextMatricaSzerkesztoHossz = findViewById(R.id.EditTextMatricaSzerkesztoHossz);
        editTextMatricaSzerkesztoSzelesseg = findViewById(R.id.EditTextMatricaSzerkesztoSzelesseg);
        editTextMatricaSzerkesztoRendeles = findViewById(R.id.EditTextMatricaSzerkesztoRendeles);
        editTextMatricaSzerkesztoCikkszam = findViewById(R.id.EditTextMatricaSzerkesztoCikkszam);

        switchMatricaSzerkesztoHossz = findViewById(R.id.SwitchMatricaSzerkesztoHossz);
        switchMatricaSzerkesztoHossz.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewMatricaSzerkesztoHossz.setVisibility(View.GONE);
                editTextMatricaSzerkesztoHossz.setVisibility(View.VISIBLE);
                textViewMatricaSzerkesztoHosszText.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewMatricaSzerkesztoHossz.setVisibility(View.VISIBLE);
                editTextMatricaSzerkesztoHossz.setVisibility(View.GONE);
                textViewMatricaSzerkesztoHosszText.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchMatricaSzerkesztoSzelesseg = findViewById(R.id.SwitchMatricaSzerkesztoSzelesseg);
        switchMatricaSzerkesztoSzelesseg.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewMatricaSzerkesztoSzelesseg.setVisibility(View.GONE);
                editTextMatricaSzerkesztoSzelesseg.setVisibility(View.VISIBLE);
                textViewMatricaSzerkesztoSzelessegText.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewMatricaSzerkesztoSzelesseg.setVisibility(View.VISIBLE);
                editTextMatricaSzerkesztoSzelesseg.setVisibility(View.GONE);
                textViewMatricaSzerkesztoSzelessegText.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchMatricaSzerkesztoRendeles = findViewById(R.id.SwitchMatricaSzerkesztoRendeles);
        switchMatricaSzerkesztoRendeles.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewMatricaSzerkesztoRendeles.setVisibility(View.GONE);
                editTextMatricaSzerkesztoRendeles.setVisibility(View.VISIBLE);
                textViewMatricaSzerkesztoRendelesText.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewMatricaSzerkesztoRendeles.setVisibility(View.VISIBLE);
                editTextMatricaSzerkesztoRendeles.setVisibility(View.GONE);
                textViewMatricaSzerkesztoRendelesText.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchMatricaSzerkesztoCikkszam = findViewById(R.id.SwitchMatricaSzerkesztoCikkszam);
        switchMatricaSzerkesztoCikkszam.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewMatricaSzerkesztoCikkszam.setVisibility(View.GONE);
                editTextMatricaSzerkesztoCikkszam.setVisibility(View.VISIBLE);
                textViewMatricaSzerkesztoCikkszamText.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewMatricaSzerkesztoCikkszam.setVisibility(View.VISIBLE);
                editTextMatricaSzerkesztoCikkszam.setVisibility(View.GONE);
                textViewMatricaSzerkesztoCikkszamText.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        btnMatricaSzereksztoMentes = findViewById(R.id.btnMatricaSzereksztoMentes);
        btnMatricaSzereksztoMentes.setOnClickListener(view -> {
            szerkesztettAdatMentes();
            if (controllerSzerkeszto.matricaSzerkesztoMentes()) {
                controllerSzerkeszto.run(new Parameter<>(false));
            }
        });

        controllerSzerkeszto = ControllerSzerkeszto.getInstance();
        controllerSzerkeszto.setMatricaSzerkesztoActivity(this);
        controllerSzerkeszto.run(new Parameter<>(false));
    }

//    @SuppressLint("SetTextI18n")
//    public void fillMatirca()
//    {
//        BeolvasottQRCode beolvasottMatrica = getControllerMain().getBeolvasottQRCode();
//        if (beolvasottMatrica != null)
//        {
//            textViewMatricaSzerkesztoHossz.setText(df.format(beolvasottMatrica.getBeolvasottHossz()) + "m");
//            textViewMatricaSzerkesztoSzelesseg.setText(String.valueOf(beolvasottMatrica.getSzelesseg()) + "m");
//            textViewMatricaSzerkesztoRendeles.setText(beolvasottMatrica.getRendelesSzam());
//            textViewMatricaSzerkesztoCikkszam.setText(beolvasottMatrica.getCikkszam());
//            textViewmatricaSzerkesztoQRCode.setText("QR Code:\n" + beolvasottMatrica.getQRCode());
//
//            editTextMatricaSzerkesztoHossz.setText(String.valueOf(beolvasottMatrica.getBeolvasottHossz()));
//            editTextMatricaSzerkesztoSzelesseg.setText(String.valueOf(beolvasottMatrica.getSzelesseg()));
//            editTextMatricaSzerkesztoRendeles.setText(beolvasottMatrica.getRendelesSzam());
//            editTextMatricaSzerkesztoCikkszam.setText(beolvasottMatrica.getCikkszam());
//
//            switchMatricaSzerkesztoHossz.setChecked(false);
//            switchMatricaSzerkesztoSzelesseg.setChecked(false);
//            switchMatricaSzerkesztoRendeles.setChecked(false);
//            switchMatricaSzerkesztoCikkszam.setChecked(false);
//        } else {
//            textViewMatricaSzerkesztoHossz.setText("Nem található!");
//            textViewMatricaSzerkesztoSzelesseg.setText("Nem található!");
//            textViewMatricaSzerkesztoRendeles.setText("Nem található!");
//            textViewMatricaSzerkesztoCikkszam.setText("Nem található!");
//            textViewmatricaSzerkesztoQRCode.setText("Nem található!");
//
//            switchMatricaSzerkesztoHossz.setClickable(false);
//            switchMatricaSzerkesztoSzelesseg.setClickable(false);
//            switchMatricaSzerkesztoRendeles.setClickable(false);
//            switchMatricaSzerkesztoCikkszam.setClickable(false);
//        }
//    }

    private void szerkesztettAdatMentes()
    {
        if (switchMatricaSzerkesztoCikkszam.isChecked()) controllerSzerkeszto.getAktualisBeolvasottQRCode().setCikkszam(editTextMatricaSzerkesztoCikkszam.getText().toString());
        if (switchMatricaSzerkesztoRendeles.isChecked()) controllerSzerkeszto.getAktualisBeolvasottQRCode().setRendelesSzam(editTextMatricaSzerkesztoRendeles.getText().toString());
        if (switchMatricaSzerkesztoSzelesseg.isChecked()) controllerSzerkeszto.getAktualisBeolvasottQRCode().setSzelesseg(Double.parseDouble(editTextMatricaSzerkesztoSzelesseg.getText().toString()));
        if (switchMatricaSzerkesztoHossz.isChecked()) controllerSzerkeszto.getAktualisBeolvasottQRCode().setBeolvasottHossz(Double.parseDouble(editTextMatricaSzerkesztoHossz.getText().toString()));
    }
}
