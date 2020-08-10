package com.example.transcr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="registro.db";
    public static final String TABLE_NAME="register";
    public static final String COL_1="ID";
    public static final String COL_2="Nome";
    public static final String COL_3="SobreNome";
    public static final String COL_4="Email";
    public static final String COL_5="Senha";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null,1);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,Nome TEXT,SobreNome TEXT,Senha TEXT,Email TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //SOLTE A TABELA MAIS VELHA SE EXISTIR
        onCreate(db);

    }

}
    //para enviar vc usa o metodo putExtra primeiro parametro é uma chave e o segundo o dado
   /* protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(DatabaseHelper.this, CheckLists.class);

        intent.putExtra("_id", id);
        intent.putExtra("anotacao", anotacao);

        startActivity(intent);
    }*/




