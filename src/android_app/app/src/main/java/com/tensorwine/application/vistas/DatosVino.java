package com.tensorwine.application.vistas;

import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.tensorwine.application.Data;
import com.tensorwine.application.R;
import com.tensorwine.application.Vino;

/**
 * Created by Virginia RdelC on 14/12/2017.
 */

public class DatosVino extends AppCompatActivity {
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_vino);

        int idVino = Data.getInstance().vino_seleccionado;
        Vino vino = Data.getInstance().vinos.get(idVino);

        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(vino.name);
        TextView owner = (TextView) findViewById(R.id.owner);
        owner.setText(vino.owner);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        ratingBar.setNumStars(5);
        ratingBar.setRating(vino.stars);

        TextView dorigen = (TextView) findViewById(R.id.dorigen);
        dorigen.setText(vino.info_do);

        SmartImageView imagen = (SmartImageView) findViewById(R.id.imageView2);

        try {

            String imagenUrl = vino.image;

            if (imagenUrl.length() > 5) {
                imagen.setImageUrl(imagenUrl);
            } else {
                imagen.setImageUrl("http://ns3261968.ip-5-39-77.eu:8000/media/404.png");
            }


        } catch (Exception e){
            Log.e("ImagenVino", "Error al cargar la imagen del vino", e);
        }

        sa = new SimpleAdapter(this, vino.generateData(),
                android.R.layout.simple_list_item_2,
                new String[] { "line1","line2" },
                new int[] {android.R.id.text1,android.R.id.text2});

        ((ListView)findViewById(R.id.list)).setAdapter(sa);

    }
}