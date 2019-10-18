package berwin.StockHandler.LogicLayer.Naplozas;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.DataLayer.Naplozas.DAONaplozas;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.NaplozasActivity;

public class ControllerNaplozas extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerNaplozas instance;
    public static ControllerNaplozas getInstance() {
        if (instance == null) {
            instance = new ControllerNaplozas();
        }
        return instance;
    }

    private NaplozasActivity naplozasActivity;
    public void setNaplozasActivity(NaplozasActivity naplozasActivity) { this.naplozasActivity = naplozasActivity; }

    @Override
    public DAONaplozas getDAO() { return DAONaplozas.getInstance(); }

    @Override
    public void run(IParamable parameters) {
        this.naploBejegyzesekKiiras(((Parameter<String>)parameters).getElsoParam());
        serverStatusWriter();
    }

    private void naploBejegyzesekKiiras(String id) {
        naplozasActivity.textViewNaplozoCim.setText(id + "-es vég Naplója:");
        ArrayList<VegcedulaNaplo> vegcedulaMozgasok = getDAO().getVegcedulaNaploByID(id);
        if (vegcedulaMozgasok != null) {
            ArrayList<String> listViewMozgasok = new ArrayList<>();
            for (VegcedulaNaplo i: vegcedulaMozgasok) {
                listViewMozgasok.add(i.toString());
            }
            naplozasActivity.listViewNaplo.setAdapter(new ArrayAdapter<String>(naplozasActivity, android.R.layout.simple_expandable_list_item_1, listViewMozgasok));
        }

    }

    @Override
    public void serverStatusWriter() {
        if (naplozasActivity != null) {
            naplozasActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            naplozasActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
        }
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) { }
}
