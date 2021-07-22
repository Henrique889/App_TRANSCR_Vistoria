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

public class TipoDeCarreta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnCarreta,btnCarreta40,btnCarretaBug,btnCarretaSider;
    TextView textView;

    private OnFragmentInteractionListener mListener;

    public TipoDeCarreta () {
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
    public static TipoDeCarreta newInstance ( String param1, String param2 ) {
        TipoDeCarreta fragment = new TipoDeCarreta();
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
        View vista = inflater.inflate( R.layout.escolha_carreta_bug_ousider, container, false );

        btnCarreta = vista.findViewById( R.id.button4 );
        btnCarreta40 = vista.findViewById( R.id.button5 );
        btnCarretaBug = vista.findViewById( R.id.button7 );
        btnCarretaSider = vista.findViewById( R.id.button6 );
        textView = vista.findViewById( R.id.txtV);

        btnCarreta.setVisibility(View.VISIBLE);
        btnCarreta40.setVisibility(View.VISIBLE);
        btnCarretaBug.setVisibility(View.VISIBLE);
        btnCarretaSider.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

        btnCarreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new CriarVistoriaCarreta() );
                fr.commit();
                btnCarreta.setVisibility(View.INVISIBLE);
                btnCarreta40.setVisibility(View.INVISIBLE);
                btnCarretaBug.setVisibility(View.INVISIBLE);
                btnCarretaSider.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        } );
        btnCarretaSider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new CriarVistoriaCarreta_Sider() );
                fr.commit();
                btnCarreta.setVisibility(View.INVISIBLE);
                btnCarreta40.setVisibility(View.INVISIBLE);
                btnCarretaBug.setVisibility(View.INVISIBLE);
                btnCarretaSider.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        } );
        btnCarreta40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new CriarVistoriaCarreta_40() );
                fr.commit();
                btnCarreta.setVisibility(View.INVISIBLE);
                btnCarreta40.setVisibility(View.INVISIBLE);
                btnCarretaBug.setVisibility(View.INVISIBLE);
                btnCarretaSider.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        } );
        btnCarretaBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace( R.id.container2,new CriarVistoriaCarreta_Bug20() );
                fr.commit();
                btnCarreta.setVisibility(View.INVISIBLE);
                btnCarreta40.setVisibility(View.INVISIBLE);
                btnCarretaBug.setVisibility(View.INVISIBLE);
                btnCarretaSider.setVisibility(View.INVISIBLE);
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
