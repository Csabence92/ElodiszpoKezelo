package berwin.StockHandler.PresentationLayer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.R;

public class VegszerkesztoActivity extends AppCompatActivity {

    public TextView textViewConnection;
    public TextView textViewRendelesSzerkesztoAdatai;
    public TextView textViewSzerkesztoCikkszam;
    public TextView textViewSzerkesztoBin;
    public TextView textViewSzerkesztoLokacio;
    public TextView textViewSzerkesztoSzelesseg;
    public TextView textViewSzerkesztoHossz;
    public TextView textViewSzerkesztoBevNap;
    public TextView textViewSzerkesztoVevo;
    public TextView textViewSzerkesztoAllapot;
    public TextView textViewSzerkesztoRendeles;
    public TextView textViewSzerkesztoNev;
    public TextView textViewSzerkesztoMozgas;

    public TextView textViewSzerkesztoCikkszamView;
    public TextView textViewSzerkesztoBinView;
    public TextView textViewSzerkesztoLokacioView;
    public TextView textViewSzerkesztoSzelessegView;
    public TextView textViewSzerkesztoHosszView;
    public TextView textViewSzerkesztoBevNapView;
    public TextView textViewSzerkesztoVevoView;
    public TextView textViewSzerkesztoAllapotView;
    public TextView textViewSzerkesztoRendelesView;
    public TextView textViewSzerkesztoNevView;
    public TextView textViewSzerkesztoMozgasView;

    public EditText editTextSzerkesztoCikkszam;
    public EditText editTextSzerkesztoBin;
    public EditText editTextSzerkesztoLokacio;
    public EditText editTextSzerkesztoSzelesseg;
    public EditText editTextSzerkesztoHossz;
    public EditText editTextSzerkesztoBevNap;
    public EditText editTextSzerkesztoVevo;
    public EditText editTextSzerkesztoAllapot;
    public EditText editTextSzerkesztoRendeles;
    public EditText editTextSzerkesztoNev;
    public EditText editTextSzerkesztoMozgas;

    public Switch switchSzerkesztoCikkszam;
    public Switch switchSzerkesztoBin;
    public Switch switchSzerkesztoLokacio;
    public Switch switchSzerkesztoSzelesseg;
    public Switch switchSzerkesztoHossz;
    public Switch switchSzerkesztoBevNap;
    public Switch switchSzerkesztoVevo;
    public Switch switchSzerkesztoAllapot;
    public Switch switchSzerkesztoRendeles;
    public Switch switchSzerkesztoNev;
    public Switch switchSzerkesztoMozgas;

    public Button btnSzerkesztoUj;
    public Button btnSzereksztoMentes;
    public Button btnSzereksztoFelszabadit;

