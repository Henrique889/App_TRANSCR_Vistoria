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

public class CriarVistoriaCarreta_Sider extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd2;
    private CheckBox check1,check2,check3,check4;
    EditText txtID, txtPlaca, txtCorda, txtCinta, txtCatracas, txtCone, txtcatracaSolta,
            txtChaveRodas, txtDocumentos, txtQtsChaves, txtEstepe,
            txtcaboAco, txtCantoneira, txtEmergenciaP, txtEmergenciaG;
    ProgressDialog progress;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public CriarVistoriaCarreta_Sider () {
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
    public static CriarVistoriaCarreta_Sider newInstance ( String param1, String param2 ) {
        CriarVistoriaCarreta_Sider fragment = new CriarVistoriaCarreta_Sider();
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
        View vista = inflater.inflate( R.layout.scrollcarreta_sider, container, false );
        txtID = vista.findViewById( R.id.txtid );
        txtPlaca = vista.findViewById( R.id.txtPlaca );
        txtChaveRodas = vista.findViewById( R.id.txtChaveRodas);
        txtcaboAco = vista.findViewById( R.id.txtCaboAco );
        txtCorda = vista.findViewById( R.id.txtCordas );
        txtQtsChaves = vista.findViewById( R.id.txtqtsChaves );
        txtDocumentos = vista.findViewById( R.id.txtDocumentos );
        txtEstepe = vista.findViewById( R.id.txtEstepe );
        txtCone = vista.findViewById( R.id.txtCone );
        txtCantoneira = vista.findViewById( R.id.txtCantoneiraF );
        txtCatracas = vista.findViewById( R.id.txtCatraca );
        txtcatracaSolta = vista.findViewById( R.id.txtCatracasS );
        txtCinta = vista.findViewById( R.id.txtCinta );
        txtEmergenciaG = vista.findViewById( R.id.txtKitG );
        txtEmergenciaP = vista.findViewById( R.id.txtKitP );
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
        String url = ip + "/webservice/fichaCarretaSider.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progress.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtID.setText("");
                    txtPlaca.setText("");
                    txtChaveRodas.setText("");
                    txtCorda.setText("");
                    txtCinta.setText("");
                    txtCatracas.setText("");
                    txtcaboAco.setText("");
                    txtQtsChaves.setText("");
                    txtDocumentos.setText("");
                    txtCone.setText("");
                    txtEstepe.setText("");
                    txtcatracaSolta.setText("");
                    txtCantoneira.setText("");
                    txtEmergenciaG.setText("");
                    txtEmergenciaP.setText("");
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
                    String ChaveRodas = txtChaveRodas.getText().toString();
                    String Cordas = txtCorda.getText().toString();
                    String Cinta = txtCinta.getText().toString();
                    String Catraca = txtCatracas.getText().toString();
                    String caboAco = txtcaboAco.getText().toString();
                    String Documentos = txtDocumentos.getText().toString();
                    String qtsChaves = txtQtsChaves.getText().toString();
                    String ConePreto = txtCone.getText().toString();
                    String cantoneiraFerro = txtCantoneira.getText().toString();
                    String Estepe = txtEstepe.getText().toString();
                    String catracaSolta = txtcatracaSolta.getText().toString();
                    String KitEmergenciaG = txtEmergenciaG.getText().toString();
                    String KitEmergenciaP = txtEmergenciaP.getText().toString();

                    Map <String, String> params = new HashMap <>();
                    params.put( "id", id );
                    params.put( "PLACA", PLACA );
                    params.put( "ChaveRodas", ChaveRodas );
                    params.put( "Cordas", Cordas );
                    params.put( "Cinta", Cinta );
                    params.put( "Catracas", Catraca );
                    params.put( "caboAco", caboAco );
                    params.put( "Documentos", Documentos );
                    params.put( "qtsChaves", qtsChaves );
                    params.put( "ConePreto", ConePreto );
                    params.put( "cantoneiraFerro", cantoneiraFerro );
                    params.put( "Estepe", Estepe );
                    params.put( "catracaSolta", catracaSolta );
                    params.put( "KitEmergenciaG", KitEmergenciaG );
                    params.put( "KitEmergenciaP", KitEmergenciaP );

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
