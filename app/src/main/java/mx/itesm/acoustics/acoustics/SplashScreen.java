package mx.itesm.acoustics.acoustics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends ActionBarActivity {
    private final int SPLASH= 2000;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey";
    public static final String pass = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedpreferences;
                sharedpreferences=getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
                if (sharedpreferences.contains(name))
                {
                    if(sharedpreferences.contains(pass)){
                        Intent i = new Intent(SplashScreen.this,MainMenuActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Intent it= new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                }else{
                    Intent it= new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        }, SPLASH);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
