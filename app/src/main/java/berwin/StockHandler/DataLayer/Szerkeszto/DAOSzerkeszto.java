package berwin.StockHandler.DataLayer.Szerkeszto;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottQRCode;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottTeljes;

public class DAOSzerkeszto extends DAO {
    private static DAOSzerkeszto instance;
    public static DAOSzerkeszto getInstance() {
        if (instance == null) {
            instance = new DAOSzerkeszto();
        }
        return instance;
    }

    public boolean vegSzerkesztoFeltoltes(BeolvasottTeljes beolvasottTeljes, boolean felszabaditE, boolean lokacioFrissitveE) {
        try {
            if (felszabaditE) {
                querry = "UPDATE [dbo].[vegcedula] SET Rendeles = '-', nev = 'Szabad', mozgas = 'Bevet' WHERE id = '" + beolvasottTeljes.getId() + "'";
            } else{
                if (beolvasottTeljes.getRendelesSzam() == null || beolvasottTeljes.getRendelesSzam().isEmpty()) {
                    beolvasottTeljes.setRendelesSzam("-");
                }
                if (lokacioFrissitveE) {
                    ujLokacioFeltoltese(beolvasottTeljes.getBin(), beolvasottTeljes.getCikkszam());
                    querry = "UPDATE [dbo].[vegcedula] SET cikkszam = '" + beolvasottTeljes.getCikkszam() + "', lokacio = '" +  beolvasottTeljes.getLokacio() + "', szelesseg = '" + beolvasottTeljes.getSzelesseg()
                            + "', hossz = '" + beolvasottTeljes.getBeolvasottHossz() + "', Rendeles = '" + beolvasottTeljes.getRendelesSzam() + "', nev = '" + beolvasottTeljes.getNev()
                            + "', mozgas = '" + beolvasottTeljes.getMozgas() + "', datumm = '" + beolvasottTeljes.getBevNap() + "', vevo = '" + beolvasottTeljes.getVevo() + "', kiadva = '" + beolvasottTeljes.getAllapot() +
                            "' WHERE id = '" + beolvasottTeljes.getId() + "'";
                } else {
                    querry = "UPDATE [dbo].[vegcedula] SET cikkszam = '" + beolvasottTeljes.getCikkszam() + "', lokacio = '" +  beolvasottTeljes.getLokacio() + "', szelesseg = '" + beolvasottTeljes.getSzelesseg()
                            + "', hossz = '" + beolvasottTeljes.getBeolvasottHossz() + "', Rendeles = '" + beolvasottTeljes.getRendelesSzam() + "', nev = '" + beolvasottTeljes.getNev()
                            + "', mozgas = '" + beolvasottTeljes.getMozgas() + "', datumm = '" + beolvasottTeljes.getBevNap() + "', vevo = '" + beolvasottTeljes.getVevo() + "', kiadva = '" + beolvasottTeljes.getAllapot() +
                            "' WHERE id = '" + beolvasottTeljes.getId() + "'";
                }
            }
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    vegSzerkesztoFeltoltes(beolvasottTeljes, felszabaditE, lokacioFrissitveE);
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } finally {
            dispose();
        }
    }

    public boolean matricaSzerkesztoFeltoltes(BeolvasottQRCode beolvasottQRCode)
    {
        try {
            querry = "UPDATE [dbo].[PotlasKuldes] SET rendeles = '" + beolvasottQRCode.getRendelesSzam()
                    + "', cikk = '" + beolvasottQRCode.getCikkszam() + "', hossz = " + beolvasottQRCode.getBeolvasottHossz()
                    + ", szelesseg = " + beolvasottQRCode.getSzelesseg() + " WHERE qrcode = '" + beolvasottQRCode.getQRCode() + "'";
            runHUNGARYUpload(querry);
            return true;
        }
        catch (Exception ex) {
            if (reconnectBeforeReRun()) {
                matricaSzerkesztoFeltoltes(beolvasottQRCode);
            }
            return false;
        } finally {
            dispose();
        }
    }

    public BeolvasottQRCode buildBeolvasottQRCode (final String qrcode) {
        try {
            querry = "SELECT qrcode, rendeles, cikk, hossz, szelesseg FROM [dbo].[PotlasKuldes] WHERE qrcode = '" + qrcode + "'";
            runHUNGARYQuerry(querry);

            if (getResultSet().isBeforeFirst())
            {
                while (getResultSet().next()) {
                    return new BeolvasottQRCode() {{
                        setQRCode(qrcode);
                        setRendelesSzam(getResultSet().getString("rendeles"));
                        setCikkszam(getResultSet().getString("cikk"));
                        setBeolvasottHossz(getResultSet().getDouble("hossz"));
                        setSzelesseg(getResultSet().getDouble("szelesseg"));
                    }};
                }
            }
            return null;
        }
        catch (Exception ex) {
            try {
                if (reconnectBeforeReRun()) {
                    buildBeolvasottQRCode(qrcode);
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
