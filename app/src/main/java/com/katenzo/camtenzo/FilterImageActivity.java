package com.katenzo.camtenzo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FilterImageActivity extends Activity {

    private ImageView image;
    private Button filterGrayscale;
    private Button filterSephia;
    private Bitmap bitmapOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_image);

        Uri imageOriginalUri = getOriginalImageFromIntent();
        bitmapOri = getThumbnailImage(imageOriginalUri);

        image = (ImageView) findViewById(R.id.image_container);
        image.setImageBitmap(bitmapOri);

        filterGrayscale = (Button) findViewById(R.id.filter_gray_scale);

        filterGrayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToGrayScale(bitmapOri));
            }
        });

        filterSephia = (Button) findViewById(R.id.filter_sephia);
        filterSephia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToSephia(bitmapOri,1,10,10,255));
            }
        });

    }

    private Uri getOriginalImageFromIntent() {
        Intent intent = getIntent();
        Uri imageName = intent.getData();
        return imageName;
    }

    private Bitmap getThumbnailImage(Uri uriOriginal){
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uriOriginal, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
