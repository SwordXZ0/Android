package mx.itesm.acoustics.acoustics;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {
    public LoginFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText e1=(EditText)getActivity().findViewById(R.id.editText);
        EditText e2=(EditText)getActivity().findViewById(R.id.editText2);
        e1.setText("");
        e2.setText("");
        Button mButton = (Button) getActivity().findViewById(R.id.angry_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1=(EditText)getActivity().findViewById(R.id.editText);
                EditText e2=(EditText)getActivity().findViewById(R.id.editText2);
                String u = e1.getText().toString();
                String p = e2.getText().toString();
                LogInAsyncTask asycnTask = new LogInAsyncTask(getActivity(), u, p);
                asycnTask.execute("http://ancestralstudios.com/emotiv/log.php");
            }
        });
    }
}