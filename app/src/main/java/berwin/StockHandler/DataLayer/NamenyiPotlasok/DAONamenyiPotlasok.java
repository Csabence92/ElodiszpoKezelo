package berwin.StockHandler.DataLayer.NamenyiPotlasok;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottQRCode;

public class DAONamenyiPotlasok extends DAO {

    private static DAONamenyiPotlasok instance;
    public static DAONamenyiPotlasok getInstance() {
        if (instance == null) {
            instance = new DAONamenyiPotlasok();
        }
        return instance;
    }

    public ArrayList<BeolvasottQRCode> getElkuldottBeolvasottak(String keresendo) {
        ArrayList<BeolvasottQRCode> potlasok = new ArrayList<>();
        try {
            if (keresendo != null && !keresendo.isEmpty()) {
                querry = "SELECT qrcode, rendeles, cikk, hossz FROM [dbo].[PotlasKuldes] WHERE qrcode LIKE '%" + keresendo + "%' OR rendeles LIKE '%" + keresendo + "%' OR cikk LIKE '%" + keresendo + "%'";
            } else {
                querry = "SELECT TOP 25 qrcode, rendeles, cikk, hossz FROM [dbo].[PotlasKuldes]";
            }

            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    potlasok.add(new BeolvasottQRCode() {{
                        setQRCode(getResultSet().getString("qrcode"));
                        setRendelesSzam(getResultSet().getString("rendeles"));
                        setCikkszam(getResultSet().getString("cikk"));
                        setBeolvasottHossz(getResultSet().getDouble("hossz"));
                    }});
                }
            }
            return potlasok;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    getElkuldottBeolvasottak(keresendo);
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        } finally {
            dispose();
        }
    }
}
