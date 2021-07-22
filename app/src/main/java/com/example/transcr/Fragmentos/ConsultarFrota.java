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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.Frota1;
import com.example.transcr.entidades.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ConsultarFrota extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RelativeLayout layoutRegistrar;
    Button btnAtualizar, btnDeletar;
    ImageView imgPhoto;
    ImageButton btnConsultar;
    EditText txtPID,campoPlaca,campoModelo;

    ProgressDialog progresso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectReq;
    StringRequest stringRequest;

    Bitmap bitmap;
    private String path;
    File fileImagem;

    private static final int COD_SELECIONA = 10;
    private static final int COD_FOTO = 20;
    private static final int COD_PERMISSAO = 100;

    private static final String PASTA_PRINCIPAL = "minhasImagensApp/";  //dir principal
    private static final String PASTA_IMAGEM = "imagens";  //PASTA ONDE FICARAM AS FOTOS
    private static final String DIRETORIO_IMAGEM = PASTA_PRINCIPAL + PASTA_IMAGEM;


    private OnFragmentInteractionListener mListener;

    public static ConsultarFrota newInstance(String param1, String param2) {
        ConsultarFrota fragment = new ConsultarFrota();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if(getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate( R.layout.fragment_consulta_frotas, container, false);
        txtPID = vista.findViewById( R.id.codigo );
        campoPlaca = vista.findViewById(R.id.txt_placaVeiculo);
        campoModelo = vista.findViewById(R.id.txt_modelo);
        btnAtualizar = vista.findViewById( R.id.btnAtualizar );
        btnDeletar = vista.findViewById( R.id.btnDeletar );
        btnConsultar = vista.findViewById( R.id.btnConsult );
        imgPhoto = vista.findViewById( R.id.imagemId );
        //Só para instanciar o objeto falando que ele vai receber as requisições do tipo Volley
        //request = Volley.newRequestQueue( getContext() );

        btnConsultar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                carregarWEBService();
                campoPlaca.setEnabled( true );
                campoModelo.setEnabled( true );
            }
        });
        if(solicitarPermissoesVersoesSuperiores()){
            imgPhoto.setEnabled(true);
        }else{
            imgPhoto.setEnabled(false);
        }

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarDialog();
            }
        });


        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWEBServiceAtualizar();
                campoPlaca.setEnabled( false );
                campoModelo.setEnabled( false);
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                carregarWEBServiceDeletar();
                campoPlaca.setEnabled( false );
                campoModelo.setEnabled( false);
            }

        });

        return vista;

    }
    private void carregarWEBServiceDeletar() {
        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Deletando...");
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/deletar.php?id=" + txtPID.getText().toString();
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progresso.hide();

                if (response.trim().equalsIgnoreCase("excluido")) {
                    txtPID.setText("");
                    campoPlaca.setText("");
                    campoModelo.setText("");
                    imgPhoto.setImageResource(R.drawable.sem_foto);
                    Toast.makeText(getContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.trim().equalsIgnoreCase("naoExiste")) {
                        Toast.makeText(getContext(), "Veículo não Encontrado", Toast.LENGTH_SHORT).show();
                        Log.i("RESPOSTA: ", "" + response);
                    } else {
                        Toast.makeText(getContext(), "Erro na hora de deletar", Toast.LENGTH_SHORT).show();
                        Log.i("RESPOSTA: ", "" + response);
                    }

                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erro ao Deletar", Toast.LENGTH_SHORT).show();
                Log.i( "RESPOSTA: ", "" + error );
                progresso.hide();
            }
        });

        //request.add(stringRequest);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void carregarWEBServiceAtualizar() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Atualizando...");
        progresso.show();

        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/update.php?";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progresso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {
                    txtPID.setText("");
                    campoPlaca.setText("");
                    campoModelo.setText("");
                    imgPhoto.setImageResource(R.drawable.sem_foto);

                    Toast.makeText(getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Registro não atualizado", Toast.LENGTH_SHORT).show();
                    Log.i("RESPOSTA: ", "" + response);
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erro ao Atualizar", Toast.LENGTH_SHORT).show();
                Log.i( "RESPOSTA: ", "" + error );
                progresso.hide();
            }
        }) {
            @Override
            protected Map <String, String> getParams() throws AuthFailureError {

                String codigo = txtPID.getText().toString();
                String placa = campoPlaca.getText().toString() + " ";
                String modelo = campoModelo.getText().toString();
                String imagem = converterImgString(bitmap);

                Map<String,String> parametros = new HashMap <>();
                parametros.put("id", codigo);
                parametros.put("placaVeiculo", placa);
                parametros.put("modelo", modelo);
                parametros.put("imagem", imagem);


                return parametros;
            }

        };

        //request.add(stringRequest);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    private String converterImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, array );
        }
        byte[] imagemByte=array.toByteArray();
        String imagemString= Base64.encodeToString(imagemByte,Base64.DEFAULT);

        return imagemString;
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
        File meuFile = new File( Environment.getExternalStorageDirectory(), DIRETORIO_IMAGEM);
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
                Uri frota1 = data.getData();
                imgPhoto.setImageURI(frota1);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),frota1);
                    imgPhoto.setImageBitmap(bitmap);
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
                imgPhoto.setImageBitmap(bitmap);
                break;
        }

        bitmap=redimensionarImagem(bitmap,600,600);
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
    private void carregarWEBService() {

        progresso = new ProgressDialog(getContext());
        progresso.setMessage("Buscando...");
        progresso.show();
        String ip = getString( R.string.ip2 );
        String url = ip+"/webservice/consultarFrotaUrl.php?id=" + txtPID.getText().toString();


        jsonObjectReq = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse ( JSONObject response ) {
                progresso.hide();
                Frota1 tabFrota = new Frota1();
                JSONArray jsonArray = response.optJSONArray( "frota1" );
                JSONObject jsonObject =null;

                try{
                    jsonObject = jsonArray.getJSONObject( 0 );
                    tabFrota.setPlacaVehicles( jsonObject.optString( "placaVeiculo" ) );
                    tabFrota.setModeloVehicles( jsonObject.optString( "modelo" ) );
                    tabFrota.setUrlImagem(jsonObject.optString( "url_imagem" ) );
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                campoPlaca.setText(tabFrota.getPlacaVehicles());
                campoModelo.setText(tabFrota.getModeloVehicles());


                String ip = getString( R.string.ip2 );
                String urlImagem = ip+"/webservice/" + tabFrota.getUrlImagem();
                carregarWEBServiceImg(urlImagem);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                progresso.hide();
                Toast.makeText(getContext(), "Não foi possível efetuar a consulta " +error.toString() , Toast.LENGTH_SHORT).show();
                Log.i("ERROR", error.toString());
            }
        } );
        //request.add(jsonObjectReq);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectReq);


    }

    private void carregarWEBServiceImg (String urlImagem) {
        urlImagem = urlImagem.replace( " ","%20" );
        ImageRequest imageRequest = new ImageRequest( urlImagem, new Response.Listener <Bitmap>() {
            @Override
            public void onResponse ( Bitmap response ) {
                if (response != null){
                    bitmap = response;
                    imgPhoto.setImageBitmap( response );
                }
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                NetworkResponse response = error.networkResponse;
                if (response != null && response.statusCode == 404) {
                    imgPhoto.setImageResource(R.drawable.sem_foto);
                }else {
                    Toast.makeText( getContext(), "Erro ao carregar imagem", Toast.LENGTH_SHORT ).show();
                    Log.i( "ERRO IMAGEM", "Response ->" + error );
                }
            }
        } );
        //request.add( imageRequest );
        MySingleton.getInstance(getContext()).addToRequestQueue(imageRequest);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
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
        // TODO: Update argument type and name
        void onFragmentInteraction( Uri uri);
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
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==COD_PERMISSAO){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//REPRESENTA DUAS PERMISSOES
                Toast.makeText(getContext(),"Permissões Aceitas",Toast.LENGTH_SHORT);
                imgPhoto.setEnabled(true);
            }
        }else{
            solicitarPermissoesManual();
        }
    }


    private void solicitarPermissoesManual() {
        final CharSequence[] opciones={"sim","não"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Deseja configurar as permissões manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("sim")){
                    Intent intent=new Intent();
                    intent.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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
        AlertDialog.Builder dialogo= new AlertDialog.Builder(getContext());
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
