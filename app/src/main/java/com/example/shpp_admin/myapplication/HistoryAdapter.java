package com.example.shpp_admin.myapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView historiesName;
        TextView historiesValue;

        ViewHolder (View viewItem) {
            super(viewItem);
            cv = (CardView)itemView.findViewById(R.id.cvHistory);
            historiesName = (TextView)itemView.findViewById(R.id.textCardName);
            historiesValue = (TextView) itemView.findViewById(R.id.textCardValue);
        }
    }

    List<HistoryStrings> histories;

    HistoryAdapter(List<HistoryStrings> histories) {
        this.histories = histories;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.histories_item,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.historiesName.setText(histories.get(position).name);
        holder.historiesValue.setText(histories.get(position).value);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
