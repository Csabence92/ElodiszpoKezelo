package berwin.StockHandler.LogicLayer.NamenyiPotlasok;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.DAOBase;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottQRCode;
import berwin.StockHandler.DataLayer.NamenyiPotlasok.DAONamenyiPotlasok;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.KuldottPotlasokActivity;
import berwin.StockHandler.R;

public class ControllerNamenyiPotlasok extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerNamenyiPotlasok instance;
    public static ControllerNamenyiPotlasok getInstance() {
        if (instance == null) {
            instance = new ControllerNamenyiPotlasok();
        }
        return instance;
    }

    @Override
    public DAONamenyiPotlasok getDAO() { return DAONamenyiPotlasok.getInstance(); }

    private KuldottPotlasokActivity kuldottPotlasokActivity;
    public void setKuldottPotlasokActivity(KuldottPotlasokActivity kuldottPotlasokActivity) {
        this.kuldottPotlasokActivity = kuldottPotlasokActivity;
    }

    private AlertDialog dialog;
    private final int RequestCameraPermissionID = 1001;

    @Override
    public void run(IParamable parameters) {
        if (parameters != null) {
            this.elkuldottPotlasokListaKitoltes(((Parameter<String>) parameters).getElsoParam());
        } else {
            this.elkuldottPotlasokListaKitoltes(null);
        }
        serverStatusWriter();
    }

    private void elkuldottPotlasokListaKitoltes(String keresendo) {
        ArrayList<String> elkuldottPotlasokListItems = new ArrayList<>();
        ArrayList<BeolvasottQRCode> beolvasottElkuldottek = getDAO().getElkuldottBeolvasottak(keresendo);
        if (beolvasottElkuldottek != null) {
            for (BeolvasottQRCode i: beolvasottElkuldottek) {
                elkuldottPotlasokListItems.add(i.toString());
            }
            kuldottPotlasokActivity.listElkuldottPotlasok.setAdapter(new ArrayAdapter<>(kuldottPotlasokActivity, android.R.layout.simple_expandable_list_item_1,
                    elkuldottPotlasokListItems));
        }
    }

    @Override
    public void serverStatusWriter() {
        kuldottPotlasokActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        kuldottPotlasokActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
    }

    @Override
    public void alertDialogBuilder(Enum enumerator, IParamable parameter) {
        android.support.v7.app.AlertDialog.Builder aBuilder = new android.support.v7.app.AlertDialog.Builder(kuldottPotlasokActivity);
        View aView = kuldottPotlasokActivity.getLayoutInflater().inflate(R.layout.dialog_beolvasas_sima, null);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(kuldottPotlasokActivity)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        CameraSource cameraSource = new CameraSource
                .Builder(kuldottPotlasokActivity, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setRequestedFps(10.0f)
                .build();


        SurfaceView qrOlvaso = aView.findViewById(R.id.qrOlvaso);
        qrOlvaso.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(kuldottPotlasokActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(kuldottPotlasokActivity,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(qrOlvaso.getHolder());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    kuldottPotlasokActivity.editTextElkuldPotlasokKereses.post(() -> kuldottPotlasokActivity.editTextElkuldPotlasokKereses.setText(qrcodes.valueAt(0).displayValue));
                    dialog.dismiss();
                }
            }
        });

        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }
}
