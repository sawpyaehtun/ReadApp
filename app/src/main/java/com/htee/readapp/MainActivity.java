package com.htee.readapp;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView message;
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            message.setText(intent.getExtras().getString("message"));
        }
    };

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (TextView) findViewById(R.id.message);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        if(Build.VERSION.SDK_INT>=23) {
            if (!checkPermissionRead())
                ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_SMS}, 0);
            if (!checkPermissionSMS())
                ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECEIVE_MMS}, 0);
        }

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String address = extras.getString("address");
//            String message = extras.getString("message");
//            TextView addressField = (TextView) findViewById(R.id.address);
//            TextView messageField = (TextView) findViewById(R.id.message);
//            addressField.setText(address);
//            messageField.setText(message);
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermissionRead()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissionSMS()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(intentReceiver);
        super.onStop();
    }
}
