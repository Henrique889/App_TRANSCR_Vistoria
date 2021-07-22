package com.example.transcr.ShowAvariasImages;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

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

import java.util.ArrayList;
import java.util.List;

public class Ver_TodasAvarias_Carretas extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    TextView placa;
    public static final String EXTRA_PLACA = "placaCarreta";
    RequestQueue requestQueue;
    List <SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    String request_Url;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.visualizar_avaria );
        placa = findViewById(R.id.txtPlaca10 );

        if (getIntent() != null) {
        String placaV = getIntent().getStringExtra(EXTRA_PLACA);
        placa.setText(placaV);
        }
    requestQueue = CustomVolleyRequest.getInstance(this).getRequestQueue();
    //requestQueue = Volley.newRequestQueue( this );

    sliderImg = new ArrayList <>();

    viewPager = findViewById( R.id.viewPager );

    sliderDotspanel = findViewById( R.id.SliderDots );
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
        request_Url = "http://transcr10.com/webservice/Pegar_ImagensAvarias.php?name=" + placa.getText().toString();
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
                viewPagerAdapter = new ViewPagerAdapter( sliderImg, Ver_TodasAvarias_Carretas.this );
                viewPager.setAdapter( viewPagerAdapter );

                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView( Ver_TodasAvarias_Carretas.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.twotone_brightness_1_black_18dp));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(30, 20, 30, 20);

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
