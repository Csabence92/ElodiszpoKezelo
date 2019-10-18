package berwin.StockHandler.PresentationLayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import berwin.StockHandler.LogicLayer.Naplozas.ControllerNaplozas;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.R;

public class NaplozasActivity extends AppCompatActivity {

    public ListView listViewNaplo;

    public TextView textViewNaplozoCim;
    public TextView textViewConnection;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.table_naplozo);

        listViewNaplo = findViewById(R.id.ListViewNaplo);
        textViewConnection = findViewById(R.id.TextViewConnection);
        textViewNaplozoCim = findViewById(R.id.TextViewNaplozoCim);

        ControllerNaplozas controllerNaplozas = ControllerNaplozas.getInstance();
        controllerNaplozas.setNaplozasActivity(this);
        controllerNaplozas.run(new Parameter<>(controllerNaplozas.getMainActivity().editTextID.getText().toString()));
    }
}