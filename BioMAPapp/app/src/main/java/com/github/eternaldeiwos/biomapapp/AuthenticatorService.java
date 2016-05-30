package com.github.eternaldeiwos.biomapapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by glinklater on 2016/05/29.
 */

public class AuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }

}
