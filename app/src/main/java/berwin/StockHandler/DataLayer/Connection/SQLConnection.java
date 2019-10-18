package berwin.StockHandler.DataLayer.Connection;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.DriverManager;
import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Enums.ServerState;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.PresentationLayer.MainActivity;

/**
 * Created by Röhberg Péter on 2018. 11. 05..
 */

public class SQLConnection
{
    public static java.sql.Connection conHUNGARY;
    public static java.sql.Connection conSOP;
    final static Handler handler = new Handler();
    final static int delay = 120000;

    public static void startReconnectTimer(ArrayList<ControllerBase> controllers) {
        handler.postDelayed(new Runnable(){
            public void run(){
                reconnectToServers();
                for (ControllerBase i: controllers) {
                    i.serverStatusWriter();
                }

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public static boolean connectToServers() {
        return connectToHUNGARY() == ServerState.Online && connectToSOP() == ServerState.Online;
    }

    public static boolean reconnectToServers() {
        disconnectFromSOP();
        disconnectFromHUNGARY();
        return (connectToHUNGARY().equals(ServerState.Online) && connectToSOP().equals(ServerState.Online));
    }

    @SuppressLint("NewApi")
    public static ServerState connectToHUNGARY()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conHUNGARY = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.3.10;databaseName=hungary;user=stockiduser;password=Berwin1559;loginTimeout=5;socketTimeout=5");
            return ServerState.Online;
        } catch (Exception e) {
            return ServerState.Offline;
        }
    }

    @SuppressLint("NewApi")
    public static ServerState connectToSOP()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conSOP = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.3.10;databaseName=sop;user=stockiduser;password=Berwin1559;loginTimeout=5;socketTimeout=5");
            return ServerState.Online;
        } catch (Exception e) {
            return ServerState.Offline;
        }
    }

    public static boolean disconnectFromHUNGARY() {
        try {
            if (conHUNGARY != null) {
                conHUNGARY.close();
                return true;
            }
            return false;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean disconnectFromSOP() {
        try {
            if (conSOP != null) {
                conSOP.close();
                return true;
            }
            return false;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static ServerState getServerStatus() {
        try {
            if (!conHUNGARY.isClosed() && !conSOP.isClosed()) {
                return ServerState.Online;
            } else {
                return ServerState.Offline;
            }
        }
        catch (Exception ex) {
            return ServerState.Offline;
        }
    }
}
