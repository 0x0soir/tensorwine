package com.tensorwine.application;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Virginia RdelC on 15/12/2017.
 */

public class Data {
    private static Data mInstance= null;
    public int vino_seleccionado;
    public static ArrayList<Vino> vinos;
    public static InputStream is;
    public JSONArray array;
    public String ruta_imagen;
    public Bitmap bitmap_imagen;

    protected Data(){
        vino_seleccionado=0;
        try{
            vinos = new ArrayList<Vino>();
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsontext = new String(buffer);
            JSONArray entries = new JSONArray(jsontext);
            array = entries;

            int i;
            int length = entries.length();
            for (i=0;i<length;i++){
                JSONObject post = entries.getJSONObject(i);
                Vino v = new Vino(post);
                vinos.add(v);
            }

        }catch (Exception je){

        }
    }

    public static synchronized Data getInstance(){
        return mInstance;
    }

    public static int createInstance(InputStream inputStream) {
        is = inputStream;
        mInstance = new Data();
        return vinos.size();
    }

}
