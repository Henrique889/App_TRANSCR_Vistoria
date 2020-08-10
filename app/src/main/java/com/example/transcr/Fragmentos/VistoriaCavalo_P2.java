package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistoriaCavalo_P2 extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaVeiculo;
    TextView viewChaveRodas, viewTacografo,
            viewTapetes, viewCordas, viewCinta, viewqtsChaves, viewLanterna, viewKitG, viewKitP,viewInterclima;
    EditText campoChaveRodas, campoTacografo, campoTapetes,
            campoCordas, campoCinta, campoqtsChaves, campoLanterna, campoKitG, campoKitP,campoInterclima;
    private Spinner spInterclima,spLampadasD,spLampadasL,spLampadasT, spInterior;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;
    TextView textViewDate;

    public VistoriaCavalo_P2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollfichacavalo_p2);

        placaVeiculo = findViewById(R.id.placa );
        btn = findViewById(R.id.button2);
        btnAdicionar = findViewById(R.id.btnAdicione);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = findViewById(R.id.txt_date1);
        textViewDate.setText(currentDate);

        campoChaveRodas = findViewById( R.id.campoChaveRodas);
        campoTacografo = findViewById( R.id.campoTacografo);
        campoTapetes = findViewById( R.id.campoTapetes);
        campoCordas = findViewById( R.id.campoCordas );
        campoCinta = findViewById( R.id.campoCinta);
        campoqtsChaves = findViewById( R.id.campoQtsChaves);
        campoLanterna = findViewById( R.id.campoLanterna);
        campoKitG = findViewById( R.id.campoKitGrande);
        campoKitP = findViewById( R.id.campoKitPequeno);
        campoInterclima = findViewById( R.id.campoInterclima );

        viewChaveRodas = findViewById( R.id.viewChavesRoda);
        viewTacografo = findViewById( R.id.viewTacografo);
        viewTapetes = findViewById( R.id.viewTapetes);
        viewCordas = findViewById( R.id.viewCordas);
        viewCinta = findViewById( R.id.viewCinta);
        viewqtsChaves = findViewById( R.id.viewQtsChave);
        viewLanterna = findViewById( R.id.viewLanterna);
        viewKitG = findViewById( R.id.viewKitG);
        viewKitP = findViewById( R.id.viewKitP);
        viewInterclima = findViewById( R.id.viewInterclima);

        spInterclima = findViewById( R.id.spInterclima);
        spLampadasD = findViewById( R.id.spLampaD);
        spLampadasL = findViewById( R.id.spLampaL);
        spLampadasT = findViewById( R.id.spLampaT);

        List<String> cat = new ArrayList<>();
        cat.add( 0,"Toque aqui para escolher" );
        cat.add("Sim");
        cat.add("Não");

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,cat);
        //Dropdown layout style
        dataAdapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasD.setAdapter(dataAdapter);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter1;
        dataAdapter1 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,cat);
        //Dropdown layout style
        dataAdapter1.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasL.setAdapter(dataAdapter1);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,cat);
        //Dropdown layout style
        dataAdapter2.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasT.setAdapter(dataAdapter2);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter4;
        dataAdapter4 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,cat);
        //Dropdown layout style
        dataAdapter4.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spInterclima.setAdapter(dataAdapter4);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                carregarWEBService();
            }
        } );

        btnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                carregarWebService1();
            }
        } );

        Intent mIntent = getIntent();
        //Get intent from EscolhaVeiculo.class
        if (mIntent != null) {
            String placa = mIntent.getStringExtra(EXTRA_PLACA);
            placaVeiculo.setText( placa );
        }
    }
    private void carregarWEBService() {

        progresso = new ProgressDialog(this);
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

                    tabFicha.setChaveRodas( jsonObject.optString("ChavesRodas"));
                    tabFicha.setTacografo( jsonObject.optString("Tacografo"));
                    tabFicha.setTapetes( jsonObject.optString("tapetes"));
                    tabFicha.setCordas( jsonObject.optString("cordas"));
                    tabFicha.setCinta( jsonObject.optString("cinta"));
                    tabFicha.setqtsChaves( jsonObject.optString("qtsChaves"));
                    tabFicha.setLanterna( jsonObject.optString("lanterna"));
                    tabFicha.setSuporteG( jsonObject.optString("KitEmergenciaG"));
                    tabFicha.setSuporteP( jsonObject.optString("KitEmergenciaP"));
                    tabFicha.setInterclima( jsonObject.optString("Interclima"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                viewChaveRodas.setText(tabFicha.getChaveRodas());
                viewTacografo.setText(tabFicha.getTacografo());
                viewTapetes.setText(tabFicha.getTapetes());
                viewCordas.setText(tabFicha.getCordas());
                viewCinta.setText(tabFicha.getCinta());
                viewqtsChaves.setText(tabFicha.getqtsChaves());
                viewLanterna.setText(tabFicha.getLanterna());
                viewKitG.setText(tabFicha.getSuporteG());
                viewKitP.setText(tabFicha.getSuporteP());
                viewInterclima.setText(tabFicha.getInterclima());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                progresso.hide();
                Toast.makeText(getApplicationContext(), "Não foi possível mostrar os dados do banco de dados " +error.toString() , Toast.LENGTH_SHORT).show();
                Log.i("ERROR", error.toString());
            }
        } );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq);
    }

    private void carregarWebService1 () {
        progresso = new ProgressDialog( this );
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip + "/webservice/cadastro_VistoriaCavalo.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {

                    campoChaveRodas.setText("");
                    campoTacografo.setText("");
                    campoTapetes.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoqtsChaves.setText("");
                    campoLanterna.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");
                    campoInterclima.setText("");
                    Toast.makeText( getApplicationContext(), "Foi criado com sucesso", Toast.LENGTH_SHORT ).show();
                }else {
                    Toast.makeText( getApplicationContext(), "Ficha de Vistoria não inserida", Toast.LENGTH_SHORT ).show();
                    Log.i( "RESPOSTA: ", "" + response );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Toast.makeText( getApplicationContext(), "Erro ao inserir " + error, Toast.LENGTH_SHORT ).show();
                Log.i( "RESPOSTA: ", "" + error );
                progresso.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams () throws AuthFailureError {
                Bundle bundle = getIntent().getExtras();

                String PLACA = placaVeiculo.getText().toString();
                String nome = bundle.getString("One");
                String data = textViewDate.getText().toString();
                String extintor = bundle.getString("Two");
                String capaDeChuva = bundle.getString("Three");
                String Antena = bundle.getString("Four");
                String Radio = bundle.getString("Five");
                String Lona = bundle.getString("Six");
                String ColeteRefletivo = bundle.getString("Seven");
                String Xrefletivo = bundle.getString("Eight");
                String parDeluvas = bundle.getString("Nine");
                String capacete = bundle.getString("Ten");
                String GuardaChuva = bundle.getString("Eleven");
                String MarteloMadeira = bundle.getString("Twelve");
                String MarteloFerro = bundle.getString("Thirteen");
                String Documentos = bundle.getString("Fourteen");
                String Acendedor = bundle.getString("Fifteen");
                String CabosForca = bundle.getString("Sixteen");
                String macaco = bundle.getString("Seventeen");
                String ferroMacaco = bundle.getString("Eighteen");
                String triangulo = bundle.getString("Nineteen");
                String ChaveRodas = campoChaveRodas.getText().toString();
                String Tacografo = campoTacografo.getText().toString();
                String tapetes = campoTapetes.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String lanterna = campoLanterna.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String Interclima = campoInterclima.getText().toString();
                String InterclimaFuncionando = spInterclima.getSelectedItem().toString();
                String LampadasDianteiras = spLampadasD.getSelectedItem().toString();
                String LampadasLaterais = spLampadasL.getSelectedItem().toString();
                String LampadasTraseiras = spLampadasT.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
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
                params.put( "lanterna", lanterna );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "Interclima", Interclima );
                    params.put( "LampadasDianteiras", LampadasDianteiras );

                    params.put( "LampadasLaterais", LampadasLaterais );

                    params.put( "LampadasTraseiras", LampadasTraseiras );

                    params.put( "InterclimaFuncionando", InterclimaFuncionando );

                return params;
            }

        };
        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        Bundle bundle = getIntent().getExtras();
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria do Cavalo - Parte 1");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placaVeiculo.getText().toString()+", Nome: "+
                bundle.getString("One").toString()+", Data: "+ textViewDate.getText().toString()+", Extintor: "+
                bundle.getString("Two").toString()+", Capa de Chuva: "+ bundle.getString("Three").toString()+", Antena: "+
                bundle.getString("Four").toString()+ ", Radio: "+ bundle.getString("Five").toString()+
                ", Lona: "+ bundle.getString("Six").toString()+ ", Colete Refletivo: "+ bundle.getString("Seven").toString()+
                ", X Refletivo: "+ bundle.getString("Eight").toString()+", Par De Luvas: "+ bundle.getString("Nine").toString()+
                ", Capacete: "+ bundle.getString("Ten").toString()+ ", Guarda-Chuva: "+ bundle.getString("Eleven").toString()+
                ", Martelo de Madeira: "+ bundle.getString("Twelve").toString()+", Martelo de Ferro: "+bundle.getString("Thirteen").toString()+
                ", Documentos: "+ bundle.getString("Fourteen").toString()+ ", Acendedor: "+ bundle.getString("Fifteen").toString()+
                ", Cabo de Força: "+ bundle.getString("Sixteen").toString()+ ", Macaco: "+ bundle.getString("Seventeen").toString()+
                ", Ferro do Macaco: "+ bundle.getString("Eighteen").toString()+
                ", Triângulo: "+ bundle.getString("Nineteen").toString()+
                ", Chave de Rodas: "+campoChaveRodas.getText().toString()+ ", Tacógrafo: "+campoTacografo.getText().toString()+
                ", Tapetes: "+campoTapetes.getText().toString()+ ", Cordas: "+campoCordas.getText().toString()+
                ", Cinta: "+campoCinta.getText().toString()+", Qts de Chaves: "+campoqtsChaves.getText().toString()+
                ", Lanterna: "+campoLanterna.getText().toString()+", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+", Interclima: "+campoInterclima.getText().toString()+
                ", Interclima Funcionando: "+spInterclima.getSelectedItem().toString()+
                ", Lâmpadas Dianteiras Funcionando: "+spLampadasD.getSelectedItem().toString()+
                ", Lâmpadas Laterais Funcionando: "+spLampadasL.getSelectedItem().toString()+
                ", Lâmpadas Traseira Funcionando: "+spLampadasT.getSelectedItem().toString());
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}