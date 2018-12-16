package com.example.deepak.jobschedular;

import android.app.job.JobParameters;
import android.util.Log;

public class JobService extends android.app.job.JobService {
    private boolean mIsJobCancelled = false;
    private static final String TAG = JobService.class.getName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "job Started");
        doInBackGround(params);
        return true;

    }

    private void doInBackGround(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run " + i);
                    if (mIsJobCancelled)
                        return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job cancelled before completion");
        mIsJobCancelled = true;
        return true;
    }
}
