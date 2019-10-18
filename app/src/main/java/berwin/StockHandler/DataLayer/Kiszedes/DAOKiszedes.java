package berwin.StockHandler.DataLayer.Kiszedes;

import java.util.Calendar;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.LogicLayer.Enums.OrderStatus;

public class DAOKiszedes extends DAO {

    private static DAOKiszedes instance;
    public static DAOKiszedes getInstance() {
        if (instance == null) {
            instance = new DAOKiszedes();
        }
        return instance;
    }

    public Diszpo buildDiszpo (String rendelesSzam)
    {
        Diszpo newDiszpo = new Diszpo();
        newDiszpo.setRendelesSzam(rendelesSzam);
        try
        {
            querry = "SELECT [dbo].[Order].[Vevo], [dbo].[Order].[Szc], [dbo].[Order].[Szelesseg], ROUND([dbo].[labderok].[vna],2), [dbo].[Order].[GVF] FROM [dbo].[Order] INNER JOIN labderok ON [dbo].[Order].[rendeles] = [dbo].[labderok].[rendeles] WHERE [dbo].[Order].[rendeles] = '" + rendelesSzam + "'";
            runHUNGARYQuerry(querry);

            while (getResultSet().next()) {
                newDiszpo.setLezarva(Integer.parseInt(getResultSet().getString(5)));
                newDiszpo.setVevo(getResultSet().getString(1));
                newDiszpo.setCikkSzam(getResultSet().getString(2));
                newDiszpo.setSzelesseg(getResultSet().getDouble(3));
                newDiszpo.setSzuksegesHossz(getResultSet().getDouble(4));
            }

            querry = "SELECT PHYSICAL, BIN_NAME FROM [dbo].[SSRFLIT] WHERE ITEM_CODE = '" + newDiszpo.getCikkSzam() + "' AND LOCATION = 'BRMGG'";
            runHUNGARYQuerry(querry);

            while (getResultSet().next())
            {
                newDiszpo.setKeszletenHossz(getResultSet().getDouble(1));
                newDiszpo.setLokacio(getResultSet().getString(2));
            }

            querry = "SELECT DISTINCT ITEM_CODE FROM [dbo].[TRIMREQ] WHERE ProductionOrderNo = '" + rendelesSzam + "' AND (SUBSTRING(ITEM_CODE, 4,2) = '01' OR SUBSTRING(ITEM_CODE, 4,2) = '02' OR SUBSTRING(ITEM_CODE, 4,2) = '99' OR SUBSTRING(ITEM_CODE, 4,2) = '12')";
            runHUNGARYQuerry(querry);

            while (getResultSet().next()) {
                Beles instance = new Beles();
                instance.setAnyagKod(getResultSet().getString(1));
                querry = "SELECT BIN_NAME, PHYSICAL FROM [dbo].[SSRFLIT] WHERE ITEM_CODE = '" + instance.getAnyagKod() + "' AND (LOCATION = 'DEINV' OR LOCATION = 'BRMGG') AND BIN_NAME IS NOT NULL";
                runHUNGARYQuerryHelper(querry);
                while (getResultSetHelper().next())
                {
                    instance.setLokacio(getResultSetHelper().getString(1));
                    instance.setKeszletenHossz(getResultSetHelper().getDouble(2));
                }

                querry = "SELECT ROUND(lay_plan_rating,2), [TRIMREQ].[LineNo] FROM [dbo].[TRIMREQ] WHERE ProductionOrderNo = '" + rendelesSzam + "' AND ITEM_CODE = '" + instance.getAnyagKod() + "'";
                runHUNGARYQuerryHelper(querry);
                while (getResultSetHelper().next())
                {
                    instance.setSzuksegesHossz(getResultSetHelper().getDouble(1) * getResultSetHelper().getDouble(2));
                }

                double osszBelesBeolvasott = 0;
                double osszVirtualVegBelesBeolvasott = 0;
                int belesBeolvasottDB = 0;
                int virtualVegBeolvasottBelesDB = 0;

                querry = "SELECT hossz, comment FROM [dbo].[vegcedula] WHERE rendeles = '" + newDiszpo.getRendelesSzam() + "' AND cikkszam = '"  + instance.getAnyagKod() + "'";
                runHUNGARYQuerryHelper(querry);
                while (getResultSetHelper().next())
                {
                    if (getResultSetHelper().getString("comment") != null && getResultSetHelper().getString("comment").equals("VirtualVeg")) {
                        osszVirtualVegBelesBeolvasott += getResultSetHelper().getDouble("hossz");
                        virtualVegBeolvasottBelesDB += 1;
                    }
                    osszBelesBeolvasott += getResultSetHelper().getDouble(1);
                    belesBeolvasottDB += 1;
                }
                instance.setOsszBelesBeolvasott(osszBelesBeolvasott);
                instance.setBelesBeolvasottDB(belesBeolvasottDB);
                instance.setOsszVirtualVegBelesBeolvasott(osszVirtualVegBelesBeolvasott);
                instance.setBeolvasottVirtualVegekBelesSzama(virtualVegBeolvasottBelesDB);

                newDiszpo.addToBelesek(instance);
            }

            double osszSzovetBeolvasott = 0;
            double osszVirtualVegSzovetBeolvasott = 0;
            int szovetBeolvasottDB = 0;
            int virtualVegBeolvasottSzovetDB = 0;

            querry = "SELECT hossz, comment FROM [dbo].[vegcedula] WHERE rendeles = '" + newDiszpo.getRendelesSzam() + "' AND cikkszam = '"  + newDiszpo.getCikkSzam() + "'";
            runHUNGARYQuerry(querry);
            while (getResultSet().next())
            {
                if (getResultSet().getString("comment") != null && getResultSet().getString("comment").equals("VirtualVeg")) {
                    osszVirtualVegSzovetBeolvasott += getResultSet().getDouble("hossz");
                    virtualVegBeolvasottSzovetDB += 1;
                }
                osszSzovetBeolvasott += getResultSet().getDouble("hossz");
                szovetBeolvasottDB += 1;
            }
            newDiszpo.setOsszCikkszamBeolvasott(osszSzovetBeolvasott);
            newDiszpo.setBeolvasottSzovetVegekSzama(szovetBeolvasottDB);
            newDiszpo.setBeolvasottVirtualVegekSzovetSzama(virtualVegBeolvasottSzovetDB);
            newDiszpo.setOsszVirtualVegSzovetBeolvasott(osszVirtualVegSzovetBeolvasott);

            newDiszpo.setAllapot(getRendelesAllapot(newDiszpo));

            return newDiszpo;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    buildDiszpo(rendelesSzam);
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        finally {
            dispose();
        }
    }

    private OrderStatus getRendelesAllapot(Diszpo newDiszpo) {
        if (newDiszpo.getLezarva() == 1) {
            return OrderStatus.Lezarva;
        } else {
            if (newDiszpo.getOsszCikkszamBeolvasott() > 0 || DiszpoBelesekAllapotEllenorzes(newDiszpo)) {
                return OrderStatus.VanAdat;
            }
        }
        return OrderStatus.NincsAdat;
    }

    private boolean DiszpoBelesekAllapotEllenorzes(Diszpo newDiszpo) {
        for (Beles i : newDiszpo.getBelesek()) {
            if (i.getOsszBelesBeolvasott() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public int getSzelessegByID(String id) {
        try {
            querry = "SELECT szelesseg FROM [dbo].[vegcedula] WHERE id = '" + id + "'";
            runHUNGARYQuerry(querry);
            while (getResultSet().next()) {
                return getResultSet().getInt("szelesseg");
            }
            return 0;
        }
        catch (Exception ex) {
            return 0;
        } finally {
            dispose();
        }
    }


    public boolean rendelesSzamLetezikE(String rendelesSzam) {
        try {
            querry = "SELECT [dbo].[Order].[Szc] FROM [dbo].[Order] WHERE [dbo].[Order].[rendeles] = '" + rendelesSzam + "'";
            runHUNGARYQuerry(querry);
            return getResultSet().isBeforeFirst();
        } catch (Exception ex) {
            return false;
        }
        finally {
            dispose();
        }
    }

    public boolean KiszedesLezaras(String rendelesszam) {
        try {
            querry = "UPDATE [dbo].[Order] SET GVF = -1 WHERE Rendeles = '" + rendelesszam + "'";
            runHUNGARYUpload(querry);
            return true;
        } catch (Exception e) {
            //ErrorSender
            return false;
        } finally {
            dispose();
        }
    }

    public boolean KiszedesBefejezesFeltoltes(Beolvasott beolvasott, String rendelesSzam, boolean tullepteE, double tullepesMerteke)
    {
        try {
            if (!tullepteE) {
                querry = "UPDATE [dbo].[vegcedula] SET nev = '" + rendelesSzam + "', mozgas = 'Kiszedes', Rendeles = '" + rendelesSzam + "' WHERE id = '" + beolvasott.getId() + "'";
            } else {
                querry = "UPDATE [dbo].[vegcedula] SET nev = '" + rendelesSzam + "', mozgas = 'Kiszedes', Rendeles = '" + rendelesSzam + "' WHERE id = '" + beolvasott.getId()
                        + "';INSERT INTO [dbo].[vegcedula] (cikkszam, lokacio, szelesseg, hossz, nev, mozgas, comment, nap, descr, vevo, datumm, megj, nyomt, bin, kiadva)\n" +
                        "SELECT cikkszam, lokacio, szelesseg, '" + Math.abs(tullepesMerteke) + "', 'Szabad', 'Bevet', 'VirtualVeg', '"
                        + dateFormatRovid.format(Calendar.getInstance().getTime()) + "', descr, vevo, '" + dateFormatHosszu.format(Calendar.getInstance().getTime())
                        + "', megj, nyomt, bin,kiadva \n" +
                        "FROM [dbo].[vegcedula] WHERE id = '" + beolvasott.getId() + "';";
            }
            runHUNGARYQuerry(querry);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            dispose();
        }
    }



}
