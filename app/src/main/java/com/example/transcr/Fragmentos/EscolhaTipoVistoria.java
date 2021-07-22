package com.example.transcr.Fragmentos;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.transcr.R;

import java.util.ArrayList;
import java.util.List;

public class EscolhaTipoVistoria extends AppCompatActivity{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spinner;
    private TextView textView;

    private static final String TAG = "EscolhaTipoVistoria";

    private OnFragmentInteractionListener mListener;
    public EscolhaTipoVistoria() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lists_test1);
        Log.d(TAG, "onCreate: Starting.");

        spinner = findViewById( R.id.spinner1 );
        textView = findViewById( R.id.textView00 );

        textView.setVisibility(View.VISIBLE);
        spinner.setVisibility( View.VISIBLE);

        List<String> categorias = new ArrayList <>();
        categorias.add( 0,"Toque aqui para escolher" );
        categorias.add("Caminhão sem Interclima");
        categorias.add("Caminhão com Interclima");
        categorias.add("Sprinter com Ferramentas");
        categorias.add("Sprinter sem Ferramentas");
        categorias.add("Carroceria");
        categorias.add("Cavalo");

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter( this,android.R.layout.simple_spinner_item,categorias);
        //Dropdown layout style
        dataAdapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected ( AdapterView <?> parent, View view, int position, long id ) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher" )){
                    //do nothing
                }else{
                    //on selecting a spinner item
                    String item = parent.getItemAtPosition( position ).toString();

                    //show selected spinner item
                    Toast.makeText( parent.getContext(), "Selecionado: " + item,Toast.LENGTH_SHORT ).show();
                    //anything else you want to do on item selection do here
                    if (parent.getItemAtPosition( position ).equals("Caminhão sem Interclima")) {
                        Fragment fragment = new VistoriaTab_Caminhao_P1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }else{
                        Fragment fragment = new VistoriaTab_Caminhao_P1();
                        getSupportFragmentManager().beginTransaction().hide(fragment);
                    }
                    if (parent.getItemAtPosition( position ).equals("Caminhão com Interclima")) {
                        Fragment fragment0 = new VistoriaTab_CamInterclima_p1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment0).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment0);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    else {
                        Fragment fragment = new VistoriaTab_CamInterclima_p1();
                        getSupportFragmentManager().beginTransaction().hide( fragment );
                    }
                    if (parent.getItemAtPosition( position ).equals("Sprinter com Ferramentas")) {
                        Fragment fragment1 = new VistoriaSprinterMercedes_P1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment1).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment1);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    else {
                        Fragment fragment = new VistoriaSprinterMercedes_P1();
                        getSupportFragmentManager().beginTransaction().hide( fragment );
                    }
                    if (parent.getItemAtPosition( position ).equals("Sprinter sem Ferramentas")) {
                        Fragment fragment2 = new VistoriaTabSprinter_p1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment2).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment2);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    else {
                        Fragment fragment = new VistoriaTabSprinter_p1();
                        getSupportFragmentManager().beginTransaction().hide( fragment );
                    }
                    if (parent.getItemAtPosition( position ).equals("Carroceria")) {
                        Fragment fragment3 = new VistoriaCarroceria_P1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment3).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment3);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    else {
                        Fragment fragment = new VistoriaCarroceria_P1();
                        getSupportFragmentManager().beginTransaction().hide( fragment );
                    }
                    if (parent.getItemAtPosition( position ).equals("Cavalo")) {
                        Fragment fragment4 = new VistoriaCavalo_P1();
                        getSupportFragmentManager().beginTransaction().replace( R.id.check1, fragment4).commit();
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.check1, fragment4);
                        //transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    else {
                        Fragment fragment = new VistoriaCavalo_P1();
                        getSupportFragmentManager().beginTransaction().hide( fragment );
                    }
                }
            }

            @Override
            public void onNothingSelected ( AdapterView <?> parent ) {
                //TODO Auto-generated method stub

            }
        } );

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
