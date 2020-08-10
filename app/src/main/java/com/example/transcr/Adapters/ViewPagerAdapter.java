package com.example.transcr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.transcr.Helper.BitmapHelper;
import com.example.transcr.R;
import com.example.transcr.Touch_Draw.TouchDraw;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

   // public static final String EXTRA_PLACA = "placaCarreta";
    public static final String EXTRA_PLACA = "placaVeiculo";

    private TouchDraw touchDraw;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> sliderImg;
    private ImageLoader imageLoader;

    public ViewPagerAdapter ( List <SliderUtils> sliderImg, Context context ){
        this.sliderImg = sliderImg;
        this.context = context;
    }

    @Override
    public int getCount () {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject ( View view,  Object o ) {
        return view == o;
    }

    @Override
    public Object instantiateItem (  ViewGroup container, final int position ) {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.custom_layout, null);
        SliderUtils utils = sliderImg.get( position );

        final ImageView imageView = view.findViewById( R.id.imageView2 );
        final EditText placa = view.findViewById( R.id.placa5 );

        imageLoader = CustomVolleyRequest.getInstance( context ).getImageLoader();
        imageLoader.get( utils.getSliderImageUrl(),ImageLoader.getImageListener( imageView,R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert ) );

        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                //if(position == 0){
                    Toast.makeText(context, "Slide Clicked", Toast.LENGTH_SHORT).show();
                    //int i=R.drawable.ladodireito_eob6025;
                    imageView.setDrawingCacheEnabled(true);
                    Bitmap b=imageView.getDrawingCache();
                    Intent i = new Intent(context, TouchDraw.class);
                    //int currentItem =view.getCurrentItem();
                    i.putExtra("image_Url", 0);
                    BitmapHelper.getInstance().setBitmap( b );
                    //i.putExtra("EXTRA_PLACA", String.valueOf( placa ) );

                    //i.putExtra("Currentitem", sliderImg.get( position ).toString());
                    imageView.setImageBitmap( b );
                    //i.putExtra("Bitmap", b);
                    context.startActivity(i);

                //}
                /*if(position == 1){
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.frente_eob6025;

                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } if(position == 2){
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.frente_direito_eob6025;

                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 3){
                    Toast.makeText(context, "Slide 4 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.frente_esquerdo_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 4){
                    Toast.makeText(context, "Slide 5 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.ladoesquerdo_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 5){
                    Toast.makeText(context, "Slide 6 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.pneu_traseiro_esquerdo_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 6){
                    Toast.makeText(context, "Slide 7 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.pneu_dianteiro_esquerdo_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 7){
                    Toast.makeText(context, "Slide 8 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parte_traseira_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 8){
                    Toast.makeText(context, "Slide 9 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.pneu_dianteiro_direito_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 9){
                    Toast.makeText(context, "Slide 10 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.pneu_traseiro_direito_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 10){
                    Toast.makeText(context, "Slide 11 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 11){
                    Toast.makeText(context, "Slide 12 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna2_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 12){
                    Toast.makeText(context, "Slide 13 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna3_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 13){
                    Toast.makeText(context, "Slide 14 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna4_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 14){
                    Toast.makeText(context, "Slide 15 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna5_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 15){
                    Toast.makeText(context, "Slide 16 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna6_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 16){
                    Toast.makeText(context, "Slide 17 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna7_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                } else if(position == 17){
                    Toast.makeText(context, "Slide 18 Clicked", Toast.LENGTH_SHORT).show();
                    int i=R.drawable.parteinterna8_eob6025;
                    Intent intent = new Intent(context,TouchDraw.class);
                    intent.putExtra("image_Url", i);
                    context.startActivity(intent);
                }*/
            }
        } );
        ViewPager vp = (ViewPager) container;
        vp.addView( view, 0 );

        return view;
    }

    @Override
    public void destroyItem ( @NonNull ViewGroup container, int position, @NonNull Object object ) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView( view );
    }

}
