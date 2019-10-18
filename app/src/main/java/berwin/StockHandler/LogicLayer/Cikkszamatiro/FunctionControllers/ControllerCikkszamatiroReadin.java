package berwin.StockHandler.LogicLayer.Cikkszamatiro.FunctionControllers;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Model.BeolvasottModel.BeolvasottLeiras;
import berwin.StockHandler.LogicLayer.Cikkszamatiro.ControllerCikkszamatiro;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.Reversed;

public class ControllerCikkszamatiroReadin extends ControllerCikkszamatiro implements IRunnable {

    private static ControllerCikkszamatiroReadin instance;
    public static ControllerCikkszamatiroReadin getInstance(ArrayList<BeolvasottLeiras> cikkszamAtiroBeolvasottak) {
        if (instance == null) {
            instance = new ControllerCikkszamatiroReadin();
        }
        instance.setCikkszamAtiroBeolvasottak(cikkszamAtiroBeolvasottak);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        this.beolvasas(((Parameter<BeolvasottLeiras>) parameters).getElsoParam());
    }

    private void beolvasas(BeolvasottLeiras beolvasottUj) {
        getCikkszamAtiroBeolvasottak().add(beolvasottUj);
    }
}
