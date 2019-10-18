package berwin.StockHandler.LogicLayer.KeszletKivet;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.KeszletKivet.DAOKeszletKivet;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKeszletKivet;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Enums.KeszletKivetEnum;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.PresentationLayer.KeszletKivetActivity;
import berwin.StockHandler.R;

public class ControllerKeszletKivet extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerKeszletKivet instance;
    public static ControllerKeszletKivet getInstance() {
        if (instance == null) {
            instance = new ControllerKeszletKivet();
        }
        return instance;
    }

    private android.support.v7.app.AlertDialog dialog;

    private KeszletKivetActivity keszletKivetActivity;
    public KeszletKivetActivity getKeszletKivetActivity() { return this.keszletKivetActivity; }
    public void setKeszletKivetActivity(KeszletKivetActivity keszletKivetActivity) { this.keszletKivetActivity = keszletKivetActivity; }

    private BeolvasottKeszletKivet beolvasottKeszletKivet;
    public void setBeolvasottKeszletKivet(String azonosito, KeszletKivetEnum keszletKivetEnum) { this.beolvasottKeszletKivet = getDAO().buildBeolvasottKeszletKivet(azonosito, keszletKivetEnum); }
    public void setBeolvasottKeszletKivet(BeolvasottKeszletKivet beolvasottKeszletKivet) { this.beolvasottKeszletKivet = beolvasottKeszletKivet; }
    public BeolvasottKeszletKivet getBeolvasottKeszletKivet() { return this.beolvasottKeszletKivet; }

    public ControllerKeszletKivetUIWrite getControllerKeszletKivetUIWrite() {
        return ControllerKeszletKivetUIWrite.getInstance(keszletKivetActivity, beolvasottKeszletKivet, getMainActivity());
    }

    @Override
    public DAOKeszletKivet getDAO() {
        return DAOKeszletKivet.getInstance();
    }

    @Override
    public void run(IParamable parameters) {
        getControllerKeszletKivetUIWrite().spinnerCikkszamKitoltes();
        getControllerKeszletKivetUIWrite().listViewAdatokKitoltes();
        serverStatusWriter();
    }

    public void befejezesAdatFeltoltesKeszletKivet() {
        if (getDAO().keszletKivetFeltoltes(getBeolvasottKeszletKivet()))
            MessageBox("Sikeres feltöltés!");
        else
            MessageBox("Feltöltés sikertelen! Próbáld újra...");
    }

    @Override
    public void serverStatusWriter() {
        if (keszletKivetActivity != null) {
            keszletKivetActivity.textViewKeszletKivetConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            keszletKivetActivity.textViewKeszletKivetConnection.setText(SQLConnection.getServerStatus().toString());
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void alertDialogBuilder(Enum enumerator, IParamable parameter) {
        android.support.v7.app.AlertDialog.Builder aBuilder = new android.support.v7.app.AlertDialog.Builder(keszletKivetActivity);
        View aView = keszletKivetActivity.getLayoutInflater().inflate(R.layout.dialog_keszletkivet_javitas, null);
        TextView textViewJavitas = aView.findViewById(R.id.TextViewKeszletKivetJavitas);
        textViewJavitas.setText("\n" + getBeolvasottKeszletKivet().getCikkszam() + " adatai:\n\nLokáció: " + getBeolvasottKeszletKivet().getLokacio() +
                "\nRaktáron Hossz: " + getBeolvasottKeszletKivet().getBeolvasottHossz() + "m" +
                "\nEddig kiadott Hossz: " + getBeolvasottKeszletKivet().getEddigkiadotthossz() + "m" +
                "\nUtolsó kiadás dátuma: " + getBeolvasottKeszletKivet().getUtolsoKiadasDatum());

        EditText editTextDialogData = aView.findViewById(R.id.EditTextDialogData);
        editTextDialogData.setText(String.valueOf(getBeolvasottKeszletKivet().getUtolsoKiadottHossz()));
        Button btnOK = aView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(view -> {
            if (getDAO().utoljaraKiadottHosszJavitasaFeltoltes(getBeolvasottKeszletKivet(), editTextDialogData.getText().toString())) {
                MessageBox(getBeolvasottKeszletKivet().getCikkszam() + " sikeresen javítva!");
                dialog.dismiss();
            } else {
                MessageBox(getBeolvasottKeszletKivet().getCikkszam() + " javítása sikertelen!");
            }
        });
        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }


}
