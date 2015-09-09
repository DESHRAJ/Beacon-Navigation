package com.deshraj.siftr.navin;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = NotifyDemoActivity.class.getSimpleName();
    private BeaconManager beaconManager;
    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                Log.d(TAG, "Ranged beacons: " + beacons);
            }
        });
//
//        findViewById(R.id.list_all_beacons).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ListBeaconsActivity.class);
//                intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY,"Notify Demo Class Name");
//                startActivity(intent);
//            }
//        });
//        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ListBeaconsActivity.class);
//                intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, NotifyDemoActivity.class.getName());
//                startActivity(intent);
//            }
//        });
    }

    protected void onStart(){
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    public void onStop(){
        super.onStop();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot stop but it does not matter now", e);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        beaconManager.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
