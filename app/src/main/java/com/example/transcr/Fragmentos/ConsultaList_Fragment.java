package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.transcr.Adapters.FrotaAdaptador;
import com.example.transcr.R;
import com.example.transcr.entidades.Frota1;
import com.example.transcr.entidades.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultaList_Fragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerFrota;
    ArrayList<Frota1> listaFrotas;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ImageView imgSemNet;
    private OnFragmentInteractionListener mListener;

    public static final String EXTRA_PLACA = "placaVeiculo";


    public ConsultaList_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment consultarListaCursosFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaList_Fragment newInstance(String param1, String param2) {
        ConsultaList_Fragment fragment = new ConsultaList_Fragment();
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
    public View onCreateView ( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate( R.layout.consulta_list_veiculos,container, false);

        listaFrotas = new ArrayList <>(  );

        recyclerFrota= vista.findViewById( R.id.idRecycler );
        recyclerFrota.setLayoutManager( new LinearLayoutManager( this.getContext() ) );
        recyclerFrota.setHasFixedSize( true );
        imgSemNet=  vista.findViewById(R.id.imgSemNet);
        imgSemNet.setVisibility(View.INVISIBLE);

        // request= Volley.newRequestQueue(getContext());

        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = con.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnected()) {
            imgSemNet.setVisibility(View.INVISIBLE);
            carregarWebService();
        }else{
            imgSemNet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "É preciso se conectar para receber informações do Aplicativo" , Toast.LENGTH_LONG).show();
        }

        return vista;
    }

    private void carregarWebService () {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Buscando...");
        progressDialog.show();

        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultarListafrota.php";


        jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, url, null, this, this);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
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

    @Override
    public void onErrorResponse ( VolleyError error ) {
        progressDialog.hide();
        Toast.makeText(getContext(), "Não foi possível listar a frota  " +error.toString() , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse ( JSONObject response ) {
        progressDialog.hide();

        Frota1 frota1 = null;
        JSONArray jsonArray = response.optJSONArray( "frota1" );

        try{
            for(int i=0; i<jsonArray.length();i++){
                frota1 = new Frota1();
                JSONObject jsonObject = null;
                // Objeto do tipo jsonObject recebe minha consulta da Tabela Frota1
                jsonObject = jsonArray.getJSONObject( i );

                frota1.setCodigo( jsonObject.optInt( "id" ) );
                frota1.setPlacaVehicles( jsonObject.optString( "placaVeiculo" ) );
                frota1.setModeloVehicles( jsonObject.optString( "modelo" ) );
                listaFrotas.add( frota1 );
            }
            progressDialog.hide();
            FrotaAdaptador adapter = new FrotaAdaptador(getContext(), listaFrotas );
            recyclerFrota.setAdapter( adapter );
            adapter.setOnItemClickListener( new FrotaAdaptador.OnItemClickListener() {
                @Override
                public void onItemClick ( int position ) {
                    Intent detailIntent = new Intent(getContext(), EscolhaTipoVistoria.class );
                    detailIntent.putExtra( EXTRA_PLACA, listaFrotas.get( position ).getPlacaVehicles());
                    startActivity( detailIntent );
                }
            } );

        }catch (JSONException e) {
            e.printStackTrace();
            progressDialog.hide();
            Toast.makeText(getContext(), "Não foi possível listar a frota " + response, Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction( Uri uri);
    }
}

