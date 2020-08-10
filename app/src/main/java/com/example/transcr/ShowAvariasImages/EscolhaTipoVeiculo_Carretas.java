package com.example.transcr.ShowAvariasImages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.transcr.Fragmentos.CriarVistoria;
import com.example.transcr.R;

public class EscolhaTipoVeiculo_Carretas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnCaminhoes,btnCarretas;
    TextView textView;

    private OnFragmentInteractionListener mListener;

    public EscolhaTipoVeiculo_Carretas () {
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
    public static EscolhaTipoVeiculo_Carretas newInstance ( String param1, String param2 ) {
        EscolhaTipoVeiculo_Carretas fragment = new EscolhaTipoVeiculo_Carretas();
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
        View vista = inflater.inflate( R.layout.escolha_carreta_caminhao, container, false );

        btnCaminhoes = vista.findViewById( R.id.button4 );
        btnCarretas = vista.findViewById( R.id.button6 );

        textView = vista.findViewById( R.id.txtV);

        btnCaminhoes.setVisibility(View.VISIBLE);
        btnCarretas.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

        btnCaminhoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new EscolherCaminhao_Avarias2() );
                fr.commit();
                btnCaminhoes.setVisibility(View.INVISIBLE);
                btnCarretas.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        } );
        btnCarretas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new EscolherCarretas_Avarias2() );
                fr.commit();
                btnCaminhoes.setVisibility(View.INVISIBLE);
                btnCarretas.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
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
