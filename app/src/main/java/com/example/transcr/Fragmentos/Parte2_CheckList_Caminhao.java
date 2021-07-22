package com.example.transcr.Fragmentos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.MySingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Parte2_CheckList_Caminhao extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spAguaRadiador, spPneusCalibrados, spPneusBatidos, spPneusRasgados, spPneusFurados, spEstepe,spOculos,spControle;
    private TextView textData;
    private EditText Placa, txtnome,edtPneusCalibrados, edtPneusBatidos, edtPneusRasgados, edtPneusFurados,edtPneusCalibrados2,
            edtAgua, edtPneusBatidos2, edtPneusRasgados2, edtPneusFurados2,edtEstepe;
    Button btnEnviar, btnPhoto;
    StringRequest stringRequest;
    private String path;
    ImageView imgFoto;
    File fileImagem;
    Bitmap bitmap;
    private RequestQueue requestQueue;
    private static final String TAG = "Parte2_CheckList_Caminhao";

    private OnFragmentInteractionListener mListener;

    private static final int COD_SELECIONA = 10;
    private static final int COD_FOTO = 20;
    private static final int COD_PERMISSAO = 100;

    private static final String PASTA_PRINCIPAL = "minhasImagensApp/";  //dir principal
    private static final String PASTA_IMAGEM = "imagens";  //PASTA ONDE FICARAM AS FOTOS
    private static final String DIRETORIO_IMAGEM = PASTA_PRINCIPAL + PASTA_IMAGEM;

    ProgressDialog progress;
    private static final String EXTRA_PLACA = "placaVeiculo" ;
    public Parte2_CheckList_Caminhao() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static Parte2_CheckList_Caminhao newInstance(String param1, String param2) {
        Parte2_CheckList_Caminhao fragment = new Parte2_CheckList_Caminhao();
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
        View vista = inflater.inflate( R.layout.scroll_parte2_hecklist, container, false);

        txtnome = vista.findViewById(R.id.textNome);
        textData = vista.findViewById(R.id.txtData);
        Placa = vista.findViewById(R.id.textPlaca);
        edtAgua = vista.findViewById(R.id.editAgua);
        edtPneusCalibrados = vista.findViewById(R.id.editPneusCalibrados);
        edtPneusBatidos =  vista.findViewById(R.id.editPneusBatidos);
        edtPneusRasgados = vista.findViewById(R.id.editPneusRasgados);
        edtPneusFurados = vista.findViewById(R.id.editPneusFurados);
        edtPneusCalibrados2 = vista.findViewById(R.id.editPneusCalibrados2);
        edtPneusBatidos2 = vista.findViewById(R.id.editPneusBatidos3);
        edtPneusRasgados2 = vista.findViewById(R.id.editPneusRasgados2);
        edtPneusFurados2 = vista.findViewById(R.id.editPneusFurados2);
        edtEstepe = vista.findViewById(R.id.editEstepe);

        spOculos = vista.findViewById(R.id.spinner_oculos);
        spAguaRadiador = vista.findViewById(R.id.spinner_agua);
        spPneusCalibrados = vista.findViewById(R.id.spneusCalibrados);
        spPneusBatidos = vista.findViewById(R.id.spneusBatidos);
        spPneusRasgados = vista.findViewById(R.id.spneusRasgados);
        spPneusFurados = vista.findViewById(R.id.spneusFurados);
        spEstepe = vista.findViewById(R.id.spinner_Estepe);
        spControle = vista.findViewById(R.id.spinner_controle);
        imgFoto = vista.findViewById(R.id.imageView3);
        btnEnviar = vista.findViewById(R.id.btnEnvia);
        btnPhoto = vista.findViewById(R.id.buttonCombustivel);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textData = vista.findViewById(R.id.txtData);
        textData.setText(currentDate);

        List<String> adt = new ArrayList<>();
        adt.add( 0,"Toque aqui para escolher" );
        adt.add("Sim");
        adt.add("Não");

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spOculos.setAdapter(dataAdapter);

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter7;
        dataAdapter7 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,adt);
        //Dropdown layout style
        dataAdapter7.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spControle.setAdapter(dataAdapter7);

        List<String> hype = new ArrayList<>();
        hype.add( 0,"Toque aqui para escolher" );
        hype.add("Maximo");
        hype.add("Medio");
        hype.add("Abaixo do Nivel");
        hype.add("Completou");

        //style and populate the spinner
        ArrayAdapter<String> dataAdapt;
        dataAdapt = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,hype);
        //Dropdown layout style
        dataAdapt.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spAguaRadiador.setAdapter(dataAdapt);

        spAguaRadiador.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Máximo")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtAgua.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Médio")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtAgua.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Abaixo do Nível")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtAgua.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Completou")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtAgua.setText(vnome);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        List<String> categories = new ArrayList<>();
        categories.add( 0,"Toque aqui para escolher" );
        categories.add("Sim");
        categories.add("Nao");
        //style and populate the spinner
        ArrayAdapter<String> dataAdapter1;
        dataAdapter1 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,categories);
        //Dropdown layout style
        dataAdapter1.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spPneusCalibrados.setAdapter(dataAdapter1);

        spPneusCalibrados.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Sim")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusCalibrados2.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Não")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusCalibrados2.setText(vnome);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,categories);
        //Dropdown layout style
        dataAdapter2.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spPneusBatidos.setAdapter(dataAdapter2);

        spPneusBatidos.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Sim")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusBatidos2.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Não")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusBatidos2.setText(vnome);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter3;
        dataAdapter3 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,categories);
        //Dropdown layout style
        dataAdapter3.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spPneusRasgados.setAdapter(dataAdapter3);

        spPneusRasgados.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Sim")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusRasgados2.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Não")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusRasgados2.setText(vnome);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        //style and populate the spinner
        ArrayAdapter<String> dataAdapter4;
        dataAdapter4 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,categories);
        //Dropdown layout style
        dataAdapter4.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spPneusFurados.setAdapter(dataAdapter4);

        spPneusFurados.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Sim")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusFurados2.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Não")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtPneusFurados2.setText(vnome);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        List<String> category = new ArrayList<>();
        category.add( 0,"Toque aqui para escolher" );
        category.add("Bom");
        category.add("Regular");
        category.add("Ruim");
        //style and populate the spinner
        ArrayAdapter<String> dataAdapter5;
        dataAdapter5 = new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,category);
        //Dropdown layout style
        dataAdapter5.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spEstepe.setAdapter(dataAdapter5);

        spEstepe.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition( position ).equals( "Toque aqui para escolher"  )){
                    //do nothing
                }else{
                    if (parent.getItemAtPosition( position ).equals("Bom")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtEstepe.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Regular")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtEstepe.setText(vnome);
                    }
                    if (parent.getItemAtPosition( position ).equals("Ruim")) {
                        final String vnome = parent.getSelectedItem().toString();
                        edtEstepe.setText(vnome);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        btnPhoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                carregarDialog();
            }
        } );

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregarWebService ();
            }
        });

        if(solicitarPermissoesVersoesSuperiores()){
            btnPhoto.setEnabled(true);
        }else{
            btnPhoto.setEnabled(false);
        }
        return vista;

    }
    private void carregarDialog() {
        final CharSequence[] opcoes = {"Tirar Foto", "Selecionar da Galeria", "Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Escolha uma Opção");
        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opcoes[i].equals("Tirar Foto")){
                    abrirCamera();
                }else{
                    if (opcoes[i].equals("Selecionar da Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(Intent.createChooser(intent,"Selecione"),COD_SELECIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }
    private void abrirCamera() {
        File meuFile = new File(Environment.getExternalStorageDirectory(), DIRETORIO_IMAGEM);
        boolean estaCriada = meuFile.exists();

        if(estaCriada == false){

            estaCriada = meuFile.mkdirs();
        }

        if(estaCriada == true){

            Long consecultivo = System.currentTimeMillis()/1000;
            String nome = consecultivo.toString()+".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRETORIO_IMAGEM + File.separator + nome;  //caminho completo da imagem
            fileImagem = new File(path);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagem));

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagem);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagem));
            }
            startActivityForResult(intent,COD_FOTO);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_SELECIONA:
                Uri tabCurso = data.getData();
                imgFoto.setImageURI(tabCurso);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),tabCurso);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                break;
        }

        bitmap=redimensionarImagem(bitmap,580,580);
    }

    private Bitmap redimensionarImagem(Bitmap bitmap, float larguraNova, float alturaNova) {

        int largura=bitmap.getWidth();
        int altura=bitmap.getHeight();

        if(largura>larguraNova || altura>alturaNova){
            float escalaLargura=larguraNova/largura;
            float escalaAltura= alturaNova/altura;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaLargura,escalaAltura);

            return Bitmap.createBitmap(bitmap,0,0,largura,altura,matrix,false);

        }else{
            return bitmap;
        }
    }

    private void carregarWebService () {
        progress = new ProgressDialog( getContext() );
        progress.setMessage( "Carregando..." );
        progress.show();

        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/parte2_fichaVistoria.php?";

        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {
            @Override
            public void onResponse ( String response ) {
                progress.hide();
                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtnome.setText("");
                    Placa.setText("");
                    edtPneusCalibrados.setText("");
                    edtPneusBatidos.setText("");
                    edtPneusRasgados.setText("");
                    edtPneusFurados.setText("");
                    edtEstepe.setText("");
                    imgFoto.setImageResource(R.drawable.sem_foto);
                    Toast.makeText( getContext(), "Registrado com sucesso", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( getContext(), "Registro não inserido", Toast.LENGTH_SHORT ).show();
                    Log.i( "RESPOSTA: ", "" + response );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Toast.makeText( getContext(), "Erro ao registrar " + error, Toast.LENGTH_SHORT ).show();
                Log.i( "RESPOSTA: ", "" + error );
                progress.hide();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String Nome_motorista = txtnome.getText().toString();
                String placaVeiculo = Placa.getText().toString();
                String Data = textData.getText().toString();
                String OculosProtecao = spOculos.getSelectedItem().toString();
                String controle = spControle.getSelectedItem().toString();
                String AguaRadiador = spAguaRadiador.getSelectedItem().toString();
                String PneusCalibrados = spPneusCalibrados.getSelectedItem().toString();
                String qtsPneusCalibrados = edtPneusCalibrados.getText().toString();
                String PneusBatidos = spPneusBatidos.getSelectedItem().toString();
                String qtsPneusBatidos =edtPneusBatidos.getText().toString();
                String PneusRasgados = spPneusRasgados.getSelectedItem().toString();
                String qtsPneusRasgados =edtPneusRasgados.getText().toString();
                String PneusFurados = spPneusFurados.getSelectedItem().toString();
                String qtsPneusFurados =edtPneusFurados.getText().toString();
                String Estepe = spEstepe.getSelectedItem().toString();
                String combustivel_imagem = converterImgString( bitmap );

                Map <String, String> parametros = new HashMap<>();
                parametros.put("nome",Nome_motorista);
                parametros.put( "placaVeiculo", placaVeiculo );
                parametros.put( "Data", Data );
                parametros.put( "Oculos", OculosProtecao );
                parametros.put( "ControlePortao", controle );
                parametros.put( "AguaRadiador", AguaRadiador );
                parametros.put( "PneusCalibrados", PneusCalibrados );
                parametros.put("qtsPneusCalibrados", qtsPneusCalibrados);
                parametros.put( "PneusBatidos", PneusBatidos );
                parametros.put("qtsPneusBatidos", qtsPneusBatidos);
                parametros.put( "PneusRasgados", PneusRasgados );
                parametros.put("qtsPneusRasgados", qtsPneusRasgados);
                parametros.put( "PneusFurados", PneusFurados );
                parametros.put("qtsPneusFurados", qtsPneusFurados);
                parametros.put("Estepe", Estepe);
                parametros.put("imagem", combustivel_imagem);

                return parametros;
            }

        };
        //requestQueue.add( stringRequest );
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(imgFoto);

        /* Fill it with Data */
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ficha Vistoria do Caminhão / Sprinter - Parte 2");
        email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+Placa.getText().toString()+", Nome: "+
                txtnome.getText().toString()+", Data: "+ textData.getText().toString()+", Óculos de Proteção: "+
                spOculos.getSelectedItem().toString()+", Controle do Portão: "+ spControle.getSelectedItem().toString()+", Água da Radiador: "+
                spAguaRadiador.getSelectedItem().toString()+ ", Pneus Calibrados: "+ spPneusCalibrados.getSelectedItem().toString()+
                ", Qts de Pneus Calibrados: "+ edtPneusCalibrados.getText().toString()+
                ", Pneus Batidos: "+ spPneusBatidos.getSelectedItem().toString()+
                ", Qts de Pneus Batidos: "+ edtPneusBatidos.getText().toString()+
                ", Pneus Rasgados: "+ spPneusRasgados.getSelectedItem().toString()+
                ", Qts de Pneus Rasgados: "+ edtPneusRasgados.getText().toString()+
                ", Pneus Furados: "+ spPneusFurados.getSelectedItem().toString()+
                ", Qts de Pneus Furados: "+ edtPneusFurados.getText().toString()+
                ", Estepe: "+ spEstepe.getSelectedItem().toString()+
                ", A foto do Marcador de Combustível está em Anexo...");
        //+", Marcador do Combustível: "+ converterImgString( bitmap ).toString());
        email.putExtra(Intent.EXTRA_STREAM, bmpUri);
        email.setType("image/*");

        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(email, "Enviando E-mail..."));
    }
    private String converterImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagemByte=array.toByteArray();
        String imagemString= Base64.encodeToString(imagemByte,Base64.DEFAULT);

        return imagemString;
    }
    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        Long consecultivo = System.currentTimeMillis()/1000;
        String nome = consecultivo.toString()+".jpg";
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + DIRETORIO_IMAGEM + File.separator + nome);
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction ( Uri uri );
    }

    //solicitação de permissões
    private boolean solicitarPermissoesVersoesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validar se estamos em versão de android menor que 6 para solicitar permissoes
            return true;
        }

        //ver se as permissões foram aceitas
        if((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            carregarDialogoRecomendacao();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, COD_PERMISSAO);
        }

        return false;//processa o evento dependendo do que se defina aqui
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==COD_PERMISSAO){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//REPRESENTA DUAS PERMISSOES
                Toast.makeText(getContext(),"Permissões Aceitas",Toast.LENGTH_SHORT);
                btnPhoto.setEnabled(true);
            }
        }else{
            solicitarPermissoesManual();
        }
    }
    private void solicitarPermissoesManual() {
        final CharSequence[] opciones = {"sim", "não"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Deseja configurar as permissões manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("sim")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Permissões Aceitas", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void carregarDialogoRecomendacao() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permissões Desativadas");
        dialogo.setMessage("Deve aceitar as permissões para funcionamento completo do App");

        dialogo.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
}
