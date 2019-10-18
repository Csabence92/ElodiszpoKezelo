package berwin.StockHandler.Others;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.sql.SQLException;

import berwin.StockHandler.DataLayer.DAO;

public class VersionContoller {
    private double currentVersion;
    private DAO dao = new DAO();
    private double newVersion;
    private Activity activity;
    private String versionName;
    private String filename;

    public String getVersionName() { return versionName; }
    public void setVersionName(String versionName) { this.versionName = versionName; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }


    public VersionContoller(Activity activity){
        this.activity = activity;
        this.newVersion = this.getNewVersionName();
        Log.i("New Version",String.valueOf(this.newVersion));
        this.currentVersion = this.getCurrentVersion();
        Log.i("Current version", String.valueOf(this.currentVersion));

    }
    public VersionContoller(String versionName, String filename){
        this.versionName = versionName;
        this.filename = filename;
    }

    private void UpdateNewVersion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("Frissítés elérhető");
        builder.setMessage("Szeretnéd frissíteni az alkalmazést\n" + this.currentVersion + " verzióról " + this.newVersion + " verzóra?");
        builder.setPositiveButton("Igen", (dialog, which) -> {
            dialog.dismiss();
            updateApplication();
        });
        builder.setNegativeButton("Nem", (dialog, which) -> dialog.dismiss());
        builder.create();
        builder.show();

    }
    private String getNewVersionFileName(){
        try {
            return this.dao.newVersionFileName();
        } catch (SQLException e) {
            Log.e("getNewVersionFileName",e.getMessage());
            return null;
        }
    }
    public void NewVersionIsAvalible(){
        if (this.newVersion > this.currentVersion){
            UpdateNewVersion();
        }
    }
    private double getNewVersionName(){
        try {
            return dao.newVersionCode();
        } catch (SQLException e) {
            Log.e("GetNewVersionName",e.getMessage());
            return 0;
        }
    }
    private double getCurrentVersion(){
        try {
            return Double.parseDouble(this.activity.getPackageManager().getPackageInfo(this.activity.getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
           Log.e("GetCurrentVersion",e.getMessage());
           return 0;
        }
    }
    private void updateApplication(){
        String filename = this.getNewVersionFileName();
        Update update = new Update(this.activity,2,filename);
        update.execute();
    }
}
