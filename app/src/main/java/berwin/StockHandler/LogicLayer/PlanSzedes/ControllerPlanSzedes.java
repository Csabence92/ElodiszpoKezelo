package berwin.StockHandler.LogicLayer.PlanSzedes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.LogicLayer.Enums.DialogsEnum;
import berwin.StockHandler.LogicLayer.Enums.PlanStatus;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.PresentationLayer.PlanSzedesActivity;
import berwin.StockHandler.PresentationLayer.PlanSzedesKiszedesActivity;
import berwin.StockHandler.DataLayer.Planszedes.DAOPlanSzedes;
import berwin.StockHandler.DataLayer.Interfaces.IQuerryable;
import berwin.StockHandler.LogicLayer.Enums.KiszedesStatus;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.ControllerBase;
import berwin.StockHandler.LogicLayer.Enums.IDCheckResult;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesKiszedesDelete;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesKiszedesReadIn;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesKiszedesUIWrite;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesKiszedesValidate;
import berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers.ControllerPlanSzedesUIWrite;
import berwin.StockHandler.DataLayer.Model.BeolvasottModel.Beolvasott;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.Others.MethodParameters.Parameter;
import berwin.StockHandler.Others.MethodParameters.Parameters;
import berwin.StockHandler.R;

public  class ControllerPlanSzedes extends ControllerBase implements IQuerryable {

    private static ControllerPlanSzedes instanceControllerPlanSzedes;
    public static ControllerPlanSzedes getInstanceControllerPlanSzedes()
    {
        if (instanceControllerPlanSzedes == null) { instanceControllerPlanSzedes = new ControllerPlanSzedes(); }
        return instanceControllerPlanSzedes;
    }

