package berwin.StockHandler.Others;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import berwin.StockHandler.BuildConfig;

@SuppressLint("Registered")
public class GenerciFileProvider extends FileProvider {
    public void InstallAPK(Activity activity,File file){
        Intent intent;
        Log.i("SDK Version:", String.valueOf(Build.VERSION.SDK_INT));
        Log.i("VERSION_CODE 'N': ", String.valueOf(Build.VERSION_CODES.N));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".files", file);
            intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(intent,"Choose an application"));
        }else{
            Uri apkUri = Uri.fromFile(file);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(Intent.createChooser(intent,"Choose an application"));
        }

    }

}
