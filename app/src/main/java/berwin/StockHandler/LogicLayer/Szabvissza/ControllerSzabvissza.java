package berwin.StockHandler.LogicLayer.Szabvissza;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Enums.ServerState;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.DataLayer.Szabvissza.DAOSzabvissza;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Szabvissza.FunctionControllers.ControllerSzabvisszaUIWrite;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.PresentationLayer.SzabvisszaActivity;
import berwin.StockHandler.R;

public class ControllerSzabvissza extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerSzabvissza instance;
    public static ControllerSzabvissza getInstance() {
        if (instance == null) {
            instance = new ControllerSzabvissza();
        }
        return instance;
    }

    private SzabvisszaActivity szabvisszaActivity;
    protected SzabvisszaActivity getSzabvisszaActivity() { return this.szabvisszaActivity; }
    public void setSzabvisszaActivity(SzabvisszaActivity szabvisszaActivity) { this.szabvisszaActivity = szabvisszaActivity; }

    private BeolvasottRendelesSzam aktualisBeolvasott;
    public BeolvasottRendelesSzam getAktualisBeolvasott() { return this.aktualisBeolvasott; }
    protected void setAktualisBeolvasott(BeolvasottRendelesSzam aktualisBeolvasott) { this.aktualisBeolvasott = aktualisBeolvasott; }
    public void setAktualisBeolvasott(String id) { this.aktualisBeolvasott = (BeolvasottRendelesSzam) getDAO().buildBeolvasott(id, ActivitiesEnum.SzabvisszaActivity); }


    private ControllerSzabvisszaUIWrite getControllerSzabvisszaUIWrite(){
        return ControllerSzabvisszaUIWrite.getInstance(szabvisszaActivity, aktualisBeolvasott);
    }

    @Override
    public DAOSzabvissza getDAO() {
        return DAOSzabvissza.getInstance();
    }

    android.support.v7.app.AlertDialog dialog;

    protected ControllerSzabvissza() { }

    @Override
    public void run(IParamable parameters) {
        getControllerSzabvisszaUIWrite().run(null);
        serverStatusWriter();
    }

    private boolean szabvisszaBefejezes() {
        setVegcedulaNaplo(new VegcedulaNaplo()
        {{
            setID(aktualisBeolvasott.getId());
            setMennyiseg(aktualisBeolvasott.getBeolvasottHossz());
            setNev_regi(aktualisBeolvasott.getNev());
            setNev_uj("Szabad");
            setMozgas_regi(aktualisBeolvasott.getMozgas());
            setMozgas_uj("Szabvissza");
            setBin_regi(aktualisBeolvasott.getBin());
            setBin_uj(aktualisBeolvasott.getBin());
            setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
            setRendeles_uj(aktualisBeolvasott.getRendelesSzam());
            setKiadva(aktualisBeolvasott.getKiadva());
        }});

        if (getDAO().szabvisszaBefejezesFeltoltes(aktualisBeolvasott) && getDAO().mozgasFeltoltes(getVegcedulaNaplo())) {
            MessageBox("Sikeres feltöltés!");
            getControllerSzabvisszaUIWrite().szabvisszaUjKartyaAdatokKitoltes();
            hideKeyboard(getSzabvisszaActivity());
            getSzabvisszaActivity().editViewSzabviszaUjHossz.setEnabled(false);
            return true;
        }
        else {
            MessageBox("Feltöltés sikertelen!");
        }
        return false;
    }


    @Override
    public void serverStatusWriter() {
        if (szabvisszaActivity != null) {
            szabvisszaActivity.textViewSzabivisszaConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            szabvisszaActivity.textViewSzabivisszaConnection.setText(SQLConnection.getServerStatus().toString());
        }
    }

    @Override
    public void alertDialogBuilder(Enum enumerator, IParamable parameter) {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(getSzabvisszaActivity());
        builder.setMessage("\nSzabászatról visszajött hossz: " + getAktualisBeolvasott().getBeolvasottHossz() + "m\n\nBiztosan befejezed?");
        builder.setPositiveButton(
                "Igen",
                (dialog, id) -> szabvisszaBefejezes());
        builder.setTitle("Figyelem!");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton(
                "Nem",
                (dialog, id) -> dialog.cancel());
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    public void ujLokacioAlertDialogBuilder(Activity activity)
    {
        android.support.v7.app.AlertDialog.Builder aBuilder = new android.support.v7.app.AlertDialog.Builder(activity);
        View aView = activity.getLayoutInflater().inflate(R.layout.dialog_ujlokacio, null);
        TextView textViewUjLokacio = aView.findViewById(R.id.TextViewUjLokacio);
        textViewUjLokacio.setText(getAktualisBeolvasott().getId() + "-es vég adatai:\n\nCikkszám: "
                + getAktualisBeolvasott().getCikkszam() + "\nLokáció: " + getAktualisBeolvasott().getLokacio() +
                "\nHossz: " + getAktualisBeolvasott().getBeolvasottHossz() + "m");
        EditText editTextDialogData = aView.findViewById(R.id.EditTextDialogData);
        Button btnOK = aView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(view -> {
            if (getDAO().ujLokacioFeltoltese(editTextDialogData.getText().toString(), aktualisBeolvasott.getCikkszam())) {
                setAktualisBeolvasott(aktualisBeolvasott.getId());
                getControllerSzabvisszaUIWrite().szabvisszaAdatokKitoltes();
                MessageBox("Sikeres lokáció frissítés!");
            }
            else
                MessageBox("Lokáció frissítés sikertelen!");
            dialog.dismiss();
            //if (activity instanceof SzabvisszaActivity) { setSzabvisszaBeolvasott(getSzabvisszaBeolvasott().getId()); getSzabvisszaActivityActivity().szabvisszaAdatokKitoltes(); }
        });
        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }
}
