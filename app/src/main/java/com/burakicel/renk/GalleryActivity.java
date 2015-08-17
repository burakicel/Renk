package com.burakicel.renk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Environment;
import java.io.File;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class GalleryActivity extends ActionBarActivity {
    private float dpHeight;
    private float dpWidth;
    private File[] imageFiles;
    private Bitmap[] bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if (!externalStorageAvailable()){
        }
        else{
            File imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFiles = imageDir.listFiles();
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this));
            gridview.setColumnWidth((int)(dpWidth/1.5-15));

        }
    }

    protected void onDestroy(){
        super.onDestroy();
    }
    private boolean externalStorageAvailable() {
        return
                Environment.MEDIA_MOUNTED
                        .equals(Environment.getExternalStorageState());
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams((int)(dpWidth/1-20), (int)(dpWidth/1.5-20)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        Bitmap[] mThumbIds = convertFileArraytoBitmapArray(imageFiles);
    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
