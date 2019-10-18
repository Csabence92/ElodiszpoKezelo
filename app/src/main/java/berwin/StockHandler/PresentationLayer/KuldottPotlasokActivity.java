package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.NamenyiPotlasok.ControllerNamenyiPotlasok;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.R;

public class KuldottPotlasokActivity extends AppCompatActivity {

    private ControllerNamenyiPotlasok controllerNamenyiPotlasok;

    public ListView listElkuldottPotlasok;
    public TextView textViewConnection;

    public EditText editTextElkuldPotlasokKereses;

    public Button btnQRScann;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.table_kuldottpotlas);

        listElkuldottPotlasok = findViewById(R.id.ListElkuldottPotlasok);
        textViewConnection = findViewById(R.id.TextViewConnection);

        editTextElkuldPotlasokKereses = findViewById(R.id.EditTextElkuldPotlasokKereses);
        editTextElkuldPotlasokKereses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 0)
                {
                    if (s.length() >= 5) {
                        controllerNamenyiPotlasok.run(new Parameter<>(s.toString()));
                    } else {
                        controllerNamenyiPotlasok.run(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btnQRScann = findViewById(R.id.btnQRScann);
        btnQRScann.setOnClickListener(view -> controllerNamenyiPotlasok.alertDialogBuilder(null, null)

        );

        controllerNamenyiPotlasok = ControllerNamenyiPotlasok.getInstance();
        controllerNamenyiPotlasok.setKuldottPotlasokActivity(this);
        controllerNamenyiPotlasok.run(null);
    }
}
