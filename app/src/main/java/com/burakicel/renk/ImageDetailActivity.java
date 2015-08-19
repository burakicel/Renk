package com.burakicel.renk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        int position = getIntent().getIntExtra("position", -1);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setMaxHeight(600);
        imageView.setMaxWidth(600);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://"
                + GalleryActivity.mThumbIds[position]
                ,imageView);

    }
}
