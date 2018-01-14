package com.tensorwine.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tensorwine.application.adaptadores.CustomListAdapter;
import com.tensorwine.application.vistas.DatosVino;

import java.util.ArrayList;


/**
 * Created by Virginia RdelC on 14/12/2017.
 */

public class Find extends AppCompatActivity {
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> list = new ArrayList<String>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);

        ArrayList<Vino> vinos = new ArrayList<>();

        final String[] tmpStrings = new String[Data.vinos.size()];

        for(int i = 0; i < Data.vinos.size();i++){
            vinos.add(Data.vinos.get(i));
            tmpStrings[i] = Data.vinos.get(i).name;
        }

        CustomListAdapter adapter = new CustomListAdapter(this, tmpStrings, vinos);

        ListView list= (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = tmpStrings[+position];
                Toast.makeText(getApplicationContext(), "Abriendo "+Slecteditem+"...", Toast.LENGTH_SHORT).show();

                Data.getInstance().vino_seleccionado = (int)id;
                openVino(view);

            }
        });


    }

    public void openVino(View view){
        Intent intent = new Intent(this, DatosVino.class);
        startActivity(intent);
    }

}
