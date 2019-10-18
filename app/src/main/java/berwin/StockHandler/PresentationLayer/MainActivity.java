package berwin.StockHandler.PresentationLayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Enums.DialogsEnum;
import berwin.StockHandler.LogicLayer.KeszletKivet.ControllerKeszletKivet;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.Main.ControllerMain;
import berwin.StockHandler.LogicLayer.Naplozas.ControllerNaplozas;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.LogicLayer.Potlas.ControllerPotlas;
import berwin.StockHandler.LogicLayer.Szabvissza.ControllerSzabvissza;
import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.LogicLayer.Uzenetek.ControllerUzenetek;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.Others.Update;
import berwin.StockHandler.Others.VersionContoller;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ControllerMain controllerMain;
    private ControllerKiszedes controllerKiszedes;
    private ControllerPlanSzedes controllerPlanSzedes;
    private ControllerSzabvissza controllerSzabvissza;
    private ControllerNaplozas controllerNaplozas;
    private ControllerUzenetek controllerUzenetek;
    private ControllerPotlas controllerPotlas;
    private ControllerSzerkeszto controllerSzerkeszto;
    private ControllerKeszletKivet controllerKeszletKivet;

    public SwitchCompat switchRendeles;
    public Switch switchKeziBevitel;

    public SurfaceView qrOlvaso;

    public EditText editTextID;
    public EditText editTextPlan;
    public EditText editTextCikkszam;

    public TextView textViewConnection;
    public TextView textViewMainUjUzenet;
    public TextView textViewID;
    public TextView textViewBeolvasottID;
    public TextView textViewDevelopmentState;
    public TextView textViewElozoPlan;

    public Button btnKiszedes;
    public Button btnPotlas;
    public Button btnSzabvissza;
    public Button btnKeszletKivet;
    public Button btnVegszerkeszto;
    public Button btnMessages;
    public Button btnCikkszamatiro;
    public Button btnNaplozas;
    public Button btnPlanSzedes;
    public Button btnReleaseNote;
    public Button btnUpdate;

    AlertDialog.Builder aBuilder;
    public AlertDialog dialog;
    public View aView;
    public AsyncLoading asyncLoading;
    public BarcodeDetector barcodeDetector;
    public CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    private ArrayList<ControllerBase> controllers;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewConnection = findViewById(R.id.TextViewConnection);
        textViewMainUjUzenet = findViewById(R.id.TextViewMainUjUzenet);
        textViewDevelopmentState = findViewById(R.id.TextViewDevelopmentState);

        btnKiszedes = findViewById(R.id.btnKiszedes);
        btnKiszedes.setOnClickListener(view -> beolvasasDialogBuilder(ActivitiesEnum.KiszedesActivity));

        btnPlanSzedes = findViewById(R.id.btnPlanSzedes);
        btnPlanSzedes.setOnClickListener(view -> beolvasasPlanDialogBuilder());

        btnPotlas = findViewById(R.id.btnPotlas);
        btnPotlas.setOnClickListener(view -> beolvasasDialogBuilder(ActivitiesEnum.PotlasActivity));

        btnSzabvissza = findViewById(R.id.btnSzabvissza);
        btnSzabvissza.setOnClickListener(view -> beolvasasDialogBuilder(ActivitiesEnum.SzabvisszaActivity));

        btnKeszletKivet = findViewById(R.id.btnKeszletKivet);
        btnKeszletKivet.setOnClickListener(view -> controllerKeszletKivet.activityStarter(MainActivity.this, KeszletKivetActivity.class));

        btnVegszerkeszto = findViewById(R.id.btnVegszerkeszto);
        btnVegszerkeszto.setOnClickListener(view -> beolvasasDialogBuilder(ActivitiesEnum.VegszerkesztoActivity));

        btnMessages = findViewById(R.id.btnMessages);
        btnMessages.setOnClickListener(view -> controllerUzenetek.activityStarter(MainActivity.this, UzenetekActivity.class));

        btnCikkszamatiro = findViewById(R.id.btnCikkszamatiro);
        btnCikkszamatiro.setOnClickListener(view -> controllerMain.activityStarter(MainActivity.this, CikkszamSzerkesztoActivity.class));

        btnNaplozas = findViewById(R.id.btnNaplozas);
        btnNaplozas.setOnClickListener(view -> beolvasasDialogBuilder(ActivitiesEnum.NaplozasActivity));

        btnReleaseNote = findViewById(R.id.btnReleaseNote);
        btnReleaseNote.setOnClickListener(v -> controllerMain.MessageBox("Fejlesztés alatt..."));

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> controllerMain.activityStarter(MainActivity.this,UpdateActivity.class));
        controllerMain = ControllerMain.getInstance();
        controllerMain.setMainActivity(this);
        controllerMain.addToControllers(controllerMain);
        controllerKiszedes = ControllerKiszedes.getInstanceControllerKiszedes();
        controllerKiszedes.setMainActivity(this);
        controllerMain.addToControllers(controllerKiszedes);
        controllerPlanSzedes = ControllerPlanSzedes.getInstanceControllerPlanSzedes();
        controllerPlanSzedes.setMainActivity(this);
        controllerMain.addToControllers(controllerPlanSzedes);
        controllerSzabvissza = ControllerSzabvissza.getInstance();
        controllerSzabvissza.setMainActivity(this);
        controllerMain.addToControllers(controllerSzabvissza);
        controllerNaplozas = ControllerNaplozas.getInstance();
        controllerNaplozas.setMainActivity(this);
        controllerMain.addToControllers(controllerNaplozas);
        controllerUzenetek = ControllerUzenetek.getInstance();
        controllerUzenetek.setMainActivity(this);
        controllerPotlas = ControllerPotlas.getInstance();
        controllerPotlas.setMainActivity(this);
        controllerMain.addToControllers(controllerPotlas);
        controllerSzerkeszto = ControllerSzerkeszto.getInstance();
        controllerSzerkeszto.setMainActivity(this);
        controllerMain.addToControllers(controllerSzerkeszto);
        controllerKeszletKivet = ControllerKeszletKivet.getInstance();
        controllerKeszletKivet.setMainActivity(this);
        controllerMain.addToControllers(controllerKeszletKivet);
        controllerMain.Starting();
        checkUpdateAvailabel();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemReconnect = menu.findItem(R.id.MainMenuItemReconnect);
        itemReconnect.setOnMenuItemClickListener(menuItem -> {
            asyncLoading = new AsyncLoading(MainActivity.this, ActivitiesEnum.MainActivity);
            asyncLoading.execute();
            return true;
        });
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        controllerMain.Starting();
        vanNewMessageKiiras();
        controllerMain.fillDevelopmentState();
    }

    public void vanNewMessageKiiras() {
        if (controllerMain.isNewMessage()) {
            textViewMainUjUzenet.setVisibility(View.VISIBLE);
        } else {
            textViewMainUjUzenet.setVisibility(View.GONE);
        }
    }

    public void beolvasasPlanDialogBuilder() {
        aBuilder = new AlertDialog.Builder(MainActivity.this);
        aView = getLayoutInflater().inflate(R.layout.dialog_plan, null);

        textViewElozoPlan = aView.findViewById(R.id.TextViewElozoPlan);
        elozolegBeolvasottKitoltes(ActivitiesEnum.PlanSzedes);

        editTextPlan = aView.findViewById(R.id.EditTextPlan);
        editTextCikkszam = aView.findViewById(R.id.EditTextCikkszam);
        editTextPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (text.length() == 3) {
                    String inputCikkszam = editTextCikkszam.getText().toString();
                    activityLuncherHelper(ActivitiesEnum.PlanSzedes);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        Button btnElozolegBeolvasott = aView.findViewById(R.id.btnElozolegBeolvasott);
        btnElozolegBeolvasott.setOnClickListener(v -> elozolegBeolvasottLuncherHelper(ActivitiesEnum.PlanSzedes));

        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void beolvasasDialogBuilder(final ActivitiesEnum activitiesEnum)
    {
        aBuilder = new AlertDialog.Builder(MainActivity.this);
        ZXingScannerView scannerView;
        Switch turnLedSwitch;
        switch (activitiesEnum) {
            case KiszedesActivity : aView = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_rendelesszam, null);
               scannerView = (ZXingScannerView) this.aView.findViewById(R.id.rendelessszamScanner_view);
                turnLedSwitch = aView.findViewById(R.id.rendelesLED);
                break;

            default: aView = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_id, null);
                scannerView = (ZXingScannerView) this.aView.findViewById(R.id.qrIdScanner_view);
                turnLedSwitch = aView.findViewById(R.id.idLED);
            break;
        }

        scannerView.setResultHandler(MainActivity.this);
        scannerView.setFlash(false);
        scannerView.setAspectTolerance(0.5f);
        ArrayList<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formats);
        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
            scannerView.startCamera();
        }
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
                if (activitiesEnum.equals(ActivitiesEnum.KiszedesActivity)) {
                    textViewBeolvasottID.setText("Új Rendelésszám:");
                } else {
                    textViewBeolvasottID.setText("Új ID:");
                }
            } else {
                editTextID.setVisibility(View.GONE);
                textViewID.setVisibility(View.VISIBLE);
                if (activitiesEnum.equals(ActivitiesEnum.KiszedesActivity)) {
                    textViewBeolvasottID.setText("Előző Rendelésszám:");
                } else {
                    textViewBeolvasottID.setText("Előző ID:");
                }

            }
        });

        textViewBeolvasottID = aView.findViewById(R.id.TextViewBeolvasottID);
        textViewID = aView.findViewById(R.id.TextViewID);
        elozolegBeolvasottKitoltes(activitiesEnum);

        editTextID = aView.findViewById(R.id.EditTextID);
        editTextID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (text.length() == 7 && !activitiesEnum.equals(ActivitiesEnum.KiszedesActivity)) {
                    activityLuncherHelper(activitiesEnum);
                } else if (text.length() == 10 && activitiesEnum.equals(ActivitiesEnum.KiszedesActivity)){
                    activityLuncherHelper(activitiesEnum);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        Button btnElozolegBeolvasott = aView.findViewById(R.id.btnElozolegBeolvasott);
        btnElozolegBeolvasott.setOnClickListener(view -> elozolegBeolvasottLuncherHelper(activitiesEnum));




        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }

    private void activityLuncherHelper(ActivitiesEnum activitiesEnum)
    {
        switch (activitiesEnum)
        {
            case KiszedesActivity : if (editTextID.getText().toString().length() == 10)
            {
                asyncLoading = new AsyncLoading(editTextID.getText().toString(),"", MainActivity.this, ActivitiesEnum.KiszedesActivity, DialogsEnum.Nothing);
                asyncLoading.execute();
                dialog.dismiss();
                break;
            }

            case PotlasActivity: if (editTextID.getText().toString().length() == 7)
            {
                controllerPotlas.setAktualisBeolvasott(editTextID.getText().toString());
                if (controllerPotlas.getAktualisBeolvasott() != null) {
                    controllerPotlas.activityStarter(MainActivity.this, PotlasActivity.class);
                    dialog.dismiss();
                } else {
                    controllerSzabvissza.MessageBox("Nem találtam ilyen véget! Próbáld újra...");
                    editTextID.setText("");
                }
                break;
            }

            case SzabvisszaActivity: if (editTextID.getText().toString().length() == 7)
            {
                controllerSzabvissza.setAktualisBeolvasott(editTextID.getText().toString());
                if (controllerSzabvissza.getAktualisBeolvasott() != null) {
                    controllerSzabvissza.activityStarter(MainActivity.this, SzabvisszaActivity.class);
                    dialog.dismiss();
                } else {
                    controllerSzabvissza.MessageBox("Nem találtam ilyen véget! Próbáld újra...");
                    editTextID.setText("");
                }
                break;
            }

            case VegszerkesztoActivity: if (editTextID.getText().toString().length() == 7)
            {
                if (editTextID.getText().toString().startsWith("1")) {
                    controllerSzerkeszto.setAktualisBeolvasottTeljes(editTextID.getText().toString());
                    controllerSzerkeszto.activityStarter(MainActivity.this, VegszerkesztoActivity.class);
                } else {
                    controllerSzerkeszto.setAktualisBeolvasottQRCode(editTextID.getText().toString());
                    controllerSzerkeszto.activityStarter(MainActivity.this, MatricaSzerkesztoActivity.class);
                }
                dialog.dismiss();
                break;
            }

            case NaplozasActivity: if (editTextID.getText().toString().length() == 7)
            {
                controllerNaplozas.activityStarter(MainActivity.this, NaplozasActivity.class);
                dialog.dismiss();
                break;
            }

            case PlanSzedes: if (editTextPlan.getText().toString().length() == 3) {

                asyncLoading = new AsyncLoading(editTextPlan.getText().toString(),editTextCikkszam.getText().toString().toUpperCase(), MainActivity.this, ActivitiesEnum.PlanSzedes, DialogsEnum.Nothing);
                asyncLoading.execute();
                dialog.dismiss();
                break;
            }
        }
    }

    private void elozolegBeolvasottLuncherHelper(ActivitiesEnum activitiesEnum) {
        switch (activitiesEnum)
        {
            case KiszedesActivity :
                if (controllerKiszedes.getAktualisDiszpo() != null) {
                    controllerKiszedes.activityStarter(MainActivity.this, KiszedesActivity.class);
                    dialog.dismiss();
                }
                break;
            case PotlasActivity:
                if (controllerPotlas.getAktualisBeolvasott() != null) {
                    controllerPotlas.activityStarter(MainActivity.this, PotlasActivity.class);
                    dialog.dismiss();
                }
                break;
            case SzabvisszaActivity:
                if (controllerSzabvissza.getAktualisBeolvasott() != null) {
                    controllerSzabvissza.activityStarter(MainActivity.this,SzabvisszaActivity.class);
                    dialog.dismiss();
                }
                break;
            case VegszerkesztoActivity:
                if (controllerSzerkeszto.getAktualisBeolvasottTeljes() != null) {
                    controllerSzerkeszto.activityStarter(MainActivity.this, VegszerkesztoActivity.class);
                    dialog.dismiss();
                }
                else if (controllerSzerkeszto.getAktualisBeolvasottQRCode() != null) {
                    controllerSzerkeszto.activityStarter(MainActivity.this, MatricaSzerkesztoActivity.class);
                    dialog.dismiss();
                }
                break;
            case PlanSzedes:
                if (controllerPlanSzedes.getAktualisPlan() != null) {
                    controllerPlanSzedes.activityStarter(MainActivity.this, PlanSzedesActivity.class);
                    dialog.dismiss();
                }
                break;
            case NaplozasActivity: controllerMain.MessageBox("Naplózásnál nem elérhető ez a funkció! Olvasd be a véget...");
        }
    }

    private void elozolegBeolvasottKitoltes(ActivitiesEnum activitiesEnum) {
        switch (activitiesEnum)
        {
            case KiszedesActivity :
                if (controllerKiszedes.getAktualisDiszpo() != null) {
                    textViewID.setText(controllerKiszedes.getAktualisDiszpo().getRendelesSzam());
                }
                break;
            case PotlasActivity:
                if (controllerPotlas.getAktualisBeolvasott() != null) {
                    textViewID.setText(controllerPotlas.getAktualisBeolvasott().getId());
                }
                break;
            case SzabvisszaActivity:
                if (controllerSzabvissza.getAktualisBeolvasott() != null) {
                    textViewID.setText(controllerSzabvissza.getAktualisBeolvasott().getId());
                }
                break;
            case VegszerkesztoActivity:
                if (controllerSzerkeszto.getAktualisBeolvasottTeljes() != null) {
                    textViewID.setText(controllerSzerkeszto.getAktualisBeolvasottTeljes().getId());
                }
                else if (controllerSzerkeszto.getAktualisBeolvasottQRCode() != null) {
                    textViewID.setText(controllerSzerkeszto.getAktualisBeolvasottQRCode().getId());
                }
                break;
            case PlanSzedes:
                if (controllerPlanSzedes.getAktualisPlan() != null) {
                    textViewElozoPlan.setText(controllerPlanSzedes.getAktualisPlan().toString());
                }
                break;
        }
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getText().isEmpty()){
            editTextID.post(() -> editTextID.setText(result.getText()));
        }

    }
    private void checkUpdateAvailabel(){
        VersionContoller versionContoller = new VersionContoller(MainActivity.this);
        versionContoller.NewVersionIsAvalible();

    }

    private void NewVersionAvailabelAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Új verzió elérhető: " + "\nFrissíted?");
        builder.setPositiveButton("Igen", (dialogInterface, i) -> {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Nem", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create();
        builder.show();
    }
}