package com.example.rayan.tingrr_1;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleViewActivity extends RecyclerView
        .Adapter<RecycleViewActivity
        .DataObjectHolder> {
    private ArrayList<CardObject> mDataset;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
        public View view;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView);
            description = (TextView) itemView.findViewById(R.id.textView2);
            image = (ImageView) itemView.findViewById(R.id.img);

            //on click listener
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent openDes = new Intent(view.getContext(), DogInfo.class);
                    //put extra name
                    openDes.putExtra("Dog Name", title.getText().toString());
                    view.getContext().startActivity(openDes);

                }
            });

            //end on click
        }
    }

    public RecycleViewActivity(ArrayList<CardObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getName());
        holder.description.setText(mDataset.get(position).getDes());
        holder.image.setImageDrawable(mDataset.get(position).getImage());
        /*
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */
    }


    public void addItem(CardObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
