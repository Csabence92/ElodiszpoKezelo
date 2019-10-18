package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Potlasok.ControllerPotlasok;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.R;

public class PotlasokActivity extends AppCompatActivity {

    private ControllerPotlasok controllerPotlasok;

    public ListView listPotlasok;

    public EditText editTextPotlasokKereses;

    public TextView textViewConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.table_potlasok);

        listPotlasok = findViewById(R.id.ListPotlasok);
        textViewConnection = findViewById(R.id.TextViewConnection);

        editTextPotlasokKereses = findViewById(R.id.EditTextPotlasokKereses);
        editTextPotlasokKereses.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() >= 7) {
                    controllerPotlasok.run(new Parameter<>(s.toString()));
                }
                else {
                    controllerPotlasok.run(new Parameter<>(""));
                }
            }
        });

        controllerPotlasok = ControllerPotlasok.getInstance();
        controllerPotlasok.setPotlasokActivity(this);
        controllerPotlasok.run(new Parameter<>(""));
    }
}
