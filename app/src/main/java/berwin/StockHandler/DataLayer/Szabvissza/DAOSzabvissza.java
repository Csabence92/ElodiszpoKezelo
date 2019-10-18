package berwin.StockHandler.DataLayer.Szabvissza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;

public class DAOSzabvissza extends DAO {

    private static DAOSzabvissza instance;
    public static DAOSzabvissza getInstance() {
        if (instance == null) {
            instance = new DAOSzabvissza();
        }
        return instance;
    }

    public boolean szabvisszaBefejezesFeltoltes(BeolvasottRendelesSzam beolvasott) {
        try {
            querry = "UPDATE [dbo].[vegcedula] SET nev = 'Szabad', mozgas = 'Szabvissza', Rendeles = '-', hossz = '" + beolvasott.getBeolvasottHossz()
                    + "', szelesseg = '" + beolvasott.getSzelesseg() + "', kiadva = '" + beolvasott.getRendelesSzam() + "', nap = '" + dateFormatRovid.format(Calendar.getInstance().getTime())
                    + "' WHERE id = '" + beolvasott.getId() + "'";
            runHUNGARYUpload(querry);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            dispose();
        }
    }

//    public boolean ujLokacioFeltoltese(String bin, String azonosito) {
//        querries = new ArrayList<>(Arrays.asList(
//                "UPDATE [SOP].[dbo].[ssrflit_bbm] SET BIN_NAME = '" + bin.toUpperCase() + "' WHERE ITEM_CODE = '" + azonosito + "' AND (LOCATION = 'DEINV' OR LOCATION = 'BRMGG')",
//                "UPDATE [dbo].[vegcedula] SET bin = '" + bin.toUpperCase() + "' WHERE cikkszam = '" + azonosito + "'"
//        ));
//
//        try {
//            for (int i = 0; i < querries.size(); i++) {
//                if (i == 0) {
//                    runSOPUpload(querries.get(0));
//                } else if (i == 1) {
//                    runHUNGARYUpload(querries.get(1));
//                }
//            }
//            return true;
//        } catch (Exception e) {
//            //ErrorLogger
//            return false;
//        } finally {
//            dispose();
//        }
//    }
}
