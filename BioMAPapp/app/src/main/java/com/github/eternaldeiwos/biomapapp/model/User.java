package com.github.eternaldeiwos.biomapapp.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.github.eternaldeiwos.biomapapp.Authenticator;
import com.github.eternaldeiwos.biomapapp.AuthenticatorActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by glinklater on 2016/05/28.
 */

public class User {
    public boolean success = false;
    public String token;
    public String name;
    public String surname;
    public String email;
    public String adu_number;

    public User() { /* default */ }

    public User(boolean success, String token, String name, String surname, String email, String adu_number) {
        this.success = success;
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.adu_number = adu_number;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }

    public static List<User> getFromAccountManger(Context context) {
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType(AuthenticatorActivity.ACCOUNT_TYPE);
        LinkedList<User> list = new LinkedList<>();

        for (Account a : accounts) {
            list.add(new User(
                    true,
                    am.getUserData(a, AccountManager.KEY_AUTHTOKEN),
                    am.getUserData(a, Authenticator.KEY_USER_NAME),
                    am.getUserData(a, Authenticator.KEY_USER_SURNAME),
                    am.getUserData(a, Authenticator.KEY_USER_EMAIL),
                    am.getUserData(a, Authenticator.KEY_ADU_NUMBER)
            ));
        }

        return list;
    }
}
