package mx.itesm.acoustics.acoustics;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by sky on 16/03/2015.
 */
public class ServicioCambios extends Service {
    public static final String _ACTION ="mx.itesm.acoustics.acoustics";
    private final Handler handler= new Handler();
    private Intent intent;

    private ServicioCambios s=this;
    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(_ACTION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        handler.removeCallbacks(ejecutaAccion);
        handler.postDelayed(ejecutaAccion, 1000);
        return START_STICKY;
    }

    private Runnable ejecutaAccion= new Runnable() {
        @Override
        public void run() {
            MiThread thread= new MiThread(intent, s);
            thread.start();
            handler.postDelayed(ejecutaAccion, 60000);
        }
    };

    @Override
    public void onDestroy(){
        handler.removeCallbacks(ejecutaAccion);
        super.onDestroy();
    }
}
