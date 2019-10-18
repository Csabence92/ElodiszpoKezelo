package berwin.StockHandler.PresentationLayer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.DataLayer.DAO;
import berwin.StockHandler.Others.AsyncLoading;
import berwin.StockHandler.Others.Permission;
import berwin.StockHandler.Others.Update;
import berwin.StockHandler.Others.VersionContoller;
import berwin.StockHandler.PresentationLayer.Enums.ActivitiesEnum;
import berwin.StockHandler.R;
import pub.devrel.easypermissions.EasyPermissions;

public class UpdateActivity extends AppCompatActivity {
    private TextView unknowSourcesTextView;
    private ListView updateListView;
    public boolean isGranted;
    private static final int WRITE_REQUEST_CODE = 300;
    private Permission permission;
    private ArrayList<VersionContoller> versionContollerArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Init();
        this.unknowSourcesTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivity(intent,null);
        });
        this.updateListView.setOnItemClickListener((parent, view, position, id) -> {
            String file_name = this.versionContollerArrayList.get(position).getFilename();
                AlertDialogBuilder(file_name);
        });
    }
    private void Init(){
        this.permission = new Permission(UpdateActivity.this);
        this.permission.RequestPermission(Permission.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        this.permission.RequestPermission(Permission.READ_EXTERNAL_STORAGE_REQUEST_CODE);
        this.permission.RequestPermission(Permission.INSTALL_PACKAGES_REQUEST_CODE);
        unknowSourcesTextView = (TextView) findViewById(R.id.updateUnknowSourcesTextView);
        updateListView = (ListView) findViewById(R.id.updateListView);
        DAO dao = new DAO();
        try {
            this.versionContollerArrayList = dao.getAllVersion();
        } catch (SQLException e) {
            Log.e("Get version data", e.getMessage());
        }
        this.updateListView.setAdapter(this.setAdapter());



    }
    private ArrayAdapter setAdapter() {
        return new UpdateListAdapter(UpdateActivity.this,R.layout.update_listview,this.versionContollerArrayList);
    }


    private void AlertDialogBuilder(String file_name){
        int fileNameStartPosition = file_name.indexOf("_")+1;
        int fileNameEndPosition = file_name.indexOf(".apk");
        String versionNumber = file_name.substring(fileNameStartPosition,fileNameEndPosition);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateActivity.this);
        alertDialog.setTitle("Figyelmeztetés");
        alertDialog.setMessage("Biztosan frissíted az alkalmazást az " + versionNumber + " verzióra?");
        alertDialog.setPositiveButton("Igen", (dialogInterface, i) -> downloadAPK(file_name));
        alertDialog.setNegativeButton("Nem", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
    }

    private void downloadAPK(String filename) {
       Update update = new Update(UpdateActivity.this,2,filename);
       update.execute();
    }
}

