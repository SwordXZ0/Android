package mx.itesm.acoustics.acoustics;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDataFragment extends Fragment {
    public static String sensor;
    public static SharedPreferences sharedPref;

    public DetailDataFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences sharedpreferences=getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String fil=sharedpreferences.getString("fileName","");
        String url="http://ancestralstudios.com/emotiv/coeficiente.php?name="+fil+"&sensor="+sharedPref.getString("prefs_sensores", "");
        TextView t= (TextView)getActivity().findViewById(R.id.textView2);
        FormulaAsycnTask  asycnTask = new FormulaAsycnTask (getActivity(), t);
        asycnTask.execute(url);
        super.onViewCreated(view, savedInstanceState);
    }
}
