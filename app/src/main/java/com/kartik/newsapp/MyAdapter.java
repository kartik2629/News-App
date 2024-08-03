package com.kartik.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.InnerClass> {
    ArrayList<MyModel> arrayList;
    Context context;

    public MyAdapter(ArrayList<MyModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public InnerClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycard, parent, false);
        return new InnerClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerClass holder, int position) {
        MyModel currentItem = arrayList.get(position);
        holder.txt1.setText(currentItem.getTitle());
        holder.txt2.setText(currentItem.getDescription());

        if (currentItem.getUrlToImage() != null && !currentItem.getUrlToImage().isEmpty()) {
            Glide.with(context)
                    .load(currentItem.getUrlToImage())
                    .into(holder.imageView);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            String url = currentItem.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InnerClass extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        ImageView imageView;

        public InnerClass(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.author);
            txt2 = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
