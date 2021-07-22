package com.example.transcr;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.transcr.Fragmentos.ConsultaList_Carretas;
import com.example.transcr.Fragmentos.ConsultaList_Fragment;
import com.example.transcr.Fragmentos.EscolherCaminhao_Avarias;
import com.example.transcr.Fragmentos.EscolherCarretas_Avarias;
import com.example.transcr.Fragmentos.Parte2_CheckList_Caminhao;
import com.example.transcr.Interfaces.Ifragmentos;
import com.google.android.material.navigation.NavigationView;

public class EscolhaVeiculoUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Ifragmentos {
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

    public EscolhaVeiculoUser () {
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
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new Pagina_Inicial() ).commit();
            navigationView.setCheckedItem( R.id.nav_pag_inicio );
        }
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
            case R.id.nav_pag_inicio:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new Pagina_Inicial() ).commit();
                break;
            case R.id.nav_consulta_lista:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new ConsultaList_Fragment() ).commit();
                break;
            case R.id.nav_checklist2:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new Parte2_CheckList_Caminhao() ).commit();
                break;
            case R.id.nav_consultar_carretas_lista:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new ConsultaList_Carretas() ).commit();
                break;
            case R.id.nav_desenhar_avarias:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new EscolherCarretas_Avarias() ).commit();
                break;
            case R.id.nav_escolher_foto:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment, new EscolherCaminhao_Avarias() ).commit();
                break;

            case R.id.nav_registro:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
            break;
            case R.id.nav_carreta:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_consulta:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cadastro:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cadastro_carreta:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_lookImages:
                Toast.makeText(getApplicationContext(),"Não é permitido acessar esse conteúdo",Toast.LENGTH_SHORT).show();
                break;
            }

            drawer.closeDrawer( GravityCompat.START );
            return true;
        }
        @Override
        public void onFragmentInteraction ( Uri uri){

        }
}



