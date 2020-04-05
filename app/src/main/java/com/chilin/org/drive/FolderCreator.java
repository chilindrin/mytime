package com.chilin.org.drive;

import com.chilin.org.DriveActivity;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.List;

public class FolderCreator extends Thread {

    private Drive service = null;
    private DriveActivity driveActivity;
    private static final int EXPIRED_SESSION = 1;
    private static final String APPLICATION_NAME = "mytime";

    public FolderCreator(Drive service, DriveActivity driveActivity){
        this.service = service;
        this.driveActivity = driveActivity;
    }

    public void run(){
        createApplicationFolderIfNecessary();
    }

    private void createApplicationFolderIfNecessary(){
        Drive.Files files = service.files();
        FileList resultQuery = null;
        try {
            resultQuery = files.list().setQ("mimeType='application/vnd.google-apps.folder' and name='"+APPLICATION_NAME+"'").execute();
            List<File> currentFolders = resultQuery.getFiles();
            if (currentFolders.size() == 0){
                createApplicationFolder();
            }
        } catch (UserRecoverableAuthIOException e) {
            this.driveActivity.startActivityForResult(e.getIntent(), EXPIRED_SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createApplicationFolder() {
        File applicationFolder = new File();
        applicationFolder.setName(APPLICATION_NAME);
        applicationFolder.setMimeType("application/vnd.google-apps.folder");
        try {
            service.files().create(applicationFolder).execute();
        } catch (UserRecoverableAuthIOException e) {
            this.driveActivity.startActivityForResult(e.getIntent(), EXPIRED_SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
