package com.enel_locdefalta;

import android.app.job.JobParameters;
import android.util.Log;

public class JobService extends android.app.job.JobService {
    private static final String TAG="JobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG,"Job INICIADO..");
        doBackgroundWor(params);



        return true;
    }


    private void doBackgroundWor(final JobParameters parameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10;i++){
                    Log.e(TAG, "run "+i);
                    if (jobCancelled){
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                Log.e(TAG,"Job FINALIZADO");
                jobFinished(parameters, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.d(TAG, "Job CANCELADO before completion ");
        jobCancelled = true;
        return true;
    }
}
