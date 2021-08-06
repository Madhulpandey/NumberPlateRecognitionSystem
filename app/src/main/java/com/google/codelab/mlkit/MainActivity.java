// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codelab.mlkit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
//import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.takusemba.cropme.CropView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
//public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";
    private CropView mImageView;
    private TextView profFname;
    private TextView profEmail;
    private TextView profLname;
    private Button logoutBtn;
    private Button mTextButton;
    private Button mDetailsBtn;
    //private Button cropButton;
    private Bitmap mSelectedImage;
//    private GraphicOverlay mGraphicOverlay;
    // Max width (portrait mode)
    private Integer mImageMaxWidth;
    // Max height (portrait mode)
    private Integer mImageMaxHeight;
    private Uri uri;
    DatabaseHandler db;
    String extractedStr;
    String imgUrl;
    Bitmap bmap;

    /**
     * Number of results to show in the UI.
     */
    private static final int RESULTS_TO_SHOW = 3;
    /**
     * Dimensions of inputs.
     */
    DrawerLayout drawer;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_toolbar);

        profEmail=findViewById(R.id.profileEmail);
        profFname=findViewById(R.id.profileFname);
        profLname=findViewById(R.id.profileLname);
        drawer=findViewById(R.id.drawer_layout);
        mImageView = findViewById(R.id.image_view);
        mTextButton = findViewById(R.id.button_text);
        mDetailsBtn = findViewById(R.id.button_details);

        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BMAP3", "onCreate: "+bmap);
                runTextRecognition();
            }
        });
        logoutBtn=findViewById(R.id.LogOutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
            }
        });
        //TODO:remove face button from layout also
        mDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(extractedStr.isEmpty()){
                Toast.makeText(MainActivity.this, "SInvalid Image", Toast.LENGTH_SHORT).show();
            }

                Intent intent=new Intent(MainActivity.this,OffenderActivity.class);
                intent.putExtra("Extracted String",extractedStr);
                startActivity(intent);
            }
        });

        String loggedInMail= Objects.requireNonNull(getIntent().getExtras()).getString("Mail");
        String loggedInPwd=getIntent().getExtras().getString("Pwd");
        String loggedInFname=getIntent().getExtras().getString("Fname");
        String loggedInLname=getIntent().getExtras().getString("Lname");
        imgUrl=getIntent().getExtras().getString("Url");
//        bmap=getIntent().getParcelableExtra("bitmap");

        new GetImageFromUrl().execute(imgUrl);

        if(bmap==null){
            Log.d("BMAP", "onCreate: "+bmap);
        }

        assert loggedInLname != null;
        profLname.setText(loggedInLname.toUpperCase());
        assert loggedInFname != null;
        profFname.setText(loggedInFname.toUpperCase());
        profEmail.setText(loggedInMail);

    }

    public class GetImageFromUrl extends AsyncTask<String,Void,Bitmap> {
    //ImageView imgV;

//    public GetImageFromUrl(ImageView imgV){
//      //  this.imgV=imgV;
//    }
public GetImageFromUrl(){
    //  this.imgV=imgV;
}
        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay=strings[0];
            //imgV=null;
            //bmap=null;
            try{
                InputStream srt=new URL(urlDisplay).openStream();
                bmap=BitmapFactory.decodeStream(srt);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("BMAP1", "doInBackground: "+bmap);
            return bmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.d("BMAP", "doInBackground: "+bitmap);

            //mImageView.setImageBitmap(bitmap);
            mImageView.setBitmap(bitmap);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawer);
    }

    public void ClickMenu(View view){
        openDrawer(drawer);
    }
    private static void openDrawer(DrawerLayout drawer){
        drawer.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
     closeDrawer(drawer);
    }

    private static void closeDrawer(DrawerLayout drawer){
        if(drawer.isDrawerOpen(GravityCompat.START)){
        drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void Clickrefresh(){
        Intent intent=getIntent();
        finish();
        startActivity(intent);
    }

    private void runTextRecognition() {
        // Replace with code from the codelab to run text recognition.
        //InputImage image=InputImage.fromBitmap(mSelectedImage,0);
        InputImage image=InputImage.fromBitmap(bmap,0);
        TextRecognizer recognizer = TextRecognition.getClient();

        mTextButton.setEnabled(false);
        recognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text text) {
                        mTextButton.setEnabled(true);
                        processTextRecognitionResult(text);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mTextButton.setEnabled(true);
                        e.printStackTrace();
                    }
                });
    }

    private void processTextRecognitionResult(Text texts) {
        // Replace with code from the codelab to process the text recognition result.
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        if(blocks.size()==0){
            Toast.makeText(this, "No Number Plate Detected", Toast.LENGTH_SHORT).show();
        }
        extractedStr = texts.getText();
        extractedStr=extractInt(extractedStr);
        Toast.makeText(this,extractedStr, Toast.LENGTH_SHORT).show();

    }


    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            mImageMaxWidth = mImageView.getWidth();
        }

        return mImageMaxWidth;
    }


    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            mImageMaxHeight =
                    mImageView.getHeight();
        }

        return mImageMaxHeight;
    }


    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }



    private Bitmap getBitmapfromUrl(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void pickImageFromGallery() {

//        Intent intent=new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent,IMAGE_PICK_CODE);

        CropImage.startPickImageActivity(MainActivity.this);
    }

    static String extractInt(String str)
    {

        str = str.replaceAll("[^\\d]", " ");
        str = str.trim();
        str = str.replaceAll(" +", " ");

        if (str.equals(""))
            return "-1";

        return str;
    }

    private void startCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Do nothing
//    }

}
