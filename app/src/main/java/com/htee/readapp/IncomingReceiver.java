package com.htee.readapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class IncomingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Toast.makeText(context,"received",Toast.LENGTH_LONG).show();
		Bundle bundle = intent.getExtras();
		SmsMessage[] messages;
		String str = "";

		if(bundle!=null)
		{
			Object[] pdus = (Object[]) bundle.get("pdus");
			messages = new SmsMessage[pdus!=null ? pdus.length : 0];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += messages[i].getOriginatingAddress();
				str += ": ";
				str += messages[i].getMessageBody();
				str += "\n";
			}

			Toast.makeText(context,str,Toast.LENGTH_LONG).show();

			Intent broadCastIntent = new Intent();
			broadCastIntent.setAction("SMS_RECEIVED_ACTION");
			broadCastIntent.putExtra("message",str);
			context.sendBroadcast(broadCastIntent);

		}

	}
}