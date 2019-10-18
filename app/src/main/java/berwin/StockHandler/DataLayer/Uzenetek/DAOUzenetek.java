package berwin.StockHandler.DataLayer.Uzenetek;

import java.util.ArrayList;
import java.util.Calendar;

import berwin.StockHandler.DataLayer.DAO;

public class DAOUzenetek extends DAO {

    private static DAOUzenetek instance;
    public static DAOUzenetek getInstance() {
        if (instance == null) {
            instance = new DAOUzenetek();
        }
        return instance;
    }

    public boolean uzenetFeltoltes(String kuldo, String uzenet) {
        try {
            querry = "INSERT INTO [dbo].[DeveloperUserMessages] (sender, message, sentdate) VALUES ('" + kuldo + "', '" + uzenet + "', '" + dateFormatHosszu.format(Calendar.getInstance().getTime()) + "')";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    uzenetFeltoltes(kuldo, uzenet);
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } finally {
            dispose();
        }
    }

    public ArrayList<String> getMessages(String keresendo) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            if (keresendo == null) {
                querry = "SELECT TOP 100 sender, message, sentdate FROM [dbo].[DeveloperUserMessages] ORDER BY sentdate DESC";
            } else {
                querry = "SELECT sender, message, sentdate FROM [dbo].[DeveloperUserMessages] WHERE sender LIKE '%" + keresendo + "%' OR message LIKE '%" + keresendo + "%'";
            }
            runHUNGARYQuerry(querry);
            while (getResultSet().next()) {
                messages.add(getResultSet().getString("sender") + " (" + getResultSet().getString("sentdate") + "): " + getResultSet().getString("message"));
            }
            return messages;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                getMessages(keresendo);
            }
            return null;
        } finally {
            dispose();
        }
    }
}
