package berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers;

import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class ControllerPlanSzedesKiszedesDelete extends ControllerPlanSzedes implements IRunnable, IQuerryable {

    private static ControllerPlanSzedesKiszedesDelete instance;
    public static ControllerPlanSzedesKiszedesDelete getInstance(Szovet aktualisSzovet) {
        if (instance == null) {
            instance = new ControllerPlanSzedesKiszedesDelete();
        }
        instance.setAktualisSzovet(aktualisSzovet);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        torlesBeolvasottSzovet(((Parameter<String>) parameters).getElsoParam());
    }

    private void torlesBeolvasottSzovet(String torlendoID)
    {
        int torlendoIndex = 0;
        for (int i = 0; i < getAktualisSzovet().getSzovetBeolvasottak().size(); i++) {
            if (getAktualisSzovet().getSzovetBeolvasottak().get(i).getId().equals(torlendoID)) {
                torlendoIndex = i;
            }
        }
        if (deleteFromServer(getAktualisSzovet().getSzovetBeolvasottak().get(torlendoIndex).getId())) {
            getAktualisSzovet().getSzovetBeolvasottak().remove(torlendoIndex);
        }
    }

    private boolean deleteFromServer(String id) {
        return getDAO().deleteBeolvasottVegFromServer(id);
    }
}
