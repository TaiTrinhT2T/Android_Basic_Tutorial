package example.com.broadcasttest;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
 
public class SmsEvent extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String strAction = intent.getAction();
        if (strAction.equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        MainActivity.setText("number: "+phoneNumber+'\n'+"content: "+message);
                    }
                }
            } catch (Exception e) {
            }
        }
        else if (strAction.equalsIgnoreCase(MainActivity.ACTION)){
            String data = intent.getStringExtra("data");
            MainActivity.setText(data);
        }
    }
}