    @Override
    public DAOPlanSzedes getDAO() {
        return DAOPlanSzedes.getInstance();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private PlanSzedesActivity planSzedesActivity;
    public void setPlanSzedesActivity(PlanSzedesActivity planSzedesActivity) { this.planSzedesActivity = planSzedesActivity;}
    protected PlanSzedesActivity getPlanSzedesActivity() { return planSzedesActivity; }

    private PlanSzedesKiszedesActivity planSzedesKiszedesActivity;
    public void setPlanSzedesKiszedesActivity(PlanSzedesKiszedesActivity planSzedesKiszedesActivity) { this.planSzedesKiszedesActivity = planSzedesKiszedesActivity;}
    protected PlanSzedesKiszedesActivity getPlanSzedesKiszedesActivity() { return planSzedesKiszedesActivity; }

    private Plan aktualisPlan;
    public void setAktualisPlan(String planSzam, String inputCikkszam) { this.aktualisPlan = getDAO().buildPlan(planSzam, inputCikkszam); }
    public void setAktualisPlan(Plan plan) { this.aktualisPlan = plan; }
    public Plan getAktualisPlan() { return aktualisPlan; }

    private Szovet aktualisSzovet;
    public void setAktualisSzovet(Szovet aktualisSzovet) { this.aktualisSzovet = aktualisSzovet; }
    public Szovet getAktualisSzovet() { return this.aktualisSzovet; }

    private Beolvasott aktualisBeolvasott;
    public void setAktualisBeolvasott(Beolvasott aktualisBeolvasott) { this.aktualisBeolvasott = aktualisBeolvasott; }
    public Beolvasott getAktualisBeolvasott() { return aktualisBeolvasott; }

    public ControllerPlanSzedesUIWrite getControllerPlanSzedesUIWrite() {
        return ControllerPlanSzedesUIWrite.getInstance(aktualisPlan, planSzedesActivity);
    }

    public ControllerPlanSzedesKiszedesUIWrite getControllerPlanSzedesKiszedesUIWrite() {
       return ControllerPlanSzedesKiszedesUIWrite.getInstance(aktualisSzovet, aktualisPlan, planSzedesKiszedesActivity, getMainActivity());
    }

    public ControllerPlanSzedesKiszedesValidate getControllerPlanSzedesKiszedesValidate() {
        return ControllerPlanSzedesKiszedesValidate.getInstance(aktualisSzovet);
    }

    public ControllerPlanSzedesKiszedesReadIn getControllerPlanSzedesKiszedesReadIn() {
        return ControllerPlanSzedesKiszedesReadIn.getInstance(aktualisSzovet, aktualisPlan);
    }

    public ControllerPlanSzedesKiszedesDelete getControllerPlanSzedesKiszedesDelete() {
        return ControllerPlanSzedesKiszedesDelete.getInstance(aktualisSzovet);
    }

    protected ControllerPlanSzedes() { }

    @Override
    public void serverStatusWriter() {
        if (planSzedesActivity != null) {
            planSzedesActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
            planSzedesActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        }
        if (planSzedesKiszedesActivity != null) {
            planSzedesKiszedesActivity.textViewConnection.setText(SQLConnection.getServerStatus().toString());
            planSzedesKiszedesActivity.textViewConnection.setTextColor(SQLConnection.getServerStatus().toColor());
        }
    }

    public void runPlanSzedes() {
        this.kiszedesAlapAdatokKitoltes();
        this.runSzovetKitoltes();
        this.serverStatusWriter();
    }

    public void runSzovetekListaKitoltes() {
        if (getAktualisPlan() == null) {

            MessageBox("Plan letöltése sikertelen! Próbáld újra..." + getDAO().getMessage());
            planSzedesActivity.finish();
        } else {
            getControllerPlanSzedesUIWrite().run(new Parameters<>(planSzedesActivity, aktualisPlan));
        }
    }

    public void runSzovetKitoltes() {
        if (aktualisSzovet != null) {
            getControllerPlanSzedesKiszedesUIWrite().run(new Parameter<>(getControllerPlanSzedesKiszedesValidate().szovetSzuksegesHosszTullepveE()));
            setBeolvasottTorlesBtnVisibility();
        } else {
            MessageBox("Sikertelen letöltés! Próbáld újra...");
            getPlanSzedesKiszedesActivity().finish();
        }
        this.serverStatusWriter();
    }

    public void runSzovetBeolvasasEllenorzes(String beolvasandoID) {
        IDCheckResult idCheckResult = getControllerPlanSzedesKiszedesValidate().ellenorzesIDSzovet(beolvasandoID);
        if (idCheckResult == IDCheckResult.OK) {
            this.runSzovetBeolvasas(beolvasandoID);
        }
        getControllerPlanSzedesKiszedesUIWrite().ellenorzesEredmenyKiiro(idCheckResult);
    }
    // ITT NEM MEGY BELE A FOR CIKLUSBA
    public void runSzovetBeolvasas(String beolvasandoID) {
        getControllerPlanSzedesKiszedesReadIn().run(new Parameter<>(beolvasandoID));
        getControllerPlanSzedesKiszedesUIWrite().setAktualisBeolvasott(aktualisSzovet.getBeolvasottByID(beolvasandoID));
        getControllerPlanSzedesKiszedesUIWrite().runBeolvasasKitoltes(getControllerPlanSzedesKiszedesValidate().szovetSzuksegesHosszTullepveE(), // Itt nem megy bele a For ciklusba, hanem kikerüli azt
        getControllerPlanSzedesKiszedesValidate().szovetSzuksHosszTullepveErtesithetoE());
        this.hideKeyboard(getPlanSzedesKiszedesActivity());
    }

    public void runSzovetBeolvasottTorles(String torlendoID) {
        getControllerPlanSzedesKiszedesDelete().run(new Parameter<>(torlendoID));
        getControllerPlanSzedesKiszedesUIWrite().runBeolvasasKitoltes(getControllerPlanSzedesKiszedesValidate().szovetSzuksegesHosszTullepveE(),
                getControllerPlanSzedesKiszedesValidate().szovetSzuksHosszTullepveErtesithetoE());
    }

    public void runSzovetBeolvasottKitoltes() {
        getControllerPlanSzedesKiszedesUIWrite().szovetBeolvasottKiiras();
    }

    public void runPlanLezarasEllenorzes() {
        getControllerPlanSzedesUIWrite().setKiirandoSzovetek(aktualisPlan.getNemBefejezettSzovetek());
    }

    public void setControllersAktualisBeolvasott(String ID) {
        setAktualisBeolvasott(aktualisSzovet.getBeolvasottByID(ID));
        getControllerPlanSzedesKiszedesUIWrite().setAktualisBeolvasott(getAktualisBeolvasott());
        getControllerPlanSzedesKiszedesReadIn().setAktualisBeolvasott(getAktualisBeolvasott());
        getControllerPlanSzedesKiszedesDelete().setAktualisBeolvasott(getAktualisBeolvasott());
        getControllerPlanSzedesKiszedesValidate().setAktualisBeolvasott(getAktualisBeolvasott());
    }

    @SuppressLint("SetTextI18n")
    private void kiszedesAlapAdatokKitoltes() {
        if (aktualisSzovet != null) {
            planSzedesKiszedesActivity.textViewPlanSzedesCikkszam.setText("Cikkszám: " + aktualisSzovet.getCikkszam());
            planSzedesKiszedesActivity.textViewPlanSzedesRendelesek.setText(aktualisSzovet.getRendelesSzamok());
            switch (aktualisSzovet.getKiszedesStatus()) {
                case MentveRovidebb:
                    planSzedesKiszedesActivity.textViewPlanSzedesLezarva.setTextColor(aktualisSzovet.getKiszedesStatus().toColor());
                    planSzedesKiszedesActivity.btnPlanSzedesQRScan.setEnabled(true);
                    planSzedesKiszedesActivity.editTextPlanSzedesID.setEnabled(true);
                    planSzedesKiszedesActivity.btnPlanSzedesMentesBefejezes.setEnabled(true);
                    break;
                case MentveHosszabb:
                    planSzedesKiszedesActivity.textViewPlanSzedesLezarva.setTextColor(aktualisSzovet.getKiszedesStatus().toColor());
                    planSzedesKiszedesActivity.btnPlanSzedesQRScan.setEnabled(true);
                    planSzedesKiszedesActivity.editTextPlanSzedesID.setEnabled(true);
                    planSzedesKiszedesActivity.btnPlanSzedesMentesBefejezes.setEnabled(true);
                    break;
                case NincsAdat:
                    planSzedesKiszedesActivity.textViewPlanSzedesLezarva.setTextColor(aktualisSzovet.getKiszedesStatus().toColor());
                    planSzedesKiszedesActivity.btnPlanSzedesQRScan.setEnabled(true);
                    planSzedesKiszedesActivity.editTextPlanSzedesID.setEnabled(true);
                    planSzedesKiszedesActivity.btnPlanSzedesMentesBefejezes.setEnabled(true);
                    break;
            }
            planSzedesKiszedesActivity.textViewPlanSzedesLezarva.setText(aktualisSzovet.getKiszedesStatus().toString());
        }
    }

    protected void setBeolvasottTorlesBtnVisibility() {
        if (planSzedesKiszedesActivity.spinnerPlanSzedesBeolvasottID.getAdapter() == null || getAktualisSzovet().getSzovetBeolvasottak().size() == 0) {
            planSzedesKiszedesActivity.btnPlanSzedesBeolvasottTorles.setVisibility(View.GONE);
            planSzedesKiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.GONE);
        } else {
            planSzedesKiszedesActivity.btnPlanSzedesBeolvasottTorles.setVisibility(View.VISIBLE);
            planSzedesKiszedesActivity.linearLayoutBeolvasottAdatok.setVisibility(View.VISIBLE);
        }
    }

