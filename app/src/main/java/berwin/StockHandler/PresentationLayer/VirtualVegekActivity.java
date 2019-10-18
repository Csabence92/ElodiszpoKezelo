package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.*;
import berwin.StockHandler.*;
import berwin.StockHandler.LogicLayer.Virtualvegek.ControllerVirtualvegek;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class VirtualVegekActivity extends AppCompatActivity {

    private ControllerVirtualvegek controllerVirtualvegek;

    public ListView listViewVirtualVegek;
    public TextView textViewConnection;

    public EditText editTextVirtVegekKereses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.table_virtualvegek);

        listViewVirtualVegek = findViewById(R.id.ListViewVirtualVegek);
        textViewConnection = findViewById(R.id.TextViewConnection);

        editTextVirtVegekKereses = findViewById(R.id.EditTextVirtVegekKereses);
        editTextVirtVegekKereses.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() >= 7) {
                    controllerVirtualvegek.run(new Parameter<>(s.toString()));
                } else {
                    controllerVirtualvegek.run(null);
                }
            }
        });

        controllerVirtualvegek = ControllerVirtualvegek.getInstance();
        controllerVirtualvegek.setVirtualVegekActivity(this);
        controllerVirtualvegek.run(null);
    }
}
