package com.tensorwine.application.adaptadores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.tensorwine.application.R;
import com.tensorwine.application.Vino;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<Vino> wines;

    public CustomListAdapter(Activity context, String[] names, ArrayList<Vino> all_wines) {
        super(context, R.layout.list_single, names);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.wines = all_wines;
    }

    public View getView(int position,View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_single, null,true);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        SmartImageView imageView = (SmartImageView) rowView.findViewById(R.id.imageView);
        TextView owner = (TextView) rowView.findViewById(R.id.owner);
        TextView points = (TextView) rowView.findViewById(R.id.points);

        name.setText(wines.get(position).name);

        owner.setText(wines.get(position).owner);

        points.setText(String.valueOf(wines.get(position).stars == 0 ? "No disponible" : wines.get(position).stars));

        try {

            String imagenUrl = wines.get(position).image;

            if (imagenUrl.length() > 5) {
                imageView.setImageUrl(imagenUrl);
            } else {
                imageView.setImageUrl("http://ns3261968.ip-5-39-77.eu:8000/media/404.png");
            }


        } catch (Exception e){
            Log.e("ImagenVino", "Error al cargar la imagen del vino", e);
        }

        return rowView;

    };
}