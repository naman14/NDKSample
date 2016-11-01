package com.naman14.ndksample.fibonacci;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.naman14.ndksample.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by naman on 01/11/16.
 */
public class FibonacciActivity extends AppCompatActivity {

    TextView statusJavaTime, statusJavaNumbers, numberJava;
    FloatingActionButton fabStart;

    static TextView statusNativeTime, statusNativeNumbers, numberNative;
    static Handler uihandler;

    protected Timer timeTicker = new Timer("Ticker");
    private Handler timerHandler = new Handler();
    long elapsedTimeJava, elapsedTimeNative;

    static int num;

    static {
        System.loadLibrary("ndksample");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        fabStart = (FloatingActionButton) findViewById(R.id.fabStart);
        numberJava = (TextView) findViewById(R.id.txt_number_java);
        numberNative = (TextView) findViewById(R.id.txt_number_native);
        statusJavaTime = (TextView) findViewById(R.id.status_java_time);
        statusJavaNumbers = (TextView) findViewById(R.id.status_java_numbers);

        statusNativeTime = (TextView) findViewById(R.id.status_native_time);
        statusNativeNumbers = (TextView) findViewById(R.id.status_native_numbers);

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = 45;
                startExecution(num + 1);
            }
        });
    }


    private void startExecution(final int n) {
        timerHandler.post(new Runnable() {
            @Override
            public void run() {

                boolean javaCompleted = statusJavaTime.getText().toString().contains("Completed");
                boolean nativeCompleted = statusNativeTime.getText().toString().contains("Completed");

                if (!javaCompleted) {
                    statusJavaTime.setText("Time taken - " + String.valueOf(elapsedTimeJava) + " s");
                    elapsedTimeJava += 1;
                }
                if (!nativeCompleted) {
                    statusNativeTime.setText("Time taken - " + String.valueOf(elapsedTimeNative) + " s");
                    elapsedTimeNative += 1;
                }

                if (!javaCompleted || !nativeCompleted)
                    timerHandler.postDelayed(this, 1000);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                startFibJava(n);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                uihandler = new Handler(Looper.getMainLooper());
                startFibNative(n);
            }
        }).start();

    }

    private void startFibJava(int n) {
        int i = 0, c;
        for (c = 1; c <= n; c++) {
            final int fibonacci = fibJava(i);
            final int numberPrinted = i;
            i++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (num == numberPrinted) {
                        statusJavaTime.setText(statusJavaTime.getText().toString() + " : Completed");
                    }
                    numberJava.setText(String.valueOf(fibonacci));
                    statusJavaNumbers.setText("Numbers printed - " + String.valueOf(numberPrinted));
                }
            });
        }
    }

    private int fibJava(int n) {
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else
            return (fibJava(n - 1) + fibJava(n - 2));
    }


    private static native void startFibNative(int n);

    public static void numberRecieved(final int number, final int numberPrinted) {
        uihandler.post(new Runnable() {
            @Override
            public void run() {
                if (num == numberPrinted) {
                    statusNativeTime.setText(statusNativeTime.getText().toString() + " : Completed");
                }
                numberNative.setText(String.valueOf(number));
                statusNativeNumbers.setText("Numbers printed - " + String.valueOf(numberPrinted));
            }
        });

    }

}
