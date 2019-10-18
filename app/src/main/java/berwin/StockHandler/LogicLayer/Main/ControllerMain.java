package berwin.StockHandler.LogicLayer.Main;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Enums.ServerState;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;

public class ControllerMain extends ControllerBase implements IQuerryable {

    private static ControllerMain instance;

    private ArrayList<ControllerBase> controllers;
    public void addToControllers(ControllerBase controller) {
        this.controllers.add(controller);
    }

    public static ControllerMain getInstance() {
        if (instance == null) {
            instance = new ControllerMain();
        }
        return instance;
    }

    private ControllerMain() {
        controllers = new ArrayList<>();
    }

    @Override
    public DAO getDAO() {
        return DAO.getInstance();
    }

    @Override
    public void serverStatusWriter() {
        if (SQLConnection.getServerStatus() == ServerState.Online) {
            getMainActivity().textViewConnection.setText(SQLConnection.getServerStatus().toString());
            getMainActivity().textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            getMainActivity().btnKiszedes.setClickable(true);
            getMainActivity().btnPotlas.setClickable(true);
            getMainActivity().btnSzabvissza.setClickable(true);
            getMainActivity().btnVegszerkeszto.setClickable(true);
            getMainActivity().btnCikkszamatiro.setClickable(true);
            getMainActivity().btnKeszletKivet.setClickable(true);
            getMainActivity().btnMessages.setClickable(true);
            fillDevelopmentState();
        } else {
            getMainActivity().textViewConnection.setText(SQLConnection.getServerStatus().toString());
            getMainActivity().textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            getMainActivity().btnKiszedes.setClickable(false);
            getMainActivity().btnPotlas.setClickable(false);
            getMainActivity().btnSzabvissza.setClickable(false);
            getMainActivity().btnVegszerkeszto.setClickable(false);
            getMainActivity().btnCikkszamatiro.setClickable(false);
            getMainActivity().btnKeszletKivet.setClickable(false);
            getMainActivity().btnMessages.setClickable(false);

        }
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) { }

    public void Starting() {
        WifiManager wifiManager = (WifiManager) getMainActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        wifiManager.setWifiEnabled(true);
        connectSQL();
        serverStatusWriter();
        SQLConnection.startReconnectTimer(controllers);
    }

    public boolean connectSQL() {
        WifiManager wifiManager = (WifiManager) getMainActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        wifiManager.setWifiEnabled(true);
        if (!SQLConnection.getServerStatus().equals(ServerState.Online)) {
            return SQLConnection.connectToServers();
        } else {
            return true;
        }
    }

    public boolean disconnectSQL() {
        if (!SQLConnection.getServerStatus().equals(ServerState.Offline)) {
            if (SQLConnection.disconnectFromHUNGARY() && SQLConnection.disconnectFromSOP()) {
                WifiManager wifiManager = (WifiManager) getMainActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                assert wifiManager != null;
                wifiManager.setWifiEnabled(false);
            }
            return true;
        } else {
            return true;
        }
    }

    public boolean reconnectSQL() {
        return (disconnectSQL() && connectSQL());
    }

    public void fillDevelopmentState() {
        getMainActivity().textViewDevelopmentState.setText(getDAO().getDeveloperMessages());
    }

    public boolean isNewMessage() {
        return getDAO().isNewMessageQuerry();
    }

}