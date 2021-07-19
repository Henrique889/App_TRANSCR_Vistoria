package com.example.transcr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Button _btnlogin;
    private EditText _txtemail,_txtsenha;
    CheckBox remember;
    private Cursor cursor;
    private static final String Ficheiro = "docEmail.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);

        _btnlogin= findViewById( R.id.btnlogin);
        _txtemail= findViewById( R.id.txtemail);
        _txtsenha= findViewById( R.id.txtsenha);
        remember = findViewById(R.id.remember);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user = prefs.getString("f1","");
        _txtemail.setText(user);

        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = ((EditText) findViewById( R.id.txtemail)).getText().toString();
                String password = ((EditText)findViewById( R.id.txtsenha)).getText().toString();
                String email = _txtemail.getText().toString();
                String senha = _txtsenha.getText().toString();

                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                String checkbox = preferences.getString("remember","");
                if (checkbox.equals("true")){
                    SharedPreferences prefes = PreferenceManager.getDefaultSharedPreferences(login.this);
                    SharedPreferences.Editor editor = prefes.edit();
                    editor.putString("f1", email);
                    editor.apply();

                }else if (checkbox.equals("false")){
                    Toast.makeText(getApplicationContext(),"Por favor, marque a opção lembrar", Toast.LENGTH_SHORT);
                }

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

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }else if(!compoundButton.isChecked()){
                    SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
    }
}
