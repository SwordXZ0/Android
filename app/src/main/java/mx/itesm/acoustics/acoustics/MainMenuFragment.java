package mx.itesm.acoustics.acoustics;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainMenuFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button GButton = (Button) getActivity().findViewById(R.id.grabar);
        GButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(getActivity(), ListActivity.class);
                startActivity(it);*/
                //getFragmentManager().beginTransaction().replace(R.id.container2, new recordFragment()).commit();
                Intent i = new Intent(getActivity(),RecordActivity.class);
                startActivity(i);
            }
        });

        Button AButton = (Button) getActivity().findViewById(R.id.analizar);
        AButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.container2, new terapiasFragment()).commit();
                Intent i = new Intent(getActivity(),ListActivity.class);
                startActivity(i);
            }
        });
    }

    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }



}
