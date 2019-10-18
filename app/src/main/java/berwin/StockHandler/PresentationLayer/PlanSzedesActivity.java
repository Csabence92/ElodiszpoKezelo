package berwin.StockHandler.PresentationLayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.IOException;
import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.DataLayer.Planszedes.DAOPlanSzedes;
import berwin.StockHandler.LogicLayer.Enums.DialogsEnum;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesUIWrite;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PlanSzedesActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ControllerPlanSzedes controllerPlanSzedes;

    public ListView listViewSzovetek;
    public TextView textViewPlanSzedesSzovet;
    public TextView textViewPlanSzedesPlanSzam;
    public TextView textViewPlanAllapot;
    public TextView textViewConnection;
    public Button btnPlanSzedesUj;
    public Button btnPlanSzedesLezarasEllenorzes;
    public Button btnPlanSzedesLezaras;
    private ImageButton btnRefresh;
    private Button btnPlanSzedesKereses;
    private EditText planSzedesCikkszamEditText;
    public Context context = PlanSzedesActivity.this;
    private final int CAMERA_REQUEST_CODE = 1001;
    private SurfaceView QReaderView;
    private BarcodeDetector barcodeDetector;
    private EditText idEditText;
    private AlertDialog dialog;
    private ZXingScannerView mScannerView;
    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planszedes);
        btnRefresh = (ImageButton) findViewById(R.id.planszedesRefresh);
        planSzedesCikkszamEditText = (EditText) findViewById(R.id.planSzedesCikkszamEditText);
        btnPlanSzedesKereses = (Button) findViewById(R.id.btnPlanSzedesKereses);
        textViewPlanSzedesPlanSzam = findViewById(R.id.TextViewPlanSzedesPlanSzam);
        textViewPlanAllapot = findViewById(R.id.TextViewPlanAllapot);
        textViewConnection = findViewById(R.id.TextViewConnection);
        btnPlanSzedesUj = findViewById(R.id.btnPlanSzedesUj);
        btnPlanSzedesUj.setOnClickListener(v -> QReaderAlertDialog());
        btnPlanSzedesLezarasEllenorzes = findViewById(R.id.btnPlanSzedesLezarasEllenorzes);
        btnPlanSzedesLezarasEllenorzes.setOnClickListener(v -> controllerPlanSzedes.runPlanLezarasEllenorzes());
        btnPlanSzedesLezaras = findViewById(R.id.btnPlanSzedesLezaras);
        btnPlanSzedesLezaras.setOnClickListener(v -> controllerPlanSzedes.runPlanLezaras());
        listViewSzovetek = findViewById(R.id.ListViewSzovetek);
        listViewSzovetek.setOnItemClickListener((parent, view, position, id) -> {
            view.setSelected(true);
            controllerPlanSzedes.setAktualisSzovet(controllerPlanSzedes.getAktualisPlan().getSzovetByPosition(position));
            controllerPlanSzedes.activityStarter(PlanSzedesActivity.this, PlanSzedesKiszedesActivity.class);
        });
        controllerPlanSzedes = ControllerPlanSzedes.getInstanceControllerPlanSzedes();
        controllerPlanSzedes.setPlanSzedesActivity(this);
        controllerPlanSzedes.runSzovetekListaKitoltes();
        btnPlanSzedesKereses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Kereses(planSzedesCikkszamEditText.getText().toString());
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshList();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planszedes, menu);
        MenuItem itemTextViewLehetosegek = menu.findItem(R.id.KiszedesMenuTextViewLehetosegek);
        itemTextViewLehetosegek.setActionView(R.layout.actionbar_textview);
        MenuItem itemReconnect = menu.findItem(R.id.KiszedesMenuItemReconnect);
        itemReconnect.setOnMenuItemClickListener(menuItem -> {
            //controllerKiszedes.reconnectSQL();
            return true;
        });
        MenuItem itemPlanSzedesFeloldas = menu.findItem(R.id.KiszedesMenuItemFeloldas);
        itemPlanSzedesFeloldas.setOnMenuItemClickListener(menuItem -> {
            controllerPlanSzedes.runPlanFeloldas();
            return false;
        });
        MenuItem itemPlanSzedesLezaras = menu.findItem(R.id.KiszedesMenuItemLezaras);
        itemPlanSzedesLezaras.setOnMenuItemClickListener(menuItem -> {
            controllerPlanSzedes.runPlanLezaras();
            return false;
        });

        return true;
    }
    private void RefreshList(){
        AsyncLoading asyncLoading = new AsyncLoading(controllerPlanSzedes.getAktualisPlan().getPlanID(),"",PlanSzedesActivity.this, ActivitiesEnum.PlanSzedes, DialogsEnum.Nothing);
        asyncLoading.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        controllerPlanSzedes.getControllerPlanSzedesUIWrite().setAdapter();
    }

    private void btnPlanSzedesUjClicked() {
        this.finish();
        controllerPlanSzedes.getMainActivity().beolvasasPlanDialogBuilder();
    }
    private void Kereses (String cikkszam)
    {
       for(int i = 0; i <listViewSzovetek.getCount();i++)
        {
            if(listViewSzovetek.getItemAtPosition(i).toString().contains(cikkszam.toUpperCase()))
            {
                controllerPlanSzedes.setAktualisSzovet(controllerPlanSzedes.getAktualisPlan().getSzovetByPosition(i));
                controllerPlanSzedes.activityStarter(PlanSzedesActivity.this, PlanSzedesKiszedesActivity.class);
                break;
            }
        }
    }
    private boolean checkCameraPermission(){
        if(ActivityCompat.checkSelfPermission(PlanSzedesActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlanSzedesActivity.this, new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
        return ActivityCompat.checkSelfPermission(PlanSzedesActivity.this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void QReaderAlertDialog(){
            View aView = getLayoutInflater().inflate(R.layout.qr_scanner_cikkszam,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(PlanSzedesActivity.this);
            builder.setView(aView);

            this.mScannerView = (ZXingScannerView) aView.findViewById(R.id.scanner_view);
            this.mScannerView.setResultHandler(this);
            this.mScannerView.setFlash(false);
            this.mScannerView.setAutoFocus(true);
            this.mScannerView.setAspectTolerance(0.5f);
            ArrayList<BarcodeFormat> formats = new ArrayList<>();
            formats.add(BarcodeFormat.QR_CODE);
            if(this.checkCameraPermission()){
                this.mScannerView.startCamera();
            }
            this.mScannerView.setFormats(formats);

        Switch turnLEDSwitch = (Switch) aView.findViewById(R.id.ledSwitch);
        turnLEDSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mScannerView.setFlash(!mScannerView.getFlash());
            }
        });
            this.idEditText = aView.findViewById(R.id.qrCikkszamID);
            this.idEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() == 7){
                        int id = Integer.parseInt(s.toString());
                        String cikkszam = DAOPlanSzedes.getInstance().getCikkszamByID(id);
                        dialog.dismiss();
                        Kereses(cikkszam);

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dialog = builder.create();
            builder.show();
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getText().isEmpty()){
            this.idEditText.post(new Runnable() {
                @Override
                public void run() {
                    idEditText.setText(result.getText());
                }
            });
        }
    }
}

