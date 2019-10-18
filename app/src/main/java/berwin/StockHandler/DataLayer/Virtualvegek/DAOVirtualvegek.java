package berwin.StockHandler.DataLayer.Virtualvegek;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottRendelesSzam;

public class DAOVirtualvegek extends DAO {

    private static DAOVirtualvegek instance;
    public static DAOVirtualvegek getInstance() {
        if (instance == null) {
            instance = new DAOVirtualvegek();
        }
        return instance;
    }

    public ArrayList<BeolvasottRendelesSzam> getVirtualVegek(String keresendo, boolean mindenVirtalVegE)
    {
        ArrayList<BeolvasottRendelesSzam> virtualVegek = new ArrayList<>();
        try {
            if (keresendo != null)
            {
                querry = "SELECT DISTINCT id, cikkszam, szelesseg, lokacio, hossz, Rendeles FROM [dbo].[VirtualVegek] WHERE id LIKE '%" + keresendo + "%' OR cikkszam LIKE '%" + keresendo + "%' ";
            }
            else
            {
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

}
