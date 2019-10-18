package berwin.StockHandler.DataLayer.Cikkszamatiro;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;

public class DAOCikkszamatiro extends DAO {

    private static DAOCikkszamatiro instance;
    public static DAOCikkszamatiro getInstance() {
        if (instance == null) {
            instance = new DAOCikkszamatiro();
        }
        return instance;
    }

    public boolean cikkszamAtiroFeltoltes(BeolvasottLeiras beolvasottLeiras) {
        try {
            querry = "UPDATE [dbo].[vegcedula] SET cikkszam = '" + beolvasottLeiras.getCikkszamPar() + "', mozgas = 'CikkszAtir' WHERE id = '" + beolvasottLeiras.getId() + "'";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                cikkszamAtiroFeltoltes(beolvasottLeiras);
            }
            return false;
        } finally {
            dispose();
        }
    }

    public boolean cikkszamAtiroFeltoltes(BeolvasottLeiras beolvasottLeiras, String ujLokacio) {
        try {
            querry = "UPDATE [dbo].[vegcedula] SET cikkszam = '" + beolvasottLeiras.getCikkszamPar() + "', mozgas = 'CikkszAtir', lokacio = '" + ujLokacio + "' WHERE id = '" + beolvasottLeiras.getId() + "'";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                cikkszamAtiroFeltoltes(beolvasottLeiras, ujLokacio);
            }
            return false;
        } finally {
            dispose();
        }
    }
}
