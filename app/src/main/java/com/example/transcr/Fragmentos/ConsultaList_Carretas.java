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
import com.example.transcr.Adapters.CarretasAdaptador;
import com.example.transcr.R;
import com.example.transcr.entidades.Carretas;
import com.example.transcr.entidades.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultaList_Carretas extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String EXTRA_PLACA = "placaCarreta";

    RecyclerView recyclerCarreta;
    ArrayList<Carretas> listaCarretas;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ImageView imgSemNet;
    private OnFragmentInteractionListener mListener;

    public ConsultaList_Carretas () {
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
    public static ConsultaList_Carretas newInstance( String param1, String param2) {
        ConsultaList_Carretas fragment = new ConsultaList_Carretas();
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
        View vista = inflater.inflate( R.layout.consulta_lista_carretas,container, false);

        listaCarretas = new ArrayList <>(  );

        recyclerCarreta= vista.findViewById( R.id.idRecycler1 );
        recyclerCarreta.setLayoutManager( new LinearLayoutManager( this.getContext() ) );
        recyclerCarreta.setHasFixedSize( true );
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
        String url = ip+"/webservice/consultarListaCarreta.php";


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
        Toast.makeText(getContext(), "Não foi possível listar as Carretas  " +error.toString() , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse ( JSONObject response ) {
        progressDialog.hide();

        Carretas carreta = null;
        JSONArray jsonArray = response.optJSONArray( "carreta" );

        try{
            for(int i=0; i<jsonArray.length();i++){
                carreta = new Carretas();
                JSONObject jsonObject = null;
                // Objeto do tipo jsonObject recebe minha consulta da Tabela Frota1
                jsonObject = jsonArray.getJSONObject( i );

                carreta.setCodigo( jsonObject.optInt( "id" ) );
                carreta.setPlacaCarreta( jsonObject.optString( "placaCarreta" ) );
                carreta.setTipoCarreta( jsonObject.optString( "tipoCarreta" ) );
                listaCarretas.add( carreta );
            }
            progressDialog.hide();
            CarretasAdaptador adapter = new CarretasAdaptador( getContext(), listaCarretas );
            recyclerCarreta.setAdapter( adapter );
            adapter.setOnItemClickListener( new CarretasAdaptador.OnItemClickListener() {
                @Override
                public void onItemClick ( int position ) {
                    Intent detailIntent = new Intent(getContext(), EscolhaTipoVistoria2.class );
                    detailIntent.putExtra( EXTRA_PLACA, listaCarretas.get( position ).getPlacaCarreta());
                    startActivity( detailIntent );
                }
            } );

        }catch (JSONException e) {
            e.printStackTrace();
            progressDialog.hide();
            Toast.makeText(getContext(), "Não foi possível listar as Carretas " + response, Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction ( Uri uri );
    }
}

