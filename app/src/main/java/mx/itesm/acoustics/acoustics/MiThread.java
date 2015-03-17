package mx.itesm.acoustics.acoustics;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Handler;
import android.text.format.Time;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sky on 16/03/2015.
 */
public class MiThread extends Thread{
    private static MiThread t;

    private Intent intent;
    private Integer size;
    private final Handler handler;
    ServicioCambios s;

    private MiThread(Intent intent,Handler handler,ServicioCambios s){
        super();
        this.intent=intent;
        this.handler=handler;
        this.s=s;
    }

    public static MiThread getThread(Intent intent,Handler handler, ServicioCambios s){
        if(t==null){
            t= new MiThread(intent,handler,s);
        }
        return t;
    }

    @Override
    public void run() {
        mensajeActivity();
        handler.postDelayed(this, 60000);
        //leerArchivo("http://ancestralstudios.com/emotiv/terapias.php");
        //handler.postDelayed(this, 60000);
    }

    public void leerArchivo(String url)
    {
        StringBuilder responseString = new StringBuilder();
        HttpURLConnection connection = null;
        URL serviceURL = null;
        try {
            serviceURL = new URL(url);
            connection = (HttpURLConnection) serviceURL.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.connect();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new NetworkErrorException();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                responseString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NetworkErrorException e){
            e.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }
        ArrayList<String> objetos = new ArrayList<String>();
        String datos=responseString.toString();
        try {
            JSONArray arreglo = new JSONArray(datos);
            if(size==null){
                size= new Integer(arreglo.length());
                intent.putExtra("mensaje", "false");
                s.sendBroadcast(intent);
            }else if (size.intValue()==arreglo.length()){
                intent.putExtra("mensaje", "false");
                s.sendBroadcast(intent);
            }else{
                size= new Integer(arreglo.length());
                intent.putExtra("mensaje", "true");
                s.sendBroadcast(intent);
            }
        }catch (Exception ex ){
            ex.printStackTrace();
        }
    }

    private void mensajeActivity(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        Time now = new Time();
        now.setToNow();
        intent.putExtra("mensaje",  now.toString());
        s.sendBroadcast(intent);
    }

}