package com.axolotls.prachetadboy.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.activity.LoginActivity;

public class Session {

    public static final String PREFER_NAME = "EKart_dboy";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String fcmId, String id, String name, String mobile, String password, String address, String bonus, String balance, String status, String created_at) {
        editor.putBoolean(Constant.IS_USER_LOGIN, true);
        editor.putString(Constant.FCM_ID, fcmId);
        editor.putString(Constant.ID, id);
        editor.putString(Constant.NAME, name);
        editor.putString(Constant.MOBILE, mobile);
        editor.putString(Constant.PASSWORD, password);
        editor.putString(Constant.ADDRESS, address);
        editor.putString(Constant.BONUS, bonus);
        editor.putString(Constant.BALANCE, balance);
        editor.putString(Constant.STATUS, status);
        editor.putString(Constant.CREATED_AT, created_at);
        editor.commit();
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }

    public void setData(String id, String val) {
        editor.putString(id, val);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(Constant.IS_USER_LOGIN, false);
    }

    public void logoutUser(final Activity activity) {

        editor.clear();
        editor.commit();

        Intent i = new Intent(activity, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.finish();

    }

    public void logoutUserConfirmation(final Activity activity) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);
        // Setting Dialog Message
        alertDialog.setTitle(R.string.logout);
        alertDialog.setMessage(R.string.logout_msg);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.ic_logout_dialog);
        final AlertDialog alertDialog1 = alertDialog.create();

        // Setting OK Button
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                editor.clear();
                editor.commit();

                Intent i = new Intent(activity, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
                activity.finish();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog1.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }
}
