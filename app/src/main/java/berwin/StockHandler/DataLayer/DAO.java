package berwin.StockHandler.DataLayer;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottTeljes;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.Others.VersionContoller;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;

/**
 * Created by Röhberg Péter on 2018. 10. 26..
 */

public class DAO extends DAOBase
{
    private static DAO instance;
    public static DAO getInstance()
    {
        if (instance == null){
            instance = new DAO();
        }
        return instance;
    }

    public double getNewVersion(){
        double version = 0;
        try{
            String sqlQuery = "SELECT TOP 1 [version_name] FROM [Hungary].[dbo].[Elodiszpo_version]";
            runHUNGARYQuerry(sqlQuery);
            if(getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    version = Double.parseDouble(getResultSet().getString("version_name"));
                }
            }
            getResultSet().close();
        }catch (SQLException e){
            if (reconnectBeforeReRun()) {
                getNewVersion();
            }else{
                e.printStackTrace();
            }
        }

        return version;
    }

    public String getDeveloperMessages()
    {
        try {
            querry = "SELECT TOP 1 message FROM [dbo].[DeveloperUserCommunicate]";
            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    return getResultSet().getString("message");
                }
            }
            return "Nincs Adat!";
        } catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                getDeveloperMessages();
            }
            return "Nincs Adat!";
        } finally {
            dispose();
        }
    }

    public boolean isNewMessageQuerry() {
        String lastMessageDate = "";
        try {
            querry = "SELECT TOP 1 sentdate FROM [dbo].[DeveloperUserMessages] ORDER BY sentdate DESC";
            runHUNGARYQuerry(querry);

            while (getResultSet().next()) {
                lastMessageDate = getResultSet().getString("sentdate");
            }

            return lastMessageDate.substring(0, 10).equals(dateFormatRovid.format(Calendar.getInstance().getTime()));
        }
        catch (Exception ex)
        {
            if (reconnectBeforeReRun()) {
                isNewMessageQuerry();
            }
            return false;
        }
        finally
        {
            dispose();
        }
    }

    public Object buildBeolvasott (final String id, ActivitiesEnum activitiesEnum)
    {
        try {
            if (id == null)
                querry = "SELECT TOP 1 id, cikkszam, kiadva, szelesseg, hossz, comment FROM [dbo].[vegcedula] ORDER BY id DESC";
            else
                querry = "SELECT * FROM [dbo].[vegcedula] WHERE id = '" + id + "'"; //AND cikkszam = '" + azonosito + "'

            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst())
            {
                while (getResultSet().next())
                {
                    switch (activitiesEnum)
                    {
                        case PotlasActivity: return new BeolvasottKiadottHossz()
                        {{
                            setID(id);
                            setKiadva(getResultSet().getString("kiadva"));
                            setRendelesSzam(getResultSet().getString("Rendeles"));
                            setCikkszam(getResultSet().getString("cikkszam"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setBin(getResultSet().getString("bin"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setNev(getResultSet().getString("nev"));
                            setMozgas(getResultSet().getString("utolso_mozg"));
                        }};
                        case SzabvisszaActivity: return new BeolvasottRendelesSzam()
                        {{
                            setLokacio(getResultSet().getString("bin"));
                            setRendelesSzam(getResultSet().getString("Rendeles"));
                            setID(getResultSet().getString("id"));
                            setCikkszam(getResultSet().getString("cikkszam"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setNev(getResultSet().getString("nev"));
                            setMozgas(getResultSet().getString("utolso_mozg"));
                            setBin(getResultSet().getString("bin"));
                            setKiadva(getResultSet().getString("kiadva"));
                            setLegnagyobbRendeles(getResultSet().getString("legnagyobb_rendeles"));
                        }};
                        case CikkszamSzerkesztoActivity: return new BeolvasottLeiras()
                        {{
                            setID(id);
                            setCikkszam(getResultSet().getString("cikkszam"));
                            if (getCikkszam() != null) setCikkszamPar(DAO.this.getCikkszamPar(getCikkszam()));
                            setLokacio(getResultSet().getString("lokacio"));
                            setBin(getResultSet().getString("bin"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setLeiras(getResultSet().getString("descr"));
                            setNev(getResultSet().getString("nev"));
                            setMozgas(getResultSet().getString("utolso_mozg"));
                        }};
                        case VegszerkesztoActivity: return new BeolvasottTeljes()
                        {{
                            setID(id);
                            setCikkszam(getResultSet().getString("cikkszam"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setLokacio(getResultSet().getString("lokacio"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setBin(getResultSet().getString("bin"));
                            if (getBin() == null || getBin().equals("-") || getBin().isEmpty()) {
                                String newLokacio = getVegcedulaLokacioFromBBM(getCikkszam());
                                if (newLokacio != null && !newLokacio.isEmpty()) {
                                    if (updateVegcedulaLokacioFromBBM(newLokacio, getId())) {
                                        setBin(newLokacio);
                                    }
                                } else {
                                    setBin("-");
                                }
                            }
                            setBevNap(getResultSet().getString("datumm"));
                            setVevo(getResultSet().getString("vevo"));
                            setAllapot(getResultSet().getString("kiadva"));
                            setRendelesSzam(getResultSet().getString("Rendeles"));
                            setNev(getResultSet().getString("nev"));
                            setMozgas(getResultSet().getString("utolso_mozg"));
                            setKiadva(getResultSet().getString("kiadva"));
                        }};
                        default: return new Beolvasott()
                        {{
                            setID(id);
                            setCikkszam(getResultSet().getString("cikkszam"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setLokacio(getResultSet().getString("bin"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setNev(getResultSet().getString("nev"));
                            setMozgas(getResultSet().getString("utolso_mozg"));
                            if (getResultSet().getString("comment") != null && getResultSet().getString("comment").equals("VirtualVeg")) {
                                setVirtualVegE(true);
                            }
                            if (getResultSet().getString("legnagyobb_rendeles") != null) {
                                setLegnagyobbRendeles(getResultSet().getString("legnagyobb_rendeles"));
                            } else {
                                setLegnagyobbRendeles("");
                            }
                        }};
                    }
                }
            }
            return null;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                buildBeolvasott(id, activitiesEnum);
            }
            return null;
        } finally {
            dispose();
        }
    }

    private boolean updateVegcedulaLokacioFromBBM (final String newLokacio, String id) {
        try {
            if (newLokacio != null && !newLokacio.isEmpty()) {
                querry = "UPDATE [dbo].[vegcedula] SET bin = '" + newLokacio + "' WHERE id = '" + id + "'";
                runHUNGARYQuerryHelper(querry);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                updateVegcedulaLokacioFromBBM(newLokacio, id);
            }
            return false;
        } finally {
            disposeHelper();
        }
    }

    private String getVegcedulaLokacioFromBBM(String cikkszam) {
        try {
            querry = "SELECT BIN_NAME FROM [dbo].[SSRFLIT] WHERE ITEM_CODE = '" + cikkszam + "' AND BIN_NAME IS NOT NULL";
            runHUNGARYQuerryHelper(querry);

            while (getResultSetHelper().next()) {
                return getResultSetHelper().getString("BIN_NAME");
            }
            return null;
        } catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                getVegcedulaLokacioFromBBM(cikkszam);
            }
            return null;
        } finally {
            disposeHelper();
        }
    }

    public String getCikkszamPar(String cikkszam) {
        try {
            querry = "SELECT cikkszamPar FROM [dbo].[szovet] WHERE cikkszam = '" + cikkszam + "'";

            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    return getResultSet().getString("cikkszamPar");
                }
            }
            return "Nem találtam!";
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    getCikkszamPar(cikkszam);
                }
                return "Nem találtam!";
            }
            catch (Exception e) {
                return "Nem találtam!";
            }
        }
        finally {
            dispose();
        }
    }


    public ArrayList<String> IDEllenorzes(String id) {
        ArrayList<String> instance = new ArrayList<>();
        try
        {
            querry = "SELECT cikkszam, rendeles, szelesseg, mozgas FROM [dbo].[Vegcedula] WHERE id = '" + id  + "'";
            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next())
                {
                    instance.add(getResultSet().getString("cikkszam"));
                    instance.add(getResultSet().getString("rendeles"));
                    instance.add(getResultSet().getString("szelesseg"));
                    instance.add(getResultSet().getString("mozgas"));
                }
                return instance;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        finally {
           dispose();
        }
    }

    public boolean ujLokacioFeltoltese(String bin, String azonosito) {
        querries = new ArrayList<>(Arrays.asList(
                "UPDATE [SOP].[dbo].[ssrflit_bbm] SET BIN_NAME = '" + bin.toUpperCase() + "' WHERE ITEM_CODE = '" + azonosito + "' AND (LOCATION = 'DEINV' OR LOCATION = 'BRMGG')",
                "UPDATE [dbo].[vegcedula] SET bin = '" + bin.toUpperCase() + "' WHERE cikkszam = '" + azonosito + "'"
        ));

        try {
            for (int i = 0; i < querries.size(); i++) {
                if (i == 0) {
                    runSOPUpload(querries.get(0));
                } else if (i == 1) {
                    runHUNGARYUpload(querries.get(1));
                }
            }
            return true;
        } catch (Exception e) {
            //ErrorLogger
            return false;
        } finally {
            dispose();
        }
    }

    public ArrayList<BeolvasottRendelesSzam> getVirtualVegek(String keresendo, boolean mindenVirtalVegE)
    {
        ArrayList<BeolvasottRendelesSzam> virtualVegek = new ArrayList<>();
        try {
            if (keresendo != null) {
                querry = "SELECT DISTINCT id, cikkszam, szelesseg, lokacio, hossz, Rendeles FROM [dbo].[VirtualVegek] WHERE id LIKE '%" + keresendo + "%' OR cikkszam LIKE '%" + keresendo + "%' ";
            } else {
                querry = "SELECT TOP 25 id, cikkszam, szelesseg, lokacio, hossz, Rendeles FROM [dbo].[VirtualVegek]";
            }
            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    if (!mindenVirtalVegE) {
                        String rendelesSzam = getResultSet().getString("Rendeles");
                        if (rendelesSzam == null || rendelesSzam.equals("-"))
                        {
                            virtualVegek.add(new BeolvasottRendelesSzam() {{
                                setID(getResultSet().getString("id"));
                                setCikkszam(getResultSet().getString("cikkszam"));
                                setSzelesseg(getResultSet().getDouble("szelesseg"));
                                setLokacio(getResultSet().getString("lokacio"));
                                setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            }});
                        }
                    } else {
                        virtualVegek.add(new BeolvasottRendelesSzam() {{
                            setID(getResultSet().getString("id"));
                            setCikkszam(getResultSet().getString("cikkszam"));
                            setSzelesseg(getResultSet().getDouble("szelesseg"));
                            setLokacio(getResultSet().getString("lokacio"));
                            setBeolvasottHossz(getResultSet().getDouble("hossz"));
                            setRendelesSzam(getResultSet().getString("Rendeles"));
                        }});
                    }
                }
                return virtualVegek;
            } else {
                return null;
            }
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    getVirtualVegek(keresendo, mindenVirtalVegE);
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

    public boolean mozgasFeltoltes(VegcedulaNaplo vegcedulaNaplo) {
        try {
            querry = "INSERT INTO [dbo].[VegcedulaNaplo] VALUES('" + vegcedulaNaplo.getId() + "', '" +
                    dateFormatRovid.format(Calendar.getInstance().getTime()) + "', '" + vegcedulaNaplo.getMennyiseg()
                    + "', '" + vegcedulaNaplo.getNev_regi() + "', '" + vegcedulaNaplo.getNev_uj() + "', '" +
                    vegcedulaNaplo.getMozgas_regi() + "', '" + vegcedulaNaplo.getMozgas_uj() + "', '" +
                    vegcedulaNaplo.getBin_regi() + "', '" + vegcedulaNaplo.getBin_uj() + "', '" +
                    vegcedulaNaplo.getRendeles_regi() + "', '" + vegcedulaNaplo.getRendeles_uj() + "', '" +
                    vegcedulaNaplo.getKiadva()+ "')";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception e) {
            if (reconnectBeforeReRun()) {
                mozgasFeltoltes(vegcedulaNaplo);
            }
            return false;
        } finally {
            dispose();
        }
    }
    public double newVersionCode() throws SQLException {
        double newVersion = 0;
        String table = "[dbo].[Elodiszpo_version]";
        String baseCol = "version_name";
        String newColName = "version";
        String selectStr = "SELECT CAST(" + baseCol + " as decimal(9,2)) as " + newColName;
        this.querry = selectStr + " FROM " + table + " WHERE id=(SELECT MAX([id]) FROM " + table + ")";
        runHUNGARYQuerry(this.querry);
        while (getResultSet().next()) {
            newVersion = getResultSet().getDouble(newColName);
        }
        return newVersion;
    }
    public String
    newVersionFileName() throws SQLException{
        String filename = null;
        this.querry = "SELECT LTrim(RTrim([file])) as files FROM [dbo].[Elodiszpo_version] WHERE id=(SELECT MAX([id]) FROM [dbo].[Elodiszpo_version])";
        runHUNGARYQuerry(this.querry);
        while (getResultSet().next()) {
            Log.e("New File name",getResultSet().getString("files"));
            filename = getResultSet ().getString("files");
        }
        return filename;
    }
    public ArrayList<VersionContoller> getAllVersion() throws SQLException {
        ArrayList<VersionContoller> arrayList = new ArrayList<>();
        this.querry = "SELECT LTrim(Rtrim(version_name)) as vn,LTRim(RTrim([file])) as files FROM [dbo].[Elodiszpo_version] ORDER BY vn";
        runHUNGARYQuerry(this.querry);
        int i = 0;
        while (getResultSet().next()){
            arrayList.add(i,new VersionContoller(getResultSet().getString("vn"),getResultSet().getString("files")));
        }
        return arrayList;
    }

}
