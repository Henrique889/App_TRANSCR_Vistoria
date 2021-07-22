package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.FichaDeVistoria;
import com.example.transcr.entidades.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class VistoriaTabSprinter_p1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaVeiculo;
    TextView viewExtintor, viewCapaChuva,viewColete,viewX, viewParLuvas, viewCapacete, viewGchuva,
            viewMarteloMadeira, viewDocumentos, viewAcendedor, viewCaboF, viewMacaco, viewFmacaco, viewTriangulo, viewChaveRodas;
    EditText camponome, campoExtintor, campoCapaChuva, campoColete, campoX,campoParLuvas,
            campoCapacete, campoGchuva, campoMarteloMadeira, campoDocumentos, campoAcendedor, campoCaboF, campoMacaco,
            campoFmacaco, campoTriangulo, campoChaveRodas;
    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;
    TextView textViewDate;

    public VistoriaTabSprinter_p1() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static VistoriaTabSprinter_p1 newInstance(String param1, String param2) {
        VistoriaTabSprinter_p1 fragment = new VistoriaTabSprinter_p1();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate( R.layout.scrollfichasprinter_p1, container, false);
        camponome = vista.findViewById(R.id.camponome);
        placaVeiculo = vista.findViewById(R.id.placa3 );
        btn = vista.findViewById(R.id.button2);
        btnAdicionar = vista.findViewById(R.id.proximoBtn);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = vista.findViewById(R.id.txt_date8);
        textViewDate.setText(currentDate);

        campoExtintor = vista.findViewById( R.id.campoExtintor );
        campoCapaChuva = vista.findViewById( R.id.campoCapa );
        campoCapacete= vista.findViewById( R.id.campoCapacete );
        campoColete= vista.findViewById( R.id.campoColete );
        campoX= vista.findViewById( R.id.campoX );
        campoParLuvas = vista.findViewById( R.id.campoLuvas );
        campoGchuva = vista.findViewById( R.id.campoGuarda );
        campoMarteloMadeira = vista.findViewById( R.id.campoMartelo );
        campoDocumentos = vista.findViewById( R.id.campoDocumentos );
        campoAcendedor = vista.findViewById( R.id.campoAcendedor);
        campoCaboF = vista.findViewById( R.id.campoCaboF );
        campoMacaco = vista.findViewById( R.id.campoMacaco );
        campoFmacaco = vista.findViewById( R.id.campoFerroMacaco);
        campoTriangulo = vista.findViewById( R.id.campoTriangulo);
        campoChaveRodas = vista.findViewById( R.id.campoChaveRodas);

        viewExtintor = vista.findViewById( R.id.viewExtintor);
        viewCapaChuva = vista.findViewById( R.id.viewCapa);
        viewCapacete = vista.findViewById( R.id.viewCapacete );
        viewColete= vista.findViewById( R.id.viewColete );
        viewX= vista.findViewById( R.id.viewX );
        viewParLuvas = vista.findViewById( R.id.viewLuvas );
        viewGchuva = vista.findViewById( R.id.viewGuarda );
        viewMarteloMadeira = vista.findViewById( R.id.viewMartelo );
        viewDocumentos = vista.findViewById( R.id.viewDocumentos );
        viewAcendedor = vista.findViewById( R.id.viewAcendedor );
        viewCaboF = vista.findViewById( R.id.viewCabosF );
        viewMacaco = vista.findViewById( R.id.viewMacaco);
        viewFmacaco = vista.findViewById( R.id.viewFerroMacaco );
        viewTriangulo = vista.findViewById( R.id.viewTriangulo);
        viewChaveRodas = vista.findViewById( R.id.viewChavesRoda);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                carregarWEBService();
            }
        } );

        btnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent detailIntent = new Intent(getContext(), VistoriaTabSprinter_p2.class );
                detailIntent.putExtra( EXTRA_PLACA,placaVeiculo.getText().toString());
                String nome = camponome.getText().toString();
                String extintor = campoExtintor.getText().toString();
                String capaDeChuva = campoCapaChuva.getText().toString();
                String ColeteRefletivo = campoColete.getText().toString();
                String Xrefletivo = campoX.getText().toString();
                String parDeluvas = campoParLuvas.getText().toString();
                String capacete = campoCapacete.getText().toString();
                String GuardaChuva = campoGchuva.getText().toString();
                String MarteloMadeira = campoMarteloMadeira.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String Acendedor = campoAcendedor.getText().toString();
                String CabosForca = campoCaboF.getText().toString();
                String macaco = campoMacaco.getText().toString();
                String ferroMacaco = campoFmacaco.getText().toString();
                String triangulo = campoTriangulo.getText().toString();
                String ChaveRodas = campoChaveRodas.getText().toString();

                detailIntent.putExtra(EXTRA_PLACA,placaVeiculo.getText().toString());
                detailIntent.putExtra("One",nome);
                detailIntent.putExtra("Two",extintor);
                detailIntent.putExtra("Three",capaDeChuva);
                detailIntent.putExtra("Four",ColeteRefletivo);
                detailIntent.putExtra("Five",Xrefletivo);
                detailIntent.putExtra("Six",parDeluvas);
                detailIntent.putExtra("Seven",capacete);
                detailIntent.putExtra("Eight",GuardaChuva);
                detailIntent.putExtra("Nine",MarteloMadeira);
                detailIntent.putExtra("Ten",Documentos);
                detailIntent.putExtra("Eleven",Acendedor);
                detailIntent.putExtra("Twelve",CabosForca);
                detailIntent.putExtra("Thirteen",macaco);
                detailIntent.putExtra("Fourteen",ferroMacaco);
                detailIntent.putExtra("Fifteen",triangulo);
                detailIntent.putExtra("Sixteen",ChaveRodas);

                startActivity( detailIntent );
            }
        } );

        Intent mIntent = getActivity().getIntent();
        //Get intent from EscolhaVeiculo.class
        if (mIntent != null) {
            String placa = mIntent.getStringExtra(EXTRA_PLACA);
            placaVeiculo.setText( placa );
        }
        return vista;
    }
    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Buscando...");
        progresso.show();
        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultaSprinter2.php?PLACA=" + placaVeiculo.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeVistoria tabFicha = new FichaDeVistoria();
                JSONArray jsonArray = response.optJSONArray( "ficha_sprinter" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFicha.setExtintor( jsonObject.optString( "extintor" ) );
                    tabFicha.setCapaDeChuva( jsonObject.optString( "capaDeChuva" ) );
                    tabFicha.setColeteRefletivo( jsonObject.optString("ColeteRefletivo"));
                    tabFicha.setXrefletivo( jsonObject.optString("Xrefletivo"));
                    tabFicha.setparDeluvas( jsonObject.optString("parDeluvas"));
                    tabFicha.setCapacete( jsonObject.optString("capacete"));
                    tabFicha.setGuardaChuva( jsonObject.optString("GuardaChuva"));
                    tabFicha.setMarteloMadeira( jsonObject.optString("MarteloMadeira"));
                    tabFicha.setDocumentos( jsonObject.optString("Documentos"));
                    tabFicha.setAcendedor( jsonObject.optString("Acendedor"));
                    tabFicha.setCabosForca( jsonObject.optString("CabosForca"));
                    tabFicha.setMacaco( jsonObject.optString("macaco"));
                    tabFicha.setferroMacaco( jsonObject.optString("ferroMacaco"));
                    tabFicha.setTriangulo( jsonObject.optString("triangulo"));
                    tabFicha.setChaveRodas( jsonObject.optString("ChaveRodas"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //placaV.setText(tabFichaC.getPlacaV());
                viewExtintor.setText(tabFicha.getExtintor());
                viewCapaChuva.setText(tabFicha.getCapaDeChuva());
                viewCapacete.setText(tabFicha.getCapacete());
                viewColete.setText(tabFicha.getColeteRefletivo());
                viewX.setText(tabFicha.getXrefletivo());
                viewParLuvas.setText(tabFicha.getparDeluvas());
                viewGchuva.setText(tabFicha.getGuardaChuva());
                viewMarteloMadeira.setText(tabFicha.getMarteloMadeira());
                viewDocumentos.setText(tabFicha.getDocumentos());
                viewAcendedor.setText(tabFicha.getAcendedor());
                viewCaboF.setText(tabFicha.getCabosForca());
                viewMacaco.setText(tabFicha.getMacaco());
                viewFmacaco.setText(tabFicha.getferroMacaco());
                viewTriangulo.setText(tabFicha.getTriangulo());
                viewChaveRodas.setText(tabFicha.getChaveRodas());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                progresso.hide();
                Toast.makeText(getContext(), "Não foi possível mostrar os dados do banco de dados " +error.toString() , Toast.LENGTH_SHORT).show();
                Log.i("ERROR", error.toString());
            }
        } );

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectReq);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*@Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
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