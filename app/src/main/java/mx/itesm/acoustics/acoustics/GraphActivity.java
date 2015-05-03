package mx.itesm.acoustics.acoustics;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class GraphActivity extends ActionBarActivity {
    private String[] itemsTitulos;
    private DrawerLayout drawerLayout;
    private ListView listViewDrawer;
    private ActionBarDrawerToggle toggle;

    private CharSequence tituloDrawer, titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this,R.xml.settings, false);
        setContentView(R.layout.activity_graph);
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.containerGraph, new GraphFragment())
                    .commit();
        }*/
        titulo=tituloDrawer=getTitle();
        itemsTitulos=getResources().getStringArray(R.array.opciones);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        listViewDrawer=(ListView)findViewById(R.id.left_drawer);

        Objeto[] opciones= new Objeto[3];
        opciones[0]=new Objeto(R.drawable.ic_action_graph, itemsTitulos[0]);
        opciones[1]=new Objeto(R.drawable.ic_action_data, itemsTitulos[1]);
        opciones[2]=new Objeto(R.drawable.ic_action_return, itemsTitulos[2]);

        DrawerAdaptador adaptador= new DrawerAdaptador(this,R.layout.renglon,opciones);
        listViewDrawer.setAdapter(adaptador);

        listViewDrawer.setOnItemClickListener(new DrawerListListener());

        if (savedInstanceState == null) {
            seleccionaAccion(0);
        }

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abrir,R.string.cerrar){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(tituloDrawer);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(titulo);
            }
        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    public class DrawerListListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            seleccionaAccion(position);
        }
    }

    public void seleccionaAccion(int p){
        Fragment fragment=null;
        switch (p){
            case 0:
                fragment=new GraphFragment();
                break;
            case 1:
                fragment=new DetailDataFragment();
                break;
            case 2:
                Intent i = new Intent(this, ListActivity.class);
                startActivity(i);
                finish();
                break;
        }
        if(fragment!=null){
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
            listViewDrawer.setItemChecked(p,true);
            listViewDrawer.setSelection(p);
            setTitle(itemsTitulos[p]);
            drawerLayout.closeDrawer(listViewDrawer);
        }else{
            listViewDrawer.setItemChecked(p,true);
            listViewDrawer.setSelection(p);
            setTitle(itemsTitulos[p]);
            drawerLayout.closeDrawer(listViewDrawer);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,PrefrencesActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        if (id == R.id.action_refresh) {
            SharedPreferences sharedpreferences=getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String url="http://ancestralstudios.com/emotiv/fileTerapias.php?name="+sharedpreferences.getString("fileName","")+"&sensor="+GraphFragment.sensor;
            GraphLineAsycnTask  asycnTask = new GraphLineAsycnTask (this);
            asycnTask.execute(url);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this, ListActivity.class);
        startActivity(i);
        finish();
    }
}
