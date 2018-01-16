package com.tensorwine.application.almacenamiento;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Jorge Parrilla on 1/1/18.
 */

public class Almacenamiento extends AppCompatActivity {
    String filename = "vinos.json";

    public void guardarVino(Context cnt, String wineName) {
        String savedJson = this.leerVinos(cnt);
        JSONObject json, wine, new_json = new JSONObject();
        JSONArray jsonArray;

        try {
            try {
                json = new JSONObject(savedJson);
            } catch (Exception JSONException){
                json = new JSONObject();
            }

            wine = new JSONObject();

            wine.put("wine_name", wineName);

            try {
                jsonArray = json.getJSONArray("wines");
            } catch (Exception JSONException){
                jsonArray = new JSONArray();
            }

            jsonArray.put(wine);

            new_json.put("wines", jsonArray);

            FileOutputStream fos = cnt.openFileOutput(filename, cnt.MODE_PRIVATE);
            fos.write(new_json.toString().getBytes());
            fos.close();

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), String.format("Encontrado error al escribir fichero"), e);
        }
    }

    public String leerVinos(Context cnt) {
        String result = "";

        try {
            FileInputStream vinos = cnt.openFileInput(filename);
            byte[] buffer = new byte[(int) vinos.getChannel().size()];
            vinos.read(buffer);

            for(byte b:buffer) result+=(char)b;

            vinos.close();
            Log.i(getClass().getSimpleName(), String.format("Lee vino: [%s]", result));
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), String.format("Encontrado error al leer fichero con vinos", e));
        }

        return result;
    }
}
