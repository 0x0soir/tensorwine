package com.tensorwine.application.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;
import com.tensorwine.application.R;
import android.view.LayoutInflater;
import android.widget.TextView;


/**
 * Created by Jorge Parrilla on 10/1/18.
 */

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private final String[] wines;
    private final String[] images;

    public ImageAdapter(Context c, String[] wines, String[] images ) {
        mContext = c;
        this.images = images;
        this.wines = wines;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return wines.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            Log.i("getView", "Carga nuevo");
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            Log.i("getView", "Carga ya existente");
            grid = convertView;
        }

        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        SmartImageView imageView = (SmartImageView) grid.findViewById(R.id.grid_image);
        textView.setText(wines[position]);

        try {
            String imagenUrl = images[position];

            imageView.setImageUrl(imagenUrl);


        } catch (Exception e){
            Log.e("ImagenVino", "Error al cargar la imagen del vino", e);
        }

        return grid;
    }
}