package com.parrot.sdksample.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.parrot.sdksample.R;
import com.parrot.sdksample.Rest.Interface.JsonApi;
import com.parrot.sdksample.adaptadores.AdaptadorPersonajes;
import com.parrot.sdksample.entidades.PersonajeVo;
import com.parrot.sdksample.entidades.Transformacion;
import com.parrot.sdksample.interfaces.IComunicaFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaPersonajesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<PersonajeVo> listaPersonaje = new ArrayList<>();
    ;

    RecyclerView recyclerPersonajes;

    Activity actividad;
    IComunicaFragments interfaceComunicaFragments;

    public ListaPersonajesFragment() {
        // Required empty public constructor
    }


    public static ListaPersonajesFragment newInstance(String param1, String param2) {
        ListaPersonajesFragment fragment = new ListaPersonajesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       return show(inflater, container, savedInstanceState);


    }

    private View show(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState){
        View vista=inflater.inflate(R.layout.fragment_lista_personajes, container, false);
        //

        recyclerPersonajes= (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerPersonajes.setLayoutManager(new LinearLayoutManager(getContext()));

       // if(listaPersonaje.isEmpty()) {
            llenarListaPersonajes();
        //}
        try {
            Thread.sleep(10000);

        }
        catch (Exception e){


        }
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AdaptadorPersonajes adapter = new AdaptadorPersonajes(listaPersonaje);
                recyclerPersonajes.setAdapter(adapter);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Seleccion: " +
                                listaPersonaje.get(recyclerPersonajes.
                                        getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();

                        interfaceComunicaFragments.enviarPersonaje(listaPersonaje.get(recyclerPersonajes.getChildAdapterPosition(view)));
                    }
                });
            }
        });

        return vista;

    }

                    //.baseUrl("http://192.168.4.4:4000")
    private void llenarListaPersonajes(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<Transformacion>> call = jsonApi.getPersonajes();
        call.enqueue(new Callback<List<Transformacion>>() {
            @Override
            public void onResponse(Call<List<Transformacion>> call, Response<List<Transformacion>> response) {

                List<Transformacion> postsList = response.body();
                Transformacion personaje = postsList.get(0);
                for( int i=0; i< 6; i++ ) {
                    personaje = postsList.get(i);
                    PersonajeVo person = new PersonajeVo(  personaje.getTitle(), personaje.getTitle(), personaje.getTitle(),R.drawable.paisaje,R.drawable.paisaje);
                    listaPersonaje.add(person);
                    System.out.println(listaPersonaje.get(i).getDescripcion());



                }


            }
            @Override
            public void onFailure(Call<List<Transformacion>> call, Throwable t) {
                Toast.makeText(getActivity(), "On failure",
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            this.actividad= (Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        }

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