    private ControllerSzerkeszto controllerSzerkeszto;
    private AsyncLoading asyncLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vegszerkeszto);

        textViewConnection = findViewById(R.id.TextViewConnection);
        textViewRendelesSzerkesztoAdatai = findViewById(R.id.TextViewRendelesSzerkesztoAdatai);
        textViewSzerkesztoCikkszam = findViewById(R.id.TextViewSzerkesztoCikkszam);
        textViewSzerkesztoBin = findViewById(R.id.TextViewSzerkesztoBin);
        textViewSzerkesztoLokacio = findViewById(R.id.TextViewSzerkesztoLokacio);
        textViewSzerkesztoSzelesseg = findViewById(R.id.TextViewSzerkesztoSzelesseg);
        textViewSzerkesztoHossz = findViewById(R.id.TextViewSzerkesztoHossz);
        textViewSzerkesztoBevNap = findViewById(R.id.TextViewSzerkesztoBevNap);
        textViewSzerkesztoVevo = findViewById(R.id.TextViewSzerkesztoVevo);
        textViewSzerkesztoAllapot = findViewById(R.id.TextViewSzerkesztoAllapot);
        textViewSzerkesztoRendeles = findViewById(R.id.TextViewSzerkesztoRendeles);
        textViewSzerkesztoNev = findViewById(R.id.TextViewSzerkesztoNev);
        textViewSzerkesztoMozgas = findViewById(R.id.TextViewSzerkesztoMozgas);

        textViewSzerkesztoCikkszamView = findViewById(R.id.TextViewSzerkesztoCikkszamView);
        textViewSzerkesztoBinView = findViewById(R.id.TextViewSzerkesztoBinView);
        textViewSzerkesztoLokacioView = findViewById(R.id.TextViewSzerkesztoLokacioView);
        textViewSzerkesztoSzelessegView = findViewById(R.id.TextViewSzerkesztoSzelessegView);
        textViewSzerkesztoHosszView = findViewById(R.id.TextViewSzerkesztoHosszView);
        textViewSzerkesztoBevNapView = findViewById(R.id.TextViewSzerkesztoBevNapView);
        textViewSzerkesztoVevoView = findViewById(R.id.TextViewSzerkesztoVevoView);
        textViewSzerkesztoAllapotView = findViewById(R.id.TextViewSzerkesztoAllapotView);
        textViewSzerkesztoRendelesView = findViewById(R.id.TextViewSzerkesztoRendelesView);
        textViewSzerkesztoNevView = findViewById(R.id.TextViewSzerkesztoNevView);
        textViewSzerkesztoMozgasView = findViewById(R.id.TextViewSzerkesztoMozgasView);

        editTextSzerkesztoCikkszam = findViewById(R.id.EditTextSzerkesztoCikkszam);
        editTextSzerkesztoCikkszam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setCikkszam(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoBin = findViewById(R.id.EditTextSzerkesztoBin);
        editTextSzerkesztoBin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setBin(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoLokacio = findViewById(R.id.EditTextSzerkesztoLokacio);
        editTextSzerkesztoLokacio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setLokacio(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoSzelesseg = findViewById(R.id.EditTextSzerkesztoSzelesseg);
        editTextSzerkesztoSzelesseg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setSzelesseg(Double.parseDouble(charSequence.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoHossz = findViewById(R.id.EditTextSzerkesztoHossz);
        editTextSzerkesztoHossz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setBeolvasottHossz(Double.parseDouble(charSequence.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoBevNap = findViewById(R.id.EditTextSzerkesztoBevNap);
        editTextSzerkesztoBevNap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setBevNap(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoVevo = findViewById(R.id.EditTextSzerkesztoVevo);
        editTextSzerkesztoVevo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setVevo(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoAllapot = findViewById(R.id.EditTextSzerkesztoAllapot);
        editTextSzerkesztoAllapot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setAllapot(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoRendeles = findViewById(R.id.EditTextSzerkesztoRendeles);
        editTextSzerkesztoRendeles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setRendelesSzam(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoNev = findViewById(R.id.EditTextSzerkesztoNev);
        editTextSzerkesztoNev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setNev(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextSzerkesztoMozgas = findViewById(R.id.EditTextSzerkesztoMozgas);
        editTextSzerkesztoMozgas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.toString().isEmpty()) {
                    controllerSzerkeszto.getAktualisBeolvasottTeljes().setMozgas(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        switchSzerkesztoCikkszam = findViewById(R.id.SwitchSzerkesztoCikkszam);
        switchSzerkesztoCikkszam.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoCikkszam.setVisibility(View.GONE);
                editTextSzerkesztoCikkszam.setVisibility(View.VISIBLE);
                textViewSzerkesztoCikkszamView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoCikkszam.setVisibility(View.VISIBLE);
                editTextSzerkesztoCikkszam.setVisibility(View.GONE);
                textViewSzerkesztoCikkszamView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoBin = findViewById(R.id.SwitchSzerkesztoBin);
        switchSzerkesztoBin.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoBin.setVisibility(View.GONE);
                editTextSzerkesztoBin.setVisibility(View.VISIBLE);
                textViewSzerkesztoBinView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoBin.setVisibility(View.VISIBLE);
                editTextSzerkesztoBin.setVisibility(View.GONE);
                textViewSzerkesztoBinView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoLokacio = findViewById(R.id.SwitchSzerkesztoLokacio);
        switchSzerkesztoLokacio.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoLokacio.setVisibility(View.GONE);
                editTextSzerkesztoLokacio.setVisibility(View.VISIBLE);
                textViewSzerkesztoLokacioView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoLokacio.setVisibility(View.VISIBLE);
                editTextSzerkesztoLokacio.setVisibility(View.GONE);
                textViewSzerkesztoLokacioView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoSzelesseg = findViewById(R.id.SwitchSzerkesztoSzelesseg);
        switchSzerkesztoSzelesseg.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoSzelesseg.setVisibility(View.GONE);
                editTextSzerkesztoSzelesseg.setVisibility(View.VISIBLE);
                textViewSzerkesztoSzelessegView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoSzelesseg.setVisibility(View.VISIBLE);
                editTextSzerkesztoSzelesseg.setVisibility(View.GONE);
                textViewSzerkesztoSzelessegView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoHossz = findViewById(R.id.SwitchSzerkesztoHossz);
        switchSzerkesztoHossz.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoHossz.setVisibility(View.GONE);
                editTextSzerkesztoHossz.setVisibility(View.VISIBLE);
                textViewSzerkesztoHosszView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoHossz.setVisibility(View.VISIBLE);
                editTextSzerkesztoHossz.setVisibility(View.GONE);
                textViewSzerkesztoHosszView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoBevNap = findViewById(R.id.SwitchSzerkesztoBevNap);
        switchSzerkesztoBevNap.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoBevNap.setVisibility(View.GONE);
                editTextSzerkesztoBevNap.setVisibility(View.VISIBLE);
                textViewSzerkesztoBevNapView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoBevNap.setVisibility(View.VISIBLE);
                editTextSzerkesztoBevNap.setVisibility(View.GONE);
                textViewSzerkesztoBevNapView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoVevo = findViewById(R.id.SwitchSzerkesztoVevo);
        switchSzerkesztoVevo.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoVevo.setVisibility(View.GONE);
                editTextSzerkesztoVevo.setVisibility(View.VISIBLE);
                textViewSzerkesztoVevoView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoVevo.setVisibility(View.VISIBLE);
                editTextSzerkesztoVevo.setVisibility(View.GONE);
                textViewSzerkesztoVevoView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoAllapot = findViewById(R.id.SwitchSzerkesztoAllapot);
        switchSzerkesztoAllapot.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoAllapot.setVisibility(View.GONE);
                editTextSzerkesztoAllapot.setVisibility(View.VISIBLE);
                textViewSzerkesztoAllapotView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoAllapot.setVisibility(View.VISIBLE);
                editTextSzerkesztoAllapot.setVisibility(View.GONE);
                textViewSzerkesztoAllapotView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoRendeles = findViewById(R.id.SwitchSzerkesztoRendeles);
        switchSzerkesztoRendeles.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoRendeles.setVisibility(View.GONE);
                editTextSzerkesztoRendeles.setVisibility(View.VISIBLE);
                textViewSzerkesztoRendelesView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoRendeles.setVisibility(View.VISIBLE);
                editTextSzerkesztoRendeles.setVisibility(View.GONE);
                textViewSzerkesztoRendelesView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoNev = findViewById(R.id.SwitchSzerkesztoNev);
        switchSzerkesztoNev.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoNev.setVisibility(View.GONE);
                editTextSzerkesztoNev.setVisibility(View.VISIBLE);
                textViewSzerkesztoNevView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoNev.setVisibility(View.VISIBLE);
                editTextSzerkesztoNev.setVisibility(View.GONE);
                textViewSzerkesztoNevView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        switchSzerkesztoMozgas = findViewById(R.id.SwitchSzerkesztoMozgas);
        switchSzerkesztoMozgas.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                textViewSzerkesztoMozgas.setVisibility(View.GONE);
                editTextSzerkesztoMozgas.setVisibility(View.VISIBLE);
                textViewSzerkesztoMozgasView.setTextColor(Color.rgb(10, 201, 192));
            } else {
                textViewSzerkesztoMozgas.setVisibility(View.VISIBLE);
                editTextSzerkesztoMozgas.setVisibility(View.GONE);
                textViewSzerkesztoMozgasView.setTextColor(Color.rgb(189, 189, 189));
            }
        });

        btnSzerkesztoUj = findViewById(R.id.btnSzerkesztoUj);
        btnSzerkesztoUj.setOnClickListener(v -> {
            this.finish();
            controllerSzerkeszto.getMainActivity().beolvasasDialogBuilder(ActivitiesEnum.VegszerkesztoActivity);
        });
        btnSzereksztoMentes = findViewById(R.id.btnSzereksztoMentes);
        btnSzereksztoMentes.setOnClickListener(v -> {
            asyncLoading = new AsyncLoading(VegszerkesztoActivity.this, ActivitiesEnum.VegszerkesztoActivity, false, switchSzerkesztoBin.isChecked());
            asyncLoading.execute();
        });
        btnSzereksztoFelszabadit = findViewById(R.id.btnSzereksztoFelszabadit);
        btnSzereksztoFelszabadit.setOnClickListener(v -> {
            if (controllerSzerkeszto.vegFeltoltesVegszerkeszto(true, false)) {
                controllerSzerkeszto.run(new Parameter<>(true));
            }
        });

        controllerSzerkeszto = ControllerSzerkeszto.getInstance();
        controllerSzerkeszto.setVegszerkesztoActivity(this);
        controllerSzerkeszto.run(new Parameter<>(true));
    }
}
