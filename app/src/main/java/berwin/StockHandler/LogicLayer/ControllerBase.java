package berwin.StockHandler.LogicLayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import berwin.StockHandler.PresentationLayer.MainActivity;
import berwin.StockHandler.LogicLayer.Interfaces.IParamable;
import berwin.StockHandler.DataLayer.Model.VegcedulaNaplo;
import berwin.StockHandler.R;

public abstract class ControllerBase {

    private MainActivity mainActivity;
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public MainActivity getMainActivity() {
        return mainActivity;
    }

    private VegcedulaNaplo vegcedulaNaplo;
    public void setVegcedulaNaplo(VegcedulaNaplo vegcedulaNaplo) {
        this.vegcedulaNaplo = vegcedulaNaplo;
    }
    public VegcedulaNaplo getVegcedulaNaplo() {
        return this.vegcedulaNaplo;
    }

    private DecimalFormat df = new DecimalFormat("#.##");
    public DecimalFormat getDecimalFormat() {
        return df;
    }
    protected AlertDialog dialog;

    public void MessageBox(String uzenetSzoveg) {
        Toast.makeText(getMainActivity().getApplicationContext(), uzenetSzoveg, Toast.LENGTH_LONG).show();
    }

    protected void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void activityStarter(Context activityFrom, Class activityTo) {
        Intent intent = new Intent(activityFrom, activityTo);
        activityFrom.startActivity(intent);
    }

    public abstract void serverStatusWriter();

    protected abstract void alertDialogBuilder(Enum enumerator, IParamable parameter);
}
