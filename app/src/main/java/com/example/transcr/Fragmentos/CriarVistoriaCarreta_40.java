package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class CriarVistoriaCarreta_40 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd2;
    private CheckBox check1,check2,check3,check4;
    EditText txtID, txtPlaca, txtFerramentas, txtCorda, txtCinta, txtCatracas, txtLona, txtCatracaS, txtKit_P,
            txtBoloCordas, txtDocumentos, txtQtsChaves, txtEstepe,
            txtKit_G, txtCantoneiraF;
    ProgressDialog progress;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public CriarVistoriaCarreta_40 () {
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
    public static CriarVistoriaCarreta_40 newInstance ( String param1, String param2 ) {
        CriarVistoriaCarreta_40 fragment = new CriarVistoriaCarreta_40();
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
        View vista = inflater.inflate( R.layout.scrollcarreta_40, container, false );
        txtID = vista.findViewById( R.id.txtid );
        txtPlaca = vista.findViewById( R.id.txtPlaca );
        txtFerramentas = vista.findViewById( R.id.txtFerramentas);
        txtLona = vista.findViewById( R.id.txtLona );
        txtCorda = vista.findViewById( R.id.txtCordas );
        txtBoloCordas = vista.findViewById( R.id.txtBoloCorda );
        txtCatracaS= vista.findViewById( R.id.txtCatracasSoltas );
        txtQtsChaves = vista.findViewById( R.id.txtQtsChave );
        txtDocumentos = vista.findViewById( R.id.txtDocumentos );
        txtEstepe = vista.findViewById( R.id.txtEstepe );
        txtCantoneiraF = vista.findViewById( R.id.txtCantoneiraF );
        txtCatracas = vista.findViewById( R.id.txtCatraca );
        txtCinta = vista.findViewById( R.id.txtCinta );
        txtKit_P = vista.findViewById( R.id.txtKit_p );
        txtKit_G = vista.findViewById( R.id.txtKit_g );

        btnAdd2 = vista.findViewById( R.id.btnAdicionar );

        //Só para instanciar o objeto falando que ele vai receber as requisições do tipo Volley
        //requestQueue = Volley.newRequestQueue( getContext() );

        btnAdd2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                carregarWebService();
            }
        } );
        return vista;
    }
    private void carregarWebService () {
        progress = new ProgressDialog( getContext() );
        progress.setMessage( "Carregando..." );
        progress.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/fichaCarreta40.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progress.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtID.setText("");
                    txtPlaca.setText("");
                    txtFerramentas.setText("");
                    txtCorda.setText("");
                    txtBoloCordas.setText("");
                    txtCatracaS.setText("");
                    txtLona.setText("");
                    txtQtsChaves.setText("");
                    txtDocumentos.setText("");
                    txtEstepe.setText("");
                    txtKit_P.setText("");
                    txtCatracas.setText("");
                    txtCinta.setText("");
                    txtCantoneiraF.setText("");
                    txtKit_G.setText("");
                    Toast.makeText( getContext(), "Foi criado com sucesso", Toast.LENGTH_SHORT ).show();
            }else {
                    Toast.makeText( getContext(), "Ficha de Vistoria não inserida", Toast.LENGTH_SHORT ).show();
                    Log.i( "RESPOSTA: ", "" + response );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Toast.makeText( getContext(), "Erro ao inserir " + error, Toast.LENGTH_SHORT ).show();
                Log.i( "RESPOSTA: ", "" + error );
                progress.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {

                    String id = txtID.getText().toString();
                    String PLACA = txtPlaca.getText().toString();
                    String Ferramentas = txtFerramentas.getText().toString();
                    String Lona = txtLona.getText().toString();
                    String Cordas = txtCorda.getText().toString();
                    String Cinta = txtCinta.getText().toString();
                    String Catraca = txtCatracas.getText().toString();
                    String CatracasS = txtCatracaS.getText().toString();
                    String BoloCordas = txtBoloCordas.getText().toString();
                    String Documentos = txtDocumentos.getText().toString();
                    String qtsChaves = txtQtsChaves.getText().toString();
                    String Estepe = txtEstepe.getText().toString();
                    String KitP = txtKit_P.getText().toString();
                    String cantoneiraF = txtCantoneiraF.getText().toString();
                    String KitG = txtKit_G.getText().toString();

                    Map <String, String> params = new HashMap <>();
                    params.put( "id", id );
                    params.put( "PLACA", PLACA );
                    params.put( "qtsFerramentas", Ferramentas );
                    params.put( "Lona", Lona );
                    params.put( "Cordas", Cordas );
                    params.put( "Cinta", Cinta );
                    params.put( "Catracas", Catraca );
                    params.put( "CatracasSoltas", CatracasS );
                    params.put( "BoloCordas", BoloCordas );
                    params.put( "Documentos", Documentos );
                    params.put( "qtsChaves", qtsChaves );
                    params.put( "Estepe", Estepe );
                    params.put( "cantoneiraF", cantoneiraF );
                    params.put( "kit_p_emergencia", KitP );
                    params.put( "kit_g_emergencia", KitG );

                    return params;
            }

        };
        MySingleton.getInstance( getContext() ).addToRequestQueue( stringRequest );
    }
    public void onButtonPressed( Uri uri) {
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
