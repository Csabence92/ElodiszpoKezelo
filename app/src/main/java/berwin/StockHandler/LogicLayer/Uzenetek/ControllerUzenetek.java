package berwin.StockHandler.LogicLayer.Uzenetek;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.DAOBase;
import berwin.StockHandler.DataLayer.Uzenetek.DAOUzenetek;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.UzenetekActivity;

public class ControllerUzenetek extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerUzenetek instance;
    public static ControllerUzenetek getInstance() {
        if (instance == null) {
            instance = new ControllerUzenetek();
        }
        return instance;
    }

    private UzenetekActivity uzenetekActivity;
    public void setUzenetekActivity(UzenetekActivity uzenetekActivity) { this.uzenetekActivity = uzenetekActivity; }

    @Override
    public DAOUzenetek getDAO() { return DAOUzenetek.getInstance(); }

    @Override
    public void run(IParamable parameters) {
        if  (parameters != null) {
            this.fillUzenetekListView(((Parameter<String>) parameters).getElsoParam());
        } else {
            this.fillUzenetekListView(null);
        }

        serverStatusWriter();
    }

    public boolean uzenetFeltoltes(String kuldo, String uzenet) {
        if (!kuldo.equals("Küldő...")) {
            if (getDAO().uzenetFeltoltes(kuldo, uzenet)) {
                MessageBox("Üzenet elküldve!");
                return true;
            }
            else {
                MessageBox("Hiba történt! Kérlek próbáld újra...");
            }
        } else {
            MessageBox("Válassz küldőt!");
        }
        return false;
    }

    public void fillUzenetekListView(String keresendo)
    {
        ArrayList<String> messages = getDAO().getMessages(keresendo);
        if (messages == null) {
            MessageBox("Hiba történt az üzenetek betöltése során! Kérlek próbáld újra...");
        }
        else {
            uzenetekActivity.listViewUzenetek.setAdapter(new ArrayAdapter<>(uzenetekActivity, android.R.layout.simple_expandable_list_item_1,
                    messages));
        }
    }

    @Override
    public void serverStatusWriter() {
        uzenetekActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        uzenetekActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) { }


}
