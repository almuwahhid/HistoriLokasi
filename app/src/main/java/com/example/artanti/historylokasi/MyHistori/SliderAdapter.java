package com.example.artanti.historylokasi.MyHistori;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artanti.historylokasi.Model.Foto;
import com.example.artanti.historylokasi.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by root on 12/28/17.
 */

public class SliderAdapter extends PagerAdapter {

    private ArrayList<Foto> sliders;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(ArrayList<Foto> sliders, Context context) {
        this.sliders = sliders;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.layout_slide_adapter, container, false);
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.slider_page_item);
        File imgFile = new File(sliders.get(position).getNama_file());
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }else{
            Log.d("asd", "onBindViewHolder: nothing");
        }
//        Log.d("ngetes", "instantiateItem: "+sliders.get(position).getUrl());
        /*Picasso.with(context)
                .load(sliders.get(position).getUrl())
                .error(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .into(imageView);*/
        container.addView(imageLayout, 0);
        return imageLayout;
    }
}