    public void kiszedesMentes() {
        if (getMentesSzovetEngedely()) {
            aktualisCikkszamStatusAtiras();
            if (getControllerPlanSzedesKiszedesValidate().szovetSzuksegesHosszTullepveE()) {
                this.mentesPlanSzedes();
            } else {
                this.mentesPlanSzedes();
                if (cikkszamAllapotMentes()) {
                    EredmenyAlertDialog(getPlanSzedesKiszedesActivity().activity,getAktualisPlan().getPlanID(),getAktualisBeolvasott().getCikkszam());
                } else {
                    MessageBox("Mentés sikertelen!");
                }
            }
        } else {
            MessageBox("Nincs mentendő adat!");
        }

    }
    private void updateSzovet(){
        ProgressDialog dialog = new ProgressDialog(planSzedesKiszedesActivity.context);
        dialog.setMessage("Letöltés folyamatban...");
        dialog.setTitle("Letöltés");
        dialog.show();
        AsyncLoading asyncLoading = new AsyncLoading(aktualisPlan.toString(),"",planSzedesActivity.context,ActivitiesEnum.PlanSzedes,DialogsEnum.Nothing);
        asyncLoading.execute();
        getControllerPlanSzedesUIWrite().setKiirandoSzovetek(aktualisPlan.getSzovetek());
        dialog.dismiss();
    }

