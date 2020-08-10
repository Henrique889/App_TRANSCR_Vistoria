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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.MySingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class RegistrarCarretas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnAdd1,btnFoto;
    ImageView imgFoto;
    EditText txtPID,editText12,editText13;
    ProgressDialog progress;
    RelativeLayout layoutRegistrar;
    //private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    private static final int COD_SELECIONA = 10;
    private static final int COD_FOTO = 20;
    private static final int COD_PERMISSAO = 100;

    private static final String PASTA_PRINCIPAL = "minhasImagensApp/";  //dir principal
    private static final String PASTA_IMAGEM = "imagens";  //PASTA ONDE FICARAM AS FOTOS
    private static final String DIRETORIO_IMAGEM = PASTA_PRINCIPAL + PASTA_IMAGEM;

    private String path;
    File fileImagem;
    Bitmap bitmap;

    private OnFragmentInteractionListener mListener;

    public RegistrarCarretas () {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registrarCursoFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarCarretas newInstance ( String param1, String param2 ) {
        RegistrarCarretas fragment = new RegistrarCarretas();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString( ARG_PARAM1 );
            String mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View vista = inflater.inflate( R.layout.cadastrocarretas, container, false );
        txtPID = vista.findViewById( R.id.txtPID );
        editText12 = vista.findViewById( R.id.editText12 );
        editText13 = vista.findViewById( R.id.editText13 );
        btnAdd1 = vista.findViewById( R.id.btnAdd );
        imgFoto = vista.findViewById( R.id.imgFoto );
        btnFoto = vista.findViewById( R.id.btnFoto );

        //Só para instanciar o objeto falando que ele vai receber as requisições do tipo Volley
        //requestQueue = Volley.newRequestQueue( getContext() );

        btnAdd1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                carregarWebService();
            }
        } );

        btnFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                carregarDialog();
            }
        } );

        if(solicitarPermissoesVersoesSuperiores()){
            btnFoto.setEnabled(true);
        }else{
            btnFoto.setEnabled(false);
        }
        return vista;
    }
    private void carregarDialog () {
        final CharSequence[] option = {"Tirar Foto", "Selecionar da Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
        builder.setTitle( "Escolha uma opção" );
        builder.setItems( option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface, int which ) {
                if (option[which].equals( "Tirar Foto" )) {
                    //chamar method
                    abrirCamera();
                } else {
                    if (option[which].equals( "Selecionar da Galeria" )) {
                        //To call function
                        Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        intent.setType( "image/" );
                        startActivityForResult( Intent.createChooser( intent, "Selecione" ), COD_SELECIONA );
                    } else {
                        dialogInterface.dismiss();
                    }

                }

            }
        } );
        builder.show();
    }

    private void abrirCamera () {
        File meuFile = new File( Environment.getExternalStorageDirectory(), DIRETORIO_IMAGEM );
        boolean estaCriada = meuFile.exists();

        if (estaCriada == false) {

            estaCriada = meuFile.mkdirs();
        }

        if (estaCriada == true) {

            Long consecultivo = System.currentTimeMillis() / 1000;
            String nome = consecultivo.toString() + ".jpg";

            //caminho completo da imagem
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
    public void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
            switch (requestCode) {
                case COD_SELECIONA:
                        Uri carretas = data.getData();
                        imgFoto.setImageURI(carretas);
                    try {
                            bitmap = MediaStore.Images.Media.getBitmap( getContext().getContentResolver(), carretas );
                            imgFoto.setImageBitmap( bitmap );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile( getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted ( String path, Uri uri ) {
                                    Log.i( "Path", "" + path );
                                }
                            } );
                    bitmap = BitmapFactory.decodeFile( path );
                    imgFoto.setImageBitmap( bitmap );
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
        String url = ip+"/webservice/registroCarreta.php?";
        stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

            @Override
            public void onResponse ( String response ) {
                progress.hide();

                if (response.trim().equalsIgnoreCase( "registra" )) {
                    txtPID.setText("");
                    editText13.setText("");
                    editText12.setText("");
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
                Toast.makeText( getContext(), "Erro ao registrar "+ error, Toast.LENGTH_SHORT ).show();
                Log.i( "RESPOSTA: ", "" + error );
                progress.hide();
            }
        } ) {
            @Override
            protected Map <String, String> getParams() throws AuthFailureError {

                String id = txtPID.getText().toString();
                String placaCarreta = editText13.getText().toString();
                String tipoCarreta = editText12.getText().toString();
                String imagem = converterImgString( bitmap );

                Map <String, String> parametros = new HashMap <>();
                parametros.put( "id", id );
                parametros.put( "placaCarreta", placaCarreta );
                parametros.put( "tipoCarreta", tipoCarreta );
                parametros.put( "imagem", imagem );

                return parametros;
            }

        };
        //requestQueue.add( stringRequest );
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    public static  String converterImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, array );
        byte [] imageByte = array.toByteArray();
        String imgS = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imgS;
    }

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
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            //validar se estamos em versão de android menor que 6 para solicitar permissoes
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
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==COD_PERMISSAO){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//REPRESENTA DUAS PERMISSOES
                Toast.makeText(getContext(),"Permissões Aceitas",Toast.LENGTH_SHORT);
                btnFoto.setEnabled(true);
            }
        }else{
            solicitarPermissoesManual();
        }
    }


    private void solicitarPermissoesManual() {
        final CharSequence[] opciones={"sim","não"};
        final android.support.v7.app.AlertDialog.Builder alertOpciones=new android.support.v7.app.AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Deseja configurar as permissões manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("sim")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Permissões Aceitas",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void carregarDialogoRecomendacao() {
        android.support.v7.app.AlertDialog.Builder dialogo=new android.support.v7.app.AlertDialog.Builder(getContext());
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
