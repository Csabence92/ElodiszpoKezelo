package berwin.StockHandler.LogicLayer.Potlasok;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;
import berwin.StockHandler.DataLayer.Potlasok.DAOPotlasok;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.PotlasokActivity;

public class ControllerPotlasok extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerPotlasok instance;
    public static ControllerPotlasok getInstance() {
        if (instance == null) {
            instance = new ControllerPotlasok();
        }
        return instance;
    }

    private PotlasokActivity potlasokActivity;
    public void setPotlasokActivity(PotlasokActivity potlasokActivity) { this.potlasokActivity = potlasokActivity; }

    @Override
    public DAOPotlasok getDAO() {
        return DAOPotlasok.getInstance();
    }

    @Override
    public void run(IParamable parameters) {
        this.potlasokListaKitoltes(((Parameter<String>) parameters).getElsoParam());
        serverStatusWriter();
    }

    private void potlasokListaKitoltes(String keresendo)
    {
        ArrayList<String> potlasokListItems = new ArrayList<>();
        ArrayList<BeolvasottKiadottHossz> potlasok = getDAO().getPotlasok(keresendo);
        if (potlasok != null) {
            for (BeolvasottKiadottHossz i: potlasok) {
                potlasokListItems.add(i.toString());
            }
            ArrayAdapter<String> potlasokAdapter = new ArrayAdapter<>(potlasokActivity, android.R.layout.simple_expandable_list_item_1,
                    potlasokListItems);
            potlasokActivity.listPotlasok.setAdapter(potlasokAdapter);
        }
    }

    @Override
    public void serverStatusWriter() {
        potlasokActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
        potlasokActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) { }
}
