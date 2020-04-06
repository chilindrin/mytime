package com.chilin.org;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.drive.BackupCreator;
import com.chilin.org.drive.DriveServiceProvider;
import com.chilin.org.view.AdviceUser;

public class DriveActivity extends AppCompatActivity {

    private BackupCreator backupCreatorThread;
    private DriveServiceProvider serviceProvider;
    private static final String TAG = "drive-activity";
    private static final int SIGNIN_GOOGLEDRIVE_AND_CREATE_DRIVESERVICE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        setUpButtonInToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( this.serviceProvider == null){
            this.serviceProvider = new DriveServiceProvider(this);
        }
        if (this.serviceProvider.getService() == null) {
            this.serviceProvider.createCredential();
            startActivityForResult(this.serviceProvider.getCredential().newChooseAccountIntent(), SIGNIN_GOOGLEDRIVE_AND_CREATE_DRIVESERVICE);
        }
    }

    private void setUpButtonInToolbar() {
        Toolbar myChildToolbar = findViewById(R.id.toolbarDrive);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SIGNIN_GOOGLEDRIVE_AND_CREATE_DRIVESERVICE:
                singInGoogleDriveAndCreateService(resultCode, data);
                break;
        }
    }

    private void singInGoogleDriveAndCreateService(int resultCode, Intent data) {
        Log.i(TAG, "Sign in request code");
        if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Log.i(TAG, "Signed in successfully.");
            this.serviceProvider.updateCredentialsAndCreateService(data);
        }
    }

    public void createBackup(View view){
        if (this.serviceProvider.getService() == null){
            new AdviceUser().showSorryBackupNotPossible(view.getContext());
        }
        backupCreatorThread = new BackupCreator(this.serviceProvider);
        backupCreatorThread.start();
    }

}
