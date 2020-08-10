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
import com.example.transcr.R;
import com.example.transcr.entidades.FichaDeVistoria;
import com.example.transcr.entidades.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class VistoriaCavalo_P1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaVeiculo;
    TextView viewExtintor, viewCapaChuva, viewRadio, viewAntena,viewLona,viewColete,viewX, viewParLuvas, viewCapacete, viewGchuva,
            viewMarteloMadeira, viewDocumentos, viewAcendedor, viewCaboF, viewMarteloF, viewMacaco, viewFmacaco, viewTriangulo;
    EditText camponome, campoExtintor, campoCapaChuva, campoRadio, campoAntena, campoLona, campoColete, campoX,campoParLuvas,
            campoCapacete, campoGchuva, campoMarteloMadeira, campoDocumentos, campoAcendedor, campoCaboF,campoMarteloF, campoMacaco,
            campoFmacaco, campoTriangulo;
    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;

    private OnFragmentInteractionListener mListener;
    TextView textViewDate;

    public VistoriaCavalo_P1() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static VistoriaCavalo_P1 newInstance(String param1, String param2) {
        VistoriaCavalo_P1 fragment = new VistoriaCavalo_P1();
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
        View vista = inflater.inflate( R.layout.scrollfichacavalo_p1, container, false);
        camponome = vista.findViewById(R.id.camponome);
        placaVeiculo = vista.findViewById(R.id.placa );
        btn = vista.findViewById(R.id.button2);
        btnAdicionar = vista.findViewById(R.id.proBtn);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = vista.findViewById(R.id.txt_date1);
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
        campoMarteloF = vista.findViewById( R.id.campoMarteloFerro );
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
        viewX= vista.findViewById( R.id.viewX );
        viewParLuvas = vista.findViewById( R.id.viewLuvas );
        viewGchuva = vista.findViewById( R.id.viewGChuva );
        viewMarteloMadeira = vista.findViewById( R.id.viewMartelo );
        viewMarteloF = vista.findViewById( R.id.viewMarteloFerro );
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
                Intent detailIntent = new Intent(getContext(), VistoriaCavalo_P2.class );
                detailIntent.putExtra( EXTRA_PLACA,placaVeiculo.getText().toString());
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
                String MarteloFerro = campoMarteloF.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String Acendedor = campoAcendedor.getText().toString();
                String CabosForca = campoCaboF.getText().toString();
                String macaco = campoMacaco.getText().toString();
                String ferroMacaco = campoFmacaco.getText().toString();
                String triangulo = campoTriangulo.getText().toString();

                detailIntent.putExtra(EXTRA_PLACA,placaVeiculo.getText().toString());
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
                detailIntent.putExtra("Thirteen",MarteloFerro);
                detailIntent.putExtra("Fourteen",Documentos);
                detailIntent.putExtra("Fifteen",Acendedor);
                detailIntent.putExtra("Sixteen",CabosForca);
                detailIntent.putExtra("Seventeen",macaco);
                detailIntent.putExtra("Eighteen",ferroMacaco);
                detailIntent.putExtra("Nineteen",triangulo);

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
        String url = ip+"/webservice/consultaCavalo.php?PLACA=" + placaVeiculo.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeVistoria tabFicha = new FichaDeVistoria();
                JSONArray jsonArray = response.optJSONArray( "fichaCavalo" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFicha.setExtintor( jsonObject.optString( "extintor" ) );
                    tabFicha.setCapaDeChuva( jsonObject.optString( "capaDeChuva" ) );
                    tabFicha.setAntena(jsonObject.optString( "Antena" ) );
                    tabFicha.setRadio( jsonObject.optString("Radio"));
                    tabFicha.setLona( jsonObject.optString("Lona"));
                    tabFicha.setColeteRefletivo( jsonObject.optString("ColeteRefletivo"));
                    tabFicha.setXrefletivo( jsonObject.optString("Xrefletivo"));
                    tabFicha.setparDeluvas( jsonObject.optString("parDeluvas"));
                    tabFicha.setCapacete( jsonObject.optString("capacete"));
                    tabFicha.setGuardaChuva( jsonObject.optString("GuardaChuva"));
                    tabFicha.setMarteloMadeira( jsonObject.optString("MarteloMadeira"));
                    tabFicha.setMarteloFerro( jsonObject.optString("MarteloFerro"));
                    tabFicha.setDocumentos( jsonObject.optString("Documentos"));
                    tabFicha.setAcendedor( jsonObject.optString("Acendedor"));
                    tabFicha.setCabosForca( jsonObject.optString("CabosForca"));
                    tabFicha.setMacaco( jsonObject.optString("macaco"));
                    tabFicha.setferroMacaco( jsonObject.optString("ferroMacaco"));
                    tabFicha.setTriangulo( jsonObject.optString("triangulo"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //placaV.setText(tabFichaC.getPlacaV());
                viewExtintor.setText(tabFicha.getExtintor());
                viewCapaChuva.setText(tabFicha.getCapaDeChuva());
                viewAntena.setText(tabFicha.getAntena());
                viewRadio.setText(tabFicha.getRadio());
                viewCapacete.setText(tabFicha.getCapacete());
                viewLona.setText(tabFicha.getLona());
                viewColete.setText(tabFicha.getColeteRefletivo());
                viewX.setText(tabFicha.getXrefletivo());
                viewParLuvas.setText(tabFicha.getparDeluvas());
                viewGchuva.setText(tabFicha.getGuardaChuva());
                viewMarteloMadeira.setText(tabFicha.getMarteloMadeira());
                viewMarteloF.setText(tabFicha.getMarteloFerro());
                viewDocumentos.setText(tabFicha.getDocumentos());
                viewAcendedor.setText(tabFicha.getAcendedor());
                viewCaboF.setText(tabFicha.getCabosForca());
                viewMacaco.setText(tabFicha.getMacaco());
                viewFmacaco.setText(tabFicha.getferroMacaco());
                viewTriangulo.setText(tabFicha.getTriangulo());


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