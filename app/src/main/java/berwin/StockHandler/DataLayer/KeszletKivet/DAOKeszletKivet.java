package berwin.StockHandler.DataLayer.KeszletKivet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKeszletKivet;
import berwin.StockHandler.LogicLayer.Enums.KeszletKivetEnum;

public class DAOKeszletKivet extends DAO {

    private static DAOKeszletKivet instance;
    public static DAOKeszletKivet getInstance() {
        if (instance == null) {
            instance = new DAOKeszletKivet();
        }
        return instance;
    }

    public BeolvasottKeszletKivet buildBeolvasottKeszletKivet (final String cikkszam, KeszletKivetEnum keszletKivetEnum) {
        String lokacio;
        querries = new ArrayList<>(Arrays.asList(
                "SELECT BIN_NAME, PHYSICAL FROM [dbo].[SSRFLIT] WHERE ITEM_CODE = '" + cikkszam + "' AND (LOCATION = 'BRMGG' OR LOCATION = 'DEINV')",
                "SELECT SUM(meter) FROM [dbo].[keszletkivet] WHERE kikonyveltee != 2 AND cikkszam = '" + cikkszam + "'",
                "SELECT TOP 1 meter, datum FROM keszletkivet WHERE cikkszam = '" + cikkszam + "' ORDER BY datum DESC"
        ));

        BeolvasottKeszletKivet beolvasottKeszletKivet = new BeolvasottKeszletKivet();
        beolvasottKeszletKivet.setCikkszam(cikkszam);
        try {
            if (keszletKivetEnum.equals(KeszletKivetEnum.Kereses)) {
                runSOPQuerry(querries.get(0));
                if (getResultSet().isBeforeFirst()) {
                    while (getResultSet().next()) {
                        if (beolvasottKeszletKivet.getBeolvasottHossz() == 0) {
                            lokacio = getResultSet().getString("BIN_NAME");
                            if (lokacio == null)
                                beolvasottKeszletKivet.setLokacio("Nincs");
                            else
                                beolvasottKeszletKivet.setLokacio(lokacio);
                            beolvasottKeszletKivet.setBeolvasottHossz(Math.round(getResultSet().getDouble("PHYSICAL") * 100.0) / 100.0);
                            beolvasottKeszletKivet.setEddigkiadotthossz(0);
                        }
                    }
                } else {
                    return null;
                }
            } else {
                for (int querryNumber = 0; querryNumber < querries.size(); querryNumber++) {
                    if (querryNumber == 0) {
                        runSOPQuerry(querries.get(querryNumber));
                    } else {
                        runHUNGARYQuerry(querries.get(querryNumber));
                    }
                    if (getResultSet().isBeforeFirst()) {
                        if (querryNumber == 0) {
                            while (getResultSet().next()) {
                                if (beolvasottKeszletKivet.getBeolvasottHossz() == 0) {
                                    lokacio = getResultSet().getString("BIN_NAME");
                                    if (lokacio == null)
                                        beolvasottKeszletKivet.setLokacio("Nincs");
                                    else
                                        beolvasottKeszletKivet.setLokacio(lokacio);

                                    beolvasottKeszletKivet.setBeolvasottHossz(Math.round(getResultSet().getDouble("PHYSICAL") * 100.0) / 100.0);
                                }
                            }
                        } else if (querryNumber == 1) {
                            while (getResultSet().next()) {
                                beolvasottKeszletKivet.setEddigkiadotthossz(getResultSet().getDouble(1));
                            }
                        } else {
                            while (getResultSet().next()) {
                                beolvasottKeszletKivet.setUtolsoKiadottHossz(getResultSet().getDouble("meter"));
                                beolvasottKeszletKivet.setUtolsoKiadasDatum(getResultSet().getString("datum"));
                            }
                        }
                    } else {
                        return null;
                    }
                }

            }
            return beolvasottKeszletKivet;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    buildBeolvasottKeszletKivet(cikkszam, keszletKivetEnum);
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        finally {
            dispose();
        }
    }

    public boolean keszletKivetFeltoltes(BeolvasottKeszletKivet beolvasottKeszletKivet)
    {
        try
        {
            if (beolvasottKeszletKivet.getRendelesSzam() != null && !beolvasottKeszletKivet.getRendelesSzam().isEmpty())
            {
                querry = "INSERT INTO keszletkivet (cikkszam, meter, datum, rendelesszam, utolsokiadas, kikonyveltee) VALUES ('"
                        + beolvasottKeszletKivet.getCikkszam() + "', '" + beolvasottKeszletKivet.getUtolsoKiadottHossz() + "', '"
                        + dateFormatHosszu.format(new Date()) + "', '" + beolvasottKeszletKivet.getRendelesSzam() + "','" + beolvasottKeszletKivet.getUtolsoKiadottHossz() + "', 1)";
            }
            else
            {
                querry = "INSERT INTO keszletkivet (cikkszam, meter, datum, utolsokiadas, kikonyveltee) VALUES ('" + beolvasottKeszletKivet.getCikkszam() + "', '"
                        + beolvasottKeszletKivet.getUtolsoKiadottHossz() + "', '" + dateFormatHosszu.format(new Date()) + "','" + beolvasottKeszletKivet.getUtolsoKiadottHossz() + "', 1)";
            }

            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    keszletKivetFeltoltes(beolvasottKeszletKivet);
                }
                return false;
            }
            catch (Exception e) {
                return false;
            }
        }
        finally {
            dispose();
        }
    }

    public boolean utoljaraKiadottHosszJavitasaFeltoltes(BeolvasottKeszletKivet beolvasottKeszletKivet, String javitottHossz) {
        try {
            querry = "UPDATE [dbo].[keszletkivet] SET meter = '" + javitottHossz + "' WHERE cikkszam = '" + beolvasottKeszletKivet.getCikkszam() + "' AND " +
                    "datum = '" + beolvasottKeszletKivet.getUtolsoKiadasDatum() + "'";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    utoljaraKiadottHosszJavitasaFeltoltes(beolvasottKeszletKivet, javitottHossz);
                }
                return false;
            }
            catch (Exception e) {
                return false;
            }
        }
        finally {
            dispose();
        }
    }

    public ArrayList<String> getSpinnerCikkszamok() {
        ArrayList<String> listSpinner = new ArrayList<>();

        try {
            querry = "SELECT cikkszam FROM [dbo].[keszletkivet] GROUP BY cikkszam ORDER BY SUBSTRING(cikkszam,4,2)";
            runHUNGARYQuerry(querry);

            while (getResultSet().next()) {
                listSpinner.add(getResultSet().getString("cikkszam"));
            }

            return listSpinner;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    getSpinnerCikkszamok();
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        finally {
            dispose();
        }
    }

    public ArrayList<String> getListViewAdatok()
    {
        ArrayList<String> listListView = new ArrayList<>();

        try {
            querry = "SELECT TOP 100 cikkszam, SUM(meter), MAX(datum) FROM [dbo].[keszletkivet] WHERE kikonyveltee != 2 GROUP BY cikkszam ORDER BY MAX(datum) DESC";

            runHUNGARYQuerry(querry);
            while (getResultSet().next())
            {
                listListView.add("Cikkszám: " + getResultSet().getString(1) + " Kiadva: "
                        + getResultSet().getString(2) + "m Dátum: " + getResultSet().getString(3));
            }
            return listListView;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    getListViewAdatok();
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        finally {
            dispose();
        }
    }


}
