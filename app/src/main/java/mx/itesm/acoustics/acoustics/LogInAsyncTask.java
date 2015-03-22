package mx.itesm.acoustics.acoustics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genome on 2/6/15.
 */
public class LogInAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

    private Context context;
    private String u;
    private String p;

    private ProgressDialog processDialog;

    public LogInAsyncTask(Context c, String user, String pass){
        context = c;
        processDialog = new ProgressDialog(context);
        u=user;
        p=pass;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        processDialog.setTitle(context.getResources().getString(R.string.procesing));
        processDialog.setCancelable(false);
        processDialog.show();
    }


    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> arreglo = new  ArrayList<String>();
        arreglo= procesaJSON(leerArchivo(params[0]));
        return arreglo;
    }



    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        processDialog.dismiss();
        SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
        if(strings.get(0).equals("true")){
            editor.putString(MainActivity.name, u);
            editor.putString(MainActivity.pass, p);
            editor.commit();
            Intent i = new Intent(context,MainMenuActivity.class);
            context.startActivity(i);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.loginMessage)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog d=builder.create();
            d.show();
        }
    }



    public String leerArchivo(String url)
    {
        StringBuilder buf = new StringBuilder();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List nameValuePairs = new ArrayList();
            nameValuePairs.add(new BasicNameValuePair("user", u));
            nameValuePairs.add(new BasicNameValuePair("password", p));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();


            BufferedReader in =
                    new BufferedReader(

                            new InputStreamReader(is, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        return buf.toString();
    }

    public ArrayList<String> procesaJSON(String datos){
        ArrayList<String> objetos = new ArrayList<String>();
        try {
            JSONObject object = new JSONArray(datos).getJSONObject(0);
            objetos.add(object.getString("exist"));
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        return objetos;
    }
}