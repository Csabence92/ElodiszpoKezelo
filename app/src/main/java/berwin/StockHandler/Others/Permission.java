package berwin.StockHandler.Others;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class Permission {
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1001;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1002;
    public static final int INSTALL_PACKAGES_REQUEST_CODE = 1003;
    private static final String LOG = "PERMISSION";
    Activity mContext;

    public Permission(Activity mContext){
        this.mContext = mContext;
    }
    public void RequestPermission(int requestCode){
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE :
                if (checkPermission(requestCode)){
                    Log.i(LOG,"WRITE_EXTERNAL_STORAGE PERMISSION: DENIED");
                    ActivityCompat.requestPermissions(this.mContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    break;
                }else{
                    Log.i(LOG,"WRITE_EXTERNAL_STORAGE PERMISSION: GRANTED");
                    break;
                }
            case READ_EXTERNAL_STORAGE_REQUEST_CODE :
                if (checkPermission(requestCode)){
                    Log.i(LOG,"READ_EXTERNAL_STORAGE PERMISSION: DENIED");
                    ActivityCompat.requestPermissions(this.mContext,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE_REQUEST_CODE);
                    break;
                }else{
                    Log.i(LOG,"READ_EXTERNAL_STORAGE PERMISSION: GRANTED");
                    break;
                }
            case INSTALL_PACKAGES_REQUEST_CODE :
                if (checkPermission(requestCode)){
                    Log.i(LOG,"INSTALL_PACKAGES PERMISSION: DENIED");
                    ActivityCompat.requestPermissions(this.mContext,new String[]{Manifest.permission.INSTALL_PACKAGES},INSTALL_PACKAGES_REQUEST_CODE);
                    break;
                }else{
                    Log.i(LOG,"INSTALL_PACKAGES PERMISSION: GRANTED");
                    break;
                }
        }

    }
    private boolean checkPermission(int requestCode){
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE : return ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED;
            case READ_EXTERNAL_STORAGE_REQUEST_CODE :  return ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED;
            case INSTALL_PACKAGES_REQUEST_CODE : return ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.INSTALL_PACKAGES) == PackageManager.PERMISSION_DENIED;
            default: return false;}
    }
}
