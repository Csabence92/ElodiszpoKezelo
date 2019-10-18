package berwin.StockHandler.DataLayer.Naplozas;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;

public class DAONaplozas extends DAO {

    private static DAONaplozas instance;

    public static DAONaplozas getInstance() {
        if (instance == null) {
            instance = new DAONaplozas();
        }
        return instance;
    }

    public ArrayList<VegcedulaNaplo> getVegcedulaNaploByID(String id) {
        ArrayList<VegcedulaNaplo> vegcedulaMozgasok = new ArrayList<VegcedulaNaplo>();

        try {
            querry = "SELECT * FROM [dbo].[VegcedulaNaplo] WHERE id ='" + id + "' ORDER BY mozgas_datum";
            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next())
                {
                    vegcedulaMozgasok.add(new VegcedulaNaplo() {{
                        setID(getResultSet().getString("id"));
                        setMozgas_datum(getResultSet().getString("mozgas_datum"));
                        setMennyiseg(getResultSet().getDouble("mennyiseg"));
                        setNev_regi(getResultSet().getString("nev_regi"));
                        setNev_uj(getResultSet().getString("nev_uj"));
                        setMozgas_regi(getResultSet().getString("mozgas_regi"));
                        setMozgas_uj(getResultSet().getString("mozgas_uj"));
                        setBin_regi(getResultSet().getString("bin_regi"));
                        setBin_uj(getResultSet().getString("bin_uj"));
                        setRendeles_regi(getResultSet().getString("rendeles_regi"));
                        setRendeles_uj(getResultSet().getString("rendeles_uj"));
                        setKiadva(getResultSet().getString("kiadva"));
                    }});
                }
            }
            return vegcedulaMozgasok;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                getVegcedulaNaploByID(id);
            }
            return null;
        } finally {
            dispose();
        }
    }
}
