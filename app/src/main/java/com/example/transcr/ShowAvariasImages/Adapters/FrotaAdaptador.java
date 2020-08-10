package com.example.transcr.ShowAvariasImages.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.transcr.R;
import com.example.transcr.ShowAvariasImages.Ver_TodasAvarias;
import com.example.transcr.entidades.Frota1;

import java.util.List;

public class FrotaAdaptador extends RecyclerView.Adapter<FrotaAdaptador.FrotasHolder> {
    Context mContext;
    List<Frota1> listaFrotas;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick ( int position );
    }
    public void setOnItemClickListener ( OnItemClickListener listener) {
        mListener = listener;
    }
    public FrotaAdaptador ( Context mContext, List<Frota1> listaFrotas)    {
        this.mContext = mContext;
        this.listaFrotas = listaFrotas;
    }

    @NonNull
    @Override
    public FrotasHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {

        View vista = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_veiculos, viewGroup, false);
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        final FrotasHolder cHolder = new FrotasHolder( vista );
        cHolder.item_contact.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent( mContext, Ver_TodasAvarias.class );
                //Toast.makeText(mContext, "O login foi realizado com sucesso", Toast.LENGTH_LONG).show();
                mContext.startActivity(intent);
            }
        } );
        vista.setLayoutParams(layoutParams);
        return new FrotasHolder(vista);
    }

    @Override
    public void onBindViewHolder ( @NonNull FrotasHolder frotasHolder, int position ) {
        frotasHolder.txtCodigo.setText(listaFrotas.get(position).getCodigo().toString());
        frotasHolder.txtPlaca.setText(listaFrotas.get(position).getPlacaVehicles());
        frotasHolder.txtModelo.setText(listaFrotas.get(position).getModeloVehicles());
    }

    @Override
    public int getItemCount () {
        return listaFrotas.size();
    }

    public class FrotasHolder extends RecyclerView.ViewHolder {
        TextView txtPlaca,txtCodigo,txtModelo;
        private LinearLayout item_contact;
        public FrotasHolder ( @NonNull View itemView ) {
            super( itemView );
            item_contact = itemView.findViewById(R.id.list_itemV);

            txtPlaca= itemView.findViewById(R.id.txtPLACA);
            txtCodigo= itemView.findViewById(R.id.txtCodigo);
            txtModelo= itemView.findViewById(R.id.txtModelo);

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

