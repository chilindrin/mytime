package com.chilin.org.drive;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Arrays;

public class DriveServiceProvider {

    private Drive service = null;
    private GoogleAccountCredential credential = null;
    private Context context;

    public DriveServiceProvider(Context context){
        this.context = context;
    }

    public void createCredential() {
        credential = GoogleAccountCredential.usingOAuth2(this.context, Arrays.asList(DriveScopes.DRIVE));
    }

    public GoogleAccountCredential getCredential(){
        return this.credential;
    }

    public void updateCredentialsAndCreateService(Intent data){
        String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        if (accountName != null && credential != null) {
            credential.setSelectedAccountName(accountName);
            service = new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
                    .setApplicationName("My Time")
                    .build();
        } else {
            throw new IllegalStateException("I can not do too much if the account is null");
        }
    }

    public Drive getService(){
        return this.service;
    }

    public Context getContext(){
        return this.context;
    }
}
