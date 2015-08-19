package com.burakicel.renk;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Environment;
import java.io.File;
import java.net.URI;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GalleryActivity extends ActionBarActivity {
    private float dpHeight;
    private float dpWidth;
    private static File[] imageFiles;
    private Bitmap[] bitmapList;
    DisplayImageOptions options;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        //set options for image display
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank)
                .showImageForEmptyUri(R.drawable.blank)
                .showImageOnFail(R.drawable.blank)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        if (!externalStorageAvailable()){
        }
        else{
            final GridView gridview = (GridView) findViewById(R.id.gridview);
            File imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFiles = imageDir.listFiles();
            mThumbIds = decodeFiles(imageFiles);
            gridview.setAdapter(new ImageAdapter());
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    gridview.getAdapter().getItem(position);
                    Intent intent = new Intent(GalleryActivity.this, ImageDetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
//            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//            dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//            dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//
//            GridView gridview = (GridView) findViewById(R.id.gridview);
//            gridview.setAdapter(new ImageAdapter(this));
//            gridview.setColumnWidth((int)(dpWidth/1.5-15));

        }
    }

    static class ViewHolder {
        ImageView imageView;
    }

    //    our custom adapter
    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View view = convertView;
            final ViewHolder gridViewImageHolder;
//            check to see if we have a view
            if (convertView == null) {
//                no view - so create a new one
                view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
                gridViewImageHolder = new ViewHolder();
                gridViewImageHolder.imageView = (ImageView) view.findViewById(R.id.image);
                gridViewImageHolder.imageView.setMaxHeight(80);
                gridViewImageHolder.imageView.setMaxWidth(80);
                view.setTag(gridViewImageHolder);
            } else {
//                we've got a view
                gridViewImageHolder = (ViewHolder) view.getTag();
            }
            imageLoader.displayImage(mThumbIds[position]
                    , gridViewImageHolder.imageView
                    , options);

            return view;
        }
    }

    static String[] mThumbIds;

    protected void onDestroy(){
        super.onDestroy();
    }

    private boolean externalStorageAvailable() {
        return
                Environment.MEDIA_MOUNTED
                        .equals(Environment.getExternalStorageState());
    }

//    public class ImageAdapter extends BaseAdapter {
//        private Context mContext;
//
//        public ImageAdapter(Context c) {
//            mContext = c;
//        }
//
//        public int getCount() {
//            return mThumbIds.length;
//        }
//
//        public Object getItem(int position) {
//            return null;
//        }
//
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        // create a new ImageView for each item referenced by the Adapter
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView;
//            if (convertView == null) {
//                // if it's not recycled, initialize some attributes
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams((int)(dpWidth/1-20), (int)(dpWidth/1.5-20)));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            } else {
//                imageView = (ImageView) convertView;
//            }
//
//            imageView.setImageBitmap(mThumbIds[position]);
//            return imageView;
//        }
//
//        // references to our images
//        Bitmap[] mThumbIds = convertFileArraytoBitmapArray(imageFiles);
//    }

    private Bitmap[] convertFileArraytoBitmapArray(File[] files){
        int length = files.length;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 10; // 1/4
        opts.inPurgeable = true;
        Bitmap[] output = new Bitmap[length];
        for (int i=0;i<length;i++){
            output[i] = BitmapFactory.decodeFile(imageFiles[i].getAbsolutePath(),opts);
        }
        bitmapList = output;
        return output;
    }

    private static String[] decodeFiles(File[] files){
        int len = files.length;
        String uri;
        String decoded;
        String[] output = new String[len];

        for(int i=0; i<len;i++){
            uri = Uri.fromFile(files[i]).toString();
            decoded = Uri.decode(uri);
            output[i] = decoded;
        }
        return output;
    }

    private void recycleBitmap(Bitmap[] bitmapList){
        int length = bitmapList.length;
        for (int i=0;i<length;i++){
            bitmapList[i].recycle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;}

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
