package com.google.codelab.mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Bitmap bitmap;
    DisplayAdpater adapter;
    Intent intent;
    private ImageButton cancel;
    private Button proceed;

    List<String> urlList=new ArrayList<>();
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.list_toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        String baseUrl="https://safeer-media.s3.ap-south-1.amazonaws.com/madhul/someAPI.json";

        proceed=findViewById(R.id.proceedButton);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent!=null){
                    startActivity(intent);
                }
            }
        });

        final String loggedInMail= Objects.requireNonNull(getIntent().getExtras()).getString("Mail");
        final String loggedInPwd=getIntent().getExtras().getString("Pwd");
        final String loggedInFname=getIntent().getExtras().getString("Fname");
        final String loggedInLname=getIntent().getExtras().getString("Lname");

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://www.google.com";
int i=0;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String data[]=response.split(",");
                        for(int i=10;i<data.length-20;i=i+10){
                            String str=data[i]+"\n";
//                            String fin=str.replace("")
                            if(str.contains("content")){
                                str=str.replace("{\"content\": \"","");
                                String fin=str.replace("\"","");
                                urlList.add(fin);
                            }
                        }
                        adapter=new DisplayAdpater(urlList, new DisplayAdpater.OnClickAdapterListener() {
                            @Override
                            public void OnClick(View v, int position) {

                                intent=new Intent(ListActivity.this,MainActivity.class);
                                intent.putExtra("Mail",loggedInMail);
                                intent.putExtra("Pwd",loggedInPwd);
                                intent.putExtra("Fname",loggedInFname);
                                intent.putExtra("Lname",loggedInLname);
                                intent.putExtra("Url",urlList.get(position));

                                intent.putExtra("bitmap",bitmap);

                                //startActivity(intent);

                                Toast.makeText(ListActivity.this, "Sab Changa si", Toast.LENGTH_SHORT).show();
                            }
                        },ListActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(ListActivity.this,2));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Toast.makeText(ListActivity.this, "nahi hua", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}