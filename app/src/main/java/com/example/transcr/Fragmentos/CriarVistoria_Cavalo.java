package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

public class CriarVistoria_Cavalo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd1;
    private CheckBox chkInterclima, chkLampadaD, chkLampadaL, chkLampadaT, chkInterior;
    EditText txtID, txtPlaca, txtExtintor, txtCapaChuva, txtRadio, txtAntena, txtLona, txtColete,
            txtX, txtLuvas, txtCapacete, txtGchuva, txtMartelo, txtMarteloferro, txtDocumentos, txtAcendedor, txtCaboF,
            txtMacaco, txtFmacaco, txtTriangulo, txtChavesRodas, txtOculos, txtControle,
            txtTacografo, txtTapetes, txtInterclima, txtCordas,  txtCinta, txtChaves, txtLanterna,
            txtEmergenciaP, txtEmergenciaG;
    ProgressDialog progress;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public CriarVistoria_Cavalo () {
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
    public static CriarVistoria_Cavalo newInstance ( String param1, String param2 ) {
        CriarVistoria_Cavalo fragment = new CriarVistoria_Cavalo();
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
        View vista = inflater.inflate( R.layout.scroll5, container, false );
        txtID = vista.findViewById( R.id.txtid );
        txtPlaca = vista.findViewById( R.id.txtPlaca );
        txtExtintor = vista.findViewById( R.id.txtExtintor );
        txtCapaChuva = vista.findViewById( R.id.txtCapa );
        txtRadio = vista.findViewById( R.id.txtRadio );
        txtAntena = vista.findViewById( R.id.txtAntena );
        txtLona = vista.findViewById( R.id.txtLona );
        txtColete = vista.findViewById( R.id.txtColete );
        txtX = vista.findViewById( R.id.txtX );
        txtLuvas = vista.findViewById( R.id.txtLuvas );
        txtCapacete = vista.findViewById( R.id.txtcapacete );
        txtGchuva = vista.findViewById( R.id.txtGchuva );
        txtMartelo = vista.findViewById( R.id.txtMartelo );
        txtMarteloferro = vista.findViewById( R.id.txtMarteloferro );
        txtDocumentos = vista.findViewById( R.id.txtDocumentos );
        txtAcendedor = vista.findViewById( R.id.txtAcendedor );
        txtCaboF = vista.findViewById( R.id.txtCaboF );
        txtMacaco = vista.findViewById( R.id.txtMacaco );
        txtFmacaco = vista.findViewById( R.id.txtFMacaco );
        txtTriangulo = vista.findViewById( R.id.txtTriangulo );
        txtChavesRodas = vista.findViewById( R.id.txtChaveRodas );
        txtTacografo = vista.findViewById( R.id.txtTacografo );
        txtTapetes = vista.findViewById( R.id.txtTapetes );
        txtCordas = vista.findViewById( R.id.txtCordas );
        txtCinta = vista.findViewById( R.id.txtCinta );
        txtChaves = vista.findViewById( R.id.txtQtschave );
        txtLanterna = vista.findViewById( R.id.txtLanterna );
        txtOculos = vista.findViewById( R.id.txtOculosProtecao );
        txtControle = vista.findViewById( R.id.txtControlePortao );
        txtEmergenciaG = vista.findViewById( R.id.txtEmergenciaG );
        txtEmergenciaP = vista.findViewById( R.id.txtEmergenciaP );
        txtInterclima = vista.findViewById( R.id.txtInterclima );
        btnAdd1 = vista.findViewById( R.id.btnAdicionar );

        chkInterclima = vista.findViewById( R.id.chkInterclima );
        chkLampadaD = vista.findViewById( R.id.chkLampadaD );
        chkLampadaL = vista.findViewById( R.id.chkLampadaL );
        chkLampadaT = vista.findViewById( R.id.chkLampadaT );

        //Só para instanciar o objeto falando que ele vai receber as requisições do tipo Volley
        //requestQueue = Volley.newRequestQueue( getContext() );

        btnAdd1.setOnClickListener( new View.OnClickListener() {
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
        String url = ip + "/webservice/fichaCavalo.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progress.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtID.setText("");
                    txtPlaca.setText("");
                    txtExtintor.setText("");
                    txtCapaChuva.setText("");
                    txtRadio.setText("");
                    txtAntena.setText("");
                    txtLona.setText("");
                    txtColete.setText("");
                    txtX.setText("");
                    txtLuvas.setText("");
                    txtCapacete.setText("");
                    txtGchuva.setText("");
                    txtMartelo.setText("");
                    txtMarteloferro.setText("");
                    txtDocumentos.setText("");
                    txtAcendedor.setText("");
                    txtCaboF.setText("");
                    txtMacaco.setText("");
                    txtFmacaco.setText("");
                    txtTriangulo.setText("");
                    txtChavesRodas.setText("");
                    txtTacografo.setText("");
                    txtTapetes.setText("");
                    txtCordas.setText("");
                    txtCinta.setText("");
                    txtChaves.setText("");
                    txtLanterna.setText("");
                    txtOculos.setText("");
                    txtControle.setText("");
                    txtEmergenciaG.setText("");
                    txtEmergenciaP.setText("");
                    txtInterclima.setText("");
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
                    String extintor = txtExtintor.getText().toString();
                    String capaDeChuva = txtCapaChuva.getText().toString();
                    String Radio = txtRadio.getText().toString();
                    String Antena = txtAntena.getText().toString();
                    String Lona = txtLona.getText().toString();
                    String ColeteRefletivo = txtColete.getText().toString();
                    String Xrefletivo = txtX.getText().toString();
                    String parDeluvas = txtLuvas.getText().toString();
                    String capacete = txtCapacete.getText().toString();
                    String GuardaChuva = txtGchuva.getText().toString();
                    String MarteloMadeira = txtMartelo.getText().toString();
                    String MarteloFerro = txtMarteloferro.getText().toString();
                    String Documentos = txtDocumentos.getText().toString();
                    String Acendedor = txtAcendedor.getText().toString();
                    String CabosForca = txtCaboF.getText().toString();
                    String macaco = txtMacaco.getText().toString();
                    String ferroMacaco = txtFmacaco.getText().toString();
                    String triangulo = txtTriangulo.getText().toString();
                    String ChaveRodas = txtChavesRodas.getText().toString();
                    String Tacografo = txtTacografo.getText().toString();
                    String tapetes = txtTapetes.getText().toString();
                    String cordas = txtCordas.getText().toString();
                    String cinta = txtCinta.getText().toString();
                    String qtsChaves = txtChaves.getText().toString();
                    String lanterna = txtLanterna.getText().toString();
                    String Oculos_Pr = txtOculos.getText().toString();
                    String Controle_Pr = txtControle.getText().toString();
                    String KitEmergenciaG = txtEmergenciaG.getText().toString();
                    String KitEmergenciaP = txtEmergenciaP.getText().toString();
                    String interclima = txtInterclima.getText().toString();
                    String chekInterclima = chkInterclima.getText().toString();
                    String lampadaDianteira = chkLampadaD.getText().toString();
                    String lampadaLaterais = chkLampadaL.getText().toString();
                    String lampadaTraseiras = chkLampadaT.getText().toString();

                    Map <String, String> params = new HashMap <>();
                    params.put( "id", id );
                    params.put( "PLACA", PLACA );
                    params.put( "extintor", extintor );
                    params.put( "capaDeChuva", capaDeChuva );
                    params.put( "Antena", Antena );
                    params.put( "Radio", Radio );
                    params.put( "Lona", Lona );
                    params.put( "ColeteRefletivo", ColeteRefletivo );
                    params.put( "Xrefletivo", Xrefletivo );
                    params.put( "parDeluvas", parDeluvas );
                    params.put( "capacete", capacete );
                    params.put( "GuardaChuva", GuardaChuva );
                    params.put( "MarteloMadeira", MarteloMadeira );
                    params.put( "MarteloFerro", MarteloFerro );
                    params.put( "Documentos", Documentos );
                    params.put( "Acendedor", Acendedor );
                    params.put( "CabosForca", CabosForca );
                    params.put( "macaco", macaco );
                    params.put( "ferroMacaco", ferroMacaco );
                    params.put( "triangulo", triangulo );
                    params.put( "ChaveRodas", ChaveRodas );
                    params.put( "Tacografo", Tacografo );
                    params.put( "tapetes", tapetes );
                    params.put( "cordas", cordas );
                    params.put( "cinta", cinta );
                    params.put( "qtsChaves", qtsChaves );
                    params.put( "laterna", lanterna );
                    params.put( "OculosProtecao", Oculos_Pr );
                    params.put( "ControlePortao", Controle_Pr );
                    params.put( "KitEmergenciaG", KitEmergenciaG );
                    params.put( "KitEmergenciaP", KitEmergenciaP );
                    params.put( "Interclima", interclima );
                    if(chkInterclima.isChecked()) {
                        params.put( "chekInterclima", chekInterclima );
                    }
                    if(chkLampadaD.isChecked()) {
                        params.put( "lampadasDianteiras", lampadaDianteira );
                    }
                    if(chkLampadaL.isChecked()) {
                        params.put( "lampadasLaterais", lampadaLaterais );
                    }
                    if(chkLampadaT.isChecked()) {
                        params.put( "lampadasTraseiras", lampadaTraseiras );
                    }

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
