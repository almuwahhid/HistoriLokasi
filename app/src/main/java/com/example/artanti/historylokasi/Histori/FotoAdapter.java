package com.example.artanti.historylokasi.Histori;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artanti.historylokasi.R;
import com.example.artanti.historylokasi.Utility.FilePath;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {
    Context context;
    private List<Uri> uris;

    public FotoAdapter(Context context, List<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    @Override
    public FotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tampilan = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foto_adapter, parent, false);
        return new FotoAdapter.ViewHolder(tampilan);
    }

    @Override
    public void onBindViewHolder(FotoAdapter.ViewHolder holder, int position) {
        Uri uri = uris.get(position);
        String uri_file = "";
        if(FilePath.isFile(uri)){
            uri_file = uri.getPath();
        }else{
            uri_file = FilePath.getPath(context, uri);
        }

        File imgFile = new File(uri_file);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.img.setImageBitmap(bitmap);
        }else{
            Log.d("asd", "onBindViewHolder: nothing");
        }
    }


    @Override
    public int getItemCount() {
        return uris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
