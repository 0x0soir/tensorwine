package com.tensorwine.application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tensorwine.application.vistas.DatosVinoServidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cz.msebera.android.httpclient.Header;

public class EnviarImagen extends AppCompatActivity {
    ImageView imgView;
    MorphingButton btnMorph1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enviar_imagen);

        imgView = (ImageView) findViewById(R.id.imgView);

        if(Data.getInstance().ruta_imagen!=null){
            imgView.setImageBitmap(BitmapFactory.decodeFile(Data.getInstance().ruta_imagen));
            Log.i("PRUEBA", "Imagen almacenada");
        }else{
            imgView.setImageBitmap(Data.getInstance().bitmap_imagen);
            Log.i("PRUEBA", "Imagen fotografiada");
        }

        btnMorph1 = (MorphingButton) findViewById(R.id.btnMorph1);

        btnMorph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMorphButton1Clicked(btnMorph1);
            }
        });

    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    private void btnConfirmacion(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_green))
                .colorPressed(color(R.color.mb_green_dark))
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
        btnMorph.blockTouch();
    }

    private void btnNormal(MorphingButton btnMorph, int duration) {
        Log.i("HOLA", "HOLA");
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text("Volver a enviar");
        btnMorph.unblockTouch();
        btnMorph.morph(square);
    }

    private void onMorphButton1Clicked(final MorphingButton btnMorph) {

        Toast toast= Toast.makeText(getApplicationContext(),
                "Enviando imagen...", Toast.LENGTH_LONG);
        toast.show();

        btnConfirmacion(btnMorph);

        // Send info to server
        try {
             new SendRequest().execute();
        } catch (Exception e)
        {
            showError();
        }
    }

    public void cargarVino(String datosVino) {
        Intent intent = new Intent(this, DatosVinoServidor.class);
        intent.putExtra("jsonWine", datosVino);
        startActivity(intent);
    }

    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnNormal(btnMorph1, integer(R.integer.mb_animation));
                Log.i("EnviarImagen", "Error al enviar fichero");

                Toast toast= Toast.makeText(getApplicationContext(),
                        "Se ha producido un error al enviar la imagen", Toast.LENGTH_LONG);

                toast.show();
            }
        });

    }

    public class SendRequest extends AsyncTask<String, Void, String> {
        String serverResponse;

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            Log.i(getClass().getSimpleName(), "Enviando imagen al servidor...");
            SyncHttpClient client = new SyncHttpClient();
            RequestParams params = new RequestParams();

            /* Config petición */
            client.setResponseTimeout(20*1000);


            File filesDir = getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir, "prueba.jpg");
            Bitmap image = ((BitmapDrawable)imgView.getDrawable()).getBitmap();

            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error genernado bitmap", e);
            }

            try {
                params.put("wine_photo", imageFile);

                client.post("http://ns3261968.ip-5-39-77.eu:8000/wine_api/recognize_photo", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        serverResponse = null;
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        serverResponse = responseString;
                        cargarVino(responseString);
                    }
                });

                Log.i(getClass().getSimpleName(), serverResponse);
            }
            catch (Exception e){
                Log.e(getClass().getSimpleName(), "Error al cargar la imagen");
                showError();
            }
            Log.i(getClass().getSimpleName(), "Return datos");
            return serverResponse;
        }
    }

}