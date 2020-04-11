package com.chilin.org.backup;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chilin.org.db.DBReader;
import com.chilin.org.util.TextProcessor;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class BackupCreatorThread extends Thread {

    private static final String APPLICATION_NAME = "mytime";
    private static final String TAG = "backup-creator";
    private GoogleDriveServiceProvider serviceProvider;

    public BackupCreatorThread(GoogleDriveServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void run(){
        String appFolderId = createApplicationFolderIfNecessary();
        String backupFileId = createFileForBackup(appFolderId);
        writeDBContentInFile(backupFileId);
    }

    private String createApplicationFolderIfNecessary(){
        Drive.Files files = this.serviceProvider.getService().files();
        FileList resultQuery = null;
        String appFolderId = "";
        try {
            resultQuery = files.list().setQ("mimeType='application/vnd.google-apps.folder' and name='"+APPLICATION_NAME+"'").execute();
            List<File> currentFolders = resultQuery.getFiles();
            if (currentFolders.size() == 0){
                appFolderId = createApplicationFolder();
            } else if(currentFolders.size() == 1){
                appFolderId = currentFolders.iterator().next().getId();
            }
            Log.i(TAG,"createApplicationFolderIfNecessary Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            // TODO: What do we do in this situation
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appFolderId;
    }

    private String createApplicationFolder() {
        String createdAppFolderId = "";
        File applicationFolder = new File();
        applicationFolder.setName(APPLICATION_NAME);
        applicationFolder.setMimeType("application/vnd.google-apps.folder");
        try {
            File createdAppFolder = this.serviceProvider.getService().files().create(applicationFolder).execute();
            createdAppFolderId = createdAppFolder.getId();
            Log.i(TAG,"createApplicationFolder Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            // TODO: What do we do in this situation
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createdAppFolderId;
    }

    private String createFileForBackup(String appFolderId) {
        File fileMetadata = new File();
        fileMetadata.setName(TextProcessor.createFileName());
        fileMetadata.setParents(Collections.singletonList(appFolderId));
        String backupFileId = "";
        try {
            backupFileId = this.serviceProvider.getService().files().create(fileMetadata).execute().getId();
            Log.i(TAG,"createFileForBackup Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            // TODO: What do we do in this situation
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backupFileId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void writeDBContentInFile(String backupFileId) {
        DBReader dbReader = new DBReader(this.serviceProvider.getContext());
        String dbContentAsString = TextProcessor.convertDBContentToString(dbReader.getAllDataInDB());
        ByteArrayContent content = new ByteArrayContent(null,dbContentAsString.getBytes(Charset.forName("UTF-8")));
        try {
            this.serviceProvider.getService().files().update(backupFileId,null,content).execute();
            Log.i(TAG,"writeContentInFile Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            // TODO: What do we do in this situation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
