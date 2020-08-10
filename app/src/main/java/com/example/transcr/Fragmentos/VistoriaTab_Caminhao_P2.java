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

public class VistoriaTab_Caminhao_P2 extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaVeiculo" ;
    EditText placaV;
    TextView viewChaveRodas, viewTacografo, viewTapetes, viewcaboAco, viewCatracas, viewcatracaM,
            viewCordas, viewCinta, viewqtsChaves, viewLanterna, viewKitG, viewKitP;
    EditText campoChaveRodas, campoTacografo, campoTapetes, campocaboAco, campoCatracas,
            campocatracaM, campoCordas, campoCinta, campoqtsChaves, campoLanterna, campoKitG, campoKitP;
    CheckBox checkLD,checkLD2,checkLL,checkLL2,checkLT,checkLT2,checkInterior,checkInterior2;
    private Spinner spLampadasD,spLampadasL,spLampadasT, spInterior;

    TextView textViewDate;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public VistoriaTab_Caminhao_P2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollficha0_p2);

        placaV = findViewById(R.id.placa1 );
        btn = findViewById(R.id.button2);
        btnAdicionar = findViewById(R.id.btnAdicione);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = findViewById(R.id.txt_date2);
        textViewDate.setText(currentDate);

        campoChaveRodas = findViewById( R.id.campoChaveRodas);
        campoTacografo = findViewById( R.id.campoTacografo);
        campoTapetes = findViewById( R.id.campoTapetes);
        campocaboAco = findViewById( R.id.campoCaboAco);
        campoCatracas = findViewById( R.id.campoCatracas);
        campocatracaM = findViewById( R.id.campoCatracaM);
        campoCordas = findViewById( R.id.campoCordas );
        campoCinta = findViewById( R.id.campoCinta);
        campoqtsChaves = findViewById( R.id.campoQtsChaves);
        campoLanterna = findViewById( R.id.campoLanterna);
        campoKitG = findViewById( R.id.campoKitGrande);
        campoKitP = findViewById( R.id.campoKitPequeno);

        viewChaveRodas = findViewById( R.id.viewChavesRoda);
        viewTacografo = findViewById( R.id.viewTacografo);
        viewTapetes = findViewById( R.id.viewTapetes);
        viewcaboAco = findViewById( R.id.viewCaboAco);
        viewCatracas = findViewById( R.id.viewCatracas);
        viewcatracaM = findViewById( R.id.viewCatracaMovel);
        viewCordas = findViewById( R.id.viewCordas);
        viewCinta = findViewById( R.id.viewCinta);
        viewqtsChaves = findViewById( R.id.viewQtsChave);
        viewLanterna = findViewById( R.id.viewLanterna);
        viewKitG = findViewById( R.id.viewKitG);
        viewKitP = findViewById( R.id.viewKitP);

        spLampadasD = findViewById( R.id.spLDianteira);
        spLampadasL = findViewById( R.id.spLLaterais);
        spLampadasT = findViewById( R.id.spLTraseira);
        spInterior = findViewById( R.id.spInterior);

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
            placaV.setText( placa );
        }
    }

    private void carregarWEBService() {

        progresso = new ProgressDialog(this);
        progresso.setMessage("Buscando...");
        progresso.show();
        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultaCaM.php?PLACA=" + placaV.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeVistoria tabFichaC = new FichaDeVistoria();
                JSONArray jsonArray = response.optJSONArray( "fichaVistoria" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFichaC.setChaveRodas( jsonObject.optString("ChaveRodas"));
                    tabFichaC.setTacografo( jsonObject.optString("Tacografo"));
                    tabFichaC.setTapetes( jsonObject.optString("tapetes"));
                    tabFichaC.setcaboAco( jsonObject.optString("caboAco"));
                    tabFichaC.setCatraca( jsonObject.optString("catracas"));
                    tabFichaC.setcatracaMovel( jsonObject.optString("catracaMovel"));
                    tabFichaC.setCordas( jsonObject.optString("cordas"));
                    tabFichaC.setCinta( jsonObject.optString("cinta"));
                    tabFichaC.setqtsChaves( jsonObject.optString("qtsChaves"));
                    tabFichaC.setLanterna( jsonObject.optString("lanterna"));
                    tabFichaC.setSuporteG( jsonObject.optString("KitEmergenciaG"));
                    tabFichaC.setSuporteP( jsonObject.optString("KitEmergenciaP"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                viewChaveRodas.setText(tabFichaC.getChaveRodas());
                viewTacografo.setText(tabFichaC.getTacografo());
                viewTapetes.setText(tabFichaC.getTapetes());
                viewcaboAco.setText(tabFichaC.getcaboAco());
                viewCatracas.setText(tabFichaC.getCatraca());
                viewcatracaM.setText(tabFichaC.getcatracaMovel());
                viewCordas.setText(tabFichaC.getCordas());
                viewCinta.setText(tabFichaC.getCinta());
                viewqtsChaves.setText(tabFichaC.getqtsChaves());
                viewLanterna.setText(tabFichaC.getLanterna());
                viewKitG.setText(tabFichaC.getSuporteG());
                viewKitP.setText(tabFichaC.getSuporteP());

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
        String url = ip + "/webservice/cadastro_VistoriaA.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    campoChaveRodas.setText("");
                    campoTacografo.setText("");
                    campoTapetes.setText("");
                    campocaboAco.setText("");
                    campoCatracas.setText("");
                    campocatracaM.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoqtsChaves.setText("");
                    campoLanterna.setText("");
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

                String PLACA = placaV.getText().toString();
                String nome = bundle.getString("One");
                String data = textViewDate.getText().toString();
                String extintor =  bundle.getString("Two");
                String capaDeChuva =  bundle.getString("Three");
                String Antena =  bundle.getString("Four");
                String Radio =  bundle.getString("Five");
                String Lona =  bundle.getString("Six");
                String ColeteRefletivo =  bundle.getString("Seven");
                String Xrefletivo =  bundle.getString("Eight");
                String parDeluvas =  bundle.getString("Nine");
                String capacete =  bundle.getString("Ten");
                String GuardaChuva =  bundle.getString("Eleven");
                String MarteloMadeira =  bundle.getString("Twelve");
                String Documentos =  bundle.getString("Thirteen");
                String Acendedor =  bundle.getString("Fourteen");
                String CabosForca =  bundle.getString("Fifteen");
                String macaco =  bundle.getString("Sixteen");
                String ferroMacaco =  bundle.getString("Seventeen");
                String triangulo =  bundle.getString("Eighteen");
                String ChaveRodas = campoChaveRodas.getText().toString();
                String Tacografo = campoTacografo.getText().toString();
                String tapetes = campoTapetes.getText().toString();
                String caboAco = campocaboAco.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String catracaMovel = campocatracaM.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String lanterna = campoLanterna.getText().toString();
                String KitEmergenciaP = campoKitP.getText().toString();
                String KitEmergenciaG = campoKitG.getText().toString();
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
                params.put( "Antena", Antena );
                params.put( "Radio", Radio );
                params.put( "Lona", Lona );
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
                params.put( "Tacografo", Tacografo );
                params.put( "tapetes", tapetes );
                params.put( "caboAco", caboAco );
                params.put( "catracas", catracas );
                params.put( "catracaMovel", catracaMovel );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "qtsChaves", qtsChaves );
                params.put( "lanterna", lanterna );
                params.put( "KitEmergenciaG", KitEmergenciaG );
                params.put( "KitEmergenciaP", KitEmergenciaP );
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
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placaV.getText().toString()+", Nome: "+
                bundle.getString("One").toString()+", Data: "+ textViewDate.getText().toString()+", Extintor: "+
                bundle.getString("Two").toString()+", Capa de Chuva: "+ bundle.getString("Three").toString()+", Antena: "+
                bundle.getString("Four").toString()+ ", Radio: "+ bundle.getString("Five").toString()+
                ", Lona: "+ bundle.getString("Six").toString()+ ", Colete Refletivo: "+ bundle.getString("Seven").toString()+
                ", X Refletivo: "+ bundle.getString("Eight").toString()+", Par De Luvas: "+ bundle.getString("Nine").toString()+
                ", Capacete: "+ bundle.getString("Ten").toString()+ ", Guarda-Chuva: "+ bundle.getString("Eleven").toString()+
                ", Martelo de Madeira: "+ bundle.getString("Twelve").toString()+ ", Documentos: "+
                bundle.getString("Thirteen").toString()+ ", Acendedor: "+ bundle.getString("Fourteen").toString()+
                ", Cabo de Força: "+ bundle.getString("Fifteen").toString()+ ", Macaco: "+
                bundle.getString("Sixteen").toString()+", Ferro do Macaco: "+ bundle.getString("Seventeen").toString()+
                ", Triângulo: "+ bundle.getString("Eighteen").toString()+
                ", Chave de Rodas: "+campoChaveRodas.getText().toString()+
                ", Tacógrafo: "+campoTacografo.getText().toString()+", Tapetes: "+campoTapetes.getText().toString()+
                ", Cabo de Aço: "+campocaboAco.getText().toString()+", Catracas: "+campoCatracas.getText().toString()+
                ", Catracas Móveis: "+campocatracaM.getText().toString()+", Cordas: "+campoCordas.getText().toString()+
                ", Cinta: "+campoCinta.getText().toString()+", Qts de Chaves: "+campoqtsChaves.getText().toString()+
                ", Lanterna: "+campoLanterna.getText().toString()+", Kit Suporte de Emergência Pequeno: "+campoKitP.getText().toString()+
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