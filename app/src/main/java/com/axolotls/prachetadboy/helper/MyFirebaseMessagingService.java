package com.axolotls.prachetadboy.helper;


import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import com.axolotls.prachetadboy.activity.OrderDetailActivity;
import com.axolotls.prachetadboy.model.WalletHistory;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    TextToSpeech textToSpeech;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        try {

            JSONObject data = json.getJSONObject(Constant.DATA);

            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            String type = data.getString("type");
            String id = data.getString("id");

            Intent intent;

            if (type.equals("delivery_boys")) {
                intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra(Constant.ORDER_ITEM_ID, id);
            } else {
                intent = new Intent(getApplicationContext(), WalletHistory.class);
            }

            textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
                if (status != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            if (imageUrl == null || imageUrl.equals("null") || imageUrl.equals("")) {
                mNotificationManager.showSmallNotification(title, message, intent);
            } else {
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        AppController.getInstance().setDeviceToken(s);
        //MainActivity.UpdateToken(s);
    }

}
