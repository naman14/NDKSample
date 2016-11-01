package com.naman14.ndksample.blur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman14.ndksample.R;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by naman on 01/11/16.
 */
public class BlurComparisonActivity extends AppCompatActivity {

    FloatingActionButton fabBlur;
    TextView textJava, textNative;
    Bitmap bitmap;
    ImageView imgPick, imgJava, imgNative;

    private static final int REQUEST_PICK_IMAGE = 888;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_comparison);

        fabBlur = (FloatingActionButton) findViewById(R.id.fabBlur);
        textJava = (TextView) findViewById(R.id.txt_blur_java);
        textNative = (TextView) findViewById(R.id.txt_blur_native);
        imgPick = (ImageView) findViewById(R.id.pickImage);
        imgJava = (ImageView) findViewById(R.id.blurJava);
        imgNative = (ImageView) findViewById(R.id.blurNative);

        fabBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null)
                    startBlur();
            }
        });
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                if (inputStream != null) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                    imgPick.setImageBitmap(bitmap);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBlur() {
        final StackBlurManager blurManager = new StackBlurManager(bitmap);
        final long initialTime = System.currentTimeMillis();

        //making sure both async tasks run parallely

        //start java blur
        new AsyncTask<Integer, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Integer... integers) {
                return blurManager.process(integers[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imgJava.setImageBitmap(bitmap);
                textJava.setText("Java \n" + String.valueOf(System.currentTimeMillis() - initialTime + " ms"));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 30);

        //start native blur
        new AsyncTask<Integer, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Integer... integers) {
                return blurManager.processNatively(integers[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imgNative.setImageBitmap(bitmap);
                textNative.setText("Native \n" + String.valueOf(System.currentTimeMillis() - initialTime + " ms"));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 30);
    }
}
