package com.example.artanti.historylokasi.MyHistori;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artanti.historylokasi.DetailHistori;
import com.example.artanti.historylokasi.Model.Histori;
import com.example.artanti.historylokasi.R;
import com.example.artanti.historylokasi.Utility.StringUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyHistoriAdapter extends RecyclerView.Adapter<MyHistoriAdapter.ViewHolder> {
    Context context;
    ArrayList<Histori> historis;

    public MyHistoriAdapter(Context context, ArrayList<Histori> historis) {
        this.context = context;
        this.historis = historis;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    private String getDate(String date){
        String year = date.split("-")[0];
        String tgl = date.split("-")[2].substring(0, 2);
        Log.d("asd", "setDate: "+date.split("-")[1]);
        String month = StringUtils.getShortMonthName(Integer.valueOf(date.split("-")[1]));
        return tgl+" "+month;
    }

    private String getYear(String date){
        String year = date.split("-")[0];
        return year;
    }


    @Override
    public MyHistoriAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tampilan = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_histori_adapter, parent, false);
        return new MyHistoriAdapter.ViewHolder(tampilan, viewType);
    }

    @Override
    public void onBindViewHolder(MyHistoriAdapter.ViewHolder holder, int position) {
        final Histori histori = this.historis.get(position);
        holder.txtDate.setText(getDate(histori.getTanggal()));
        holder.txtEvent.setText(histori.getEvent());
        holder.txtYear.setText(getYear(histori.getTanggal()));
        holder.txtPlace.setText(getYear(histori.getNama_tempat()));
        holder.txtTime.setText(histori.getJam());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHistori.class);
                intent.putExtra("id_histori", histori.getId_histori());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtYear)
        TextView txtYear;
        @BindView(R.id.txtEvent)
        TextView txtEvent;
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.txtPlace)
        TextView txtPlace;
        @BindView(R.id.timeline_marker)
        TimelineView mTimelineView;
        @BindView(R.id.cardView)
        CardView cardView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTimelineView.initLine(viewType);
        }
    }
}
