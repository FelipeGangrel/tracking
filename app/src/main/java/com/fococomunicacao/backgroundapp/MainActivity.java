package com.fococomunicacao.backgroundapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button btnStartJob, btnCancelJobs, btnAtualizaLista;

    private ListView lista;

    JobScheduler jobScheduler;
    private static final int MYJOBID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chronometer = (Chronometer)findViewById(R.id.chronometer);
        btnStartJob = (Button)findViewById(R.id.startjob);
        btnCancelJobs = (Button)findViewById(R.id.canceljobs);
        btnAtualizaLista = (Button)findViewById(R.id.atualizaLista);

        jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);


        btnAtualizaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BancoController crud = new BancoController(getBaseContext());
                Cursor cursor = crud.carregaDados();

                String[] nomeCampos = new String[] {
                        Banco.ID,
                        Banco.LATITUDE,
                        Banco.LONGITUDE,
                        Banco.DATAHORA};

                int[] idViews = new int[] {
                        R.id.id,
                        R.id.latitude,
                        R.id.longitude,
                        R.id.datahora};

                SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                        R.layout.row,cursor,nomeCampos,idViews,0);
                lista = (ListView)findViewById(R.id.listView);
                lista.setAdapter(adaptador);

            }
        });


        btnStartJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

                ComponentName jobService = new ComponentName(getPackageName(), MyJobService.class.getName());
                JobInfo jobInfo = new JobInfo.Builder(MYJOBID, jobService)
                        //.setPersisted(true)
                        .setPeriodic(20000)
                        .setRequiresCharging(false)
                        .setRequiresDeviceIdle(false)
                        .build();

                int jobId = jobScheduler.schedule(jobInfo);

                if (jobId>0){
                    Toast.makeText(MainActivity.this, "Job agendado: " + jobId, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "FALLHA: " + jobId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                jobScheduler.cancelAll();
            }
        });

    }
}
