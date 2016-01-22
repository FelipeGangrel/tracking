package com.fococomunicacao.backgroundapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrador on 21/12/2015.
 */
public class Banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    protected static final String TABELA = "Localizacao";
    protected static final String ID = "_id";
    protected static final String LATITUDE = "latitude";
    protected static final String LONGITUDE = "longitude";
    protected static final String DATAHORA = "datahora";
    private static final int VERSAO = 1;

    //Método construtor
    public Banco(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement,"
                + LATITUDE + " text,"
                + LONGITUDE + " text,"
                + DATAHORA + " text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}
