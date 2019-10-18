package berwin.StockHandler.Others;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import berwin.StockHandler.LogicLayer.Main.ControllerMain;
import berwin.StockHandler.LogicLayer.KeszletKivet.ControllerKeszletKivet;
import berwin.StockHandler.LogicLayer.Szerkesztes.ControllerSzerkeszto;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.PresentationLayer.KiszedesActivity;
import berwin.StockHandler.PresentationLayer.PlanSzedesActivity;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.LogicLayer.Enums.DialogsEnum;
import berwin.StockHandler.LogicLayer.Enums.KeszletKivetEnum;
import berwin.StockHandler.LogicLayer.Kiszedes.ControllerKiszedes;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;

public class AsyncLoading extends AsyncTask<String, String, String> {



    private Update update;
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    private String filename;
    private ProgressDialog progressDialog;
    public ProgressDialog getProgressDialog() { return progressDialog; }
    public void setProgressDialog(ProgressDialog progressDialog) { this.progressDialog = progressDialog; }

    @SuppressLint("StaticFieldLeak")
    private Context context;
    public Context getContext() { return this.context; }
    public void setContext(Context context) { this.context = context; }

    private ControllerMain controllerMain;
    public ControllerMain getControllerMain() { return this.controllerMain; }
    public void setControllerMain(ControllerMain controllerMain) { this.controllerMain = controllerMain; }

    private ControllerSzerkeszto controllerSzerkeszto;
    public ControllerSzerkeszto getControllerSzerkeszto() { return this.controllerSzerkeszto; }
    public void setControllerSzerkeszto() { this.controllerSzerkeszto = ControllerSzerkeszto.getInstance(); }

    private ControllerPlanSzedes controllerPlanSzedes;
    public ControllerPlanSzedes getControllerPlanSzedes() { return this.controllerPlanSzedes; }
    public void setControllerPlanSzedes(ControllerPlanSzedes controllerPlanSzedes) { this.controllerPlanSzedes = controllerPlanSzedes; }

    private ControllerKiszedes controllerKiszedes;
    public ControllerKiszedes getControllerKiszedes() { return this.controllerKiszedes; }
    public void setControllerKiszedes(ControllerKiszedes controllerKiszedes) { this.controllerKiszedes = controllerKiszedes; }

    private ControllerKeszletKivet controllerKeszletKivet;
    public ControllerKeszletKivet getControllerKeszletKivet() { return this.controllerKeszletKivet; }
    public void setControllerKeszletKivet() { this.controllerKeszletKivet = ControllerKeszletKivet.getInstance(); }

    private Intent intent;
    public Intent getIntent() { return intent; }
    public void setIntent(Intent intent) { this.intent = intent; }

    public String getInputCikkszam() {
        return inputCikkszam;
    }

    public void setInputCikkszam(String inputCikkszam) {
        this.inputCikkszam = inputCikkszam;
    }

    private String inputCikkszam;
    private String azonosito;
    public String getAzonosito() { return azonosito; }
    public void setAzonosito(String azonosito) { this.azonosito = azonosito; }

    private ActivitiesEnum activitiesEnum;
    public ActivitiesEnum getActivitiesEnum() { return activitiesEnum; }
    public void setActivitiesEnum(ActivitiesEnum activitiesEnum) { this.activitiesEnum = activitiesEnum; }

    private KeszletKivetEnum keszletKivetEnum;
    public void setKeszletKivetEnum(KeszletKivetEnum keszletKivetEnum) { this.keszletKivetEnum = keszletKivetEnum; }
    public KeszletKivetEnum getKeszletKivetEnum() { return keszletKivetEnum; }

    private DialogsEnum dialogsEnum;
    public void setDialogsEnum(DialogsEnum dialogsEnum) { this.dialogsEnum = dialogsEnum; }
    public DialogsEnum getDialogsEnum() { return dialogsEnum; }

    private boolean felszabaditE;
    public boolean isFelszabaditE() { return felszabaditE; }
    public void setFelszabaditE(boolean felszabaditE) { this.felszabaditE = felszabaditE; }

    private boolean lokacioFrissitveE;
    public boolean isLokacioFrissitveE() { return lokacioFrissitveE; }
    public void setLokacioFrissitveE(boolean lokacioFrissitveE) { this.lokacioFrissitveE = lokacioFrissitveE; }

