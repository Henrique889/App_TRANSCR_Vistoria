package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class VistoriaTab_Caminhao_P1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaV;
    TextView viewExtintor, viewCapaChuva, viewRadio, viewAntena,viewLona,viewColete,viewX, viewParLuvas, viewCapacete, viewGchuva,
             viewMarteloMadeira, viewDocumentos, viewAcendedor, viewCaboF, viewMacaco, viewFmacaco, viewTriangulo;
    EditText camponome, campoExtintor, campoCapaChuva, campoRadio, campoAntena, campoLona, campoColete, campoX,campoParLuvas,
             campoCapacete, campoGchuva, campoMarteloMadeira, campoDocumentos, campoAcendedor, campoCaboF, campoMacaco,
             campoFmacaco, campoTriangulo;
    TextView textViewDate;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public VistoriaTab_Caminhao_P1() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static VistoriaTab_Caminhao_P1 newInstance(String param1, String param2) {
        VistoriaTab_Caminhao_P1 fragment = new VistoriaTab_Caminhao_P1();
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
        View vista = inflater.inflate( R.layout.scrollficha0_p1, container, false);
        camponome = vista.findViewById(R.id.camponome);
        placaV = vista.findViewById(R.id.placa1 );
        btn = vista.findViewById(R.id.button2);
        btnAdicionar = vista.findViewById(R.id.btn_Prox);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = vista.findViewById(R.id.txt_date2);
        textViewDate.setText(currentDate);

        campoExtintor = vista.findViewById( R.id.campoExtintor );
        campoCapaChuva = vista.findViewById( R.id.campoCapa );
        campoRadio = vista.findViewById( R.id.campoRadio );
        campoAntena = vista.findViewById( R.id.campoAntena );
        campoLona = vista.findViewById( R.id.campoLona );
        campoCapacete= vista.findViewById( R.id.campoCapacete );
        campoColete= vista.findViewById( R.id.campoColete );
        campoX= vista.findViewById( R.id.campoX );
        campoParLuvas = vista.findViewById( R.id.campoLuvas );
        campoGchuva = vista.findViewById( R.id.campoGuarda );
        campoMarteloMadeira = vista.findViewById( R.id.campoMartelo );
        campoDocumentos = vista.findViewById( R.id.campoDocumentos );
        campoAcendedor = vista.findViewById( R.id.campoAcendedor );
        campoCaboF = vista.findViewById( R.id.campoCaboF );
        campoMacaco = vista.findViewById( R.id.campoMacaco );
        campoFmacaco = vista.findViewById( R.id.campoFerroMacaco);
        campoTriangulo = vista.findViewById( R.id.campoTriangulo);

        viewExtintor = vista.findViewById( R.id.viewExtintor);
        viewCapaChuva = vista.findViewById( R.id.viewCapa);
        viewAntena = vista.findViewById( R.id.viewAntena );
        viewRadio = vista.findViewById( R.id.viewRadio );
        viewLona = vista.findViewById( R.id.viewLona );
        viewCapacete = vista.findViewById( R.id.viewCapacete );
        viewColete= vista.findViewById( R.id.viewColete );
        viewX= vista.findViewById( R.id.viewX);
        viewParLuvas = vista.findViewById( R.id.viewLuvas );
        viewGchuva = vista.findViewById( R.id.viewGChuva );
        viewMarteloMadeira = vista.findViewById( R.id.viewMartelo);
        viewDocumentos = vista.findViewById( R.id.viewDocumentos );
        viewAcendedor = vista.findViewById( R.id.viewAcendedor );
        viewCaboF = vista.findViewById( R.id.viewCabosF );
        viewMacaco = vista.findViewById( R.id.viewMacaco);
        viewFmacaco = vista.findViewById( R.id.viewFerroMacaco );
        viewTriangulo = vista.findViewById( R.id.viewTriangulo);


        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                carregarWEBService();
            }
        } );

        btnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent detailIntent= new Intent(getContext(), VistoriaTab_Caminhao_P2.class );
                detailIntent.putExtra( EXTRA_PLACA,placaV.getText().toString());
                String nome = camponome.getText().toString();
                String extintor = campoExtintor.getText().toString();
                String capaDeChuva = campoCapaChuva.getText().toString();
                String antena = campoAntena.getText().toString();
                String radio = campoRadio.getText().toString();
                String lona = campoLona.getText().toString();
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


                detailIntent.putExtra(EXTRA_PLACA,placaV.getText().toString());
                detailIntent.putExtra("One",nome);
                detailIntent.putExtra("Two",extintor);
                detailIntent.putExtra("Three",capaDeChuva);
                detailIntent.putExtra("Four",antena);
                detailIntent.putExtra("Five",radio);
                detailIntent.putExtra("Six",lona);
                detailIntent.putExtra("Seven",ColeteRefletivo);
                detailIntent.putExtra("Eight",Xrefletivo);
                detailIntent.putExtra("Nine",parDeluvas);
                detailIntent.putExtra("Ten",capacete);
                detailIntent.putExtra("Eleven",GuardaChuva);
                detailIntent.putExtra("Twelve",MarteloMadeira);
                detailIntent.putExtra("Thirteen",Documentos);
                detailIntent.putExtra("Fourteen",Acendedor);
                detailIntent.putExtra("Fifteen",CabosForca);
                detailIntent.putExtra("Sixteen",macaco);
                detailIntent.putExtra("Seventeen",ferroMacaco);
                detailIntent.putExtra("Eighteen",triangulo);

                startActivity( detailIntent );
            }
        } );


        Intent mIntent = getActivity().getIntent();
        //Get intent from EscolhaVeiculo.class
        if (mIntent != null) {
            String placa = mIntent.getStringExtra(EXTRA_PLACA);
            placaV.setText( placa );
        }
        return vista;
    }

    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Buscando...");
        progresso.show();
        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultaCaM.php?PLACA=" + placaV.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeVistoria tabFichaC = new FichaDeVistoria();
                JSONArray jsonArray = response.optJSONArray( "fichaVistoria" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFichaC.setExtintor( jsonObject.optString( "extintor" ) );
                    tabFichaC.setCapaDeChuva( jsonObject.optString( "capaDeChuva" ) );
                    tabFichaC.setAntena(jsonObject.optString( "Antena" ) );
                    tabFichaC.setRadio( jsonObject.optString("Radio"));
                    tabFichaC.setLona( jsonObject.optString("Lona"));
                    tabFichaC.setColeteRefletivo( jsonObject.optString("ColeteRefletivo"));
                    tabFichaC.setXrefletivo( jsonObject.optString("Xrefletivo"));
                    tabFichaC.setparDeluvas( jsonObject.optString("parDeluvas"));
                    tabFichaC.setCapacete( jsonObject.optString("capacete"));
                    tabFichaC.setGuardaChuva( jsonObject.optString("GuardaChuva"));
                    tabFichaC.setMarteloMadeira( jsonObject.optString("MarteloMadeira"));
                    tabFichaC.setDocumentos( jsonObject.optString("Documentos"));
                    tabFichaC.setAcendedor( jsonObject.optString("Acendedor"));
                    tabFichaC.setCabosForca( jsonObject.optString("CabosForca"));
                    tabFichaC.setMacaco( jsonObject.optString("macaco"));
                    tabFichaC.setferroMacaco( jsonObject.optString("ferroMacaco"));
                    tabFichaC.setTriangulo( jsonObject.optString("triangulo"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //placaV.setText(tabFichaC.getPlacaV());
                viewExtintor.setText(tabFichaC.getExtintor());
                viewCapaChuva.setText(tabFichaC.getCapaDeChuva());
                viewAntena.setText(tabFichaC.getAntena());
                viewRadio.setText(tabFichaC.getRadio());
                viewCapacete.setText(tabFichaC.getCapacete());
                viewLona.setText(tabFichaC.getLona());
                viewColete.setText(tabFichaC.getColeteRefletivo());
                viewX.setText(tabFichaC.getXrefletivo());
                viewParLuvas.setText(tabFichaC.getparDeluvas());
                viewGchuva.setText(tabFichaC.getGuardaChuva());
                viewMarteloMadeira.setText(tabFichaC.getMarteloMadeira());
                viewDocumentos.setText(tabFichaC.getDocumentos());
                viewAcendedor.setText(tabFichaC.getAcendedor());
                viewCaboF.setText(tabFichaC.getCabosForca());
                viewMacaco.setText(tabFichaC.getMacaco());
                viewFmacaco.setText(tabFichaC.getferroMacaco());
                viewTriangulo.setText(tabFichaC.getTriangulo());


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