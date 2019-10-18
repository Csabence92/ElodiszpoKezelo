package berwin.StockHandler.DataLayer.Planszedes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.LogicLayer.Enums.KiszedesStatus;
import berwin.StockHandler.LogicLayer.Enums.PlanStatus;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Rendeles;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.PresentationLayer.PlanSzedesKiszedesActivity;

public class DAOPlanSzedes extends DAO{

    private static DAOPlanSzedes instanceDAOPlanSzedes;
    public static DAOPlanSzedes getInstance() {
        if (instanceDAOPlanSzedes == null) {
            instanceDAOPlanSzedes = new DAOPlanSzedes();
        }
        return instanceDAOPlanSzedes;
    }
    private ArrayList<Rendeles> aktualisRendelesek;
    private ArrayList<Beolvasott> eddigBeolvasottak;

    public String getCikkszam() {
        return cikkszam;
    }

    public void setCikkszam(String cikkszam) {
        this.cikkszam = cikkszam;
    }

    private String cikkszam;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private String message;

    public Plan buildPlan(String plan_id, String inputCikkszam) {
        setCikkszam(inputCikkszam);
        updateDB();
        buildRendelesek(plan_id);
        buildEddigBeolvasottak(plan_id);
        String keresvalt;
        boolean isAll = inputCikkszam.equals("") || inputCikkszam.isEmpty();
        if(isAll) {
            keresvalt="1=1";
        }
        else{
            keresvalt="dbo.plan_szovet_ah.szovet LIKE '%" + inputCikkszam +"%'";
        }


        querry = "SELECT TOP (100) PERCENT dbo.plan_szovet_ah.szovet, dbo.vegcedula_bin.lokacio, dbo.vegcedula_bin.bin, HUNGARY.dbo.cikkszam_keszlet.hossz AS keszlet, dbo.plan_szovet_ah.allapot, MIN(HUNGARY.dbo.vegcedula.szelesseg) As szelesseg, COUNT(DISTINCT HUNGARY.dbo.vegcedula.id) As Count \n" +
                "FROM dbo.plan_szovet_ah LEFT OUTER JOIN\n" +
                "   HUNGARY.dbo.cikkszam_keszlet ON dbo.plan_szovet_ah.szovet = HUNGARY.dbo.cikkszam_keszlet.cikkszam LEFT OUTER JOIN\n" +
                "   HUNGARY.dbo.vegcedula ON dbo.plan_szovet_ah.plan_id = HUNGARY.dbo.vegcedula.Rendeles AND dbo.plan_szovet_ah.plan_id = HUNGARY.dbo.vegcedula.nev AND \n" +
                "   dbo.plan_szovet_ah.szovet = HUNGARY.dbo.vegcedula.cikkszam LEFT OUTER JOIN\n" +
                "   dbo.vegcedula_bin ON dbo.plan_szovet_ah.szovet = dbo.vegcedula_bin.cikkszam\n" +
                "GROUP BY  dbo.plan_szovet_ah.plan_id, dbo.plan_szovet_ah.szovet, dbo.vegcedula_bin.lokacio, dbo.vegcedula_bin.bin, HUNGARY.dbo.cikkszam_keszlet.hossz, dbo.plan_szovet_ah.allapot\n" +
                "HAVING (dbo.plan_szovet_ah.plan_id = '" + plan_id + "' and "+ keresvalt + ")\n" +
                "ORDER BY dbo.plan_szovet_ah.szovet";

        try {
            runSOPQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                ArrayList<Szovet> szovetek = new ArrayList<>();
                while (getResultSet().next()) {
                    szovetek.add(new Szovet() {{
                        setCikkszam(getResultSet().getString(1).toUpperCase().trim());
                        if (getResultSet().getString(2) != null) {
                            setLokacio(getResultSet().getString(2).toUpperCase().trim());
                        } else {
                            setLokacio("Nincs");
                        }
                        if (getResultSet().getString(3) != null) {
                            setBin(getResultSet().getString(3).toUpperCase().trim());
                        } else {
                            setBin("Nincs");
                        }
                        setCount(String.valueOf(getResultSet().getInt(7)));
                        setKeszletenHossz(getResultSet().getDouble(4));
                        setSzelesseg(getResultSet().getDouble(6));
                        switch (getResultSet().getInt(5)) {
                            case 0:
                                setKiszedesStatus(KiszedesStatus.NincsAdat);
                                break;
                            case 1:
                                setKiszedesStatus(KiszedesStatus.MentveHosszabb);
                                break;
                            case 2:
                                setKiszedesStatus(KiszedesStatus.MentveRovidebb);
                                break;
                        }
                        setRendelesek(getRendelesekByCikkszam(getCikkszam()));
                        setSzovetBeolvasottak(getBeolvasottakByCikkszam(getCikkszam()));

                    }});
                }
                return new Plan(plan_id, szovetek, getPlanStatusByPlanID(plan_id));
            }
            return null;
        } catch (Exception e) {
            setMessage(e.getMessage());
            return null;
        } finally {
            dispose();
        }
    }

    private void updateDB() {
        querry = "INSERT INTO [dbo].[plan_szovet_ah] (plan_id, meter, szovet) SELECT plan_id, meter, szovet FROM [dbo].[plan_szovet_ah_plussz]";
        try {
            runSOPUpload(querry);
        }
        catch (Exception ignored) {
        }
        finally {
            dispose();
        }
    }
    private void buildRendelesek(String plan_id) {
        aktualisRendelesek = new ArrayList<>();
        querry = "SELECT szovet, bf_production_order_no, igeny FROM [dbo].[plan_rendeles_szovet] WHERE plan_id = '" + plan_id + "'";

        try {
            runSOPQuerryHelper(querry);
            if (getResultSetHelper().isBeforeFirst()) {
                while (getResultSetHelper().next()) {
                    aktualisRendelesek.add(new Rendeles() {{
                        setCikkszam(getResultSetHelper().getString("szovet").toUpperCase().trim());
                        setRendelesSzam(getResultSetHelper().getString("bf_production_order_no").toUpperCase().trim());
                        setSzuksegesHossz(getResultSetHelper().getDouble("igeny"));
                    }});
                }
            }
        } catch (Exception ignored) {
        } finally {
            disposeHelper();
        }
    }
    private void buildEddigBeolvasottak(String plan_id) {
        eddigBeolvasottak = new ArrayList<>();
        querry = "SELECT * FROM [dbo].[vegcedula] WHERE Rendeles = '" + plan_id + "'";

        try {
            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                while (getResultSet().next()) {
                    eddigBeolvasottak.add(new Beolvasott() {{
                        setID(getResultSet().getString("id"));
                        setCikkszam(getResultSet().getString("cikkszam"));
                        setLokacio(getResultSet().getString("lokacio"));
                        setBin(getResultSet().getString("bin"));
                        setSzelesseg(getResultSet().getDouble("szelesseg"));
                        setBeolvasottHossz(getResultSet().getDouble("hossz"));
                        setLegnagyobbRendeles(getResultSet().getString("legnagyobb_rendeles"));
                    }});
                }
            }

        } catch (Exception ignored) {
        } finally {
            dispose();
        }
    }

    private PlanStatus getPlanStatusByPlanID(String plan_id) {
        querry = "SELECT TOP 1 lezar FROM [dbo].[plan_szovet_ah] WHERE plan_id = '" + plan_id + "' AND lezar = 0";

        try {
            runSOPQuerry(querry);
            if (getResultSet().isBeforeFirst()) {
                return PlanStatus.Nyitott;
            } else {
                return PlanStatus.Zart;
            }
        } catch (Exception e) {
            return PlanStatus.Nyitott;
        } finally {
            dispose();
        }
    }

    private ArrayList<Rendeles> getRendelesekByCikkszam(String cikkszam) {
        ArrayList<Rendeles> rendelesek = new ArrayList<>();
        for (Rendeles i : aktualisRendelesek) {
            if (i.getCikkszam().equals(cikkszam)) {
                rendelesek.add(i);
            }
        }
        return (rendelesek.size() > 0) ? rendelesek : null;
    }

    private ArrayList<Beolvasott> getBeolvasottakByCikkszam(String cikkszam) {
        ArrayList<Beolvasott> beolvasottak = new ArrayList<>();
        if (eddigBeolvasottak != null && eddigBeolvasottak.size() > 0) {
            for (Beolvasott i : eddigBeolvasottak) {
                if (i.getCikkszam().equals(cikkszam)) {
                    beolvasottak.add(i);
                }
            }
        }
        return beolvasottak;
    }

    public boolean planSzedesKiszedesBefejezesFeltoltes( Beolvasott beolvasott, String planSzam, String legnagyobbRendeles,  boolean tullepteE, double megosztasHossz, boolean megosztjaE, String message) {
        try {
            double hossz = beolvasott.getBeolvasottHossz() - megosztasHossz;
            if (!tullepteE || !megosztjaE) { // Itt a megosztashossz = 0
                querry = "UPDATE [dbo].[vegcedula] SET nev='" + planSzam + "', mozgas='Kiszedes', Rendeles ='" + planSzam + "',kiadva='" + planSzam + "',legnagyobb_rendeles ='" + legnagyobbRendeles + "', datumm='" + dateFormatHosszu.format(Calendar.getInstance().getTime()) + "' WHERE id=" + beolvasott.getId() + "";
            } else { // Itt a megosztás hossz = beírt megosztáshossz
                querry = "UPDATE [dbo].[vegcedula] SET hossz='" + hossz + "' , nev='Szabad',mozgas='Vagott',Rendeles='-', legnagyobb_rendeles ='', nap = '" + dateFormatRovid.format(Calendar.getInstance().getTime()) + "',utolso_mozg = '" + beolvasott.getMozgas() + "', kiadva = '" + planSzam + "' WHERE id = " + beolvasott.getId() + "";

                String preaperQuery = "SELECT  cikkszam ,lokacio, szelesseg, comment, descr, vevo, megj, nyomt, bin, utolso_mozg FROM [dbo].[vegcedula] WHERE id='" + beolvasott.getId() + "'" ;
                String cikkszam = null, lokacio = null, comment = "", descr = null, vevo = "", megj = null,  bin = null, utolsomozg = null;
                int nyomt = 0;
                double szelesseg = 0;
                runHUNGARYQuerry(preaperQuery);
                while (getResultSet().next())
                {
                    cikkszam = getResultSet().getString("cikkszam");
                    lokacio = getResultSet().getString("lokacio");
                    comment = getResultSet().getString("comment");
                    descr = getResultSet().getString("descr");
                    vevo = getResultSet().getString("vevo");
                    bin = getResultSet().getString("bin");
                    megj = getResultSet().getString("megj");
                    nyomt = getResultSet().getInt("nyomt");
                    szelesseg = getResultSet().getDouble("szelesseg");
                    utolsomozg = getResultSet().getString("utolso_mozg");
                }
                String mozgas = "Vagott";
                String datumm = dateFormatHosszu.format(Calendar.getInstance().getTime());
                String nap = dateFormatRovid.format(Calendar.getInstance().getTime());
                String querry2 = "INSERT INTO [dbo].[vegcedula](cikkszam, lokacio, szelesseg, comment, descr, vevo, megj, nyomt, bin, hossz, nev, mozgas, nap, datumm, kiadva, Rendeles, legnagyobb_rendeles, utolso_mozg) " +
                        "VALUES('" + cikkszam + "','" + lokacio + "','" + szelesseg + "','" + comment + "','" + descr + "','" + vevo + "','" + megj + "','" + nyomt + "','" + bin + "','" + megosztasHossz + "','" + planSzam + "','" + mozgas + "','" + nap +
                        "','" + datumm + "','" + planSzam + "','" + planSzam + "','" + legnagyobbRendeles + "','" + utolsomozg + "')";

                getResultSet().close();
                runHUNGARYUpload(querry2);
            }
            runHUNGARYUpload(querry);
            return true;
            }

        catch (SQLException e){ setMessage(getMessage() + " \nSQLException " + e.getMessage()); return false;}
        catch (Exception e) { setMessage(getMessage() + " \nException "+ e.getMessage());return false; }
        finally {
            dispose();
        }
    }
    public ArrayList<String> infoQuery(String planSzam,String cikkszam ){
        setMessage("");
        ArrayList<String> resultList = new ArrayList<>();
        double szelesseg = 0;
        double keszletenHossz = 0;
        double beolvasottHossz = 0;
        String szuksegesHossz = "";
        String getHosszQuery = "SELECT hossz FROM [HUNGARY].[dbo].[cikkszam_keszlet] WHERE cikkszam='" + cikkszam + "'";
        try {
            runHUNGARYQuerry(getHosszQuery);
            while(getResultSet().next()){
                keszletenHossz = getResultSet().getDouble("hossz");
            }
            setMessage("Szélesség: " + String.valueOf(szelesseg) + "Készleten hossz: " + String.valueOf(keszletenHossz));
            getResultSet().close();
            String getSzelessegQuery = "SELECT MIN(szelesseg) As szelesseg FROM [HUNGARY].[dbo].[vegcedula] WHERE cikkszam='" + cikkszam + "'";
            runHUNGARYQuerry(getSzelessegQuery);
            while (getResultSet().next()){
                szelesseg = getResultSet().getDouble("szelesseg");
            }
            getResultSet().close();
            String getBeolvasottHosszQuery = "SELECT SUM(hossz) As hossz FROM [HUNGARY].[dbo].[vegcedula] WHERE Rendeles='" + planSzam + "' AND cikkszam='" + cikkszam + "'";
            runHUNGARYQuerry(getBeolvasottHosszQuery);
            while (getResultSet().next()){
                beolvasottHossz = getResultSet().getDouble("hossz");
            }
            getResultSet().close();
            String getSzuksegesHosszQuery = "SELECT meter FROM [SOP].[dbo].[plan_szovet_ah] WHERE plan_id='" + planSzam + "' AND szovet='" + cikkszam + "'";
            runSOPQuerry(getSzuksegesHosszQuery);
            while (getResultSet().next()){
                szuksegesHossz = getResultSet().getString("meter");
            }
            getResultSet().close();

            resultList.add(0,String.valueOf(szelesseg));
            resultList.add(1,String.valueOf(keszletenHossz));
            resultList.add(2,String.valueOf(beolvasottHossz));
            resultList.add(3,szuksegesHossz);
        } catch (SQLException e) {
            setMessage("infoQuery SQLException: " + e.getMessage() + "Msg: " + getMessage());
        }
            return resultList;

    }


    public boolean planSzedesKiszedesCikkszamLezarasFeltoltes(Szovet szovet, String planID) {
        try {
            querry = "UPDATE [dbo].[plan_szovet_ah] SET allapot = '" + szovet.getKiszedesStatus().toInt() + "' WHERE szovet = '" + szovet.getCikkszam() + "' AND plan_id = '" + planID + "'";
            runSOPUpload(querry);
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            dispose();
        }
    }

    public boolean planLezarasFeloldasFeltoltes(String planID, boolean lezarE) {
        try {
            setMessage("");
            boolean lezarhatoE = false;
            String checkQuery = "SELECT TOP 1  lezar FROM [dbo].[plan_szovet_ah] WHERE plan_id ='" + planID + "'";
            runSOPQuerry(checkQuery);
            while(getResultSet().next()) {
                lezarhatoE = getResultSet().getInt("lezar") == 0;
            }
            getResultSet().close();
            if (lezarE) {
                if(lezarhatoE) {
                    querry = "UPDATE [SOP].[dbo].[plan_szovet_ah] SET lezar = 1 WHERE plan_id = '" + planID + "'";
                }
                else
                {
                    setMessage("Ez a Plan már levan zárva!");
                    return false;
                }
            } else {
                if(!lezarhatoE) {
                    querry = "UPDATE [SOP].[dbo].[plan_szovet_ah] SET lezar = 0 WHERE plan_id = '" + planID + "'";
                }
                else
                {
                    setMessage("Ez a plan már felvan oldva!");
                    return false;
                }
            }
            runSOPUploadHelper(querry);
            if(lezarE && lezarhatoE){
                boolean isSent = sendLezarasMail(planID);
                if(!isSent){
                    setMessage(getMessage() + " Sikertelen küldés");
                }
            }
            return true;
        } catch (Exception e) {
            setMessage(e.getMessage());
            return false;
        } finally {
            dispose();
        }
    }

    private boolean sendLezarasMail(String planID) {
        try {
            querry = "UPDATE [SOP].[dbo].[valtozok] SET lezar = '" + planID + "'";
            runSOPUpload(querry);
            return true;
        } catch (Exception e) {
            setMessage(e.getMessage());
            return false;
        } finally {
            dispose();
        }
    }

    public boolean deleteBeolvasottVegFromServer(String torlendoID) {
        querry = "UPDATE [dbo].[vegcedula] SET Rendeles = '-', nev = 'Szabad', Mozgas = 'Bevet', " +
                "kiadva = '', utolso_mozg = 'Bevet', legnagyobb_rendeles = '' WHERE id = '" + torlendoID + "'";
        try {
            runHUNGARYUpload(querry);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            dispose();
        }
    }
    public String getCikkszamByID(int id){
        String cikkszam = "";
        String query = "SELECT cikkszam FROM [HUNGARY].[dbo].[vegcedula] WHERE id='" + id + "'";
        try{
            runHUNGARYQuerry(query);
            while(getResultSet().next()){
                cikkszam = getResultSet().getString("cikkszam");
            }
        }catch (SQLException e){setMessage("getCikkszamByID: " + e.getMessage());}
        return cikkszam;
    }
}
