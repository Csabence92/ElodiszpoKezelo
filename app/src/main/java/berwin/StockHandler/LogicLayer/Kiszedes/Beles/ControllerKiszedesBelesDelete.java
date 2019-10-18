package berwin.StockHandler.LogicLayer.Kiszedes.Beles;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Beles;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;


public class ControllerKiszedesBelesDelete extends ControllerKiszedes implements IRunnable {

    public ControllerKiszedesBelesDelete(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity, Beles aktualisBeles) {
        setAktualisBeles(aktualisBeles);
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameters) {
        torlesBeolvasottBeles(((Parameter<String>) parameters).getElsoParam());
    }

    private void torlesBeolvasottBeles(String torlendoID)
    {
        int torlendoIndex = 0;
        for (int i = 0; i < getAktualisBeles().getBelesBeolvasottak().size(); i++) {
            if (getAktualisBeles().getBelesBeolvasottak().get(i).getId().equals(torlendoID)) {
                torlendoIndex = i;
            }
        }
        getAktualisBeles().getBelesBeolvasottak().remove(torlendoIndex);
    }
}
