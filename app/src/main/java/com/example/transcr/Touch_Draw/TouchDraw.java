package com.example.transcr.Touch_Draw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.transcr.Helper.BitmapHelper;
import com.example.transcr.R;
import com.example.transcr.entidades.MySingleton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TouchDraw extends Activity{
    int angle = 0;
    SubsamplingScaleImageView imageView0;
    Button saveBtn,redoButton, BtnAvancar,editBtn, rotateBtn;
    Bitmap bitmap1,bitmap2;
    int width, height;
    Canvas canvas;
    int BitmapSize = 30;
    Resources resources;
    TextView placa;
    private Path mPath;
    ProgressDialog progress;
    private Spinner spiPosition;
    public static final String Carreta_PLACA = "placaCarreta";
    public static final String EXTRA_PLACA = "EscolherCarretas_Avarias.placaCarreta";
    private static final String PASTA_PRINCIPAL = "minhasImagensApp/";  //dir principal
    private static final String PASTA_IMAGEM = "imagens";  //PASTA ONDE FICARAM AS FOTOS
    private static final String DIRETORIO_IMAGEM = PASTA_PRINCIPAL + PASTA_IMAGEM;

    Float scale = 1.0f;


    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_touch_draw );
        spiPosition = findViewById( R.id.spiPosition );
        imageView0 =  findViewById( R.id.imageView1 );

        /*PhotoViewAttacher photoView = new PhotoViewAttacher(imageView0);
        photoView.update();*/

        placa = findViewById( R.id.placa5 );
        rotateBtn = findViewById(R.id.btnGirar);
        editBtn = findViewById( R.id.btnButton2 );
        saveBtn = findViewById( R.id.btn_Zoom );
        redoButton = findViewById( R.id.btnButton );
        //imageView0.setOnTouchListener(this) ;
        BtnAvancar = findViewById( R.id.btnAvancar );

        BtnAvancar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                progress = new ProgressDialog( TouchDraw.this );
                progress.setMessage( "Enviando..." );
                progress.show();

                String ip = getString( R.string.ip2 );
                String url = ip + "/webservice/SalvarImagemPaint.php?";
                StringRequest stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {

                    @Override
                    public void onResponse ( String response ) {
                        progress.dismiss();
                        Toast.makeText( TouchDraw.this, "Enviado com sucesso", Toast.LENGTH_SHORT ).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse ( VolleyError error ) {
                        progress.dismiss();
                        Toast.makeText( TouchDraw.this, "Erro ao enviar "+ error, Toast.LENGTH_SHORT ).show();
                        Log.i( "RESPOSTA: ", "" + error );
                    }
                } ) {
                    @Override
                    protected Map <String, String> getParams () throws AuthFailureError {

                        Map <String, String> parametros = new HashMap <>();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String fname = placa.getText().toString();
                        String imageData = imageToString(bitmap2);
                        String spinner = spiPosition.getSelectedItem().toString();

                        parametros.put( "name", fname);
                        parametros.put( "imagem", imageData);
                        parametros.put( "posicaoImage", spinner);

                        return parametros;
                    }

                };
                MySingleton.getInstance( TouchDraw.this).addToRequestQueue(stringRequest);

                Intent email = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                email.setType("plain/text");
                email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"informatica@transcr.com.br,operacional.vcp@transcr.com.br"});
                email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Avaria no Caminhão / Carreta - FOTO");
                email.putExtra(android.content.Intent.EXTRA_TEXT,"Placa Veículo: "+placa.getText().toString()+
                        ", Posição do Veículo/Carreta na Foto: "+ spiPosition.getSelectedItem().toString()
                        +", Marcador do Combustível: "+ "http://transcr10.com/webservice/imagensAvarias/"+placa.getText().toString()+
                        "_"+spiPosition.getSelectedItem().toString()+".jpg");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(email, "Enviando E-mail..."));

            }
        } );

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("image_Url", 0);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String TotalScore = extras.getString( String.valueOf( intValue ) );
            placa.setText(TotalScore);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                imageView0 =  findViewById( R.id.imageView1 );
                imageView0.setImage(ImageSource.bitmap(bitmap2));
                imageView0.setZoomEnabled(true);
                imageView0.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }});
                //imageView0.setImage(ImageSource.bitmap(bitmap2));
                //imageView0.setImage(ImageSource.cachedBitmap(bitmap2));
            }
        });
        imageView0.setZoomEnabled(true);
        resources = getResources();

        CreateBitmap();

        bitmap2 = Bitmap.createBitmap(
                bitmap1.getWidth()+15/2, bitmap1.getHeight()+15/2,
                Bitmap.Config.ARGB_8888);

        DrawCanvas();
        imageView0.setImage(ImageSource.bitmap(bitmap2));

        editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generatedcreateScaledBitmap method stub
                imageView0.setOnTouchListener(new View.OnTouchListener(){

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        int x = (int) event.getX();
                        int y = (int) event.getY();

                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                //textSource.setText("ACTION_DOWN- " + x + " : " + y);
                                drawOnProjectedBitMap( (SubsamplingScaleImageView) v, bitmap1, x, y);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //textSource.setText("ACTION_MOVE- " + x + " : " + y);
                                drawOnProjectedBitMap( (SubsamplingScaleImageView) v, bitmap1, x, y);
                                break;
                            case MotionEvent.ACTION_UP:
                                //textSource.setText("ACTION_UP- " + x + " : " + y);
                                drawOnProjectedBitMap( (SubsamplingScaleImageView) v, bitmap1, x, y);
                                break;
                        }
                        /*
                         * Return 'true' to indicate that the event have been consumed.
                         * If auto-generated 'false', your code can detect ACTION_DOWN only,
                         * cannot detect ACTION_MOVE and ACTION_UP.
                         */

                        return true;
                    }});
                }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.drawColor( Color.TRANSPARENT);
                canvas.drawBitmap(
                        bitmap1,
                        0,
                        0,
                        null
                );
                imageView0.setImage(ImageSource.bitmap(bitmap2));

                    imageView0.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }});
                CreateBitmap();

            }
        });
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle = angle + 90;
                imageView0.setRotation(angle);
            }
        });

        List <String> categories = new ArrayList <>();
        categories.add( 0,"Indicar Posição do Veículo/Carreta" );
        categories.add("Frente_diagonalEsquerda");
        categories.add("Frente_diagonalDireita");
        categories.add("Frente");
        categories.add("Lado Esquerdo");
        categories.add("Lado_Esquerdo_PneuFrente");
        categories.add("Lado_Esquerdo_PneuTraseiro");
        categories.add("Lado Direito");
        categories.add("Lado_Direito_PneuFrente");
        categories.add("Lado_Direito_PneuTraseiro");
        categories.add("Atrás_veiculo");
        categories.add("Parte Interna");

        //style and populate the spinner
        ArrayAdapter <String> dataAdapter;
        dataAdapter = new ArrayAdapter( this,android.R.layout.simple_spinner_item,categories);
        //Dropdown layout style
        dataAdapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        //attaching data adapter to spinner
        spiPosition.setAdapter(dataAdapter);

    }
    public void DrawCanvas(){

        canvas = new Canvas(bitmap2);
        mPath = new Path();

        canvas.drawColor( Color.TRANSPARENT);
        canvas.drawBitmap(bitmap1,0,0,null);


    }
    /*
     * Project position on ImageView to position on Bitmap
     * draw on it
     */
   private void drawOnProjectedBitMap(SubsamplingScaleImageView iv, Bitmap bitm, int x, int y){
       if(x<0 || y<0 || x > iv.getWidth() || y > iv.getHeight()){
           //outside ImageView
            return;
       }else{
            int projectedX = (int)((double)x * ((double)bitm.getWidth()/(double)iv.getWidth()));
            int projectedY = (int)((double)y * ((double)bitm.getHeight()/(double)iv.getHeight()));

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(12);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor( Color.GREEN );

            canvas.drawCircle( projectedX, projectedY, 5, paint );
            imageView0.invalidate();


        }
   }
    public void CreateBitmap(){
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("image_url",0);

        bitmap1  = BitmapHelper.getInstance().getBitmap();

    }
    public void GetBitmapWidthHeight(){

        width = bitmap1.getWidth() + BitmapSize * 2;
        height = bitmap1.getHeight() + BitmapSize * 2;

    }

    private String imageToString (Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public void onPointerCaptureChanged ( boolean hasCapture ) {

    }

}
