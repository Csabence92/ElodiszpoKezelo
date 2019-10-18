package berwin.StockHandler.Others;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import berwin.StockHandler.BuildConfig;
import berwin.StockHandler.DataLayer.DAO;

public class Update extends AsyncTask<Void, Void, Boolean> {
    public Update(Activity mContext, int progressCode) {
        this.mContext = mContext;
        this.progressCode = progressCode;
        this.dialog = new ProgressDialog(this.mContext);
        this.dialog.setCancelable(false);
        this.FTP_LOCAL_DOWNLOAD_DIR = mContext.getExternalFilesDir(null).getPath() + "/";
    }
    public Update(Activity mContext, int progressCode, String filename) {
        this.mContext = mContext;
        this.progressCode = progressCode;
        this.filename = filename;
        this.dialog = new ProgressDialog(this.mContext);
        this.dialog.setCancelable(false);
        this.FTP_LOCAL_DOWNLOAD_DIR = mContext.getExternalFilesDir(null).getPath() + "/";

    }
    public Update(){

    }

    public ArrayList<String> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<String> fileList) {
        this.fileList = fileList;
    }

    private ArrayList<String> fileList = new ArrayList<>();

    private Activity mContext;
        private final String FTP_REMOTE_DIRECTORY = "/ApplicationUpdate/StockHandler/";
    private  String FTP_LOCAL_DOWNLOAD_DIR;
    private final static int FTP_LIST = 1;
    private final static int FTP_DOWNLOAD = 2;
    private int progressCode;
    private ProgressDialog dialog;
    private String filename;
    private Permission permission;

    private FTPClient FTPConnection(){
        FTPClient ftp = new FTPSClient();
        String ftpHost = "192.168.3.131";
        String ftpPassword = "Berwin1558";
        String ftpUser = "android";
        int ftpPort = 21;

        try {
            ftp.connect(ftpHost,ftpPort);
            boolean status = ftp.login(ftpUser,ftpPassword);
            if(FTPReply.isPositiveCompletion(ftp.getReplyCode()))
            {
                if (status) {
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                    ftp.enterLocalPassiveMode();
                }
                else{
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }
    private boolean disconnetFTP(FTPClient ftp) {
        try {
            if (ftp.isConnected()) {
                boolean isSuccess = ftp.logout();
                if (isSuccess) {
                    ftp.disconnect();
                    return true;
                } else {
                    Toast.makeText(this.mContext, "Sikertelen kijelentkezés", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else{
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    private void downloadAPK()
    {
        boolean status;
        FTPClient ftp = FTPConnection();
        long startTimeMillis  = System.currentTimeMillis();
        try{
            assert ftp != null;
            ftp.changeWorkingDirectory(FTP_REMOTE_DIRECTORY);
            if(!new File(FTP_LOCAL_DOWNLOAD_DIR).exists()){
                new File(FTP_LOCAL_DOWNLOAD_DIR).mkdirs();
            }
            File target = new File(FTP_LOCAL_DOWNLOAD_DIR +  this.filename);
            FileOutputStream outputStream = new FileOutputStream(target);
            Log.i("FTP Download", target.getAbsolutePath());
            status = ftp.retrieveFile(this.filename,outputStream);
            if (status){
                Log.i("FTP Download","Success");
            }else{Log.i("FTP Download", "Unsuccess");}
            this.disconnetFTP(ftp);
            outputStream.close();
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("Cannot change to FTP directory: " + FTP_REMOTE_DIRECTORY);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            status = false;

        }
        finally {
            // Calculate time taken
            long endTimeMillis = System.currentTimeMillis();
            long totalTimemillis = endTimeMillis - startTimeMillis;
            System.out.println(MessageFormat.format("Upload process took {0} ms", totalTimemillis));

        }
    }
    public ArrayList<String> listFiles(){
        FTPClient ftp = FTPConnection();
        FTPFile[] arrayList;
        ArrayList<String> resultList = new ArrayList<>();
        try{
            if (ftp != null) {
                if (!ftp.changeWorkingDirectory(FTP_REMOTE_DIRECTORY)){
                    Log.i("Update", "Nem sikeült átváltani a könyvtárba");
                }
            }
            else{
                Log.i("Update","FTP is disconnected");
            }
            int size = ftp.listFiles().length;
            Log.i("Update","FTP Working Directory:  " + ftp.printWorkingDirectory());
            arrayList = ftp.listFiles();
            for(int i = 0; i < size;i++){
                resultList.add(i,arrayList[i].getName());
                boolean isFile = arrayList[i].isFile();
                if(isFile){
                    Log.i("Update: ","File: " + arrayList[i].getName());
                }
                else{
                    Log.i("Update: ","Directory: " + arrayList[i].getName());
                }
            }
            this.disconnetFTP(ftp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    private void InstallAPK()
    {
        try {
                Log.e("Local Path",this.FTP_LOCAL_DOWNLOAD_DIR);
                File file = new File(FTP_LOCAL_DOWNLOAD_DIR + this.filename);
                GenerciFileProvider generciFileProvider = new GenerciFileProvider();
                generciFileProvider.InstallAPK(this.mContext,file);

        }
        catch (Exception e)
        {
            Log.e("INSTALL APK Exception",e.getMessage());
            Toast.makeText(mContext, "Install APK: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        switch (this.progressCode){
            case FTP_DOWNLOAD : downloadAPK();break;
            case FTP_LIST : listFiles();break;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        switch (this.progressCode){
            case FTP_DOWNLOAD : this.dialog.setMessage("Frissítés megkezdése...");this.dialog.show();break;
            case  FTP_LIST : this.dialog.setMessage("Frissítések keresése...");this.dialog.show();break;
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        this.dialog.dismiss();
        if (progressCode == FTP_DOWNLOAD){
            InstallAPK();
        }
    }
    public boolean updateAvailable(double currentVersion){
        DAO dao = new DAO();
        double newVersion = dao.getNewVersion();
        if (newVersion > currentVersion){
            return true;
        }else {
            return false;
        }

    }
}
