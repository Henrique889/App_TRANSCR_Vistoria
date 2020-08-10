package com.example.transcr.Touch_Draw;

import android.content.Intent;
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
import com.example.transcr.Adapters.ViewPagerAdapterCarretas;
import com.example.transcr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OlharImagensCarretas extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    Intent intent = getIntent();

    EditText placa;
    Button button;
    public static final String EXTRA_PLACA1 = "placaCarreta";

    RequestQueue requestQueue;
    List<SliderUtils> sliderImg;
    ViewPagerAdapterCarretas viewPagerAdapter;

    String request_Url;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.escolhafoto_para_editar);
        placa = findViewById(R.id.placa4 );

        Intent mIntent = getIntent();
        //Get intent from EscolherCaminhao_Avarias.class || Get intent from EscolherCarretas_Avarias.class
        if (mIntent != null) {
            String placaV = getIntent().getStringExtra(EXTRA_PLACA1);
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
                viewPagerAdapter = new ViewPagerAdapterCarretas( sliderImg, OlharImagensCarretas.this );
                viewPager.setAdapter( viewPagerAdapter );
                viewPager.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick ( View v ) {
                        Intent i = new Intent(OlharImagensCarretas.this, TouchDraw.class);
                        String st = placa.getText().toString();
                        i.putExtra(EXTRA_PLACA1, st);
                        startActivity(i);
                   }
                } );
                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView( OlharImagensCarretas.this);
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

}
