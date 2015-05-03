package mx.itesm.acoustics.acoustics;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {


    public GraphFragment() {
    }

    public  static LineChart mChart;
    public static FrameLayout parent;
    public static String sensor, colorline;

    public static SharedPreferences sharedPref;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChart =new LineChart(getActivity());

        mChart.setDescription("");
        mChart.setDrawYValues(false);

        mChart.setHighlightIndicatorEnabled(false);
        mChart.setDrawBorder(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawVerticalGrid(true);
        mChart.setDrawYValues(false);
        mChart.setStartAtZero(false);
        mChart.setGridColor(Color.BLACK);
        mChart.animateX(3000);
        mChart.setUnit(" V");
        parent = (FrameLayout) getActivity().findViewById(R.id.graphsen);

        String fil;
        SharedPreferences sharedpreferences=getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        if(getActivity().getIntent().getStringExtra("name")==null){
                fil=sharedpreferences.getString("fileName","");
        }else{
            fil=getActivity().getIntent().getStringExtra("name");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("fileName", fil);
            editor.commit();
        }

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sensor=sharedPref.getString("prefs_sensores", "");
        colorline=sharedPref.getString("prefs_color", "");
        String url="http://ancestralstudios.com/emotiv/fileTerapias.php?name="+fil+"&sensor="+sharedPref.getString("prefs_sensores", "");
        GraphLineAsycnTask  asycnTask = new GraphLineAsycnTask (getActivity());
        asycnTask.execute(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        return v;

    }
}
