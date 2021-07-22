package com.example.transcr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Button _btnlogin;
    private EditText _txtemail,_txtsenha;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);

        _btnlogin= findViewById( R.id.btnlogin);
        _txtemail= findViewById( R.id.txtemail);
        _txtsenha= findViewById( R.id.txtsenha);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        SharedPreferences loginPreferences = getSharedPreferences("SPF_NAME",
                Context.MODE_PRIVATE);
        _txtemail.setText(loginPreferences.getString("USERNAME", ""));
        _txtsenha.setText(loginPreferences.getString("PASSWORD", ""));

        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = ((EditText) findViewById( R.id.txtemail)).getText().toString();
                String password = ((EditText)findViewById( R.id.txtsenha)).getText().toString();
                String email = _txtemail.getText().toString().trim();
                String senha = _txtsenha.getText().toString().trim();

                SharedPreferences loginPreferences = getSharedPreferences("SPF_NAME", Context.MODE_PRIVATE);
                loginPreferences.edit().putString("USERNAME", email).putString("PASSWORD", senha).apply();

                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, senha});
                if (cursor!=null){
                    if (usuario.equals( "admin" )&&(password.equals( "admin" ))){
                        Intent intent = new Intent(login.this, EscolhaVeiculo.class );
                        Toast.makeText(login.this, "O login foi realizado com sucesso", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    else if (cursor.getCount()>0){
                        cursor.moveToNext();
                        Toast.makeText(login.this, "O login foi realizado com sucesso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(login.this, EscolhaVeiculoUser.class );
                        startActivity(intent);
                    }else{
                        Toast.makeText(login.this, "Seu e-mail ou senha não estão corretos  ", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }
}
