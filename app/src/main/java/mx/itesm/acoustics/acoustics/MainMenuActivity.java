package mx.itesm.acoustics.acoustics;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainMenuActivity extends ActionBarActivity{
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container2, new MainMenuFragment())
                    .commit();
        }
        intent=new Intent(this,ServicioCambios.class);
        intent.putExtra("nombre", "prueba");
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(ServicioCambios._ACTION));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_Logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(this,mx.itesm.acoustics.acoustics.MainActivity.class);
        startActivity(i);
        MainMenuActivity.this.finish();
    }

    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            actualiza(intent);
        }
    };

    public void actualiza(Intent it){
        lanzaNotificacion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    public void lanzaNotificacion(){
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(getString(R.string.NotifyTerapies));
        mBuilder.setTicker(getString(R.string.NotifyTerapies));
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setAutoCancel(true);
        Intent intento =new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent= PendingIntent.getActivity(this, 500, intento, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5007, mBuilder.build());
    }
}
