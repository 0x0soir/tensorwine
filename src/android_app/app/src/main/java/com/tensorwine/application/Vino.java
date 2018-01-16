package com.tensorwine.application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Virginia RdelC on 15/12/2017.
 */

public class Vino {
    public String grape;
    public String name;
    public String owner;
    public String image;
    public float stars;
    public String price;
    public String type;
    public String alcohol;
    public String bottle;
    public String info;
    public String info_do;
    public String pairing;
    public String recomendations;
    public String elaboration;
    private int i;
    private String[][] caracteristicas ;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();


    public Vino(JSONObject object) throws JSONException {
        int numero_carac =0;
        // Name
        name = object.getString("name");
        // Owner
        owner = object.getString("owner");
        // Image
        try {
            image = object.getString("image");
        } catch (Exception e){
            image = "http://ns3261968.ip-5-39-77.eu:8000/media/404.png";
        }
        //Points
        try {
            String points = object.getString("points");
            String[] data_points= points.split(" ");
            stars = Float.parseFloat(data_points[0]);
        }catch (Exception e){
            stars = 0;
        }
        //Price
        try{
            price = object.getString("price");
            numero_carac++;
        }catch (Exception e){
            price = null;
        }
        //Info
        JSONObject detalles = object.getJSONObject("detailed");
        //Grape
        try{
            grape = detalles.getString("info_grape");
            numero_carac++;
        }catch (Exception e){
            grape=null;
        }
        //Alcohol
        try{
            alcohol = detalles.getString("info_alcohol");
            numero_carac++;
        }catch (Exception e){
            alcohol = null;
        }
        //Bottle
        try{
            bottle = detalles.getString("info_bottle");
            numero_carac++;
        }catch (Exception e){
            bottle = null;
        }
        //Elaboration
        try{
            elaboration = detalles.getString("info_elaboration");
            numero_carac++;
        }catch (Exception e){
            elaboration = null;
        }
        //Pairing
        try{
            pairing = detalles.getString("info_pairing");
            numero_carac++;
        }catch (Exception e){
            pairing = null;
        }
        //Recomendaciones
        try {
            recomendations = detalles.getString("info_recommendations");
            numero_carac++;
        }catch (Exception e){
            recomendations = null;
        }
        //text
        try {
            info = detalles.getString("info_text");
            numero_carac++;
        }catch (Exception e){
            info = null;
        }
        //do
        try {
            info_do = detalles.getString("info_do");
            numero_carac++;
        }catch (Exception e){
            info_do = null;
        }
        //Type
        try {
            type = detalles.getString("info_type");
            numero_carac++;
        }catch (Exception e){
            type = null;
        }
        caracteristicas = new String[numero_carac][2];
        generateList();
    }

    public ArrayList<HashMap<String, String>> generateData(){
        HashMap<String,String> item;
        list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<caracteristicas.length;i++){
            item = new HashMap<String,String>();
            item.put( "line1", caracteristicas[i][0]);
            item.put( "line2", caracteristicas[i][1]);
            list.add( item );
        }
        return list;
    }

    public void generateList(){
        int contador =0;
        if(price != null){
            caracteristicas[contador][0]="Precio estimado";
            caracteristicas[contador][1]=price;
            contador++;
        }
        if(grape != null){
            caracteristicas[contador][0]="Uva";
            caracteristicas[contador][1]=grape;
            contador++;
        }
        if(alcohol != null){
            caracteristicas[contador][0]="Alcohol";
            caracteristicas[contador][1]=alcohol;
            contador++;
        }
        if(bottle != null){
            caracteristicas[contador][0]="Botella";
            caracteristicas[contador][1]=bottle;
            contador++;
        }
        if(elaboration != null){
            caracteristicas[contador][0]="Elaboración";
            caracteristicas[contador][1]=elaboration;
            contador++;
        }
        if(pairing != null){
            caracteristicas[contador][0]="Maridaje";
            caracteristicas[contador][1]=pairing;
            contador++;
        }
        if(recomendations != null){
            caracteristicas[contador][0]="Recomendaciones";
            caracteristicas[contador][1]=recomendations;
            contador++;
        }
        if(info_do != null){
            caracteristicas[contador][0]="Denominación de origen";
            caracteristicas[contador][1]=info_do;
            contador++;
        }
        if(type != null){
            caracteristicas[contador][0]="Tipo";
            caracteristicas[contador][1]=type;
            contador++;
        }
        if(info != null){
            caracteristicas[contador][0]="Información adicional";
            caracteristicas[contador][1]=info;
            contador++;
        }
    }
}