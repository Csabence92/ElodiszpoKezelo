package berwin.StockHandler.LogicLayer.Kiszedes.Beles;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class ControllerKiszedesBelesReadIn extends ControllerKiszedes implements IRunnable {

    public ControllerKiszedesBelesReadIn(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity, Beles aktualisBeles) {
        setAktualisBeles(aktualisBeles);
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameters) {
        belesBeolvasas(((Parameter<String>) parameters).getElsoParam());
    }

    private void belesBeolvasas(String id) {
        if (getAktualisBeles() != null) {
            getAktualisBeles().addToAnyagBeolvasott((Beolvasott) getDAO().buildBeolvasott(id, ActivitiesEnum.KiszedesActivity));
            getAktualisBeles().setMentve(false);
        }
    }
}
