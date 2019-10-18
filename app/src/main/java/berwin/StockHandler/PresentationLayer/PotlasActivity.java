package berwin.StockHandler.PresentationLayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import berwin.StockHandler.LogicLayer.Potlas.ControllerPotlas;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.R;

public class PotlasActivity extends AppCompatActivity
{
    private ControllerPotlas controllerPotlas;

    public Button btnPotlasMehet;
    public Button btnPotlasMasolas;
    public Button btnPotlasUj;

    public EditText editTextRendelesszamSzoveg;
    public EditText editViewPotlasKiadasHossz;

    public TextView textViewPotlasRendelesAdatai;
    public TextView textViewPotlasCikkszam;
    public TextView textViewPotlasSzelesseg;
    public TextView textViewPotlasBin;
    public TextView textViewPotlasHossz;
    public TextView textViewConnection;

    public TextView textViewPotlasQRCode;
    public TextView textViewPotlasMatricaRendeles;
    public TextView textViewPotlasMatricaHossz;
    public TextView textViewPotlasMatricaCikkszam;
    public TextView textViewPotlasMatricaSzelesseg;

    public LinearLayout potlasMatricaLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_potlas);

        btnPotlasMehet = findViewById(R.id.btnPotlasMehet);
        btnPotlasMehet.setOnClickListener(v -> btnPotlasMehetClicked());
        btnPotlasMasolas = findViewById(R.id.btnPotlasMasolas);
        btnPotlasMasolas.setOnClickListener(v -> btnPotlasMasolasClicked());
        btnPotlasUj = findViewById(R.id.btnPotlasUj);
        btnPotlasUj.setOnClickListener(v -> ujBtnClicked());

        editTextRendelesszamSzoveg = findViewById(R.id.EditTextRendelesszamSzoveg);
        editViewPotlasKiadasHossz = findViewById(R.id.EditViewPotlasKiadasHossz);

        textViewPotlasRendelesAdatai = findViewById(R.id.TextViewPotlasRendelesAdatai);
        textViewPotlasCikkszam = findViewById(R.id.TextViewPotlasCikkszam);
        textViewPotlasSzelesseg = findViewById(R.id.TextViewPotlasSzelesseg);
        textViewPotlasBin = findViewById(R.id.TextViewPotlasBin);
        textViewPotlasHossz = findViewById(R.id.TextViewPotlasHossz);
        textViewConnection = findViewById(R.id.TextViewConnection);

        textViewPotlasQRCode = findViewById(R.id.TextViewPotlasQRCode);
        textViewPotlasMatricaRendeles = findViewById(R.id.TextViewPotlasMatricaRendeles);
        textViewPotlasMatricaHossz = findViewById(R.id.TextViewPotlasMatricaHossz);
        textViewPotlasMatricaCikkszam = findViewById(R.id.TextViewPotlasMatricaCikkszam);
        textViewPotlasMatricaSzelesseg = findViewById(R.id.TextViewPotlasMatricaSzelesseg);

        potlasMatricaLinearLayout = findViewById(R.id.potlasMatricaLinearLayout);

        controllerPotlas = ControllerPotlas.getInstance();
        controllerPotlas.setPotlasActivity(this);
        controllerPotlas.run(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_potlas, menu);
        MenuItem itemTextViewLehetosegek = menu.findItem(R.id.PotlasMenuTextViewLehetosegek);
        itemTextViewLehetosegek.setActionView(R.layout.actionbar_textview);
        MenuItem itemUj = menu.findItem(R.id.PotlasMenuItemReconnect);
        itemUj.setOnMenuItemClickListener(menuItem -> {
            //getControllerMain().reconnectSQL();
            return true;
        });
        MenuItem itemNamenyiPotlasok = menu.findItem(R.id.PotlasMenuItemKuldottPotlasok);
        itemNamenyiPotlasok.setOnMenuItemClickListener(menuItem -> {
            controllerPotlas.activityStarter(PotlasActivity.this, KuldottPotlasokActivity.class);
            return false;
        });
        MenuItem itemEddigiPotlasok = menu.findItem(R.id.PotlasMenuItemPotlasok);
        itemEddigiPotlasok.setOnMenuItemClickListener(menuItem -> {
            controllerPotlas.activityStarter(PotlasActivity.this, PotlasokActivity.class);
            return false;
        });
        return true;
    }

    private void ujBtnClicked() {
        this.potlasMatricaLinearLayout.setVisibility(View.GONE);
        this.finish();
        controllerPotlas.getMainActivity().beolvasasDialogBuilder(ActivitiesEnum.PotlasActivity);
    }

    private void btnPotlasMehetClicked() {
        if (!editTextRendelesszamSzoveg.getText().toString().isEmpty() && !editViewPotlasKiadasHossz.getText().toString().isEmpty()) {
            controllerPotlas.getAktualisBeolvasott().setLeiras(editTextRendelesszamSzoveg.getText().toString());
            controllerPotlas.getAktualisBeolvasott().setKiadottHossz(Double.parseDouble(editViewPotlasKiadasHossz.getText().toString()));
            controllerPotlas.potlasFeltoltes();
            editTextRendelesszamSzoveg.setText("");
            editViewPotlasKiadasHossz.setText("");
            controllerPotlas.setAktualisBeolvasott(controllerPotlas.getAktualisBeolvasott().getId());
            controllerPotlas.run(null);
        } else {
            controllerPotlas.MessageBox("Nem adtál meg adatot!");
        }
    }

    private void btnPotlasMasolasClicked() {
        if (!editTextRendelesszamSzoveg.getText().toString().isEmpty() && !editViewPotlasKiadasHossz.getText().toString().isEmpty()) {
            controllerPotlas.getAktualisBeolvasott().setLeiras(editTextRendelesszamSzoveg.getText().toString());
            controllerPotlas.getAktualisBeolvasott().setKiadottHossz(Double.parseDouble(editViewPotlasKiadasHossz.getText().toString()));
            controllerPotlas.alertDialogBuilder(null, null);
        }
        else {
            controllerPotlas.MessageBox("Nem adtál meg adatot!");
        }
    }
}
