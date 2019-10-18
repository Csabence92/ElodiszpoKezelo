package berwin.StockHandler.LogicLayer.Virtualvegek;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Virtualvegek.DAOVirtualvegek;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.VirtualVegekActivity;

public class ControllerVirtualvegek extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerVirtualvegek instance;
    public static ControllerVirtualvegek getInstance() {
        if (instance == null) {
            instance = new ControllerVirtualvegek();
        }
        return instance;
    }

    @Override
    public DAOVirtualvegek getDAO() { return DAOVirtualvegek.getInstance(); }

    private VirtualVegekActivity virtualVegekActivity;
    public void setVirtualVegekActivity(VirtualVegekActivity virtualVegekActivity) { this.virtualVegekActivity = virtualVegekActivity; }

    @Override
    public void run(IParamable parameters) {
        if (parameters != null) {
            this.virtualisVegekListaKitoltes(((Parameter<String>) parameters).getElsoParam());
        } else {
            this.virtualisVegekListaKitoltes(null);
        }
        serverStatusWriter();
    }

    private void virtualisVegekListaKitoltes(String keresendo)
    {
        ArrayList<String> virtualisVegekListItems = new ArrayList<>();
        ArrayList<BeolvasottRendelesSzam> virtualisVegek = getDAO().getVirtualVegek(keresendo, true);
        if (virtualisVegek != null) {
            for (BeolvasottRendelesSzam i: virtualisVegek) {
                virtualisVegekListItems.add(i.toString());
            }
            virtualVegekActivity.listViewVirtualVegek.setAdapter(new ArrayAdapter<>(virtualVegekActivity, android.R.layout.simple_expandable_list_item_1,
                    virtualisVegekListItems));
        }
    }

    @Override
    public void serverStatusWriter() {
        virtualVegekActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        virtualVegekActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) {

    }
}
