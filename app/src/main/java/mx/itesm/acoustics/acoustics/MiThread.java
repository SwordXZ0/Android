package mx.itesm.acoustics.acoustics;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sky on 16/03/2015.
 */
public class MiThread extends Thread{

    private Intent intent;
    private String size;
    ServicioCambios s;

    public MiThread(Intent intent,ServicioCambios s){
        super();
        this.intent=intent;
        this.s=s;
    }

    @Override
    public void run() {
        leerArchivo("http://ancestralstudios.com/emotiv/terapias.php");
    }

    public void leerArchivo(String url)
    {
        SharedPreferences sharedpreferences=s.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        size=sharedpreferences.getString("sizeT", null);
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

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
        String datos=responseString.toString();
        try {
            JSONArray arreglo = new JSONArray(datos);
            if(size==null){
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putString("sizeT",String.valueOf(arreglo.length()));
                editor.commit();
            }else if (size.equals(String.valueOf(arreglo.length()))){
            }else{
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putString("sizeT",String.valueOf(arreglo.length()));
                editor.commit();
                intent.putExtra("mensaje", "true");
                s.sendBroadcast(intent);
            }
        }catch (Exception ex ){
            ex.printStackTrace();
        }
    }
}