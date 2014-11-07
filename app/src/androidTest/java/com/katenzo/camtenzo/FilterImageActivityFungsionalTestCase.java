package com.katenzo.camtenzo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by daori on 10/6/14.
 */
public class FilterImageActivityFungsionalTestCase extends ActivityInstrumentationTestCase2<FilterImageActivity>{

    private FilterImageActivity activity;
    private ImageView imageContainer;
    private Button filterGrayScale;
    private Button buttonSend;
    private Button filterSephia;
    private Intent filterIntentImage;
    private Bitmap dummyImage;
    private ImageView imageOverlay;
    private Button filterOriginal;
    private Uri uriMessage;
    private Bitmap bitmapOriginal;
    private Bitmap bitmapFiltered;

    public FilterImageActivityFungsionalTestCase(String name) {
        super(FilterImageActivity.class);
        setName(name);


    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        uriMessage = Uri.parse("android.resource://drawable-hdpi/meme.jpg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(CameraActivity.INTENT_NAME, uriMessage);
        intent.setDataAndType(uriMessage, "image/*");
        setActivityIntent(intent);
        activity = getActivity();
        assertNotNull(activity);

        imageContainer = (ImageView) activity.findViewById(R.id.image_container);
        assertNotNull(imageContainer);

        imageOverlay = (ImageView) activity.findViewById(R.id.imageViewOverlay);
        assertNotNull(imageOverlay);

        filterGrayScale = (Button) activity.findViewById(R.id.filter_gray_scale);
        assertNotNull(filterGrayScale);

        filterSephia = (Button) activity.findViewById(R.id.filter_sephia);
        assertNotNull(filterSephia);

        filterOriginal = (Button) activity.findViewById(R.id.filter_original);
        assertNotNull(filterOriginal);

        buttonSend = (Button) activity.findViewById(R.id.button_send);
        assertNotNull(buttonSend);

        //dummyImage =((BitmapDrawable)imageContainer.getDrawable()).getBitmap();

//        filterGrayScale.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                FilterImage.convertToGrayScale(dummyImage);
//            }
//        });
    }

    @UiThreadTest
    public void testImageContainerShouldSetUsingIntentFromActivity() throws Exception {
        imageContainer.setImageBitmap(bitmapOriginal);
        assertNotNull(imageContainer.getDrawable());
    }

    @UiThreadTest
    public void testButtonFilterGrayscaleClickShouldChangeBitmapOriginal() throws Exception {
        bitmapOriginal = activity.getThumbnailImage(activity.getOriginalImageFromIntent());
        bitmapFiltered = bitmapOriginal;
        filterGrayScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setImageBitmap(FilterImage.convertToGrayScale(bitmapFiltered));
            }
        });

        assertFalse("Bitmap not change to grayscale", ((BitmapDrawable)imageContainer.getDrawable()).getBitmap().sameAs(bitmapOriginal));
    }

    private void initializeBitmap(){
        bitmapOriginal = activity.getThumbnailImage(activity.getOriginalImageFromIntent());
        bitmapFiltered = bitmapOriginal;
    }

    @SmallTest
    public void testComponentOnScreen() throws Exception {
        final View container = activity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(container, imageContainer);
        ViewAsserts.assertOnScreen(container, imageOverlay);
        ViewAsserts.assertOnScreen(container, filterGrayScale);
        ViewAsserts.assertOnScreen(container, filterSephia);
        ViewAsserts.assertOnScreen(container, filterOriginal);
        ViewAsserts.assertOnScreen(container, buttonSend);
    }

    @SmallTest
    public void testJustification() throws Exception {
        final int expectedButtonFilter = Gravity.LEFT|Gravity.CENTER_VERTICAL;
        final int expectedButtonSend = Gravity.RIGHT|Gravity.CENTER_VERTICAL;

        assertEquals(expectedButtonFilter, filterGrayScale.getGravity());
        assertEquals(expectedButtonFilter, filterSephia.getGravity());
        assertEquals(expectedButtonSend, buttonSend.getGravity());
    }

}
