package com.burakicel.renk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.content.Intent;
import android.os.Handler;


public class MainActivity extends Activity implements View.OnClickListener {

    private ImageButton buttonImage;
    private Button buttonGallery;
    private Handler buttonHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Page Views
        buttonImage = (ImageButton)findViewById(R.id.photo_button);
        buttonGallery = (Button)findViewById(R.id.gallery_button);
        buttonImage.setOnClickListener(this);
        buttonGallery.setOnClickListener(this);
    }

    //Tracking Button Clicks
    public void onClick(View v){

        switch(v.getId()){
            //Photo Button
            case R.id.photo_button:
                buttonImage.setImageResource(R.drawable.photo_button_click);
                buttonHandler.postDelayed(new Runnable() {
                    public void run() {
                        buttonImage.setImageResource(R.drawable.photo_button);
                    }
                }, 100);
                Intent a;
                a = new Intent(this, CameraActivity.class);
                startActivity(a);
                break;
            //Gallery Button
            case R.id.gallery_button:
                buttonGallery.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                buttonHandler.postDelayed(new Runnable() {
                    public void run() {
                        buttonGallery.setBackgroundColor(getResources().getColor(R.color.renk_blue_gallery_button));
                    }
                }, 100);
                Intent i;
                i = new Intent(this, GalleryActivity.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
