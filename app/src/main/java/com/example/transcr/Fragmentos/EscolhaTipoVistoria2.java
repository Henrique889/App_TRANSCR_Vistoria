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
import androidx.fragment.app.FragmentTransaction;

import com.example.transcr.R;

import java.util.ArrayList;
import java.util.List;

public class EscolhaTipoVistoria2 extends AppCompatActivity{

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
    public EscolhaTipoVistoria2 () {
        // Required empty public constructor
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lists_test2);
        Log.d(TAG, "onCreate: Starting.");

        spinner = findViewById( R.id.spinner1 );
        textView = findViewById( R.id.textView00 );

        textView.setVisibility(View.VISIBLE);
        spinner.setVisibility( View.VISIBLE);

        List<String> categorias = new ArrayList <>();
        categorias.add( 0,"Toque aqui para escolher" );
        categorias.add("Carreta Sider");
        categorias.add("Carreta");
        categorias.add("Carreta Bug 20");
        categorias.add("Carreta 40");

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
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    //on selecting a spinner item
                    String item = parent.getItemAtPosition( position ).toString();

                    //show selected spinner item
                    Toast.makeText( parent.getContext(), "Selecionado: " + item,Toast.LENGTH_SHORT ).show();
                    //anything else you want to do on item selection do here
                    if (parent.getItemAtPosition( position ).equals("Carreta Sider")) {
                        Fragment fragment = new VistoriaCarretaSider();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.check2, fragment);
                        transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }else{
                        Fragment fragment = new VistoriaCarretaSider();
                        getSupportFragmentManager().beginTransaction().hide(fragment);
                    }
                    if (parent.getItemAtPosition( position ).equals("Carreta")) {
                        Fragment fragment = new VistoriaCarreta();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.check2, fragment);
                        transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    if (parent.getItemAtPosition( position ).equals("Carreta Bug 20")) {
                        Fragment fragment = new VistoriaCarretaBug20();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.check2, fragment);
                        transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
                    }
                    if (parent.getItemAtPosition( position ).equals("Carreta 40")) {
                        Fragment fragment = new VistoriaCarreta40();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.check2, fragment);
                        transaction.commit();
                        textView.setVisibility(View.INVISIBLE);
                        spinner.setVisibility( View.INVISIBLE);
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
