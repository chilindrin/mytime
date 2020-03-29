package com.chilin.org;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DriveActivity extends AppCompatActivity {

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int EXPIRED_SESSION = 1;

    private Drive service = null;
    private GoogleAccountCredential credential = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    Log.i(TAG, "Signed in successfully.");
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        service = getDriveService(credential);
                        callApi();
                    }
                }
                break;
            case EXPIRED_SESSION:
                Log.i(TAG, "No more token, a new one has to be requested");
                startActivityForResult(credential.newChooseAccountIntent(), REQUEST_CODE_SIGN_IN);
                break;
        }
    }

    private void callApi() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Drive.Files files = service.files();
                try {
                    FileList execute = files.list().execute();
                    List<File> files1 = execute.getFiles();
                } catch (UserRecoverableAuthIOException e) {
                    startActivityForResult(e.getIntent(), EXPIRED_SESSION);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (service == null) {
            credential = GoogleAccountCredential.usingOAuth2(this, Arrays.asList(DriveScopes.DRIVE));
            startActivityForResult(credential.newChooseAccountIntent(), REQUEST_CODE_SIGN_IN);
        }
    }

    private Drive getDriveService(GoogleAccountCredential credential) {
        return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
                .setApplicationName("My Time")
                .build();
    }

}
