package mx.itesm.acoustics.acoustics;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class terapiasFragment extends Fragment {
    private ListView lista;
    private ArrayAdapter<String> adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lista = (ListView) getView().findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.terap,R.id.textView4, new ArrayList<String>());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tmp= adapter.getItem(position).trim();
                String[] parts=tmp.split(" ");
                tmp= parts[1].replace("\n", "").replace("\r", "");
                Intent i = new Intent(getActivity(),GraphActivity.class).putExtra("name", tmp);
                startActivity(i);
                getActivity().finish();
            }
        });

        TerapiasAsyncTask asycnTask = new TerapiasAsyncTask (getActivity(),adapter);
        asycnTask.execute("http://ancestralstudios.com/emotiv/terapias.php");
    }

    public terapiasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terapias_list, container, false);
        return view;
    }
}
