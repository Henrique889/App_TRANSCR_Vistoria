package com.example.transcr.Touch_Draw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.transcr.Adapters.CustomVolleyRequest;
import com.example.transcr.Adapters.SliderUtils;
import com.example.transcr.Adapters.ViewPagerAdapter;
import com.example.transcr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OlharImagens extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    Intent intent = getIntent();

    EditText placa;
    Button button;
    public static final String EXTRA_PLACA = "placaVeiculo";
    RequestQueue requestQueue;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    String request_Url;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.escolhafoto_para_editar);
        placa = findViewById(R.id.placa4 );


        //Get intent from EscolherCaminhao_Avarias.class || Get intent from EscolherCarretas_Avarias.class
        if (getIntent() != null) {
            String placaV = getIntent().getStringExtra(EXTRA_PLACA);
            placa.setText(placaV);
        }
        requestQueue = CustomVolleyRequest.getInstance(this).getRequestQueue();
        //requestQueue = Volley.newRequestQueue( this );

        sliderImg = new ArrayList <>();

        viewPager = findViewById( R.id.viewPager );

        sliderDotspanel = findViewById(R.id.SliderDots);
        sendRequest();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable( ContextCompat.getDrawable(getApplicationContext(), R.drawable.twotone_brightness_1_black_18dp));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sharp_fiber_manual_record_black_18dp));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void sendRequest(){
        request_Url = "http://transcr10.com/webservice/sliderjson.php?placa=" + placa.getText().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, request_Url, null, new Response.Listener <JSONArray>() {
            @Override
            public void onResponse ( JSONArray response ) {
                for (int i = 0; i < response.length(); i++ ){
                    SliderUtils sliderUtils = new SliderUtils();

                    try{
                        JSONObject jsonObject = response.getJSONObject( i );

                        sliderUtils.setSliderImageUrl( jsonObject.getString("image_Url" ) );


                    }catch (JSONException e){
                        e.printStackTrace();
                        Log.e("user",e.getMessage());
                    }
                    sliderImg.add( sliderUtils );

                }
                viewPagerAdapter = new ViewPagerAdapter( sliderImg, OlharImagens.this );
                viewPager.setAdapter( viewPagerAdapter );
                viewPager.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick ( View v ) {
                        Intent i = new Intent(OlharImagens.this, TouchDraw.class);
                        i.putExtra(EXTRA_PLACA, placa.getText().toString());
                        startActivity(i);
                    }
                } );
                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(OlharImagens.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.twotone_brightness_1_black_18dp));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sharp_fiber_manual_record_black_18dp));

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Log.e("user", error.getMessage());

            }
        } );
        //requestQueue.add( jsonArrayRequest );

        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
    private Bitmap loadBitmapFromUrl(String sourceLink){
        try{
            URL url= new URL(sourceLink);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput( true );
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            return myBitmap;

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

}
