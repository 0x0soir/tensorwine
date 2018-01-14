package com.tensorwine.application.vistas;

import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ErrorVino extends AppCompatActivity {
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vino_error);
    }
}