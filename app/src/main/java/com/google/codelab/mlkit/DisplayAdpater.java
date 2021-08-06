package com.google.codelab.mlkit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DisplayAdpater extends RecyclerView.Adapter<DisplayAdpater.MyViewHolder> {

    private List<String> urlList;
    public OnClickAdapterListener mOnClickListener;
    private Context context;

    public DisplayAdpater(List<String> urlList, OnClickAdapterListener mOnClickListener, Context context) {
        this.urlList = urlList;
        this.mOnClickListener = mOnClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public DisplayAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayAdpater.MyViewHolder holder, int position) {

        Log.d("DISPLAY ADAPTER", "onBindViewHolder: "+urlList.get(position));
//        URL url=null;
//        try {
//            url=new URL(urlList.get(position));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Picasso.get()
//                .load(urlList.get(position))
//                .placeholder(R.drawable.ic_launcher_background)
//                .into(holder.imageButton);

        //holder.tv.append(urlList.get(position));
//        Picasso.with(context)
//                .load(urlList.get(position).replaceAll(" ",""))
//                .into(holder.imageButton);

        holder.imgView.setVisibility(View.INVISIBLE);
        Picasso.get()
                .load(urlList.get(position).trim())
                .into(holder.imageButton, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e)
                    {
                     //   Log.d("PIC", "onError: "+e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageButton imageButton;
        private ImageView imgView;
        //private TextView tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton=itemView.findViewById(R.id.imgButton);
            imgView=itemView.findViewById(R.id.imgview);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                mOnClickListener.OnClick(view,getAdapterPosition());
                imgView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public interface OnClickAdapterListener {
        void OnClick(View v, int position);
    }
}
