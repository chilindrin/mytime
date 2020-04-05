package com.chilin.org.drive;

import android.content.Intent;
import android.util.Log;

import com.chilin.org.DriveActivity;
import com.chilin.org.actions.ChilinAction;
import com.chilin.org.db.TimeRegister;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BackupCreator extends Thread {

    private static final int EXPIRED_SESSION = 1;
    private static final String APPLICATION_NAME = "mytime";
    private static final String TAG = "folder-creator";
    private Drive service = null;
    private DriveActivity driveActivity;

    public BackupCreator(Drive service, DriveActivity driveActivity){
        this.service = service;
        this.driveActivity = driveActivity;
    }

    public void run(){
        String appFolderId = createApplicationFolderIfNecessary();
        String backupFileId = createFileForBackup(appFolderId);
        writeContentInFile(backupFileId);
    }

    private void writeContentInFile(String backupFileId) {
        String dbContentAsString = convertDBContentToString();
        ByteArrayContent content = new ByteArrayContent(null,dbContentAsString.getBytes(Charset.forName("UTF-8")));
        try {
            this.service.files().update(backupFileId,null,content).execute();
            Log.i(TAG,"writeContentInFile Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            Intent intent = e.getIntent();
            intent.setAction(ChilinAction.CREATE_BACKUP.getAction());
            this.driveActivity.startActivityForResult(intent, EXPIRED_SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFileForBackup(String appFolderId) {
        File fileMetadata = new File();
        fileMetadata.setName(createFileName());
        fileMetadata.setParents(Collections.singletonList(appFolderId));
        String backupFileId = "";
        try {
            backupFileId = this.service.files().create(fileMetadata).execute().getId();
            Log.i(TAG,"createFileForBackup Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            Intent intent = e.getIntent();
            intent.setAction(ChilinAction.CREATE_BACKUP.getAction());
            this.driveActivity.startActivityForResult(intent, EXPIRED_SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backupFileId;
    }

    private String convertDBContentToString(){
        TimeRegister timeRegister = new TimeRegister();
        List<String[]> allDataInDB = timeRegister.getAllDataInDB(this.driveActivity.getBaseContext());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<allDataInDB.size();i++) {
            String[] content = allDataInDB.get(i);
            stringBuilder.append(content[0]);
            stringBuilder.append(',');
            stringBuilder.append(content[1]);
            stringBuilder.append(',');
            stringBuilder.append(content[2]);
            if (i != allDataInDB.size() - 1){
                stringBuilder.append('|');
            }
        }
        Log.i(TAG,"convertDBContentToString Successfull");
        return stringBuilder.toString();
    }

    private String createFileName(){
        String randomUUID = UUID.randomUUID().toString();
        return "mytime-"+randomUUID+".txt";
    }

    private String createApplicationFolderIfNecessary(){
        Drive.Files files = service.files();
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
            Intent intent = e.getIntent();
            intent.setAction(ChilinAction.CREATE_BACKUP.getAction());
            this.driveActivity.startActivityForResult(intent, EXPIRED_SESSION);
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
            File createdAppFolder = service.files().create(applicationFolder).execute();
            createdAppFolderId = createdAppFolder.getId();
            Log.i(TAG,"createApplicationFolder Successfull");
        } catch (UserRecoverableAuthIOException e) {
            Log.i(TAG,"The session of the user has expired");
            Intent intent = e.getIntent();
            intent.setAction(ChilinAction.CREATE_BACKUP.name());
            this.driveActivity.startActivityForResult(intent, EXPIRED_SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createdAppFolderId;
    }

}
