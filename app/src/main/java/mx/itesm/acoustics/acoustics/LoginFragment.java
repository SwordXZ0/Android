package mx.itesm.acoustics.acoustics;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
            /*ScrollView frm = (ScrollView)getActivity().findViewById(R.id.intro);
            TransitionDrawable trans = new TransitionDrawable(MainActivity.colorToRed);
            frm.setBackgroundDrawable(trans);
            trans.startTransition(5000);*/
        Button mButton = (Button) getActivity().findViewById(R.id.angry_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
                EditText e1=(EditText)getActivity().findViewById(R.id.editText);
                EditText e2=(EditText)getActivity().findViewById(R.id.editText2);
                String u = e1.getText().toString();
                String p = e2.getText().toString();
                if(u.equals("Android")&& p.equals("Android")){
                    editor.putString(MainActivity.name, u);
                    editor.putString(MainActivity.pass, p);
                    editor.commit();
                    Intent i = new Intent(getActivity(),MainMenuActivity.class);
                    startActivity(i);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("usuario o contraseña incorrecto")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });
                    AlertDialog d=builder.create();
                    d.show();
                    /*LoginDialogFragment d = new LoginDialogFragment();
                    d.show(getActivity().getFragmentManager(),"mi_boton");*/
                }
            }
        });
    }
}