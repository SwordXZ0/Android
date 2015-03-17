package mx.itesm.acoustics.acoustics;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;

import java.util.Calendar;

/**
 * Created by sky on 16/03/2015.
 */
public class ServicioCambios extends Service {

    private static final String TAG= "InvocaServicio";
    public static final String _ACTION ="mx.itesm.acoustics.acoustics";
    private final Handler handler= new Handler();
    private Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(_ACTION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    String n ="";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        n=intent.getStringExtra("nombre");
        handler.removeCallbacks(ejecutaAccion);
        handler.postDelayed(ejecutaAccion, 1000);

        return START_STICKY;
    }

    private Runnable ejecutaAccion= new Runnable() {
        @Override
        public void run() {
            mensajeActivity();
            handler.postDelayed(this, 60000);
        }
    };

    private void mensajeActivity(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        Time now = new Time();
        now.setToNow();
        intent.putExtra("mensaje", n + " : " + now);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy(){
        handler.removeCallbacks(ejecutaAccion);
        super.onDestroy();
    }


}
