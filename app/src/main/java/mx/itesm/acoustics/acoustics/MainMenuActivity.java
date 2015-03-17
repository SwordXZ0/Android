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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
        if (id == R.id.action_Logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences sharedpreferences = getSharedPreferences
                (MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.clear();
        editor2.commit();*/
        Intent i = new Intent(this,mx.itesm.acoustics.acoustics.MainActivity.class);
        startActivity(i);
        MainMenuActivity.this.finish();
    }
    public void exit(){
        moveTaskToBack(true);
        MainMenuActivity.this.finish();
    }

    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            actualiza(intent);
        }
    };

    public void actualiza(Intent it){
        //TextView tv=(TextView)findViewById(R.id.textView);
        String mensaje = it.getStringExtra("mensaje");
        //tv.setText(mensaje);
        /*if(mensaje.equals("true")){
            lanzaNotificacion();
        }*/
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
        mBuilder.setContentText("Terapias modificadas");
        mBuilder.setTicker("Terapias modificadas");
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setAutoCancel(true);

        Intent intento =new Intent(this, ListActivity.class);
        //intento.putExtra("mensaje", new Date().toString());
        PendingIntent resultPendingIntent= PendingIntent.getActivity(this, 500, intento, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setDefaults(Notification.DEFAULT_ALL);

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5007, mBuilder.build());
    }
}
