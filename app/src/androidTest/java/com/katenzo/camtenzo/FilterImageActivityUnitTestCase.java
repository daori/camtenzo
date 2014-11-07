package com.katenzo.camtenzo;

import android.content.Intent;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageView;

/**
 * Created by daori on 10/8/14.
 */
public class FilterImageActivityUnitTestCase extends ActivityUnitTestCase<FilterImageActivity> {

    private Uri uriMessage = null;

    private Intent intent;

    private FilterImageActivity activity;
    private ImageView imageContainer;


    public FilterImageActivityUnitTestCase(String name) {
        super(FilterImageActivity.class);
        this.setName(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        uriMessage = Uri.parse("android.resource://drawable-hdpi/meme.jpg");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(CameraActivity.INTENT_NAME, uriMessage);
        intent.setDataAndType(uriMessage, "image/*");

        startActivity(intent, null, null);
        activity = getActivity();

        imageContainer = (ImageView) activity.findViewById(R.id.image_container);
        imageContainer.setImageBitmap(activity.getThumbnailImage(uriMessage));
        assertNotNull(imageContainer);

    }

    @SmallTest
    public void testIntentShouldNotEmpty() throws Exception {
        Intent intent = activity.getIntent();
        assertNotNull("Intent was NULL", intent);

        Uri message = intent.getData();
        assertEquals("Incorrect uri passed via Intent",uriMessage, message);
    }

    @SmallTest
    public void testFilterImageActivityShouldHaveSameImageWithProvidedUriFromIntent() throws Exception {
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_container);
        assertNotNull("Image View Cannot null", imageView);

        Intent intent = activity.getIntent();
        final Uri expected = intent.getData();
        assertEquals("Image Uri Not Same", expected, uriMessage);
    }

}
