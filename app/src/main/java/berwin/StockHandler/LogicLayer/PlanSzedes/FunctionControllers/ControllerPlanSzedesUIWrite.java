package berwin.StockHandler.LogicLayer.PlanSzedes.FunctionControllers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import berwin.StockHandler.PresentationLayer.PlanSzedesActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.LogicLayer.PlanSzedes.ControllerPlanSzedes;
import berwin.StockHandler.LogicLayer.Interfaces.IRunnable;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Plan;
import berwin.StockHandler.DataLayer.Model.PlanSzedes.Szovet;
import berwin.StockHandler.Others.MethodParameters.Parameters;
import berwin.StockHandler.R;

public class ControllerPlanSzedesUIWrite extends ControllerPlanSzedes implements IRunnable {

    private static ControllerPlanSzedesUIWrite instance;
    public static ControllerPlanSzedesUIWrite getInstance(Plan aktualisPlan, PlanSzedesActivity planSzedesActivity) {
        if (instance == null) {
            instance = new ControllerPlanSzedesUIWrite() ;
        }
        instance.setAktualisPlan(aktualisPlan);
        instance.setPlanSzedesActivity(planSzedesActivity);
        return instance;
    }

    private ArrayList<Szovet> kiirandoSzovetek;
    public void setKiirandoSzovetek(ArrayList<Szovet> kiirandoSzovetek) {
        this.kiirandoSzovetek = kiirandoSzovetek;
        getPlanSzedesActivity().listViewSzovetek.setAdapter(new ControllerPlanSzedesUIWrite.SzovetAdapter());
    }

    private ControllerPlanSzedesUIWrite() {
        kiirandoSzovetek = new ArrayList<>();
    }

    public void setAdapter() {
        getPlanSzedesActivity().listViewSzovetek.setAdapter(new ControllerPlanSzedesUIWrite.SzovetAdapter());
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void run(IParamable parameters) {
        setPlanSzedesActivity(((Parameters<PlanSzedesActivity, Plan>) parameters).getElsoParam());
        setAktualisPlan(((Parameters<PlanSzedesActivity, Plan>) parameters).getMasodikParam());
        getPlanSzedesActivity().textViewPlanSzedesPlanSzam.setText(getAktualisPlan().toString() + "-es Plan:");
        planStatusWrite();
        setKiirandoSzovetek(getAktualisPlan().getSzovetek());
    }

    public void planStatusWrite() {
        getPlanSzedesActivity().textViewPlanAllapot.setTextColor(getAktualisPlan().getPlanStatus().toColor());
        getPlanSzedesActivity().textViewPlanAllapot.setText(getAktualisPlan().getPlanStatus().toString());
    }

    public class SzovetAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return kiirandoSzovetek.size();
        }

        @Override
        public Object getItem(int position) {
            return kiirandoSzovetek.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        @SuppressLint("ViewHolder")
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getPlanSzedesActivity().getLayoutInflater().inflate(R.layout.item_planszedes, null);
            getPlanSzedesActivity().textViewPlanSzedesSzovet = convertView.findViewById(R.id.TextViewPlanSzedesSzovet);
            if (getAktualisPlan().getMentettHosszabbSzovetek(kiirandoSzovetek).contains(position)) {
                getPlanSzedesActivity().textViewPlanSzedesSzovet.setTextColor(Color.GREEN);
            } else if (getAktualisPlan().getMentettRovidebbSzovetek(kiirandoSzovetek).contains(position)) {
                getPlanSzedesActivity().textViewPlanSzedesSzovet.setTextColor(Color.RED);
            }
            getPlanSzedesActivity().textViewPlanSzedesSzovet.setText(kiirandoSzovetek.get(position).toString());

            return convertView;
        }
    }
}
