package com.example.transcr.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.transcr.R;
import com.example.transcr.entidades.Frota1;

import java.util.List;


public class FrotaAdapteIMG extends RecyclerView.Adapter<FrotaAdapteIMG.FrotaHolder> {
    List <Frota1> listaFrotasimg;

    public FrotaAdapteIMG(List<Frota1> listaFrotasimg) {
        this.listaFrotasimg = listaFrotasimg;
    }
    @NonNull
    @Override
    public FrotaHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_veiculos_img, viewGroup, false);
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        vista.setLayoutParams(layoutParams);
        return new FrotaHolder(vista);
    }

    @Override
    public void onBindViewHolder ( @NonNull FrotaHolder frotasHolderImg, int position ) {
        //frotasHolderImg.txtCodigo.setText(listaFrotasimg.get(position).getCodigo().toString());
        frotasHolderImg.txtPlaca.setText(listaFrotasimg.get(position).getPlacaVehicles());
        frotasHolderImg.txtModelo.setText(listaFrotasimg.get(position).getModeloVehicles());
        if (listaFrotasimg.get(position).getImagem() != null){
            frotasHolderImg.idImagem.setImageBitmap( listaFrotasimg.get(position).getImagem());
        }else{
            frotasHolderImg.idImagem.setImageResource( R.drawable.sem_foto );
        }

    }

    @Override
    public int getItemCount () {
        return listaFrotasimg.size();
    }

    public static class FrotaHolder extends RecyclerView.ViewHolder {

        TextView txtPlaca,txtCodigo,txtModelo;
        ImageView idImagem;

        public FrotaHolder( View itemView) {
            super(itemView);
            txtPlaca= itemView.findViewById(R.id.txtPLACA);
            txtCodigo= itemView.findViewById(R.id.txtCodigo);
            txtModelo= itemView.findViewById(R.id.txtModelo);
            idImagem=  itemView.findViewById(R.id.idImagem);
        }
    }
}
