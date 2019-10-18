package berwin.StockHandler.PresentationLayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.StringBufferInputStream;
import java.util.ArrayList;

import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PlanSzedesKiszedesActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ControllerPlanSzedes controllerPlanSzedes;

    public SurfaceView qrOlvaso;

    public TextView textViewPlanSzedesCikkszam;
    public TextView textViewPlanSzedesLezarva;
    public TextView textViewPlanSzedesRendelesek;
    public TextView textViewPlanSzedesSzuksegesHossz;
    public TextView textViewPlanSzedesKeszletenHossz;
    public TextView textViewPlanSzedesBin;
    public TextView textViewPlanSzedesIDHelyesE;
    public TextView textViewPlanSzedesBeolvasottBin;
    public TextView textViewPlanSzedesBeolvasottHossz;
    public TextView textViewPlanSzedesEddigBeolvasottHossz;
    public TextView textViewPlanSzedesEddigBeolvasottDB;
    public TextView textViewID;
    public TextView textViewBeolvasottID;
    public TextView textViewConnection;
    public TextView TextViewPlanSzedesBeolvasottSzelesseg;

    public EditText editTextPlanSzedesID;
    public EditText editTextID;

    public Switch switchKeziBevitel;

    public LinearLayout linearLayoutBeolvasottAdatok;
    public Spinner spinnerPlanSzedesBeolvasottID;

    public Button btnPlanSzedesQRScan;
    public Button btnPlanSzedesBeolvasottTorles;
    public Button btnPlanSzedesMentesBefejezes;

    AlertDialog.Builder aBuilder;
    public AlertDialog dialog;
    public View aView;
    public BarcodeDetector barcodeDetector;
    public CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    public final Context context = this;
    public final Activity activity = PlanSzedesKiszedesActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planszedes_kiszedes);

        textViewPlanSzedesCikkszam = findViewById(R.id.TextViewPlanSzedesCikkszam);
        textViewPlanSzedesLezarva = findViewById(R.id.TextViewPlanSzedesLezarva);
        textViewPlanSzedesRendelesek = findViewById(R.id.TextViewPlanSzedesRendelesek);
        textViewPlanSzedesSzuksegesHossz = findViewById(R.id.TextViewPlanSzedesSzuksegesHossz);
        textViewPlanSzedesKeszletenHossz = findViewById(R.id.TextViewPlanSzedesKeszletenHossz);
        textViewPlanSzedesBin = findViewById(R.id.TextViewPlanSzedesBin);
        textViewPlanSzedesIDHelyesE = findViewById(R.id.TextViewPlanSzedesIDHelyesE);
        textViewPlanSzedesBeolvasottBin = findViewById(R.id.TextViewPlanSzedesBeolvasottBin);
        textViewPlanSzedesBeolvasottHossz = findViewById(R.id.TextViewPlanSzedesBeolvasottHossz);
        textViewPlanSzedesEddigBeolvasottHossz = findViewById(R.id.TextViewPlanSzedesEddigBeolvasottHossz);
        textViewPlanSzedesEddigBeolvasottDB = findViewById(R.id.TextViewPlanSzedesEddigBeolvasottDB);
        textViewConnection = findViewById(R.id.TextViewConnection);
        TextViewPlanSzedesBeolvasottSzelesseg = findViewById(R.id.TextViewPlanSzedesBeolvasottSzelesseg);

        editTextPlanSzedesID = findViewById(R.id.EditTextPlanSzedesID);
        editTextPlanSzedesID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 7) {
                    controllerPlanSzedes.runSzovetBeolvasasEllenorzes(s.toString());
                    //Toast.makeText(PlanSzedesKiszedesActivity.this,controllerPlanSzedes.getMessage(),Toast.LENGTH_LONG).show();
                } else {
                    textViewPlanSzedesIDHelyesE.setText("");
                }
            }
        });

        linearLayoutBeolvasottAdatok = findViewById(R.id.LinearLayoutBeolvasottAdatok);

        spinnerPlanSzedesBeolvasottID = findViewById(R.id.SpinnerPlanSzedesBeolvasottID);
        spinnerPlanSzedesBeolvasottID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                controllerPlanSzedes.setControllersAktualisBeolvasott(spinnerPlanSzedesBeolvasottID.getSelectedItem().toString());
                controllerPlanSzedes.runSzovetBeolvasottKitoltes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnPlanSzedesQRScan = findViewById(R.id.btnPlanSzedesQRScan);
        btnPlanSzedesQRScan.setOnClickListener(v -> beolvasasDialogBuilder());
        btnPlanSzedesBeolvasottTorles = findViewById(R.id.btnPlanSzedesBeolvasottTorles);
        btnPlanSzedesBeolvasottTorles.setOnClickListener(v -> controllerPlanSzedes.runSzovetBeolvasottTorles(spinnerPlanSzedesBeolvasottID.getSelectedItem().toString()));
        btnPlanSzedesMentesBefejezes = findViewById(R.id.btnPlanSzedesMentesBefejezes);
        btnPlanSzedesMentesBefejezes.setOnClickListener(v -> controllerPlanSzedes.kiszedesMentes());

        controllerPlanSzedes = ControllerPlanSzedes.getInstanceControllerPlanSzedes();
        controllerPlanSzedes.setPlanSzedesKiszedesActivity(this);
        controllerPlanSzedes.runPlanSzedes();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(qrOlvaso.getHolder());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void beolvasasDialogBuilder()
    {
        aBuilder = new AlertDialog.Builder(PlanSzedesKiszedesActivity.this);
        aView = getLayoutInflater().inflate(R.layout.dialog_id, null);
        ZXingScannerView scannerView = aView.findViewById(R.id.qrIdScanner_view);
        scannerView.setResultHandler(this);
        scannerView.setFlash(false);
        scannerView.setAutoFocus(false);
        scannerView.setAspectTolerance(0.5f);
        ArrayList<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formats);
        if(ActivityCompat.checkSelfPermission(context,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            scannerView.startCamera();
        }
        Switch turnLedSwitch = aView.findViewById(R.id.idLED);
        turnLedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                scannerView.setFlash(!scannerView.getFlash());
            }
        });
        switchKeziBevitel = aView.findViewById(R.id.SwitchKeziBevitel);
        switchKeziBevitel.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editTextID.setVisibility(View.VISIBLE);
                textViewID.setVisibility(View.GONE);
            } else {
                editTextID.setVisibility(View.GONE);
                textViewID.setVisibility(View.VISIBLE);
            }
        });

        textViewBeolvasottID = aView.findViewById(R.id.TextViewBeolvasottID);
        textViewID = aView.findViewById(R.id.TextViewID);
        if (controllerPlanSzedes.getAktualisBeolvasott() != null) {
            textViewID.setText(controllerPlanSzedes.getAktualisBeolvasott().getId());
        }

        editTextID = aView.findViewById(R.id.EditTextID);
        editTextID.setText("");
        editTextID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (text.length() == 7) {
                    editTextPlanSzedesID.setText(text.toString());
                    dialog.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        Button btnElozolegBeolvasott = aView.findViewById(R.id.btnElozolegBeolvasott);
        btnElozolegBeolvasott.setOnClickListener(view -> elozoBeolvasottBetoltesBtnClicked());




        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }

    private void elozoBeolvasottBetoltesBtnClicked() {
        editTextPlanSzedesID.setText(textViewID.getText().toString());
        dialog.dismiss();
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getText().isEmpty()){
            this.editTextID.post(new Runnable() {
                @Override
                public void run() {
                    editTextID.setText(result.getText());
                }
            });
        }

    }
}
