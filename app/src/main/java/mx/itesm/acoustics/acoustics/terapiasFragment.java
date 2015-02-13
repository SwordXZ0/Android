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


    public terapiasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terapias, container, false);
        ArrayList<String> terapias = new ArrayList<String>();
        terapias.add("Terapia1");
        terapias.add("Terapia2");
        terapias.add("Terapia3");
        terapias.add("Terapia4");
        terapias.add("Terapia5");
        terapias.add("Terapia6");
        terapias.add("Terapia7");
        terapias.add("Terapia8");
        terapias.add("Terapia9");
        terapias.add("Terapia10");
        terapias.add("Terapia11");
        terapias.add("Terapia12");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,terapias);
        ListView list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(getActivity(),"hola", Toast.LENGTH_SHORT).show();
                System.out.println("hola");*/
                //getFragmentManager().beginTransaction().replace(R.id.container2, new GraphFragment()).commit();
                Intent i = new Intent(getActivity(),GraphActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
