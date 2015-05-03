package mx.itesm.acoustics.acoustics;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sky on 14/04/2015.
 */
public class FormulaAsycnTask extends AsyncTask<String, Void, ArrayList<String>> {
    private Context context;
    private TextView text;

    private ProgressDialog processDialog;

    public FormulaAsycnTask(Context c, TextView t){
        context = c;
        processDialog = new ProgressDialog(context);
        text=t;
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
        String res="";
        for(String s: strings){
            res+=s;
        }
        text.setText(res);
        processDialog.dismiss();
    }



    public String leerArchivo(String url)
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
        return responseString.toString();
    }

    public ArrayList<String> procesaJSON(String datos){
        ArrayList<String> objetos = new ArrayList<String>();
        String[] sesnorN= context.getResources().getStringArray(R.array.sensoresNames);
        try {
            JSONArray arreglo = new JSONArray(datos);
            for(int i = 0; i < arreglo.length(); i++){
                JSONObject s = arreglo.getJSONObject(i);
                String[] spl=s.getString("sensors").split(",");
                objetos.add("C["+sesnorN[Integer.parseInt(spl[0])-1]+","+sesnorN[Integer.parseInt(spl[1])-1]+"]: "+s.getString("value")+"\n\n");
            }
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        return objetos;
    }
}
