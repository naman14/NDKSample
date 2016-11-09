package com.naman14.ndksample.lodepng;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naman14.ndksample.R;

/**
 * Created by naman on 09/11/16.
 */

public class PNGInfoActivity extends AppCompatActivity {

    static {
        System.loadLibrary("ndksample");
    }

    private int PICKFILE_REQUEST_CODE = 123;

    TextView info;
    Uri inputUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_png_info);

        info = (TextView) findViewById(R.id.txtInfo);

        Button btnPickFile = (Button) findViewById(R.id.btnPickImage);

        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    public void displayInfo(View view) {
        info.setText(getPngInfo(getRealPathFromURI(inputUri)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String filename = data.getDataString();
                inputUri = data.getData();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public native String getPngInfo(String file);
}