    private boolean getMentesSzovetEngedely() {
        return getPlanSzedesKiszedesActivity().spinnerPlanSzedesBeolvasottID.getAdapter() != null;
    }

    public void runPlanLezaras() {
        if (getDAO().planLezarasFeloldasFeltoltes(aktualisPlan.toString(), true)) {
            MessageBox(aktualisPlan.toString() + "-es lezárása sikeres!" + getDAO().getMessage());
            aktualisPlan.setPlanStatus(PlanStatus.Zart);
            getControllerPlanSzedesUIWrite().planStatusWrite();
            planSzedesActivity.finish();
        } else {
            MessageBox("Lezaras sikertelen! Próbáld újra..." + getDAO().getMessage());
        }
    }

    public void runPlanFeloldas() {
        if (getDAO().planLezarasFeloldasFeltoltes(aktualisPlan.toString(), false)) {
            MessageBox(aktualisPlan.toString() + "-es plan felodása sikeres!" + getDAO().getMessage());
            aktualisPlan.setPlanStatus(PlanStatus.Nyitott);
            getControllerPlanSzedesUIWrite().planStatusWrite();
        } else {
            MessageBox("Plan feloldása sikertelen! Próbáld újra..." + getDAO().getMessage());
        }
    }

    public void mentesPlanSzedes() {
        double szuksegesHossz = aktualisSzovet.getSzuksegesHossz();
        for (int i = 0; i < aktualisSzovet.getSzovetBeolvasottak().size(); i++)
         {
             Beolvasott aktualisBeolvasott = aktualisSzovet.getSzovetBeolvasottak().get(i);
            aktualisBeolvasott.setLegnagyobbRendeles(aktualisSzovet.getLegnagyobbRendeles());
            szuksegesHossz -= aktualisBeolvasott.getBeolvasottHossz();  //szuksegesHossz = szuksegesHossz -  aktualisBeolvasott.getBeolvasottHossz()
            if (szuksegesHossz < 0) { //Túllépve = true, MegosztjaE = True, megosztasHossz = beírt méter
                final double osszebeolvasottHelper = szuksegesHossz;
                setVegcedulaNaplo(new VegcedulaNaplo()
                {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-osszebeolvasottHelper);
                    setNev_regi("Szabad");
                    setNev_uj(aktualisPlan.toString());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisPlan.toString());
                    setKiadva("Kiadva");
                }});
                getDAO().mozgasFeltoltes(getVegcedulaNaplo());
                this.alertDialogBuilder(null,null);
                //this.megosztasAlertDialogBuilder();
                szuksegesHossz = 0;
            } else { //Túllépve = false, MegosztjaE = falsa, megosztasHossz = 0;
                setVegcedulaNaplo(new VegcedulaNaplo()
                {{
                    setID(aktualisBeolvasott.getId());
                    setMennyiseg(-aktualisBeolvasott.getBeolvasottHossz());
                    setNev_regi("Szabad");
                    setNev_uj(aktualisPlan.toString());
                    setMozgas_regi("Bevet");
                    setMozgas_uj("Kiszedes");
                    setBin_regi(aktualisBeolvasott.getBin());
                    setBin_uj(aktualisBeolvasott.getBin());
                    //setRendeles_regi(aktualisBeolvasott.getRendelesSzam());
                    setRendeles_uj(aktualisPlan.toString());
                    setKiadva("Kiadva");
                }});
                getDAO().mozgasFeltoltes(getVegcedulaNaplo());
              getDAO().planSzedesKiszedesBefejezesFeltoltes(aktualisBeolvasott, aktualisPlan.toString(), aktualisSzovet.getLegnagyobbRendeles(), false, 0, false, getMessage());
            }
        }
    }

   @Override
    @SuppressLint("SetTextI18n")
    public void alertDialogBuilder(Enum alertDialogActions, IParamable parameter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(planSzedesKiszedesActivity);
        builder.setTitle("Figyelem!");
        builder.setMessage("A szükséges hossz túllépve! \n\nSzükséges hossz: " + getDecimalFormat().format(getAktualisSzovet().getSzuksegesHossz())
                + "m\nBeolvasott hossz: " + getDecimalFormat().format(getAktualisSzovet().getOsszSzovetBeolvasottHossz()) + "m\nBeolvasott darabszám: "
                + getAktualisSzovet().getSzovetBeolvasottVegekSzama() + "db\n\nSzeretnél menteni?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(
                "Igen",
                (dialog, id) -> megosztasAlertDialogBuilder());
        builder.setNegativeButton(
                "Nem",
                (dialog, id) -> dialog.cancel());
        builder.create();
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    private void megosztasAlertDialogBuilder() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(getPlanSzedesKiszedesActivity());
        @SuppressLint("InflateParams") View aView = getPlanSzedesKiszedesActivity().getLayoutInflater().inflate(R.layout.dialog_megosztas, null);

        CheckBox checkBoxTeljesVeg = aView.findViewById(R.id.CheckBoxTeljesVeg);
        EditText editTextMegosztasHossz = aView.findViewById(R.id.EditTextMegosztasHossz);
        Button btnMegosztasMehet = aView.findViewById(R.id.btnMegosztasMehet);
        Button btnMegosztasMegse = aView.findViewById(R.id.btnMegosztasMegse);

        btnMegosztasMegse.setOnClickListener(v -> closeDialog());

        TextView textViewMegosztasAdatok = aView.findViewById(R.id.TextViewMegosztasAdatok);
        textViewMegosztasAdatok.setText("Cikkszám: " + aktualisSzovet.getCikkszam() + "\nSzükséges hossz: " + getDecimalFormat().format(aktualisSzovet.getSzuksegesHossz()) + "m\nBeolvasott hossz: "
                + getDecimalFormat().format(aktualisSzovet.getOsszSzovetBeolvasottHossz()-aktualisBeolvasott.getBeolvasottHossz()) + "m\nMég szükséges hossz: " + getDecimalFormat().format((aktualisBeolvasott.getBeolvasottHossz()+aktualisSzovet.getSzuksegesHossz())-aktualisSzovet.getOsszSzovetBeolvasottHossz()) +
                "m\n\nMegosztandó vég:\n\n" + aktualisBeolvasott.toString());

        btnMegosztasMehet.setOnClickListener(v -> {
           if (checkBoxTeljesVeg.isChecked()) {
                megosztasMehetBtnClicked(0, false);
            } else {
                megosztasMehetBtnClicked(Double.parseDouble(editTextMegosztasHossz.getText().toString()), true);

            }
        });

        checkBoxTeljesVeg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextMegosztasHossz.setEnabled(false);
            } else {
                editTextMegosztasHossz.setEnabled(true);
            }
        });

        aBuilder.setView(aView);
        dialog = aBuilder.create();
        dialog.show();
    }

    private void closeDialog() {
        dialog.dismiss();
        hideKeyboard(planSzedesKiszedesActivity);
    }

    private void megosztasMehetBtnClicked(double megosztasiMeter, boolean megosztjaE) {

        aktualisCikkszamStatusAtiras();
        if (getDAO().planSzedesKiszedesBefejezesFeltoltes(aktualisBeolvasott, aktualisPlan.toString(), aktualisSzovet.getLegnagyobbRendeles(), true, megosztasiMeter, megosztjaE, getMessage())
                && cikkszamAllapotMentes()) {
            mentesErtesites(true);


        } else {
            mentesErtesites(false);
        }
    }
    private void EredmenyAlertDialog(Activity activity,String planID, String cikkszam){
        ArrayList<String> ertesitesArrayList = getDAO().infoQuery(planID,cikkszam);
        if(ertesitesArrayList.size() == 0){
            Toast.makeText(getPlanSzedesActivity(),"Nem kaptuk meg az adatokat" + getDAO().getMessage(), Toast.LENGTH_LONG).show();
        }else {
            String szelesseg = ertesitesArrayList.get(0);
            String keszletenHossz = ertesitesArrayList.get(1);
            String beolvasottHossz = ertesitesArrayList.get(2);
            String szuksegesHossz = ertesitesArrayList.get(3);
            String message = "Cikkszám: " + cikkszam + " Plan: " + planID + "\nSzélesség: " + szelesseg + "\nKészleten hossz: " + keszletenHossz + "\nBeolvasott hossz: " + beolvasottHossz + "\nSzükséges hossz: " + szuksegesHossz;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Sikeres mentés!");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    planSzedesKiszedesActivity.finish();
                }
            });
            builder.create();
            builder.show();
        }

    }
    private boolean cikkszamAllapotMentes() {
        return getDAO().planSzedesKiszedesCikkszamLezarasFeltoltes(aktualisSzovet, aktualisPlan.toString());
    }
    private void mentesErtesites(boolean sikeresE) {
        if (sikeresE) {
          //  MessageBox(aktualisSzovet.getCikkszam() + " sikeresen mentve!");
            EredmenyAlertDialog(getPlanSzedesKiszedesActivity().activity,getAktualisPlan().getPlanID(),getAktualisBeolvasott().getCikkszam());
        } else {
            MessageBox("Mentés sikertelen! Próbáld újra...");
        }
    }

    private void aktualisCikkszamStatusAtiras() {
        if (aktualisSzovet.getSzuksegesHossz() < aktualisSzovet.getOsszSzovetBeolvasottHossz()) {
            aktualisPlan.getSzovetByCikkszam(aktualisSzovet.getCikkszam()).setKiszedesStatus(KiszedesStatus.MentveHosszabb);
        } else if (aktualisSzovet.getSzuksegesHossz() > aktualisSzovet.getOsszSzovetBeolvasottHossz()) {
            aktualisPlan.getSzovetByCikkszam(aktualisSzovet.getCikkszam()).setKiszedesStatus(KiszedesStatus.MentveRovidebb);
        } else if (aktualisSzovet.getOsszSzovetBeolvasottHossz() == 0){
            aktualisPlan.getSzovetByCikkszam(aktualisSzovet.getCikkszam()).setKiszedesStatus(KiszedesStatus.NincsAdat);
        }
    }
}