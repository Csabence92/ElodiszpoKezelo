package berwin.StockHandler.DataLayer.Potlas;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;

public class DAOPotlas extends DAO {

    private static DAOPotlas instance;
    public static DAOPotlas getInstance() {
        if (instance == null) {
            instance = new DAOPotlas();
        }
        return instance;
    }

    public boolean potlasFeltoltes(BeolvasottKiadottHossz beolvasott) {
        try {
            querry = "UPDATE [dbo].[vegcedula] SET hossz = '" + (beolvasott.getBeolvasottHossz() - beolvasott.getKiadottHossz()) + "' WHERE id = '" + beolvasott.getId() + "';" +
                    "INSERT INTO PotlasKiadas VALUES('" + beolvasott.getId() + "', '" + beolvasott.getCikkszam() + "', '" + beolvasott.getSzelesseg()
                    + "', '" + beolvasott.getLokacio() + "', '" + beolvasott.getBeolvasottHossz() + "', '" + beolvasott.getKiadottHossz() + "', '"
                    + (beolvasott.getBeolvasottHossz() - beolvasott.getKiadottHossz()) + "', '" + beolvasott.getLeiras() + "')";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception e) {
            if (reconnectBeforeReRun()) {
                potlasFeltoltes(beolvasott);
            }
            return false;
        } finally {
            dispose();
        }
    }

    public void PotlasMasolas(BeolvasottKiadottHossz beolvasott) {
        try {
            querry = "INSERT INTO PotlasKuldes VALUES(" + beolvasott.getQRCode() + ", '" + beolvasott.getLeiras() + "', '"
                    + beolvasott.getCikkszam() + "', " + beolvasott.getKiadottHossz() + ", " + beolvasott.getSzelesseg() + ")";
            runHUNGARYUpload(querry);
        }
        catch (Exception e) {
            //ErrorSender
        } finally {
            dispose();
        }
    }
}
