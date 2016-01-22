package com.fococomunicacao.backgroundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrador on 21/12/2015.
 */
public class BancoController  {
    private SQLiteDatabase db;
    private Banco banco;

    public BancoController(Context context){
        banco = new Banco(context);
    }

    public String insereDado(String latitude, String longitude, String datahora){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Banco.LATITUDE, latitude);
        valores.put(Banco.LONGITUDE, longitude);
        valores.put(Banco.DATAHORA, datahora);

        resultado = db.insert(Banco.TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos = {banco.ID,banco.LATITUDE,banco.LONGITUDE,banco.DATAHORA};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA,campos,null,null,null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

}
