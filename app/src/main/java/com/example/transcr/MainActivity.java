package com.example.transcr;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _btnRegister,_btnlogin;
    EditText _txtFnome, _txtNome, _txtSenha,_txtEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        openHelper= new DatabaseHelper(this);
        _btnRegister = findViewById(R.id.btnRegister);
        _txtFnome = findViewById(R.id.txtFnome);
        _txtNome = findViewById(R.id.txtNome);
        _txtSenha = findViewById(R.id.txtSenha);
        _txtEmail = findViewById(R.id.editText5);
        _btnlogin= findViewById(R.id.btnlogin);
        _btnRegister.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                if (_txtFnome.getText().length() == 0 || _txtNome.getText().length() == 0 || _txtSenha.getText().length() == 0 ||_txtEmail.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos vazios, por favor", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "O cadastro foi realizado com sucesso", Toast.LENGTH_LONG).show();
                    db= openHelper.getWritableDatabase();
                    String fnome=_txtFnome.getText().toString();
                    String SobreNome=_txtNome.getText().toString();
                    String pass=_txtSenha.getText().toString();
                    String email=_txtEmail.getText().toString();
                    insertdata(fnome, SobreNome, pass, email);
                    Intent intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                }
            }
        });

        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
    }
    public void insertdata (String fnome, String SobreNome,String pass,String email ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,fnome);
        contentValues.put(DatabaseHelper.COL_3,SobreNome);
        contentValues.put(DatabaseHelper.COL_4, email);
        contentValues.put(DatabaseHelper.COL_5, pass);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

}
