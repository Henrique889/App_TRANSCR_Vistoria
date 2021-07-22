package com.example.transcr.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.transcr.R;

public class TipoDeVistoria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnCaminhao,btnCarroceria,btnSprinter,btnSp,
            btnCamInterclima,btnCavalo;
    TextView txt25;

    private OnFragmentInteractionListener mListener;

    public TipoDeVistoria() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registrarCursoFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static TipoDeVistoria newInstance ( String param1, String param2 ) {
        TipoDeVistoria fragment = new TipoDeVistoria();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString( ARG_PARAM1 );
            String mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View vista = inflater.inflate( R.layout.escolha_veiculo_grandeoupequeno, container, false );

        btnCaminhao = vista.findViewById( R.id.btnCaminhao );
        btnSp = vista.findViewById( R.id.btnSp );
        btnSprinter = vista.findViewById( R.id.btnSprinter );
        btnCarroceria = vista.findViewById( R.id.btnCarroceria );
        txt25 = vista.findViewById( R.id.txtV25 );
        btnCamInterclima = vista.findViewById( R.id.btnCaInter );
        btnCavalo= vista.findViewById( R.id.btnCavalo );

        btnCaminhao.setVisibility(View.VISIBLE);
        btnSp.setVisibility(View.VISIBLE);
        btnSprinter.setVisibility(View.VISIBLE);
        btnCarroceria.setVisibility(View.VISIBLE);
        btnCamInterclima.setVisibility(View.VISIBLE);
        btnCavalo.setVisibility(View.VISIBLE);
        txt25.setVisibility(View.VISIBLE);

        btnCaminhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoria() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );

        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoriaSprinterF() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );

        btnSprinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoria_Sprinter() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );

        btnCarroceria.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoria_Carroceria() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );
        btnCamInterclima.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoria_Caminhao() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );
        btnCavalo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container1,new CriarVistoria_Cavalo() );
                fr.commit();
                btnCaminhao.setVisibility(View.INVISIBLE);
                btnSp.setVisibility(View.INVISIBLE);
                btnSprinter.setVisibility(View.INVISIBLE);
                btnCarroceria.setVisibility(View.INVISIBLE);
                btnCamInterclima.setVisibility(View.INVISIBLE);
                btnCavalo.setVisibility(View.INVISIBLE);
                txt25.setVisibility(View.INVISIBLE);
            }
        } );

        return vista;
    }
    public void onButtonPressed( Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if (context instanceof CriarVistoria.OnFragmentInteractionListener) {
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
        void onFragmentInteraction ( Uri uri );
    }

}
