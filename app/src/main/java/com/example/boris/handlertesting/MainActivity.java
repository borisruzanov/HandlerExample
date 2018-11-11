package com.example.boris.handlertesting;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG = "log";

    Handler handler;
    TextView tvInfo;
    Button btnStart, btnTest;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setEnabled(false);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 10; i++){
                            downloadFile();
                            //Here we are sending message to handler in sendEmptyMessage
                            handler.sendEmptyMessage(i);
                            Log.d(LOG, "in Runnable i = " + i);
                        }
                    }
                });
                thread.start();
            }
        });
        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "Click");

            }
        });
        handler = new Handler(){
            public void handleMessage(android.os.Message msg){
                //Triggering with message from the thread
                tvInfo.setText("Закачано файлов " + msg.what);
                if (msg.what == 10) btnStart.setEnabled(true);
            }
        };
    }

    void downloadFile() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
