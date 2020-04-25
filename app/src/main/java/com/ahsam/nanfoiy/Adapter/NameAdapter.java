package com.ahsam.nanfoiy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahsam.nanfoiy.Item.Item;
import com.ahsam.nanfoiy.Item.SetViewHolder;
import com.ahsam.nanfoiy.OnTapListener;
import com.ahsam.nanfoiy.R;

import java.util.Collections;
import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<SetViewHolder> {

    private Activity activity;
    List<Item> items = Collections.emptyList();
    private OnTapListener onTapListener;
//    private int lastPosition = -1;
//    Context context;

    public NameAdapter (Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);

        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, final int position) {
        holder.arabic.setText(items.get(position).getArabic());
        holder.divehi.setText(items.get(position).getDivehi());
//        holder.letter.setText(items.get(position).getLetter());
        holder.meaning.setText(items.get(position).getMeaning());
    //    holder.gender.setText(items.get(position).getGender());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTapListener!=null) {
                    onTapListener.OnTapView(position);
                }
            }
        });
//        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    private void setAnimation(View viewToAnimate, int pos)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (pos > lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = pos;
//        }
//    }

    public void setOnTapListener (OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }
}
