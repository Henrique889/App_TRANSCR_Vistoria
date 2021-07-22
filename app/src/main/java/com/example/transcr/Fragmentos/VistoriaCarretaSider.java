package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.FichaDeCarretasVistoria;
import com.example.transcr.entidades.MySingleton;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class VistoriaCarretaSider extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textViewDate;
    private static final String EXTRA_PLACA = "placaCarreta" ;
    EditText placa;
    CheckBox checkLateral, checkLateral2, chkLampadaT,chkLampadaT2;
    TextView viewChaveRodas, viewCordas, viewCinta, viewqtsChaves, viewDocumentos, viewcaboAco, viewCatracas,viewCatracasSoltas,
             viewEstepe, viewKitG, viewKitP, viewCantoneira;
    EditText camponome, campoChaveRodas, campoCordas, campoCinta, campoqtsChaves, campoDocumentos, campocaboAco, campoCatracas,
            campoCatracasSoltas, campoEstepe, campoKitG, campoKitP, campoCantoneira;

    Spinner P_eixo,S_eixo,T_eixo, leftP_eixo,leftS_eixo, leftT_eixo;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private AsyncHttpClient cliente;
    private Spinner spVistoria;

    private OnFragmentInteractionListener mListener;

    public VistoriaCarretaSider () {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static VistoriaCarretaSider newInstance( String param1, String param2) {
        VistoriaCarretaSider fragment = new VistoriaCarretaSider();
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
        View vista = inflater.inflate( R.layout.scrollfichacarretasider, container, false);
        camponome = vista.findViewById(R.id.camponome);
        placa = vista.findViewById(R.id.placa );
        btn = vista.findViewById(R.id.button2);
        btnAdicionar = vista.findViewById(R.id.btnAdicione);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = vista.findViewById(R.id.text_date3);
        textViewDate.setText(currentDate);

        P_eixo = vista.findViewById(R.id.spinner);
        S_eixo = vista.findViewById(R.id.spinner6);
        T_eixo = vista.findViewById(R.id.spinner7);
        leftP_eixo = vista.findViewById(R.id.spinner5);
        leftS_eixo = vista.findViewById(R.id.spinner3);
        leftT_eixo = vista.findViewById(R.id.spinner2);

        String[] list = getResources().getStringArray( R.array.pneus_obs );
        ArrayAdapter<String> adapter = new ArrayAdapter <String>( getContext(),R.layout.support_simple_spinner_dropdown_item,list );
        P_eixo.setAdapter( adapter );
        S_eixo.setAdapter( adapter );
        T_eixo.setAdapter( adapter );

        leftP_eixo.setAdapter( adapter );
        leftS_eixo.setAdapter( adapter );
        leftT_eixo.setAdapter( adapter );

        checkLateral = vista.findViewById( R.id.checkLL );
        checkLateral2 = vista.findViewById( R.id.chckleftLL2 );
        chkLampadaT = vista.findViewById( R.id.checkLT );
        chkLampadaT2 = vista.findViewById( R.id.chekrightLT2 );

        campocaboAco = vista.findViewById( R.id.campoCaboAco );
        campoDocumentos = vista.findViewById( R.id.campoDocumentos );
        campoChaveRodas = vista.findViewById( R.id.campoChaveRodas);
        campoCordas = vista.findViewById( R.id.campoCordas );
        campoCinta = vista.findViewById( R.id.campoCinta);
        campoqtsChaves = vista.findViewById( R.id.campoQtsChaves);
        campoKitG = vista.findViewById( R.id.campoKitGrande);
        campoKitP = vista.findViewById( R.id.campoKitPequeno);
        campoCatracas = vista.findViewById( R.id.campoCatracas);
        campoCatracasSoltas = vista.findViewById( R.id.campoCatracaS);
        campoEstepe = vista.findViewById( R.id.campoEstepe);
        campoCantoneira = vista.findViewById( R.id.campoCantoneira);

        viewcaboAco = vista.findViewById( R.id.viewCaboAco );
        viewDocumentos = vista.findViewById( R.id.viewDocumentos );
        viewChaveRodas = vista.findViewById( R.id.viewChavesRoda);
        viewCordas = vista.findViewById( R.id.viewCordas );
        viewCinta = vista.findViewById( R.id.viewCinta);
        viewqtsChaves = vista.findViewById( R.id.viewQtsChave);
        viewKitG = vista.findViewById( R.id.viewKitG);
        viewKitP = vista.findViewById( R.id.viewKitP);
        viewCatracas = vista.findViewById( R.id.viewCatracas);
        viewCatracasSoltas = vista.findViewById( R.id.viewCatracasSoltas);
        viewEstepe = vista.findViewById( R.id.viewGChuva);
        viewCantoneira = vista.findViewById( R.id.viewCantoneira);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                carregarWEBService();
            }
        } );

        btnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                if(checkLateral.isChecked() && chkLampadaT.isChecked() ) {
                    carregarWebService1();
                }
                else if(checkLateral.isChecked()){
                    carregarWebServiceAlternateB();
                }
                else if(chkLampadaT.isChecked()){
                    carregarWebServiceAlternateC();
                }else{
                    carregarWebServiceAlternate();
                }
              }
        } );

        Intent mIntent = getActivity().getIntent();
        //Get intent from EscolhaVeiculo.class
        if (mIntent != null) {
            String placaV = mIntent.getStringExtra(EXTRA_PLACA);
            placa.setText( placaV);
        }


        return vista;
    }

    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Buscando...");
        progresso.show();
        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultaCarretaSider.php?PLACA=" + placa.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeCarretasVistoria tabFicha = new FichaDeCarretasVistoria();
                JSONArray jsonArray = response.optJSONArray( "fichaCarreta_Sider" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFicha.setChaveRodas( jsonObject.optString( "ChaveRodas" ) );
                    tabFicha.setCordas( jsonObject.optString( "Cordas" ) );
                    tabFicha.setCinta(jsonObject.optString( "Cinta" ) );
                    tabFicha.setCatraca( jsonObject.optString("Catracas"));
                    tabFicha.setcaboAco( jsonObject.optString("caboAco"));
                    tabFicha.setDocumentos( jsonObject.optString("Documentos"));
                    tabFicha.setqtsChaves( jsonObject.optString("qtsChaves"));
                    tabFicha.setCantoneiraFerro( jsonObject.optString("cantoneiraFerro"));
                    tabFicha.setEstepe( jsonObject.optString("Estepe"));
                    tabFicha.setCatracaSolta( jsonObject.optString("catracaSolta"));
                    tabFicha.setSuporteG( jsonObject.optString("KitEmergenciaG"));
                    tabFicha.setSuporteP( jsonObject.optString("KitEmergenciaP"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //placaV.setText(tabFichaC.getPlacaV());
                viewChaveRodas.setText(tabFicha.getChaveRodas());
                viewCordas.setText(tabFicha.getCordas());
                viewCinta.setText(tabFicha.getCinta());
                viewCatracas.setText(tabFicha.getCatraca());
                viewcaboAco.setText(tabFicha.getcaboAco());
                viewDocumentos.setText(tabFicha.getDocumentos());
                viewqtsChaves.setText(tabFicha.getqtsChaves());
                viewCantoneira.setText(tabFicha.getCantoneiraFerro());
                viewEstepe.setText(tabFicha.getEstepe());
                viewCatracasSoltas.setText(tabFicha.getcatracaSolta());
                viewKitG.setText(tabFicha.getSuporteG());
                viewKitP.setText(tabFicha.getSuporteP());

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mFrgAct = getActivity();
        //Intent mIntent = mFrgAct.getIntent(); //  Intent intent = new Intent(getActivity().getIntent());
    }
    private void carregarWebService1 () {
        progresso = new ProgressDialog( getContext() );
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/cadastro_VistoriaCarretaSider.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoChaveRodas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campocaboAco.setText("");
                    campoqtsChaves.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoCatracasSoltas.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
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
                progresso.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {
                String PLACA = placa.getText().toString();
                String nome = camponome.getText().toString();
                String data = textViewDate.getText().toString();
                String ChaveRodas = campoChaveRodas.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String caboAco = campocaboAco.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Cantoneira = campoCantoneira.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String catracaSolta = campoCatracasSoltas.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String LampadasLaterais = checkLateral.getText().toString();
                String LampadasTraseiras = chkLampadaT.getText().toString();
                String eixoDireitoUm = P_eixo.getSelectedItem().toString();
                String eixoDireitoDois = S_eixo.getSelectedItem().toString();
                String eixoDireitoTres = T_eixo.getSelectedItem().toString();
                String eixoEsquerdoUm = leftP_eixo.getSelectedItem().toString();
                String eixoEsquerdoDois = leftS_eixo.getSelectedItem().toString();
                String eixoEsquerdoTres = leftT_eixo.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
                params.put( "ChaveRodas", ChaveRodas );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "caboAco", caboAco );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "Cantoneira", Cantoneira );
                params.put( "Estepe", Estepe );
                params.put( "catracaSolta", catracaSolta );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "LampadasLaterais", LampadasLaterais );
                params.put( "LampadasTraseiras", LampadasTraseiras );
                params.put( "eixoDireitoUm", eixoDireitoUm );
                params.put( "eixoDireitoDois", eixoDireitoDois );
                params.put( "eixoDireitoTres", eixoDireitoTres );
                params.put( "eixoEsquerdoUm", eixoEsquerdoUm );
                params.put( "eixoEsquerdoDois", eixoEsquerdoDois );
                params.put( "eixoEsquerdoTres", eixoEsquerdoTres );

                return params;
            }

        };
        MySingleton.getInstance( getContext() ).addToRequestQueue( stringRequest );

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta Sider ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Chave de Rodas: "+
                campoChaveRodas.getText().toString()+", Cordas: "+ campoCordas.getText().toString()+
                ", Cinta: "+ campoCinta.getText().toString()+ ", Catracas: "+ campoCatracas.getText().toString()+
                ", Cabo de Aço: "+ campocaboAco.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+", Cantoneira: "+ campoCantoneira.getText().toString()+
                ", Estepe: "+campoEstepe.getText().toString()+", Catraca Solta: "+campoCatracasSoltas.getText().toString()+
                ", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+
                ", Lâmpadas Laterais Funcionando: "+checkLateral.getText().toString()+
                ", Lâmpadas Traseira Funcionando: "+chkLampadaT.getText().toString() +
                ", 1º Eixo Lado Direito: "+P_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Direito: "+S_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Direito: "+T_eixo.getSelectedItem().toString()+
                ", 1º Eixo Lado Esquerdo: "+leftP_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Esquerdo: "+leftS_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Esquerdo: "+leftT_eixo.getSelectedItem().toString());
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
    }
    private void carregarWebServiceAlternate () {
        progresso = new ProgressDialog( getContext() );
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/cadastro_VistoriaCarretaSider.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoChaveRodas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campocaboAco.setText("");
                    campoqtsChaves.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoCatracasSoltas.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
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
                progresso.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {
                String PLACA = placa.getText().toString();
                String nome = camponome.getText().toString();
                String data = textViewDate.getText().toString();
                String ChaveRodas = campoChaveRodas.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String caboAco = campocaboAco.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Cantoneira = campoCantoneira.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String catracaSolta = campoCatracasSoltas.getText().toString();
                String LampadasLaterais = checkLateral2.getText().toString();
                String LampadasTraseiras = chkLampadaT2.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String eixoDireitoUm = P_eixo.getSelectedItem().toString();
                String eixoDireitoDois = S_eixo.getSelectedItem().toString();
                String eixoDireitoTres = T_eixo.getSelectedItem().toString();
                String eixoEsquerdoUm = leftP_eixo.getSelectedItem().toString();
                String eixoEsquerdoDois = leftS_eixo.getSelectedItem().toString();
                String eixoEsquerdoTres = leftT_eixo.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
                params.put( "ChaveRodas", ChaveRodas );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "caboAco", caboAco );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "Cantoneira", Cantoneira );
                params.put( "Estepe", Estepe );
                params.put( "catracaSolta", catracaSolta );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "LampadasLaterais", LampadasLaterais );
                params.put( "LampadasTraseiras", LampadasTraseiras );
                params.put( "eixoDireitoUm", eixoDireitoUm );
                params.put( "eixoDireitoDois", eixoDireitoDois );
                params.put( "eixoDireitoTres", eixoDireitoTres );
                params.put( "eixoEsquerdoUm", eixoEsquerdoUm );
                params.put( "eixoEsquerdoDois", eixoEsquerdoDois );
                params.put( "eixoEsquerdoTres", eixoEsquerdoTres );

                return params;
            }

        };
        MySingleton.getInstance( getContext() ).addToRequestQueue( stringRequest );
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta Sider ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Chave de Rodas: "+
                campoChaveRodas.getText().toString()+", Cordas: "+ campoCordas.getText().toString()+
                ", Cinta: "+ campoCinta.getText().toString()+ ", Catracas: "+ campoCatracas.getText().toString()+
                ", Cabo de Aço: "+ campocaboAco.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+", Cantoneira: "+ campoCantoneira.getText().toString()+
                ", Estepe: "+campoEstepe.getText().toString()+", Catraca Solta: "+campoCatracasSoltas.getText().toString()+
                ", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+
                ", Lâmpadas Laterais Funcionando: "+checkLateral2.getText().toString()+
                ", Lâmpadas Traseira Funcionando: "+chkLampadaT2.getText().toString() +
                ", 1º Eixo Lado Direito: "+P_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Direito: "+S_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Direito: "+T_eixo.getSelectedItem().toString()+
                ", 1º Eixo Lado Esquerdo: "+leftP_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Esquerdo: "+leftS_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Esquerdo: "+leftT_eixo.getSelectedItem().toString());
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
    }
    private void carregarWebServiceAlternateB () {
        progresso = new ProgressDialog( getContext() );
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/cadastro_VistoriaCarretaSider.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoChaveRodas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campocaboAco.setText("");
                    campoqtsChaves.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoCatracasSoltas.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
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
                progresso.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {
                String PLACA = placa.getText().toString();
                String nome = camponome.getText().toString();
                String data = textViewDate.getText().toString();
                String ChaveRodas = campoChaveRodas.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String caboAco = campocaboAco.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Cantoneira = campoCantoneira.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String catracaSolta = campoCatracasSoltas.getText().toString();
                String LampadasLaterais = checkLateral.getText().toString();
                String LampadasTraseiras = chkLampadaT2.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String eixoDireitoUm = P_eixo.getSelectedItem().toString();
                String eixoDireitoDois = S_eixo.getSelectedItem().toString();
                String eixoDireitoTres = T_eixo.getSelectedItem().toString();
                String eixoEsquerdoUm = leftP_eixo.getSelectedItem().toString();
                String eixoEsquerdoDois = leftS_eixo.getSelectedItem().toString();
                String eixoEsquerdoTres = leftT_eixo.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
                params.put( "ChaveRodas", ChaveRodas );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "caboAco", caboAco );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "Cantoneira", Cantoneira );
                params.put( "Estepe", Estepe );
                params.put( "catracaSolta", catracaSolta );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "LampadasLaterais", LampadasLaterais );
                params.put( "LampadasTraseiras", LampadasTraseiras );
                params.put( "eixoDireitoUm", eixoDireitoUm );
                params.put( "eixoDireitoDois", eixoDireitoDois );
                params.put( "eixoDireitoTres", eixoDireitoTres );
                params.put( "eixoEsquerdoUm", eixoEsquerdoUm );
                params.put( "eixoEsquerdoDois", eixoEsquerdoDois );
                params.put( "eixoEsquerdoTres", eixoEsquerdoTres );

                return params;
            }

        };
        MySingleton.getInstance( getContext() ).addToRequestQueue( stringRequest );
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta Sider ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Chave de Rodas: "+
                campoChaveRodas.getText().toString()+", Cordas: "+ campoCordas.getText().toString()+
                ", Cinta: "+ campoCinta.getText().toString()+ ", Catracas: "+ campoCatracas.getText().toString()+
                ", Cabo de Aço: "+ campocaboAco.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+", Cantoneira: "+ campoCantoneira.getText().toString()+
                ", Estepe: "+campoEstepe.getText().toString()+", Catraca Solta: "+campoCatracasSoltas.getText().toString()+
                ", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+
                ", Lâmpadas Laterais Funcionando: "+checkLateral.getText().toString()+
                ", Lâmpadas Traseira Funcionando: "+chkLampadaT2.getText().toString() +
                ", 1º Eixo Lado Direito: "+P_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Direito: "+S_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Direito: "+T_eixo.getSelectedItem().toString()+
                ", 1º Eixo Lado Esquerdo: "+leftP_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Esquerdo: "+leftS_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Esquerdo: "+leftT_eixo.getSelectedItem().toString());
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
    }
    private void carregarWebServiceAlternateC () {
        progresso = new ProgressDialog( getContext() );
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/cadastro_VistoriaCarretaSider.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoChaveRodas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campocaboAco.setText("");
                    campoqtsChaves.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoCatracasSoltas.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
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
                progresso.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {
                String PLACA = placa.getText().toString();
                String nome = camponome.getText().toString();
                String data = textViewDate.getText().toString();
                String ChaveRodas = campoChaveRodas.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String caboAco = campocaboAco.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Cantoneira = campoCantoneira.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String catracaSolta = campoCatracasSoltas.getText().toString();
                String LampadasLaterais = checkLateral2.getText().toString();
                String LampadasTraseiras = chkLampadaT.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String eixoDireitoUm = P_eixo.getSelectedItem().toString();
                String eixoDireitoDois = S_eixo.getSelectedItem().toString();
                String eixoDireitoTres = T_eixo.getSelectedItem().toString();
                String eixoEsquerdoUm = leftP_eixo.getSelectedItem().toString();
                String eixoEsquerdoDois = leftS_eixo.getSelectedItem().toString();
                String eixoEsquerdoTres = leftT_eixo.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
                params.put( "ChaveRodas", ChaveRodas );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "caboAco", caboAco );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "Cantoneira", Cantoneira );
                params.put( "Estepe", Estepe );
                params.put( "catracaSolta", catracaSolta );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "LampadasLaterais", LampadasLaterais );
                params.put( "LampadasTraseiras", LampadasTraseiras );
                params.put( "eixoDireitoUm", eixoDireitoUm );
                params.put( "eixoDireitoDois", eixoDireitoDois );
                params.put( "eixoDireitoTres", eixoDireitoTres );
                params.put( "eixoEsquerdoUm", eixoEsquerdoUm );
                params.put( "eixoEsquerdoDois", eixoEsquerdoDois );
                params.put( "eixoEsquerdoTres", eixoEsquerdoTres );

                return params;
            }

        };
        MySingleton.getInstance( getContext() ).addToRequestQueue( stringRequest );
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta Sider ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Chave de Rodas: "+
                campoChaveRodas.getText().toString()+", Cordas: "+ campoCordas.getText().toString()+
                ", Cinta: "+ campoCinta.getText().toString()+ ", Catracas: "+ campoCatracas.getText().toString()+
                ", Cabo de Aço: "+ campocaboAco.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+", Cantoneira: "+ campoCantoneira.getText().toString()+
                ", Estepe: "+campoEstepe.getText().toString()+", Catraca Solta: "+campoCatracasSoltas.getText().toString()+
                ", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+
                ", Lâmpadas Laterais Funcionando: "+checkLateral2.getText().toString()+
                ", Lâmpadas Traseira Funcionando: "+chkLampadaT.getText().toString() +
                ", 1º Eixo Lado Direito: "+P_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Direito: "+S_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Direito: "+T_eixo.getSelectedItem().toString()+
                ", 1º Eixo Lado Esquerdo: "+leftP_eixo.getSelectedItem().toString()+
                ", 2º Eixo Lado Esquerdo: "+leftS_eixo.getSelectedItem().toString()+
                ", 3º Eixo Lado Esquerdo: "+leftT_eixo.getSelectedItem().toString());
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
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
        void onFragmentInteraction ( Uri uri );
    }
}