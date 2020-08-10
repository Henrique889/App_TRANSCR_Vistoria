package com.example.transcr.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Frota1 {
    private Integer codigo;
    private String placaVehicles;
    private String modeloVehicles;
    private String dado;
    private Bitmap imagem;
    private String urlImagem;
    public void changeText1(String text){
        placaVehicles = text;
    }

    public String getUrlImagem () {
        return urlImagem;
    }

    public void setUrlImagem ( String urlImagem ) {
        this.urlImagem = urlImagem;
    }

    public String getDado () {
        return dado;
    }

    public void setDado ( String dado ) {
        this.dado = dado;
        try {
            byte[] bytecode = Base64.decode( dado, Base64.DEFAULT );
            //this.imagem = BitmapFactory.decodeByteArray( bytecode,0,bytecode.length );
            int altura = 200;
            int largura = 200;

            Bitmap foto = BitmapFactory.decodeByteArray( bytecode,0,bytecode.length );
            this.imagem = Bitmap.createScaledBitmap( foto,altura,largura,true );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImagem () {
        return imagem;
    }

    public void setImagem ( Bitmap imagem ) {
        this.imagem = imagem;
    }


    public Integer getCodigo () {
        return codigo;
    }

    public void setCodigo ( Integer codigo ) {
        this.codigo = codigo;
    }

    public String getPlacaVehicles () {
        return placaVehicles;
    }

    public void setPlacaVehicles ( String placaVehicles ) {
        this.placaVehicles = placaVehicles;
    }

    public String getModeloVehicles () {
        return modeloVehicles;
    }

    public void setModeloVehicles ( String modeloVehicles ) {
        this.modeloVehicles = modeloVehicles;
    }
}
