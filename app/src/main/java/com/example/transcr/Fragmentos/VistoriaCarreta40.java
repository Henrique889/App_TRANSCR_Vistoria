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

public class VistoriaCarreta40 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_PLACA = "placaCarreta" ;
    EditText placa;
    CheckBox checkLateral, checkLateral2, chkLampadaT,chkLampadaT2;
    TextView viewFerramentas, viewCordas, viewCinta, viewqtsChaves, viewDocumentos, viewLona, viewCatracas,
             viewCatracasSoltas,viewEstepe, viewKitG, viewKitP, viewCantoneira,viewBoloCordas;
    EditText camponome,campoFerramentas,campoCordas, campoCinta, campoqtsChaves, campoDocumentos,
            campoLona, campoCatracas, campoCatracasSoltas,campoEstepe, campoKitG, campoKitP, campoCantoneira, campoBoloCordas;

    Spinner P_eixo,S_eixo,T_eixo, leftP_eixo,leftS_eixo, leftT_eixo;

    Button btn, btnAdicionar;

    ProgressDialog progresso;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;
    TextView textViewDate;

    private AsyncHttpClient cliente;
    private Spinner spVistoria;

    private OnFragmentInteractionListener mListener;

    public VistoriaCarreta40 () {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static VistoriaCarreta40 newInstance( String param1, String param2) {
        VistoriaCarreta40 fragment = new VistoriaCarreta40();
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
        View vista = inflater.inflate( R.layout.scrollfichacarreta40, container, false);
        camponome = vista.findViewById(R.id.camponome);
        placa = vista.findViewById(R.id.placa );
        btn = vista.findViewById(R.id.button2);
        btnAdicionar = vista.findViewById(R.id.btnAdicione);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate = vista.findViewById(R.id.text_date1);
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

        campoKitG = vista.findViewById( R.id.campoKitG );
        campoDocumentos = vista.findViewById( R.id.campoDocumentos );
        campoFerramentas = vista.findViewById( R.id.campoFerramentas);
        campoCordas = vista.findViewById( R.id.campoCordas );
        campoCinta = vista.findViewById( R.id.campoCinta);
        campoqtsChaves = vista.findViewById( R.id.campoQtsChaves);
        campoCatracas = vista.findViewById( R.id.campoCatracas);
        campoLona = vista.findViewById( R.id.campoLona);
        campoEstepe = vista.findViewById( R.id.campoEstepe);
        campoCantoneira = vista.findViewById( R.id.campoCantoneira);
        campoBoloCordas = vista.findViewById( R.id.campoBoloCordas);
        campoKitP = vista.findViewById( R.id.campoKitP);
        campoCatracasSoltas = vista.findViewById( R.id.campoCatracasSoltas);

        viewLona = vista.findViewById( R.id.viewLona );
        viewDocumentos = vista.findViewById( R.id.viewDocumentos );
        viewFerramentas = vista.findViewById( R.id.viewFerramentas);
        viewCordas = vista.findViewById( R.id.viewCordas );
        viewCinta = vista.findViewById( R.id.viewCinta);
        viewqtsChaves = vista.findViewById( R.id.viewQtsChaves);
        viewCatracas = vista.findViewById( R.id.viewCatracas);
        viewKitP = vista.findViewById( R.id.viewkit_P);
        viewCatracasSoltas = vista.findViewById( R.id.viewCatracaS);
        viewEstepe = vista.findViewById( R.id.viewGChuva);
        viewCantoneira = vista.findViewById( R.id.viewCantoneira);
        viewBoloCordas = vista.findViewById( R.id.viewBoloCordas);
        viewKitG = vista.findViewById( R.id.viewkit_G );

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
        String url = ip+"/webservice/consultaCarreta40.php?PLACA=" + placa.getText().toString();
        url = url.replace( " ", "%20" );


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.dismiss();
                FichaDeCarretasVistoria tabFicha = new FichaDeCarretasVistoria();
                JSONArray jsonArray = response.optJSONArray( "fichaCarreta_40" );
                JSONObject jsonObject = null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFicha.setqtsFerramentas( jsonObject.optString( "qtsFerramentas" ) );
                    tabFicha.setLona( jsonObject.optString( "Lona" ) );
                    tabFicha.setCordas( jsonObject.optString( "Cordas" ) );
                    tabFicha.setCinta(jsonObject.optString( "Cinta" ) );
                    tabFicha.setCatraca( jsonObject.optString("Catracas"));
                    tabFicha.setCatracaSolta(jsonObject.optString( "CatracasSoltas" ) );
                    tabFicha.setBoloCordas(jsonObject.optString( "BoloCordas" ) );
                    tabFicha.setDocumentos( jsonObject.optString("Documentos"));
                    tabFicha.setqtsChaves( jsonObject.optString("qtsChaves"));
                    tabFicha.setEstepe( jsonObject.optString("Estepe"));
                    tabFicha.setCantoneiraFerro( jsonObject.optString("cantoneiraF"));
                    tabFicha.setSuporteP( jsonObject.optString("kit_p_emergencia"));
                    tabFicha.setSuporteG( jsonObject.optString("kit_g_emergencia"));

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //placaV.setText(tabFichaC.getPlacaV());
                viewFerramentas.setText(tabFicha.getQtsFerramentas());
                viewLona.setText(tabFicha.getLona());
                viewCordas.setText(tabFicha.getCordas());
                viewCinta.setText(tabFicha.getCinta());
                viewCatracas.setText(tabFicha.getCatraca());
                viewCatracasSoltas.setText(tabFicha.getcatracaSolta());
                viewKitP.setText(tabFicha.getSuporteP());
                viewBoloCordas.setText(tabFicha.getBoloCordas());
                viewDocumentos.setText(tabFicha.getDocumentos());
                viewqtsChaves.setText(tabFicha.getqtsChaves());
                viewKitG.setText(tabFicha.getSuporteG());
                viewCantoneira.setText(tabFicha.getCantoneiraFerro());
                viewEstepe.setText(tabFicha.getEstepe());


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
        String url = ip + "/webservice/cadastro_VistoriaCarreta_40.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoFerramentas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campoLona.setText("");
                    campoCatracasSoltas.setText("");
                    campoqtsChaves.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoBoloCordas.setText("");
                    campoKitG.setText("");

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
                String Ferramentas = campoFerramentas.getText().toString();
                String Lona = campoLona.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String CatracasS = campoCatracasSoltas.getText().toString();
                String BoloCordas = campoBoloCordas.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String CantoneiraFerro = campoCantoneira.getText().toString();
                String SuporteP = campoKitP.getText().toString();
                String SuporteG = campoKitG.getText().toString();
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
                params.put( "Ferramentas", Ferramentas );
                params.put( "Lona", Lona );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "CatracasSoltas", CatracasS );
                params.put( "BoloCordas", BoloCordas );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "Estepe", Estepe );
                params.put( "CantoneiraF", CantoneiraFerro );
                params.put( "kit_p_emergencia", SuporteP );
                params.put( "kit_g_emergencia", SuporteG );
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
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta 40 ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Ferramentas: "+
                campoFerramentas.getText().toString()+", Lona: "+ campoLona.getText().toString()+", Cordas: "+
                campoCordas.getText().toString()+ ", Cinta: "+ campoCinta.getText().toString()+
                ", Catracas: "+ campoCatracas.getText().toString()+", Catracas Soltas: "+ campoCatracasSoltas.getText().toString()+
                ", Bolo de Cordas: "+ campoBoloCordas.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+ ", Estepe: "+campoEstepe.getText().toString()+
                ", Cantoneira de Ferro: "+ campoCantoneira.getText().toString()+
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
        String url = ip + "/webservice/cadastro_VistoriaCarreta_40.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoFerramentas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campoLona.setText("");
                    campoCatracasSoltas.setText("");
                    campoqtsChaves.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoBoloCordas.setText("");
                    campoKitG.setText("");
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
                String Ferramentas = campoFerramentas.getText().toString();
                String Lona = campoLona.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String CatracasS = campoCatracasSoltas.getText().toString();
                String BoloCordas = campoBoloCordas.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String CantoneiraFerro = campoCantoneira.getText().toString();
                String SuporteP = campoKitP.getText().toString();
                String SuporteG = campoKitG.getText().toString();
                String LampadasLaterais = checkLateral2.getText().toString();
                String LampadasTraseiras = chkLampadaT2.getText().toString();
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
                params.put( "Ferramentas", Ferramentas );
                params.put( "Lona", Lona );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "CatracasSoltas", CatracasS );
                params.put( "BoloCordas", BoloCordas );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "CantoneiraF", CantoneiraFerro );
                params.put( "Estepe", Estepe );
                params.put( "kit_p_emergencia", SuporteP );
                params.put( "kit_g_emergencia", SuporteG );
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
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta 40 ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Ferramentas: "+
                campoFerramentas.getText().toString()+", Lona: "+ campoLona.getText().toString()+", Cordas: "+
                campoCordas.getText().toString()+ ", Cinta: "+ campoCinta.getText().toString()+
                ", Catracas: "+ campoCatracas.getText().toString()+", Catracas Soltas: "+ campoCatracasSoltas.getText().toString()+
                ", Bolo de Cordas: "+ campoBoloCordas.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+ ", Estepe: "+campoEstepe.getText().toString()+
                ", Cantoneira de Ferro: "+ campoCantoneira.getText().toString()+
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
        String url = ip + "/webservice/cadastro_VistoriaCarreta_40.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoFerramentas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campoLona.setText("");
                    campoCatracasSoltas.setText("");
                    campoqtsChaves.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoBoloCordas.setText("");
                    campoKitG.setText("");
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
                String Ferramentas = campoFerramentas.getText().toString();
                String Lona = campoLona.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String CatracasS = campoCatracasSoltas.getText().toString();
                String BoloCordas = campoBoloCordas.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String SuporteP = campoKitP.getText().toString();
                String CantoneiraFerro = campoCantoneira.getText().toString();
                String SuporteG = campoKitG.getText().toString();
                String LampadasLaterais = checkLateral.getText().toString();
                String LampadasTraseiras = chkLampadaT2.getText().toString();
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
                params.put( "Ferramentas", Ferramentas );
                params.put( "Lona", Lona );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "CatracasSoltas", CatracasS );
                params.put( "BoloCordas", BoloCordas );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "CantoneiraF", CantoneiraFerro );
                params.put( "Estepe", Estepe );
                params.put( "kit_p_emergencia", SuporteP );
                params.put( "kit_g_emergencia", SuporteG );
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
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta 40 ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Ferramentas: "+
                campoFerramentas.getText().toString()+", Lona: "+ campoLona.getText().toString()+", Cordas: "+
                campoCordas.getText().toString()+ ", Cinta: "+ campoCinta.getText().toString()+
                ", Catracas: "+ campoCatracas.getText().toString()+", Catracas Soltas: "+ campoCatracasSoltas.getText().toString()+
                ", Bolo de Cordas: "+ campoBoloCordas.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+ ", Estepe: "+campoEstepe.getText().toString()+
                ", Cantoneira de Ferro: "+ campoCantoneira.getText().toString()+
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
        String url = ip + "/webservice/cadastro_VistoriaCarreta_40.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progresso.dismiss();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    camponome.setText("");
                    campoDocumentos.setText("");
                    campoFerramentas.setText("");
                    campoCordas.setText("");
                    campoCinta.setText("");
                    campoCatracas.setText("");
                    campoLona.setText("");
                    campoCatracasSoltas.setText("");
                    campoqtsChaves.setText("");
                    campoKitP.setText("");
                    campoCantoneira.setText("");
                    campoEstepe.setText("");
                    campoBoloCordas.setText("");
                    campoKitG.setText("");
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
                String Ferramentas = campoFerramentas.getText().toString();
                String Lona = campoLona.getText().toString();
                String cordas = campoCordas.getText().toString();
                String cinta = campoCinta.getText().toString();
                String catracas = campoCatracas.getText().toString();
                String CatracasS = campoCatracasSoltas.getText().toString();
                String BoloCordas = campoBoloCordas.getText().toString();
                String Documentos = campoDocumentos.getText().toString();
                String qtsChaves = campoqtsChaves.getText().toString();
                String Estepe = campoEstepe.getText().toString();
                String SuporteP = campoKitP.getText().toString();
                String CantoneiraFerro = campoCantoneira.getText().toString();
                String SuporteG = campoKitG.getText().toString();
                String LampadasLaterais = checkLateral2.getText().toString();
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
                params.put( "Ferramentas", Ferramentas );
                params.put( "Lona", Lona );
                params.put( "cordas", cordas );
                params.put( "cinta", cinta );
                params.put( "catracas", catracas );
                params.put( "CatracasSoltas", CatracasS );
                params.put( "BoloCordas", BoloCordas );
                params.put( "Documentos", Documentos );
                params.put( "qtsChaves", qtsChaves );
                params.put( "CantoneiraF", CantoneiraFerro );
                params.put( "Estepe", Estepe );
                params.put( "kit_p_emergencia", SuporteP );
                params.put( "kit_g_emergencia", SuporteG );
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
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria da Carreta 40 ");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+", Nome: "+
                camponome.getText().toString()+", Data: "+ textViewDate.getText().toString()+", Ferramentas: "+
                campoFerramentas.getText().toString()+", Lona: "+ campoLona.getText().toString()+", Cordas: "+
                campoCordas.getText().toString()+ ", Cinta: "+ campoCinta.getText().toString()+
                ", Catracas: "+ campoCatracas.getText().toString()+", Catracas Soltas: "+ campoCatracasSoltas.getText().toString()+
                ", Bolo de Cordas: "+ campoBoloCordas.getText().toString()+", Documentos: "+ campoDocumentos.getText().toString()+
                ", Qts de Chaves: "+ campoqtsChaves.getText().toString()+ ", Estepe: "+campoEstepe.getText().toString()+
                ", Cantoneira de Ferro: "+ campoCantoneira.getText().toString()+
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