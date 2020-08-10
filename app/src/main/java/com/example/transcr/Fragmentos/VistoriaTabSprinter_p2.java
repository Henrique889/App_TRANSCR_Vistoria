package com.example.transcr.Fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class VistoriaTabSprinter_p2 extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaVeiculo;
    TextView viewCarrinho, viewTapetes, viewCatracas, viewCatracasMovel, viewCordas, viewCinta, viewqtsChaves,viewCalotas,viewKitG, viewKitP;
    EditText campoCatracaM, campoTapetes, campoCatracas, campoCordas, campoCinta,campoCarrinho, campoqtsChaves, campoKitG, campoKitP, campoCalotas;
    CheckBox checkLD,checkLD2,checkLL,checkLL2,checkLT,checkLT2,checkInterior,checkInterior2;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;
    private Spinner spLampadasD,spLampadasL,spLampadasT, spInterior;
    private OnFragmentInteractionListener mListener;
    TextView textViewDate;

    public VistoriaTabSprinter_p2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollfichasprinter_p2);

        placaVeiculo = findViewById(R.id.placa3 );
        btn = findViewById(R.id.button2);
        btnAdicionar = findViewById(R.id.btnAdicione);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = findViewById(R.id.txt_date8);
        textViewDate.setText(currentDate);

        campoTapetes = findViewById( R.id.campoTapetes);
        campoCarrinho = findViewById( R.id.campoCarrinho);
        campoCalotas = findViewById( R.id.campoCalotas);
        campoCatracas = findViewById( R.id.campoCatracas);
        campoCordas = findViewById( R.id.campoCordas );
        campoCinta = findViewById( R.id.campoCinta);
        campoqtsChaves = findViewById( R.id.campoQtsChaves);
        campoKitG = findViewById( R.id.campoKitGrande);
        campoKitP = findViewById( R.id.campoKitPequeno);
        campoCatracaM = findViewById( R.id.campoCatracas_Mo);

        viewTapetes = findViewById( R.id.viewTapetes);
        viewCatracas = findViewById( R.id.viewCatracas);
        viewCordas = findViewById( R.id.viewCordas);
        viewCinta = findViewById( R.id.viewCinta);
        viewCalotas = findViewById( R.id.viewCalotas);
        viewqtsChaves = findViewById( R.id.viewQtsChave);
        viewKitG = findViewById( R.id.viewKitG);
        viewKitP = findViewById( R.id.viewKitP);
        viewCarrinho = findViewById( R.id.viewCarrinho);
        viewCatracasMovel = findViewById( R.id.viewCatracas_Mo);

        spLampadasD = findViewById( R.id.spLampD);
        spLampadasL = findViewById( R.id.spLampL);
        spLampadasT = findViewById( R.id.spLampT);
        spInterior = findViewById( R.id.spInteriorB);

        List<String> adt = new ArrayList<>();
        adt.add( 0,"Toque aqui para escolher" );
        adt.add("Sim");
        adt.add("Não");

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasD.setAdapter(dataAdapter);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter1;
        dataAdapter1 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter1.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasL.setAdapter(dataAdapter1);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter2.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spLampadasT.setAdapter(dataAdapter2);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter3;
        dataAdapter3 = new ArrayAdapter<>( getApplicationContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter3.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spInterior.setAdapter(dataAdapter3);

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
        String url = ip+"/webservice/consultaSprinter2.php?PLACA=" + placaVeiculo.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeVistoria tabFicha = new FichaDeVistoria();
                JSONArray jsonArray = response.optJSONArray( "ficha_sprinter" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFicha.setTapetes( jsonObject.optString("tapetes"));
                    tabFicha.setCatraca( jsonObject.optString("catracas"));
                    tabFicha.setcatracaMovel( jsonObject.optString("Catracas_Moveis"));
                    tabFicha.setCordas( jsonObject.optString("cordas"));
                    tabFicha.setCinta( jsonObject.optString("cinta"));
                    tabFicha.setCarrinho( jsonObject.optString("carrinhoHidraulico"));
                    tabFicha.setCalotas( jsonObject.optString("calotas"));
                    tabFicha.setqtsChaves( jsonObject.optString("qtsChaves"));
                    tabFicha.setSuporteG( jsonObject.optString("KitEmergenciaG"));
                    tabFicha.setSuporteP( jsonObject.optString("KitEmergenciaP"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                viewTapetes.setText(tabFicha.getTapetes());
                viewCatracas.setText(tabFicha.getCatraca());
                viewCatracasMovel.setText(tabFicha.getcatracaMovel());
                viewCordas.setText(tabFicha.getCordas());
                viewCinta.setText(tabFicha.getCinta());
                viewCarrinho.setText(tabFicha.getCarrinho());
                viewCalotas.setText(tabFicha.getCalotas());
                viewqtsChaves.setText(tabFicha.getqtsChaves());
                viewKitG.setText(tabFicha.getSuporteG());
                viewKitP.setText(tabFicha.getSuporteP());

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
        String url = ip + "/webservice/cadastro_VistoriaSprinterB.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {

                    campoTapetes.setText("");
                    campoCatracas.setText("");
                    campoCatracaM.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCarrinho.setText("");
                    campoCalotas.setText("");
                    campoqtsChaves.setText("");
                    campoKitG.setText("");
                    campoKitP.setText("");

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
                String ColeteRefletivo = bundle.getString("Four");
                String Xrefletivo = bundle.getString("Five");
                String parDeluvas = bundle.getString("Six");
                String capacete = bundle.getString("Seven");
                String GuardaChuva = bundle.getString("Eight");
                String MarteloMadeira = bundle.getString("Nine");
                String Documentos = bundle.getString("Ten");
                String Acendedor = bundle.getString("Eleven");
                String CabosForca = bundle.getString("Twelve");
                String macaco = bundle.getString("Thirteen");
                String ferroMacaco = bundle.getString("Fourteen");
                String triangulo = bundle.getString("Fifteen");
                String ChaveRodas = bundle.getString("Sixteen");
                String tapetes = campoTapetes.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String catracasMoveis = campoCatracaM.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String carrinho = campoCarrinho.getText().toString();
                String calotas = campoCalotas.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String LampadasDianteiras = spLampadasD.getSelectedItem().toString();
                String LampadasLaterais = spLampadasL.getSelectedItem().toString();
                String LampadasTraseiras = spLampadasT.getSelectedItem().toString();
                String Interior = spInterior.getSelectedItem().toString();

                Map <String, String> params = new HashMap <>();
                params.put( "placa", PLACA );
                params.put( "nome", nome );
                params.put( "data", data );
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
                params.put( "Catracas_Moveis", catracasMoveis );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "carrinho", carrinho );
                params.put( "calotas", calotas );
                params.put( "qtsChaves", qtsChaves );
                params.put( "KitEmergenciaP", KitEmergenciaP );
                params.put( "KitEmergenciaG", KitEmergenciaG );

                    params.put( "LampadasDianteiras", LampadasDianteiras );

                    params.put( "LampadasLaterais", LampadasLaterais );

                    params.put( "LampadasTraseiras", LampadasTraseiras );

                    params.put( "Interior", Interior );

                return params;
            }

        };
        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        Bundle bundle = getIntent().getExtras();
        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria do Caminhão / Sprinter - Parte 1");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placaVeiculo.getText().toString()+", Nome: "+
                bundle.getString("One").toString()+", Data: "+ textViewDate.getText().toString()+", Extintor: "+
                bundle.getString("Two").toString()+", Capa de Chuva: "+ bundle.getString("Three").toString()+
                ", Colete Refletivo: "+ bundle.getString("Four").toString()+
                ", X Refletivo: "+ bundle.getString("Five").toString()+", Par De Luvas: "+ bundle.getString("Six").toString()+
                ", Capacete: "+ bundle.getString("Seven").toString()+ ", Guarda-Chuva: "+ bundle.getString("Eight").toString()+
                ", Martelo de Madeira: "+ bundle.getString("Nine").toString()+ ", Documentos: "+
                bundle.getString("Ten").toString()+ ", Acendedor: "+ bundle.getString("Eleven").toString()+
                ", Cabo de Força: "+ bundle.getString("Twelve").toString()+ ", Macaco: "+
                bundle.getString("Thirteen").toString()+", Ferro do Macaco: "+ bundle.getString("Fourteen").toString()+
                ", Triângulo: "+ bundle.getString("Fifteen").toString()+ ", Chave de Rodas: "+bundle.getString("Sixteen").toString()+
                ", Tapetes: "+campoTapetes.getText().toString()+ ", Catracas: "+campoCatracas.getText().toString()+
                ", Catracas Móveis: "+campoCatracaM.getText().toString()+", Cordas: "+campoCordas.getText().toString()+
                ", Cinta: "+campoCinta.getText().toString()+", Qts de Chaves: "+campoqtsChaves.getText().toString()+
                ", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
                ", Kit Suporte de Emergência Grande: "+campoKitG.getText().toString()+
                ", Lâmpadas Dianteiras Funcionando: "+spLampadasD.getSelectedItem().toString()+
                ", Lâmpadas Laterais Funcionando: "+spLampadasL.getSelectedItem().toString()+
                ", Lâmpadas Traseira Funcionando: "+spLampadasT.getSelectedItem().toString()+
                ", Interior Baú/Sider Limpo: "+spInterior.getSelectedItem().toString());

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