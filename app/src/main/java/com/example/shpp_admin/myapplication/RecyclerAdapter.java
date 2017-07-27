package com.example.shpp_admin.myapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cvHistory;
        TextView historyName;
        TextView historyValue;

        ViewHolder(View itemView) {
            super(itemView);
            cvHistory = (CardView)itemView.findViewById(R.id.cvHistory);
            historyName = (TextView)itemView.findViewById(R.id.valueName);
            historyValue = (TextView)itemView.findViewById(R.id.valueValue);
        }
    }

    List<HistoryStrings> histories;

    RecyclerAdapter(List<HistoryStrings> histories) {
        this.histories = histories;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.histories_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.historyName.setText(histories.get(position).name);
        holder.historyValue.setText(histories.get(position).value);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}