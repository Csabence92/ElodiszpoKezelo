package berwin.StockHandler.LogicLayer.Kiszedes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Kiszedes.DAOKiszedes;
import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Enums.OrderStatus;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Kiszedes.Beles.ControllerKiszedesBelesDelete;
import berwin.StockHandler.LogicLayer.Kiszedes.Beles.ControllerKiszedesBelesReadIn;
import berwin.StockHandler.LogicLayer.Kiszedes.Beles.ControllerKiszedesBelesUIWrite;
import berwin.StockHandler.LogicLayer.Kiszedes.Beles.ControllerKiszedesBelesValidate;
import berwin.StockHandler.LogicLayer.Enums.AlertDialogActions;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.Enums.SzelessegCheckResult;
import berwin.StockHandler.LogicLayer.Kiszedes.Szovet.ControllerKiszedesSzovetDelete;
import berwin.StockHandler.LogicLayer.Kiszedes.Szovet.ControllerKiszedesSzovetReadIn;
import berwin.StockHandler.LogicLayer.Kiszedes.Szovet.ControllerKiszedesSzovetUIWrite;
import berwin.StockHandler.LogicLayer.Kiszedes.Szovet.ControllerKiszedesSzovetValidate;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ControllerKiszedes extends ControllerBase implements IQuerryable, ZXingScannerView.ResultHandler {

    private static ControllerKiszedes instanceControllerKiszedes;

    public static ControllerKiszedes getInstanceControllerKiszedes() {
        if (instanceControllerKiszedes == null) {
            instanceControllerKiszedes = new ControllerKiszedes();
        }
        return instanceControllerKiszedes;
    }

    @Override
    public DAOKiszedes getDAO() {
        return DAOKiszedes.getInstance();
    }

    private KiszedesActivity kiszedesActivity;
    public void setKiszedesActivity(KiszedesActivity kiszedesActivity) {
        this.kiszedesActivity = kiszedesActivity;
    }
    protected KiszedesActivity getKiszedesActivity() {
        return kiszedesActivity;
    }

    private Diszpo aktualisDiszpo;

    public void setAktualisDiszpo(String rendelesSzam) {
        this.aktualisDiszpo = getDAO().buildDiszpo(rendelesSzam);
    }

    public void setAktualisDiszpo(Diszpo diszpo) {
        this.aktualisDiszpo = diszpo;
    }

    public Diszpo getAktualisDiszpo() {
        return aktualisDiszpo;
    }

    private Beles aktualisBeles;

    private void setAktualisBeles(String cikkszam) {
        this.aktualisBeles = getEgyenloBeles(cikkszam, aktualisDiszpo.getBelesek());
    }

    protected void setAktualisBeles(Beles aktualisBeles) {
        this.aktualisBeles = aktualisBeles;
    }

    protected Beles getAktualisBeles() {
        return aktualisBeles;
    }

    private Beolvasott aktualisBeolvasott;

    private void setAktualisBeolvasott(String ID, List<Beolvasott> beolvasottak) {
        this.aktualisBeolvasott = getBeolvasott(ID, beolvasottak);
    }

    protected void setAktualisBeolvasott(Beolvasott aktualisBeolvasott) {
        this.aktualisBeolvasott = aktualisBeolvasott;
    }

    protected Beolvasott getAktualisBeolvasott() {
        return aktualisBeolvasott;
    }

    private ControllerKiszedesSzovetUIWrite controllerKiszedesSzovetUIWrite;

    private void setControllerKiszedesSzovetUIWrite(ControllerKiszedesSzovetUIWrite controllerKiszedesSzovetUIWrite) {
        this.controllerKiszedesSzovetUIWrite = controllerKiszedesSzovetUIWrite;
    }

    private ControllerKiszedesSzovetReadIn controllerKiszedesSzovetReadIn;

    private void setControllerKiszedesSzovetReadIn(ControllerKiszedesSzovetReadIn controllerKiszedesSzovetReadIn) {
        this.controllerKiszedesSzovetReadIn = controllerKiszedesSzovetReadIn;
    }

    protected ControllerKiszedesSzovetValidate controllerKiszedesSzovetValidate;

    private void setControllerKiszedesSzovetValidate(ControllerKiszedesSzovetValidate controllerKiszedesSzovetValidate) {
        this.controllerKiszedesSzovetValidate = controllerKiszedesSzovetValidate;
    }

    private ControllerKiszedesSzovetDelete controllerKiszedesSzovetDelete;

    private void setControllerKiszedesSzovetDelete(ControllerKiszedesSzovetDelete controllerKiszedesSzovetDelete) {
        this.controllerKiszedesSzovetDelete = controllerKiszedesSzovetDelete;
    }


    private ControllerKiszedesBelesUIWrite controllerKiszedesBelesUIWrite;

    private void setControllerKiszedesBelesUIWrite(ControllerKiszedesBelesUIWrite controllerKiszedesBelesUIWrite) {
        this.controllerKiszedesBelesUIWrite = controllerKiszedesBelesUIWrite;
    }

    private ControllerKiszedesBelesReadIn controllerKiszedesBelesReadIn;

    private void setControllerKiszedesBelesReadIn(ControllerKiszedesBelesReadIn controllerKiszedesBelesReadIn) {
        this.controllerKiszedesBelesReadIn = controllerKiszedesBelesReadIn;
    }

    protected ControllerKiszedesBelesValidate controllerKiszedesBelesValidate;

    private void setControllerKiszedesBelesValidate(ControllerKiszedesBelesValidate controllerKiszedesBelesValidate) {
        this.controllerKiszedesBelesValidate = controllerKiszedesBelesValidate;
    }

    private ControllerKiszedesBelesDelete controllerKiszedesBelesDelete;

    private void setControllerKiszedesBelesDelete(ControllerKiszedesBelesDelete controllerKiszedesBelesDelete) {
        this.controllerKiszedesBelesDelete = controllerKiszedesBelesDelete;
    }

    private android.support.v7.app.AlertDialog dialog;
    final int RequestCameraPermissionID = 1001;

    protected ControllerKiszedes() {
    }

    protected Beles getEgyenloBeles(String cikkszam, List<Beles> belesek) {
        return belesek.stream().filter(x -> x.getAnyagKod().equals(cikkszam)).findFirst().orElse(null);
    }

    protected Beolvasott getBelesBeolvasott(Beles instance, String id) {
        return instance.getBelesBeolvasottak().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    private Beolvasott getBeolvasott(String id, List<Beolvasott> beolvasottak) {
        return beolvasottak.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void serverStatusWriter() {
        if (kiszedesActivity != null) {
            kiszedesActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
            kiszedesActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        }
    }

    public void runKiszedes() {
        setControllerKiszedesSzovetReadIn(new ControllerKiszedesSzovetReadIn(aktualisDiszpo, kiszedesActivity, getMainActivity()));
        setControllerKiszedesSzovetValidate(new ControllerKiszedesSzovetValidate(aktualisDiszpo, kiszedesActivity, getMainActivity()));
        setControllerKiszedesSzovetDelete(new ControllerKiszedesSzovetDelete(aktualisDiszpo, kiszedesActivity, getMainActivity()));
        setControllerKiszedesSzovetUIWrite(new ControllerKiszedesSzovetUIWrite(aktualisDiszpo, kiszedesActivity, getMainActivity()));

        setControllerKiszedesBelesReadIn(new ControllerKiszedesBelesReadIn(aktualisDiszpo, kiszedesActivity, getMainActivity(), getAktualisBeles()));
        setControllerKiszedesBelesValidate(new ControllerKiszedesBelesValidate(aktualisDiszpo, kiszedesActivity, getMainActivity(), getAktualisBeles()));
        setControllerKiszedesBelesDelete(new ControllerKiszedesBelesDelete(aktualisDiszpo, kiszedesActivity, getMainActivity(), getAktualisBeles()));
        setControllerKiszedesBelesUIWrite(new ControllerKiszedesBelesUIWrite(aktualisDiszpo, kiszedesActivity, getMainActivity(), getAktualisBeles()));

        this.serverStatusWriter();
        this.kiszedesAlapAdatokKitoltes();
        this.runSzovetKitoltes();
    }

    public void runSzovetKitoltes() {
        if (getAktualisDiszpo() != null) {
            controllerKiszedesSzovetUIWrite.run(new Parameter<>(controllerKiszedesSzovetValidate.szovetSzuksegesHosszTullepveE()));
            if (controllerKiszedesSzovetValidate.vanESzovetVirtualVeg(getAktualisDiszpo().getCikkSzam())
                    && !getAktualisDiszpo().getAllapot().equals(OrderStatus.Lezarva)) {
                getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.VISIBLE);
            } else {
                getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.GONE);
            }
            setBeolvasottTorlesBtnVisibility();
        } else {
            MessageBox("Sikertelen letöltés! Próbáld újra...");
            getKiszedesActivity().finish();
        }
    }

    public void runBelesKitoltes() {
        controllerKiszedesBelesUIWrite.run(new Parameter<>(controllerKiszedesBelesValidate.belesSzuksegesHosszTullepveE()));
        if (controllerKiszedesBelesValidate.vanEBelesVirtalVeg() &&
                !getAktualisDiszpo().getAllapot().equals(OrderStatus.Lezarva)) {
            getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.VISIBLE);
        } else {
            getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.GONE);
        }
        setBeolvasottTorlesBtnVisibility();
    }

    public void runSzovetBeolvasasEllenorzes(String beolvasandoID) {
        IDCheckResult idCheckResult = controllerKiszedesSzovetValidate.ellenorzesIDSzovet(beolvasandoID);
        if (idCheckResult == IDCheckResult.OK) {
            SzelessegCheckResult szelessegCheckResult = controllerKiszedesSzovetValidate.szelessegEllenorzes(beolvasandoID);
            if (szelessegCheckResult != SzelessegCheckResult.Nagyobb) {
                this.runSzovetBeolvasas(beolvasandoID);
            } else {
                this.alertDialogBuilder(AlertDialogActions.SzelessegNagyobb, new Parameter<>(beolvasandoID));
            }
        }
        controllerKiszedesSzovetUIWrite.ellenorzesEredmenyKiiro(idCheckResult);
    }

    public void runSzovetBeolvasas(String beolvasandoID) {
        controllerKiszedesSzovetReadIn.run(new Parameter<>(beolvasandoID));
        controllerKiszedesSzovetUIWrite.runBeolvasasKitoltes(controllerKiszedesSzovetValidate.szovetSzuksegesHosszTullepveE(), controllerKiszedesSzovetValidate.szovetSzuksHosszTullepveErtesithetoE());
        this.hideKeyboard(getKiszedesActivity());
    }

    public void runBelesBeolvasasEllenorzes(String beolvasandoID) {
        IDCheckResult idCheckResult = controllerKiszedesBelesValidate.ellenorzesIDBeles(beolvasandoID);
        if (idCheckResult == IDCheckResult.OK) {
            this.runBelesBeolvasas(beolvasandoID);
        }
        controllerKiszedesBelesUIWrite.ellenorzesEredmenyKiiro(idCheckResult);
    }

    public void runBelesBeolvasas(String beolvasandoID) {
        controllerKiszedesBelesReadIn.run(new Parameter<>(beolvasandoID));
        controllerKiszedesBelesUIWrite.runBeolvasasKitoltes(controllerKiszedesBelesValidate.belesSzuksegesHosszTullepveE(), controllerKiszedesBelesValidate.belesSzuksHosszTullepveErtesithetoE());
        this.hideKeyboard(getKiszedesActivity());
    }

    public void runSzovetBeolvasottTorles(String torlendoID) {
        controllerKiszedesSzovetDelete.run(new Parameter<>(torlendoID));
        controllerKiszedesSzovetUIWrite.runBeolvasasKitoltes(controllerKiszedesSzovetValidate.szovetSzuksegesHosszTullepveE(), controllerKiszedesSzovetValidate.szovetSzuksHosszTullepveErtesithetoE());
    }

    public void runBelesBeolvasottTorles(String torlendoID) {
        controllerKiszedesBelesDelete.run(new Parameter<>(torlendoID));
        controllerKiszedesBelesUIWrite.runBeolvasasKitoltes(controllerKiszedesBelesValidate.belesSzuksegesHosszTullepveE(), controllerKiszedesBelesValidate.belesSzuksHosszTullepveErtesithetoE());
    }

    public void setControllersAktualisBeles(String cikkszam) {
        setAktualisBeles(cikkszam);
        controllerKiszedesBelesUIWrite.setAktualisBeles(getAktualisBeles());
        controllerKiszedesBelesReadIn.setAktualisBeles(getAktualisBeles());
        controllerKiszedesBelesDelete.setAktualisBeles(getAktualisBeles());
        controllerKiszedesBelesValidate.setAktualisBeles(getAktualisBeles());
    }

    public void setControllersAktualisBeolvasott(String ID, boolean szovetE) {
        if (szovetE) {
            setAktualisBeolvasott(ID, aktualisDiszpo.getCikkszamBeolvasott());
            controllerKiszedesSzovetUIWrite.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesSzovetDelete.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesSzovetValidate.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesSzovetReadIn.setAktualisBeolvasott(getAktualisBeolvasott());
        } else {
            setAktualisBeolvasott(ID, aktualisBeles.getBelesBeolvasottak());
            controllerKiszedesBelesUIWrite.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesBelesReadIn.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesBelesDelete.setAktualisBeolvasott(getAktualisBeolvasott());
            controllerKiszedesBelesValidate.setAktualisBeolvasott(getAktualisBeolvasott());
        }
    }

    public void runSzovetBeolvasottKitoltes() {
        controllerKiszedesSzovetUIWrite.szovetBeolvasottKiiras();
    }

    public void runBelesSzuksHosszTullepveErtesites() {
        if (controllerKiszedesBelesValidate != null && controllerKiszedesBelesValidate.belesSzuksHosszTullepveErtesithetoE()) {
            this.alertDialogBuilder(AlertDialogActions.SzuksegesHosszTullepveBeles, null);
        }
    }

    public void runBelesBeolvasottKitoltes() {
        controllerKiszedesBelesUIWrite.belesBeolvasottAdatKiiras();
    }

    @SuppressLint("SetTextI18n")
    private void kiszedesAlapAdatokKitoltes() {
        if (aktualisDiszpo != null) {
            kiszedesActivity.textViewRendelessszam.setText(aktualisDiszpo.getRendelesSzam() + " rendelés adatai:");
            kiszedesActivity.textViewKiszedesCikkszam.setText(aktualisDiszpo.getCikkSzam());
            ArrayList<String> belesekListItems = new ArrayList<>();
            for (Beles i : aktualisDiszpo.getBelesek()) {
                belesekListItems.add(i.getAnyagKod());
            }
            ArrayAdapter<String> belesekAdapter = new ArrayAdapter<>(getMainActivity(), R.layout.item_list, R.id.TxtItem, belesekListItems);
            kiszedesActivity.spinnerKiszedesBelesek.setAdapter(belesekAdapter);
            kiszedesActivity.textViewRendelesiSzelesseg.setText(getDecimalFormat().format(aktualisDiszpo.getSzelesseg()) + "m");
            switch (aktualisDiszpo.getAllapot()) {
                case Lezarva:
                    kiszedesActivity.textViewKiszedesLezarva.setTextColor(Color.rgb(186, 13, 13));
                    kiszedesActivity.btnKiszedesQRScan.setEnabled(false);
                    kiszedesActivity.editTextID.setEnabled(false);
                    kiszedesActivity.btnKiszedesMentesBefejezes.setEnabled(false);
                    kiszedesActivity.btnKiszedesMentesLezaras.setEnabled(false);
                    break;
                case VanAdat:
                    kiszedesActivity.textViewKiszedesLezarva.setTextColor(Color.rgb(186, 139, 13));
                    kiszedesActivity.btnKiszedesQRScan.setEnabled(true);
                    kiszedesActivity.editTextID.setEnabled(true);
                    kiszedesActivity.btnKiszedesMentesBefejezes.setEnabled(true);
                    kiszedesActivity.btnKiszedesMentesLezaras.setEnabled(true);
                    break;
                case NincsAdat:
                    kiszedesActivity.textViewKiszedesLezarva.setTextColor(Color.rgb(13, 186, 36));
                    kiszedesActivity.btnKiszedesQRScan.setEnabled(true);
                    kiszedesActivity.editTextID.setEnabled(true);
                    kiszedesActivity.btnKiszedesMentesBefejezes.setEnabled(true);
                    kiszedesActivity.btnKiszedesMentesLezaras.setEnabled(true);
                    break;
            }
            kiszedesActivity.textViewKiszedesLezarva.setText(aktualisDiszpo.getAllapot().toString());
        }
    }

    protected void setBeolvasottTorlesBtnVisibility() {
        if (!kiszedesActivity.szovetBelesSwitch.isChecked()) {
            SpinnerAdapter apad = kiszedesActivity.spinnerKiszedesCikkszamBeolvasottID.getAdapter();
            if (kiszedesActivity.spinnerKiszedesCikkszamBeolvasottID.getAdapter() == null || aktualisDiszpo.getCikkszamBeolvasott().size() == 0) {
                kiszedesActivity.btnBeolvasottTorles.setVisibility(View.GONE);
                kiszedesActivity.textViewVirtualVegE.setVisibility(View.GONE);
                kiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.GONE);
            } else {
                kiszedesActivity.btnBeolvasottTorles.setVisibility(View.VISIBLE);
                kiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.VISIBLE);
            }
        } else {
            Beles instance = getEgyenloBeles(kiszedesActivity.spinnerKiszedesBelesek.getSelectedItem().toString(), aktualisDiszpo.getBelesek());
            if (kiszedesActivity.spinnerKiszedesAnyagBeolvasottID.getAdapter() == null || instance.getBelesBeolvasottak().size() == 0) {
                kiszedesActivity.btnBeolvasottTorles.setVisibility(View.GONE);
                kiszedesActivity.textViewVirtualVegE.setVisibility(View.GONE);
                kiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.GONE);
            } else {
                kiszedesActivity.btnBeolvasottTorles.setVisibility(View.VISIBLE);
                kiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.VISIBLE);
            }
        }
    }

    public void clearBeolvasottAdatok() {
        getKiszedesActivity().spinnerKiszedesAnyagBeolvasottID.setAdapter(null);
        getKiszedesActivity().spinnerKiszedesCikkszamBeolvasottID.setAdapter(null);
        getKiszedesActivity().textViewKiszedesSzelesseg.setText("");
        getKiszedesActivity().textViewSzelessegHelyesE.setText("");
        getKiszedesActivity().textViewKiszedesBin.setText("");
        getKiszedesActivity().textViewKiszedesHossz.setText("");
        getKiszedesActivity().btnBeolvasottTorles.setVisibility(View.GONE);
    }

    public void kiszedesMentes(boolean ertesites) {
        if (getMentesSzovetEngedely()) {
            this.mentesSzovet();
            getKiszedesActivity().linearLayoutBeolvasottAdatok.setVisibility(View.GONE);
        }

        if (getMentesBelesEngedely()) {
            this.mentesBeles();
            getKiszedesActivity().linearLayoutBeolvasottAdatok.setVisibility(View.GONE);
            getKiszedesActivity().textViewVanAdatBeles.setVisibility(View.GONE);
        }

        if (getMentesBelesEngedely() || getMentesSzovetEngedely()) {
            this.clearBeolvasottAdatok();
            if (ertesites) {
                MessageBox(aktualisDiszpo.getRendelesSzam() + " rendelés sikeresen mentve!");
            }

        } else {
            if (ertesites) {
                MessageBox("Nincs mentendő adat!");
            }
        }
    }

    public void kiszedesLezaras() {
        this.kiszedesMentes(false);
        if (getDAO().KiszedesLezaras(aktualisDiszpo.getRendelesSzam())) {
            MessageBox(aktualisDiszpo.getRendelesSzam() + " lezárása sikeres!");
            kiszedesActivity.finish();
        } else {
            MessageBox("Sikertelen lezárás!");
        }
    }

    private boolean getMentesSzovetEngedely() {
        return getKiszedesActivity().spinnerKiszedesCikkszamBeolvasottID.getAdapter() != null;
    }

    private void mentesSzovet() {
        double osszbeolvasott = aktualisDiszpo.getSzuksegesHossz();
        for (int i = 0; i < aktualisDiszpo.getCikkszamBeolvasott().size(); i++) {
            Beolvasott aktualisBeolvasott = aktualisDiszpo.getCikkszamBeolvasott().get(i);
            osszbeolvasott -= aktualisBeolvasott.getBeolvasottHossz();
            if (osszbeolvasott < 0) {
                final double osszebeolvasottHelper = osszbeolvasott;
                setVegcedulaNaplo(new VegcedulaNaplo() {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-osszebeolvasottHelper);
                    setNev_regi("Szabad");
                    setNev_uj(aktualisDiszpo.getRendelesSzam());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisDiszpo.getRendelesSzam());
                    setKiadva("Kiadva");
                }});
                getDAO().mozgasFeltoltes(getVegcedulaNaplo());
                getDAO().KiszedesBefejezesFeltoltes(aktualisDiszpo.getCikkszamBeolvasott().get(i), aktualisDiszpo.getRendelesSzam(), true, osszbeolvasott);
                osszbeolvasott = 0;
            } else {
                setVegcedulaNaplo(new VegcedulaNaplo() {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-aktualisBeolvasott.getBeolvasottHossz());
                    setNev_regi("Szabad");
                    setNev_uj(aktualisDiszpo.getRendelesSzam());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisDiszpo.getRendelesSzam());
                    setKiadva("Kiadva");
                }});
                getDAO().mozgasFeltoltes(getVegcedulaNaplo());
                getDAO().KiszedesBefejezesFeltoltes(aktualisDiszpo.getCikkszamBeolvasott().get(i), aktualisDiszpo.getRendelesSzam(), false, 0);
            }
        }
    }

    private boolean getMentesBelesEngedely() {
        return getKiszedesActivity().spinnerKiszedesAnyagBeolvasottID.getAdapter() != null;
    }

    private void mentesBeles() {
        double osszBeolvasott = getAktualisBeles().getSzuksegesHossz();
        for (int i = 0; i < getAktualisBeles().getBelesBeolvasottak().size(); i++) {
            Beolvasott aktualisBeolvasott = getAktualisBeles().getBelesBeolvasottak().get(i);
            osszBeolvasott -= aktualisBeolvasott.getBeolvasottHossz();

            if (osszBeolvasott < 0) {
                final double osszebeolvasottHelper = osszBeolvasott;
                getDAO().mozgasFeltoltes(new VegcedulaNaplo() {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-(aktualisBeolvasott.getBeolvasottHossz() + osszebeolvasottHelper));
                    setNev_regi("Szabad");
                    setNev_uj(aktualisDiszpo.getRendelesSzam());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisDiszpo.getRendelesSzam());
                    setKiadva("Kiadva");
                }});

                getDAO().KiszedesBefejezesFeltoltes(getAktualisBeles().getBelesBeolvasottak().get(i), aktualisDiszpo.getRendelesSzam(), true, osszBeolvasott);
                osszBeolvasott = 0;
            } else {
                getDAO().mozgasFeltoltes(new VegcedulaNaplo() {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-aktualisBeolvasott.getBeolvasottHossz());
                    setNev_regi("Szabad");
                    setNev_uj(aktualisDiszpo.getRendelesSzam());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisDiszpo.getRendelesSzam());
                    setKiadva("Kiadva");
                }});
                getDAO().KiszedesBefejezesFeltoltes(getAktualisBeles().getBelesBeolvasottak().get(i), aktualisDiszpo.getRendelesSzam(), false, 0);
            }
        }
        getAktualisBeles().setMentve(true);
    }

    @Override
    public void alertDialogBuilder(Enum alertDialogActions, IParamable parameter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(kiszedesActivity);
        builder.setTitle("Figyelem!");
        switch ((AlertDialogActions) alertDialogActions) {
            case SzovetBeolvasas:
                builder.setMessage(controllerKiszedesSzovetUIWrite.getVirtualSzovetMessage());
                break;
            case BelesBeolvasas:
                builder.setMessage(controllerKiszedesBelesUIWrite.getVirtualBelesekMessage());
                break;
            case SzelessegNagyobb:
                builder.setMessage("A beolvasott szélesség az rendelésben előírtnál keskenyebb!\n\n" +
                        "Rendelési szélesség: " + getAktualisDiszpo().getSzelesseg() + "m\nBeolvasott szélesség: "
                        + getDAO().getSzelessegByID(((Parameter<String>) parameter).getElsoParam()) + "m\n\nSzeretnéd folytatni?");
                break;
            case SzuksegesHosszTullepveSzovet:
                builder.setMessage("A szükséges hossz túllépve! \n\nSzükséges hossz: " + getDecimalFormat().format(getAktualisDiszpo().getSzuksegesHossz())
                        + "m\nBeolvasott hossz: " + getDecimalFormat().format(getAktualisDiszpo().getOsszCikkszamBeolvasott()) + "m\nBeolvasott darabszám: "
                        + getAktualisDiszpo().getBeolvasottSzovetVegekSzama() + "db\n\nVIGYÁZZ! A teljes kiszedést mented! Biztos mented?");
                break;
            case SzuksegesHosszTullepveBeles:
                builder.setMessage("A szükséges hossz túllépve! \n\nSzükséges hossz: " + getDecimalFormat().format(getAktualisBeles().getSzuksegesHossz())
                        + "m\nBeolvasott hossz: " + getDecimalFormat().format(getAktualisBeles().getOsszBelesBeolvasott()) + "m\nBeolvasott darabszám: "
                        + getAktualisBeles().getBelesBeolvasottDB() + "db\n\nVIGYÁZZ! A teljes kiszedést mented! Biztos mented?");
                break;
            case Lezaras:
                builder.setMessage("\nBiztosan lezárod a rendelést? Később nem változtathatsz!");
                break;
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(
                "Igen",
                (dialog, id) -> alertDialogPositiveButtonClicked((AlertDialogActions) alertDialogActions, parameter));
        builder.setNegativeButton(
                "Nem",
                (dialog, id) -> dialog.cancel());
        builder.show();
    }

    private void alertDialogPositiveButtonClicked(AlertDialogActions alertDialogActions, IParamable parameter) {
        switch (alertDialogActions) {
            case SzovetBeolvasas:
                for (int i = 0; i < controllerKiszedesSzovetUIWrite.virtualisSzovetListItems.size(); i++) {
                    getKiszedesActivity().editTextID.setText(controllerKiszedesSzovetUIWrite.virtualisSzovetListItems.get(i).substring(4, 12).trim());
                }
                getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.GONE);
                break;
            case BelesBeolvasas:
                for (int i = 0; i < controllerKiszedesBelesUIWrite.virtualisBelesekListItems.size(); i++) {
                    getKiszedesActivity().editTextID.setText(controllerKiszedesBelesUIWrite.virtualisBelesekListItems.get(i).substring(4, 12).trim());
                }
                getKiszedesActivity().linerLayoutVanVirtVeg.setVisibility(View.GONE);
                break;
            case SzelessegNagyobb:
                this.runSzovetBeolvasas(((Parameter<String>) parameter).getElsoParam());
                break;
            case Lezaras:
                this.kiszedesLezaras();
            default:
                this.kiszedesMentes(true);
        }
    }

    public void beolvasasDialogBuilder() {
        android.support.v7.app.AlertDialog.Builder aBuilder = new android.support.v7.app.AlertDialog.Builder(kiszedesActivity);
        View aView = kiszedesActivity.getLayoutInflater().inflate(R.layout.dialog_beolvasas_sima, null);
        Switch LedSwitch = aView.findViewById(R.id.simaLed);
        ZXingScannerView scannerView;
        scannerView = (ZXingScannerView)aView.findViewById(R.id.qrOlvaso);
        scannerView.setResultHandler(ControllerKiszedes.this);
        scannerView.setAspectTolerance(0.5f);
        ArrayList<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formats);
        LedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        if (!result.getText().isEmpty()) {
            kiszedesActivity.editTextID.setText(result.getText());
            dialog.dismiss();
        }
    }
}
