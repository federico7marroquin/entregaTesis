package com.parrot.sdksample.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parrot.sdksample.R;
import com.parrot.sdksample.entidades.PersonajeVo;

public class DetallePersonajeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView textDescripcion;
    ImageView imagenDetalle;

    public DetallePersonajeFragment() {
        // Required empty public constructor
    }


    public static DetallePersonajeFragment newInstance(String param1, String param2) {
        DetallePersonajeFragment fragment = new DetallePersonajeFragment();
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
        View vista=inflater.inflate(R.layout.fragment_detalle_personaje, container, false);

        textDescripcion= (TextView) vista.findViewById(R.id.descripcionId);
        imagenDetalle= (ImageView) vista.findViewById(R.id.imagenDetalleId);

        Bundle objetoPersonaje=getArguments();
        PersonajeVo miPersonaje=null;
        if (objetoPersonaje != null) {
            miPersonaje= (PersonajeVo) objetoPersonaje.getSerializable("objeto");

            asignarInformacion(miPersonaje);

        }

        return vista;
    }

    public void asignarInformacion(PersonajeVo miPersonaje) {
        imagenDetalle.setImageResource(miPersonaje.getImagenDetalle());
        textDescripcion.setText(miPersonaje.getDescripcion());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        void onFragmentInteraction(Uri uri);
    }
}
