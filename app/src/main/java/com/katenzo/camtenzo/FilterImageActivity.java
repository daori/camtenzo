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
    private Bitmap bitmapFiltered;
    private Button filterReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_image);

        bitmapOri = getThumbnailImage(getOriginalImageFromIntent());
        bitmapFiltered = bitmapOri;

        image = (ImageView) findViewById(R.id.image_container);
        image.setImageBitmap(bitmapFiltered);

        filterGrayscale = (Button) findViewById(R.id.filter_gray_scale);
        filterGrayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToGrayScale(bitmapFiltered));
                filterGrayscale.setPressed(true);
                filterReset.setPressed(false);
                filterSephia.setPressed(false);
            }
        });

        filterSephia = (Button) findViewById(R.id.filter_sephia);
        filterSephia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToSephia(bitmapFiltered,1,10,10,255));
                filterSephia.setPressed(true);
                filterGrayscale.setPressed(false);
                filterReset.setPressed(false);
            }
        });

        filterReset = (Button) findViewById(R.id.filter_original);
        filterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(bitmapOri);
                filterReset.setPressed(true);
                filterGrayscale.setPressed(false);
                filterSephia.setPressed(false);
            }
        });

    }

    protected Uri getOriginalImageFromIntent() {
        Intent intent = getIntent();
        Uri imageUri = intent.getData();
        return imageUri;
    }

    protected Bitmap getThumbnailImage(Uri uriOriginal){
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uriOriginal, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor,null, options);
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
