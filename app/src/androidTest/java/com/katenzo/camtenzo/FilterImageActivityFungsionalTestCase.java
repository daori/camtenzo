package com.katenzo.camtenzo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;
import android.view.MotionEvent;
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
    private ImageView imageExported;

    public FilterImageActivityFungsionalTestCase(String name) {
        super(FilterImageActivity.class);
        setName(name);


    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        //Dummy File di ambil dari storage hasil foto
        uriMessage = Uri.parse("file:///storage/emulated/0/camtenzo/1415674970354.jpg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(CameraActivity.INTENT_NAME, uriMessage);
        intent.setDataAndType(uriMessage, "image/*");
        setActivityIntent(intent);
        activity = getActivity();


        bitmapOriginal = activity.getThumbnailImage(activity.getOriginalImageFromIntent());

        imageContainer = (ImageView) activity.findViewById(R.id.image_container);
        imageOverlay = (ImageView) activity.findViewById(R.id.imageViewOverlay);
        filterGrayScale = (Button) activity.findViewById(R.id.filter_gray_scale);
        filterSephia = (Button) activity.findViewById(R.id.filter_sephia);
        filterOriginal = (Button) activity.findViewById(R.id.filter_original);
        buttonSend = (Button) activity.findViewById(R.id.button_send);
//        filterGrayScale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FilterImage.convertToGrayScale(activity.getThumbnailImage(uriMessage));
//                imageContainer.setImageBitmap(FilterImage.convertToGrayScale(bitmapOriginal));
//                filterGrayScale.setPressed(true);
//                filterSephia.setPressed(false);
//                filterOriginal.setPressed(false);
//            }
//        });
    }

    @SmallTest
    public void testPrecondition() throws Exception {
        /* Image yang di ambil dari File (media storage) */
        assertNotNull(activity);
        assertNotNull(bitmapOriginal);
        assertNotNull(imageContainer);
        assertNotNull(imageContainer);
        assertNotNull(imageOverlay);
        assertNotNull(filterGrayScale);
        assertNotNull(filterSephia);
        assertNotNull(filterOriginal);
        assertNotNull(buttonSend);
    }

    @UiThreadTest
    public void testImageContainerShouldSetUsingIntentFromActivity() throws Exception {
        imageContainer.setImageBitmap(bitmapOriginal);
        assertEquals(bitmapOriginal, ((BitmapDrawable) imageContainer.getDrawable()).getBitmap());
    }

    @UiThreadTest
    public void testButtonFilterGrayscaleClickShouldChangeBitmapOriginal() throws Exception {
        bitmapOriginal = activity.getThumbnailImage(activity.getOriginalImageFromIntent());
        filterGrayScale.performClick();
        assertFalse("Bitmap not change to grayscale", ((BitmapDrawable) imageContainer.getDrawable()).getBitmap().sameAs(bitmapOriginal));
    }

    @UiThreadTest
    public void testImageShouldNotEmptyFromIntent() throws Exception{
        bitmapOriginal = activity.getThumbnailImage(activity.getOriginalImageFromIntent());
        assertNotNull(bitmapOriginal);
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
        final int expectedButtonReset = Gravity.LEFT|Gravity.CENTER;

        assertEquals(expectedButtonFilter, filterGrayScale.getGravity());
        assertEquals(expectedButtonFilter, filterSephia.getGravity());
        assertEquals(expectedButtonReset, filterOriginal.getGravity());
        assertEquals(expectedButtonSend, buttonSend.getGravity());
    }

    @UiThreadTest
    public void testFilterGrayscaleOnTouchShouldChangeState() throws Exception {
        final boolean expected = true;
        filterGrayScale.performClick();
        assertEquals(expected, filterGrayScale.isPressed());
    }

    @UiThreadTest
    public void testFilterSephiaOnTouchShouldChangeState() throws Exception{
        final boolean expected = true;
        filterSephia.performClick();
        assertEquals(expected, filterSephia.isPressed());
    }

    @UiThreadTest
    public void testFilterOriginalOnTouchShouldChangeState() throws Exception{
        final boolean expected = true;
        filterOriginal.performClick();
        assertEquals(expected, filterOriginal.isPressed());
    }

    @MediumTest
    public void testButtonSendShouldPostToUrlUsingHttpPost() throws Exception{
        fail("Not Implement yet");
    }


}
