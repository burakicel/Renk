package com.burakicel.renk;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        //Get Window Dimensions
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        //ImageDetail - Universal Image Loader
        int position = getIntent().getIntExtra("position", -1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setMaxWidth((int)dpWidth);
        ImageLoader imageLoader = ImageLoader.getInstance();
        //set options for image display
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank)
                .showImageForEmptyUri(R.drawable.blank)
                .showImageOnFail(R.drawable.blank)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.displayImage(GalleryActivity.mThumbIds[position] ,imageView, options);
    }
}
