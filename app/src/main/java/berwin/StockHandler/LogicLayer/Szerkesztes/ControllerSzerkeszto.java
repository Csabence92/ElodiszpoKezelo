package berwin.StockHandler.LogicLayer.Szerkesztes;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottQRCode;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottTeljes;
import berwin.StockHandler.DataLayer.Szerkeszto.DAOSzerkeszto;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Szerkesztes.FunctionControllers.ControllerMatricaSzerkesztoUIWrite;
import berwin.StockHandler.LogicLayer.Szerkesztes.FunctionControllers.ControllerVegSzerkesztoUIWrite;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.PresentationLayer.MatricaSzerkesztoActivity;
import berwin.StockHandler.PresentationLayer.VegszerkesztoActivity;

public class ControllerSzerkeszto extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerSzerkeszto instance;
    public static ControllerSzerkeszto getInstance() {
        if (instance == null) {
            instance = new ControllerSzerkeszto();
        }
        return instance;
    }

    private VegszerkesztoActivity vegszerkesztoActivity;
    public void setVegszerkesztoActivity(VegszerkesztoActivity vegszerkesztoActivity) {
        this.vegszerkesztoActivity = vegszerkesztoActivity;
    }
    public VegszerkesztoActivity getVegszerkesztoActivity() {
        return this.vegszerkesztoActivity;
    }

    private MatricaSzerkesztoActivity matricaSzerkesztoActivity;
    public void setMatricaSzerkesztoActivity(MatricaSzerkesztoActivity matricaSzerkesztoActivity) {
        this.matricaSzerkesztoActivity = matricaSzerkesztoActivity;
    }
    public MatricaSzerkesztoActivity getMatricaSzerkesztoActivity() {
        return this.matricaSzerkesztoActivity;
    }

    private BeolvasottTeljes aktualisBeolvasottTeljes;
    public void setAktualisBeolvasottTeljes(String azonosito) { this.aktualisBeolvasottTeljes = (BeolvasottTeljes) getDAO().buildBeolvasott(azonosito, ActivitiesEnum.VegszerkesztoActivity); }
    public void setAktualisBeolvasottTeljes(BeolvasottTeljes beolvasottTeljes) { this.aktualisBeolvasottTeljes = beolvasottTeljes; }
    public BeolvasottTeljes getAktualisBeolvasottTeljes() { return this.aktualisBeolvasottTeljes; }

    private BeolvasottQRCode aktualisBeolvasottQRCode;
    public void setAktualisBeolvasottQRCode(String azonosito) { this.aktualisBeolvasottQRCode = getDAO().buildBeolvasottQRCode(azonosito); }
    public void setAktualisBeolvasottQRCode(BeolvasottQRCode beolvasottQRCode) { this.aktualisBeolvasottQRCode = beolvasottQRCode; }
    public BeolvasottQRCode getAktualisBeolvasottQRCode() {
        return this.aktualisBeolvasottQRCode;
    }

    private ControllerMatricaSzerkesztoUIWrite getControllerMatricaSzerkesztoUIWrite() {
        return ControllerMatricaSzerkesztoUIWrite.getInstance(matricaSzerkesztoActivity, aktualisBeolvasottQRCode);
    }

    private ControllerVegSzerkesztoUIWrite getControllerVegSzerkesztoUIWrite() {
        return ControllerVegSzerkesztoUIWrite.getInstance(vegszerkesztoActivity, aktualisBeolvasottTeljes);
    }

    public boolean vegFeltoltesVegszerkeszto(boolean felszabaditE, boolean lokacioFrissitveE)
    {
        if (getDAO().vegSzerkesztoFeltoltes(aktualisBeolvasottTeljes, felszabaditE, lokacioFrissitveE)) {
            setAktualisBeolvasottTeljes(aktualisBeolvasottTeljes.getId());
            return true;
        } else {
            MessageBox("Hiba történt! Próbáld újra később...");
        }
        return false;
    }

    public boolean matricaSzerkesztoMentes()
    {
        if (getDAO().matricaSzerkesztoFeltoltes(aktualisBeolvasottQRCode)) {
            return true;
        } else {
            MessageBox("Hiba történt! Próbáld újra később...");
        }
        return false;
    }

    @Override
    public DAOSzerkeszto getDAO() {
        return DAOSzerkeszto.getInstance();
    }

    @Override
    public void run(IParamable parameters) {
        if (((Parameter<Boolean>) parameters).getElsoParam()) {
            getControllerVegSzerkesztoUIWrite().fillVegszerkeszto();
        } else {
            getControllerMatricaSzerkesztoUIWrite().fillMatirca();
        }
    }

    @Override
    public void serverStatusWriter() {
        if (vegszerkesztoActivity != null) {
            vegszerkesztoActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            vegszerkesztoActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
        }
        if (matricaSzerkesztoActivity != null) {
            matricaSzerkesztoActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
            matricaSzerkesztoActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
        }
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) {

    }
}
