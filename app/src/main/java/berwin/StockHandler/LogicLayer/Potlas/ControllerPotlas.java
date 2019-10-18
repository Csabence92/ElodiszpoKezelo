package berwin.StockHandler.LogicLayer.Potlas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.DataLayer.Potlas.DAOPotlas;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Potlas.FunctionControllers.ControllerPotlasUIWrite;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.PresentationLayer.PotlasActivity;
import berwin.StockHandler.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ControllerPotlas extends ControllerBase implements IQuerryable, IRunnable, ZXingScannerView.ResultHandler {

    private static ControllerPotlas instance;
    public static ControllerPotlas getInstance() {
        if (instance == null) {
            instance = new ControllerPotlas();
        }
        return instance;
    }

    @Override
    public DAOPotlas getDAO() { return DAOPotlas.getInstance(); }

    private PotlasActivity potlasActivity;
    protected PotlasActivity getPotlasActivity() { return this.potlasActivity; }
    public void setPotlasActivity(PotlasActivity potlasActivity) { this.potlasActivity = potlasActivity; }

    private BeolvasottKiadottHossz aktualisBeolvasott;
    public void setAktualisBeolvasott(String id) { this.aktualisBeolvasott = (BeolvasottKiadottHossz) getDAO().buildBeolvasott(id, ActivitiesEnum.PotlasActivity); }
    public void setAktualisBeolvasott(BeolvasottKiadottHossz aktualisBeolvasott) { this.aktualisBeolvasott = aktualisBeolvasott; }
    public BeolvasottKiadottHossz getAktualisBeolvasott() { return this.aktualisBeolvasott; }

    private ControllerPotlasUIWrite getControllerPotlasUIWrite() {
        return ControllerPotlasUIWrite.getInstance(potlasActivity, aktualisBeolvasott);
    }

    private AlertDialog dialog;
    private final int RequestCameraPermissionID = 1001;

    @Override
    public void run(IParamable parameters) {
        getControllerPotlasUIWrite().run(null);
    }

    public void potlasFeltoltes()
    {
        setVegcedulaNaplo(new VegcedulaNaplo()
        {{
            setID(getAktualisBeolvasott().getId());
            setMennyiseg(-getAktualisBeolvasott().getKiadottHossz());
            setNev_regi("Szabad");
            setNev_uj("Szabad");
            setMozgas_regi("Bevet");
            setMozgas_uj("Potlas");
            setBin_regi(getAktualisBeolvasott().getBin());
            setBin_uj(getAktualisBeolvasott().getBin());
            setRendeles_regi(getAktualisBeolvasott().getRendelesSzam());
            setRendeles_uj(getAktualisBeolvasott().getLeiras());
            setKiadva(getAktualisBeolvasott().getKiadva());
        }});

        if (getDAO().potlasFeltoltes(getAktualisBeolvasott()) && getDAO().mozgasFeltoltes(getVegcedulaNaplo())) {
            MessageBox("Sikeres pótlás!");
        } else {
            MessageBox("Pótlás sikertelen! Próbáld újra...");
        }
    }

    private void potlasMasolas(String beolvasottQRCode) {
        aktualisBeolvasott.setQRCode(beolvasottQRCode);
        getDAO().PotlasMasolas(aktualisBeolvasott);
        getControllerPotlasUIWrite().potlasMatricaKitoltes();
    }

    @Override
    public void serverStatusWriter() {
        if (potlasActivity != null) {
            potlasActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            potlasActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
        }
    }

    @Override
    public void alertDialogBuilder(Enum enumerator, IParamable parameter) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(potlasActivity);
        View aView = potlasActivity.getLayoutInflater().inflate(R.layout.dialog_beolvasas_sima, null);
        ZXingScannerView scannerView = aView.findViewById(R.id.qrOlvaso);
        scannerView.setResultHandler(ControllerPotlas.this);
        scannerView.setAspectTolerance(0.5f);
        ArrayList<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formats);
        Switch ledSwitch = aView.findViewById(R.id.simaLed);
        ledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    scannerView.setFlash(true);
                }else{
                    scannerView.setFlash(false);
                }
            }
        });
        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }

    @Override
    public void handleResult(Result result) {
        potlasMasolas(result.getText());
        dialog.dismiss();
    }
}
