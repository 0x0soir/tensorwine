package com.tensorwine.application.vistas;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.image.SmartImageView;
import com.tensorwine.application.R;
import com.tensorwine.application.Vino;
import com.tensorwine.application.almacenamiento.Almacenamiento;

import org.json.JSONObject;

/**
 * Created by Jorge Parrilla on 31/12/17.
 */

public class DatosVinoServidor extends AppCompatActivity {
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_vino);

        Intent intent = getIntent();
        String datosVino = intent.getStringExtra("jsonWine");

        if (getWineStatus(datosVino).equals("match")){

            TextView nombre = (TextView) findViewById(R.id.nombre);
            nombre.setText(getWineName(datosVino));

            TextView owner = (TextView) findViewById(R.id.owner);
            owner.setText(getWineOwner(datosVino));

            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            ratingBar.setNumStars(5);
            ratingBar.setRating(getWineStars(datosVino));

            TextView dorigen = (TextView) findViewById(R.id.dorigen);
            dorigen.setText(getWineDO(datosVino));

            SmartImageView imagen = (SmartImageView) findViewById(R.id.imageView2);

            try {

                String imagenUrl = getWineImage(datosVino);

                if (imagenUrl.length() > 5) {
                    imagen.setImageUrl(imagenUrl);
                } else {
                    imagen.setImageUrl("http://ns3261968.ip-5-39-77.eu:8000/media/404.png");
                }


            } catch (Exception e){
                imagen.setImageUrl("http://ns3261968.ip-5-39-77.eu:8000/media/404.png");
            }

            /* Wine modules */
            try {
                Vino vinoActual = new Vino(getWineInfo(datosVino));

                sa = new SimpleAdapter(this, vinoActual.generateData(),
                        android.R.layout.simple_list_item_2,
                        new String[] { "line1","line2" },
                        new int[] {android.R.id.text1,android.R.id.text2});

                ((ListView)findViewById(R.id.list)).setAdapter(sa);
            } catch (Exception e) {
                Log.e("DatosVinoServidor", "Error al construir vino", e);
            }

            /* Save wine to database */
            /*new Almacenamiento().guardarVino(this.getApplicationContext(), getWineName(datosVino));
            new Almacenamiento().leerVinos(this.getApplicationContext());*/
        } else{
            Intent vista = new Intent(this, ErrorVino.class);
            startActivity(vista);
        }
    }

    public String getWineName(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        String name = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("name")
                .getAsString();
        return name;
    }

    public String getWineStatus(String datosVino) {
        JsonParser parser = new JsonParser();
        JsonElement elementObject = parser.parse(datosVino);
        String status = elementObject.getAsJsonObject().get("status")
                .getAsString();
        return status;
    }

    public String getWineOwner(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        String name = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("owner")
                .getAsString();
        return name;
    }

    public String getWineImage(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        String image = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("image")
                .getAsString();
        return image;
    }

    public String getWineDO(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        String dorigen = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("detailed")
                .getAsJsonObject().get("info_do")
                .getAsString();
        return dorigen;
    }

    public Float getWineStars(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        String name = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("points")
                .getAsString();
        String[] parts = name.split(" ");

        return Float.parseFloat(parts[2]);
    }

    public JSONObject getWineInfo(String datosVino) {
        JsonParser parser = new JsonParser();

        JsonElement elementObject = parser.parse(datosVino);
        JsonObject wine_info = elementObject.getAsJsonObject().get("wine_info")
                .getAsJsonObject().get("wine_info")
                .getAsJsonObject();

        Log.i("getWineInfo", "Info: "+wine_info);

        JSONObject jsonObj;

        try {
            jsonObj = new JSONObject(wine_info.toString());
        } catch (Exception e) {
            Log.e("getWineInfo", "No se puede recuperar json info");
            return new JSONObject();
        }

        try {
            Log.i("getWineInfo", jsonObj.getString("name"));
        } catch (Exception e){
            Log.e("getWineInof", "TONTO");
        }

        return jsonObj;
    }
}
