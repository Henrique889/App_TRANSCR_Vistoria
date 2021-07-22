package com.example.transcr.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.transcr.R;
import com.example.transcr.entidades.Frota1;
import com.example.transcr.entidades.MySingleton;

import java.util.List;


public class FrotaAdapteImgURL extends RecyclerView.Adapter<FrotaAdapteImgURL.FrotaHolder> {
    private static final String TAG = "FrotaAdapteImgURL";

    List <Frota1> listaFrotasimg;
    //RequestQueue request;
    Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener listener){
        mListener = listener;
    }

    public FrotaAdapteImgURL ( List <Frota1> listaFrotasimg, Context context ) {
        this.listaFrotasimg = listaFrotasimg;
        this.context = context;
        //request = Volley.newRequestQueue( context );
    }

    @NonNull
    @Override
    public FrotaHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {
        View vista = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.list_veiculos_img, viewGroup, false );
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        vista.setLayoutParams( layoutParams );
        return new FrotaHolder( vista);
    }

    @Override
    public void onBindViewHolder ( @NonNull FrotaHolder frotasHolderImg, final int position ) {
        //     frotasHolderImg.txtCodigo.setText(listaFrotasimg.get(position).getCodigo().toString());
        frotasHolderImg.txtPlaca.setText(listaFrotasimg.get( position ).getPlacaVehicles());
        frotasHolderImg.txtModelo.setText(listaFrotasimg.get( position ).getModeloVehicles());

        if (listaFrotasimg.get( position ).getUrlImagem() != null) {
            carregarImageWEBService( listaFrotasimg.get( position ).getUrlImagem(), frotasHolderImg );
        } else {

            frotasHolderImg.idImagem.setImageResource( R.drawable.sem_foto );
        }

    }

    private void carregarImageWEBService ( String urlImagem, final FrotaHolder frotasHolderImg ) {
        String ip = context.getString( R.string.ip2 );

        String pathImagem = ip + "/webservice/" + urlImagem;
        pathImagem = pathImagem.replace( " ", "%20" );

        ImageRequest imageRequest = new ImageRequest( pathImagem, new Response.Listener <Bitmap>() {
            @Override
            public void onResponse ( Bitmap response ) {
                frotasHolderImg.idImagem.setImageBitmap( response );
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                NetworkResponse response = error.networkResponse;
                if (response != null && response.statusCode == 404) {
                    frotasHolderImg.idImagem.setImageResource( R.drawable.sem_foto );
                } else {
                    Toast.makeText( context, "Erro ao carregar a imagem", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        //request.add(imageRequest);
        MySingleton.getInstance( context ).addToRequestQueue( imageRequest );
    }

    @Override
    public int getItemCount () {
        return listaFrotasimg.size();
    }

    public class FrotaHolder extends RecyclerView.ViewHolder{

        TextView txtPlaca, txtCodigo, txtModelo;
        ImageView idImagem;
        //private ItemClickListener itemClickListener;

        public FrotaHolder ( View itemView ) {
            super( itemView );
            txtPlaca = itemView.findViewById( R.id.txtPLACA );
            //txtCodigo= (TextView) itemView.findViewById(R.id.txtCodigo);
            txtModelo = itemView.findViewById( R.id.txtModelo );
            idImagem = itemView.findViewById( R.id.idImagem );
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick ( View v ) {
                    if ( mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick( position );
                        }
                    }

                }
            } );

        }
    }
}
