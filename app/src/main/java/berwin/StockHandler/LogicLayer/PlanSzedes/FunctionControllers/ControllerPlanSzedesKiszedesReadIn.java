package berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers;

import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.Others.MethodParameters.Parameter;

public class ControllerPlanSzedesKiszedesReadIn extends ControllerPlanSzedes implements IQuerryable, IRunnable {

    private static ControllerPlanSzedesKiszedesReadIn instance;
    public static ControllerPlanSzedesKiszedesReadIn getInstance(Szovet aktualisSzovet, Plan aktualisPlan) {
        if (instance == null) {
            instance = new ControllerPlanSzedesKiszedesReadIn();
        }
        instance.setAktualisSzovet(aktualisSzovet);
        instance.setAktualisPlan(aktualisPlan);
        return instance;
    }

    @Override
    public void run(IParamable parameters) {
        szovetBeolvasas(((Parameter<String>) parameters).getElsoParam());
    }

    private void szovetBeolvasas(String id) {
        Beolvasott akutalisBeolvasott = (Beolvasott) getDAO().buildBeolvasott(id, ActivitiesEnum.KiszedesActivity); //Lekérdezi a végcédzulából az ID szerint az adatokat
       // getDAO().planSzedesKiszedesBefejezesFeltoltes(akutalisBeolvasott, getAktualisPlan().toString(), getAktualisSzovet().getLegnagyobbRendeles(), false, 0, false,"X");
        getAktualisSzovet().addToSzovetBeolvasottak(akutalisBeolvasott); //Hozzáadja a Beolvasott litához
    }
}
