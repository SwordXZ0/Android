package mx.itesm.acoustics.acoustics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {
    public LoginFragment() {
    }

    ScrollView screen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            /*ScrollView frm = (ScrollView)getActivity().findViewById(R.id.intro);
            TransitionDrawable trans = new TransitionDrawable(MainActivity.colorToRed);
            frm.setBackgroundDrawable(trans);
            trans.startTransition(5000);*/
    }
}