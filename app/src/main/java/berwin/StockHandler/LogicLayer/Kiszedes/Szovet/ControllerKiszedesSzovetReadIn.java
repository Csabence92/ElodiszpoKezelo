package berwin.StockHandler.LogicLayer.Kiszedes.Szovet;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class ControllerKiszedesSzovetReadIn extends ControllerKiszedes implements IRunnable {

    public ControllerKiszedesSzovetReadIn(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity) {
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameters) {
        szovetBeolvasas(((Parameter<String>) parameters).getElsoParam());
    }

    private void szovetBeolvasas(String id) {
        getAktualisDiszpo().addToCikkszamBeolvasott((Beolvasott) getDAO().buildBeolvasott(id, ActivitiesEnum.KiszedesActivity));
    }
}
