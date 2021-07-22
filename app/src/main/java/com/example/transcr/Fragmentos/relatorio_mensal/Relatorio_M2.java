package com.example.transcr.Fragmentos.relatorio_mensal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.transcr.R;

public class Relatorio_M2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText txtFiltroCombustivel_Data, txtFiltroCombustivel_Km,txtFiltroCombustivel_Troca, txtFiltroAr_Data, txtFiltroAr_Km, txtFiltroAr_Troca,
            txtFArSistema_Data, txtFArSistema__Km, txtFArSistema_Troca, txtOleoCambioData,txtOleoCambioKm,txtOleoCambioTroca,
            txtO_DiferencialData, txtO_DiferencialKm, txtO_DiferencialTroca,txtPneus_D, txtPneus_T,txtPneus_Estepe;
    Button btnProx;

    private OnFragmentInteractionListener mListener;

    public Relatorio_M2() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static Relatorio_M1 newInstance(String param1, String param2) {
        Relatorio_M1 fragment = new Relatorio_M1();
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
        View vista = inflater.inflate( R.layout.scroll_relatorio_m2, container, false);

        txtFiltroCombustivel_Data = vista.findViewById(R.id.txtDfiltro_C);
        txtFiltroCombustivel_Km = vista.findViewById(R.id.txtFiltroCKm);
        txtFiltroCombustivel_Troca = vista.findViewById(R.id.txtTrocaFiltro_C);
        txtFiltroAr_Data = vista.findViewById(R.id.txtDfiltro_Ar);
        txtFiltroAr_Km = vista.findViewById(R.id.txtFiltro_ArKm);
        txtFiltroAr_Troca = vista.findViewById(R.id.txtFiltroAr_Troca);
        txtFArSistema_Data = vista.findViewById(R.id.txtDfiltro_ArSistema);
        txtFArSistema__Km = vista.findViewById(R.id.txtFiltro_ArSKm);
        txtFArSistema_Troca = vista.findViewById(R.id.txtFiltroArS_Troca);
        txtOleoCambioData = vista.findViewById(R.id.txtDOleo_cambio);
        txtOleoCambioKm = vista.findViewById(R.id.txtOleoCambio_km);
        txtOleoCambioTroca = vista.findViewById(R.id.txtTrocaOleo_Cambio);
        txtO_DiferencialData = vista.findViewById(R.id.txtDOleo_diferencial);
        txtO_DiferencialKm = vista.findViewById(R.id.txtOleoDiferencial_km);
        txtO_DiferencialTroca = vista.findViewById(R.id.txtOleoDTroca_km);
        txtPneus_D = vista.findViewById(R.id.txtDPneus);
        txtPneus_T = vista.findViewById(R.id.txtT_pneus);
        txtPneus_Estepe = vista.findViewById(R.id.txtStep_pneus);
        btnProx = vista.findViewById(R.id.btnP2);

        return vista;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach( Context context) {
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
        void onFragmentInteraction ( Uri uri );
    }
}
