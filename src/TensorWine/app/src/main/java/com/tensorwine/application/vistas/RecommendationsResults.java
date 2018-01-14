package com.tensorwine.application.vistas;

import android.content.Intent;
import android.os.Bundle;

import com.tensorwine.application.R;
import com.tensorwine.application.adaptadores.ImageAdapter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Jorge Parrilla on 10/1/18.
 */

public class RecommendationsResults extends AppCompatActivity {

    String[] wines;
    String[] images;
    String[] winesJson;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_results);

        Intent intent = getIntent();
        wines = intent.getStringArrayExtra("wines");
        images = intent.getStringArrayExtra("images");
        winesJson = intent.getStringArrayExtra("winesJson");

        ImageAdapter adapter = new ImageAdapter(getApplicationContext(), wines, images);
        grid=(GridView)findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "Abriendo " +wines[+ position], Toast.LENGTH_SHORT).show();

                Log.i("clicked", winesJson[+position]);

                cargarVino(winesJson[+position]);
            }
        });
    }

    public void cargarVino(String datosVino) {
        Intent intent = new Intent(this, DatosVinoServidor.class);
        intent.putExtra("jsonWine", datosVino);
        startActivity(intent);
    }
}
