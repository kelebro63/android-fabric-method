package com.livetyping.fabricmethod.notification;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;
import com.livetyping.core.notification.CoreNotification;
import com.livetyping.fabricmethod.MainActivity;
import com.livetyping.fabricmethod.R;

import java.util.Map;

class BirthdayNotification extends CoreNotification {

    static final String TYPE = "birthday";

    BirthdayNotification(RemoteMessage remoteMessage) {
        super(remoteMessage);
    }

    @Override
    protected PendingIntent configurePendingIntent(Context context) {
        String birthdayInfo = getBirthdayInfo();
        Intent intent = new Intent(birthdayInfo, null, context, MainActivity.class)
                .setPackage(context.getApplicationContext().getPackageName())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(CoreNotification.KEY_FROM_PUSH, birthdayInfo);
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static final String KEY_CHILD_COUNT = "children_count";
    private static final String BIRTHDAY_STRING = "We will celebrate birthday today.";

    private String getBirthdayInfo() {
        Map<String, String> data = remoteMessage.getData();
        if (data.containsKey(KEY_CHILD_COUNT)) {
            return BIRTHDAY_STRING + " Count of children : " + data.get(KEY_CHILD_COUNT);
        }
        return BIRTHDAY_STRING;
    }

    @Override
    protected int largeIcon() {
        return R.drawable.ic_birthday;
    }

    @Override
    protected String getNotificationTag() {
        return getClass().getName();
    }
}
