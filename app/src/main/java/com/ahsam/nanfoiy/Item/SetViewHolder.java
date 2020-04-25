package com.ahsam.nanfoiy.Item;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahsam.nanfoiy.R;

public class SetViewHolder extends RecyclerView.ViewHolder {
    public TextView arabic;
    public TextView divehi;
    public TextView letter;
    public TextView meaning;
    public TextView gender;

    public SetViewHolder(@NonNull View itemView) {
        super(itemView);
        arabic = itemView.findViewById(R.id.arabic);
        divehi = itemView.findViewById(R.id.dhivehi);
//        letter = itemView.findViewById(R.id.letter);
        meaning = itemView.findViewById(R.id.meaning);
     //   gender = itemView.findViewById(R.id.gender);

    }
}
