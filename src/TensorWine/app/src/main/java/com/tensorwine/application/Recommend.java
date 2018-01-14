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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.jaygoo.widget.RangeSeekBar;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tensorwine.application.adaptadores.ImageAdapter;
import com.tensorwine.application.vistas.DatosVinoServidor;
import com.tensorwine.application.vistas.RecommendationsResults;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class Recommend extends AppCompatActivity {
    ImageView imgView;
    MorphingButton btnRecommend;
    private RangeSeekBar seekbar2;
    private DecimalFormat df = new DecimalFormat("0.00");
    private TextView rank;

    String[] wines;
    String[] images;
    String[] winesJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recommend);

        seekbar2 = (RangeSeekBar)findViewById(R.id.seekbar1);

        rank = (TextView) findViewById(R.id.rank);

        seekbar2.setValue(1,4);

        btnRecommend = (MorphingButton) findViewById(R.id.btnRecommend);

        btnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMorphButton1Clicked(btnRecommend);
            }
        });

        seekbar2.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            String min, max;

            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    seekbar2.setLeftProgressDescription(df.format(min));
                    seekbar2.setRightProgressDescription(df.format(max));
                    this.min = df.format(min);
                    this.max = df.format(max);
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                rank.setText("Rango seleccionado: ["+this.min+", "+this.max+"]");
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
                "Esperando recomendaciones...", Toast.LENGTH_LONG);
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

    public void cargarRecomendaciones(final String datosJson) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /* Serialize datos de json */
                try {
                    JSONArray array = new JSONArray(datosJson);
                    String imageUrl, wineName;
                    wines = new String[array.length()];
                    images = new String[array.length()];
                    winesJson = new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        // Set image
                        JSONObject wineObject = new JSONObject(array.get(i).toString());
                        winesJson[i] = array.get(i).toString();

                        try {
                            imageUrl = wineObject.getJSONObject("wine_info").getJSONObject("wine_info").getString("image");
                        } catch (Exception e) {
                            imageUrl = "http://ns3261968.ip-5-39-77.eu:8000/media/404.png";
                        }
                        try {
                            wineName = wineObject.getJSONObject("wine_info").getJSONObject("wine_info").getString("name");
                        } catch (Exception e) {
                            wineName = "No disponible";
                        }
                        wines[i] = wineName;
                        images[i] = imageUrl;
                        Log.i("Array", array.get(i).toString());
                    }
                } catch (Exception e) {}

                Intent intent = new Intent(getApplicationContext(), RecommendationsResults.class);
                intent.putExtra("wines", wines);
                intent.putExtra("images", images);
                intent.putExtra("winesJson", winesJson);
                startActivity(intent);
            }
        });
    }

    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnNormal(btnRecommend, integer(R.integer.mb_animation));
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
            SyncHttpClient client = new SyncHttpClient();

            /* Config petición */
            client.setResponseTimeout(20*1000);

            try {
                client.get("http://ns3261968.ip-5-39-77.eu:8000/wine_api/recommendations", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        serverResponse = null;
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        serverResponse = responseString;
                        cargarRecomendaciones(responseString);
                    }
                });

                Log.i(getClass().getSimpleName(), serverResponse);
            }
            catch (Exception e){
                Log.e(getClass().getSimpleName(), "Error al cargar las recomendaciones");
                showError();
            }
            Log.i(getClass().getSimpleName(), "Return datos");
            return serverResponse;
        }
    }

}