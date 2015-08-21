package com.burakicel.renk;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Environment;
import java.io.File;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GalleryActivity extends AppCompatActivity {

    private static File[] imageFiles;
    DisplayImageOptions options;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Universal Image Loader Setup
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
        if (externalStorageAvailable()){
            final GridView gridview = (GridView) findViewById(R.id.gridview);

            //Getting Pictures from External File (Open to every application)
            File imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFiles = imageDir.listFiles();
            mThumbIds = decodeFiles(imageFiles);

            //Image Adapter
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

    //Picture Array
    static String[] mThumbIds;

    protected void onDestroy(){
        super.onDestroy();
    }

    //Checks whether external storage is available or not
    private boolean externalStorageAvailable() {
        return
                Environment.MEDIA_MOUNTED
                        .equals(Environment.getExternalStorageState());
    }

    //Takes files and decodes them for Image Loader
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
