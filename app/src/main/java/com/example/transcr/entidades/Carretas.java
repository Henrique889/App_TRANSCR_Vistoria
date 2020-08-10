package com.example.transcr.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Carretas {
    private Integer codigo;
    private String placaCarreta;
    private String tipoCarreta;
    private String dado;
    private Bitmap imagem;
    private String urlImagem;

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

    public String getPlacaCarreta () {
        return placaCarreta;
    }

    public void setPlacaCarreta ( String placaCarreta ) {
        this.placaCarreta = placaCarreta;
    }

    public String getTipoCarreta () {
        return tipoCarreta;
    }

    public void setTipoCarreta ( String tipoCarreta ) {
        this.tipoCarreta = tipoCarreta;
    }
}
