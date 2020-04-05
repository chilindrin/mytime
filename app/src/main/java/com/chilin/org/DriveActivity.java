package com.chilin.org;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chilin.org.drive.BackupCreator;
import com.chilin.org.view.AdviceUser;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Arrays;

public class DriveActivity extends AppCompatActivity {

    private static final String TAG = "drive-activity";
    private static final int SIGNIN_GOOGLEDRIVE_AND_CREATE_DRIVESERVICE = 0;
    private static final int EXPIRED_SESSION = 1;
    private static final int SIGNIN_AGAIN_RETRY_OPERATION = 2;

    private Drive service = null;
    private GoogleAccountCredential credential = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        setUpButtonInToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (service == null) {
            credential = GoogleAccountCredential.usingOAuth2(this, Arrays.asList(DriveScopes.DRIVE));
            startActivityForResult(credential.newChooseAccountIntent(), SIGNIN_GOOGLEDRIVE_AND_CREATE_DRIVESERVICE);
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
            case EXPIRED_SESSION:
                Log.i(TAG, "No more token, a new one has to be requested");
                startActivityForResult(credential.newChooseAccountIntent(), SIGNIN_AGAIN_RETRY_OPERATION);
                break;
            case SIGNIN_AGAIN_RETRY_OPERATION:
                singInGoogleDriveAndCreateService(resultCode, data);
                decideWhichActionToRetry(data.getAction());
                break;
        }
    }

    private void decideWhichActionToRetry(String action){
        switch (action){
            case "createBackup":
                createBackup(this.findViewById(R.id.button3));
        }
    }

    private void singInGoogleDriveAndCreateService(int resultCode, Intent data) {
        Log.i(TAG, "Sign in request code");
        if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Log.i(TAG, "Signed in successfully.");
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);
                service = getDriveService(credential);
            }
        }
    }

    private Drive getDriveService(GoogleAccountCredential credential) {
        return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
                .setApplicationName("My Time")
                .build();
    }

    public void createBackup(View view){
        if (this.service == null){
            new AdviceUser().showSorryBackupNotPossible(view.getContext());
        }
        createBackupInGoogleDrive();
    }

    private void createBackupInGoogleDrive() {
        new BackupCreator(this.service,this).start();
    }

}