    public AsyncLoading(String azonosito,  Context context, ActivitiesEnum activitiesEnum, KeszletKivetEnum keszletKivetEnum)
    {
        setControllerKiszedes(ControllerKiszedes.getInstanceControllerKiszedes());
        setControllerMain(ControllerMain.getInstance());
        setAzonosito(azonosito);
        setContext(context);
        setActivitiesEnum(activitiesEnum);
        setKeszletKivetEnum(keszletKivetEnum);
        setControllerKeszletKivet();
    }

    public AsyncLoading(String azonosito, String cikkszam, Context context, ActivitiesEnum activitiesEnum, DialogsEnum dialogsEnum)
    {
        setControllerKiszedes(ControllerKiszedes.getInstanceControllerKiszedes());
        setControllerMain(ControllerMain.getInstance());
        setControllerPlanSzedes(ControllerPlanSzedes.getInstanceControllerPlanSzedes());
        setAzonosito(azonosito);
        setInputCikkszam(cikkszam);
        setContext(context);
        setActivitiesEnum(activitiesEnum);
        setDialogsEnum(dialogsEnum);
    }

    public AsyncLoading(Context context, ActivitiesEnum activitiesEnum, boolean felszabaditE, boolean lokacioFrissitveE)
    {
        setControllerMain(ControllerMain.getInstance());
        setContext(context);
        setActivitiesEnum(activitiesEnum);
        setFelszabaditE(felszabaditE);
        setLokacioFrissitveE(lokacioFrissitveE);
        setControllerSzerkeszto();
    }

    public AsyncLoading(Context context, ActivitiesEnum activitiesEnum)
    {
        setControllerMain(ControllerMain.getInstance());
        setContext(context);
        setActivitiesEnum(activitiesEnum);
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (getActivitiesEnum())
        {
            case KiszedesActivity: getControllerKiszedes().setAktualisDiszpo(getAzonosito()); break;
            case KeszletKivetActivity: controllerKeszletKivet.setBeolvasottKeszletKivet(getAzonosito(), getKeszletKivetEnum()); break;
            case VegszerkesztoActivity: controllerSzerkeszto.vegFeltoltesVegszerkeszto(isFelszabaditE(), isLokacioFrissitveE()); break;
            case MainActivity: getControllerMain().reconnectSQL(); break;
            case PlanSzedes: getControllerPlanSzedes().setAktualisPlan(getAzonosito(),getInputCikkszam()); break;
        }
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        switch (getActivitiesEnum())
        {
            case KiszedesActivity: setProgressDialog(ProgressDialog.show(context,"Diszpó letöltése...","Kérem várjon!",false,false)); break;
            case KeszletKivetActivity:
                switch (getKeszletKivetEnum())
                {
                    case JavitasKitoltes: setProgressDialog(ProgressDialog.show(getContext(),getAzonosito() + " javításának betöltése...","Kérem várjon!",false,false)); break;
                    case Kereses: setProgressDialog(ProgressDialog.show(getContext(),getAzonosito() + " keresése...","Kérem várjon!",false,false)); break;
                    case MainKitoltes: setProgressDialog(ProgressDialog.show(getContext(),getAzonosito() + " betöltése...","Kérem várjon!",false,false)); break;
                }
                break;
            case VegszerkesztoActivity: setProgressDialog(ProgressDialog.show(getContext(),"Adatok mentése...","Kérem várjon!",false,false)); break;
            case MainActivity: setProgressDialog(ProgressDialog.show(getContext(),"Kapcsolat újraélesztése...","Kérem várjon!",false,false)); break;
            case PlanSzedes: setProgressDialog(ProgressDialog.show(getContext(), "Plan letöltése...", "Kérem várjon!", false, false)); break;
        }
    }

    @Override
    protected void onPostExecute(String r)
    {
        getProgressDialog().dismiss();
        switch (getActivitiesEnum())
        {
            case KiszedesActivity: getControllerKiszedes().activityStarter(getContext(), KiszedesActivity.class); break;
            case KeszletKivetActivity:
                switch (getKeszletKivetEnum())
                {
                    case JavitasKitoltes: controllerKeszletKivet.alertDialogBuilder(null, null); break;
                    case Kereses: controllerKeszletKivet.getControllerKeszletKivetUIWrite().keszletKivetKeresesKitoltes();
                    case MainKitoltes: controllerKeszletKivet.getControllerKeszletKivetUIWrite().adatokKitoltesKeszletKivet(); break;
                }
                break;
            case VegszerkesztoActivity: controllerSzerkeszto.run(new Parameter<>(true)); break;
            //case MainActivity: getControllerMain().KapcsolatAllasKijelzes(getActivitiesEnum());
            case PlanSzedes: getControllerPlanSzedes().activityStarter(getContext(), PlanSzedesActivity.class);break;
        }

    }
}
