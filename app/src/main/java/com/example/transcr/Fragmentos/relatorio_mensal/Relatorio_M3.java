package com.example.transcr.Fragmentos.relatorio_mensal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class Relatorio_M3 extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText txtFArSistema_Data, txtFArSistema__Km, txtFArSistema_Troca, txtOleoCambioData,txtOleoCambioKm,txtOleoCambioTroca,
            txtO_DiferencialData, txtO_DiferencialKm, txtO_DiferencialTroca,txtPneus_D, txtPneus_T,txtPneus_Estepe, txtCorreia_Data, txtCorreia_Km,txtCorreia_Troca, txtTacografo, txtRipas, txtAssoalho,
            txtOperador, txtCheckRastreio_data, txtRastreio_Status, txtManutencao_TrocaPecas;
    Button btnEnviar;

    ProgressDialog progresso;
    StringRequest stringRequest;
    private OnFragmentInteractionListener mListener;

    public Relatorio_M3() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static Relatorio_M1 newInstance(String param1, String param2) {
        Relatorio_M1 fragment = new Relatorio_M1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_relatorio_m3);
        
        txtFArSistema_Data = findViewById(R.id.txtDfiltro_ArSistema);
        txtFArSistema__Km = findViewById(R.id.txtFiltro_ArSKm);
        txtFArSistema_Troca = findViewById(R.id.txtFiltroArS_Troca);
        txtOleoCambioData = findViewById(R.id.txtDOleo_cambio);
        txtOleoCambioKm = findViewById(R.id.txtOleoCambio_km);
        txtOleoCambioTroca = findViewById(R.id.txtTrocaOleo_Cambio);
        txtO_DiferencialData = findViewById(R.id.txtDOleo_diferencial);
        txtO_DiferencialKm = findViewById(R.id.txtOleoDiferencial_km);
        txtO_DiferencialTroca = findViewById(R.id.txtOleoDTroca_km);
        txtPneus_D = findViewById(R.id.txtDPneus);
        txtPneus_T = findViewById(R.id.txtT_pneus);
        txtPneus_Estepe = findViewById(R.id.txtStep_pneus);
        txtCorreia_Data = findViewById(R.id.txtDCorreia);
        txtCorreia_Km = findViewById(R.id.txtCorreia_km);
        txtCorreia_Troca = findViewById(R.id.txtTrocaCorreia);
        txtTacografo = findViewById(R.id.txtTac);
        txtRipas = findViewById(R.id.txtRipas);
        txtAssoalho = findViewById(R.id.txtAssoalhoB);
        txtOperador = findViewById(R.id.txtOperador);
        txtCheckRastreio_data = findViewById(R.id.txtDataRas);
        txtRastreio_Status = findViewById(R.id.txtRastStatus);
        txtManutencao_TrocaPecas = findViewById(R.id.txtmanutencao);
        btnEnviar = findViewById(R.id.btnP);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWebService();
            }
        });
    }

    private void carregarWebService(){
        progresso = new ProgressDialog(this);
        progresso.setMessage( "Carregando..." );
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/relatorioMensal.php?";

        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {
            @Override
            public void onResponse ( String response ) {
                progresso.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtFArSistema_Data.setText("");
                    txtFArSistema__Km.setText("");
                    txtFArSistema_Troca.setText("");
                    txtOleoCambioData.setText("");
                    txtOleoCambioKm.setText("");
                    txtOleoCambioTroca.setText("");
                    txtO_DiferencialData.setText("");
                    txtO_DiferencialKm.setText("");
                    txtO_DiferencialTroca.setText("");
                    txtPneus_D.setText("");
                    txtPneus_T.setText("");
                    txtPneus_Estepe.setText("");
                    txtCorreia_Data.setText("");
                    txtCorreia_Km.setText("");
                    txtCorreia_Troca.setText("");
                    txtTacografo.setText("");
                    txtRipas.setText("");
                    txtAssoalho.setText("");
                    txtOperador.setText("");
                    txtCheckRastreio_data.setText("");
                    txtRastreio_Status.setText("");
                    txtManutencao_TrocaPecas.setText("");

                    Toast.makeText( getApplicationContext(), "Registrado com sucesso", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( getApplicationContext(), "Registro não inserido", Toast.LENGTH_SHORT ).show();
                    Log.i( "RESPOSTA: ", "" + response );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Toast.makeText( getApplicationContext(), "Erro ao registrar " + error, Toast.LENGTH_SHORT ).show();
                Log.i( "RESPOSTA: ", "" + error );
                progresso.hide();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Bundle bundle = getIntent().getExtras();


                String PLACA = bundle.getString("One");
                String data = bundle.getString("Two");
                String nome_Motorista = bundle.getString("Three");
                String km_principal = bundle.getString("Four");
                String alinhamento_data = bundle.getString("Five");
                String alinhamento_km = bundle.getString("Six");
                String alinhamento_status = bundle.getString("Seven");
                String alinhamento_rodizio = bundle.getString("Eight");
                String extintor_data = bundle.getString("Nine");
                String extintor_troca = bundle.getString("Ten");
                String palletera = bundle.getString("Eleven");
                String palleteraData = bundle.getString("Twelve");
                String paletas_data = bundle.getString("Thirteen");
                String paletas_km = bundle.getString("Fourteen");
                String paletas_troca = bundle.getString("Fifteen");
                String oleoM_data = bundle.getString("Sixteen");
                String oleoM_troca = bundle.getString("Seventeen");
                String oleoM_km = bundle.getString("Eighteen");
                String FiltrooleoM_data = bundle.getString("Nineteen");
                String FiltrooleoM_km = bundle.getString("Twenty");
                String FiltrooleoM_troca = bundle.getString("TwentyOne");
                String FiltroCombustivel_data = bundle.getString("TwentyTwo");
                String FiltroCombustivel_km = bundle.getString("TwentyThree");
                String FiltroCombustivel_troca = bundle.getString("TwentyFour");
                String FiltroAr_data = bundle.getString("TwentyFive");
                String FiltroAr_km = bundle.getString("TwentySix");
                String FiltroAr_troca = bundle.getString("TwentySeven");
                String FiltroArS_D = txtFArSistema_Data.getText().toString();
                String FiltroArS_km = txtFArSistema__Km.getText().toString();
                String FiltroArS_troca = txtFArSistema_Troca.getText().toString();
                String OleoC_Data = txtOleoCambioData.getText().toString();
                String OleoC_km = txtOleoCambioKm.getText().toString();
                String OleoC_troca = txtOleoCambioTroca.getText().toString();
                String OleoD_Data = txtO_DiferencialData.getText().toString();
                String OleoD_km = txtO_DiferencialKm.getText().toString();
                String OleoD_troca = txtO_DiferencialTroca.getText().toString();
                String Pneus_D = txtPneus_D.getText().toString();
                String Pneus_T = txtPneus_T.getText().toString();
                String Pneus_E = txtPneus_Estepe.getText().toString();
                String Correia_Data = txtCorreia_Data.getText().toString();
                String Correia_km = txtCorreia_Km.getText().toString();
                String Correia_troca = txtCorreia_Troca.getText().toString();
                String tacografo = txtTacografo.getText().toString();
                String ripas = txtRipas.getText().toString();
                String assoalho = txtAssoalho.getText().toString();
                String operador = txtOperador.getText().toString();
                String rastreioData = txtCheckRastreio_data.getText().toString();
                String statusRastreio = txtRastreio_Status.getText().toString();
                String manutencao = txtManutencao_TrocaPecas.getText().toString();

                Map <String, String> parametros = new HashMap<>();
                parametros.put("PlacaV",PLACA);
                parametros.put("Data",data);
                parametros.put("Nome",nome_Motorista);
                parametros.put("KM",km_principal);
                parametros.put("AlinhamentoD",alinhamento_data);
                parametros.put("AlinhamentoKM",alinhamento_km);
                parametros.put("AlinhamentoS",alinhamento_status);
                parametros.put("AlinhamentoR",alinhamento_rodizio);
                parametros.put("ExtintorD",extintor_data);
                parametros.put("ExtintorT",extintor_troca);
                parametros.put("Palletera",palletera);
                parametros.put("PalleteraD",palleteraData);
                parametros.put("PaletasD",paletas_data);
                parametros.put("PaletasKM",paletas_km);
                parametros.put("PaletasT",paletas_troca);
                parametros.put("OleoD",oleoM_data);
                parametros.put("OleoKm",oleoM_km);
                parametros.put("OleoTroca",oleoM_troca);
                parametros.put("FiltroM_D",FiltrooleoM_data);
                parametros.put("FiltroM_KM",FiltrooleoM_km);
                parametros.put("FiltroM_Troca",FiltrooleoM_troca);
                parametros.put("FiltroC_Data",FiltroCombustivel_data);
                parametros.put("FiltroC_km",FiltroCombustivel_km);
                parametros.put("FiltroC_troca",FiltroCombustivel_troca);
                parametros.put("FiltroArData",FiltroAr_data);
                parametros.put("FiltroArKm",FiltroAr_km);
                parametros.put("FiltroArTroca",FiltroAr_troca);
                parametros.put("FiltroArSData",FiltroArS_D);
                parametros.put("FiltroArSKm",FiltroArS_km);
                parametros.put("FiltroArSTroca",FiltroArS_troca);
                parametros.put("OleoCombustivel_D",OleoC_Data);
                parametros.put("OleoCombustivel_KM",OleoC_km);
                parametros.put("OleoCombustivel_T",OleoC_troca);
                parametros.put("OleoDiferencialD",OleoD_Data);
                parametros.put("OleoDiferencialKM",OleoD_km);
                parametros.put("OleoDiferencialT",OleoD_troca);
                parametros.put("PneusD",Pneus_D);
                parametros.put("PneusT",Pneus_T);
                parametros.put("PneusE",Pneus_E);
                parametros.put("Correia_D",Correia_Data);
                parametros.put("Correia_KM",Correia_km);
                parametros.put("Correia_T",Correia_troca);
                parametros.put("Tacografo",tacografo);
                parametros.put("Ripas",ripas);
                parametros.put("Assoalho",assoalho);
                parametros.put("Operador",operador);
                parametros.put("Ras_Data",rastreioData);
                parametros.put("Ras_Status",statusRastreio);
                parametros.put("Manu_Pecas",manutencao);



                return parametros;
            }

        };
        //requestQueue.add( stringRequest );
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        Bundle bundle = getIntent().getExtras();

        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Relatório Mensal do Caminhão / Sprinter");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+ bundle.getString("One").toString()+", Nome: "+
                bundle.getString("Three").toString()+", Data: "+ bundle.getString("Two").toString()+", KM PRINCIPAL: "+
                bundle.getString("Four").toString()+", Alinhamento/Balanceamento - "+ ", Data: "+ bundle.getString("Five").toString() +", Km: "+
                bundle.getString("Six").toString() + ", Status: "+ bundle.getString("Seven").toString()+
                ", Rodizio pneus: "+ bundle.getString("Eight").toString()+", Extintor - "+
                ", Trocado em Data: "+ bundle.getString("Nine").toString()+", Próxima troca em Data: "+ bundle.getString("Ten").toString()+
                ", Palletera: "+ bundle.getString("Eleven").toString() + ", Data da Revisão : "+ bundle.getString("Twelve").toString()+
                ", Paletas Limpador de Parabrisa - "+", Trocado em data: "+ bundle.getString("Thirteen").toString()+
                ", km: "+  bundle.getString("Fourteen").toString()+ ", Próxima troca Ano: "+ bundle.getString("Fifteen").toString()+
                ", Óleo Motor - "+", Troca em Data: "+ bundle.getString("Sixteen").toString()+", km: "+ bundle.getString("Eighteen").toString()+
                ", Próxima troca km: "+  bundle.getString("Seventeen").toString()+
                ", Filtro de Óleo - "+", Trocado em Data: "+ bundle.getString("Nineteen").toString()+", km: "+ bundle.getString("Twenty").toString()+
                ", Próxima troca km: "+ bundle.getString("TwentyOne").toString()+
                ", Filtro de Combustível - "+", Trocado em Data: "+ bundle.getString("TwentyTwo").toString()+", km: "+ bundle.getString("TwentyThree").toString()+
                ", Próxima troca km: "+ bundle.getString("TwentyFour").toString()+ ", Filtro de AR - "+", Trocado em Data: "+ bundle.getString("TwentyFive").toString()+
                ", km: "+ bundle.getString("TwentySix").toString()+ ", Próxima troca km: "+ bundle.getString("TwentySeven").toString()+
                ", Filtro de AR do Sistema - "+", Trocado em Data: "+ txtFArSistema_Data.getText().toString() +
                ", km: "+ txtFArSistema__Km.getText().toString()+ ", Próxima troca km: "+ txtFArSistema_Troca.getText().toString()+
                ", Óleo de Câmbio - "+", Trocado em Data: "+ txtOleoCambioData.getText().toString() +
                ", km: "+ txtOleoCambioKm.getText().toString()+ ", Próxima troca km: "+ txtOleoCambioTroca.getText().toString()+
                ", Óleo de Diferencial - "+", Trocado em Data: "+ txtO_DiferencialData.getText().toString() +
                ", km: "+ txtO_DiferencialKm.getText().toString()+ ", Próxima troca km: "+ txtO_DiferencialTroca.getText().toString()+
                ", Pneus (incluindo Estepe) - "+", Dianteiro: "+ txtPneus_D.getText().toString() +
                ", Traseiro: "+ txtPneus_T.getText().toString()+ ", Estepe: "+ txtPneus_Estepe.getText().toString()+
                ", Correia do Alternador - "+", Trocado em Data: "+ txtCorreia_Data.getText().toString() +
                ", km: "+ txtCorreia_Km.getText().toString()+ ", Próxima troca km: "+ txtCorreia_Troca.getText().toString()+
                ", Tacógrafo: " + txtTacografo.getText().toString() +", Ripas Baú: " + txtRipas.getText().toString() +
                ", Assoalho Baú: " + txtAssoalho.getText().toString() + ", CHECKLIST - RASTREADOR - "+", OPERADOR: " + txtAssoalho.getText().toString() +
                ", DATA: " + txtCheckRastreio_data.getText().toString() +", STATUS: " + txtRastreio_Status.getText().toString() +
                ", Manutenção e trocas de peças: " + txtManutencao_TrocaPecas.getText().toString());

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
        void onFragmentInteraction ( Uri uri );
    }
}
