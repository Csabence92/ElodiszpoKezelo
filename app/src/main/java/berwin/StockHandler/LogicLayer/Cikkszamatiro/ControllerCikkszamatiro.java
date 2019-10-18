package berwin.StockHandler.LogicLayer.Cikkszamatiro;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Cikkszamatiro.DAOCikkszamatiro;
import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.DAOBase;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers.ControllerCikkszamatiroDelete;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers.ControllerCikkszamatiroReadin;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers.ControllerCikkszamatiroValidate;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers.ControllerCikkszamatiroWriteout;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.CikkszamSzerkesztoActivity;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;

public class ControllerCikkszamatiro extends ControllerBase implements IQuerryable, IRunnable {

    private static ControllerCikkszamatiro instance;
    public static ControllerCikkszamatiro getInstance() {
        if (instance == null) {
            instance = new ControllerCikkszamatiro();
        }
        return instance;
    }

    private CikkszamSzerkesztoActivity cikkszamSzerkesztoActivity;
    protected CikkszamSzerkesztoActivity getCikkszamSzerkesztoActivity() { return this.cikkszamSzerkesztoActivity; }
    public void setCikkszamSzerkesztoActivity(CikkszamSzerkesztoActivity cikkszamSzerkesztoActivity) { this.cikkszamSzerkesztoActivity = cikkszamSzerkesztoActivity; }

    private BeolvasottLeiras aktualisBeolvasott;
    public BeolvasottLeiras getAktualisBeolvasott() { return this.aktualisBeolvasott; }
    public void setAktualisBeolvasott(BeolvasottLeiras aktualisBeolvasott) { this.aktualisBeolvasott = aktualisBeolvasott; }

    private ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak;
    protected ArrayList<BeolvasottLeiras> getCikkszamAtiroBeolvasottak() { return cikkszamAtiroBeolvasottak; }
    protected void setCikkszamAtiroBeolvasottak(ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak) { this.cikkszamAtiroBeolvasottak = cikkszamAtiroBeolvasottak; }

    private ControllerCikkszamatiroValidate getContollerCikkszamAtiroValidate() {
        return ControllerCikkszamatiroValidate.getInstance(cikkszamAtiroBeolvasottak);
    }

    private ControllerCikkszamatiroDelete getControllerCikkszamatiroDelete() {
        return ControllerCikkszamatiroDelete.getInstance(cikkszamAtiroBeolvasottak);
    }

    private ControllerCikkszamatiroWriteout getContollerCikkszamAtiroWriteout() {
        return ControllerCikkszamatiroWriteout.getInstance(cikkszamSzerkesztoActivity, cikkszamAtiroBeolvasottak);
    }

    private ControllerCikkszamatiroReadin getContollerCikkszamAtiroReadIn() {
        return ControllerCikkszamatiroReadin.getInstance(cikkszamAtiroBeolvasottak);
    }

    @Override
    public DAOCikkszamatiro getDAO() { return DAOCikkszamatiro.getInstance(); }

    protected ControllerCikkszamatiro() {
        cikkszamAtiroBeolvasottak = new ArrayList<>();
    }

    @Override
    public void run(IParamable parameters) {
        getContollerCikkszamAtiroWriteout().run(new Parameter<>(aktualisBeolvasott));
        serverStatusWriter();
    }

    public void runBeolvasas(String id) {
        setAktualisBeolvasott((BeolvasottLeiras) getDAO().buildBeolvasott(id, ActivitiesEnum.CikkszamSzerkesztoActivity));
        IDCheckResult checkResult = getContollerCikkszamAtiroValidate().checkBeolvashatoE(aktualisBeolvasott);
        if (checkResult.equals(IDCheckResult.OK)) {
            getContollerCikkszamAtiroReadIn().run(new Parameter<>(aktualisBeolvasott));
        } else {
            getContollerCikkszamAtiroWriteout().beolvasottIdEllenrozesKiiras(checkResult);
        }
    }

    public boolean cikkszamAtiroMentes(boolean lokacioFrissitveE, String ujLokacio) {
        if (lokacioFrissitveE) {
            for (BeolvasottLeiras i: cikkszamAtiroBeolvasottak) {
                if (!getDAO().cikkszamAtiroFeltoltes(i, ujLokacio)) {
                    return false;
                }
            }
        } else {
            for (BeolvasottLeiras i: cikkszamAtiroBeolvasottak) {
                if (!getDAO().cikkszamAtiroFeltoltes(i)) {
                    return false;
                }
            }
        }

        return true;
    }

    public BeolvasottLeiras getBeolvasottLeirasFromList(String id)
    {
        return cikkszamAtiroBeolvasottak.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void serverStatusWriter() {
        cikkszamSzerkesztoActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        cikkszamSzerkesztoActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
    }

    @Override
    protected void alertDialogBuilder(Enum enumerator, IParamable parameter) { }
}
