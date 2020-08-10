package com.example.transcr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Button _btnlogin;
    private EditText _txtemail;
    private EditText _txtsenha;
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


        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = ((EditText) findViewById( R.id.txtemail)).getText().toString();
                String password = ((EditText)findViewById( R.id.txtsenha)).getText().toString();
                String email = _txtemail.getText().toString();
                String senha = _txtsenha.getText().toString();
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
                        finish();
                    }else{
                        Toast.makeText(login.this, "Seu e-mail ou senha não estão corretos  ", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

    }

    /*public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace( R.id.login, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


}
