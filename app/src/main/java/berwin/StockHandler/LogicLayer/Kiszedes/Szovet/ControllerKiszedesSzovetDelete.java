package berwin.StockHandler.LogicLayer.Kiszedes.Szovet;

import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.DataLayer.Model.Kiszedes.Diszpo;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class ControllerKiszedesSzovetDelete extends ControllerKiszedes implements IRunnable {

    public ControllerKiszedesSzovetDelete(Diszpo aktualisDiszpo, KiszedesActivity kiszedesActivity, MainActivity mainActivity) {
        setAktualisDiszpo(aktualisDiszpo);
        setKiszedesActivity(kiszedesActivity);
        setMainActivity(mainActivity);
    }

    @Override
    public void run(IParamable parameters) {
        torlesBeolvasottSzovet(((Parameter<String>) parameters).getElsoParam());
    }

    private void torlesBeolvasottSzovet(String torlendoID)
    {
        int torlendoIndex = 0;
        for (int i = 0; i < getAktualisDiszpo().getCikkszamBeolvasott().size(); i++) {
            if (getAktualisDiszpo().getCikkszamBeolvasott().get(i).getId().equals(torlendoID)) {
                torlendoIndex = i;
            }
        }
        getAktualisDiszpo().getCikkszamBeolvasott().remove(torlendoIndex);
    }

}



