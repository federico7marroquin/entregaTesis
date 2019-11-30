package com.parrot.sdksample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.parrot.sdksample.activity.DeviceListActivity;
import com.parrot.sdksample.entidades.PersonajeVo;
import com.parrot.sdksample.fragments.DetallePersonajeFragment;
import com.parrot.sdksample.fragments.ListaPersonajesFragment;
import com.parrot.sdksample.interfaces.IComunicaFragments;
import com.parrot.sdksample.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity
        implements ListaPersonajesFragment.OnFragmentInteractionListener,
            DetallePersonajeFragment.OnFragmentInteractionListener, IComunicaFragments{

    ListaPersonajesFragment listaFragment;
    DetallePersonajeFragment detalleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        //Validamos que se trabaja en modo portrait desde un smarthPhone
        if(findViewById(R.id.contenedorFragment)!=null){
            Utilidades.PORTRAIT=true;
            if (savedInstanceState!=null){
                return;
            }
            listaFragment=new ListaPersonajesFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.contenedorFragment,listaFragment).commit();
        }else{
            Utilidades.PORTRAIT=false;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_dron:
                Intent i = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(i);
                System.out.println("dronn");

                return true;
            case R.id.action_settings:
                System.out.println("About");
                Toast.makeText(this, "ESTAMOS EN DESARROLLO :P", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void enviarPersonaje(PersonajeVo personaje) {

        detalleFragment= (DetallePersonajeFragment) this.getSupportFragmentManager().
                                                        findFragmentById(R.id.fragDetalle);

        if(detalleFragment!=null && findViewById(R.id.contenedorFragment)==null){
            detalleFragment.asignarInformacion(personaje);
        }else{
            detalleFragment=new DetallePersonajeFragment();
            Bundle bundleEnvio=new Bundle();
            bundleEnvio.putSerializable("objeto",personaje);
            detalleFragment.setArguments(bundleEnvio);

            //cargamos el fragment en el Activity
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.contenedorFragment,detalleFragment).
                    addToBackStack(null).commit();
        }

    }
}

























