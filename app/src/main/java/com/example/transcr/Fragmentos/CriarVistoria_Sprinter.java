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

public class CriarVistoria_Sprinter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd1;
    private CheckBox chkLampadaD, chkLampadaL, chkLampadaT, chkInterior;
    EditText txtID, txtPlaca, txtExtintor, txtCapaChuva,txtColete,txtCatracaM,txtOculos,txtControle,
            txtX, txtLuvas, txtCapacete, txtGchuva, txtMartelo, txtDocumentos, txtAcendedor,
            txtMacaco, txtFmacaco, txtTriangulo, txtChavesRodas,txtCarrinho,txtCalotas,
            txtTapetes, txtCabo, txtCatracas, txtCordas, txtCinta, txtChaves,
            txtEmergenciaP, txtEmergenciaG;
    ProgressDialog progress;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

   private OnFragmentInteractionListener mListener;

    public CriarVistoria_Sprinter () {
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
    public static CriarVistoria_Sprinter newInstance ( String param1, String param2 ) {
        CriarVistoria_Sprinter fragment = new CriarVistoria_Sprinter();
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
        View vista = inflater.inflate( R.layout.scroll2, container, false );

        txtID = vista.findViewById( R.id.EdID );
        txtPlaca = vista.findViewById( R.id.EdPlaca );
        txtExtintor = vista.findViewById( R.id.EdExtintor );
        txtCapaChuva = vista.findViewById( R.id.EdCapa );
        txtColete = vista.findViewById( R.id.edColete );
        txtX = vista.findViewById( R.id.edX );
        txtLuvas = vista.findViewById( R.id.edLuva );
        txtCapacete = vista.findViewById( R.id.edCapacete );
        txtGchuva = vista.findViewById( R.id.edGuarda );
        txtMartelo = vista.findViewById( R.id.edMartelo );
        txtDocumentos = vista.findViewById( R.id.edDocumentos );
        txtAcendedor = vista.findViewById( R.id.edAcendedor );
        txtMacaco = vista.findViewById( R.id.edMacaco );
        txtFmacaco = vista.findViewById( R.id.edFerro );
        txtTriangulo = vista.findViewById( R.id.edTriangulo );
        txtChavesRodas = vista.findViewById( R.id.edChaveRodas );
        txtTapetes = vista.findViewById( R.id.edTapetes );
        txtCabo = vista.findViewById( R.id.edCabo);
        txtCatracas = vista.findViewById( R.id.edCatraca );
        txtCatracaM = vista.findViewById( R.id.edCatracaMovel );
        txtCordas = vista.findViewById( R.id.edCordas );
        txtCinta = vista.findViewById( R.id.edCintas );
        txtCarrinho = vista.findViewById( R.id.edCarrinho );
        txtCalotas = vista.findViewById( R.id.edCalota );
        txtChaves = vista.findViewById( R.id.edQtsChave );
        txtOculos = vista.findViewById( R.id.edOculosP );
        txtControle = vista.findViewById( R.id.edControleP );
        txtEmergenciaG = vista.findViewById( R.id.edKitG );
        txtEmergenciaP = vista.findViewById( R.id.edKitP );
        btnAdd1 = vista.findViewById( R.id.btnAdd );

        chkLampadaD = vista.findViewById( R.id.chkLampadaD );
        chkLampadaL = vista.findViewById( R.id.chkLampadaL );
        chkLampadaT = vista.findViewById( R.id.chkLampadaT );
        chkInterior = vista.findViewById( R.id.chkInterior );
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
        String url = ip + "/webservice/fichas_Sprinter.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progress.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtID.setText("");
                    txtPlaca.setText("");
                    txtExtintor.setText("");
                    txtCapaChuva.setText("");
                    txtColete.setText("");
                    txtX.setText("");
                    txtLuvas.setText("");
                    txtCapacete.setText("");
                    txtGchuva.setText("");
                    txtMartelo.setText("");
                    txtDocumentos.setText("");
                    txtAcendedor.setText("");
                    txtMacaco.setText("");
                    txtFmacaco.setText("");
                    txtTriangulo.setText("");
                    txtCabo.setText("");
                    txtChavesRodas.setText("");
                    txtTapetes.setText("");
                    txtCatracas.setText("");
                    txtCatracaM.setText("");
                    txtCordas.setText("");
                    txtCinta.setText("");
                    txtCarrinho.setText("");
                    txtCalotas.setText("");
                    txtChaves.setText("");
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
                    String extintor = txtExtintor.getText().toString();
                    String capaDeChuva = txtCapaChuva.getText().toString();
                    String ColeteRefletivo = txtColete.getText().toString();
                    String Xrefletivo = txtX.getText().toString();
                    String parDeluvas = txtLuvas.getText().toString();
                    String capacete = txtCapacete.getText().toString();
                    String GuardaChuva = txtGchuva.getText().toString();
                    String MarteloMadeira = txtMartelo.getText().toString();
                    String Documentos = txtDocumentos.getText().toString();
                    String Acendedor = txtAcendedor.getText().toString();
                    String CabosForca = txtCabo.getText().toString();
                    String macaco = txtMacaco.getText().toString();
                    String ferroMacaco = txtFmacaco.getText().toString();
                    String triangulo = txtTriangulo.getText().toString();
                    String ChaveRodas = txtChavesRodas.getText().toString();
                    String tapetes = txtTapetes.getText().toString();
                    String catracas = txtCatracas.getText().toString();
                    String catracaM = txtCatracaM.getText().toString();
                    String cordas = txtCordas.getText().toString();
                    String cinta = txtCinta.getText().toString();
                    String carrinho = txtCarrinho.getText().toString();
                    String calotas = txtCalotas.getText().toString();
                    String qtsChaves = txtChaves.getText().toString();
                    String oculos_p = txtOculos.getText().toString();
                    String controle_p = txtControle.getText().toString();
                    String KitEmergenciaP = txtEmergenciaP.getText().toString();
                    String KitEmergenciaG = txtEmergenciaG.getText().toString();
                    String lampadaDianteira = chkLampadaD.getText().toString();
                    String lampadaLaterais = chkLampadaL.getText().toString();
                    String lampadaTraseiras = chkLampadaT.getText().toString();
                    String interior = chkLampadaT.getText().toString();


                    Map <String, String> params = new HashMap <>();
                    params.put( "id", id );
                    params.put( "PLACA", PLACA );
                    params.put( "extintor", extintor );
                    params.put( "capaDeChuva", capaDeChuva );
                params.put( "ColeteRefletivo", ColeteRefletivo );
                    params.put( "Xrefletivo", Xrefletivo );
                    params.put( "parDeluvas", parDeluvas );
                    params.put( "capacete", capacete );
                    params.put( "GuardaChuva", GuardaChuva );
                    params.put( "MarteloMadeira", MarteloMadeira );
                    params.put( "Documentos", Documentos );
                    params.put( "Acendedor", Acendedor );
                    params.put( "CabosForca", CabosForca );
                    params.put( "macaco", macaco );
                    params.put( "ferroMacaco", ferroMacaco );
                    params.put( "triangulo", triangulo );
                    params.put( "ChaveRodas", ChaveRodas );
                    params.put( "tapetes", tapetes );
                    params.put( "catracas", catracas );
                    params.put( "Catracas_M", catracaM );
                    params.put( "cordas", cordas );
                    params.put( "cinta", cinta );
                    params.put( "carrinhoHidraulico", carrinho );
                    params.put( "calotas", calotas );
                    params.put( "qtsChaves", qtsChaves );
                    params.put( "OculosProtecao", oculos_p );
                    params.put( "ControlePortao", controle_p );
                    params.put( "KitEmergenciaG", KitEmergenciaG );
                    params.put( "KitEmergenciaP", KitEmergenciaP );
                    if(chkLampadaD.isChecked()) {
                        params.put( "lampadasDianteiras", lampadaDianteira );
                    }
                    if(chkLampadaL.isChecked()) {
                        params.put( "lampadasLaterais", lampadaLaterais );
                    }
                    if(chkLampadaT.isChecked()) {
                        params.put( "lampadasTraseiras", lampadaTraseiras );
                    }
                    if(chkInterior.isChecked()) {
                        params.put( "interior", interior );
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
