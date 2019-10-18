package berwin.StockHandler.DataLayer.Model.PlanSzedes;

import java.util.ArrayList;

import berwin.StockHandler.LogicLayer.Enums.KiszedesStatus;
import berwin.StockHandler.LogicLayer.Enums.PlanStatus;

public class Plan {

    private String planID;
    public String getPlanID() { return planID; }
    public void setPlanID(String plan_ID) { this.planID = plan_ID; }

    private ArrayList<Szovet> szovetek;
    public ArrayList<Szovet> getSzovetek() { return szovetek; }
    public void setSzovetek(ArrayList<Szovet> szovetek) { this.szovetek = szovetek; }

    private PlanStatus planStatus;
    public PlanStatus getPlanStatus() { return planStatus; }
    public void setPlanStatus(PlanStatus planStatus) { this.planStatus = planStatus; }

    public Szovet getSzovetByCikkszam(String cikkszam) {
        return szovetek.stream().filter(x -> x.getCikkszam().equals(cikkszam)).findFirst().orElse(null);
    }

    public Szovet getSzovetByPosition(int position) {
        return szovetek.get(position);
    }

    public ArrayList<Integer> getMentettHosszabbSzovetek(ArrayList<Szovet> szovetek) {
        ArrayList<Integer> mentettSzovetek = new ArrayList<>();
        if (szovetek != null) {
            for (int i = 0; i < szovetek.size(); i++) {
                if (szovetek.get(i).getKiszedesStatus().equals(KiszedesStatus.MentveHosszabb)) {
                    mentettSzovetek.add(i);
                }
            }
        }
        return mentettSzovetek;
    }

    public ArrayList<Integer> getMentettRovidebbSzovetek(ArrayList<Szovet> szovetek) {
        ArrayList<Integer> mentettSzovetek = new ArrayList<>();
        if (szovetek != null) {
            for (int i = 0; i < szovetek.size(); i++) {
                if (szovetek.get(i).getKiszedesStatus().equals(KiszedesStatus.MentveRovidebb)) {
                    mentettSzovetek.add(i);
                }
            }
        }
        return mentettSzovetek;
    }

    public ArrayList<Szovet> getNemBefejezettSzovetek() {
        ArrayList<Szovet> nemBefejezettSzovetek = new ArrayList<>();
        if (szovetek != null && szovetek.size() > 0) {
            for (Szovet i: szovetek) {
                if (!i.getKiszedesStatus().equals(KiszedesStatus.MentveHosszabb)) {
                    nemBefejezettSzovetek.add(i);
                }
            }
        }
        return nemBefejezettSzovetek;
    }

    public Plan() {
        szovetek = new ArrayList<>();
    }

    public Plan(String planID, ArrayList<Szovet> szovetek, PlanStatus planStatus) {
        this.planID = planID;
        this.szovetek = szovetek;
        this.planStatus = planStatus;
    }

    @Override
    public String toString() {
        return planID;
    }
}
