package berwin.StockHandler.DataLayer.Potlasok;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottKiadottHossz;

public class DAOPotlasok extends DAO {

    private static DAOPotlasok instance;

    public static DAOPotlasok getInstance() {
        if (instance == null) {
            instance = new DAOPotlasok();
        }
        return instance;
    }

    public ArrayList<BeolvasottKiadottHossz> getPotlasok(String keresendo)
    {
        ArrayList<BeolvasottKiadottHossz> potlasok = new ArrayList<>();
        try
        {
            if (!keresendo.isEmpty())
            {
                querry = "SELECT id, cikkszam, szelesseg, lokacio, regihossz, kiadotthossz, ujhossz, szoveg FROM [dbo].[PotlasKiadas] WHERE id LIKE '%" + keresendo + "%' OR cikkszam LIKE '%" + keresendo + "%' ";
            }
            else
            {
                querry = "SELECT TOP 25 id, cikkszam, szelesseg, lokacio, regihossz, kiadotthossz, ujhossz, szoveg FROM [dbo].[PotlasKiadas]";
            }

            runHUNGARYQuerry(querry);
            if (getResultSet().isBeforeFirst())
            {
                while (getResultSet().next())
                {
                    potlasok.add(new BeolvasottKiadottHossz() {{
                        setID(getResultSet().getString(1));
                        setCikkszam(getResultSet().getString(2));
                        setSzelesseg(getResultSet().getInt(3));
                        setLokacio(getResultSet().getString(4));
                        setBeolvasottHossz(getResultSet().getDouble(5));
                        setKiadottHossz(getResultSet().getDouble(6));
                        setLeiras(getResultSet().getString(8));
                    }});
                }
            }
            return potlasok;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            dispose();
        }
    }
}
