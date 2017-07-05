package com.example.kritikagopalakrishnan.fingerprint_authentication;

/**
 * Created by M G KRISHNAN on 03-Jul-17.
 */

import com.example.kritikagopalakrishnan.fingerprint_authentication.FingerprintAuth;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.hardware.fingerprint.FingerprintManager.FINGERPRINT_ERROR_CANCELED;

/**
 * Created by kritikagopalakrishnan on 6/30/17.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{
    private CancellationSignal cancellationSignal;
    private Context context;

    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//

    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (errMsgId != FINGERPRINT_ERROR_CANCELED) {

            super.onAuthenticationError(errMsgId, errString);
            Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
        }
    }
    @Override

    //onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device//

    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
        ((Activity) context).finish();
    }

    @Override

    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);

        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }@Override

    //onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);

        ((Activity) context).finish();

    }
}

