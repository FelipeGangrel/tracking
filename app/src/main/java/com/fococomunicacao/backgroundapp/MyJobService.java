package com.fococomunicacao.backgroundapp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Vibrator;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrador on 21/12/2015.
 */
public class MyJobService extends JobService {


    public MyJobService(){

    }

    @Override
    public boolean onStartJob(JobParameters Params){
        //Toast.makeText(this, "onStartJob()", Toast.LENGTH_SHORT).show();
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);

        Date datahoraAtual = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datahoraAtualSting = format.format(datahoraAtual);

        Localizacao localizacao = new Localizacao(getBaseContext());
        BancoController crud = new BancoController(getBaseContext());

        String latitudeString = localizacao.getLatitude();
        String longitudeString = localizacao.getLongitude();
        String datahoraString = datahoraAtualSting;
        String resultado;

        resultado = crud.insereDado(latitudeString,longitudeString,datahoraString);
        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters Params){
        Toast.makeText(this, "onStopJob()", Toast.LENGTH_SHORT).show();
        return false;
    }



}
