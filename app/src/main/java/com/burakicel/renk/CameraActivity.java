package com.burakicel.renk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.os.Handler;


import java.io.IOException;
import java.util.Date;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.CameraInfo;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import org.opencv.android.Utils;


import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;

import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity implements CvCameraViewListener2,View.OnClickListener {
    private CameraBridgeViewBase mOpenCvCameraView;
    public static final String TAG = "RENK APP TAG:";

    Toast toast = null;
    View settingsButton;
    View menuLayout;
    ArcLayout arcSettings;
    View photoButton;
    boolean photoTake;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);


        photoButton = findViewById(R.id.photo_button);
        photoButton.setOnClickListener(this);

//        settingsButton = findViewById(R.id.settings);
//        menuLayout = findViewById(R.id.menu_layout);
//        arcSettings = (ArcLayout) findViewById(R.id.arc_settings);
//
//        for (int i = 0, size = arcSettings.getChildCount(); i < size; i++) {
//            arcSettings.getChildAt(i).setOnClickListener(this);
//        }
//
//        settingsButton.setOnClickListener(this);
//
//
//        //Icons
//        ImageView icon = new ImageView(this); // Create an icon
//        icon.setImageDrawable(getResources().getDrawable(R.drawable.photo_button));
//        ImageView settingsIcon = new ImageView(this); // Create an icon
//        settingsIcon.setImageDrawable(getResources().getDrawable(R.drawable.settings));
//        ImageView switchIcon = new ImageView(this);
//        switchIcon.setImageDrawable(getResources().getDrawable(R.drawable.switch_button));
//        ImageView flashIcon = new ImageView(this);
//        flashIcon.setImageDrawable(getResources().getDrawable(R.drawable.flash));
//
//
////        FloatingActionButton photoButton = (FloatingActionButton) findViewById(R.id.photo_button);
////        photoButton.setContentView(icon,new FloatingActionButton.LayoutParams(
////                FloatingActionButton.LayoutParams.MATCH_PARENT,
////                FloatingActionButton.LayoutParams.MATCH_PARENT));
////        photoButton.setPosition(5,new FloatingActionButton.LayoutParams(
////                FloatingActionButton.LayoutParams.MATCH_PARENT,
////                FloatingActionButton.LayoutParams.MATCH_PARENT));
////        photoButton.getLayoutParams().height=250;
////        photoButton.getLayoutParams().width=250;
//
////        FloatingActionButton settingsButton = (FloatingActionButton) findViewById(R.id.settings_button);
//
//        //Photo Button
//
//        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
//                .setPosition(5)
//                .build();
//        actionButton.getLayoutParams().height=250;
//        actionButton.getLayoutParams().width=250;
//
//
//        FloatingActionButton settingsButton = new FloatingActionButton.Builder(this)
//                .setContentView(settingsIcon)
//                .setPosition(2)
//                .build();
//        settingsButton.getLayoutParams().height=130;
//        settingsButton.getLayoutParams().width=130;
//        settingsButton.setPadding(-15,-15,-15,-15);
//
//        //Switch Button
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//
//        FloatingActionMenu settingsMenu = new FloatingActionMenu.Builder(this)
//                .addSubActionView(itemBuilder.setContentView(flashIcon).build())
//                .addSubActionView(itemBuilder.setContentView(switchIcon).build())
//                .attachTo(settingsButton)
//                .build();
//
////        //Settings Button
////        FloatingActionButton settingsButton = new FloatingActionButton.Builder(this)
////                .setContentView(settingsIcon)
////                .setPosition(2)
////                .build();
////        settingsButton.getLayoutParams().height=150;
////        settingsButton.getLayoutParams().width=150;


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.photo_button){
            String text = "Picture Taken";
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();
            photoTake = true;
        }

//        if (v.getId() == R.id.settings) {
//            onSettingsClick(v);
//            return;
//        }
//
//        if (v instanceof Button) {
//            showToast((Button) v);
//        }

    }

    private void showToast(Button btn) {
        if (toast != null) {
            toast.cancel();
        }

        String text = "Clicked: " + btn.getText();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

    }

    private void onSettingsClick(View v) {
        if (v.isSelected()) {
            hideMenu();
        } else {
            showMenu();
        }
        v.setSelected(!v.isSelected());
    }

    @SuppressWarnings("NewApi")
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcSettings.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcSettings.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcSettings.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcSettings.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = settingsButton.getX() - item.getX();
        float dy = settingsButton.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = settingsButton.getX() - item.getX();
        float dy = settingsButton.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        if (photoTake){

            Bitmap toSave = Bitmap.createBitmap(inputFrame.rgba().cols(), inputFrame.rgba().rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(inputFrame.rgba(),toSave);

            String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
            OutputStream fOut = null;
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            File filename = new File(path, currentDateTimeString+".jpg"); // the File to save to
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filename);
                toSave.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            photoTake = false;
        }
        return inputFrame.rgba();
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
    }


}
