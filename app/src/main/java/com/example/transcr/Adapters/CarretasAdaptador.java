package com.example.transcr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.transcr.Fragmentos.EscolhaTipoVistoria2;
import com.example.transcr.R;
import com.example.transcr.entidades.Carretas;

import java.util.List;

public class CarretasAdaptador extends RecyclerView.Adapter<CarretasAdaptador.CarretasHolder> {
    Context mContext;
    List<Carretas> listaCarretas;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener ( OnItemClickListener listener) {
        mListener = listener;
    }
    public CarretasAdaptador ( Context mContext, List<Carretas> listaCarretas)    {
        this.mContext = mContext;
        this.listaCarretas = listaCarretas;
    }

    @NonNull
    @Override
    public CarretasHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {

        View vista = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.lista_carretas, viewGroup, false);
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        final CarretasHolder cHolder = new CarretasHolder( vista );
        cHolder.item_contact.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent( mContext, EscolhaTipoVistoria2.class );
                //Toast.makeText(mContext, "O login foi realizado com sucesso", Toast.LENGTH_LONG).show();
                mContext.startActivity(intent);
            }
        } );
        vista.setLayoutParams(layoutParams);
        return new CarretasHolder(vista);
    }

    @Override
    public void onBindViewHolder ( @NonNull CarretasHolder carretasHolder, int position ) {
        carretasHolder.txtCodigo.setText(listaCarretas.get(position).getCodigo().toString());
        carretasHolder.txtPlaca.setText(listaCarretas.get(position).getPlacaCarreta());
        carretasHolder.txtTipo.setText(listaCarretas.get(position).getTipoCarreta());
    }

    @Override
    public int getItemCount () {
        return listaCarretas.size();
    }

    public class CarretasHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact;
        TextView txtPlaca,txtCodigo,txtTipo;
        public CarretasHolder ( @NonNull View itemView ) {
            super( itemView );
            item_contact = itemView.findViewById(R.id.list_item);
            txtPlaca= itemView.findViewById(R.id.txtPLACA);
            txtCodigo= itemView.findViewById(R.id.txtCodigo);
            txtTipo= itemView.findViewById(R.id.txtTipo);

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

