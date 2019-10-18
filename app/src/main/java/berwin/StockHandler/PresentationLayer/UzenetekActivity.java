package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Uzenetek.ControllerUzenetek;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.R;

public class UzenetekActivity extends AppCompatActivity {

    private ControllerUzenetek controllerUzenetek;

    public ListView listViewUzenetek;

    public Spinner spinnerFelhasznalok;

    public EditText editTextUzenetekSzoveg;
    public EditText editTextUzenetekKereses;

    public TextView textViewConnection;

    public Button btnUzenetekKuldes;

    final Handler handler = new Handler();
    final int delay = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_uzenetek);

        listViewUzenetek = findViewById(R.id.ListViewUzenetek);
        textViewConnection = findViewById(R.id.TextViewConnection);

        btnUzenetekKuldes = findViewById(R.id.btnUzenetekKuldes);
        btnUzenetekKuldes.setOnClickListener(view -> {
            if (controllerUzenetek.uzenetFeltoltes(spinnerFelhasznalok.getSelectedItem().toString(), editTextUzenetekSzoveg.getText().toString())) {
                editTextUzenetekSzoveg.setText("");
                controllerUzenetek.run(null);
            }
        });

        spinnerFelhasznalok = findViewById(R.id.SpinnerFelhasznalok);

        editTextUzenetekSzoveg = findViewById(R.id.EditTextUzenetekSzoveg);
        editTextUzenetekKereses = findViewById(R.id.EditTextUzenetekKereses);
        editTextUzenetekKereses.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() >= 3) {
                    controllerUzenetek.run(new Parameter<>(editTextUzenetekKereses.getText().toString()));
                } else {
                    controllerUzenetek.run(null);
                }
            }
        });
        controllerUzenetek = ControllerUzenetek.getInstance();
        controllerUzenetek.setUzenetekActivity(this);
        controllerUzenetek.run(null);

        handler.postDelayed(new Runnable(){
            public void run(){
                controllerUzenetek.run(null);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
