package com.example.transcr;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.transcr.Fragmentos.ConsultaList_Carretas;
import com.example.transcr.Fragmentos.ConsultaList_Fragment;
import com.example.transcr.Fragmentos.ConsultarFrota;
import com.example.transcr.Fragmentos.EscolherCaminhao_Avarias;
import com.example.transcr.Fragmentos.EscolherCarretas_Avarias;
import com.example.transcr.Fragmentos.Parte2_CheckList_Caminhao;
import com.example.transcr.Fragmentos.RegistrarCarretas;
import com.example.transcr.Fragmentos.RegistrarFrota;
import com.example.transcr.Fragmentos.TipoDeCarreta;
import com.example.transcr.Fragmentos.TipoDeVistoria;
import com.example.transcr.Interfaces.Ifragmentos;
import com.example.transcr.ShowAvariasImages.EscolhaTipoVeiculo_Carretas;

public class EscolhaVeiculo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Ifragmentos {
    //private ListView listView;
    //private CadastroDAO dao;
    //private List<Cadastro> cadastroList;
    //private List<Cadastro> cadastroFiltrado = new ArrayList<>();
    private DrawerLayout drawer;
    ImageView imgSemNet;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText txtUser;

    public EscolhaVeiculo () {
        // Required empty public constructor
    }

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.drawer_main );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        drawer = findViewById( R.id.drawer_layout );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new RegistrarFrota() ).commit();
            navigationView.setCheckedItem( R.id.nav_registro );
        }
        /*imgSemNet = findViewById( R.id.imgSemNet );
        //imgSemNet.setVisibility( View.INVISIBLE );
        ConnectivityManager con = (ConnectivityManager) this.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo netInfo = con.getActiveNetworkInfo();


        if (netInfo != null && netInfo.isConnected()) {
            imgSemNet.setVisibility( View.INVISIBLE );
            ;
        } else {
            imgSemNet.setVisibility( View.VISIBLE );
            Toast.makeText( this, "É preciso se conectar para receber informações do Aplicativo", Toast.LENGTH_LONG ).show();
        }*/


        /*listView = (ListView)findViewById(R.id.listview);
        dao = new CadastroDAO(EscolhaVeiculo.this);
        cadastroList = dao.MostrarAll();
        cadastroFiltrado.addAll(cadastroList);
        final ArrayAdapter <Cadastro> adapter = new ArrayAdapter <Cadastro>( this, android.R.layout.simple_list_item_1, cadastroFiltrado );
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick ( AdapterView <?> parent, View view, int position, long id ) {

                    Toast.makeText( getApplicationContext(), "Veículo Escolhido: " + cadastroFiltrado.get( position ).toString(), Toast.LENGTH_SHORT ).show();
                    Cadastro lm = adapter.getItem( position );
                    Intent it = new Intent( EscolhaVeiculo.this, CheckLists.class );
                    assert lm != null;
                    it.putExtra( "placaV", lm.getPlacVeiculo() );
                    startActivity( it );
            }
        } );*/
    }

    @Override
    public void onBackPressed () {
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem menuItem ) {
        switch (menuItem.getItemId()) {
            case R.id.nav_registro:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new RegistrarFrota() ).commit();
                break;
            case R.id.nav_carreta:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new RegistrarCarretas() ).commit();
                break;
            case R.id.nav_consulta_lista:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new ConsultaList_Fragment() ).commit();
                break;
            case R.id.nav_consulta:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new ConsultarFrota() ).commit();
                break;
            case R.id.nav_checklist2:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new Parte2_CheckList_Caminhao() ).commit();
                break;
            case R.id.nav_cadastro:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new TipoDeVistoria() ).commit();
                break;
            case R.id.nav_cadastro_carreta:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new TipoDeCarreta() ).commit();
                break;
            case R.id.nav_consultar_carretas_lista:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new ConsultaList_Carretas() ).commit();
                break;
            case R.id.nav_desenhar_avarias:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new EscolherCarretas_Avarias() ).commit();
                //startActivity(new Intent(getApplicationContext(), TouchDraw.class));
                break;
            case R.id.nav_escolher_foto:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new EscolherCaminhao_Avarias() ).commit();
                break;
            case R.id.nav_lookImages:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new EscolhaTipoVeiculo_Carretas() ).commit();
                break;


        }
            drawer.closeDrawer( GravityCompat.START );
            return true;
        }

        @Override
        public void onFragmentInteraction ( Uri uri){

        }
}



