package com.example.datn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    public SmSBroadcastReciverListener SmSBroadcastReciverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == SmsRetriever.SMS_RETRIEVED_ACTION) {
            Bundle extras = intent.getExtras();
            Status smsRetreiverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (smsRetreiverStatus.getStatusCode()) {
                case CommonStatusCodes
                        .SUCCESS:
                    Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    SmSBroadcastReciverListener.onSuccess(messageIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    SmSBroadcastReciverListener.onFailure();
                    break;
            }
        }
    }

    public interface SmSBroadcastReciverListener {
        void onSuccess(Intent intent);

        void onFailure();
    }
}
