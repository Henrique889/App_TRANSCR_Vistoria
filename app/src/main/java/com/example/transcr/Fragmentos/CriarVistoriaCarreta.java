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

public class CriarVistoriaCarreta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd2;
    private CheckBox check1,check2,check3,check4;
    EditText txtID, txtPlaca, txtFerramentas, txtCorda, txtCinta, txtCatracas, txtLona, txtlook,
            txtBoloCordas, txtDocumentos, txtQtsChaves, txtEstepe,
            txtPlataforma, txtCantoneira, txtEmergenciaP, txtEmergenciaG;
    ProgressDialog progress;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public CriarVistoriaCarreta () {
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
    public static CriarVistoriaCarreta newInstance ( String param1, String param2 ) {
        CriarVistoriaCarreta fragment = new CriarVistoriaCarreta();
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
        View vista = inflater.inflate( R.layout.scrollcarreta, container, false );
        txtID = vista.findViewById( R.id.txtid );
        txtPlaca = vista.findViewById( R.id.txtPlaca );
        txtFerramentas = vista.findViewById( R.id.txtFerramentas);
        txtLona = vista.findViewById( R.id.txtLona );
        txtCorda = vista.findViewById( R.id.campoCordas );
        txtBoloCordas = vista.findViewById( R.id.txtBoloCorda );
        txtlook= vista.findViewById( R.id.txtLook );
        txtQtsChaves = vista.findViewById( R.id.txtqtsChave );
        txtDocumentos = vista.findViewById( R.id.txtDocumentos );
        txtEstepe = vista.findViewById( R.id.txtEstepe );
        txtPlataforma = vista.findViewById( R.id.txtPlataforma );
        txtCantoneira = vista.findViewById( R.id.txtCantoneira );
        txtCatracas = vista.findViewById( R.id.txtCatraca );
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
        String url = ip + "/webservice/fichaCarreta.php?";
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
                    txtlook.setText("");
                    txtLona.setText("");
                    txtQtsChaves.setText("");
                    txtDocumentos.setText("");
                    txtEstepe.setText("");
                    txtPlataforma.setText("");
                    txtCatracas.setText("");
                    txtCinta.setText("");
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
                    String Ferramentas = txtFerramentas.getText().toString();
                    String Lona = txtLona.getText().toString();
                    String Cordas = txtCorda.getText().toString();
                    String Cinta = txtCinta.getText().toString();
                    String Catraca = txtCatracas.getText().toString();
                    String lookCompleto = txtlook.getText().toString();
                    String BoloCordas = txtBoloCordas.getText().toString();
                    String Documentos = txtDocumentos.getText().toString();
                    String qtsChaves = txtQtsChaves.getText().toString();
                    String Estepe = txtEstepe.getText().toString();
                    String Plataforma = txtPlataforma.getText().toString();
                    String cantoneira = txtCantoneira.getText().toString();
                    String KitEmergenciaG = txtEmergenciaG.getText().toString();
                    String KitEmergenciaP = txtEmergenciaP.getText().toString();

                    Map <String, String> params = new HashMap <>();
                    params.put( "id", id );
                    params.put( "PLACA", PLACA );
                    params.put( "qtsFerramentas", Ferramentas );
                    params.put( "Lona", Lona );
                    params.put( "Cordas", Cordas );
                    params.put( "Cinta", Cinta );
                    params.put( "Catracas", Catraca );
                    params.put( "lookCompleto", lookCompleto );
                    params.put( "BoloCordas", BoloCordas );
                    params.put( "Documentos", Documentos );
                    params.put( "qtsChaves", qtsChaves );
                    params.put( "Estepe", Estepe );
                    params.put( "Plataforma", Plataforma );
                    params.put( "cantoneira", cantoneira );
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
