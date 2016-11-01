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

/**
 * Created by naman on 01/11/16.
 */
public class FibonacciActivity extends AppCompatActivity {

    TextView statusJava, numberJava;
    FloatingActionButton fabStart;

    static TextView statusNative, numberNative;
    static long nativeStartTime;
    static Handler uihandler;

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
        statusJava = (TextView) findViewById(R.id.status_java);
        statusNative = (TextView) findViewById(R.id.status_native);

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExecution(50);
            }
        });
    }

    private void startExecution(final int n) {
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
                nativeStartTime = System.currentTimeMillis();
                startFibNative(n);
            }
        }).start();

    }

    private void startFibJava(int n) {
        final long startTime = System.currentTimeMillis();
        int i = 0, c;
        for (c = 1; c <= n; c++) {
            final int fibonacci = fibJava(i);
            final int numberPrinted = i;
            i++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    numberJava.setText(String.valueOf(fibonacci));
                    statusJava.setText("Numbers printed - " + String.valueOf(numberPrinted)
                            + " Time taken - " + String.valueOf(System.currentTimeMillis() - startTime) + " ms");
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
                numberNative.setText(String.valueOf(number));
                statusNative.setText("Numbers printed - " + String.valueOf(numberPrinted)
                        + " Time taken - " + String.valueOf(System.currentTimeMillis() - nativeStartTime) + " ms");
            }
        });

    }

}
