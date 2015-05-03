package mx.itesm.acoustics.acoustics;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sky on 07/03/2015.
 */
public class GraphLineAsycnTask extends AsyncTask<String, Void, ArrayList<String>> {

    private ArrayList<Entry> data;
    private Context context;
    private ArrayList<String> xlabels;

    private ProgressDialog processDialog;

    public GraphLineAsycnTask(Context c){
        context = c;
        data = new ArrayList<Entry>();
        processDialog = new ProgressDialog(context);
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
        data.clear();
        int count=0;
        for(String s: strings){
            Entry entry = new Entry( Float.parseFloat(s), count);
            data.add(entry);
            count++;
        }
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        String[] sNames= context.getResources().getStringArray(R.array.sensoresNames);
        LineDataSet ds1 = new LineDataSet(data, "Sensor "+sNames[Integer.parseInt(GraphFragment.sensor)-1]);
        GraphFragment.colorline=GraphFragment.sharedPref.getString("prefs_color", "");
        ds1.setColor(Color.parseColor(GraphFragment.colorline));
        ds1.setCircleColor(Color.parseColor(GraphFragment.colorline));
        ds1.setLineWidth(2.5f);
        ds1.setCircleSize(3f);
        sets.add(ds1);
        GraphFragment.mChart.setData(new LineData(xlabels, sets));
        GraphFragment.parent.removeView(GraphFragment.mChart);
        GraphFragment.parent.addView(GraphFragment.mChart);
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
        xlabels= new ArrayList<String>();
        try {
            JSONArray arreglo = new JSONArray(datos);
            for(int i = 0; i < arreglo.length(); i++){
                JSONObject s = arreglo.getJSONObject(i);
                xlabels.add(s.getString("time")+" sec");
                objetos.add(s.getString("value"));
            }
        }catch (Exception ex ){
            ex.printStackTrace();
        }
        return objetos;
    }
}