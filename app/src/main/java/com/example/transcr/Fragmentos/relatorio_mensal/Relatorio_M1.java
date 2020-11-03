package com.example.transcr.Fragmentos.relatorio_mensal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;

import java.text.DateFormat;
import java.util.Calendar;

public class Relatorio_M1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText nome, placa, km_principal,
        txtAlinhaData, txtAlinhaKm,txtAlinhaStatus,txtAlinhaRodizio, txtExtintorData, txtExtintorTrocaData,txtPalletera, txtPalleteraDataRev, txtPaletasData,
            txtPaletasKm,txtPaletasTroca, txtOleoMData, txtOleoMKm,txtOleoMTroca,txtFiltroOMData, txtFiltroOMKm,txtFiltroTroca,txtFiltroCombustivel_Data,
            txtFiltroCombustivel_Km,txtFiltroCombustivel_Troca, txtFiltroAr_Data, txtFiltroAr_Km, txtFiltroAr_Troca;

    TextView data;
    Button btnProx;

    ProgressDialog progresso;
    StringRequest stringRequest;
    private OnFragmentInteractionListener mListener;

    public Relatorio_M1() {
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
        View vista = inflater.inflate( R.layout.scroll_relatorio_m1, container, false);

        nome = vista.findViewById(R.id.textNome);
        placa = vista.findViewById(R.id.textPlaca);
        km_principal = vista.findViewById(R.id.textKm);
        txtAlinhaData = vista.findViewById(R.id.txtDAlinhamento);
        txtAlinhaKm = vista.findViewById(R.id.txtAlinhaKm);
        txtAlinhaStatus = vista.findViewById(R.id.txtAlinhaStatus);
        txtAlinhaRodizio = vista.findViewById(R.id.txtAlinha_Rodizio);
        txtExtintorData = vista.findViewById(R.id.txtDExtintor);
        txtExtintorTrocaData = vista.findViewById(R.id.txtE_Troca);
        txtPalletera = vista.findViewById(R.id.txt_Palletera);
        txtPalleteraDataRev = vista.findViewById(R.id.txtDPalletera);
        txtPaletasData = vista.findViewById(R.id.txtDPaleta);
        txtPaletasKm = vista.findViewById(R.id.txtPaleta_km);
        txtPaletasTroca = vista.findViewById(R.id.txtTrocaPaleta);
        txtOleoMData = vista.findViewById(R.id.txtDOleo_motor);
        txtOleoMKm = vista.findViewById(R.id.txtOleo_km);
        txtOleoMTroca = vista.findViewById(R.id.txtOleoTroca_km);
        txtFiltroOMData = vista.findViewById(R.id.txtDFiltro_Oleo);
        txtFiltroOMKm = vista.findViewById(R.id.txtFOleo_km);
        txtFiltroTroca = vista.findViewById(R.id.txtFiltroOleoTroca_km);
        txtFiltroCombustivel_Data = vista.findViewById(R.id.txtDfiltro_C);
        txtFiltroCombustivel_Km = vista.findViewById(R.id.txtFiltroCKm);
        txtFiltroCombustivel_Troca = vista.findViewById(R.id.txtTrocaFiltro_C);
        txtFiltroAr_Data = vista.findViewById(R.id.txtDfiltro_Ar);
        txtFiltroAr_Km = vista.findViewById(R.id.txtFiltro_ArKm);
        txtFiltroAr_Troca = vista.findViewById(R.id.txtFiltroAr_Troca);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        data = vista.findViewById(R.id.txtData0);
        data.setText(currentDate);

        btnProx = vista.findViewById(R.id.btn_P);

        btnProx.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent detailIntent= new Intent(getContext(), Relatorio_M3.class );

                String Nome = nome.getText().toString();
                String Data = data.getText().toString();
                String Placa = placa.getText().toString();
                String KmPrincipal = km_principal.getText().toString();
                String Alinhamento_Data = txtAlinhaData.getText().toString();
                String Alinhamento_Km = txtAlinhaKm.getText().toString();
                String Alinhamento_Status = txtAlinhaStatus.getText().toString();
                String Alinhamento_Rodizio = txtAlinhaRodizio.getText().toString();
                String Extintor_Data = txtExtintorData.getText().toString();
                String ExtintorTroca = txtExtintorTrocaData.getText().toString();
                String Palletera = txtPalletera.getText().toString();
                String PalleteraD = txtPalleteraDataRev.getText().toString();
                String PaletasData = txtPaletasData.getText().toString();
                String Paletas_Km = txtPaletasKm.getText().toString();
                String PaletasTroca = txtPaletasTroca.getText().toString();
                String OleoM_Data = txtOleoMData.getText().toString();
                String OleoM_km = txtOleoMKm.getText().toString();
                String OleoM_Troca = txtOleoMTroca.getText().toString();
                String FiltroOleoM_data = txtFiltroOMData.getText().toString();
                String FiltroOleoM_km = txtFiltroOMKm.getText().toString();
                String FiltroOleoM_troca = txtFiltroTroca.getText().toString();
                String FiltroCombustivel_Data = txtFiltroCombustivel_Data.getText().toString();
                String FiltroCombustivel_km = txtFiltroCombustivel_Km.getText().toString();
                String FiltroCombustivel_troca = txtFiltroCombustivel_Troca.getText().toString();
                String FiltroAr_Data = txtFiltroAr_Data.getText().toString();
                String FiltroAr_km = txtFiltroAr_Km.getText().toString();
                String FiltroAr_troca = txtFiltroAr_Troca.getText().toString();

                detailIntent.putExtra("One",Placa);
                detailIntent.putExtra("Two",Data);
                detailIntent.putExtra("Three",Nome);
                detailIntent.putExtra("Four",KmPrincipal);
                detailIntent.putExtra("Five",Alinhamento_Data);
                detailIntent.putExtra("Six",Alinhamento_Km);
                detailIntent.putExtra("Seven",Alinhamento_Status);
                detailIntent.putExtra("Eight",Alinhamento_Rodizio);
                detailIntent.putExtra("Nine",Extintor_Data);
                detailIntent.putExtra("Ten",ExtintorTroca);
                detailIntent.putExtra("Eleven",Palletera);
                detailIntent.putExtra("Twelve",PalleteraD);
                detailIntent.putExtra("Thirteen",PaletasData);
                detailIntent.putExtra("Fourteen",Paletas_Km);
                detailIntent.putExtra("Fifteen",PaletasTroca);
                detailIntent.putExtra("Sixteen",OleoM_Data);
                detailIntent.putExtra("Seventeen",OleoM_Troca);
                detailIntent.putExtra("Eighteen",OleoM_km);
                detailIntent.putExtra("Nineteen",FiltroOleoM_data);
                detailIntent.putExtra("Twenty",FiltroOleoM_km);
                detailIntent.putExtra("TwentyOne",FiltroOleoM_troca);
                detailIntent.putExtra("TwentyTwo",FiltroCombustivel_Data);
                detailIntent.putExtra("TwentyThree",FiltroCombustivel_km);
                detailIntent.putExtra("TwentyFour",FiltroCombustivel_troca);
                detailIntent.putExtra("TwentyFive",FiltroAr_Data);
                detailIntent.putExtra("TwentySix",FiltroAr_km);
                detailIntent.putExtra("TwentySeven",FiltroAr_troca);

                startActivity( detailIntent );
            }
        } );

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
